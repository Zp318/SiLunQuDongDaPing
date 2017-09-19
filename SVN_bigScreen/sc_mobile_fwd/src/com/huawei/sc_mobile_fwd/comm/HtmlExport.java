package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.common.Const;
import com.huawei.seq.tools.ExportUtils;



/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 */
public class HtmlExport implements ExportFile
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HtmlExport.class);
    
    /**
     * 导出html文件
     * @param params 输入参数
     * @return File 文件
     */
    @Override
    public File exportFile(Map<String, Object> params)
    {
        File resultFile = null;
        //获取参数
        String kpiName = (String)params.get("kpiName");
        String[] titleList = (String[])params.get("title");
        @SuppressWarnings("unchecked")
        List<String[]> dataList = (List<String[]>)params.get("data");
        @SuppressWarnings("unchecked")
        ArrayList<String> imageFilePaths = (ArrayList<String>)params.get("image");
        Integer pageSize = (Integer)params.get("pageSize");
        
        String tempFilePath = ExportUtils.getBasePath();
        tempFilePath = tempFilePath + ExportUtils.getTempPath(Const.EXPORT_PRIVATE_TYPE, null);
        String path = tempFilePath + kpiName;
        
        StringBuilder sb = new StringBuilder();
        OutputStream htmlOs = null;
        
        try
        {
            //创建HTML
            File file = new File(path + ".html");
            if (!file.getParentFile().mkdirs())
            {
                logger.error("[sc_mobile_fwd]: Make dirs failed: {}", file.getParentFile().getName());
                
            }
            if (!file.exists())
            {
                boolean isFail = file.createNewFile();
                if (isFail)
                {
                    logger.error("[sc_mobile_fwd]: exportHtml ERROR : Temp file can't be create");
                }
            }
            
            //添加html头
            sb.append(createHead(kpiName));
            
            //添加Html的body
            sb.append("<body>");
            
            //添加 标题
            sb.append("<div align='center'>");
            sb.append("<h2 align='center'>" + kpiName + "</h2>");
            sb.append("</div>");
            
            //若没有图片，只添加表格 
            if (0 == imageFilePaths.size())
            {
                sb.append(createTable(titleList, dataList));
            }
            
            int start = 0;
            int end = 0;
            
            for (int i = 0; i < imageFilePaths.size(); i++)
            {
                //添加图片
                String imagepath = imageFilePaths.get(i);
                int num = imagepath.lastIndexOf("\\");
                String imgName = imagepath.substring(num + 1);
                
                sb.append(createPicture(imgName));
                sb.append("<br>");
                
                //添加表格
                start = i * pageSize;
                end = (i == (imageFilePaths.size() - 1)) ? dataList.size()
                        : ((i + 1) * pageSize);
                
                sb.append(createTable(titleList, dataList.subList(start, end)));
                
                sb.append("<br>");
            }
            
            sb.append("</div>");
            sb.append("</body>");
            sb.append("</html>");
            
            //生成HTML
            htmlOs = FileUtil.getOutputStream(path + ".html");
            if (null == htmlOs)
            {
                logger.error("[sc_mobile_fwd]: getOutputStream failed {}", file.getParentFile().getName());
                return file;
            }
            htmlOs.write(sb.toString().getBytes("gbk"));
            
            htmlOs.flush();
            
            List<File> listFile = new ArrayList<File>();
            listFile.add(file);
            for (String filePath : imageFilePaths)
            {
                listFile.add(new File(filePath));
            }
            
            //压缩html和对应的图片
            resultFile = new File(path + ".zip");
            ZipUtil.zipFile(listFile, resultFile);
            
            //删除临时文件
            FileUtil.deleteFiles(listFile);            
        }
        catch (IOException e)
        {
            // mod by gKF73638 for Fortify问题 20140424
            logger.error("[sc_mobile_fwd]: exportHtml ERROR!");
        }
        finally
        {
            if (null != htmlOs)
            {
                try
                {
                    htmlOs.close();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd]: FileOutputStream close error!");
                }
            }
            
        }
        
        return resultFile;
    }
    
    /**生成导出的Html文件头
     * <一句话功能简述>
     * <功能详细描述>
     * @param kpiName 标题名字
     * @return String 拼接的Html语句
     * @see [类、类#方法、类#成员]
     */
    private String createHead(String kpiName)
    {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<html><head><title>" + kpiName + "</title>");
        buffer.append("<meta http-equiv='content-type' content='text/html' charset='gbk';>");
        buffer.append("<style type='text/css'> table td {text-align:center; border:1px solid #ddd;}</style>");
        buffer.append("</head>");
        
        return buffer.toString();
    }
    
    /**生成导出的Html文件图片
     * <一句话功能简述>
     * <功能详细描述>
     * @param imgPath 图片路径
     * @return String 拼接的Html语句
     * @see [类、类#方法、类#成员]
     */
    private String createPicture(String imgPath)
    {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<div align='center'>" + "<img src= " + imgPath
                + " </img>");
        
        return buffer.toString();
    }
    
    /**生成导出的Html文件表格
     * <一句话功能简述>
     * <功能详细描述>
     * @param titleList 表头
     * @param dataList  表格数据
     * @return String   拼接的Html语句
     * @see [类、类#方法、类#成员]
     */
    private String createTable(String[] titleList, List<String[]> dataList)
    {
        StringBuilder buffer = new StringBuilder();
        
        buffer.append("<div align='center'>");
        buffer.append("<table  cellpadding='0' cellspacing='0' width='80%' style='font-size:13px;'>");
        
        //添加表头
        buffer.append("<tr style='background-color:rgb(" + ExportConst.RED
                + "," + ExportConst.GREEN + "," + ExportConst.BLUE + ")'>");
        for (String title : titleList)
        {
            buffer.append("<td height='25' nowrap='nowrap'>" + title + "</td>");
        }
        buffer.append("</tr>");
        
        //添加表格数据
        for (String[] rowData : dataList)
        {
            buffer.append("<tr>");
            
            for (String data : rowData)
            {
                data = (null == data) ? " " : data;
                buffer.append("<td height='25' nowrap='nowrap'>" + data
                        + "</td>");
            }
            
            buffer.append("</tr>");
            
        }
        buffer.append("</table>");
        
        return buffer.toString();
    }
}
