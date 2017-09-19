package com.huawei.sc_mobile_fwd.comm.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import com.huawei.sc_mobile_fwd.comm.logger.SeqLogger;
import com.huawei.seq.SpringContextUtils;

/**
 * 
 * 基础Dao实现类
 *
 */
public class BaseDao
{
    /**
     * 日志
     */
    private static final SeqLogger logger = new SeqLogger();

    /**
     * 默认sql模板文件名称
     */
    private static final String DEFAULT_TEMPLATE_NAME = "miipm_sql_template.properties";

    /**
     * 检索数据
     * @param fileName sql模板文件名称
     * @param templateName sql模板名称
     * @param paramsMap 参数
     * @return 检索的数据
     * @throws SCException
     */
    public List<Map<String, Object>> queryData(String fileName, String templateName, Map<String, Object> paramsMap)
    {
        logger.entryMethod();
        MapSqlParameterSource sqlParameter = new MapSqlParameterSource();
        if (paramsMap != null && paramsMap.size() > 0)
        {
            for (String key : paramsMap.keySet())
            {
                sqlParameter.addValue(key, paramsMap.get(key));
            }
        }
        // 获取 SQL语句的模板名
        List<Object> sqlList = SecurityBasicDao.getSql(fileName, templateName, null);
        List<Map<String, Object>> resultList = null;
        if (sqlParameter.getValues().size() > 0)
        {
            resultList = SpringContextUtils.getPTBasicDao().queryForList((String)sqlList.get(0), sqlParameter);
        }
        else
        {
            resultList = SpringContextUtils.getPTBasicDao().queryForList((String)sqlList.get(0));
        }
        
        logger.exitMethod();
        return resultList;
    }

    /**
     * 检索数据接口
     * @param templateName sql模板名称
     * @param paramsMap 参数
     * @return 检索的数据
     */
    public List<Map<String, Object>> queryData(String templateName, Map<String, Object> paramsMap)
    {
        return queryData(DEFAULT_TEMPLATE_NAME, templateName, paramsMap);
    }

    /**
     * 检索数据接口
     * @param templateName sql模板名称
     * @return 检索的数据
     */
    public List<Map<String, Object>> queryData(String templateName)
    {
        return queryData(templateName, null);
    }
}
