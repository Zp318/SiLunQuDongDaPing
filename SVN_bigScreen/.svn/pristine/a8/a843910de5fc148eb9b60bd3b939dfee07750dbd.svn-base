package com.huawei.sc_mobile_fwd.comm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.huawei.seq.SpringContextUtils;
import com.huawei.seq.tools.PlaintextView;

/**
 * 业务通用控制器
 * 控制器基类，提供控制器使用的常用方法，如处理请求参数等
 * 建议每个业务继承该方法
 * 
 */
public class CommonController
{
    /**
     * 初始化日志
     */
    private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
    
    /** 
     * 方法名: handleModelAndView
     * 描述：将json对象传递给模型视图对象并返回
     * @param json 对象
     * @return 模型视图对象
     */
    protected ModelAndView handleModelAndView(JSONObject json)
    {
        if (null == json)
        {
            throw new IllegalArgumentException("The JSON parameter is null");
        }
        
        ModelAndView mnv = new ModelAndView();
        mnv.setView(new PlaintextView());
        mnv.addObject("json", json.toString());
        
        return mnv;
    }
    
    /** 
     * 方法名: handleModelAndView
     * 描述：将json字符串传递给模型视图对象并返回
     * @param json 对象
     * @return 模型视图对象
     */
    protected ModelAndView handleModelAndView(String json)
    {
        if (null == json)
        {
            return null;
        }
        
        ModelAndView mnv = new ModelAndView();
        mnv.setView(new PlaintextView());
        mnv.addObject("json", json);
        
        return mnv;
    }
    
    /** 
     * 方法名: convertJSONAsMap
     * 描述：将JSON参数转换成Map
     * @param json JSON参数
     * @return Map<String, Object> Map<参数名，参数值>
     *
     */
    @SuppressWarnings("unchecked")
    protected Map<String, Object> convertJSONAsMap(JSONObject json)
    {
        if (null == json)
        {
            return new HashMap<String, Object>();
        }
        
        Map<String, Object> result = new HashMap<String, Object>();
        Iterator< ? > itr = json.entrySet().iterator();
        while (itr.hasNext())
        {
            Object entry = itr.next();
            
            String key = ((Entry<String, Object>)entry).getKey();
            Object value = ((Entry<String, Object>)entry).getValue();
            result.put(key, value);
        }
        
        return result;
    }
    
    /** 
     * 方法名: handleQueryRequest
     * 描述：处理查询请求，默认需要将请求参数处理为JSON对象
     * @param request 请求
     * @return Map<String, Object> Map<参数名，参数值> 
     */
    protected Map<String, Object> handleQueryRequest(HttpServletRequest request)
    {
        
        String contentType = request.getContentType();
        Map<String, Object> paramMap = null;
        
        if (null != contentType && contentType.contains("application/json"))
        {
            // 将请求参数处理为JSON对象
            JSONObject jsonContent = parseJSONContent(request);
            paramMap = convertJSONAsMap(jsonContent);
        }
        else
        {
            // 将请求参数处理为Map<String, String>
            paramMap = convertMap(request.getParameterMap());
        }
        
        if (null == paramMap)
        {
            paramMap = new HashMap<String, Object>();
        }
        
        //获取国际化语言
        String locale = SpringContextUtils.getRequestLanguage(request);
        paramMap.put("locale", locale);
        
        //获取登陆用户
        String userName = SpringContextUtils.getUserName();
        paramMap.put("userName", userName);
        
        String serverName = SpringContextUtils.getDistributedLocalIpAddr(request);
        paramMap.put("serverName", serverName);
        
        String serverPort = SpringContextUtils.getDistributedLocalPort(request);
        paramMap.put("serverPort", serverPort);

        return paramMap;
    }
    
    /**
     * 从Content-Type=application/json的请求中，提取内容，以<code>JSONObject</code>对象返回。
     * 
     * @param request
     *            HTTP请求对象
     * 
     * @return <code>JSONObject</code>，如果任何发生异常，则返回<code>null<code>
     */
    protected JSONObject parseJSONContent(HttpServletRequest request)
    {
        byte[] buffer = new byte[request.getContentLength()];
        
        if (buffer == null || buffer.length == 0)
        {
            return JSONObject.fromObject("{}");
        }
        
        request.getParameterMap();
        InputStream is = null;
        try
        {
            is = request.getInputStream();
            int bufferSize = is.read(buffer);
            logger.info("[sc_mobile_fwd_common]: write buffer size: {}", bufferSize);
            is.close();
            
            String str = new String(buffer, request.getCharacterEncoding());
            
            if (null == str || "".equals(str.trim()))
            {
                str = "{}";
            }
            
            return JSONObject.fromObject(str);
        }
        catch (IOException e)
        {
            logger.error("[sc_mobile_fwd_common]: Read request content failed!");
        }
        finally
        {
            if (null != is)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    logger.error("[sc_mobile_fwd_common]: Close stream failed!");
                }
            }
        }

        return JSONObject.fromObject("{}");
    }
    
    /** 
     * 方法名: convertMap
     * 描述：转换Map格式
     * @param map 参数
     * @return Map<String,String> 新格式的map
     *
     */
    public static Map<String, Object> convertMap(Map<String, String[]> map)
    {
        if (null == map || map.size() == 0)
        {
            return null;
        }
        
        Map<String, Object> newMap = new HashMap<String, Object>();
        for (Map.Entry<String, String[]> entry : map.entrySet())
        {
            String[] value = entry.getValue();
            if (value != null && value.length > 0)
            {
                newMap.put(entry.getKey(), value[0]);
            }
        }
        
        return newMap;
    }
    
    /**
     * 处理dataType=json&req={}这种类型的数据
     * 从HttpServletRequest获取JSON串转换为Map格式
     * @param request http请求参数
     * @return Map<String,Object> 
     */
    public static Map<String, Object> handlerRequestParmasAsMap(
            HttpServletRequest request)
    {
        logger.debug("[sc_mobile_fwd_common]: entering into CommonController.handlerRequestParmasAsMap()");
        Map<String, String[]> paramMap = request.getParameterMap();
        if (null == paramMap)
        {
            return null;
        }
        Map<String, Object> resultValue = new HashMap<String, Object>();
        String locale = SpringContextUtils.getRequestLanguage(request);
        resultValue.put("locale", locale);
        for (Entry<String, String[]> entry : paramMap.entrySet())
        {
            resultValue.put(entry.getKey(), entry.getValue()[0]);
        }
        logger.debug("[sc_mobile_fwd_common]: Sign out CommonController.handlerRequestParmasAsMap()");
        return resultValue;
    }
}
