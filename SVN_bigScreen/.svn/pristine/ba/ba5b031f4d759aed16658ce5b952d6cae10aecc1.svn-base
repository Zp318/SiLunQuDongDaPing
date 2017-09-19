package com.huawei.sc_mobile_fwd.pages.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ecm.frm.util.SecurityFliterUtils;
import com.huawei.sc_mobile_fwd.comm.StrUtil;
import com.huawei.sc_mobile_fwd.comm.UnicodeReader;
import com.huawei.sc_mobile_fwd.comm.util.NumberValidationUtils;
import com.huawei.seq.tools.CloseableUtils;
import com.huawei.seq.tools.SecurityFileUtil;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * 文件工具类
 * @author SF2011 2017年5月23日
 */
public final class FileUtils
{
	/**
	 * 日志器
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
	
	private FileUtils()
	{
	}

	/**
	 * 解析csv文件-返回listmap
	 * @param file 文件
	 * @return List<Map<String, Object>> 数据 
	 * @throws IOException IO异常
	 */
	public static List<Map<String, Object>> getDataFromCsv(File file) throws IOException
	{
		logger.info("[sc_mobile_fwd]: FileUtils.getDataFromCsv.file={}", file.getAbsolutePath());
		BufferedReader br = null;
		try
		{
			List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
			// br = new BufferedReader(new InputStreamReader(new
			// FileInputStream(file), "utf-8"));
			br = new BufferedReader(new UnicodeReader(new FileInputStream(file), "utf-8"));
			String headerStr = br.readLine();
			if (StrUtil.isNullOrEmpty(headerStr))
			{
				return null;
			}
			String[] headers = headerStr.split(",");
			String line;
			while ((line = br.readLine()) != null)
			{
				String[] data = line.split(",");
				if (data.length < headers.length)
				{
					continue;
				}
				Map<String, Object> map = new HashMap<String, Object>();
				int index = 0;
				for (String header : headers)
				{
					String value = data[index++].trim();
					if (value.matches("-?\\d+"))
					{
						map.put(header.trim(), Long.valueOf(value));
					}
					else if (value.matches("-?\\d+\\.\\d*"))
					{
						map.put(header.trim(), new BigDecimal(value));
					}
					else
					{
						map.put(header.trim(), value);
					}
				}
				result.add(map);
			}
			logger.info("[sc_mobile_fwd]: FileUtils.getDataFromCsv.result={}", result);
			return result;
		}
		finally
		{
			if (br != null)
			{
				br.close();
			}
		}
	}

	/**
	 * 解析csv文件 到一个list中 每个单元个为一个String类型记录，每一行为一个list。 再将所有的行放到一个总list中
	 */
	private static List<List<String>> readCSVFile(String fileName)
	{
		logger.info("[sc_mobile_fwd]: FileUtils.readCSVFile.fileName={}", fileName);
		// String path =
		// WebCoreTools.getConfigFile(fileName).getFile().getPath();
		String path = fileName;// 本地测试
		FileInputStream fis = null;
		try
		{
			fis = new FileInputStream(new File(path));
		}
		catch (FileNotFoundException e1)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.readCSVFile.Error={}", e1.getMessage());
			return new ArrayList<List<String>>();
		}
		InputStreamReader fr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(fr);
		String rec = null;// 一行
		String str;// 一个单元格
		List<List<String>> listFile = new ArrayList<List<String>>();
		try
		{
			// 读取一行
			while ((rec = br.readLine()) != null)
			{
				Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
				Matcher mCells = pCells.matcher(rec);
				List<String> cells = new ArrayList<String>();// 每行记录一个list
				// 读取每个单元格
				while (mCells.find())
				{
					str = mCells.group();
					str = str.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str = str.replaceAll("(?sm)(\"(\"))", "$2");
					cells.add(str);
				}
				listFile.add(cells);
			}
		}
		catch (Exception e)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.readCSVFile.Error={}", e.getMessage());
		}
		finally
		{
			if (fr != null)
			{
				try
				{
					fr.close();
				}
				catch (IOException e)
				{
					logger.error("[sc_mobile_fwd]: FileUtils.readCSVFile.Error={}", e.getMessage());
				}
			}
			if (br != null)
			{
				try
				{
					br.close();
				}
				catch (IOException e)
				{
					logger.error("[sc_mobile_fwd]: FileUtils.readCSVFile.Error={}", e.getMessage());
				}
			}
		}
		logger.info("[sc_mobile_fwd]: FileUtils.readCSVFile.List={}", listFile);
		return listFile;
	}

	/**
	 * 根据scv文件返回内容
	 * @param path 文件路径
	 * @return List<Map<String, Object>> 数据
	 * @throws IOException IO异常
	 */
	public static List<Map<String, Object>> getDataByCsv(String path)
	{
		logger.info("[sc_mobile_fwd]: FileUtils.getDataByCsv.Path={}", path);
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		List<List<String>> lines = readCSVFile(path);

		List<String> head = lines.get(0);
		for (int i = 1; i < lines.size(); i++)
		{
			List<String> content = lines.get(i);
			Map<String, Object> row = new HashMap<String, Object>();
			for (int j = 0; j < head.size(); j++)
			{
				String key = head.get(j);
				String value = content.get(j);
				// 表头类型指定为String
				if (key.contains("(String)"))
				{
					key = key.substring(0, key.indexOf("("));
					row.put(key, value);
				}
				else if (NumberValidationUtils.isDecimal(value))
				{
					row.put(key, new BigDecimal(value));
				}
				else if (NumberValidationUtils.isWholeNumber(value))
				{
					row.put(key, Long.parseLong(value));
				}
				else
				{
					row.put(key, value);
				}
			}
			data.add(row);
		}
		logger.info("[sc_mobile_fwd]: FileUtils.getDataByCsv.Data={}", data);
		return data;
	}

	/**
	 * 数据导出
	 * @param file 文件
	 * @param headList 表头
	 * @param dataList 数据
	 * @return CSV文件
	 */
	@SuppressWarnings("rawtypes")
	public static File exportDataToCSV(File file, LinkedList<String> headList, List<Map<String, String>> dataList)
	{
		logger.info("[sc_mobile_fwd]: FileUtils.exportDataToCSV.file={},headList={},dataList={}", 
				file.getAbsolutePath(), headList, dataList);
		SecurityFileUtil securityFile = new SecurityFileUtil(file);// 给对应文件加权
		try
		{
			securityFile.createFile();
		}
		catch (IOException e)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.exportDataToCSV.Error={}", SecurityFliterUtils.whiteListSecurityCode(e.getMessage()));
		}
		CSVWriter writer = null;
		OutputStreamWriter outputStreamWriter = null;
		OutputStream fileOutputStream = null;
		try
		{
			fileOutputStream = Files.newOutputStream(file.toPath(), new OpenOption[]
			{ StandardOpenOption.CREATE, StandardOpenOption.WRITE });
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			// outputStreamWriter = new OutputStreamWriter(fileOutputStream,
			// "GBK");
			fileOutputStream.write(new byte[]
			{ -17, -69, -65 });
			writer = new CSVWriter(outputStreamWriter, ',');

			int maxNum = 100001;// 最大导出限制
			if (maxNum > dataList.size())
			{
				maxNum = dataList.size();// 设置导出条数
			}
			Iterator it = dataList.iterator();
			String tmpStr = "";
			while (it.hasNext())
			{
				String[] data = new String[headList.size()];
				Map dataMap = (Map) it.next();
				for (int i = 0; i < headList.size(); ++i)
				{
					tmpStr = "";
					tmpStr = getString(dataMap.get(headList.get(i)));
					if ("null".equalsIgnoreCase(tmpStr))
					{
						tmpStr = "";
					}
					data[i] = tmpStr;
				}
				writer.writeNext(data);
			}
		}
		catch (FileNotFoundException e)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.exportDataToCSV.Error={}", SecurityFliterUtils.whiteListSecurityCode(e.getMessage()));
			file = null;
		}
		catch (UnsupportedEncodingException e)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.exportDataToCSV.Error={}", SecurityFliterUtils.whiteListSecurityCode(e.getMessage()));
			file = null;
		}
		catch (IOException e)
		{
			logger.error("[sc_mobile_fwd]: FileUtils.exportDataToCSV.Error={}", SecurityFliterUtils.whiteListSecurityCode(e.getMessage()));
			file = null;
		}
		finally
		{
			CloseableUtils.closeStream(writer);
			CloseableUtils.closeStream(outputStreamWriter);
			CloseableUtils.closeStream(fileOutputStream);
		}
		return file;
	}

	/**
	 * 对象转String
	 * @param obj Object
	 * @return String
	 */
	private static String getString(Object obj)
	{
		if (null == obj)
		{
			return "";
		}
		String data = obj.toString();
		return data;
	}
}
