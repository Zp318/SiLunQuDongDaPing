package com.huawei.sc_mobile_fwd.comm.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;

import com.huawei.sc_mobile_fwd.comm.SefonConstants;

/**
 * 
 * 类名 : TableUtil 描述: <对类的描述，如功能、主要算法、内部各部分之间的关系> 创建人: 创建日期: 2016年7月29日
 * 
 * 修改历史 修改日期: <修改日期，格式：YYYY-MM-DD> 修改人: <姓名/工号> 修改原因/修改内容: <修改原因描述> <问题单号： DTSXXXXXXXX>
 */
public class TableUtil
{
    /**
     * 
     * 方法名: getTableSuffix 描述：获取iq库月表分表后缀
     * 
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return Set<Integer> iq库月表分表后缀
     *        
     */
    public static Set<Integer> getMonthTableSuffix(String startTime, String endTime)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
        Calendar zero = Calendar.getInstance();
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        
        try
        {
            zero.setTime(sdf.parse("1970-01-01"));
            start.setTime(sdf.parse(startTime));
            end.setTime(sdf.parse(endTime));
        }
        catch (ParseException e)
        {
            // TODO Auto-generated catch block
        }
        
        int startDiffMonth = (start.get(Calendar.YEAR) - zero.get(Calendar.YEAR)) * SefonConstants.MONTH_OF_YEAR
            + start.get(Calendar.MONTH) - zero.get(Calendar.MONTH);
        int endDiffMonth = (end.get(Calendar.YEAR) - zero.get(Calendar.YEAR)) * SefonConstants.MONTH_OF_YEAR
            + end.get(Calendar.MONTH) - zero.get(Calendar.MONTH);
            
        Set<Integer> suffix = new HashSet<Integer>();
        for (int i = startDiffMonth; i <= endDiffMonth; i++)
        {
            suffix.add(i);
        }
        return suffix;
    }
}
