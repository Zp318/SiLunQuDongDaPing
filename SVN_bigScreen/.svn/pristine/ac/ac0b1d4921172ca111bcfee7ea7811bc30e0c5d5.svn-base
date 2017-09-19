package com.huawei.sc_mobile_fwd.pages.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.huawei.sc_mobile_fwd.comm.thread.QueryTask;
import com.huawei.sc_mobile_fwd.comm.thread.QueryThreadPool;
import com.huawei.sc_mobile_fwd.comm.util.SFConfig;
import com.huawei.sc_mobile_fwd.pages.constants.Constants;
import com.huawei.sc_mobile_fwd.pages.dao.IndicatorQueryDao;
import com.huawei.sc_mobile_fwd.pages.model.Indicator;
import com.huawei.sc_mobile_fwd.pages.utils.CommonUtils;
import com.huawei.sc_mobile_fwd.pages.utils.CsvUtils;
import com.huawei.sc_mobile_fwd.pages.utils.DateUtils;
import com.huawei.sc_mobile_fwd.pages.utils.IndicatorManager;
import com.huawei.seq.SpringContextUtils;

/**
 * 指标查询持久层类 
 * @author SF2011 2017年5月18日
 */
@Repository
public class IndicatorQueryDaoImpl implements IndicatorQueryDao
{
    /**
     * 日志器
     */
    private static final Logger logger = LoggerFactory.getLogger(IndicatorQueryDaoImpl.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object queryCustomerPerceptionIndicatorData(Map<String, String> paramsMap, String indicator)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCustomerPerceptionIndicatorData begin.ParamsMap={}, indicator={}",
				paramsMap, indicator);
		List<Map<String, Object>> datas = null;
		try
		{
			datas = QueryThreadPool.query(new QueryTask("customer_template", paramsMap));
		}
		catch (Exception e)
		{
			logger.error("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCustomerPerceptionIndicatorData.Error={}", e.getMessage());
		}
		if (datas == null)
		{
			datas = new ArrayList<Map<String, Object>>();
		}
		//匹配地市数据
		String sort = "DESC";
		if ("VOICE_MO_ALERT_AVG_DELAY".equalsIgnoreCase(indicator) || "SF_VOLTE_CALL_DROP_RATE_1".equalsIgnoreCase(indicator))
		{
			sort = "ASC";
		}
		datas = matchCityDatas(datas, sort, "BIG");
		
		for (Map<String, Object> data : datas) 
		{
			Object value = data.get("VALUE");
			data.put("VALUE", StringUtils.isEmpty(value) ? "" : CommonUtils.cut(Double.valueOf(value.toString()), 2));
		}
		//创建指标对象
		Indicator ind = new Indicator();
		ind.setTitle(indicator);
		ind.setLegend(indicator);
		ind.setUnit(IndicatorManager.instance().getValue(String.format("%s_UNIT", indicator)));
		ind.setData(datas);
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCustomerPerceptionIndicatorData end.Indicator={}", ind);
		return ind;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Object queryMarketTacIndicatorData(Map<String, String> paramsMap, String indicator)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketTacIndicatorData begin.ParamsMap={}, indicator={}",
				paramsMap, indicator);
		List<Map<String, Object>> datas = null;
		try
		{
			datas = QueryThreadPool.query(new QueryTask("market_template", paramsMap));
		}
		catch (Exception e)
		{
			logger.error("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketTacIndicatorData.Error={}", e.getMessage());
		}
		if (datas == null)
		{
			datas = new ArrayList<Map<String, Object>>();
		}
		List<Map<String, Object>> transformDatas = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> data : datas)
		{
			//终端品牌英文名
			String brandNameEN = data.get("NAME").toString();
			//终端品牌中文名
			Map<String, String> brandMap = (Map<String, String>)SFConfig.get("terminal_brand");
			String brandNameCH = brandMap.containsKey(brandNameEN) ? brandMap.get(brandNameEN) : brandNameEN;
			
			Map<String, Object> transformData = new HashMap<String, Object>();
			transformData.put("id", brandNameEN);
			transformData.put("name", brandNameCH);
			
			Object value = data.get("VALUE");
			transformData.put("value", StringUtils.isEmpty(value) ? "" : CommonUtils.cut(Double.valueOf(value.toString()), 2));
			transformDatas.add(transformData);
		}
		//创建指标对象
		Indicator ind = new Indicator();
		ind.setTitle(indicator);
		ind.setLegend(IndicatorManager.instance().getValue(String.format("%s_CH", indicator)));
		ind.setUnit(IndicatorManager.instance().getValue(String.format("%s_UNIT", indicator)));
		ind.setData(transformDatas);
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketTacIndicatorData end.Indicator={}", ind);
		return ind;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> queryMarketSupportTacData(Map<String, String> paramsMap)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketSupportTacData begin.ParamsMap={}", paramsMap);
		List<Map<String, Object>> tacData = null;
		try
		{
			tacData = QueryThreadPool.query(new QueryTask("volte_terminal_user", paramsMap));
		}
		catch (Exception e)
		{
			logger.error("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketSupportTacData.Error={}", e.getMessage());
		}
		if (tacData == null)
		{
			tacData = new ArrayList<Map<String, Object>>();
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryMarketSupportTacData end.TacData={}", tacData);
		return tacData;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object queryCallDelayRate(Map<String, String> paramsMap, String indicator)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCallDelayRate begin.Indicator={}", indicator);
		//CSV文件名包含当天凌晨的时间戳
		String startTime = paramsMap.get("START_TIME");
		
		//从CSV文件获取数据
		List<Map<String, Object>> datas =  CsvUtils.getDataForCsv(Long.valueOf(startTime), Constants.CALL_DELAY_RATE_FILE_PATH);
		CsvUtils.dataSort(datas, "VOICERESIENT_RATIO");
		//转换后数据
		List<Map<String, Object>> transformedDatas = new ArrayList<Map<String, Object>>();
		int index = -1;
		for (Map<String, Object> data : datas)
		{
			if ("51".equals(data.get("L2_ID").toString()))
			{
				index = datas.indexOf(data);
				continue;
			}
			Map<String, Object> obj = new HashMap<String, Object>();
			obj.put("id", data.get("L2_ID"));
			obj.put("name", data.get("L2_NAME"));
			Object value = data.get("VOICERESIENT_RATIO");
			obj.put("value", StringUtils.isEmpty(value) ? "" : CommonUtils.cut(Double.valueOf(value.toString()), 2));
			transformedDatas.add(obj);
		}
		if (-1 != index)
		{
			datas.remove(index);
		}
		//匹配地市数据
		transformedDatas = matchCityDatas(transformedDatas, "DESC", "SMALL");
		
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCallDelayRate.Datas={}", transformedDatas);
		//创建指标对象
		Indicator ind = new Indicator();
		ind.setTitle(indicator);
		ind.setLegend(indicator);
		ind.setUnit("%");
		ind.setData(transformedDatas);
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryCallDelayRate end.Indicator={}", ind);
		return ind;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Object> queryAutoSubscriberIndicator(Map<String, String> paramsMap, List<String> indicators)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryAutoSubscriberIndicator begin.paramsMap={},indicators={}", 
				paramsMap, indicators);
		List<Object> indicatorDatas = new ArrayList<Object>();
		
		//CSV文件名包含当天凌晨的时间戳
		String endTime = paramsMap.get("END_TIME");
		
		//从CSV文件获取数据
		List<Map<String, Object>> datas =  CsvUtils.getDataForCsv(Constants.AUTO_SUBSCRIBER_FILE_NAME, Constants.AUTO_SUBSCRIBER_FILE_PATH);
		int size = datas.size();
		
		for (String indicator : indicators)
		{
			//指标对应CSV文件中字段
			String indicatorDesc = IndicatorManager.instance().getValue(String.format("%s_DESC", indicator));
			//转换后数据
			List<Map<String, Object>> transformedDatas = new ArrayList<Map<String, Object>>();
			//获取7天数据
			List<Map<String, Long>> last7DaysTimestamp = DateUtils.getLast7DaysTimestamp(Long.valueOf(endTime));
			for (Map<String, Long> oneDayTimestamp : last7DaysTimestamp)
			{
				Long oneDayStartTime = oneDayTimestamp.get("START_TIME");
				Long oneDayEndTime = oneDayTimestamp.get("END_TIME");
				//格式化后时间字符串
				String oneDayStartTimeFormat = DateUtils.formatDate(Constants.DATE_FORMAT, oneDayStartTime);
				
				Map<String, Object> transformedData = new HashMap<String, Object>();
				for (int j = size - 1; j >= 0; j--)
				{
					//一行数据
					Map<String, Object> data = datas.get(j);
					//一行数据对应的时间
					Long startTime = -1L;
					try
					{
						startTime = Long.valueOf(data.get("starttime").toString());
					}
					catch (Exception e)
					{
						logger.error("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryAutoSubscriberIndicator.Error={}", e.getMessage());
					}
					if (startTime >= oneDayStartTime && startTime <= oneDayEndTime)
					{
						transformedData.put("id", j);
						transformedData.put("name", oneDayStartTimeFormat);
						if (indicators.indexOf(indicator) > 1) 
						{
							Object value = data.get(indicatorDesc);
							transformedData.put("value", StringUtils.isEmpty(value) ? "" : CommonUtils.cut(Double.valueOf(value.toString()), 2));
						}
						else
						{
							transformedData.put("value", data.get(indicatorDesc));
						}
						break;
					}
				}
				if (transformedData.size() == 0)
				{
					transformedData.put("id", 0);
					transformedData.put("name", oneDayStartTimeFormat);
					transformedData.put("value", "");
				}
				transformedDatas.add(transformedData);
			}
			//创建指标对象
			Indicator ind = new Indicator();
			ind.setTitle(indicator);
			ind.setLegend(IndicatorManager.instance().getValue(String.format("%s_CH", indicator)));
			ind.setUnit(IndicatorManager.instance().getValue(String.format("%s_UNIT", indicator)));
			ind.setData(transformedDatas);
			
			indicatorDatas.add(ind);
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryAutoSubscriberIndicator end.datas={}", indicatorDatas);
		return indicatorDatas;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Map<String, Object>> queryTacUserData(Map<String, String> paramsMap, String indicator)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryTacUserData begin.paramsMap={},indicator={}", paramsMap, indicator);
		
		String sql = IndicatorManager.instance().getValue(String.format("%s_SQL", indicator));
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryTacUserData.SQL={}", sql);
		//SQL参数获取
		String startTime = paramsMap.get("START_TIME");
		String endTime = paramsMap.get("END_TIME");
		String tacBrands = paramsMap.get("TAC_BRANDS");
		//SDR表名
		String sdrName = String.format("PS.SDR_TAC_SUBSCRIBER_NUM_1DAY_%s", DateUtils.getMonthSuffix(Long.valueOf(startTime)));
		//替换SQL中占位符
		sql = String.format(sql, sdrName, startTime, endTime, tacBrands);
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryTacUserData.SQL={}", sql);
		//查询
		List<Map<String, Object>> datas = null;
		try
		{
			datas = SpringContextUtils.getPSBasicDao().queryForList(sql);
		}
		catch (Exception e)
		{
			logger.error("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryTacUserData.Error={}", e.getMessage());
		}
		if (datas == null)
		{
			datas = new ArrayList<Map<String, Object>>();
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.queryTacUserData end.Datas={}", datas);
		return datas;
	}
	
	/**
	 * 匹配地市数据
	 */
	private List<Map<String, Object>> matchCityDatas(List<Map<String, Object>> srcDatas, String sort, String font)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.matchCityDatas begin.srcDatas={}", srcDatas);
		//如果查询数据为空
		if (srcDatas != null && srcDatas.isEmpty())
		{
			return srcDatas;
		}
		
		String idKey = "BIG".equalsIgnoreCase(font) ? "ID" : "id";
		String nameKey = "BIG".equalsIgnoreCase(font) ? "NAME" : "name";
		String valueKey = "BIG".equalsIgnoreCase(font) ? "VALUE" : "value";
		
		List<Map<String, Object>> transformDatas = new ArrayList<Map<String, Object>>();
		//遗漏地市数据
		List<Map<String, Object>> leaveCityDatas = new ArrayList<Map<String, Object>>();
		//地市
		List<String> citys = IndicatorManager.instance().getCitysNo();
		outer:for (String city : citys)
		{
			for (Map<String, Object> srcData : srcDatas)
			{
				String id = srcData.get(idKey).toString();
				if (city.equalsIgnoreCase(id))
				{
					continue outer;
				}
			}
			//如果没有该地市的数据, 需要构造.
			Map<String, Object> data = new HashMap<String, Object>();
			data.put(idKey, Integer.valueOf(city));
			data.put(nameKey, IndicatorManager.instance().getValue(city));
			data.put(valueKey, "");
			leaveCityDatas.add(data);
		}
		//降序
		if ("DESC".equalsIgnoreCase(sort))
		{
			transformDatas.addAll(srcDatas);
			transformDatas.addAll(leaveCityDatas);
		}
		//升序
		else
		{
			transformDatas.addAll(leaveCityDatas);
			transformDatas.addAll(srcDatas);
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryDaoImpl.matchCityDatas end.Datas={}", transformDatas);
		return transformDatas;
	}
}
