package com.huawei.sc_mobile_fwd.comm;

import java.math.BigDecimal;
import java.util.Comparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huawei.sc_mobile_fwd.comm.bean.SeriesData;

/**
 * 
 * 类名 : ObjectComparator
 * 描述: 对中间件数据进行比较
 * 
 */
public class ObjectComparator implements Comparator<Object> 
{
    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(ObjectComparator.class);
    
    @Override
    public int compare(Object num, Object numTwo)
    {
        try
        {
            if (num instanceof Long)
            {
                Long numOne = (Long) num;
                Long numT = (Long) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof BigDecimal)
            {
                BigDecimal numOne = (BigDecimal) num;
                BigDecimal numT = (BigDecimal) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof Integer)
            {
                Integer numOne = (Integer) num;
                Integer numT = (Integer) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof Float)
            {
                Float numOne = (Float) num;
                Float numT = (Float) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof Double)
            {
                Double numOne = (Double) num;
                Double numT = (Double) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof Short)
            {
                Short numOne = (Short) num;
                Short numT = (Short) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof Byte)
            {
                Byte numOne = (Byte) num;
                Byte numT = (Byte) numTwo;
                return numT.compareTo(numOne);
            }
            else if (num instanceof SeriesData)
            {
                return 0;
            }
            else 
            {
                BigDecimal numOne = new BigDecimal((String)num);
                BigDecimal numT = new BigDecimal((String)numTwo);
                return numT.compareTo(numOne);
            }
        } 
        catch (Exception e)
        {
            logger.error("[sc_mobile_fwd]:  ObjectComparator ClassCastException");
        }
        return 0;
    }

}
