package com.huawei.sc_mobile_fwd.task.job;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.huawei.seq.tools.SecurityFliterUtils;

/**
 * 
 * 类名 : CleanOverdueFileJob
 * 描述: 清除过期的临时文件
 */
@Service("cleanOverdueFileJob")
public class CleanOverdueFileJob
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(CleanOverdueFileJob.class);
    
    /**
     * 小时对应的UTC时间
     */
    private static final long HOUR_UTC = 3600;
    
    /**
     * 单位转换进制
     */
    private static final long THOUSAND = 1000;
            
    /**
     * 导出临时文件存储时间
     */
    private static final int EXPORT_FILE_STORE_TIME = 1;
    
    /**
     * 
     * 方法名: delteOverdueFile
     * 描述：<删除导出后在服务器上的残留文件> void
     */
    public void deleteOverdueFile()
    {
        String tempFilePath = CleanOverdueFileJob.class.getResource("/").getPath() + "../export";

        File file = new File(tempFilePath);
        //删除冗余文件
        deleteOverdueFile(file, EXPORT_FILE_STORE_TIME);
        //删除冗余文件夹
        deleteOverdueDirectory(file);
    }
    
    private void deleteOverdueFile(File file, int exportFileStoreTime)
    {
        boolean isOk = false;
        if (file.isDirectory())
        {
            //删除过期文件
            File[] fileList = file.listFiles();
            if (null != fileList)
            {
                for (int i = 0; i < fileList.length; i++)
                {
                    deleteOverdueFile(fileList[i], exportFileStoreTime);
                }
            }
        }
        else
        {
            if ((System.currentTimeMillis() - file.lastModified()) > (exportFileStoreTime * HOUR_UTC * THOUSAND))
            {
                try
                {
                    logger.info("[sc_mobile_fwd_export] deleteOverdueFile fileName is {}", 
                            SecurityFliterUtils.loggerwhiteListSecurityCode(file.getCanonicalPath()));
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd_export]: getCanonicalPath is error throws IOException");
                }
                isOk = file.delete();
                if (!isOk)
                {
                    logger.info("Delete the File " + file.getName() + " is Failed");
                }
            }
        }
    }
    
    private void deleteOverdueDirectory(File file)
    {
        boolean isOk = false;
        if (file.isDirectory())
        {
            //删除过期文件
            File[] fileList = file.listFiles();
            if (null != fileList)
            {
                if (fileList.length == 0)
                {
                    try
                    {
                        logger.info("[sc_mobile_fwd_export] deleteOverdueFile fileName is {}",
                                SecurityFliterUtils.loggerwhiteListSecurityCode(file.getCanonicalPath()));
                    }
                    catch (IOException e)
                    {
                        logger.error("[sc_mobile_fwd_export]: getCanonicalPath is error throws IOException");
                    }
                    isOk = file.delete();
                    if (!isOk)
                    {
                        logger.info("Delete the File " + file.getName() + " is Failed");
                    }
                }
                else
                {
                    for (File files : fileList)
                    {
                        deleteOverdueDirectory(files);
                    }
                }
            }
        }
    }
}
