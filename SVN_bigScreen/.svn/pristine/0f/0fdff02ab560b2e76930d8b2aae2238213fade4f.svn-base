package com.huawei.sc_mobile_fwd.pages.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.seq.WebCoreTools;

/**
 * 配置文件处理工具
 */
public final class PropertiesUtil
{
	/** 日志器 */
	private static final Logger log = LoggerFactory.getLogger(PropertiesUtil.class);

	/** 实例 */
	private static PropertiesUtil propertiesUtil = new PropertiesUtil();

	/** 缓存 */
	private static Hashtable<String, Properties> register = new Hashtable<String, Properties>();

	/**
	 * 获取实例
	 * @return 实例
	 */
	public static PropertiesUtil getInstance()
	{
		return propertiesUtil;
	}

	/**
	 * 获取集合
	 * @param fileName 文件名
	 * @return 集合
	 */
	public static Properties getProperties(String fileName)
	{
		InputStream inputStream = null;
		Properties properties = null;
		try
		{
			properties = (Properties) register.get(fileName);

			if (null == properties)
			{
				try
				{
					inputStream = new FileInputStream(WebCoreTools.getConfigFile(fileName).getFile());
				}
				catch (FileNotFoundException e)
				{
					if (fileName.startsWith("/"))
					{
						inputStream = PropertiesUtil.class.getResourceAsStream(fileName);
					}
					else
					{
						inputStream = PropertiesUtil.class.getResourceAsStream("/" + fileName);
					}

				}
				catch (SecurityException e)
				{
					if (fileName.startsWith("/"))
					{
						inputStream = PropertiesUtil.class.getResourceAsStream(fileName);
					}
					else
					{
						inputStream = PropertiesUtil.class.getResourceAsStream("/" + fileName);
					}
				}

				properties = new Properties();

				properties.load(inputStream);

				register.put(fileName, properties);
			}

			if (null != inputStream)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e.getMessage());
				}
			}
		}
		catch (IOException e)
		{
			log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e.getMessage());
			if (null != inputStream)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e1)
				{
					log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e1.getMessage());
				}
			}
		}
		catch (IllegalArgumentException e)
		{
			log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e.getMessage());

			if (null != inputStream)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e1)
				{
					log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e1.getMessage());
				}
			}
		}
		finally
		{
			if (null != inputStream)
			{
				try
				{
					inputStream.close();
				}
				catch (IOException e)
				{
					log.error("[sc_mobile_fwd]: PropertiesUtil.getProperties.Error:{}", e.getMessage());
				}
			}

		}

		return properties;
	}

	/**
	 * 获取属性值
	 * @param fileName 文件名
	 * @param strKey 属性名
	 * @return 属性值
	 */
	public static String getPropertyValue(String fileName, String strKey)
	{
		if (StringUtils.isEmpty(fileName))
		{
			return null;
		}

		if (StringUtils.isEmpty(strKey))
		{
			return null;
		}

		Properties properties = getProperties(fileName);
		try
		{
			return properties.getProperty(strKey);
		}
		catch (SecurityException e)
		{
			log.error("[sc_mobile_fwd]: PropertiesUtil.getPropertyValue.Error:{}", e.getMessage());
		}
		return null;
	}

	/**
	 * 获取WebServer名
	 * @return WebServer名
	 */
	public String getWebServerName()
	{
		String serverConfigPath = "/ecmramfs/common/servercfg.shared/servercfg.cfg";
		String webserverName = getPropertyValue(serverConfigPath, "ServerName");
		log.info("[sc_mobile_fwd]: PropertiesUtil.getWebServerName.WebserverName={}", webserverName);
		if (webserverName == null)
		{
			webserverName = "";
		}
		return webserverName;
	}
}