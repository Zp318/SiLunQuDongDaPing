package com.huawei.sc_mobile_fwd.comm.util;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

/**
 * 
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class MapComparator implements
        Comparator<Map<String, Object>>
{
	/**
	 * key
	 */
    private String key;
    
    /**
     * isDesc
     */
    private boolean isDesc = false;
    
    /**
     * Map排序用
     * @param key 键
     */
    public MapComparator(String key)
    {
        this.key = key;
    }
    
    /**
     * Map排序用
     * @param key 键
     * @param isDesc 倒序
     */
    public MapComparator(String key, boolean isDesc)
    {
        this.key = key;
        this.isDesc = isDesc;
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param map1 
     * @param map2 
     * @return int sdgr
     * @see [类、类#方法、类#成员]
     */
    public int compare(Map<String, Object> map1, Map<String, Object> map2)
    {
        BigDecimal val1 = new BigDecimal(String.valueOf(map1.get(key)));
        BigDecimal val2 = new BigDecimal(String.valueOf(map2.get(key)));
        return isDesc ? val2.compareTo(val1) : val1.compareTo(val2);
    }
}
