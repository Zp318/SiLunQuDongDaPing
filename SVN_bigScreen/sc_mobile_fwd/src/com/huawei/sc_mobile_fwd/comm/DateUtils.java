package com.huawei.sc_mobile_fwd.comm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 对日期的操作
 * @version  [版本号, 2011-1-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DateUtils
{
    /**
     * 常量
     */
    public static final int SEC_ONE_HOUR = 60 * 60;
    
    /**
     * 常量
     */
    public static final int TWO = 2;
    
    /**
     * 常量
     */
    public static final int HOUR = 3600;
    
    /**
     * 常量
     */
    public static final double MINUTE = 60;
    
    /**
     * 常量
     */
    public static final long VALUE1000 = 1000;
    
    /** 
     * 静态常量 
     */
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 时间秒定义
     */
    public static final String C_TIME_PATTON = "yyyy/MM/dd HH:mm:ss";
    
    /**
     * 时间天定义
     */
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    
    /**
     * 以/分割的天数
     */
    public static final String C_DATE_PATTON = "yyyy/MM/dd";
    
    /**
     * 年的格式
     */
    public static final String C_YEAR_PATTON_DEFAULT = "yyyy";
    
    /**
     * 月
     */
    public static final String C_MONTH_PATTON_DEFAULT = "MM";
    
    /**
     * 日
     */
    public static final String C_DAY_PATTON_DEFAULT = "dd";
    
    /**
     * 默认 时分秒
     */
    public static final String C_SUFFIX_PATTON_DEFAULT = " 00:00:00";
    
    /**
     * 提示参数不能够为空
     */
    public static final String C_PARAM_IS_NOT_NULL = "参数不能为空";
    
    /**
     * 空串分隔符
     */
    public static final String C_EMPTY_STR = "";
    
    /**
     * 一天中的毫秒数
     */
    private static final int ONE_DAY_MILSEC = 86400000;
    
    /**
     * 一千 用于从毫秒数转换为秒数的被除数
     */
    private static final int THOUSAND = 1000;
    
    /**
     * 一个小时的毫秒数
     */
    private static final int ONE_HOUR_MILSEC = 3600000;
    
    /**
     * 日期格式转化 类型集合
     */
    private static List<String> list = new ArrayList<String>();
    
    //定义日期的转换格式
    static
    {
        list.add("yyyy-MM-dd HH:mm:ss");
        list.add("yyyy-MM-dd HH:mm");
        list.add("yyyy-MM-dd HH");
        list.add("yyyy-MM-dd");
        list.add("yyyyMMdd");
        list.add("yyyyMMddHH");
        list.add("yyyyMMddHHmm");
        list.add("yyyyMMddHHmmss");
    }
    
    /**
     * logger
     */
    private static final Logger logger = LoggerFactory.getLogger(DateUtils.class);
    
    /**
      * 判断一个日期是否在开始日期和结束日期之间。
      * 
      * @param srcDate
      *            目标日期 yyyy/MM/dd 或者 yyyy-MM-dd
      * @param startDate
      *            开始日期 yyyy/MM/dd 或者 yyyy-MM-dd
      * @param endDate
      *            结束日期 yyyy/MM/dd 或者 yyyy-MM-dd
      * @return 大于等于开始日期小于等于结束日期，那么返回true，否则返回false
      */
    public static boolean isInStartEnd(String srcDate, String startDate,
            String endDate)
    {
        if ((null != srcDate) && !C_EMPTY_STR.equals(srcDate.trim())
                && (null != startDate && !C_EMPTY_STR.equals(startDate.trim()))
                && (null != endDate) && !C_EMPTY_STR.equals(endDate.trim()))
        {
            if ((startDate.compareTo(srcDate) <= 0)
                    && (endDate.compareTo(srcDate) >= 0))
            {
                return true;
            }
        }
        
        return false;
    }
    
    /**
     * 格式化日期显示格式
     * 
     * @param sdate
     *            原始日期格式  表示 "yyyy-MM-dd" 形式的日期的 String 对象
     * @param format
     *            格式化后日期格式  如：yyyyMMdd
     * @return 格式化后的日期显示
     *    "" ：参数为空时返回的默认值
     */
    public static String dateFormat(String sdate, String format)
    {
        if (StringUtils.isBlank(sdate) || StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        String dateString = null;
        
        Date date = formatToDate(sdate);
        
        SimpleDateFormat formatter = new SimpleDateFormat();
        
        formatter.applyPattern(format);
        
        dateString = formatter.format(date);
        
        return dateString;
        
    }
    
    /**
     * 将字符串转换为日期
     * @param datetime 时间
     * @return Date
     * @see [类、类#方法、类#成员]
     */
    public static Date formatToDate(String datetime)
    {
        if (datetime == null || "".equals(datetime))
        {
            return new Date();
        }
        datetime = datetime.replaceAll("/", "-");
        
        Date date = null;
        
        SimpleDateFormat formatter = new SimpleDateFormat();
        
        //匹配日期的多种格式 所以循环中嵌套了异常捕获
        for (String pattern : list)
        {
            try
            {
                formatter.applyPattern(pattern);
                
                date = formatter.parse(datetime);
                
                break;
            }
            catch (ParseException e)
            {
                //在时间转换过程中 出现异常 不作为系统错误
                logger.info("[sc_mobile_fwd]: Date Formatter Parse error!");
            }
        }

        return (null == date) ? new Date() : date;
    }
    
    /**
     * 根据给定的格式，返回时间字符串。
     * <p>
     * 格式参照类描绘中说明.和方法getFormatDate是一样的。
     * 
     * @param format
     *            日期格式字符串,如："yyyy/MM/dd hh/mm/ss"
     * @return String 指定格式的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatCurrentTime(String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatDateTime(new Date(), format.trim());
    }
    
    /**
     * 根据给定的格式与时间(Date类型的)，返回时间字符串。最为通用。<br>
     * 
     * @param date
     *            指定的日期,如：new Date()
     * @param format
     *            日期格式字符串,如："yyyy/MM/dd hh/mm/ss"
     * @return String 指定格式的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatDateTime(Date date, String format)
    {
        if ((null == date) || StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        
        return sdf.format(date);
    }
    
    /**
     * 取得当前日期的年份，以yyyy格式返回.
     * 
     * @return 当年 yyyy
     */
    public static String getCurrentYear()
    {
        return getFormatCurrentTime(C_YEAR_PATTON_DEFAULT);
    }
    
    /**
     * 取得当前日期的月份，以MM格式返回.
     * 
     * @return 当前月份 MM
     */
    public static String getCurrentMonth()
    {
        return getFormatCurrentTime(C_MONTH_PATTON_DEFAULT);
    }
    
    /**
     * 取得当前日期的天数，以格式"dd"返回.
     * 
     * @return 当前月中的某天dd
     */
    public static String getCurrentDay()
    {
        return getFormatCurrentTime(C_DAY_PATTON_DEFAULT);
    }
    
    /**
     * 返回当前时间字符串。
     * <p>
     * 格式：yyyy-MM-dd
     * 
     * @return String 指定格式的日期字符串.
     */
    public static String getCurrentDate()
    {
        return getFormatDateTime(new Date(), C_DATE_PATTON_DEFAULT);
    }
    
    /**
     * 返回当前指定的时间戳。格式为yyyy-MM-dd HH:mm:ss
     * @return 格式为yyyy-MM-dd HH:mm:ss，总共19位。
     */
    public static String getCurrentDateTime()
    {
        return getFormatDateTime(new Date(), C_TIME_PATTON_DEFAULT);
    }
    
    /**
     * 返回给定时间字符串。
     * <p>
     * 格式：yyyy-MM-dd
     * 
     * @param date
     *            日期
     * @return String 指定格式的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatDate(Date date)
    {
        if (null == date)
        {
            logger.error("[sc_mobile_fwd]: {}",  C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatDateTime(date, C_DATE_PATTON_DEFAULT);
    }
    
    /**
     * 根据制定的格式，返回日期字符串。例如2007-05-05
     * 
     * @param format
     *            "yyyy-MM-dd" 或者 "yyyy/MM/dd",当然也可以是别的形式。
     * @return 指定格式的日期字符串。
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatDate(String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatDateTime(new Date(), format);
    }
    
    /**
     * 返回当前时间字符串。
     * <p>
     * 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @return String 指定格式的日期字符串.
     */
    public static String getCurrentTime()
    {
        return getFormatDateTime(new Date(), C_TIME_PATTON_DEFAULT);
    }
    
    /**
     * 返回给定时间字符串。
     * <p>
     * 格式：yyyy-MM-dd HH:mm:ss
     * 
     * @param date
     *            日期
     * @return String 指定格式的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatTime(Date date)
    {
        if (null == date)
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatDateTime(date, C_TIME_PATTON_DEFAULT);
    }
    
    /**
     * 取得指定年月日的日期对象.
     * 
     * @param year
     *            年
     * @param month
     *            月注意是从1到12
     * @param day
     *            日
     * @return 一个java.util.Date()类型的对象
     */
    public static Date getDateObj(int year, int month, int day)
    {
        Calendar c = new GregorianCalendar();
        c.set(year, month - 1, day);
        
        return c.getTime();
    }
    
    /**
     * 取得指定分隔符分割的年月日的日期对象.
     * 
     * @param argsDate
     *            格式为"yyyy-MM-dd"
     * @param split
     *            时间格式的间隔符，例如“-”，“/”，要和时间一致起来。
     * @return 一个java.util.Date()类型的对象
     */
    public static Date getDateObj(String argsDate, String split)
    {
        if (StringUtils.isBlank(argsDate) || StringUtils.isBlank(split))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return Calendar.getInstance().getTime();
        }
        
        String[] temp = argsDate.split(split);
        int year = new Integer(temp[0]).intValue();
        int month = new Integer(temp[1]).intValue();
        int day = new Integer(temp[TWO]).intValue();
        return getDateObj(year, month, day);
    }
    
    /**
     * 取得当前Date对象.
     * 
     * @return Date 当前Date对象.
     */
    public static Date getDateObj()
    {
        Calendar c = new GregorianCalendar();
        
        return c.getTime();
    }
    
    /**
     * 取得给定日期加上一定天数后的日期对象.
     * 
     * @param date
     *            给定的日期对象
     * @param amount
     *            需要添加的天数，如果是向前的天数，使用负数就可以.
     * @return Date 加上一定天数以后的Date对象.
     */
    public static Date getDateAdd(Date date, int amount)
    {
        if (null == date)
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return Calendar.getInstance().getTime();
        }
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, amount);
        
        return cal.getTime();
    }
    
    /**
     * 取得给定日期加上一定天数后的日期对象.
     * 
     * @param date
     *            给定的日期对象,如果为空，默认为当前时间
     * @param amount
     *            需要添加的天数，如果是向前的天数，使用负数就可以.
     * @param format
     *            输出格式.
     * @return Date 加上一定天数以后的Date对象.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatDateAdd(Date date, int amount, String format)
    {
        if (null == date)
        {
            return new SimpleDateFormat(C_TIME_PATTON_DEFAULT).format(new Date());
        }
        
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(GregorianCalendar.DATE, amount);
        
        return getFormatDateTime(cal.getTime(), format);
    }
    
    /**
     * 获得当前日期固定间隔天数的日期，如前60天dateAdd(-60)
     * 
     * @param amount
     *            距今天的间隔日期长度，向前为负，向后为正
     * @param format
     *            输出日期的格式.
     * @return java.lang.String 按照格式输出的间隔的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatCurrentAdd(int amount, String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        Date date = getDateAdd(new Date(), amount);
        
        return getFormatDateTime(date, format);
    }
    
    /**
     * 取得给定格式的昨天的日期输出
     * 
     * @param format
     *            日期输出的格式
     * @return String 给定格式的日期字符串.
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatYestoday(String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        //-1表示昨天
        return getFormatCurrentAdd(-1, format);
    }
    
    /**
     * 取得给定字符串描述的日期对象，描述模式采用pattern指定的格式.
     * getDateFromString("2010-11-23", "yyyy-MM-dd")
     * 
     * @param dateStr
     *            日期描述 从给定字符串的开始分析文本，以生成一个日期。该方法不使用给定
     *            字符串的整个文本。
     * 
     * @param pattern
     *            日期模式
     * @return 给定字符串描述的日期对象。若果参数为空，默认为当前 时间
     */
    public static Date getDateFromString(String dateStr, String pattern)
    {
        if (StringUtils.isBlank(dateStr) || StringUtils.isBlank(pattern))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return new Date();
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date resDate = null;
        try
        {
            resDate = sdf.parse(dateStr);
        }
     // BEGIN Modified by zKF66597 for fortify on 2014-03-14
        catch (ParseException e)
        {
            // mod by gKF73638 for Fortify清理 20140402
            logger.error("[sc_mobile_fwd]: date format PasrseException!");
        }
     // END Modified by zKF66597 for fortify on 2014-03-14   
        return resDate;
    }
    
    /**
     * 返回指定日期的前一天。<br>
     * 
     * @param sourceDate
     *         如：2000-12-10
     * @param format
     *           如： yyyy MM dd hh mm ss
     * @return 返回日期字符串，形式和formcat一致。
     *     "" ：参数为空时返回的默认值
     */
    public static String getYestoday(String sourceDate, String format)
    {
        if (StringUtils.isBlank(sourceDate) || StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}",  C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        //-1表示昨天
        return getFormatDateAdd(getDateFromString(sourceDate, format),
                -1,
                format);
    }
    
    /**
     * 返回明天的日期，<br>
     * 
     * @param format
     *         如：yyyy/MM/dd HH/mm/ss
     * @return 返回日期字符串，形式和formcat一致。
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatTomorrow(String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatCurrentAdd(1, format);
    }
    
    /**
     * 返回指定日期的后一天。<br>
     * 
     * @param sourceDate
     *         如：2010-11-20
     * @param format
     *          如：yyyy-MM-dd
     * @return 返回日期字符串，形式和formcat一致。
     *     "" ：参数为空时返回的默认值
     */
    public static String getFormatDateTommorrow(String sourceDate, String format)
    {
        if (StringUtils.isBlank(sourceDate) || StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        return getFormatDateAdd(getDateFromString(sourceDate, format),
                1,
                format);
    }
    
    /**
     * 根据给定的格式，返回时间字符串。 和getFormatDate(String format)相似。
     * 
     * @param format
     *           如： yyyy MM dd hh mm ss等
     * @return 返回一个时间字符串
     *     "" ：参数为空时返回的默认值
     */
    public static String getCurTimeByFormat(String format)
    {
        if (StringUtils.isBlank(format))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return "";
        }
        
        Date newdate = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        
        return sdf.format(newdate);
    }
    
    /**
     * 获取两个时间串时间的差值，单位为秒
     * 
     * @param startTime
     *            开始时间 yyyy-MM-dd HH:mm:ss
     * @param endTime
     *            结束时间 yyyy-MM-dd HH:mm:ss
     * @return 两个时间的差值(秒)
     *    -1 : 为空时设的默认值
     */
    public static long getDiff(String startTime, String endTime)
    {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return -1;
        }
        
        long diff = 0;
        
        // BEGIN Modified by zKF66597 for fortify on 2014-03-14
        Date startDate = formatToDate(startTime);
        
        Date endDate = formatToDate(endTime);
        
        if (null != startDate && null != endDate)
        {
            diff = startDate.getTime() - endDate.getTime();
            diff = diff / THOUSAND;
        }
        // END Modified by zKF66597 for fortify on 2014-03-14    
        return diff;
    }
    
    /**
     * 获取两个时间串时间的差值，单位为小时
     * 
     * @param startTime
     *            开始时间 yyyy-MM-dd HH:mm:ss
     * @param endTime
     *            结束时间 yyyy-MM-dd HH:mm:ss
     * @return 两个时间的差值(秒)
     *    -1 : 为空时设的默认值
     */
    public static int getDiffHour(String startTime, String endTime)
    {
        if (StringUtils.isBlank(startTime) || StringUtils.isBlank(endTime))
        {
            logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            
            return -1;
        }
        
        long diff = 0;
        SimpleDateFormat ft = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        
        try
        {
            Date startDate = ft.parse(startTime);
            Date endDate = ft.parse(endTime);
            diff = startDate.getTime() - endDate.getTime();
            diff = diff / ONE_HOUR_MILSEC;
        }
        catch (ParseException e)
        {
            // mod by gKF73638 for Fortify清理 20140402
            logger.error("[sc_mobile_fwd]: date format PasrseException!");
        }
        
        return (int)diff;
    }
    
    /**
     * lKF33938
     * 格式时间类型的字符串
     * 将字符串类型 yyyy-MM-dd HH:mm:ss 转成  yyyy-MM-dd
     * @param str 日期字符串
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getStringDate(String str)
    {
        SimpleDateFormat df = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        if (!"".equals(str))
        {
            try
            {
                return getFormatDate(df.parse(str));
            }
            catch (ParseException e)
            {
                logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            }
        }
        return str;
    }
    
    /**
     * lKF33938
     * 将字符串按指定时间格式返回
     * 将数据库类型 yyyy-MM-dd HH:mm:ss.0 转成 yyyy-MM-dd HH:mm:ss
     * @param str 日期字符串
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getStringDate2(String str)
    {
        SimpleDateFormat df = new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        if (!"".equals(str))
        {
            try
            {
                return getFormatDateTime(df.parse(str), C_TIME_PATTON_DEFAULT);
            }
            catch (ParseException e)
            {
                logger.error("[sc_mobile_fwd]: {}", C_PARAM_IS_NOT_NULL);
            }
        }
        return str;
    }
    
    /**
     * 判断指定日期 是否为当周的最后一天
     * @param time 日期字符串
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastWeekDay(String time)
    {
        Date date = getDateFromString(time, "yyyy-MM-dd");
        
        return isLastWeekDay(date);
    }
    
    /**
     * 判断指定日期是否为当周的最后一天
     * @param date 日期字符串
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastWeekDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        
        int week = cal.get(Calendar.DAY_OF_WEEK);
        
        if (week == 1)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断当前日期是否为本周的最后一天
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastWeekDay()
    {
        Calendar cal = Calendar.getInstance();
        
        int week = cal.get(Calendar.DAY_OF_WEEK);
        
        if (week == 1)
        {
            return true;
        }
        
        return false;
        
    }
    
    /**
     * 判断指定日期是否为当月的最后一天
     * @param time 字符串格式的日期
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastMonthDay(String time)
    {
        
        Date date = getDateFromString(time, "yyyy-MM-dd");
        
        return isLastMonthDay(date);
    }
    
    /**
     * 判断指定日期是否为当月的最后一天
     * @param date 时间类型
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastMonthDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        
        int curMonth = cal.get(Calendar.MONTH);
        
        cal.add(Calendar.DATE, 1);
        
        int lastMonth = cal.get(Calendar.MONTH);
        
        if (curMonth != lastMonth)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断当前时间是否为本月的最后一天
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastMonthDay()
    {
        return isLastMonthDay(new Date());
    }
    
    /**
     * 判断指定日期是否为当年的最后一天
     * @param date 日期字符串
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastYearDay(String date)
    {
        
        Date dt = getDateFromString(date, "yyyy-MM-dd");
        
        return isLastYearDay(dt);
    }
    
    /**
     * 判断指定日期是否为当年的最后一天
     * @param date 日期字符串
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastYearDay(Date date)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        
        int curYear = cal.get(Calendar.YEAR);
        
        cal.add(Calendar.DATE, 1);
        
        int lastYear = cal.get(Calendar.YEAR);
        
        if (curYear != lastYear)
        {
            return true;
        }
        
        return false;
    }
    
    /**
     * 判断当前日期是否为本年的最后一天
     * @return boolean
     * @see [类、类#方法、类#成员]
     */
    public static boolean isLastYearDay()
    {
        return isLastYearDay(new Date());
    }
    
    /**
     * 指定日期添加 指定的时间 
     * @param date 日期
     * @param name 需要添加的是  年(year) ,月(month) ,日(day) ,时(hour),分(min),秒(sec)
     * @param number 添加数字
     * @return 日期对象
     * @see [类、类#方法、类#成员]
     */
    public static Date addDate(Date date, String name, int number)
    {
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(date);
        
        if ("day".equals(name))
        {
            cal.add(Calendar.DATE, number);
        }
        else if ("week".equals(name))
        {
            cal.add(Calendar.DAY_OF_WEEK, number);
        }
        else if ("month".equals(name))
        {
            cal.add(Calendar.MONTH, number);
        }
        else if ("year".equals(name))
        {
            cal.add(Calendar.YEAR, number);
        }
        else if ("hour".equals(name))
        {
            cal.add(Calendar.HOUR, number);
        }
        else if ("min".equals(name))
        {
            cal.add(Calendar.MINUTE, number);
        }
        else if ("sec".equals(name))
        {
            cal.add(Calendar.SECOND, number);
        }
        else
        {
            cal.add(Calendar.DATE, number);
        }
        
        Date dt = cal.getTime();
        
        return dt;
    }
    
    /**
     * 指定日期添加 指定的时间 
     * @param date 日期
     * @param name 需要添加的是  年(year) ,月(month) ,日(day) ,时(hour),分(min),秒(sec)
     * @param number 添加数字
     * @return 日期字符串
     * @see [类、类#方法、类#成员]
     */
    public static String addDateString(Date date, String name, int number)
    {
        Date dt = addDate(date, name, number);
        
        return dateFormat(getFormatTime(dt), "yyyy-MM-dd HH:mm:ss");
    }
    
    /**
     * 指定日期添加 指定的时间(格式为yyyy-MM-dd) 
     * @param date 日期
     * @param name 需要添加的是  年(year) ,月(month) ,日(day) ,时(hour),分(min),秒(sec)
     * @param number 添加数字
     * @return 日期字符串
     * @see [类、类#方法、类#成员]
     */
    public static String addDateStr(Date date, String name, int number)
    {
        Date dt = addDate(date, name, number);
        
        return dateFormat(getFormatTime(dt), "yyyy-MM-dd");
    }
    
    /**
     * 指定日期添加 指定的时间 
     * @param date 日期字符串
     * @param name 需要添加的是  年(year) ,月(month) ,日(day) ,时(hour),分(min),秒(sec)
     * @param number 添加数字
     * @return 日期字符串 
     * @see [类、类#方法、类#成员]
     */
    public static String addDate(String date, String name, int number)
    {
        //TODO 修改于2010510
        
        Date dt = getDateFromString(date, "yyyy-MM-dd HH:mm:ss");
        return addDateString(dt, name, number);
    }
    
    /**
     * 指定日期添加 指定的时间 
     * @param date 日期字符串
     * @param name 需要添加的是  年(year) ,月(month) ,日(day) ,时(hour),分(min),秒(sec)
     * @param number 添加数字
     * @return 日期字符串
     * @see [类、类#方法、类#成员]
     */
    public static String addDateNew(String date, String name, int number)
    {
        Date dt = getDateFromString(date, "yyyy-MM-dd HH:mm:ss");
        
        return addDateString(dt, name, number);
    }
    
    /**
     * 求两个时间的天数差
     * @param date1 第一个日期
     * @param date2 第二个日期
     * @param isAbs 是否修改为正数
     * @return int
     * @see [类、类#方法、类#成员]
     */
    public static int datediff(String date1, String date2, boolean isAbs)
    {
        Date edate = formatToDate(dateFormat(date1, "yyyy-MM-dd"));
        Date sdate = formatToDate(dateFormat(date2, "yyyy-MM-dd"));
        
        long num = edate.getTime() - sdate.getTime();
        
        long day = 0;
        day = Math.abs(num / ONE_DAY_MILSEC);
        
        return (int)day;
    }
    
    /**
     * 日期差
     * @param date1 开始日期
     * @param date2 结束日期
     * @return 差值天数
     * @see [类、类#方法、类#成员]
     */
    public static int datediff(String date1, String date2)
    {
        return datediff(date1, date2, true);
    }
    
    /**
     * 获得UTC时间
     * @param time 需要转换的日期字符串
     * @return long
     * @see [类、类#方法、类#成员]
     */
    public static long getUTC(String time)
    {
        if (StringUtils.isBlank(time))
        {
            return System.currentTimeMillis() / THOUSAND;
        }
        
        //判断时间参数是否是数字，如果是数字，则就是utc时间，不需再次获取
        if (time.matches("\\d*"))
        {
            return Long.parseLong(time);
        }
        
        Calendar c = Calendar.getInstance();
        
        c.setTime(formatToDate(time));
        
        return c.getTimeInMillis() / THOUSAND;
    }
    
    /**
     * 
     * 将毫秒转成Date时间类型数据
     * 时间格式化
     * <功能详细描述>
     * @param time 毫秒
     * 
     * @return String [返回类型说明]
     * @see [类、类#方法、类#成员]
     */
    public static String getDate(Long time)
    {
        String dateStr = "";
        Date date = new Date(time);
        dateStr = date.toString();
        if (StringUtils.isNotEmpty(dateStr))
        {
            dateStr = getFormatDateTime(date, "yyyy-MM-dd HH:mm:ss");
        }
        return dateStr;
    }

    /**
     * 获取系统时间的UTC时间
     * @return 系统时间的UTC时间
     */
    public static long getNowUTC()
    {
        Calendar cal = Calendar.getInstance();
        long nowUtc = cal.getTimeInMillis() / VALUE1000;
        return nowUtc;
    }
}
