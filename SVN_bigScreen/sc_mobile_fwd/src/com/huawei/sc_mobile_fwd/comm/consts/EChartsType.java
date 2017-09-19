package com.huawei.sc_mobile_fwd.comm.consts;

/**
 * 类名 : EChartsInfo
 * 描述: <Echarts封装图标枚举>
 */
public enum EChartsType
{
    /**
     * 折线图
     */
    ECHARTS_LINE("line"),
    /**
     * 柱状图
     */
    ECHARTS_BAR("bar"),
    /**
     * 饼状图
     */
    ECHARTS_PIE("pie"),
    /**
     * 散点图
     */
    ECHARTS_SCATTER("scatter"),
    /**
     * --
     */
    ECHARTS_EFFECTSCATTER("effectScatter"),
    /**
     * 雷达图
     */
    ECHARTS_RADAR("radar"),
    /**
     * 矩形树图
     */
    ECHARTS_TREEMAP("treemap"),
    /**
     * 箱线图 
     */
    ECHARTS_BOXPLOT("boxplot"),
    /**
     * K线图
     */
    ECHARTS_CANDLESTICK("candlestick"),
    /**
     * --
     */
    ECHARTS_HEATMAP("heatmap"),
    /**
     * --
     */
    ECHARTS_MAP("map"),
    /**
     * 平行坐标
     */
    ECHARTS_PARALLEL("parallel"),
    /**
     * 地图
     */
    ECHARTS_LINES("lines"),
    /**
     * 关系图
     */
    ECHARTS_GRAPH("graph"),
    /**
     * 桑基图
     */
    ECHARTS_SANKEY("sankey"),
    /**
     * 漏斗图
     */
    ECHARTS_FUNNEL("funnel"),
    /**
     * 仪表盘
     */
    ECHARTS_GAUGE("gauge");
    /**
     * 仪表盘
     */
    private String type;
    
    private EChartsType(String type)
    {
        this.type = type;
    }
    
    /**
     * 
     * 方法名: type
     * 描述：<方法的功能和实现思路>
     * @return String
     *
     */
    public String type()
    {
        return this.type;
    }

   /**
    * 
    * 方法名: initEChartsType
    * 描述：<方法的功能和实现思路>
    * @param echartsType Echarts图表类型
    * @return EChartsType
    *
    */
    public static EChartsType initEChartsType(String echartsType)
    {
        switch (echartsType) 
        {
            case "line":
                return ECHARTS_LINE;
            case "bar":
                return ECHARTS_BAR;
            case "pie":
                return ECHARTS_PIE;
            case "scatter":
                return ECHARTS_SCATTER;
            case "effectScatter":
                return ECHARTS_EFFECTSCATTER;
            case "treemap":
                return ECHARTS_TREEMAP;
            case "boxplot":
                return ECHARTS_BOXPLOT;
            case "candlestick":
                return ECHARTS_CANDLESTICK;
            case "heatmap":
                return ECHARTS_HEATMAP;
            case "map":
                return ECHARTS_MAP;
            case "parallel":
                return ECHARTS_PARALLEL;
            case "lines":
                return ECHARTS_LINES;
            case "graph":
                return ECHARTS_GRAPH;
            case "sankey":
                return ECHARTS_SANKEY;
            case "funnel":
                return ECHARTS_FUNNEL;
            case "gauge":
                return ECHARTS_GAUGE;
            case "radar":
                return ECHARTS_RADAR;
            default:
                break;
        }
        return null;
    }


    
}
