package com.huawei.sc_mobile_fwd.comm;

import java.util.ArrayList;

/**
 * 字符串工具类
 * 
 */
public final class StrUtil
{
    /**
     * 空字符串""
     */
    public static final String EMPTY = "";
    
    private StrUtil()
    {
    }
    
    /**
     * 检查字符串是否是空的
     * @param str 待检查字符串
     * @return 如果是空则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(String str)
    {
        return (null == str) || (0 == str.length());
    }
    
    /**
     * 检查字符串是否是空的
     * @param obj 待检查字符串
     * @return 如果是空则返回true，否则返回false
     */
    public static boolean isNullOrEmpty(Object obj)
    {
        if (obj == null)
        {
            return true;
        } 
        if (obj instanceof String)
        {
            return 0 == ((String)obj).length();
        }
        return false;
    }
    
    /**
     * 检查字符串去空格后是否是空的
     * @param str 待检查字符串
     * @return 去空格后如果是空则返回true，否则返回false
     */
    public static boolean isNullOrTrimedEmpty(String str)
    {
        return (isNullOrEmpty(str)) || (0 == str.trim().length());
    }
    
    /** 用于多个字符拼接
     * @param strs  多个源字符串
     * @return String 拼接后的字符串
     */
    public static String combineStr(String... strs)
    {
        if (null == strs || strs.length == 0)
        {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (String str : strs)
        {
            sBuffer.append(str);
        }
        return sBuffer.toString();
    }
    
    /** 
     * 组合对象，如果是数组会拷贝地址，请使用combineStrArr
     * @param objs  对象列表
     * @return String 拼接后的字符串
     */
    public static String combineStr(Object... objs)
    {
        if (null == objs || objs.length == 0)
        {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (Object obj : objs)
        {
            sBuffer.append(obj);
        }
        return sBuffer.toString();
    }
    
    /** 
     * 组合对象，支持数组，目前支持的数组有object,char,byte,short,long
     * @param objs  对象列表
     * @return String 拼接后的字符串
     */
    public static String combineStrArr(Object... objs)
    {
        if (null == objs || objs.length == 0)
        {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (Object obj : objs)
        {
            if (obj instanceof Object[] || obj instanceof int[]
                    || obj instanceof char[] || obj instanceof short[]
                    || obj instanceof byte[] || obj instanceof long[])
            {
                sBuffer.append(combineStrArr());
            }
            sBuffer.append(obj);
        }
        return sBuffer.toString();
    }
    
    /** 
     * 多个字符串拼接，支持整数
     * @param nums  int数字
     * 
     * @return String 拼接后的字符串
     */
    public static String combineStr(int... nums)
    {
        if (null == nums || nums.length == 0)
        {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (int num : nums)
        {
            sBuffer.append(num);
        }
        return sBuffer.toString();
    }
    
    /** 
     * 组合对象拼接成字符串（不支持数组），中间用指定字符分隔
     * @param sep  分隔符
     * @param objs 组合对象
     * @return combined 拼接后字符串
     */
    public static String combineObjWithSep(String sep, Object... objs)
    {
        if (null == objs || objs.length == 0)
        {
            return null;
        }
        StringBuffer sBuffer = new StringBuffer();
        for (Object obj : objs)
        {
            sBuffer.append(obj);
            sBuffer.append(sep);
        }
        return sBuffer.toString();
    }
    
    /**
     * 去掉字符串里面的空白字符，包括换行
     * 
     * @param str   输入字符串
     * @return 处理后的字符串
     */
    public static String stripWhitespace(String str)
    {
        char[] from = str.toCharArray();
        char[] to = new char[from.length];
        int f = 0;
        int t = 0;
        int last = from.length;
        for (f = 0; f < last; f++)
        {
            char c = from[f];
            if (!(c == ' ' || c == '\t' || c == '\n' || c == '\r'))
            {
                to[t++] = c;
            }
        }
        return new String(to, 0, t);
    }
    
    /**
     * 根据字符串拆分字符串
     * @param src   源数据
     * @param split 分隔符
     * @param count 分隔符出现的次数
     * @return String[] 拆分后的字符串
     */
    public static String[] split(String src, String split, int count)
    {
        String[] strings = new String[count + 1];
        int lastIndex = 0;
        int currIndex = 0;
        for (int i = 0; i < count; i++)
        {
            currIndex = src.indexOf(split, lastIndex);
            strings[i] = src.substring(lastIndex, currIndex);
            lastIndex = currIndex + split.length();
        }
        strings[count] = src.substring(currIndex + split.length(), src.length());
        return strings;
    }
    
    /** 
     * 根据字符串拆分字符串
     * @param src   源数据
     * @param split 分隔符
     * 
     * @return String[] 拆分后的字符串
     */
    public static String[] split(String src, String split)
    {
        ArrayList<String> list = new ArrayList<String>();
        
        int splitLength = split.length();
        int charLength = src.length();
        int lastSplitIndex = 0;
        int curIndex = 0;
        
        while (true)
        {
            curIndex = src.indexOf(split, lastSplitIndex);
            if (curIndex >= 0 || curIndex == charLength - 1)
            {
                list.add(src.substring(lastSplitIndex, curIndex));
            }
            else
            {
                list.add(src.substring(lastSplitIndex, charLength));
                break;
            }
            lastSplitIndex = curIndex + splitLength;
        }
        
        String[] result = new String[list.size()];
        list.toArray(result);
        return result;
    }
    
    /**
     * 返回格式化消息
     * @param msg 消息
     * @return 返回格式化消息
     */
    public static String getNotice(String msg)
    {
        return "\n<<<<<<<<<<<<<<<<<<<<<<<<<" + msg
                + ">>>>>>>>>>>>>>>>>>>>>>>>>>";
    }
}
