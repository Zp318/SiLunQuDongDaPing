package com.huawei.sc_mobile_fwd.comm.util;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;  

/**
 * 数字类型检验类
 *
 */
public class NumberValidationUtils
{  
      
    private static boolean isMatch(String regex, String orginal)
    {
        if (orginal == null || orginal.trim().equals(""))
        {
            return false;  
        }  
        Pattern pattern = Pattern.compile(regex);  
        Matcher isNum = pattern.matcher(orginal);  
        return isNum.matches();  
    }  
  
    /**
     * 正整数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isPositiveInteger(String orginal)
    {
        return isMatch("^\\+{0,1}[1-9]\\d*", orginal);  
    }  

    /**
     * 负整数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isNegativeInteger(String orginal)
    {
        return isMatch("^-[1-9]\\d*", orginal);  
    }  

    /**
     * 整数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isWholeNumber(String orginal)
    {
        return isMatch("[+-]{0,1}0", orginal) || isPositiveInteger(orginal) || isNegativeInteger(orginal);  
    }

    /**
     * 正小数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isPositiveDecimal(String orginal)
    {
        return isMatch("\\+{0,1}[0]\\.[1-9]*|\\+{0,1}[1-9]\\d*\\.\\d*", orginal);  
    }  

    /**
     * 负小数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isNegativeDecimal(String orginal)
    {
        return isMatch("^-[0]\\.[1-9]*|^-[1-9]\\d*\\.\\d*", orginal);  
    }  

    /**
     * 小数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isDecimal(String orginal)
    {
        return isMatch("[-+]{0,1}\\d+\\.\\d*|[-+]{0,1}\\d*\\.\\d+", orginal);  
    }  

    /**
     * 实数
     * @param orginal 数字字符串
     * @return boolean 判断结果
     */
    public static boolean isRealNumber(String orginal)
    {
        return isWholeNumber(orginal) || isDecimal(orginal);  
    }  
  
}