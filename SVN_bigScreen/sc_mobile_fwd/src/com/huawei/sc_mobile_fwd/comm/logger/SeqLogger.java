package com.huawei.sc_mobile_fwd.comm.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 类名 : SeqLogger
 * 描述: <对类的描述，如功能、主要算法、内部各部分之间的关系>
 */
public class SeqLogger
{
	/**
	 * LEVEL
	 */
    private static final int LEVEL = 3;

    /**
     * logger
     */
    private Logger logger;
    
    /**
     * prefix
     */
    private String prefix = "[sc_mobile_fwd]:";
    
    
    /**
     * 
     * 描述:
     */
    public SeqLogger()
    {
        Class< ? > clz = getLogClass();
        logger = LoggerFactory.getLogger(clz);
    }
    
    /**
     * 
     * 描述:
     * 
     * @param prefix 日志前缀
     */
    public SeqLogger(String prefix)
    {
        this();
        this.prefix = prefix;
    }
    
    public static SeqLogger getLogger()
    {
        return new SeqLogger();
    }
    
    /**
     * 方法描述：开始执行方法
     *
     */
    public void entryMethod()
    {
        if (logger.isInfoEnabled())
        {
            logger.info(prefix + "entry " + getInvokeMethod());
        }
    }
    
    /**
     * 方法描述：方法执行完成
     *
     */
    public void exitMethod()
    {
        if (logger.isInfoEnabled())
        {
            logger.info(prefix + "exit " + getInvokeMethod());
        }
    }
    
    /**
     * 
     * 方法名: error
     * 描述：<方法的功能和实现思路>
     * @param log void
     */
    public void error(String log)
    {
        logger.error(prefix + log + " | " + getInvokeMethod());
    }
    
    /**
     * 
     * 方法名: info
     * 描述：<方法的功能和实现思路>
     * @param log void
     */
    public void info(String log)
    {
        if (logger.isInfoEnabled())
        {
            logger.info(prefix + log + " | " + getInvokeMethod());
        }
    }
    
    private String getInvokeMethod()
    {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        StackTraceElement ste = (StackTraceElement)stes[LEVEL];
        StringBuilder builder = new StringBuilder();
        builder.append(ste.getClassName())
            .append(".")
            .append(ste.getMethodName())
            .append("(")
            .append(ste.getFileName())
            .append(":")
            .append(ste.getLineNumber())
            .append(")");
        return builder.toString();
    }
    
    /**
     * 
     * 方法名: getLogClass
     * 描述：<方法的功能和实现思路>
     * @return Class<?>
     */
    private Class< ? > getLogClass()
    {
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        StackTraceElement ste = (StackTraceElement)stes[stes.length - 1];
        try
        {
            return Class.forName(ste.getClassName());
        }
        catch (ClassNotFoundException e)
        {
            return null;
        }
    }
    
}
