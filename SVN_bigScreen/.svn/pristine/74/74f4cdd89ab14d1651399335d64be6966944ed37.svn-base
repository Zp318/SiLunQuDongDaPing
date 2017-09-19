package com.huawei.sc_mobile_fwd.comm.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.huawei.sc_mobile_fwd.comm.util.NumberUtil;

/**
 * 类名 : EChartsInfo
 * 描述: <Echarts封装图标数据类>
 */
public class EChartsInfo
{
    /**
     * x轴数据
     */
    private List<Object> xAxis = new ArrayList<Object>();
    
    /**
      * 数据
      */
    private Map<String,List<Object>> series = new HashMap<String,List<Object>>();

    /**
     * 其他属性
     */
    private Map<String, Object> others = new HashMap<String, Object>(); 
    
    /**
     * 
     * 描述:空构造
     */
    public EChartsInfo()
    {
        
    }
    
    /**
     * 
     * 描述: 数据字段构造
     * @param seriesColumn 字段
     */
    public EChartsInfo(String seriesColumn)
    {
     // 图形中图例数
        String[] chartsLegends = seriesColumn.split(",");
        for (String chartName : chartsLegends)
        {
            series.put(chartName, new ArrayList<>());
        }
    }
    /**
     * 获取xAxis
     * @return 返回 xAxis
     */
    public List<Object> getxAxis()
    {
        return xAxis;
    }

    /**
     * 设置xAxis
     * @param xAxis 对xAxis进行赋值
     */
    public void setxAxis(List<Object> xAxis)
    {
        this.xAxis = xAxis;
    }

    /**
     * 获取series
     * @return 返回 series
     */
    public Map<String, List<Object>> getSeries()
    {
        return series;
    }

    /**
     * 设置series
     * @param series 对series进行赋值
     */
    public void setSeries(Map<String, List<Object>> series)
    {
        this.series = series;
        Map<String,Object> unitMap = NumberUtil.castUnit(series);
        for (String key : unitMap.keySet())
        {
            List<Object> list = NumberUtil.divUnit(series.get(key),(String)unitMap.get(key));
            series.put(key, list);
        }
        others.putAll(unitMap);
    }

    public Map<String, Object> getOthers()
    {
        return others;
    }

    public void setOthers(Map<String, Object> others)
    {
        this.others = others;
    }
    
    /**
     * 
     * 方法名: putOthers
     * 描述：添加数据到others
     * @param key 
     * @param value 
     *
     * 创建人: liuchang
     * 创建日期: 2016年7月27日
     * 
     * 修改历史
     * 修改日期：<创建日期，格式：YYYY-MM-DD>
     * 修改人： <姓名/工号>
     * 修改原因/修改内容:  <修改原因描述> <问题单DTSXXXXXXXX>
     */
    public void putOthers(String key, Object value)
    {
        others.put(key, NumberUtil.numberFormat(value));
    }
    
    /**
     * 
     * 方法名: putOthers
     * 描述：<方法的功能和实现思路>
     * @param key 
     * @param value 
     * @param decimal 
     */
    public void putOthers(String key, Object value, int decimal)
    {
        others.put(key, NumberUtil.numberFormat(value,decimal));
    }
}
