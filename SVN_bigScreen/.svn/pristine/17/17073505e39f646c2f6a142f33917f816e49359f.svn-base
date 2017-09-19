package com.huawei.sc_mobile_fwd.comm.thread;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.huawei.seq.WebCoreTools;
import com.huawei.sc_mobile_fwd.comm.FileUtil;
import com.huawei.sc_mobile_fwd.comm.logger.SeqLogger;
import com.huawei.sc_mobile_fwd.comm.middleware.MiddlewareService;
import com.huawei.sc_mobile_fwd.comm.middleware.ParamUtils;
import com.huawei.sc_mobile_fwd.comm.util.TestDataFromJson;
import com.huawei.sc_mobile_fwd.comm.config.DacConfig;

/**
 * 
 * 类名 : QueryTask 
 * 描述: 查询任务
 */
public class QueryTask implements Callable<List<Map<String, Object>>>
{
	/**
	 * logger
	 */
    private static SeqLogger logger = new SeqLogger();

    /**
     * 任务名称
     */
    private String taskName;
    
    /**
     * json文件名
     */
    private String jsonName;
    
    
    /**
     * json需替换的参数
     */
    private Map<String, String> params;
    
    /**
     * 查询中间件的json字符串
     */
    private String json;
    
    /**
     * 
     * 描述:构造方法
     */
    public QueryTask()
    {
    }
    /**
     * 
     * 描述: 构造方法
     * @param jsonName 
     */
    public QueryTask(String jsonName)
    {
        this.jsonName = jsonName;
        this.taskName = jsonName;
    }
    
    /**
     * 
     * 描述: 构造方法
     * @param jsonName json文件名称
     * @param params 替换参数
     */
    public QueryTask(String jsonName, Map<String, String> params)
    {
        this(jsonName);
        this.params = params;
    }
    
    /**
     * 
     * 描述: 构造方法
     * @param jsonName json文件名称
     * @param taskName 任务名称
     * @param params 替换参数
     */
    public QueryTask(String jsonName, String taskName, Map<String, String> params)
    {
        this.jsonName = jsonName;
        this.taskName = taskName;
        this.params = params;
    }
    
    protected void setTaskName(String taskName)
    {
        this.taskName = taskName;
    }
    protected String getTaskName()
    {
        return taskName;
    }
    protected void setJson(String json)
    {
        this.json = json;
    }
    protected String getJson()
    {
        return json;
    }
    /**
     * 
     * 方法名: init
     * 描述：初始化生成json字符串
     * @return QueryTask
     */
    public QueryTask init()
    {
        getQueryJson();
        return this;
    }
    
    /**
     * 
     * 方法名: getQueryJson
     * 描述：<方法的功能和实现思路>
     * @return String
     */
    protected String getQueryJson()
    {
        if (json == null)
        {
            json = FileUtil.readFileContent(jsonName + ".json");
            json = ParamUtils.getJsonByTemp(json, (Map<String, String>)params);
        }
        return json;
    }
    
    /**
     * 
     * 方法名: query
     * 描述：<方法的功能和实现思路>
     * @return List<Map<String,Object>>
     */
    protected List<Map<String, Object>> query()
    {
        return MiddlewareService.queryBySimpleDataJson(json);
    }
    
    /**
     * 
     * 方法名: call
     * 描述：<方法的功能和实现思路>
     * @return 查询结果
     */
    @Override
    public List<Map<String, Object>> call()
    {
        logger.entryMethod();
        long start = System.currentTimeMillis();
        getQueryJson();
        logger.error("[sc_mobile_fwd] queryJson:" + getQueryJson());
        if (DacConfig.getInt("SAVE_JSON") == 1)
        {
            writeFile();
        }
        List<Map<String, Object>> result = null;
        if (DacConfig.getInt("QUERY_FROM_DAC") == 0)
        {
            result = TestDataFromJson.getData(getJson(),taskName);
        }
        else
        {
            result = query(); 
        }
        long end = System.currentTimeMillis();
        logger.info("query task " + taskName + " cost " + (end - start) + "ms");
        logger.exitMethod();
        return result;
    }
    
    /**
     * 
     * 方法名: writeFile
     * 描述： 写调试文件
     */
    protected void writeFile()
    {
        String path = DacConfig.get("SAVE_JSON_PATH");
        if (path == null || path.isEmpty())
        {
            path = WebCoreTools.getWebRootPath() + "WEB-INF/tmp/";
        }
        File file = new File(path, taskName + ".json");
        if (!file.getParentFile().exists())
        {
            file.getParentFile().mkdirs();
        }
        
        FileOutputStream fo = null;
        try
        {
            fo = new FileOutputStream(file);
            fo.write(getJson().getBytes());
        }
        catch (IOException e)
        {
            logger.error(e.toString());
        }
        finally
        {
            if (fo != null)
            {
                try
                {
                    fo.close();
                }
                catch (IOException e)
                {
                    logger.error(e.toString());
                }
            }
        }
    }
}
