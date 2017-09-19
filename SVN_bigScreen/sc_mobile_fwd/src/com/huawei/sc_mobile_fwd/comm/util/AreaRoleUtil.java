package com.huawei.sc_mobile_fwd.comm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.huawei.seq.SpringContextUtils;


/**
 * 
 * 类名 : AreaRoleUtil
 * 描述: <行政区域权限工具类>
 * 创建日期:  2015年8月17日
 */
public class AreaRoleUtil
{
    /**
     * 行政区域权限：所有区域
     */
    public static final String AREA_TYPE_ALL = "0";

    /**
     * 行政区域权限：部分区域
     */
    public static final String AREA_TYPE_ROLE = "1";

    /**
     * 支持行政区域的开关
     */
    private static final int AREA_ROLE_CONTROL_SWITCHID = 7004;

    
    /**
     * 
     * 方法名: getAreaType
     * 描述：<取得区域权限类型>
     * @param areaRoleList 区域权限取得结果
     * @return String 区域权限类型
     *
     * 创建日期: 2015年8月17日
     */
    public static String getAreaType(List<Map<String, String>> areaRoleList)
    {
        String areaType = AREA_TYPE_ALL;
        if (areaRoleList == null || areaRoleList.size() == 0)
        {
            return areaType;
        }

        // 用户有全部行政区域角色时，用户为全部行政区域
        for (Map<String, String> areaRole : areaRoleList)
        {
            areaType = areaRole.get("areaType");
            if (AREA_TYPE_ALL.equals(areaType))
            {
                break;
            }
        }

        return areaType;
    }
    
    /**
     * 
     * 方法名: getRegionIdList
     * 描述：<取得配置的区域List>
     * @param areaRoleList 区域权限取得结果
     * @return String 配置的区域
     *
     * 创建日期: 2015年8月17日
     */
    public static List<Integer> getRegionIdList(List<Map<String, String>> areaRoleList)
    {
        List<Integer> regionIdList = new ArrayList<Integer>();
        if (areaRoleList == null || areaRoleList.size() == 0)
        {
            return regionIdList;
        }

        for (Map<String, String> areaRole : areaRoleList)
        {
            regionIdList.add(Integer.parseInt(areaRole.get("regionid")));
        }
        return regionIdList;
    }
    
    /**
     * 
     * 方法名: getRoleAreaSwitch
     * 描述：获取配置行政区域权限
     * @return 配置的区域
     * 创建日期: 2015年8月17日
     */
    public static int getRoleAreaSwitch()
    {
        return SpringContextUtils.geSwitchStatusBySwitchId(AREA_ROLE_CONTROL_SWITCHID);
    }
}
