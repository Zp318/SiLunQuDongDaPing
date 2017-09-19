package com.huawei.sc_mobile_fwd.comm.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.huawei.sc_mobile_fwd.comm.ObjectComparator;
import com.huawei.sc_mobile_fwd.comm.PropertiesUtil;
import com.huawei.sc_mobile_fwd.comm.bean.SeriesData;

/**
 * 
 * 数字处理工具类
 * 
 */
public class NumberUtil
{

    /**
    * 保留0位小数
    */
    public static final int RATE_NUMBER_ZERO = 0;

    /**
     * 保留1位小数
     */
    public static final int RATE_NUMBER_ONE = 1;

    /**
     * 保留2位小数
     */
    public static final int RATE_NUMBER_TWO = 2;

    /**
     * 保留4位小数
     */
    public static final int RATE_NUMBER_FOUR = 4;
    
    /**
     * 亿
     */
    public static final int NUMBER_WAN = 10000;
    
    /**
     * 亿
     */
    public static final int NUMBER_YI = 100000000;
    
    /**
     * 单位亿
     */
    public static final String WAN_CH = "(万)";
    
    /**
     * 单位万
     */
    public static final String YI_CH = "(亿)";

    /**
     * 数字格式化，保留两位小数
     *
     * @param number 数字字符串
     * @return 格式化后的数字字符串
     */
    public static String numberFormat(Object number)
    {
        return numberFormat(number, RATE_NUMBER_TWO);
    }

    /**
     * 数字格式化
     *
     * @param number 数字字符串
     * @param rateNumber 保留小数位数
     * @return 格式化后的数字字符串
     */
    public static String numberFormat(Object number, int rateNumber)
    {
        if (number == null)
        {
            return "";
        }
        // 对Integer ,Long,Byte 类型的暂时不进行截取
        BigDecimal bigDecimal = null;
        if (number instanceof Integer)
        {
            //bigDecimal = new BigDecimal((Integer)number);
            return String.valueOf(number).toString();
        }
        else if (number instanceof Long)
        {
            return String.valueOf(number).toString();
        }
        else if (number instanceof Byte)
        {
            return String.valueOf(number).toString();
        }
        else if (number instanceof Double)
        {
            bigDecimal = new BigDecimal((Double)number);
        }
        else if (number instanceof String) 
        {
            bigDecimal = new BigDecimal((String)number);
        }
        else
        {
            bigDecimal = (BigDecimal)number;
        }

        // 保留小数
        return bigDecimal.setScale(rateNumber, BigDecimal.ROUND_DOWN).toString();
    }
    
    /**
     * 数字格式化
     *
     * @param number 数字字符串
     * @param rateNumber 四舍五入保留小数位数
     * @return 格式化后的数字字符串
     */
    public static String numberFormat4L5G(Object number, int rateNumber)
    {
        if (number == null)
        {
            return "";
        }
        // 对Integer ,Long,Byte 类型的暂时不进行截取
        BigDecimal bigDecimal = null;
        if (number instanceof Integer)
        {
            //bigDecimal = new BigDecimal((Integer)number);
            return String.valueOf(number).toString();
        }
        else if (number instanceof Long)
        {
            return String.valueOf(number).toString();
        }
        else if (number instanceof Byte)
        {
            return String.valueOf(number).toString();
        }
        else if (number instanceof Double)
        {
            bigDecimal = new BigDecimal((Double)number);
        }
        else if (number instanceof String) 
        {
            bigDecimal = new BigDecimal((String)number);
        }
        else
        {
            bigDecimal = (BigDecimal)number;
        }

        // 保留小数
        return bigDecimal.setScale(rateNumber, BigDecimal.ROUND_HALF_DOWN).toString();
    }

    /**
     * 将数字对象转换成BigDecimal类型
     * @param number 数字
     * @return BigDecimal对象
     * 
     */
    public static BigDecimal castToBigDecimal(Object number)
    {
        BigDecimal bigDecimal = null;
        if (number instanceof Integer)
        {
            bigDecimal = new BigDecimal((Integer)number);
        }
        else if (number instanceof String) 
        {
            bigDecimal = new BigDecimal((String)number);
        }
        else
        {
            bigDecimal = (BigDecimal)number;
        }
        return bigDecimal;
    }
    
    
    /**desc:格式化double 类型数据
     * @param d 数据
     * @param type 保留小数
     * @return double 格式化后的数据
     */
    public static double formateDouble(double d, int type)
    {
        String forPatten = "";
        switch (type)
        {
            case RATE_NUMBER_ONE:
                forPatten = ".#";
                break;
            case RATE_NUMBER_TWO:
                forPatten = ".##";
                break;
            case RATE_NUMBER_FOUR:
                forPatten = ".####";
                break;
            default:
                forPatten = ".##";
                break;
        }

        DecimalFormat df = new DecimalFormat(forPatten); 
        String result = df.format(d);
        return Double.valueOf(result);
    }

    /**
     * 
     * 方法名: castUnit
     * 描述：后台数据转换万，亿的计算
     * @param series 集合参数
     * @return Map<String,Object>
     *
     */
    public static Map<String,Object> castUnit(Map<String,List<Object>> series)
    {
        Map<String, Object> others = new HashMap<String, Object>(); 
        if (series != null && !series.isEmpty())
        {
            Iterator<String> it =  series.keySet().iterator();
            while (it.hasNext())
            {
                String key = it.next();
                List<Object> list = new ArrayList<Object>();
                if (series.get(key) != null && !series.get(key).isEmpty() 
                    && PropertiesUtil.getInt("data_unit.properties", key) == 1) 
                {
                    list.addAll(series.get(key));
                    Collections.sort(list,new ObjectComparator());
                    if (list.get(0) != null && !(list.get(0) instanceof SeriesData))
                    {
                        BigDecimal number = NumberUtil.castToBigDecimal(list.get(0));
                        if (number.compareTo(new BigDecimal(NUMBER_YI)) > 0)
                        {
                            others.put(key,YI_CH);
                        }
                        else if (number.compareTo(new BigDecimal(NUMBER_WAN)) > 0) 
                        {
                            others.put(key, WAN_CH);
                        }
                    }
                }
            }
        }
        return others;
    }
    
    /**
     * 
     * 方法名: divUnit
     * 描述：数据除以单位，进行规整
     * @param list 参数集合
     * @param unitName void
     * @return List<Object> 返回规整后的集合
     */
    public static List<Object> divUnit(List<Object> list, String unitName)
    {
        List<Object> custUnitlist = new ArrayList<Object>(); 
        for (int i = 0; i < list.size(); i++)
        {
            Object obj = list.get(i);
            BigDecimal number = NumberUtil.castToBigDecimal(obj);
            if (WAN_CH.equals(unitName))
            {
                obj = numberFormat(number.divide(new BigDecimal(NUMBER_WAN)));
            }
            else if (YI_CH.equals(unitName))
            {
                obj = numberFormat(number.divide(new BigDecimal(NUMBER_YI)));
            }
            custUnitlist.add(obj);
        }
        return custUnitlist;
    }
}
