package com.huawei.sc_mobile_fwd.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.tools.DateUtils;
import com.huawei.seq.tools.FlexDataCommand;

/**
 * 类名: DatetimeUtils
 * 描述: 日期时间工具类
 */
public class DatetimeUtils
{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DatetimeUtils.class);
    
    /**
     * 时间格式字符串：YYYY-MM-DD HH:mm
     */
    private static final String DATE_MINUTE_PARTTERN = "yyyy-MM-dd HH:mm";
    
    /**
     * 开始时间（YMD格式）默认参数名称
     */
    private static final String STARTTIME_DEFAULT_NAME = "starttimeFmt";
    
    /**
     * 结束时间（YMD格式）默认参数名称
     */
    private static final String ENDTIME_DEFAULT_NAME = "endtimeFmt";
    
    /**
     * 开始时间（UTC格式）默认参数名称
     */
    private static final String STARTTIME_UTC_DEFAULT_NAME = "startTime";
    
    /**
     * 结束时间（UTC格式）默认参数名称
     */
    private static final String ENDTIME_UTC_DEFAULT_NAME = "endTime";
    
    /**
     * 开始日期默认参数名称（用于数据库表后缀）
     */
    private static final String START_DAY_DEFAULT_NAME = "startDay";
    
    /**
     * 结束日期默认参数名称（用于数据库表后缀）
     */
    private static final String END_DAY_DEFAULT_NAME = "endDay";
    
    /**
     * 默认的夏令时偏移量
     */
    private static final long DEFAULT_DST_OFFSET = 3600L;
    
    /**
     * 每秒毫秒数
     */
    private static final int MILLIS_PER_SECOND = 1000;
    
    /** 
     * 方法名: handleUtc2DstUtc
     * 描述：将UTC时间处理为增加夏令时偏移量的UTC，并存回Map中
     * @param commandMap 请求参数（其中时间参数为UTC形式）
     *
     */
    public static void handleUtc2DstUtc(Map<String, String> commandMap)
    {
        String starttimeUtc = commandMap.get(STARTTIME_UTC_DEFAULT_NAME);
        String endtimeUtc = commandMap.get(ENDTIME_UTC_DEFAULT_NAME);
        
        if (StringUtils.isNotBlank(starttimeUtc))
        {
            
            String starttime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(starttimeUtc);
            String startday = "" + DateUtils.getDstDayTableSuffixByUtc(starttime);
            
            commandMap.put(STARTTIME_UTC_DEFAULT_NAME, starttime);
            commandMap.put(START_DAY_DEFAULT_NAME, startday);
        }
        
        if (StringUtils.isNotBlank(endtimeUtc))
        {
            String endtime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(endtimeUtc);
            String endday = "" + DateUtils.getDstDayTableSuffixByUtc(endtime);
            commandMap.put(ENDTIME_UTC_DEFAULT_NAME, endtime);
            commandMap.put(END_DAY_DEFAULT_NAME, endday);
        }
    }
    
    /** 
     * 方法名: handleUtc2DstUtc
     * 描述：将UTC时间处理为增加夏令时偏移量的UTC，并存回Map中
     * @param commandMap 请求参数（其中时间参数为UTC形式）
     * @param dt 日期时间名称参数
     *
     */
    public static void handleUtc2DstUtc(Map<String, String> commandMap, Datetime dt)
    {
        String startTimeName =
            StringUtils.isBlank(dt.getStartTimeName()) ? STARTTIME_UTC_DEFAULT_NAME : dt.getStartTimeName();
        String endTimeName = StringUtils.isBlank(dt.getEndTimeName()) ? ENDTIME_UTC_DEFAULT_NAME : dt.getEndTimeName();
        
        String startDayName = StringUtils.isBlank(dt.getStartDayName()) ? START_DAY_DEFAULT_NAME : dt.getStartDayName();
        String endDayName = StringUtils.isBlank(dt.getEndDayName()) ? END_DAY_DEFAULT_NAME : dt.getEndDayName();
        
        String starttimeUtc = commandMap.get(startTimeName);
        String endtimeUtc = commandMap.get(endTimeName);
        
        if (StringUtils.isNotBlank(starttimeUtc))
        {
            String starttime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(starttimeUtc);
            String startday = "" + DateUtils.getDstDayTableSuffixByUtc(starttime);
            
            commandMap.put(startTimeName, starttime);
            commandMap.put(startDayName, startday);
        }
        
        if (StringUtils.isNotBlank(endtimeUtc))
        {
            String endtime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(endtimeUtc);
            String endday = "" + DateUtils.getDstDayTableSuffixByUtc(endtime);
            commandMap.put(endTimeName, endtime);
            commandMap.put(endDayName, endday);
        }
    }
    
    /** 
     * 方法名: handleDstUtc2Utc
     * 描述：将带夏令时偏移量的UTC时间处理为不带偏移量的UTC，并存回Map中
     * @param commandMap 请求参数（其中时间参数为带夏令时偏移量的UTC时间）
     * @param dt 日期时间名称参数
     *
     */
    public static void handleDstUtc2Utc(Map<String, String> commandMap, Datetime dt)
    {
        String startTimeName =
            StringUtils.isBlank(dt.getStartTimeName()) ? STARTTIME_UTC_DEFAULT_NAME : dt.getStartTimeName();
        String endTimeName = StringUtils.isBlank(dt.getEndTimeName()) ? ENDTIME_UTC_DEFAULT_NAME : dt.getEndTimeName();
        
        String startDayName = StringUtils.isBlank(dt.getStartDayName()) ? START_DAY_DEFAULT_NAME : dt.getStartDayName();
        String endDayName = StringUtils.isBlank(dt.getEndDayName()) ? END_DAY_DEFAULT_NAME : dt.getEndDayName();
        
        String starttimeUtc = commandMap.get(startTimeName);
        String endtimeUtc = commandMap.get(endTimeName);
        
        if (StringUtils.isNotBlank(starttimeUtc))
        {
            String starttime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(starttimeUtc);
            String startday = "" + DateUtils.getDstDayTableSuffixByUtc(starttime);
            
            commandMap.put(startTimeName, starttime);
            commandMap.put(startDayName, startday);
        }
        
        if (StringUtils.isNotBlank(endtimeUtc))
        {
            String endtime = DateUtils.getUtcDstTimeStringFormatByZeroUtc(endtimeUtc);
            String endday = "" + DateUtils.getDstDayTableSuffixByUtc(endtime);
            commandMap.put(endTimeName, endtime);
            commandMap.put(endDayName, endday);
        }
    }
    
    /** 
     * 方法名: handleYmd2DstUtc
     * 描述：将本地时间处理为UTC时间，并存回Map中
     * @param commandMap 请求参数（其中时间参数为本地时间）
     *
     */
    public static void handleYmd2DstUtc(Map<String, String> commandMap)
    {
        String starttimeYmd = commandMap.get(STARTTIME_DEFAULT_NAME);
        String endtimeYmd = commandMap.get(ENDTIME_DEFAULT_NAME);
        
        if (StringUtils.isNotBlank(starttimeYmd))
        {
            String starttime = DateUtils.getUtcStringFormatByLocaleDate(starttimeYmd);
            String startday = "" + DateUtils.getDstDayTableSuffixByUtc(starttime);
            
            commandMap.put(STARTTIME_UTC_DEFAULT_NAME, starttime);
            commandMap.put(START_DAY_DEFAULT_NAME, startday);
        }
        
        if (StringUtils.isNotBlank(endtimeYmd))
        {
            String endtime = DateUtils.getUtcStringFormatByLocaleDate(endtimeYmd);
            String endday = "" + DateUtils.getDstDayTableSuffixByUtc(endtime);
            commandMap.put(ENDTIME_UTC_DEFAULT_NAME, endtime);
            commandMap.put(END_DAY_DEFAULT_NAME, endday);
        }
    }
    
    /** 
     * 方法名: handleYmd2DstUtc
     * 描述：将本地时间处理为UTC时间，并存回Map中
     * @param commandMap 请求参数（其中时间参数为本地时间）
     * @param dt 日期时间名称参数
     *
     */
    public static void handleYmd2DstUtc(Map<String, String> commandMap, Datetime dt)
    {
        String startTimeName =
            StringUtils.isBlank(dt.getStartTimeName()) ? STARTTIME_DEFAULT_NAME : dt.getStartTimeName();
        String endTimeName = StringUtils.isBlank(dt.getEndTimeName()) ? ENDTIME_DEFAULT_NAME : dt.getEndTimeName();
        
        String startTimeUtcName =
            StringUtils.isBlank(dt.getStartTimeUtcName()) ? STARTTIME_UTC_DEFAULT_NAME : dt.getStartTimeUtcName();
        String endTimeUtcName =
            StringUtils.isBlank(dt.getEndTimeUtcName()) ? ENDTIME_UTC_DEFAULT_NAME : dt.getEndTimeUtcName();
        
        String startDayName = StringUtils.isBlank(dt.getStartDayName()) ? START_DAY_DEFAULT_NAME : dt.getStartDayName();
        String endDayName = StringUtils.isBlank(dt.getEndDayName()) ? END_DAY_DEFAULT_NAME : dt.getEndDayName();
        
        String starttimeYmd = commandMap.get(startTimeName);
        String endtimeYmd = commandMap.get(endTimeName);
        
        if (StringUtils.isNotBlank(starttimeYmd))
        {
            String starttime = DateUtils.getUtcStringFormatByLocaleDate(starttimeYmd);
            String startday = "" + DateUtils.getDstDayTableSuffixByUtc(starttime);
            
            commandMap.put(startTimeUtcName, starttime);
            commandMap.put(startDayName, startday);
        }
        
        if (StringUtils.isNotBlank(endtimeYmd))
        {
            String endtime = DateUtils.getUtcStringFormatByLocaleDate(endtimeYmd);
            String endday = "" + DateUtils.getDstDayTableSuffixByUtc(endtime);
            commandMap.put(endTimeUtcName, endtime);
            commandMap.put(endDayName, endday);
        }
    }
    
    /** 
     * 方法名: handleUtc2DstUtc
     * 描述：将UTC时间处理为增加夏令时偏移量的UTC，并存回Map中
     * @param command 请求参数（其中时间参数为UTC形式）
     *
     */
    public static void handleUtc2DstUtc(FlexDataCommand command)
    {
        Map<String, String> commandMap = command.getCommandmap();
        handleUtc2DstUtc(commandMap);
        command.setCommandmap(commandMap);
    }
    
    /** 
     * 方法名: handleUtc2DstUtc
     * 描述：将UTC时间处理为增加夏令时偏移量的UTC，并存回Map中
     * @param command 请求参数（其中时间参数为UTC形式）
     * @param dt 日期时间名称参数
     *
     */
    public static void handleUtc2DstUtc(FlexDataCommand command, Datetime dt)
    {
        Map<String, String> commandMap = command.getCommandmap();
        handleUtc2DstUtc(commandMap, dt);
        command.setCommandmap(commandMap);
    }
    
    /** 
     * 方法名: handleYmd2DstUtc
     * 描述：将本地时间处理为UTC时间，并存回Map中
     * @param command 请求参数（其中时间参数为本地时间）
     *
     */
    public static void handleYmd2DstUtc(FlexDataCommand command)
    {
        Map<String, String> commandMap = command.getCommandmap();
        handleYmd2DstUtc(commandMap);
        command.setCommandmap(commandMap);
    }
    
    /** 
     * 方法名: handleYmd2DstUtc
     * 描述：将本地时间处理为UTC时间，并存回Map中
     * @param command 请求参数（其中时间参数为本地时间）
     * @param dt 日期时间名称参数
     *
     */
    public static void handleYmd2DstUtc(FlexDataCommand command, Datetime dt)
    {
        Map<String, String> commandMap = command.getCommandmap();
        handleYmd2DstUtc(commandMap, dt);
        command.setCommandmap(commandMap);
    }
    
    /** 
     * 方法名: getYmdDstDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量）
     * @param utcStr UTC时间
     * @param dateFormat 日期时间格式
     * @return String 本地时间格式字符串
     *
     */
    public static String getYmdDstDate(String utcStr, String dateFormat)
    {
        if (StringUtils.isBlank(utcStr))
        {
            return "";
        }
        
        if (StringUtils.isBlank(dateFormat))
        {
            dateFormat = DATE_MINUTE_PARTTERN;
        }
        
        return DateUtils.getLocaleDateStringFormatByUtc(utcStr, dateFormat);
    }
    
    /** 
     * 方法名: getYmdDstDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量）
     * @param utcStr UTC时间
     * @return String 本地时间格式字符串
     *
     */
    public static String getYmdDstDate(String utcStr)
    {
        if (StringUtils.isBlank(utcStr))
        {
            return "";
        }
        
        return DateUtils.getLocaleDateStringFormatByUtc(utcStr, DATE_MINUTE_PARTTERN);
    }
    
    /** 
     * 方法名: getYmdDstDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量）
     * @param utc UTC时间
     * @param dateFormat 日期时间格式
     * @return String 本地时间格式字符串
     *
     */
    public static String getYmdDstDate(long utc, String dateFormat)
    {
        return getYmdDstDate(String.valueOf(utc), dateFormat);
    }
    
    /** 
     * 方法名: getYmdDstDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量）
     * @param utc UTC时间
     * @return String 本地时间格式字符串
     *
     */
    public static String getYmdDstDate(long utc)
    {
        return getYmdDstDate(String.valueOf(utc));
    }
    
    /** 
     * 方法名: getLocalYmdDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量，不包含(DST)字符串）
     * @param utc UTC时间
     * @param dateFormat 日期时间格式
     * @return String 本地时间格式字符串
     *
     */
    public static String getLocalYmdDate(long utc, String dateFormat)
    {
        if (StringUtils.isBlank(dateFormat))
        {
            dateFormat = DATE_MINUTE_PARTTERN;
        }
        
        String ymdDst = getYmdDstDate(utc, dateFormat);
        
        if (ymdDst.length() > dateFormat.length())
        {
            return ymdDst.substring(0, dateFormat.length());
        }
        
        return ymdDst;
    }
    
    /** 
     * 方法名: getLocalYmdDate
     * 描述：取得本地时间格式字符串（包括夏令时偏移量）
     * @param utc UTC时间
     * @return String 本地时间格式字符串
     *
     * 创建人: gKF73638
     * 创建日期: 2014-2-25
     * 
     * 修改历史
     * 修改日期：<创建日期，格式：YYYY-MM-DD>
     * 修改人： <姓名/工号>
     * 修改原因/修改内容:  <修改原因描述> <问题单DTSXXXXXXXX>
     */
    public static String getLocalYmdDate(long utc)
    {
        return getLocalYmdDate(utc, DATE_MINUTE_PARTTERN);
    }
    
    /** 
     * 方法名: handleDstUtc2Utc
     * 描述：将带夏令时的UTC时间处理为本地时间对应的UTC，并存回Map中
     * @param commandMap 请求参数（其中时间参数为UTC时间）
     * @param dt 本地时间对应的UTC时间
     *
     */
    public static void handleDstUtc2Utc(Map<String, String> commandMap, String dt)
    {
        if (StringUtils.isBlank(dt))
        {
            return;
        }
        
        String dst = commandMap.get(dt);
        
        long dstUtc = 0L;
        if (StringUtils.isNotBlank(dst))
        {
            try
            {
                dstUtc = Long.parseLong(dst);
            }
            catch (NumberFormatException e)
            {
                logger.error("[sc_mobile_fwd]: {} is not a number!", dst);
            }
        }
        
        String dtUtc = "" + (dstUtc + DEFAULT_DST_OFFSET);
        commandMap.put(dt, dtUtc);
    }
    
    /** 
     * 方法名: getUtcByLocalDate
     * 描述：通过格式化的本地时间获取UTC
     * @param localDate YMD本地时间
     * @param format 时间格式字符串
     * @return String UTC时间
     */
    public static String getUtcByLocalDate(String localDate, String format)
    {
        if (StringUtils.isBlank(localDate))
        {
            logger.error("[sc_mobile_fwd]: Illegal argument(localDate): {}", localDate);
            return "";
        }
        
        String fmt = StringUtils.isBlank(format) ? DATE_MINUTE_PARTTERN : format;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        
        Long utc = 0L;
        try
        {
            Date date = sdf.parse(localDate);
            cal.setTime(date);
            utc = cal.getTimeInMillis() / MILLIS_PER_SECOND;
            if (cal.getTimeZone().inDaylightTime(new Date(utc * MILLIS_PER_SECOND)))
            {
                utc = (utc * MILLIS_PER_SECOND + cal.get(Calendar.DST_OFFSET)) / MILLIS_PER_SECOND;
            }
        }
        catch (ParseException e)
        {
            logger.error("[sc_mobile_fwd]: {} is not date format!", localDate);
        }
        
        return utc.toString();
    }
    
    /** 
     * 方法名: getUtcByLocalDate
     * 描述：通过格式化的本地时间获取UTC
     * @param localDate YMD本地时间
     * @return String UTC时间
     */
    public static String getUtcByLocalDate(String localDate)
    {
        return getUtcByLocalDate(localDate, DATE_MINUTE_PARTTERN);
    }
    
}
