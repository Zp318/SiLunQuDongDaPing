package com.huawei.sc_mobile_fwd.pages.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 时间处理工具类 
 * @author SF2011 2017年5月18日
 */
public final class DateUtils
{
	private DateUtils()
	{
	}
	
	/***
	 * 获取上一小时结束时间戳
	 * @param current 当前时间
	 * @return 上一小时结束时间戳
	 */
	public static long getLastHourEndTimestamp(Date current)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		int minute = calendar.get(Calendar.MINUTE);
		int secend = calendar.get(Calendar.SECOND);
		int millisec = calendar.get(Calendar.MILLISECOND);
		long millisecond = minute*60*1000 + secend*1000 + millisec;
		//秒
		return (calendar.getTimeInMillis() - millisecond) / 1000;
	}
	
	/***
	 * 获取上一天结束时间戳
	 * @param current 当前时间
	 * @return 上一天结束时间戳
	 */
	public static long getLastDayEndTimestamp(Date current)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(current);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int secend = calendar.get(Calendar.SECOND);
		int millisec = calendar.get(Calendar.MILLISECOND);
		long millisecond = hour*60*60*1000 + minute*60*1000 + secend*1000 + millisec;
		//秒
		return (calendar.getTimeInMillis() - millisecond) / 1000;
	}
	
	/**
	 * 获取过去七天起始和结束时间戳
	 * @param currentDayStartTime 当天起始时间
	 * @return 时间戳集合
	 */
	public static List<Map<String, Long>> getLast7DaysTimestamp(Long currentDayStartTime)
	{
		List<Map<String, Long>> timeStampList = new ArrayList<Map<String, Long>>();
		Long startTime = currentDayStartTime - 7*24*60*60;
		for (int i = 0; i < 7; i++)
		{
			Map<String, Long> timeStamp = new HashMap<String, Long>();
			//前一天起始/结束时间
			Long endTime = startTime + 1*24*60*60;
			timeStamp.put("START_TIME", startTime);
			timeStamp.put("END_TIME", endTime);
			timeStampList.add(timeStamp);
			//重置
			startTime = endTime;
		}
		return timeStampList;
	}
	
	/**
	 * 格式化时间
	 * @param pattern 正则表达式
	 * @param timeStamp 时间戳
	 * @return 格式化时间字符串
	 */
	public static String formatDate(String pattern, Long timeStamp)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp*1000L);
		return new SimpleDateFormat(pattern).format(calendar.getTime());
	}
	
	/**
	 * 获取月后缀(距离1970-1-1的月数)
	 * @param timeStamp 当前时间戳
	 * @return 月数
	 */
	public static int getMonthSuffix(Long timeStamp)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeStamp*1000L);
		//距离1970的年数
		int offsetYear = calendar.get(Calendar.YEAR) - 1970;
		//距离1970-1-1的月数
		return offsetYear*12 + calendar.get(Calendar.MONTH);
	}
}
