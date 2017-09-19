package com.huawei.sc_mobile_fwd.pages.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 指标查询持久层接口 
 * @author SF2011 2017年5月18日
 */
@Repository
public interface IndicatorQueryDao
{
	/**
	 * 查询客户感知类指标数据
	 * @param paramsMap 参数集合
	 * @param indicator 指标
	 * @return 指标数据
	 */
	Object queryCustomerPerceptionIndicatorData(Map<String, String> paramsMap, String indicator);

	/**
	 * 查询市场支撑类指标数据
	 * @param paramsMap 参数集合
	 * @param indicator 指标
	 * @return 指标数据
	 */
	Object queryMarketTacIndicatorData(Map<String, String> paramsMap, String indicator);

	/**
	 * 查询市场支撑类终端数据
	 * @param paramsMap 参数集合
	 * @return 终端数据
	 */
	List<Map<String, Object>> queryMarketSupportTacData(Map<String, String> paramsMap);

	/**
	 * 查询通话时长占比
	 * @param paramsMap 参数集合
	 * @param indicator 指标
	 * @return 通话时长占比数据
	 */
	Object queryCallDelayRate(Map<String, String> paramsMap, String indicator);

	/**
	 * 查询自动开通成功率
	 * @param paramsMap 参数集合
	 * @param indicators 指标集合
	 * @return 自动开通成功率数据
	 */
	List<Object> queryAutoSubscriberIndicator(Map<String, String> paramsMap, List<String> indicators);

	/**
	 * 查询终端用户数据
	 * @return 终端用户数据集合
	 */
	List<Map<String, Object>> queryTacUserData(Map<String, String> paramsMap, String indicator);
}
