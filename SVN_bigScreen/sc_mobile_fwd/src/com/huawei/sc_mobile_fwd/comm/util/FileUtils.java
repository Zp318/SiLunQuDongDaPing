package com.huawei.sc_mobile_fwd.comm.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.WebCoreTools;

/**
 * 文件工具类
 * @author zhiyizhao
 */
public class FileUtils
{
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    
    /**
     * 是否缓存文件内容的总控参数
     * 默认由配置文件中读取
     */
    private static final boolean CACHE_CONTENT;
    
    static
    {
        boolean b;
        try 
        {
            b = SFConfig.getBoolean("cache_file_content");
        }
        catch (Exception e)
        {
            b = false;
        }
        CACHE_CONTENT = b;
    }
    
    /**
     * 文件内容缓存
     */
    private static final Map<String, FileInfo> CACHE = new HashMap<String, FileInfo>();
    
    /**
     * 读取文本文件，并返回其最新的内容，支持开发调试阶段对文件的实时修改<br/>
     * 支持行注释//和多行注释/* *<a>/</a><br/>
     * 
     * @param filename json模板文件名
     * @return jsonfile的文件内容
     */
    public static String read(String filename)
    {
        return read(filename, true);
    }
    
    /**
     * 读取文本文件，并返回其最新的内容，支持开发过程中文件的实时修改<br/>
     * 支持行注释//和多行注释/* *<a>/</a><br/>
     * 
     * @param filename json模板文件名
     * @param ifCache 是否需要缓存该文件的内容
     * @return jsonfile的文件内容
     */
    public static String read(String filename, boolean ifCache)
    {
        FileInfo fi = CACHE_CONTENT && ifCache ? CACHE.get(filename) : null;
        if (fi == null)
        {
            fi = new FileInfo(filename);
            if (CACHE_CONTENT && ifCache)
            {
                CACHE.put(filename, fi);
            }
                
        }
        
        return fi.ensure() ? fi.content : "";
    }
    
    /**
     * 获取WEB-INF/conf下的文件
     * @param filename 文件名（以正则匹配）
     * @return File 配置文件
     */
    public static File getConfigFile(String filename)
    {
        return WebCoreTools.getConfigFile(filename).getFile();
    }
    
    /**
     * 简单的文件信息类，如果文件为空或者没有
     */
    private static final class FileInfo
    {
    	/**
    	 * filename
    	 */
        private String filename;
        
        /**
         * file
         */
        private File file;
        
        /**
         * lastModified
         */
        private long lastModified = 0;
        
        /**
         * content
         */
        private String content;
        
        private FileInfo(String filename)
        {
            this.filename = filename;
            loadContent();
        }
        
        /**
         * 根据filename属性，寻找对应的文件
         * 如果找到文件，且文件可读，则返回true，否则返回false
         */
        private boolean cacheFile()
        {
            File f = FileUtils.getConfigFile(this.filename);
            if (f == null || !f.exists() || !f.canRead())
            {
                return false;
            }
            this.file = f;
            return true;
        }
        
        /**
         * 读取文件内容，同时更新文件的修改lastModified
         * 如果更新文件失败，或者文件为空，则不修改lastModified和content
         */
        private boolean loadContent()
        {
            if (!cacheFile())
            {
                return false;
            }

            byte[] buff = null;
            try
            {
                buff = Files.readAllBytes(this.file.toPath());
            }
            catch (IOException e)
            {
                logger.error("[FileUtils]: file read fail: " + this.filename);
                return false;
            }
            
            if (buff == null || buff.length == 0)
            {
                logger.error("[FileUtils]: file is empty: " + this.file.getPath());
                this.content = "";
                return true;
            }
            String contentStr = "";
            try
            {
                contentStr = new String(buff, "UTF-8");
            }
            catch (UnsupportedEncodingException e)
            {
                logger.error("[sc_mobile_fwd]: file analysis failed! May file encoding is not utf-8");
            }
            // 删除注释
            contentStr = contentStr.replaceAll("//.*?\r?\n", "\n");
            contentStr = contentStr.replaceAll("/+\\*[\\s\\S]*?\\*/", " ");
            
            this.content = contentStr;
            this.lastModified = file.lastModified();
            return true;
        }
        
        /**
         * 确认文件存在，并且将最新内容读入缓存中
         * 如果文件不存在或者不可读则返回false，否则返回true
         */
        private boolean ensure()
        {
            return this.file != null
                    && (this.file.lastModified() <= this.lastModified || loadContent());
            
        }
    }
}
