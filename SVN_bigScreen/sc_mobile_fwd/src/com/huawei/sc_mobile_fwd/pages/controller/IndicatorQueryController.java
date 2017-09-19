package com.huawei.sc_mobile_fwd.pages.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.huawei.sc_mobile_fwd.comm.controller.CommonController;
import com.huawei.sc_mobile_fwd.pages.model.ResponseObj;
import com.huawei.sc_mobile_fwd.pages.service.IndicatorQueryService;

/**
 * 指标查询控制类
 * @author SF2011 2017年5月16日
 */
@Controller
@RequestMapping({"query"})
public class IndicatorQueryController extends CommonController
{
    /**
     * 日志器
     */
    private static final Logger logger = LoggerFactory.getLogger(IndicatorQueryController.class);
    
    /**
     * 指标查询服务类
     */
    @Autowired
    private IndicatorQueryService indicatorQueryService;
    
    /**
     * 指标查询接口
     * @param request 请求
     * @param response 相应
     * @return 首页面
     */
    @RequestMapping({"/indicators.action"})
    public void queryIndicators(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        logger.info("[sc_mobile_fwd]: IndicatorQueryController.queryIndicators begin");
        response.setContentType("text/json;charset=UTF-8");
        
        //获取请求参数
        String indicatorStr = request.getParameter("indicators");
        List<String> indicators = JSON.parseArray(indicatorStr, String.class);
        logger.info("[sc_mobile_fwd]: IndicatorQueryController.queryIndicators.Indicators={}", indicators);
        
        ResponseObj respObj = indicatorQueryService.queryIndicators(indicators);
        response.getWriter().write(JSON.toJSONString(respObj));
        logger.info("[sc_mobile_fwd]: IndicatorQueryController.queryIndicators end.RespObj={}", respObj);
    }
}
