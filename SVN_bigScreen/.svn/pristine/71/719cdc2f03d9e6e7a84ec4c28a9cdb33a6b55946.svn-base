package com.huawei.sc_mobile_fwd.comm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.huawei.seq.tools.SecurityFliterUtils;
import com.huawei.smartcare.dac.sdk.QueryFactory;
import com.huawei.smartcare.dac.sdk.api.DataQuery;
import com.huawei.smartcare.dac.sdk.bean.DataResult;

/**
 * 中间件工具类
 * @author zhiyizhao
 */
public class MWTools
{
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MWTools.class);
    
    /**
     * 查询结果缓存器
     */
    private static final MemoryCache<String, List<Map<String, Object>>> MEMORY_CACHE;
    
    static
    {
        long keep;
        try
        {
            keep = SFConfig.getInt("memory_cache_keep_time");
        } 
        catch (Exception e)
        {
            keep = 0;
        }
        
        MEMORY_CACHE = new MemoryCache<String, List<Map<String, Object>>>(keep);
    }
    
    /**
     * 提取json文件内容，并替换参数
     * @param jsonfile json模板文件名
     * @param params 需要替换的参数
     * @return String json文件内容
     */
    public static String getJson(String jsonfile, Map<String, String> params)
    {
        String content = FileUtils.read(jsonfile);
        
        StringBuilder json = new StringBuilder(content);
        
        String key;
        
        String value;
        
        int start;
        
        int end;
        
        for (Entry<String, String> entry : params.entrySet())
        {
            if (entry.getKey() == null)
            {
                logger.error("[MWTools] Wrong json param key : null, ignore it");
                continue;
            }
            
            value = entry.getValue();
            if (value == null)
            {
                logger.error("[MWTools] wrong json param value : null, recognized as empty sring");
                value = "";
            }
            key = "@" + entry.getKey() + "@";
            start = json.indexOf(key);
            while (start >= 0)
            {
                end = start + key.length();
                json.replace(start, end, value);
                start = json.indexOf(key, end);
            }
        }
        
        return json.toString();
    }
    
    /**
     * 通过json模板查询中间件数据，并返回查询结果
     * @param clazz 查询接口
     * @param jsonfile json模板文件名
     * @param params 需要替换的参数
     * @return List<Map<String, Object>> 数据
     */
    public static List<Map<String, Object>> queryFromTemplate(Class< ? extends DataQuery> clazz,
            String jsonfile, Map<String, String> params)
    {
        String json = getJson(jsonfile, params);
        if (json.length() == 0)
        {
            logger.error("[MWTools]: empty json content! ", SecurityFliterUtils.loggerwhiteListSecurityCode(jsonfile));
            return new ArrayList<Map<String, Object>>(0);
        }
        
        List<Map<String, Object>> resultList;
        // 检查memoryCache，如果存在，则直接返回数据
        resultList = MEMORY_CACHE.get(json);
        if (resultList != null)
        {
            // 返回一份复制的数据，防止副作用
            return deepCopy(resultList);
        }
        
        DataQuery dataQuery = QueryFactory.newQuery(json, clazz);
        resultList = query(dataQuery);
        
        // 缓存结果集
        // 为了避免副作用，深度复制一份resultList
        // 不缓存空数据
        if (resultList.size() > 0)
        {
            MEMORY_CACHE.put(json, deepCopy(resultList));
        }
        
        return resultList;
    }
    
    /**
     * 通过json模板查询中间件数据，并返回查询结果
     * 使用DataQuery查询
     * @param jsonfile json模板文件名
     * @param params 需要替换的参数
     * @return List<Map<String, Object>> 数据
     */
    public static List<Map<String, Object>> queryFromTemplate(String jsonfile, Map<String, String> params)
    {
        return queryFromTemplate(DataQuery.class, jsonfile, params);
    }
    
    /**
     * 深度复制对象
     * 
     * <br/>可以满足一般的json结构（Map、List结构）的复制
     * <br/>不是任意的对象都可复制，尤其不能复制非JavaBean自定义类
     * <br/>如果obj中有循环引用，则可能会出问题
     * 
     * @param obj 
     * @param <T> 
     * @return T 复制对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T deepCopy(T obj)
    {
        return (T) JSON.parse(JSON.toJSONString(obj));
    }
    
    /**
     * 查询数据
     * DataQuery查询封装，处理查询失败或者空数据
     * @param dataQuery DataQuery对象
     * @return List<Map<String, Object>> 数据
     */
    public static List<Map<String, Object>> query(DataQuery dataQuery)
    {
        DataResult dataResult = dataQuery.query();
        
        List<Map<String, Object>> result = null;
        
        result = dataResult == null ? null : dataResult.getData();
        result = result != null ? result : new ArrayList<Map<String, Object>>(0);
        return result;
    }

}
