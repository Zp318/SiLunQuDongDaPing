package com.huawei.sc_mobile_fwd.comm.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.huawei.seq.WebCoreTools;
import com.huawei.sc_mobile_fwd.comm.logger.SeqLogger;

/**
 * 
 * 类名 : Config
 * 描述:  系统配置
 */
public class DacConfig
{
	/**
	 * FILE_NAME
	 */
    private static final String FILE_NAME = "dac_config.properties";
    
    /**
     * modifyTime
     */
    private static long modifyTime = -1L;
    
    /**
     * properties
     */
    private static Properties properties;
    
    /**
     * 日志对象
     */
    private static final SeqLogger logger = new SeqLogger();
    
    private static void load()
    {
        File file = WebCoreTools.getConfigFile(FILE_NAME).getFile();
        try
        {
            if (file.lastModified() != modifyTime)
            {
                Properties p = new Properties();
                modifyTime = file.lastModified();
                p.load(new FileInputStream(file));
                properties = p;
            }
        }
        catch (IOException e)
        {
            logger.error("load config error.");
        }
        
    }
    
    /**
     * 
     * 方法名: get
     * 描述：获取字符串
     * @param key 配置文件key
     * @return String
     */
    public static String get(String key)
    {
        load();
        return properties.getProperty(key);
    }
    
    /**
     * 
     * 方法名: getLong
     * 描述：获取long类型配置
     * @param key 
     * @return long
     */
    public static long getLong(String key)
    {
        String value = get(key);
        try
        {
            long l = Long.parseLong(value);
            return l;
        }
        catch (NumberFormatException e)
        {
            logger.error("get config " + key + " error");
            return 0L;
        }
        
    }
    
    /**
     * 
     * 方法名: getInt
     * 描述：获取int类型配置
     * @param key 
     * @return int
     */
    public static int getInt(String key)
    {
        long value = getLong(key);
        return (int) value;
    }
    
}