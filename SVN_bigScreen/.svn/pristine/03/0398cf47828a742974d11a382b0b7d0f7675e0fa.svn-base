package com.huawei.sc_mobile_fwd.comm.middleware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.huawei.sc_mobile_fwd.comm.middleware.constant.DataSourceEnum;
import com.huawei.sc_mobile_fwd.comm.middleware.constant.MiddlewareConst;
import com.huawei.seq.SpringContextUtils;
import com.huawei.smartcare.dac.bean.RelationOperator;
import com.huawei.smartcare.dac.sdk.bean.ActionType;
import com.huawei.smartcare.dac.sdk.bean.ColumnType;
import com.huawei.smartcare.dac.sdk.bean.Style;

/**
 * 类名: MiddlewareUtils
 * 描述: 中间件工具
 */
public class MiddlewareUtils
{
    /**
     * 字段名：STARTTIME（UTC开始时间）
     */
    private static final String COLUMN_NAME_STARTTIME_UTC = "STARTTIME";

    /**
     * 参数名称：开始时间
     */
    private static final String PARAM_STARTTIME = "startTime";
    
    /**
     * 参数名称：结束时间
     */
    private static final String PARAM_ENDTIME = "endTime";

    /**
     * 参数名称：开始时间
     */
    private static final String PARAM_STARTTIME2 = "starttime";
    
    /**
     * 参数名称：结束时间
     */
    private static final String PARAM_ENDTIME2 = "endtime";

    /** 
     * 方法名: getJsonForMultiDataRequestParams
     * 描述：获取多表（UNION ALL）数据请求参数的JSON串
     * @param params 数据请求参数
     * @return String 数据请求参数的JSON串
     */
    public static String getJsonForMultiDataRequestParams(Map<String, Object> params)
    {
        List<Map<String, Object>> columns = ParamUtils.transToList(params.get(MiddlewareConst.REQUEST_KEY_COLUMNS));
        List<Map<String, Object>> requests =
            ParamUtils.transToList(params.get(MiddlewareConst.MULTIDATA_REQUEST_KEY_REQUESTS));
        List<Map<String, Object>> orders = ParamUtils.transToList(params.get(MiddlewareConst.REQUEST_KEY_ORDERS));
        
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> requestData : requests)
        {
            String startIndex = ParamUtils.transToString(requestData.get(MiddlewareConst.REQUEST_KEY_START));
            boolean isPagination = StringUtils.isEmpty(startIndex);
            resultList.add(getDataRequestParamMap(requestData, isPagination));
        }
        
        Map<String, Object> resultMap = new TreeMap<String, Object>();
        if (!CollectionUtils.isEmpty(columns))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_COLUMNS, columns);
        }
        
        resultMap.put(MiddlewareConst.MULTIDATA_REQUEST_KEY_REQUESTS, resultList);
        
        if (!CollectionUtils.isEmpty(orders))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_ORDERS, orders);
        }
        
        resultMap.put(MiddlewareConst.REQUEST_KEY_ACTION, ActionType.QUERY.name());
            
        resultMap.put(MiddlewareConst.REQUEST_KEY_START,
            ParamUtils.getStartIndexForPagination(
                ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_START))));
        resultMap.put(MiddlewareConst.REQUEST_KEY_LIMIT,
            ParamUtils.getLimitForPagination(ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_LIMIT))));
        resultMap.put(MiddlewareConst.REQUEST_KEY_TRANSLATE,
            ParamUtils.isTranslate(ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_TRANSLATE))));
        resultMap.put(MiddlewareConst.REQUEST_KEY_LOCALE, ParamUtils.getLocale());
        
        JSONObject json = JSONObject.fromObject(resultMap);
        return json.toString();
    }
    
    /** 
     * 方法名: getJsonForDataRequestParams
     * 描述：获取数据请求参数的JSON串
     * @param paramsList 数据请求参数集合
     * @return String 数据请求参数的JSON串
     *
     */
    public static String getJsonForDataRequestParams(List<Map<String, Object>> paramsList)
    {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        
        for (Map<String, Object> params : paramsList)
        {
            resultList.add(getDataRequestParamMap(params, false));
        }
        
        JSONArray json = JSONArray.fromObject(resultList);
        return json.toString();
    }
    
    /** 
     * 方法名: getJsonForDataRequestParams
     * 描述：获取数据请求参数的JSON串
     * @param params 数据请求参数
     * @return String 数据请求参数的JSON串
     *
     */
    public static String getJsonForDataRequestParams(Map<String, Object> params)
    {
        JSONObject json = JSONObject.fromObject(getDataRequestParamMap(params, false));
        return json.toString();
    }
    
    /** 
     * 方法名: addCounter
     * 描述：向列集合中增加Counter
     * @param columnsList  列集合
     * @param counterId CounterID
     * @param alias Counter别名
     *
     */
    public static void addCounter(List<Map<String, Object>> columnsList, String counterId, String alias)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, counterId);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.COUNTER.name());
        params.put(MiddlewareConst.COMMON_KEY_ALIAS, alias);
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        
        columnsList.add(params);
    }
    
    /** 
     * 方法名: addConstCounter
     * 描述：向列集合中增加常量Counter
     * @param columnsList 列集合
     * @param constValue 常量值
     * @param alias Counter别名
     *
     */
    public static void addConstCounter(List<Map<String, Object>> columnsList, String constValue, String alias)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, constValue);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, "CONSTANT");
        params.put(MiddlewareConst.COMMON_KEY_ALIAS, alias);
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        columnsList.add(params);
    }
    
    /** 
     * 方法名: addDimension
     * 描述：向列集合中增加维度
     * @param columnsList 列集合
     * @param dimId 维度ID
     * @param alias 维度别名
     * @param isDimName 是否使用维度名称（true：使用维度名称，false：使用维度ID）
     *
     */
    public static void addDimension(List<Map<String, Object>> columnsList, 
        String dimId, String alias, boolean isDimName)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, dimId);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.DIMENSION.name());
        params.put(MiddlewareConst.COMMON_KEY_ALIAS, alias);
        params.put(MiddlewareConst.COMMON_KEY_STYLE, isDimName ? Style.NAME.name() : Style.IDENTIFIER.name());
        columnsList.add(params);
    }
    
    /** 
     * 方法名: addTimeDimension
     * 描述：向列集合中增加时间维度
     * @param columnsList 列集合
     * @param interval 时间粒度
     * @param alias 时间维度别名
     * @param isFormatTime 是否使用格式化的时间（true：使用格式化时间，false：使用UTC时间）
     *
     */
    public static void addTimeDimension(List<Map<String, Object>> columnsList, String interval, String alias,
        boolean isFormatTime)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, interval);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.TIME.name());
        params.put(MiddlewareConst.COMMON_KEY_ALIAS, alias);
        params.put(MiddlewareConst.COMMON_KEY_STYLE, isFormatTime ? Style.NAME.name() : Style.IDENTIFIER.name());
        columnsList.add(params);
    }
    
    /** 
     * 方法名: addOthersDimension
     * 描述：向列集合中增加时间维度
     * @param columnsList 列集合
     * @param interval 时间粒度
     *
     */
    public static void addOthersDimension(List<Map<String, Object>> columnsList, String interval)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, interval);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.OTHERS.name());
        params.put(MiddlewareConst.COMMON_KEY_ALIAS, interval);
        columnsList.add(params);
    }
    
    /** 
     * 方法名: addXdrCondition
     * 描述：向条件集合中增加过滤条件
     * @param conditionList 条件集合
     * @param id 过滤字段
     * @param values 过滤字段值
     * @param operator 过滤条件
     *
     */
    public static void addXdrCondition(List<Map<String, Object>> conditionList, String id, List<String> values,
        String operator)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, id);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_IGNORE_CASE, false);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_OPERATOR, operator);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.XDR.name());
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_VALUES, values);
        conditionList.add(params);
    }
    
    /** 
     * 方法名: addDimCondition
     * 描述：向条件集合中增加维度过滤条件
     * @param conditionList 条件集合
     * @param id 过滤字段
     * @param values 过滤字段值
     * @param operator 过滤条件
     *
     */
    public static void addDimCondition(List<Map<String, Object>> conditionList, String id, List<String> values,
        String operator)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, id);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_IGNORE_CASE, false);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_OPERATOR, operator);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.DIMENSION.name());
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_VALUES, values);
        conditionList.add(params);
    }
    
    /** 
     * 方法名: addTimeCondition
     * 描述：向条件集合中增加过滤条件
     * @param conditionList 条件集合
     * @param id 过滤字段
     * @param starttime 过滤字段值
     * @param isEndtime 是否结束时间（true：结束时间，false：开始时间）
     *
     */
    public static void addTimeCondition(List<Map<String, Object>> conditionList, String id, String starttime,
        boolean isEndtime)
    {
        List<String> starttimeValues = new ArrayList<String>();
        starttimeValues.add(starttime);
        
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, StringUtils.isEmpty(id) ? COLUMN_NAME_STARTTIME_UTC
            : id);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_IGNORE_CASE, false);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_OPERATOR, isEndtime ? RelationOperator.LT.name()
            : RelationOperator.GE.name());
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.TIME.name());
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_VALUES, starttimeValues);
        conditionList.add(params);
    }
    
    /** 
     * 方法名: getCondition
     * 描述：获取过滤条件
     * @param relation 关联方式
     * @param childs 关联条件
     * @return Map<String,Object> 过滤条件
     *
     */
    public static Map<String, Object> getCondition(String relation, List<Map<String, Object>> childs)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_RELATION, relation);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_CHILDS, childs);
        return params;
    }
    
    /** 
     * 方法名: addOrders
     * 描述：向排序集合中增加排序字段
     * @param ordersList 排序集合
     * @param id 排序字段
     * @param type 排序字段类型
     * @param style 按名称或ID排序
     * @param isDesc 是否降序（true：降序，false：升序）
     *
     */
    public static void addOrders(List<Map<String, Object>> ordersList, String id, String type, String style,
        boolean isDesc)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, id);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, type);
        params.put(MiddlewareConst.COMMON_KEY_STYLE, style);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_ORDERS_DESCENDING, isDesc);
        ordersList.add(params);
    }
    
    /** 
     * 方法名: addCounterCondition
     * 描述：向条件集合中增加Counter过滤条件
     * @param conditionList 条件集合
     * @param counterId CounterID
     * @param values 过滤字段值
     * @param operator 过滤条件
     *
     */
    public static void addCounterCondition(List<Map<String, Object>> conditionList, String counterId,
        List<String> values, String operator)
    {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(MiddlewareConst.COMMON_KEY_ID, counterId);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_IGNORE_CASE, false);
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_OPERATOR, operator);
        params.put(MiddlewareConst.COMMON_KEY_TYPE, ColumnType.COUNTER.name());
        params.put(MiddlewareConst.COMMON_KEY_STYLE, Style.IDENTIFIER.name());
        params.put(MiddlewareConst.REQUEST_KEY_CONDITIONS_VALUES, values);
        conditionList.add(params);
    }
    
    /** 
     * 方法名: addTimeConditionChilds
     * 描述：向条件集合中增加Counter过滤条件
     * @param conditionList 条件集合
     * @param commandMap 请求参数
     * @param interval 时间粒度
     *
     */
    public static void addTimeConditionChilds(List<Map<String, Object>> conditionList,
            Map<String, String> commandMap, String interval)
    {
        String starttime = ParamUtils.transToString(commandMap.get(PARAM_STARTTIME));
        if (StringUtils.isEmpty(starttime))
        {
            starttime = ParamUtils.transToString(commandMap.get(PARAM_STARTTIME2));
        }
        
        String endtime = ParamUtils.transToString(commandMap.get(PARAM_ENDTIME));
        if (StringUtils.isEmpty(endtime))
        {
            endtime = ParamUtils.transToString(commandMap.get(PARAM_ENDTIME2));
        }
        
        MiddlewareUtils.addTimeCondition(conditionList, interval, starttime, false);
        MiddlewareUtils.addTimeCondition(conditionList, interval, endtime, true);
    }
    
    /** 
     * 方法名: getSwitchStatusForXdrQueryByMdx
     * 描述：详单通过中间件查询的开关是否为打开状态
     * @return boolean true：开关打开；false：开关关闭
     *
     */
    public static boolean isSwitchEnabledForXdrQueryByMdx()
    {
        return MiddlewareConst.SWITCH_STATUS_ENABLED 
            == SpringContextUtils.geSwitchStatusBySwitchId(MiddlewareConst.SWITCHID_FOR_XDR_QUERY_BY_MDX);
    }
    
    /** 
     * 方法名: getRequestColumns
     * 描述：获取请求的列集合
     * @param params 查询参数
     * @return List<Map<String,Object>> 列集合
     *
     */
    private static List<Map<String, Object>> getRequestColumns(Map<String, Object> params)
    {
        return ParamUtils.transToList(params.get(MiddlewareConst.REQUEST_KEY_COLUMNS));
    }
    
    /** 
     * 方法名: getRequestConditions
     * 描述：获取请求的查询条件
     * @param params 查询参数
     * @return Map<String,Object> 查询条件
     *
     */
    private static Map<String, Object> getRequestConditions(Map<String, Object> params)
    {
        return ParamUtils.transToMap(params.get(MiddlewareConst.REQUEST_KEY_CONDITIONS));
    }
    
    /** 
     * 方法名: getRequestOrders
     * 描述：获取请求的排序方式
     * @param params 查询参数
     * @return List<Map<String,Object>> 排序方式
     *
     */
    private static List<Map<String, Object>> getRequestOrders(Map<String, Object> params)
    {
        return ParamUtils.transToList(params.get(MiddlewareConst.REQUEST_KEY_ORDERS));
    }
    
    /** 
     * 方法名: getDataRequestParamMap
     * 描述：获取格式化的数据请求参数Map
     * @param params 查询参数
     * @param isPagination 是否处理分页参数
     * @return Map<String,Object> 格式化的数据请求参数Map
     *
     */
    private static Map<String, Object> getDataRequestParamMap(Map<String, Object> params, boolean isPagination)
    {
        String sql = ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_SQL));
        String mdx = ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_MDX));
        List<Map<String, Object>> orders = getRequestOrders(params);
        
        Map<String, Object> resultMap = new TreeMap<String, Object>();
        if (!StringUtils.isEmpty(sql))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_SQL, sql);
        }
        if (!StringUtils.isEmpty(mdx))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_MDX, mdx);
        }
        if (!CollectionUtils.isEmpty(orders))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_ORDERS, orders);
        }
        
        resultMap.put(MiddlewareConst.REQUEST_KEY_COLUMNS, getRequestColumns(params));
        resultMap.put(MiddlewareConst.REQUEST_KEY_CONDITIONS, getRequestConditions(params));

        //中间件接口规定，查询动态sdr需要datasource=IQ_SDR,action=AGGRE_SDR
        if (params.containsKey("spectrumFlag") 
                && "1".equals(params.get("spectrumFlag")))
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_ACTION, ActionType.AGGRE_SDR.name());
            resultMap.put(MiddlewareConst.REQUEST_KEY_DATASOURCE, DataSourceEnum.IQ_SDR.name());
        }
        else
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_ACTION, ActionType.QUERY.name());
        } 
        resultMap.put(MiddlewareConst.REQUEST_KEY_CUBES,
            ParamUtils.transToStringList(params.get(MiddlewareConst.REQUEST_KEY_CUBES)));
        
        if (!isPagination)
        {
            resultMap.put(MiddlewareConst.REQUEST_KEY_START,
                ParamUtils.getStartIndexForPagination(
                    ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_START))));
            resultMap.put(MiddlewareConst.REQUEST_KEY_LIMIT,
                ParamUtils.getLimitForPagination(
                    ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_LIMIT))));
            resultMap.put(MiddlewareConst.REQUEST_KEY_TRANSLATE,
                ParamUtils.isTranslate(ParamUtils.transToString(params.get(MiddlewareConst.REQUEST_KEY_TRANSLATE))));
        }
        
        return resultMap;
    }
    
}
