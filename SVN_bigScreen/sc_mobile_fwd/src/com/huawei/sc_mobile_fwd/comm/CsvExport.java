package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;







import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVWriter;

import com.huawei.seq.common.Const;
import com.huawei.seq.tools.CloseableUtils;
import com.huawei.seq.tools.ConfigUtils;
import com.huawei.seq.tools.ExportUtils;

/**
 * 导出CSV文件
 * <功能详细描述>
 * 
 */
public class CsvExport implements ExportFile
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CsvExport.class);
    
    /**
     * 导出CSV文件
     * @param params 输入参数
     * @return File 文件
     */
    @Override
    public File exportFile(Map<String, Object> params)
    {
        File resultFile = null;
        
        String timeKpiName = (String)params.get("timeKpiName");
        //获取参数
        String kpiName = (null == timeKpiName) ? (String)params.get("kpiName") : timeKpiName;
        String[] titleList = (String[])params.get("title");
        @SuppressWarnings("unchecked")
        List<String[]> dataList = (List<String[]>)params.get("data");
        
        String tempFilePath = ExportUtils.getBasePath();
        tempFilePath = tempFilePath + ExportUtils.getTempPath(Const.EXPORT_PRIVATE_TYPE, null);
        String path = tempFilePath + (String)params.get("fileName");
        
        //将标题和表头加入参数封装
        dataList.add(0, titleList);
        dataList.add(0, new String[]{kpiName});
                
        CSVWriter writer = null;
        OutputStream fileOutput = null;
        
        try
        {
            File file = new File(path + ".csv");
            if (!file.getParentFile().mkdirs())
            {
                logger.error("[cem_common]: Make dirs failed:" + file.getParentFile().getName());
                
            }
            OutputStreamWriter outputStreamWriter = null;
            fileOutput = Files.newOutputStream(file.toPath(), 
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
            outputStreamWriter = new OutputStreamWriter(fileOutput, ConfigUtils.getConfigValue("csvExportEncode"));
            
            writer = new CSVWriter(outputStreamWriter);
            
            for (int i = 0; i < dataList.size(); i++)
            {
                writer.writeNext(dataList.get(i));
            }
            
            writer.close();
            
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
            logger.error("[cem_common]: IO handle exception!");
        }

        finally
        {
            CloseableUtils.closeStream(fileOutput);
            CloseableUtils.closeStream(writer);
        }
        
        return resultFile;
    }
}
