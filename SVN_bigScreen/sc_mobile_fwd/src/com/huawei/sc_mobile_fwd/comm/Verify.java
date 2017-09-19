package com.huawei.sc_mobile_fwd.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;

import com.huawei.seq.tools.FlexDataAdapter;
import com.huawei.seq.tools.FlexDataCommand;
import com.huawei.seq.tools.PlaintextView;
import com.huawei.seq.tools.SCException;
import com.huawei.seq.tools.VerifyUtils;


/**
 * 
 * 类名 : VerifyInput
 * 描述:  输入框验证类
 */

public class Verify
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(Verify.class);
    /**
     * 终端品牌标志
     */
    private static final String BRAND_FLAG = "0";
    /**
     * TAC标志
     */
    private static final String TAC_FLAG = "2";
     
    
    
    /**
     * USERNAME,MSISDN,IMSI输入错误提示信息
     * map 对象
     */
    
    private static Map<String, Object> initValue()
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        Map<String, Object> initMap = new HashMap<String, Object>();
        result.add(initMap);
        map.put("UserOrGroupError", result);
        return map;
    }
    
    
    /**
    * 
    * 方法名: getModelAndViewForPlainText
    * 描述：获取简单字符串输出的ModelAndView对象。
    * @param  request 页面传入参数
    * @return ModelAndView 要返回的内容。
    */
    public static ModelAndView getModelAndView(HttpServletRequest request)
    {
        ModelAndView modelAndView = new ModelAndView();
        String data = "";
        //获取参数结合
        try
        {
            FlexDataCommand flexDateCommand = FlexDataAdapter.getCommand(request);
            data = FlexDataAdapter.dataTransform(flexDateCommand,
                        initValue(),
                        request);
               
            modelAndView.setView(new PlaintextView());
            modelAndView.addObject("backXml", data);
            return modelAndView;
        }
        catch (SCException e)
        {
            return modelAndView;
        } 
    }
    
    /**
     * 用户用户群输入MSISDN,IMSI判断
     * @param request 页面传入参数
     * @return boolean 对象
     */
    public static boolean verifyVip(HttpServletRequest request)
    {
        logger.info("[cem_common]: Verify-->verifyVip enter");
        boolean flag = true;
        // 获取客户端请求数据
        FlexDataCommand flexDateCommand;
        try
        {
            flexDateCommand = FlexDataAdapter.getCommand(request);
            Map<String, String> params = flexDateCommand.getCommandmap();
            if (null == params.get("groupId") || "".equals(params.get("groupId")))
            {
                
                if ("USERNAME".equalsIgnoreCase(params.get("isMSISDNorIMSI")))
                {
                    flag = true;
                }
                else if ("MSISDN".equals(params.get("isMSISDNorIMSI")))
                {
                    flag = VerifyUtils.verifyMSISDN(params.get("inputKey")) 
                        || VerifyUtils.verifyMSISDN(params.get("cost")); 
                }
                else if ("IMSI".equals(params.get("isMSISDNorIMSI")))
                {
                    flag = VerifyUtils.verifyIMSI(params.get("inputKey")) || VerifyUtils.verifyIMSI(params.get("cost"));
                }
                else
                {
                    flag = VerifyUtils.verifyCommonDesignation(params.get("inputKey")) 
                        || VerifyUtils.verifyCommonDesignation(params.get("cost"));
                }
            }
           
        }
        catch (SCException e)
        {
            logger.error("[cem_common]: verifyVip error!");
        }
        
        return flag;
    }
    
    /**
     * 终端输入判断
     * @param  request 页面传入参数
     * @return boolean
     */
    public static boolean verifyTerminal(HttpServletRequest request)
    {
        logger.info("[cem_common]: Verify-->verifyTerminal enter");
        // 获取客户端请求数据
        FlexDataCommand flexDateCommand;
        boolean flag = true;
        try
        {
            flexDateCommand = FlexDataAdapter.getCommand(request);
            Map<String, String> params = flexDateCommand.getCommandmap();
            //0标示终端品牌和型号
            if (Verify.BRAND_FLAG.equals(params.get("levelId")))
            {
                if (VerifyUtils.verifyCommonDesignation(params.get("brand"))
                        && VerifyUtils.verifyCommonDesignation(params.get("model")))
                {
                    flag = true;
                }
                else
                {
                    flag = false;
                }
                
            }
            //2标示TAC
            else if (Verify.TAC_FLAG.equals(params.get("levelId")))
            {
                flag = VerifyUtils.verifyTAC(params.get("tac"));
            }
        }
        catch (SCException e)
        {
            logger.error("[cem_common]: verifyTerminal error!");
        }
        
        return flag;
    }
    
    /**
     * 速率分析输入MSISDN,IMSI判断
     * @param commandMap 页面传入参数
     * @return boolean 对象
     */
    
    public static boolean verifyThroughputInput(Map<String, Object> commandMap)
    {
        //默认输入合法
        boolean flag = true;
        
        if ("MSISDN".equals(String.valueOf(commandMap.get("isMsisdnOrImsi"))))
        {
            flag = VerifyUtils.verifyMSISDN(String.valueOf(commandMap.get("cost"))); 
        }
        else
        {
            flag = VerifyUtils.verifyIMSI(String.valueOf(commandMap.get("cost")));
        }
        
        logger.info("Verify--verifyThroughputUser flag: " + flag);
        return flag;
    }
}
