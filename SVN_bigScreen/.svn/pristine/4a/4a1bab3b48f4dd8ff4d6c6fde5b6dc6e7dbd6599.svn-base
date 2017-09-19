package com.huawei.sc_mobile_fwd.comm.thread;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.huawei.smartcare.dac.sdk.bean.DataRequest;
import com.huawei.sc_mobile_fwd.comm.middleware.MiddlewareService;

/**
 * 
 * 类名 : DataRequestTask
 * 描述: 采用DataRequest创建任务
 */
public class DataRequestTask extends QueryTask
{
    /**
     * json查询对象
     */
    private DataRequest dataRequest;
    
    /**
     * 
     * 描述:构造方法
     * 
     * @param dataRequest json查询对象
     * @param taskName 任务名称
     */
    public DataRequestTask(DataRequest dataRequest,String taskName)
    {
        setTaskName(taskName);
        this.dataRequest = dataRequest;
    }
    
    @Override
    protected List<Map<String, Object>> query()
    {
        return MiddlewareService.query(dataRequest);
    }
    
    @Override
    public String getJson()
    {
        if (super.getJson() == null)
        {
            setJson(JSONObject.toJSONString(dataRequest, true));
        }
        return super.getJson();
    }
    
    @Override
    protected String getQueryJson()
    {
        return null;
    }
}
