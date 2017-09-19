package com.huawei.sc_mobile_fwd.comm.bean;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * 类名 : OrderedProperties
 * 描述: 有顺序的Properties
 */
public class OrderedProperties extends Properties
{

	/**
	 * serialVersionUID
	 */
    private static final long serialVersionUID = -1701760603607926143L;

    /**
     * keys
     */
    private final LinkedHashSet<Object> keys = new LinkedHashSet<Object>();
 
    /**
     * 
     * 方法名: keys
     * 描述：获取文件中所有的key
     *
     * @return Enumeration<Object>
     */
    public Enumeration<Object> keys()
    {

        return Collections.<Object> enumeration(keys);

    }
 

    /**
     * 
     * 方法名: put
     * 描述：设置值
     * @param key key
     * @param value value
     * @return Object
     *
     */
    public Object put(Object key, Object value)
    {

        keys.add(key);

        return super.put(key, value);

    }
 
    /**
     * 
     * 方法名: keySet
     * 描述：所有的key
     * @return Set<Object>
     *
     */
    public Set<Object> keySet()
    {

        return keys;

    }
 
    /**
     * 
     * 方法名: stringPropertyNames
     * 描述：所有的名称
     * @return Set<String>
     *
     */
    public Set<String> stringPropertyNames()
    {
        Set<String> set = new LinkedHashSet<String>();
        for (Object key : this.keys)
        {
            set.add((String) key);
        }
        return set;
    }

    /**
     * 
     * 方法名: remove
     * 描述：移除项目
     * @param key 项目key
     * @return 移除的对象
     *
     */
    public synchronized Object remove(Object key)
    {
        keys.remove(key);
        return super.remove(key);
    }
    
    /**
     * 清空元素
     */
    public synchronized void clear()
    {
        keys.clear();
    }
}
