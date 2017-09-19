package com.huawei.sc_mobile_fwd.pages.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.WebCoreTools;

/**
 * CSV工具类
 * @author SF2011 2017年5月23日
 */
public final class CsvUtils
{
	/**
	 * 日志器
	 */
	private static final Logger logger = LoggerFactory.getLogger(IndicatorManager.class);

	private CsvUtils()
	{
	}
	
	/**
	 * 根据查询时间和路径，获取其数据文件
	 * @param fileNameCond 数据文件名条件
	 * @param dataFilePath 对应路径
	 * @return List<Map<String,Object>>
	 */
	@SuppressWarnings("null")
	public static List<Map<String, Object>> getDataForCsv(Object fileNameCond, String dataFilePath)
	{
		logger.info("[sc_mobile_fwd]: CsvUtils.getDataForCsv.fileNameCond={},dataFilePath={}", fileNameCond, dataFilePath);
		File file = new File(WebCoreTools.getWebRootPath() + File.separatorChar + dataFilePath);
		File[] files = file.listFiles();
		// 指定时间数据文件
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		// 检测目录下是否有文件
		if (files != null || files.length > 0)
		{
			//for (File temp : files)
			//{
			//	String fileName = temp.getName();
			//	if (fileName.contains(fileNameCond.toString()))
			//	{
			//	}
			//}
			try
			{
				File newestFile = getNewestFile(files);
				// 读取数据
				list = FileUtils.getDataFromCsv(newestFile);
			}
			catch (IOException e)
			{
				logger.error("[sc_mobile_fwd]: CsvUtils.getDataForCsv.Error={}", e.getMessage());
			}
		}
		else
		{
			logger.error("[sc_mobile_fwd]: CsvUtils.getDataForCsv.File no exists.");
		}
		return list;
	}

	/**
	 * 数据排序-降序
	 * @param datas 排序数据
	 * @param nameId 排序字段
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> dataSort(List<Map<String, Object>> datas, final String nameId)
	{
		logger.info("[sc_mobile_fwd]: CsvUtils.dataSort.datas={},nameId={}", datas, nameId);
		Collections.sort(datas, new Comparator<Map<String, Object>>()
		{
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2)
			{
				double temp1 = Double.valueOf(o1.get(nameId) + "");
				double temp2 = Double.valueOf(o2.get(nameId) + "");
				if (temp1 > temp2)
				{
					return -1;
				}
				if (temp1 < temp2)
				{
					return 1;
				}
				return 0;
			}
		});
		logger.info("[sc_mobile_fwd]: CsvUtils.dataSort.datas={}", datas);
		return datas;
	}

	/**
	 * 数据排序-降序
	 * @param datas 排序数据
	 * @param nameId 排序字段
	 * @return List<Map<String,Object>>
	 */
	public static List<Map<String, Object>> dataSortAsc(List<Map<String, Object>> datas, final String nameId)
	{
		logger.info("[sc_mobile_fwd]: CsvUtils.dataSortAsc.datas={},nameId={}", datas, nameId);
		Collections.sort(datas, new Comparator<Map<String, Object>>()
		{
			@Override
			public int compare(Map<String, Object> o1, Map<String, Object> o2)
			{
				double temp1 = Double.valueOf(o1.get(nameId) + "");
				double temp2 = Double.valueOf(o2.get(nameId) + "");
				if (temp1 > temp2)
				{
					return 1;
				}
				if (temp1 < temp2)
				{
					return -1;
				}
				return 0;
			}
		});
		logger.info("[sc_mobile_fwd]: CsvUtils.dataSortAsc.datas={}", datas);
		return datas;
	}
	
	/**
	 * 判断是否是最新的文件 
	 * @param files 文件集
	 */
	public static File getNewestFile(File[] files)
	{
		logger.info("[sc_mobile_fwd]: CsvUtils.getNewestFile.files={}", files.length);
		//优先判断文件名称
		Long recent = 0L;
		File recentFile = null;
		for (File f : files)
		{
			String fileName = f.getName();
			if (fileName.length() == 23)
			{
				String substr = fileName.substring(9, 19);
				try
				{
					Long fileTime = Long.valueOf(substr);
					if (recent < fileTime)
					{
						recent = fileTime;
						recentFile = f;
					}
				}
				catch (Exception e)
				{
					logger.error("[sc_mobile_fwd]: CsvUtils.getNewestFile.Error={}", e.getMessage());
				}
			}
		}
		//如果判断文件名称失败, 判断文件修改时间
		if (recentFile == null)
		{
			recent = 0L;
			for (File f : files)
			{
				long lastModified = f.lastModified();
				if (lastModified > recent)
				{
					recent = lastModified;
					recentFile = f;
				}
			}
		}
		logger.info("[sc_mobile_fwd]: CsvUtils.getNewestFile.recentFile={}", recentFile.getName());
		return recentFile;
	}
}