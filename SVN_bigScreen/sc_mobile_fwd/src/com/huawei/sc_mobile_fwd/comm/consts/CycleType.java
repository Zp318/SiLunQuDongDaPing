package com.huawei.sc_mobile_fwd.comm.consts;

/**
 * 
 * 类名 : CycleType
 * 描述: 时间粒度
 */
public enum CycleType
{
    /**
     * 5分钟
     */
    MIN5("0", "5MIN"),
    /**
     * 15分钟
     */
    MIN15("1", "15MIN"),
    /**
     * 60分钟
     */
    MIN60("2", "1Hour");
    
	/**
	 * id
	 */
    private String id;
    
    /**
     * name
     */
    private String name;
    
    private CycleType(String id, String name)
    {
        this.id = id;
        this.name = name;
    }
    
    public String getId()
    {
        return id;
    }
    
    public String getName()
    {
        return name;
    }
    
    /**
     * 
     * 方法名: getNameById 描述：获取告警周期名称
     * 
     * @param id 告警周期名称
     * @return String 告警周期名称
     *
     */
    public static String getNameById(String id)
    {
        for (CycleType ratType : CycleType.values())
        {
            if (ratType.getId().equals(id))
            {
                return ratType.getName();
            }
        }
        return MIN5.getName();
    }
}
