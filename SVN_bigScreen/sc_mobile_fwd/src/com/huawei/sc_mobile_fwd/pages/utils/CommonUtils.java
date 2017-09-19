package com.huawei.sc_mobile_fwd.pages.utils;

import java.util.List;

/**
 * 时间处理工具类 
 * @author SF2011 2017年5月18日
 */
public final class CommonUtils
{
	private CommonUtils()
	{
	}
	
	/**
	 * 非四舍五入截取小数
	 * @param decimals 小数 
	 * @param places 截取位数
	 * @return 截取值
	 */
	public static String cut(double decimals, int places)
	{
		String value = String.valueOf(decimals);
		int lastIndex = value.length() - 1;
		int index = value.indexOf(".");
		//整数
		if (index == -1) 
		{
			StringBuilder builder = new StringBuilder(".");
			for (int i = 0; i < places; i++) 
			{
				builder.append("0");
			}
			value = value + builder.toString();
		}
		else
		{
			if (lastIndex - index < places) 
			{
				StringBuilder builder = new StringBuilder();
				for (int i = 0; i < (places - (lastIndex - index)) ; i++) 
				{
					builder.append("0");
				}
				value = value + builder.toString();
			}
			else
			{
				value = value.substring(0, index + (places + 1));
			}
		}
		return value;
	}
	
	/**
	 * 四舍五入截取小数
	 * @param decimals 小数 
	 * @param places 截取位数
	 * @return 截取值
	 */
	public static String addFourToFive(double decimals, int places)
	{
		return String.format("%." + places +"f", decimals);
	}
	
	/**
	 * 转换List为字符串
	 * @param list 集合
	 * @return 双引号字符串
	 */
	public static String transformListToStr(List<Object> listDatas)
	{
		StringBuilder builder = new StringBuilder();
		for (Object data : listDatas)
		{
			builder.append("\"");
			builder.append(data.toString());
			builder.append("\"");
			if (listDatas.indexOf(data) < listDatas.size() - 1)
			{
				builder.append(",");
			}
		}
		return builder.toString();
	}
	
	/**
	 * 转换List为字符串(单引号)
	 * @param list 集合
	 * @return 单引号字符串
	 */
	public static String transformListToStrSigle(List<Object> listDatas)
	{
		StringBuilder builder = new StringBuilder();
		for (Object data : listDatas)
		{
			builder.append("'");
			builder.append(data.toString());
			builder.append("'");
			if (listDatas.indexOf(data) < listDatas.size() - 1)
			{
				builder.append(",");
			}
		}
		return builder.toString();
	}
}
