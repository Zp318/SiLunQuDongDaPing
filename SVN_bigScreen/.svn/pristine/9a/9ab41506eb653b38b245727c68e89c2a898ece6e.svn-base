package com.huawei.sc_mobile_fwd.comm.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Freemarker查询SQL生成器
 */
public class Freemark
{
    
    /** logger */
    private static final Logger logger = LoggerFactory.getLogger(Freemark.class);
    
    /** freemarker configuration */
    private Configuration cfg;
    
    /** freemarker template loader */
    private StringTemplateLoader stringTL;
    
    /**
     * 
     * 方法名: getQueryString
     * 描述：<方法的功能和实现思路>
     * @param queryName 
     * @param queryTemplate 
     * @param conditions 
     * @return String
     * @throws IOException 
     * @throws TemplateException String 
     *
     */
    public String getQueryString(String queryName, String queryTemplate, Map<String, Object> conditions)
        throws IOException, TemplateException
    {
        if (queryTemplate == null)
        {
            logger.error("查询模板不存在");
        }
        cfg = new Configuration();
        stringTL = new StringTemplateLoader();
        stringTL.putTemplate(queryName, queryTemplate);
        cfg.setTemplateLoader(stringTL);
        Template temp = cfg.getTemplate(queryName);
        StringWriter queryStringWriter = new StringWriter();
        temp.process(conditions, queryStringWriter);
        String queryString = queryStringWriter.toString();
        return queryString == null ? null : queryString.trim();
    }
}