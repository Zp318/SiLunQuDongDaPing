package com.huawei.sc_mobile_fwd.comm.bean;

/**
 * 类名 : SeriesData
 * 描述: <Echarts Data数据中Bean对象>
 */
public class SeriesData 
{
    /**
     * name
     */
    private String name;
    
    /**
     * value
     */
    private Object value;

    /**
     * 获取name
     * @return 返回 name
     */
    public String getName()
    {
        return name;
    }

    /**
     * 设置name
     * @param name 对name进行赋值
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * 获取value
     * @return 返回 value
     */
    public Object getValue()
    {
        return value;
    }

    /**
     * 设置value
     * @param value 对value进行赋值
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    
    
}
