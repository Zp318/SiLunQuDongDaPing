package com.huawei.sc_mobile_fwd.pages.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 指标描述类
 * @author SF2011 2017年5月16日
 */
public class Indicator
{
	/** 指标标题*/
	private String title = "";
	
	/** 指标说明*/
	private String legend = "";
	
	/** 指标单位*/
	private String unit = "";
	
	/** 指标数据*/
    private List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

	public Indicator()
	{
	}

	public Indicator(String title, String legend, String unit, List<Map<String, Object>> data)
	{
		this.title = title;
		this.legend = legend;
		this.unit = unit;
		this.data = data;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLegend()
	{
		return legend;
	}

	public void setLegend(String legend)
	{
		this.legend = legend;
	}

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public List<Map<String, Object>> getData()
	{
		return data;
	}

	public void setData(List<Map<String, Object>> data)
	{
		this.data = data;
	}

	@Override
	public String toString()
	{
		return "Indicator [title=" + title + ", legend=" + legend + ", unit=" + unit + ", data=" + data + "]";
	}
}
