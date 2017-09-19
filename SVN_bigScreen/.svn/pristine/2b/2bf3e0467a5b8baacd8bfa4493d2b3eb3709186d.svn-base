package com.huawei.sc_mobile_fwd.pages.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.huawei.sc_mobile_fwd.comm.util.SFConfig;
import com.huawei.sc_mobile_fwd.pages.constants.Constants;
import com.huawei.sc_mobile_fwd.pages.dao.IndicatorQueryDao;
import com.huawei.sc_mobile_fwd.pages.model.Indicator;
import com.huawei.sc_mobile_fwd.pages.model.ResponseObj;
import com.huawei.sc_mobile_fwd.pages.service.IndicatorQueryService;
import com.huawei.sc_mobile_fwd.pages.utils.CommonUtils;
import com.huawei.sc_mobile_fwd.pages.utils.DateUtils;
import com.huawei.sc_mobile_fwd.pages.utils.IndicatorManager;

/**
 * 指标查询服务实现类
 * @author SF2011 2017年5月16日
 */
@Service
public class IndicatorQueryServiceImpl implements IndicatorQueryService
{
    /**
     * 日志器
     */
    private static final Logger logger = LoggerFactory.getLogger(IndicatorQueryServiceImpl.class);
	
    /**
     * 终端品牌集合
     */
    private Map<String, String> brandMap = new HashMap<String, String>();
    
    /**
     * 持久层接口
     */
    @Autowired
    private IndicatorQueryDao indicatorQueryDao;
    
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ResponseObj queryIndicators(List<String> indicators)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryIndicators begin.Indicators={}", indicators);
		if (indicators == null || indicators.size() == 0)
		{
			return new ResponseObj(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
		//指标归属哪个页面
		String location = IndicatorManager.instance().getIndicatorLocation(indicators);
		if (StringUtils.isEmpty(location))
		{
			return new ResponseObj(HttpStatus.SC_INTERNAL_SERVER_ERROR);
		}
		//时间集合
		Map<String, String> timestampMap = new HashMap<String, String>();
		//指标数据
		List<Object> datas = new ArrayList<Object>();
		//客户感知类指标
		if (Constants.CUSTOMER_PERCEPTION.equalsIgnoreCase(location))
		{
			timestampMap = getLastHourTimestampMap();
			datas = queryCustomerPerceptionIndicatorData(timestampMap, indicators);
		}
		//支撑市场类指标 
		else if (Constants.MARKET_SUPPORT.equalsIgnoreCase(location))
		{
			timestampMap = getLastDayTimestampMap();
			datas = queryMarketSupportIndicatorData(timestampMap, indicators);
		}
		ResponseObj responseObj = buildResponseObj(timestampMap, datas);
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryIndicators end.ResponseObj={}", responseObj);
		return responseObj;
	}

	/***
	 * 组装响应对象
	 */
	private ResponseObj buildResponseObj(Map<String, String> timestampMap, List<Object> datas)
	{
		ResponseObj responseObj = new ResponseObj();
		responseObj.setStarttime(Long.valueOf(timestampMap.get("START_TIME")));
		responseObj.setEndtime(Long.valueOf(timestampMap.get("END_TIME")));
		responseObj.setData(datas);
		return responseObj;
	}

	/**
	 * 查询客户感知类指标数据 
	 */
	private List<Object> queryCustomerPerceptionIndicatorData(Map<String, String> timestampMap, List<String> indicators)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryCustomerPerceptionIndicatorData begin.Indicators={}", indicators);
		//参数集合
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.putAll(timestampMap);
		
		List<Object> datas = new ArrayList<Object>();
		for (String indicator : indicators)
		{
			//SDR表名
			String sdrName = IndicatorManager.instance().getValue(String.format("%s_SDR_NAME", indicator));
			paramsMap.put("SDR_NAME", sdrName);
			//指标名
			String modelName = IndicatorManager.instance().getValue(String.format("%s_NAME", indicator));
			paramsMap.put("MODEL_NAME", modelName);
			//升降序状态
			String descState = IndicatorManager.instance().getValue(String.format("%s_DESC", indicator));
			paramsMap.put("DESC_STATE", descState);
			
			datas.add(indicatorQueryDao.queryCustomerPerceptionIndicatorData(paramsMap, indicator));
		}
		//柱状图和折线图匹配
		if (datas.size() == 2) 
		{
			//VoLTE接通率
			Indicator connectRate = (Indicator)datas.get(0);
			List<Map<String, Object>> connectRateDatas = connectRate.getData();
			//VoLTE接通时延
			Indicator delay = (Indicator)datas.get(1);
			List<Map<String, Object>> delayDatas = delay.getData();
			
			List<Map<String, Object>> transformDatas = matchData(connectRateDatas, delayDatas, "ID");
			delay.setData(transformDatas);
			datas.set(1, delay);
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryCustomerPerceptionIndicatorData end.datas={}", datas);
		return datas;
	}
	
	/**
	 * 查询市场支撑类指标数据 
	 */
	@SuppressWarnings("unchecked")
	private List<Object> queryMarketSupportIndicatorData(Map<String, String> timestampMap, List<String> indicators)
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryMarketSupportIndicatorData begin.Indicators={}", indicators);
		Map<String, String> paramsMap = new HashMap<String, String>();
		paramsMap.putAll(timestampMap);
		
		List<Object> datas = new ArrayList<Object>();
		//终端类指标
		if (IndicatorManager.instance().isTacIndicator(indicators))
		{
			//终端品牌
			List<Object> tacBrands = new ArrayList<Object>();
			//获取终端数据
			List<Map<String, Object>> tacDatas = indicatorQueryDao.queryMarketSupportTacData(paramsMap);
			//终端品牌集合
			this.brandMap = (Map<String, String>)SFConfig.get("terminal_brand");
			
			//##### 1.VoLTE终端渗透率
			String tacPermeateRate = indicators.get(0);
			//VoLTE终端渗透率数据
			List<Map<String, Object>> tacPermeateRateDatas = new ArrayList<Map<String, Object>>();
			//Top10终端总注册VoLTE用户数
			Double totalTacVoLTEUserNum = 0D; 
			for (Map<String, Object> tacData : tacDatas)
			{
				Object userNum = tacData.get("VOLTE_USER_NUM");
				totalTacVoLTEUserNum += (userNum == null ? 0D : Double.valueOf(userNum.toString()));
			}
			for (Map<String, Object> tacData : tacDatas)
			{
				//运算单个终端的VoLTE渗透率
				Map<String, Object> data = new HashMap<String, Object>();
				
				//终端品牌英文名
				Object brandNameEN = tacData.get("BRAND_NAME");
				//终端品牌名英文转换为中文
				tacData = brandNameMapping(tacData, "BRAND_NAME");
				
				data.put("id", brandNameEN);
				data.put("name", tacData.get("BRAND_NAME_CH"));
				
				Object volteUserNum = tacData.get("VOLTE_USER_NUM");
				Double singleTacVoLTEUserNum = (volteUserNum == null ? 0D : Double.valueOf(volteUserNum.toString()));
				
				Double div = singleTacVoLTEUserNum / totalTacVoLTEUserNum;
				data.put("value", CommonUtils.addFourToFive(Double.isNaN(div) ? 0D : div*100, 2));
				tacPermeateRateDatas.add(data);
				
				tacBrands.add(brandNameEN);
			}
			datas.add(new Indicator(tacPermeateRate, tacPermeateRate, "%", tacPermeateRateDatas));
			paramsMap.put("TAC_BRANDS", CommonUtils.transformListToStrSigle(tacBrands));
			
			//##### 2.VoLTE终端转化率
			List<Map<String, Object>> tacConversionRateDatas = new ArrayList<Map<String, Object>>();
			String tacConversionRate = indicators.get(1);
			
			List<Map<String, Object>> tacUserDatas = indicatorQueryDao.queryTacUserData(paramsMap, tacConversionRate);
			for (Map<String, Object> tacUserData : tacUserDatas)
			{
				//运算单个终端的VoLTE转换率
				Map<String, Object> data = new HashMap<String, Object>();
				
				//终端品牌英文名
				Object brandNameEN = tacUserData.get("brand");
				//终端品牌名英文转换为中文
				tacUserData = brandNameMapping(tacUserData, "brand");
				
				data.put("id", brandNameEN);
				data.put("name", tacUserData.get("BRAND_NAME_CH"));
				
				Object userNumStr = tacUserData.get("lte_user");
				Double userNum = (userNumStr == null ? 0D : Double.valueOf(userNumStr.toString()));
				
				Object volteUserNumStr = tacUserData.get("volte_user");
				Double volteUserNum = (volteUserNumStr == null ? 0D : Double.valueOf(volteUserNumStr.toString()));
				
				Double div = volteUserNum / userNum;
				data.put("value", CommonUtils.cut(Double.isNaN(div) ? 0D : div*100, 2));
				tacConversionRateDatas.add(data);
			}
			tacConversionRateDatas = matchDataFromBrands(tacBrands, tacConversionRateDatas, "id");
			datas.add(new Indicator(tacConversionRate, tacConversionRate, "%", tacConversionRateDatas));
			
			//注册成功率 / 接通率 / 切换成功率 / 掉话率
			for (int i = 2; i < indicators.size(); i++)
			{
				//SDR表名
				String sdrName = IndicatorManager.instance().getValue(String.format("%s_SDR_NAME", indicators.get(i)));
				paramsMap.put("SDR_NAME", sdrName);
				//指标名
				String modelName = IndicatorManager.instance().getValue(String.format("%s_NAME", indicators.get(i)));
				paramsMap.put("MODEL_NAME", modelName);
				//TAC码
				paramsMap.put("TAC_BRAND", CommonUtils.transformListToStr(tacBrands));
				
				datas.add(indicatorQueryDao.queryMarketTacIndicatorData(paramsMap, indicators.get(i)));
			}
			//匹配数据
			if (datas.size() == 6)
			{
				//注册成功率 
				Indicator registerSuccRate = (Indicator)datas.get(2);
				List<Map<String, Object>> registerSuccRateDatas = registerSuccRate.getData();
				registerSuccRateDatas = matchDataFromBrands(tacBrands, registerSuccRateDatas, "id");
				
				registerSuccRate.setData(registerSuccRateDatas);
				datas.set(2, registerSuccRate);
				//接通率 
				Indicator connectRate = (Indicator)datas.get(3);
				List<Map<String, Object>> connectRateDatas = connectRate.getData();
				connectRateDatas = matchDataFromBrands(tacBrands, connectRateDatas, "id");
				
				connectRate.setData(connectRateDatas);
				datas.set(3, connectRate);
				//切换成功率 
				Indicator esrvccSuccRate = (Indicator)datas.get(4);
				List<Map<String, Object>> esrvccSuccRateDatas = esrvccSuccRate.getData();
				esrvccSuccRateDatas = matchDataFromBrands(tacBrands, esrvccSuccRateDatas, "id");
				
				esrvccSuccRate.setData(esrvccSuccRateDatas);
				datas.set(4, esrvccSuccRate);
				//掉话率
				Indicator callDropRate = (Indicator)datas.get(5);
				List<Map<String, Object>> callDropRateDatas = callDropRate.getData();
				callDropRateDatas = matchDataFromBrands(tacBrands, callDropRateDatas, "id");
				
				callDropRate.setData(callDropRateDatas);
				datas.set(5, callDropRate);
			}
		}
		//CSV获取类指标
		else
		{
			//自动开通成功率指标
			if (IndicatorManager.instance().isAutoSubscriberIndicator(indicators))
			{
				datas.addAll(indicatorQueryDao.queryAutoSubscriberIndicator(paramsMap, indicators));
			}
			//VoLTE通话时长占比
			else
			{
				datas.add(indicatorQueryDao.queryCallDelayRate(paramsMap, indicators.get(0)));
			}
		}
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.queryMarketSupportIndicatorData end.datas={}", datas);
		return datas;
	}


	
	
	/**
	 * 获取上一小时时间集合
	 * 备注: 客户感知类指标为小时粒度(向前规整)
	 */
	private Map<String, String> getLastHourTimestampMap()
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.getLastHourTimestampMap begin.");
		long lastHourEndTimestamp = DateUtils.getLastHourEndTimestamp(new Date());
		//上一小时起始时间戳
		long lastHourStartTimestamp = lastHourEndTimestamp - 1*60*60;
		
		Map<String, String> lastHourTimestampMap = new HashMap<String, String>();
		lastHourTimestampMap.put("START_TIME", String.valueOf(lastHourStartTimestamp));
		lastHourTimestampMap.put("END_TIME", String.valueOf(lastHourEndTimestamp));
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.getLastHourTimestampMap end.LastHourTimestampMap={}", 
				lastHourTimestampMap);
		return lastHourTimestampMap;
	}
	
	/**
	 * 获取上一天时间集合
	 * 备注: 支撑市场类指标为天粒度(前一天)
	 */
	private Map<String, String> getLastDayTimestampMap()
	{
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.getLastDayTimestampMap begin.");
		long lastDayEndTimestamp = DateUtils.getLastDayEndTimestamp(new Date());
		//上一天起始时间戳
		long lastDayStartTimestamp = lastDayEndTimestamp - 24*60*60;
		
		Map<String, String> lastDayTimestampMap = new HashMap<String, String>();
		lastDayTimestampMap.put("START_TIME", String.valueOf(lastDayStartTimestamp));
		lastDayTimestampMap.put("END_TIME", String.valueOf(lastDayEndTimestamp));
		logger.info("[sc_mobile_fwd]: IndicatorQueryServiceImpl.getLastDayTimestampMap end.LastDayTimestampMap={}", 
				lastDayTimestampMap);
		return lastDayTimestampMap;
	}
	
	/**
     * 将终端品牌转为中文
     * @param data 数据
     * @param field 字段
     * @return Map<String,Object> 转换后数据
     */
    private Map<String, Object> brandNameMapping(Map<String, Object> data, String field)
    {
        if (data == null)
        {
            return data;
        }
        String brandName = (String)data.get(field);
        String brandNameCh = brandMap.containsKey(brandName) ? brandMap.get(brandName) : brandName;
        data.put("BRAND_NAME_CH", brandNameCh);
        return data;
    }
    
    /**
     * 匹配数据
     */
    private List<Map<String, Object>> matchData(List<Map<String, Object>> templateDatas, List<Map<String, Object>> matchDatas, String key)
    {
    	List<Map<String, Object>> transformDatas = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> templateData : templateDatas) 
		{
			String id1 = templateData.get(key).toString();
			for (Map<String, Object> matchData : matchDatas) 
			{
				String id2 = matchData.get(key).toString();
				if (id1 != null && id1.equalsIgnoreCase(id2)) 
				{
					transformDatas.add(matchData);
					break;
				}
			}
		}
		return transformDatas.size() == 0 ? matchDatas : transformDatas;
    }
    
    /**
     * 匹配数据
     */
    private List<Map<String, Object>> matchDataFromBrands(List<Object> tacBrands, 
    		List<Map<String, Object>> matchDatas, String key)
    {
    	List<Map<String, Object>> transformDatas = new ArrayList<Map<String, Object>>();
    	outer : for (Object tacBrand : tacBrands) 
    	{
    		String id1 = tacBrand.toString();
    		for (Map<String, Object> matchData : matchDatas) 
    		{
    			String id2 = matchData.get(key).toString();
    			if (id1 != null && id1.equalsIgnoreCase(id2)) 
    			{
    				transformDatas.add(matchData);
    				continue outer;
    			}
    		}
    		//如果没有找到对应数据, 则补上
    		Map<String, Object> data = new HashMap<String, Object>();
    		data.put("id", tacBrand);
    		data.put("name", brandMap.containsKey(tacBrand) ? brandMap.get(tacBrand) : tacBrand);
    		data.put("value", "");
    		transformDatas.add(data);
    	}
    	return transformDatas.size() == 0 ? matchDatas : transformDatas;
    }
}
