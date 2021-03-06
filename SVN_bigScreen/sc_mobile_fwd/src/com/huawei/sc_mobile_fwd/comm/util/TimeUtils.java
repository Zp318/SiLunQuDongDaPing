package com.huawei.sc_mobile_fwd.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import com.huawei.sc_mobile_fwd.comm.SefonConstants;

/**
 * 获取时间的工具类
 * 
 * @author zhiyizhao
 */
public class TimeUtils
{
    /**
     * 时间粒度：毫秒
     */
    public static final long MILLISECOND = 1000;
    
    /**
     * 时间粒度：15分钟
     */
    public static final long INTERVAL_15MIN = 900;
    
    /**
     * 时间粒度：15分钟
     */
    public static final long INTERVAL_5MIN = 300;
    
    /**
     * 时间粒度：1小时
     */
    public static final long INTERVAL_HOUR = 3600;
    
    /**
     * 时间粒度：1天
     */
    public static final long INTERVAL_DAY = 86400;
    
    /**
     * 时区:需要除以100
     */
    public static final long TIME_ZONE = 100;
    
    /**
     * HOUR_MINUTES
     */
    private static final int HOUR_MINUTES = 60;
    
    /**
     * 当前系统所在时区的偏移量 中国时间-东八区的utc偏移量 8 * INTERVAL_HOUR <br/>
     * 如果取系统的默认时区的utc偏移量应该使用以下格式: TimeZone.getDefault().getRawOffset() / 1000
     */
    private static final long UTC_OFF_SET = 8 * INTERVAL_HOUR;
    
    /**
     * 将utc时间转化为时间字符串，格式为yyyy-MM-dd HH:mm:ss
     * 
     * @param utc utc时间
     * @return String UTC时间字符串
     */
    public static String utc2TimeString(long utc)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date(utc * MILLISECOND));
    }
    
    /**
     * 将时间字符串解析为utc时间
     * 
     * @param timeStr 将字符串格式的日期转换为UTC时间
     * @return long
     */
    public static long getUTC(String timeStr)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            return sdf.parse(timeStr).getTime() / MILLISECOND;
        }
        catch (ParseException e)
        {
            throw new RuntimeException("[TimeUtils] getUTC receives wrong param timeStr: " + timeStr);
        }
    }
    
    /**
     * 获取当前时间点的utc时间
     * 
     * @return long
     */
    public static long getCurrTimeUTC()
    {
        return System.currentTimeMillis() / MILLISECOND;
    }
    
    /**
     * 获取当天0:00时间点的utc时间
     * 
     * @return long
     */
    public static long getCurrDayBeginUTC()
    {
        return getDayBeginUTC(getCurrTimeUTC());
    }
    
    /**
     * 获取当前小时开始时间点的utc时间
     * 
     * @return long
     */
    public static long getCurrHourBeginUTC()
    {
        return getHourBeginUTC(getCurrTimeUTC());
    }
    
    /**
     * 获取当前15min段开始时间点的utc时间
     * 
     * @return long
     */
    public static long getCurr15minBeginUTC()
    {
        return get15minBeginUTC(getCurrTimeUTC());
    }
    
    /**
     * 获取当前5min段开始时间点的utc时间
     * 
     * @return long
     */
    public static long getCurr5minBeginUTC()
    {
        return get5minBeginUTC(getCurrTimeUTC());
    }
    
    /**
     * 获取传入时间当天0:00的时间点的utc时间
     * 
     * @param utcTime utc时间
     * @return long
     */
    public static long getDayBeginUTC(long utcTime)
    {
        return ((utcTime + UTC_OFF_SET) / INTERVAL_DAY) * INTERVAL_DAY - UTC_OFF_SET;
    }
    
    /**
     * 获取传入时间当前小时0分的时间点的utc时间
     * 
     * @param utcTime utc时间
     * @return long
     */
    public static long getHourBeginUTC(long utcTime)
    {
        return (utcTime / INTERVAL_HOUR) * INTERVAL_HOUR;
    }
    
    /**
     * 获取传入时间当前所处15分钟段的开始时间点的utc时间
     * 
     * @param utcTime utc时间
     * @return long
     */
    public static long get15minBeginUTC(long utcTime)
    {
        return (utcTime / INTERVAL_15MIN) * INTERVAL_15MIN;
    }
    
    /**
     * 获取传入时间当前所处5分钟段的开始时间点的utc时间
     * 
     * @param utcTime utc时间
     * @return long
     */
    public static long get5minBeginUTC(long utcTime)
    {
        return (utcTime / INTERVAL_5MIN) * INTERVAL_5MIN;
    }
    
    /**
     * 判断utc时间是否在同一天内
     * 
     * @param first first
     * @param utcTimes utcTimes
     * @return boolean utc时间是否在同一天内
     */
    public static boolean isInSameDay(long first, long... utcTimes)
    {
        long n = (first + UTC_OFF_SET) / INTERVAL_DAY;
        for (int i = 0; i < utcTimes.length; i++)
        {
            if (n != (utcTimes[i] + UTC_OFF_SET) / INTERVAL_DAY)
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 
     * 方法名: getEndTime 描述：获取查询的结束时间
     * 
     * @param date 需规整的时间
     * @param timeDim 粒度
     * @param dely 倒推时间段个数
     * @return long 结束时间
     *
     *         创建人: liuchang 创建日期: 2016年7月27日
     * 
     *         修改历史 修改日期：<创建日期，格式：YYYY-MM-DD> 修改人： <姓名/工号> 修改原因/修改内容: <修改原因描述> <问题单DTSXXXXXXXX>
     */
    public static long getEndTime(Date date, int timeDim, int dely)
    {
        long n = date.getTime() / SefonConstants.MILLI_OF_SECOND;
        // 按15分钟规整
        long n0 = n / SefonConstants.SECOND_OF_15MIN * SefonConstants.SECOND_OF_15MIN;
        // 再次规整
        if (timeDim == SefonConstants.SECOND_OF_DAY)
        {
            n0 = getDayBeginUTC(n0);
        }
        else
        {
            n0 = n0 / timeDim * timeDim;
        }
        // 规整后按粒度倒退dely个时间段
        n0 -= timeDim * dely;
        return n0;
    }
    
    /**
     * 
     * 方法名: getStartTime 描述：获取查询的开始时间
     * 
     * @param date 
     * @param timeDim 
     * @param dely 
     * @return long 
     *
     */
    public static long getStartTime(Date date, int timeDim, int dely)
    {
        long n = getEndTime(date, timeDim, dely);
        return n - timeDim;
    }
    
    /**
     * 获取后台服务器时区
     * @return 服务器时区
     */
    public static long getTimeZone()
    {
        Date date = new Date(getCurrTimeUTC());
        SimpleDateFormat dateFormat = new SimpleDateFormat("Z", Locale.ENGLISH);
        String timeZoneStr = dateFormat.format(date);
        return -(Long.parseLong(timeZoneStr) / TIME_ZONE) * HOUR_MINUTES;
    }
}
