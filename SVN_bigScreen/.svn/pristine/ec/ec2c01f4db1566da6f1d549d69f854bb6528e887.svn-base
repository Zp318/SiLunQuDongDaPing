package com.huawei.sc_mobile_fwd.comm.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.huawei.sc_mobile_fwd.comm.bean.EChartsInfo;
import com.huawei.sc_mobile_fwd.comm.bean.SeriesData;
import com.huawei.sc_mobile_fwd.comm.consts.EChartsDataType;
import com.huawei.sc_mobile_fwd.comm.consts.EChartsType;


/**
 * 类名 : EChartsManager
 * 描述: <Echarts管理类>
 */
public class EChartsManager 
{

    /**
     * 
     * 方法名: dealData
     * 描述：<方法的功能和实现思路>
     * @param echartsType 
     * @param businessData 
     * @param xAxisColumn 
     * @param seriesColumn 
     * @return EChartsInfo 
     * 
     */
    public static EChartsInfo dealData(String echartsType, 
        List<Map<String, Object>> businessData, String xAxisColumn, String seriesColumn) 
    {
        int length = seriesColumn.split(",").length;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++)
        {
            if (i > 0)
            {
                sb.append(",");
            }
            sb.append("2");
        }
        return dealData(echartsType, businessData, xAxisColumn, seriesColumn, sb.toString());
    }
    
    /** 
     * 方法名: dealData
     * 描述：<处理Echarts返回数据类型>
     * @param echartsType  图表类型 
     * @param businessData  业务数据
     * @param xAxisColumn   X轴数据字段 
     * @param seriesColumn  data数据字段 “,”分割多个字段
     * @param decimals seriesColumn对应保留的小数位数，“,”分隔
     * @return List<EChartsInfo> 图表集合
     *
     */
    public static EChartsInfo dealData(String echartsType, 
            List<Map<String, Object>> businessData, String xAxisColumn, String seriesColumn, String decimals) 
    {
        if (businessData == null || businessData.isEmpty())
        {
            return new EChartsInfo(seriesColumn);
        }
        EChartsInfo chartsInfo = new EChartsInfo();
        if (StringUtils.isEmpty(seriesColumn))
        {
            return chartsInfo;
        }
        Map<String,Object> component = initComponent(echartsType);
        
        // 图形中图例数
        String[] chartsLegends = seriesColumn.split(",");
        String[] decimal = decimals.split(",");
        
        List<Object> xAxis = chartsInfo.getxAxis();
        Map<String,List<Object>> seriesMap = chartsInfo.getSeries();
        Boolean loadData = false;
        for (int i = 0; i < chartsLegends.length; i++)
        {
            String chart = chartsLegends[i];
            List<Object> series = new ArrayList<Object>();
            if (EChartsDataType.MAP.getV().equals(component.get("seriesDataType")))
            {
                for (Map<String,Object> map : businessData)
                {
                    SeriesData seriesData = new SeriesData();
                    seriesData.setName(String.valueOf(map.get(xAxisColumn)));
                    seriesData.setValue(NumberUtil.numberFormat(map.get(chart),Integer.parseInt(decimal[i].trim())));
                    series.add(seriesData);
                }
            }
            else if (EChartsDataType.VALUE.getV().equals(component.get("seriesDataType")))
            {
                
                for (Map<String,Object> map : businessData)
                {
                    if ((Boolean)component.get("xAxis") && !loadData)
                    {
                        xAxis.add(map.get(xAxisColumn));
                    }
                    series.add(NumberUtil.numberFormat(map.get(chart),Integer.parseInt(decimal[i].trim())));
                }
                loadData = true;
            }
            seriesMap.put(chart, series);
        }
        // 此行代码不要取消，回设规整显示
        chartsInfo.setSeries(seriesMap);
        return chartsInfo;
    }


    /** 
     * 方法名: initComponent
     * 描述：<初始化Echarts报表需要的数据结构>
     * @param echartsType
     * @return Map<String,Boolean>
     *
     */
    private static Map<String, Object> initComponent(String echartsType) 
    {
        Map<String, Object> componentMap = new HashMap<String, Object>();
        // X轴是否需要封装数据
        componentMap.put("xAxis", true);
        // data 的数据类型，默认为数组[1,2,3,4] 类型
        componentMap.put("seriesDataType", EChartsDataType.VALUE.getV());
        EChartsType ecType = EChartsType.initEChartsType(echartsType);
        switch (ecType) 
        {
            case ECHARTS_LINE:
                componentMap.put("xAxis", true);
                componentMap.put("seriesDataType", EChartsDataType.VALUE.getV());
                break;
            case ECHARTS_BAR:
                componentMap.put("xAxis", true);
                componentMap.put("seriesDataType", EChartsDataType.VALUE.getV());
                break;
            case ECHARTS_PIE:
                componentMap.put("xAxis", false);
                componentMap.put("seriesDataType", EChartsDataType.MAP.getV());
                break;
            case ECHARTS_SCATTER:
                break;
            case ECHARTS_EFFECTSCATTER:
                break;
            case ECHARTS_RADAR:
                componentMap.put("xAxis", false);
                componentMap.put("seriesDataType", EChartsDataType.MAP.getV());
                break;
            case ECHARTS_TREEMAP:
                break;
            case ECHARTS_BOXPLOT:
                componentMap.put("xAxis", true);
                componentMap.put("seriesDataType", EChartsDataType.VALUE.getV());
                break;
            case ECHARTS_CANDLESTICK:
                componentMap.put("xAxis", true);
                componentMap.put("seriesDataType", EChartsDataType.VALUE.getV());
                break;
            case ECHARTS_HEATMAP:
                break;
            case ECHARTS_MAP:
                break;
            case ECHARTS_PARALLEL:
                break;
            case ECHARTS_LINES:
                componentMap.put("xAxis", false);
                componentMap.put("seriesDataType", EChartsDataType.MAP.getV());
                break;
            case ECHARTS_GRAPH:
                break;
            case ECHARTS_SANKEY:
                break;
            case ECHARTS_FUNNEL:
                componentMap.put("xAxis", false);
                componentMap.put("seriesDataType", EChartsDataType.MAP.getV());
                break;
            case ECHARTS_GAUGE:
                componentMap.put("xAxis", false);
                componentMap.put("seriesDataType", EChartsDataType.MAP.getV());
                break;
            default:
                break;
        }
        return componentMap;
    }

    
   /**
    * 
    * 方法名: dealSortListMap
    * 描述：对查询结果进行字段排序
    * @param dataList 排序集合
    * @param sortType 排序类型  false 升序 true 降序 
    * @param fieldName 需要升降序的字段
    * @return List<Map<String,Object>> 
    *
    */
    public static List<Map<String, Object>> dealSortListMap(
        List<Map<String, Object>> dataList, Boolean sortType, String fieldName)
    {
        if (dataList == null || dataList.isEmpty())
        {
            return dataList;
        }
        Comparator<Map<String, Object>> comparator = new MapComparator(fieldName,sortType);
        Collections.sort(dataList, comparator);
        return  dataList;
    }
   
}
