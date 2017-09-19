package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 获取图片文件
 * <功能详细描述>
 * 
 */
public abstract class ImageUtil
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);
    
    /**
     * 保存临时文件
     * @param imageByte 上传的图片字节数组
     * @param request 请求对象
     * @return 保存文件路径、URL和字节流数据的MAP
     */
    public static String saveImageFile(byte[] imageByte,
            HttpServletRequest request)
    {
        // 完整路径
        String realPath = getTempFilePath(request, ExportConst.PNG);
        
        OutputStream fos = null;
        
        try
        {
            fos = Files.newOutputStream(new File(realPath).toPath(), 
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);        
            
            fos.write(imageByte);
            
            fos.flush();
        }
        catch (FileNotFoundException e)
        {
            logger.error("[cem_common]: file not found: {}", realPath);
        }
        catch (IOException e)
        {
            logger.error("[cem_common]: save file failed: {}", realPath);
        }

        finally
        {
            if (null != fos)
            {
                try
                {
                    fos.close();
                }
                catch (IOException e)
                {
                    logger.error("[cem_common]: FileOutputStream close error!");
                }
            }
        }
        
        return realPath;
    }
    
    /**
     * 生成随机名称的文件
     * @param request 请求对象
     * @param fileType 文件类型
     * @return 临时文件的物理路径
     */
    public static String getTempFilePath(HttpServletRequest request,
            String fileType)
    {
        String tempFilePath = ImageUtil.class.getResource("/").getPath()
                + "../../";
        
        tempFilePath = tempFilePath + ExportConst.TEMPFILEPATH + File.separator;
        // 文件名,UUID
        String fileName = UUID.randomUUID().toString();
        
        // 完整路径
        String pdfPath = tempFilePath + fileName + fileType;
        
        return pdfPath;
    }
}
