package com.huawei.sc_mobile_fwd.comm.util;

/**
 * 转换工具类
 *
 */
public class CastUtil
{

    /**
     * Object 类型强转
     * @param t 对象
     * @param <V> 泛型
     * @return V 强转后的对象
     */
    @SuppressWarnings("unchecked")
    public static <V> V cast(Object t)
    {
        if (t == null)
        {
            return null;
        }
        return (V)t;
        
    }

    /**
     * 将对象强转为Long
     * @param t 对象
     * @return Long类型对象
     */
    public static Long parseLong(Object t)
    {
        Long l = 0L;
        if (t == null)
        {
            return l;
        }
        if (t instanceof Integer)
        {
            int temp = (Integer)t;
            l = (long) temp;
        }
        else if (t instanceof String)
        {
            l = Long.parseLong((String)t);
        }
        else if (t instanceof Long)
        {
            l = (Long)t;
        }
        return l;
    }
}
