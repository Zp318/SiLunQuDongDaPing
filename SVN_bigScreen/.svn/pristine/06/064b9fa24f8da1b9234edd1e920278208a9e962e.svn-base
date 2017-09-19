package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.tools.CloseableUtils;


/**
 * 压缩文件工具类
 * <功能详细描述>
 * 
 */
public abstract class ZipUtil
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(HtmlExport.class);
        
    /**
     * 打包文件
     * 
     * @param fileList
     *            需要打包的文件集合
     * @param filename
     *            包名称
     * @throws IOException
     * 
     */
    public static void zipFile(List<File> fileList, File filename)
    {
        
        ZipOutputStream out = null;
        FileInputStream in = null;
        OutputStream zipOut = null;
        ZipEntry zipEntry = null;
        try
        {
            byte[] buf = new byte[new Integer("1024")];
            // 获取文件
            zipOut = Files.newOutputStream(filename.toPath(), 
                    StandardOpenOption.WRITE,StandardOpenOption.CREATE);
            out = new ZipOutputStream(zipOut);
            // 循环添加 文件到包中
            for (File file : fileList)
            {
                try
                {
                    in = new FileInputStream(file);
                    zipEntry = new ZipEntry(file.getName());
                    out.setEncoding("GBK");
                    out.putNextEntry(zipEntry);
                    int len;
                    while ((len = in.read(buf)) > 0)
                    {
                        out.write(buf, 0, len);
                    }
                    
                    out.closeEntry();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd]: zipFile error!");
                }
                finally
                {
                    CloseableUtils.closeStream(in);
                }
            }
            
        }
        catch (FileNotFoundException e)
        {
            logger.error("[sc_mobile_fwd]: file not found: {}", filename.getName());
        }
        catch (IOException e1)
        {
            logger.error("[sc_mobile_fwd]: file not found: {}", filename.getName());
        }
        finally
        {
            
            if (null != out)
            {
                try
                {
                    out.close();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd]: ZipOutputStream close error!");
                }
            }
            if (null != zipOut)
            {
                try
                {
                    zipOut.close();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd]: FileOutputStream close error!");
                }
            }
        }
        
    }
}


