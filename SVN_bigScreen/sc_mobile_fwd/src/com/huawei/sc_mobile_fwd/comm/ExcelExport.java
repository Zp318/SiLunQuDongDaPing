package com.huawei.sc_mobile_fwd.comm;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.common.Const;
import com.huawei.seq.tools.ExportUtils;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  z00216173
 * @version  [版本号, 2012-7-11]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@SuppressWarnings("deprecation")
public class ExcelExport implements ExportFile
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ExcelExport.class);
    
    /**
     * 导出Excel文件
     * @param params 输入参数
     * @return File 文件
     */
    @SuppressWarnings("resource")
	@Override
    public File exportFile(Map<String, Object> params)
    {
        File resultFile = null;
        
        //获取参数
        String kpiName = (String)params.get("kpiName");
        String timeKpiName = (String)params.get("timeKpiName");
        String[] titleList = (String[])params.get("title");
        @SuppressWarnings("unchecked")
        List<String[]> dataList = (List<String[]>)params.get("data");
        @SuppressWarnings("unchecked")
        ArrayList<String> imageFilePaths = (ArrayList<String>)params.get("image");
        Integer pageSize = (Integer)params.get("pageSize");
        
        String tempFilePath = ExportUtils.getBasePath();
        tempFilePath = tempFilePath + ExportUtils.getTempPath(Const.EXPORT_PRIVATE_TYPE, null);
        String path = tempFilePath + (String)params.get("fileName");

        // 图片路径
        OutputStream os = null;
        HSSFWorkbook workBook = null;        
        
        try
        {
            File file = new File(path + ".xls");
            if (!file.getParentFile().mkdirs())
            {
                logger.error("[sc_mobile_fwd]: Make dirs failed: {}", file.getParentFile().getName());
                
            }
            workBook = new HSSFWorkbook();
            os = FileUtil.getOutputStream(path + ".xls");
            if (null == os)
            {
                logger.error("[sc_mobile_fwd]: getOutputStream failed!: {}", file.getParentFile().getName());
                return file;   
            }
            HSSFSheet sheet = workBook.createSheet(kpiName);
            String[] titleCol = new String[1];
            titleCol[0] = (null == timeKpiName) ? kpiName : timeKpiName;
            creatStyledRow(workBook, sheet, 0, ExportConst.TITLE_FONT, titleCol);
            
            //合并标题行
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0,
                    titleList.length - 1));
            HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
            
            if (0 == imageFilePaths.size())
            {
                //填充数据
                fillExcelTable(workBook, sheet, dataList, titleList);
            }
            
            for (int i = 0; i < imageFilePaths.size(); i++)
            {
                String image = imageFilePaths.get(i);
                int start = sheet.getLastRowNum() + 1;
                HSSFRow imgRow = sheet.createRow(start);
                
                // 插入图像               
                int imgHeight = insertImageToExcel(workBook,
                        sheet,
                        patriarch,
                        image,
                        start);
                imgRow.setHeightInPoints(imgHeight);
                
                int startIndex = i * pageSize;
                int endIndex = (i == imageFilePaths.size() - 1) ? dataList.size()
                        : (i + 1) * pageSize;
                List<String[]> data = dataList.subList(startIndex, endIndex);
                
                //填充数据
                fillExcelTable(workBook, sheet, data, titleList);
                
                // 删除临时图片文件
                if (0 != imageFilePaths.size())
                {
                    File imageFile = new File(image);
                    
                    boolean isDeleted = imageFile.delete();
                    
                    if (!isDeleted)
                    {
                        logger.error("[sc_mobile_fwd]: Temp file can't be deleted!");
                    }
                }
                
            }
            
            workBook.write(os);
            os.flush();
            
            //压缩文件
            List<File> listFile = new ArrayList<File>();
            listFile.add(file);
            resultFile = new File(path + ".zip");
            ZipUtil.zipFile(listFile, resultFile);
            
            //删除临时文件
            FileUtil.deleteFiles(listFile);
                      
        }
        catch (IOException e)
        {
            logger.error("[sc_mobile_fwd]: IO handle exception!");
        }
        finally
        {
            if (null != os)
            {
                try
                {
                    os.close();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd]: IO close exception!");
                }
            }
        }
        
        return resultFile;
    }
    
    /**
     * 将数据填充到EXCEL中
     * @param wb 工作薄
     * @param sheet sheet页
     * @param dataList 数据集合
     * @param cols 列集合
     * @return TABLE对象
     */
    private void fillExcelTable(HSSFWorkbook wb, HSSFSheet sheet,
            List<String[]> dataList, String[] cols)
    {
        
        int start = sheet.getLastRowNum() + 1;
        
        // 创建表头
        creatStyledRow(wb, sheet, start, ExportConst.HEADER_FONT, cols);
        
        // 循环数据列,给表格添加数据
        for (String[] data : dataList)
        {
            start = sheet.getLastRowNum() + 1;
            
            creatStyledRow(wb, sheet, start, ExportConst.CONTENT_FONT, data);
        }
    }
    
    /**
     * 生成固定格式的行，并填入数据
     * @param workBook
     * @param sheet
     * @param start:行号
     * @param part：title、header、conent三种具有不同的格式
     * @param data：需填入的数据
     */
    private void creatStyledRow(HSSFWorkbook workBook, HSSFSheet sheet,
            int start, String part, String[] data)
    {
        
        HSSFRow row = sheet.createRow(start);
        
        HSSFCellStyle style = workBook.createCellStyle();
        
        HSSFFont font = workBook.createFont();
        
        float cellHeight = 0;
        short charaHeight = 0;
        short charaBold = 0;
        
        if (ExportConst.CONTENT_FONT.equals(part))
        {
            cellHeight = ExportConst.CONTENT_HEIGHT;
            charaHeight = ExportConst.CHARA_HEIGHT;
            charaBold = ExportConst.CHARA_BOLD;
            
        }
        if (ExportConst.HEADER_FONT.equals(part))
        {
            cellHeight = ExportConst.HEADER_CELL_HEIGHT;
            charaHeight = ExportConst.HEADER_CHARA_HEIGHT;
            charaBold = ExportConst.HEADER_CHARA_BOLD;
        }
        if (ExportConst.TITLE_FONT.equals(part))
        {
            cellHeight = ExportConst.TITLE_CELL_HEIGHT;
            charaHeight = ExportConst.TITLE_CHARA_HEIGHT;
            charaBold = ExportConst.TITLE_CHARA_BOLD;
        }
        
        row.setHeightInPoints(cellHeight);
        font.setFontHeightInPoints(charaHeight);
        font.setBoldweight(charaBold);
        
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setFont(font);
        
        for (int i = 0; i < data.length; i++)
        {
            HSSFCell cell = row.createCell(i);
            cell.setCellValue(data[i]);
            cell.setCellStyle(style);
            sheet.setColumnWidth(i, ExportConst.CELL_WIDTH_CHARA
                    * ExportConst.RATIO);
        }
        
    }
    
    /**
     * 将柱状图插入相应的行
     * 修改q00215040，2012-07-10，增加了文件输出流的关闭
     * 向excel中插入图片
     * @param wb
     * @param sheet
     * @param imageFile
     */
    private int insertImageToExcel(HSSFWorkbook wb, HSSFSheet sheet,
            HSSFPatriarch patriarch, String imageFile, int start)
    {
        //先把图片文件读进缓存中
        BufferedImage bufferImg = null;
        
        //先把读进来的图片放到一个ByteArrayOutputStream中，以便产生ByteArray 
        ByteArrayOutputStream byteArrayOut = null;
        int height = 0;
        int width = 0;
        try
        {
            
            bufferImg = ImageIO.read(new File(imageFile));
            byteArrayOut = new ByteArrayOutputStream();
            ImageIO.write(bufferImg,
                    ExportConst.PNG.replace(".", ""),
                    byteArrayOut);
            
            height = bufferImg.getHeight();
            width = bufferImg.getWidth();
            
            int cellCount = (int)((width / (ExportConst.CELL_WIDTH_CHARA * ExportConst.CHA_TO_PIX)) + 1);
            for (int i = 1; i <= cellCount; i++)
            {
                sheet.setColumnWidth(i,
                        (int)(ExportConst.CELL_WIDTH_CHARA * ExportConst.RATIO));
            }
            sheet.addMergedRegion(new CellRangeAddress(start, start, 0,
                    cellCount));
            
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                    ExportConst.DX, ExportConst.DY, (short)0, start,
                    (short)cellCount, start);
            
              //插入图片
            patriarch.createPicture(anchor,
                        wb.addPicture(byteArrayOut.toByteArray(),
                                HSSFWorkbook.PICTURE_TYPE_PNG)); 
        }
        catch (IOException e)
        {
            logger.error("[sc_mobile_fwd]: insertImageToExcel ERROR!");
        }
        finally
        {
            try
            {
                if (null != byteArrayOut)
                {
                    byteArrayOut.close();
                }
            }
            catch (IOException e)
            {
                logger.error("[sc_mobile_fwd]: insertImageToExcel ERROR!");
            }
        }
        return height;
        
    }
}
