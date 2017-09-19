package com.huawei.sc_mobile_fwd.comm;

import java.util.Map;

import com.huawei.seq.dao.BasicDao;

/**
 * 数据源抽象工厂
 * @author wKF66595
 *
 */
public abstract class DataSourceFactory
{
    
    /**
     * 自定义返回的数据源
     * @param params 自定义参数
     * @return  返回自定义数据源
     */
    public abstract BasicDao getBasicDao(Map<String, String> params);
    
}
