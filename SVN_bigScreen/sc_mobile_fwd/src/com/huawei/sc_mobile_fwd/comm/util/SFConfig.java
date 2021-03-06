package com.huawei.sc_mobile_fwd.comm.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 配置信息类
 * @author zhiyizhao
 */
@SuppressWarnings("unchecked")
public class SFConfig
{
    
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(SFConfig.class);
    
    /**
     * 参数缓存
     */
    private static final Map<String, Object> CONFIG_MAP;
    
    static
    {
        Map<String, Object> map;
        try
        {
            map = (Map<String, Object>) JSON.parse(FileUtils.read("sefon_config.json", false));
        }
        catch (Exception e)
        {
            logger.error("[SFConfig] parse sf_config.json file error");
            map = new HashMap<String, Object>();
        }
        CONFIG_MAP = map;
    }
    
    /**
     * 获取配置文件中的参数
     * @param key 
     * @return Object 
     */
    public static Object get(String key)
    {
        Object value = CONFIG_MAP.get(key);
        if (value == null)
        {
            logger.error("[SFConfig] null value for key[" + key + "] in config map.");
        }
        
        return value;
    }
    
    /**
     * 获取配置文件中的整型参数
     * @param key 
     * @return Integer 
     */
    public static Integer getInt(String key)
    {
        Object obj = get(key);
        if (obj == null)
        {
            throw new RuntimeException("[SFConfig] null for " + key);
        }
        else if (obj instanceof Integer)
        {
            return (Integer) obj;
        }
        else
        {
            return Integer.parseInt(String.valueOf(obj));
        }
    }
    
    /**
     * 获取配置文件中的长整型参数
     * @param key 
     * @return Long 
     */
    public static Long getLong(String key)
    {
        Object obj = get(key);
        if (obj == null)
        {
            throw new RuntimeException("[SFConfig] null for " + key);
        }
        else if (obj instanceof Long)
        {
            return (Long) obj;
        }
        else
        {
            return Long.parseLong(String.valueOf(obj));
        }
    }
    
    /**
     * 获取配置文件中的浮点型参数
     * @param key 
     * @return Double 
     */
    public static Double getDouble(String key)
    {
        Object obj = get(key);
        if (obj == null)
        {
            throw new RuntimeException("[SFConfig] null for " + key);
        }
        else if (obj instanceof Double)
        {
            return (Double) obj;
        }
        else
        {
            return Double.parseDouble(String.valueOf(obj));
        }
    }
    
    /**
     * 获取配置文件中的字符串型参数
     * @param key 
     * @return String 
     */
    public static String getString(String key)
    {
        return String.valueOf(get(key));
    }
    
    /**
     * 获取配置文件中的布尔型参数
     * @param key 
     * @return String 
     */
    public static boolean getBoolean(String key)
    {
        Object obj = get(key);
        if (obj == null)
        {
            throw new RuntimeException("[SFConfig] null for " + key);
        }
        else if (obj instanceof Boolean)
        {
            return (Boolean) obj;
        }
        else
        {
            return Boolean.parseBoolean(String.valueOf(obj));
        }
    }
}
