package com.huawei.sc_mobile_fwd.comm.consts;

/**
 * 导出文件类型
 * 类名 : FileType
 */
public enum FileType
{
    /**
     * excel 2003
     */
    EXCEL("excel"),
    /**
     * csv
     */
    CSV("csv");
    
	/**
	 * type
	 */
    private String type;
    
    private FileType(String type)
    {
        this.type = type;
    }
    
    /**
     * 文件类型
     * 方法名: value
     * 描述：<方法的功能和实现思路>
     * @return String 文件类型
     */
    public String value()
    {
        return this.type;
    }
    
}
