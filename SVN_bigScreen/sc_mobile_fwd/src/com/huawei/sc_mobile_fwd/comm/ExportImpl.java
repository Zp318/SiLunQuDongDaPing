package com.huawei.sc_mobile_fwd.comm;

import com.huawei.seq.SpringContextUtils;
import com.huawei.seq.intf.Export;

/**
 * 终端分析业务导出实现类
 * 
 */
public class ExportImpl implements Export
{
    
    /**
     * <p>列头国际化</p>
     * @param title  列头
     * @param locale  国际化
     * @return String  国际化列图
     */
    @Override
    public String getTitle(String title, String locale)
    {
        if (null == title)
        {
            return "";
        }
        String msg = PropertiesUtil.getProperties(title);
        return SpringContextUtils.getI18N().getI18N(msg, locale);
    }
    
    /**
     * <p>查询数据</p>
     * @param title 列头
     * @param value 传入参数
     * @param locale 国际化
     * @return String  返回结果
     */
    @Override
    public String getValue(String title, Object value, String locale)
    {
        if (null == value)
        {
            return "";
        }
        
        return value.toString();
    }
    
}
