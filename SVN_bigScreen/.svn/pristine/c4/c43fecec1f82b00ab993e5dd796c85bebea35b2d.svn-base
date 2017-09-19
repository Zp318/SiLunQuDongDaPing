package com.huawei.sc_mobile_fwd.comm.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.huawei.seq.WebCoreTools;
import com.huawei.seq.bean.SqlTemplateParam;
import com.huawei.seq.tools.SecurityFliterUtils;
/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 */
public class SecurityBasicDao
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(SecurityBasicDao.class);
    /**
     * 获取SQL语句
     * @param filePath 模板文件名称
     * @param templateName SQL语句的Key
     * @param paramList SQL语句的参数列表
     * @return SQL语句
     */
    public static List<Object> getSql(String filePath, String templateName,
            List<SqlTemplateParam> paramList)
    {
        String sqlTemplate = WebCoreTools.getSQLProperties(filePath)
                .getSQL(templateName);
        String resultSql = getSql(sqlTemplate, paramList);
        List<Object> result = new ArrayList<Object>();
        result.add(resultSql);
        String sqlStr = resultSql;
        StringBuffer sqlParam = new StringBuffer();
        MapSqlParameterSource temp = new MapSqlParameterSource();
        if (null != paramList)
        {
            for (SqlTemplateParam param : paramList)
            {
                if (param != null)
                {
                    String key = param.getKey();
                    String value = param.getValue();
                    if (key == null)
                    {
                        key = "";
                    }
                    if (value == null)
                    {
                        value = "";
                    }
                    key = key.replaceAll("@", "");
                    value = value.replaceAll("\''", "\'");
                    sqlParam.append(key + "=" + value + "||");
                    sqlStr = sqlStr.replaceAll(":" + key, value);
                    temp.addValue(key, value);
                }
            }
        }
        logger.info("[sc_mobile_fwd_sql]: SQL[{}] ", SecurityFliterUtils.whiteListSecurityCode(sqlParam.toString()));
        logger.info("[sc_mobile_fwd_sql]: SQL[{}] ", SecurityFliterUtils.whiteListSecurityCode(sqlStr));
        result.add(temp);
        return result;
    }
    
    /**
     * 获取SQL语句
     * @param sqlTemplate 模板文件名称
     * @param paramList SQL语句的参数列表
     * @return SQL语句
     */
    public static String getSql(String sqlTemplate,
            List<SqlTemplateParam> paramList)
    {
        if (null != paramList)
        {
            Collections.sort(paramList, Collections.reverseOrder());
            for (SqlTemplateParam param : paramList)
            {
                if (param != null)
                {
                    String key = param.getKey();
                    String currentkey = "'" + key + "'";
                    String replaceValue = key.replaceAll("@", ":");
                    sqlTemplate = sqlTemplate.replaceAll(currentkey, replaceValue);
                    sqlTemplate = sqlTemplate.replaceAll(key, replaceValue);
                }
            }
        }
        return sqlTemplate;
    }
    
    /**
     * 获取SQL语句
     * @param filePath 模板文件名称
     * @param templateName SQL语句的Key
     * @param params SQL语句的参数列表
     * @return SQL语句
     */
    public static List<Object> getSqlByMap(String filePath, String templateName,
            Map<String, String> params)
    {
        String sqlTemplate = WebCoreTools.getSQLProperties(filePath)
                .getSQL(templateName);
        String resultSql = getSqlByMap(sqlTemplate, params);
        List<Object> result = new ArrayList<Object>();
        result.add(resultSql);
        String sqlStr = resultSql;
        StringBuffer sqlParam = new StringBuffer();
        MapSqlParameterSource temp = new MapSqlParameterSource();
        if (null != params && !params.isEmpty())
        {
            Set<Entry<String, String>> keySet = params.entrySet();
            for (Entry<String, String> key : keySet)
            {
                String keyTemp = key.getKey().replace("@", "");
                sqlParam.append(keyTemp + "=" + key.getValue() + "||");
                sqlStr = sqlStr.replaceAll(":" + keyTemp, key.getValue());
                temp.addValue(keyTemp, key.getValue());
            }
        }
        logger.info("[sc_mobile_fwd_sql]: SQL[{}] ", SecurityFliterUtils.whiteListSecurityCode(sqlParam.toString()));
        logger.info("[sc_mobile_fwd_sql]: SQL[{}] ", SecurityFliterUtils.whiteListSecurityCode(sqlStr));
        result.add(temp);
        return result;
    }
    
    /**
     * 获取SQL语句
     * @param sqlTemplate 模板文件名称
     * @param params SQL语句的参数列表
     * @return SQL语句
     */
    public static String getSqlByMap(String sqlTemplate,
            Map<String, String> params)
    {
        
        if (null != params && !params.isEmpty())
        {
            Set<Entry<String, String>> keySet = params.entrySet();
            for (Entry<String, String> key : keySet)
            {
                String keyTemp = key.getKey();
                String currentkey = "'" + keyTemp + "'";
                String replaceValue = keyTemp.replaceAll("@", ":");
                sqlTemplate = sqlTemplate.replaceAll(currentkey, replaceValue);
                sqlTemplate = sqlTemplate.replaceAll(keyTemp, replaceValue);
            }
        }
        
        return sqlTemplate;
    }
    
    /** 
     * 获取SQL语句.
     * SQL中的占位符必须是单词的右边界：如 xxx@placeHolder, '@placeHolder'.
     * @param filePath filePath
     * @param templateName key in template file.
     * @param params SQL语句的参数列表
     * @return String sql
     * @author w00191004
     */
    public static List<Object> getSqlByMapWithRightWordBoundry(String filePath,
            String templateName, Map<String, String> params)
    {
        List<Object> resultValue = new ArrayList<Object>();
        String sqlTemplate = WebCoreTools.getSQLProperties(filePath)
                .getSQL(templateName);
        
        MapSqlParameterSource temp = new MapSqlParameterSource();
        
        // [begin] mod by gKF73638 for Fortify 20141208
        for (Map.Entry<String, String> param : params.entrySet())
        {
            String key = param.getKey();
            String currentkey = "'" + key + "'";
            String replaceValue = key.replaceAll("@", ":");
            String value = param.getValue();
            temp.addValue(key.replace("@", ""), value);
            sqlTemplate = sqlTemplate.replaceAll(currentkey, replaceValue);
            sqlTemplate = sqlTemplate.replaceAll(key, replaceValue);
        }
        // [end] mod by gKF73638 for Fortify 20141208
        
        resultValue.add(sqlTemplate);
        resultValue.add(temp);
        return resultValue;
    }
}
