package com.huawei.sc_mobile_fwd.comm.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * 类名 : SqlParamUtils
 * 描述: Sql模板工具类。
 */
public class SqlParamUtils
{
    /** 
     * 方法名: transformSqlParamMap<br>
     * 描述：将原始的参数 Map 替换成 SQL模板中使用的Map： 
     * 如："param_name=param_value" >>> "@param_name=param_value"
     * @param orgMap 原始的参数Map
     * @return Map<String,String> 修改后的Map
     * 
     */
    public static Map<String, String> transformSqlParamMap(
            Map<String, String> orgMap)
    {
        Map<String, String> sqlTemplateMap = new HashMap<String, String>();
        for (Map.Entry<String, String> entry : orgMap.entrySet())
        {
            sqlTemplateMap.put("@" + entry.getKey(), entry.getValue());
        }
        return sqlTemplateMap;
    }
}
