package com.huawei.sc_mobile_fwd.comm.middleware;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.huawei.seq.SpringContextUtils;
import com.huawei.seq.tools.SecurityFliterUtils;
import com.huawei.sc_mobile_fwd.comm.middleware.constant.DataSourceEnum;
import com.huawei.sc_mobile_fwd.comm.middleware.constant.MiddlewareConst;

/**
 * 类名: ParamUtils
 * 描述: 参数处理工具
 */
public class ParamUtils
{
    /**
     * 日志器
     */
    private static Logger logger = LoggerFactory.getLogger(ParamUtils.class);
    
    /**
     * 默认每页记录数
     */
    private static final int DEFAULT_PAGE_SIZE = 20;
    
    /**
     * 默认起始行
     */
    private static final int DEFAULT_PAGE_START_INDEX = 0;
    
    /**
     * 百分比小数精度
     */
    private static final int PERCENT_FRACTION = 2;
    
    /**
     * 百分比初始值
     */
    private static final double PERCENT_DEFAULT = 0.0;

    /**
     * 数字：100
     */
    private static final double DIGIT_HUNDRED = 100.0;

    /** 
     * 方法名: transToString
     * 描述：将对象转换为字符串形式
     * @param obj 对象
     * @return String 字符串
     *
     */
    public static String transToString(Object obj)
    {
        String result = "";
        
        if (null != obj)
        {
            result = obj.toString();
        }
        
        return result;
    }
    
    /** 
     * 方法名: transToString
     * 描述：将对象转换为字符串集合形式
     * @param obj 对象
     * @return List<String> 字符串集合
     *
     */
    @SuppressWarnings("unchecked")
    public static List<String> transToStringList(Object obj)
    {
        if (null == obj)
        {
            return new ArrayList<String>();
        }
        
        return (List<String>)obj;
    }
    
    /** 
     * 方法名: transToMap
     * 描述：将对象转换为Map<String,Object>形式
     * @param obj 对象
     * @return Map<String,Object> 对象Map
     *
     */
    @SuppressWarnings("unchecked")
    public static Map<String, Object> transToMap(Object obj)
    {
        if (null == obj)
        {
            return new HashMap<String, Object>();
        }
        
        return (Map<String, Object>)obj;
    }
    
    /** 
     * 方法名: convertToStringMap
     * 描述：将Map<String,Object>转换为Map<String,String>形式
     * @param params 对象Map
     * @return Map<String,Object> 字符串Map
     *
     */
    public static Map<String, String> convertToStringMap(Map<String, Object> params)
    {
        Map<String, String> result = new HashMap<String, String>();
        
        if (CollectionUtils.isEmpty(params))
        {
            return result;
        }
        
        for (Map.Entry<String, Object> param : params.entrySet())
        {
            String key = param.getKey();
            Object value = param.getValue();
            result.put(key, transToString(value));
        }
        
        return result;
    }
    
    /** 
     * 方法名: transToList
     * 描述：将对象转换为List<Map<String, Object>>形式
     * @param obj 对象
     * @return List<Map<String,Object>> 对象Map集合
     *
     */
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> transToList(Object obj)
    {
        if (null == obj)
        {
            return new ArrayList<Map<String, Object>>();
        }
        
        return (List<Map<String, Object>>)obj;
    }
    
    /** 
     * 方法名: transToDouble
     * 描述：将指标转换为Double型
     * @param indicator 指标
     * @return Double Double数据
     *
     */
    public static Double transToDouble(Object indicator)
    {
        Double indicatorValue = 0.0d;
        
        if (null == indicator)
        {
            return indicatorValue;
        }
        
        try
        {
            indicatorValue = Double.valueOf(indicator.toString());
        }
        catch (NumberFormatException e)
        {
            logger.error("[sc_mobile_fwd] NumberFormatException! indicator = "
                + SecurityFliterUtils.loggerwhiteListSecurityCode(indicator.toString()));
        }
        
        return indicatorValue;
    }
    
    /** 
     * 方法名: transToLong
     * 描述：将指标转换为Long型
     * @param indicator 指标
     * @return Long Long型数据
     *
     */
    public static Long transToLong(Object indicator)
    {
        return transToDouble(indicator).longValue();
    }
    
    /** 
     * 方法名: transToInt
     * 描述：将指标转换为Integer型
     * @param indicator 指标
     * @return Integer Integer型数据
     *
     */
    public static Integer transToInt(Object indicator)
    {
        return transToDouble(indicator).intValue();
    }
    
    /** 
     * 方法名: getDataSource
     * 描述：获取数据库类型
     * @param dataSourceType 数据库类型参数
     * @return String 数据库类型
     *
     */
    public static String getDataSource(Object dataSourceType)
    {
        if (null == dataSourceType)
        {
            return DataSourceEnum.IQ.name();
        }
        
        if (DataSourceEnum.PT.name().equals(dataSourceType.toString()))
        {
            return dataSourceType.toString();
        }
        else
        {
            return DataSourceEnum.IQ.name();
        }
    }
    
    /** 
     * 方法名: getLocale
     * 描述：获取国际化语言
     * @return String 国际化语言
     *
     */
    public static String getLocale()
    {
        String locale = SpringContextUtils.getLanguage();
        if (StringUtils.isEmpty(locale))
        {
            return "en_US";
        }
        
        return locale;
    }
    
    /** 
     * 方法名: getStartIndexForPagination
     * 描述：获取分页开始行索引
     * @param startIndex 开始行索引
     * @return int 开始行索引
     *
     */
    public static int getStartIndexForPagination(String startIndex)
    {
        // 默认起始索引为第一条记录
        if (StringUtils.isEmpty(startIndex))
        {
            return 0;
        }
        
        try
        {
            return Integer.parseInt(startIndex);
        }
        catch (NumberFormatException e)
        {
            logger.error("[sc_mobile_fwd] NumberFormatException! startIndex = "
                + SecurityFliterUtils.loggerwhiteListSecurityCode(startIndex));
            
            return 0;
        }
    }
    
    /** 
     * 方法名: getLimitForPagination
     * 描述：获取每页的行数
     * @param limit 每页的行数
     * @return int 每页的行数
     *
     */
    public static int getLimitForPagination(String limit)
    {
        // 若limit为空，则每页行数默认为最大上限
        if (StringUtils.isEmpty(limit))
        {
            return MiddlewareConst.PAGINATION_MAX_LIMIT;
        }
        
        try
        {
            return Integer.parseInt(limit);
        }
        catch (NumberFormatException e)
        {
            logger.error("[sc_mobile_fwd] NumberFormatException! limit = "
                + SecurityFliterUtils.loggerwhiteListSecurityCode(limit));
            
            return 0;
        }
    }
    
    /** 
     * 方法名: isTranslate
     * 描述：是否翻译
     * @param isTranslate 是否翻译 
     * @return boolean 是否翻译
     *
     */
    public static boolean isTranslate(String isTranslate)
    {
        if (StringUtils.isEmpty(isTranslate))
        {
            return false;
        }
        return Boolean.TRUE.toString().equalsIgnoreCase(isTranslate) ? true : false;
    }
    
    /** 
     * 方法名: toList
     * 描述：将指定分隔符的字符串（如 a,b,c）转换为字符串List
     * @param str 字符串
     * @param seperator 分隔符
     * @return List<String> 字符串集合
     *
     */
    public static List<String> toList(String str, String seperator)
    {
        if (StringUtils.isEmpty(str))
        {
            return new ArrayList<String>();
        }
        
        String[] strArr = str.split(seperator);
        return Arrays.asList(strArr);
    }
    
    /** 
     * 方法名: getParamValues
     * 描述：获取参数值集合
     * @param value 参数值
     * @return List<String> 参数值集合
     *
     */
    public static List<String> getParamValues(String value)
    {
        List<String> values = new ArrayList<String>();
        
        if (null == value)
        {
            return values;
        }
        
        values.add(value);
        return values;
    }
    
    /** 
     * 方法名: getPageStartIndex
     * 描述：获取分页的起始行
     * @param pageNumber 页码
     * @param pageSize 每页记录数
     * @return int 起始行
     *
     */
    public static int getPageStartIndex(String pageNumber, String pageSize)
    {
        if (StringUtils.isEmpty(pageNumber))
        {
            return DEFAULT_PAGE_START_INDEX;
        }
        
        int pageNo = 0;
        int pageLimit = DEFAULT_PAGE_SIZE;
        
        try
        {
            pageNo = Integer.parseInt(pageNumber);
            
            if (!StringUtils.isEmpty(pageSize))
            {
                pageLimit = Integer.parseInt(pageSize);
            }
        }
        catch (NumberFormatException e)
        {
            logger.error("[sc_mobile_fwd] NumberFormatException! pageNumber = "
                + SecurityFliterUtils.loggerwhiteListSecurityCode(pageNumber));
            logger.error("[sc_mobile_fwd] NumberFormatException! pageSize = "
                + SecurityFliterUtils.loggerwhiteListSecurityCode(pageSize));
            return DEFAULT_PAGE_START_INDEX;
        }
        
        return (pageNo - 1) * pageLimit;
    }

    /** 
     * 方法名: getJsonByTemp
     * 描述：通过模板获取JSON串
     * @param jsonTemp JSON模板
     * @param params 参数
     * @return String JSON串
     *
     */
    public static String getJsonByTemp(String jsonTemp, Map<String, String> params)
    {
        logger.debug("[sc_mobile_fwd] jsonTemp: " + SecurityFliterUtils.loggerwhiteListSecurityCode(jsonTemp));
        for (Map.Entry<String, String> param : params.entrySet())
        {
            String key = param.getKey();
            String val = param.getValue();
            
            jsonTemp = jsonTemp.replaceAll("@" + key + "@", val);
        }
        logger.debug("[sc_mobile_fwd] json: " + SecurityFliterUtils.loggerwhiteListSecurityCode(jsonTemp));
        return jsonTemp;
    }
    
    /** 
     * 方法名: getFormatedRate
     * 描述：获取格式化的率值
     * @param numerator 分子
     * @param denominator 分母
     * @return String 率值
     *
     */
    public static String getFormatedRate(long numerator, long denominator)
    {
        return getFormatedRate(numerator, denominator, PERCENT_FRACTION);
    }
    
    /** 
     * 方法名: getFormatedRate
     * 描述：获取格式化的率值
     * @param numerator 分子
     * @param denominator 分母
     * @param maxFraction 小数精度
     * @return String 率值
     *
     */
    public static String getFormatedRate(long numerator, long denominator, int maxFraction)
    {
        if (denominator == 0)
        {
            return "" + PERCENT_DEFAULT;
        }
        
        double rate = DIGIT_HUNDRED * numerator / denominator;
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMaximumFractionDigits(maxFraction);
        return nf.format(rate);
    }
    
    /** 
     * 方法名: getCubesForSdrWithTimeSuffix
     * 描述：获取带时间粒度后缀的表名
     * @param tableName 表名
     * @param interval 时间粒度
     * @return List<String> 带时间粒度后缀的表名
     *
     */
    public static List<String> getCubesForSdrWithTimeSuffix(String tableName, String interval)
    {
        return ParamUtils.getParamValues(getTableNameWithTimeSuffix(tableName, interval));
    }
    
    /** 
     * 方法名: getTableNameWithTimeSuffix
     * 描述：获取带时间粒度后缀的表名
     * @param tableName 表名
     * @param interval 时间粒度
     * @return String 带时间粒度后缀的表名
     *
     */
    public static String getTableNameWithTimeSuffix(String tableName, String interval)
    {
        if (StringUtils.isEmpty(tableName) || StringUtils.isEmpty(interval))
        {
            return "";
        }
        return tableName + "_" + interval;
    }
}
