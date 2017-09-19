package com.huawei.sc_mobile_fwd.pages.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.huawei.sc_mobile_fwd.comm.controller.CommonController;

/**
 * 页面初始化控制类
 * @author SF2011 2017年5月16日
 */
@Controller
@RequestMapping({"forword"})
public class PageInitController extends CommonController
{
    /**
     * 日志器
     */
    private static final Logger logger = LoggerFactory.getLogger(PageInitController.class);
    
    /**
     * 客户感知类指标总览
     * @param request 请求
     * @param response 相应
     * @return 客户感知类指标总览页面
     */
    @RequestMapping({"/customerPerception.action"})
    public String initCustomerPerceptionPage()
    {
        logger.info("[sc_mobile_fwd]: PageInitController.initCustomerPerceptionPage()");
        return "/sc_mobile_fwd/perceptionPages";
    }
    
    /**
     * 支撑市场类指标总览
     * @param request 请求
     * @param response 相应
     * @return 支撑市场类指标总览页面
     */
    @RequestMapping({"/marketSupport.action"})
    public String initMarketSupportPage()
    {
    	logger.info("[sc_mobile_fwd]: PageInitController.initMarketSupportPage()");
    	return "/sc_mobile_fwd/bracePages";
    }
}
