package com.huawei.sc_mobile_fwd.comm.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.huawei.seq.SpringContextUtils;
import com.huawei.sc_mobile_fwd.comm.logger.SeqLogger;

/**
 * 
 * 类名 : QueryThreadPool
 * 描述: 多线程执行查询任务（只有一个查询任务时，直接使用当前线程查询）
 */
public class QueryThreadPool
{
	/**
	 * logger
	 */
    private static SeqLogger logger = new SeqLogger();

    private static Future<List<Map<String, Object>>> submit(Callable<List<Map<String, Object>>> task)
    {
        ThreadPoolTaskExecutor es =  (ThreadPoolTaskExecutor)SpringContextUtils.getBean("bigthreadPool");
        Future<List<Map<String, Object>>> future = es.submit(task);
        return future;
    }
    
    /**
     * 方法描述：执行查询任务
     * @param tasks 查询任务
     * @return 查询结果，任务名称为key，任务结果为value
     */
    public static Map<String, List<Map<String, Object>>> query(List<QueryTask> tasks)
    {
        Map<String, List<Map<String, Object>>> result = new HashMap<String, List<Map<String, Object>>>();
        if (tasks.size() == 1)
        { // 如果只有一个任务，直接使用当前线程
            QueryTask task = tasks.get(0);
            result.put(task.getTaskName(), task.call());
        }
        else
        {
            List<Future<List<Map<String, Object>>>> futures =
                new ArrayList<Future<List<Map<String, Object>>>>(tasks.size());
            for (QueryTask task : tasks)
            {
                futures.add(submit(task));
            }
            // for(Future<List<Map<String, Object>>> future : futures){
            for (int i = 0; i < futures.size(); i++)
            {
                Future<List<Map<String, Object>>> future = futures.get(i);
                try
                {
                    result.put(tasks.get(i).getTaskName(), future.get());
                }
                catch (InterruptedException e)
                {
                    logger.error("InterruptedException");
                }
                catch (ExecutionException e)
                {
                    logger.error("ExecutionException");
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * 方法名: query
     * 描述：单任务查询
     * @param task 
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> query(QueryTask task)
    {
        return task.call();
    }
    
}
