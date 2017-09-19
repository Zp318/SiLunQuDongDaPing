package com.huawei.sc_mobile_fwd.pages.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpStatus;

/**
 * 响应实体类
 * @author SF2011
 */
public class ResponseObj
{
    /** 状态响应码 */
    private int code = HttpStatus.SC_OK;

    /** 起始时间*/
    private Long starttime;
    
    /** 起始时间*/
    private Long endtime;
    
    /** 数据内容 */
    private List<Object> data = new ArrayList<Object>();

    public ResponseObj()
	{
	}

	public ResponseObj(int code)
	{
		this.code = code;
	}

	public int getCode()
	{
		return code;
	}

	public void setCode(int code)
	{
		this.code = code;
	}

	public Long getStarttime()
	{
		return starttime;
	}

	public void setStarttime(Long starttime)
	{
		this.starttime = starttime;
	}

	public Long getEndtime()
	{
		return endtime;
	}

	public void setEndtime(Long endtime)
	{
		this.endtime = endtime;
	}

	public List<Object> getData()
	{
		return data;
	}

	public void setData(List<Object> data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "ResponseObj [code=" + code + ", starttime=" + starttime + ", endtime=" + endtime + ", data=" + data
				+ "]";
	}
}
