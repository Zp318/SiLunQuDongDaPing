package com.huawei.sc_mobile_fwd.comm.middleware;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.huawei.seq.tools.SecurityFliterUtils;
import com.huawei.smartcare.dac.sdk.QueryFactory;
import com.huawei.smartcare.dac.sdk.api.DataQuery;
import com.huawei.smartcare.dac.sdk.api.DimensionQuery;
import com.huawei.smartcare.dac.sdk.api.MultiDataQuery;
import com.huawei.smartcare.dac.sdk.bean.DataRequest;
import com.huawei.smartcare.dac.sdk.bean.DataResult;
import com.huawei.smartcare.dac.sdk.bean.Dimension;
import com.huawei.smartcare.dac.support.QueryException;

/**
 * 类名: MiddlewareService
 * 描述: 中间件服务
 */
public class MiddlewareService
{
    /**
     * 参数名称：是否复杂数据（UNION ALL）查询
     */
    private static final String PARAM_IS_MULTI_DATA_QUERY = "isMultiDataQuery";

    /**
     * 日志器
     */
    private static Logger logger = LoggerFactory.getLogger(MiddlewareService.class);
    
    /** 
     * 方法名: queryDimensionsById
     * 描述：通过维度标识查询维度信息
     * @param dimId 维度标识
     * @return List<Dimension> 维度数据
     *
     */
    public static List<Dimension> queryDimsById(String dimId)
    {
        DimensionQuery dq = QueryFactory.newDimensionQuery();
        dq.setId(dimId);
        return dq.query();
    }
    
    /** 
     * 方法名: queryDimById
     * 描述：通过维度标识查询单条维度信息
     * @param dimId 维度标识
     * @return Dimension 单条维度信息
     *
     */
    public static Dimension queryDimById(String dimId)
    {
        List<Dimension> dims = queryDimsById(dimId);
        
        if (CollectionUtils.isEmpty(dims) || null == dims.get(0))
        {
            return new Dimension();
        }
        
        return dims.get(0);
    }
    
    /** 
     * 方法名: queryByJson
     * 描述：通过JSON查询中间件数据
     * @param commandMap 请求参数
     * @param json JSON参数
     * @return List<Map<String,Object>> 查询结果
     */
    public static List<Map<String, Object>> queryByJson(Map<String, Object> commandMap, String json)
    {
        if (StringUtils.isEmpty(json))
        {
            return new ArrayList<Map<String, Object>>();
        }
        
        String isMultiDataQuery = (String) commandMap.get(PARAM_IS_MULTI_DATA_QUERY);
        
        // 使用union all的json串查询
        if (Boolean.TRUE.toString().equals(isMultiDataQuery))
        {
            return queryByMultiDataJson(json);
        }
        else
        {
            return queryBySimpleDataJson(json);
        }
    }
    
    /** 
     * 方法名: queryBySimpleDataJson
     * 描述：通过Json串使用中间件查询数据
     * @param json JSON参数
     * @return List<Map<String,Object>> 查询结果
     *
     */
    public static List<Map<String, Object>> queryBySimpleDataJson(String json)
    {
        return query(json, false);
    }
    
    /** 
     * 方法名: queryByMultiDataJson
     * 描述：通过Json串使用中间件查询数据
     * @param json JSON参数
     * @return List<Map<String,Object>> 查询结果
     *
     */
    public static List<Map<String, Object>> queryByMultiDataJson(String json)
    {
        return query(json, true);
    }
    
    /** 
     * 方法名: query
     * 描述：使用中间件查询
     * @param json JSON参数
     * @param isMultiData 是否使用UNION ALL查询
     * @return List<Map<String,Object>> 查询结果
     *
     */
    private static List<Map<String, Object>> query(String json, boolean isMultiData)
    {
        logger.info("[sc_mobile_fwd]: MiddlewareService.query json: " 
            + SecurityFliterUtils.loggerwhiteListSecurityCode(json));
        
        List<Map<String, Object>> emptyData = new ArrayList<Map<String, Object>>();
        
        if (StringUtils.isEmpty(json))
        {
            return emptyData;
        }
        
        DataQuery dataQuery = null;
        if (isMultiData)
        {
            dataQuery = QueryFactory.newQuery(json, MultiDataQuery.class);
        }
        else
        {
            dataQuery = QueryFactory.newQuery(json, DataQuery.class);
        }
        
        if (null == dataQuery)
        {
            return emptyData;
        }
        
        logger.info("[sc_mobile_fwd]: MiddlewareService.dataQuery.query() begin: ");
        DataResult dataResult = null;
        try
        {
            dataResult = dataQuery.query();
        }
        catch (QueryException e)
        {
            logger.error("[sc_mobile_fwd]: MiddlewareService.dataQuery.query() exception" 
                + SecurityFliterUtils.loggerwhiteListSecurityCode(e.toString()));
        }
        logger.info("[sc_mobile_fwd]: MiddlewareService.dataQuery.query() end: ");
        
        if (null == dataResult)
        {
            return emptyData;
        }
        
        return dataResult.getData();
    }
    
    /**
     * 
     * 方法名: query
     * 描述：根据json对象查询中间件
     * @param request json请求对象
     * @return List<Map<String,Object>> 查询结果
     */
    public static List<Map<String, Object>> query(DataRequest request)
    {
        DataQuery dataQuery = QueryFactory.newDataQuery();
        dataQuery.setRequest(request);
        DataResult dataResult = null;
        try
        {
            dataResult = dataQuery.query();
        }
        catch (QueryException e)
        {
            logger.error("[sc_mobile_fwd]:MiddlewareService.dataQuery.query() exception" 
                + SecurityFliterUtils.loggerwhiteListSecurityCode(e.toString()));
        }
        return dataResult == null ? new ArrayList<Map<String, Object>>() : dataResult.getData();
    }
}
