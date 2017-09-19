package com.huawei.sc_mobile_fwd.comm.middleware;

import java.util.Arrays;
import java.util.List;

import com.huawei.smartcare.dac.sdk.bean.ColumnType;
import com.huawei.smartcare.dac.sdk.bean.Condition;
import com.huawei.smartcare.dac.sdk.bean.DataRequest;
import com.huawei.smartcare.dac.sdk.bean.OrderBy;
import com.huawei.smartcare.dac.sdk.bean.RelationOperator;
import com.huawei.smartcare.dac.sdk.bean.RequestColumn;
import com.huawei.smartcare.dac.sdk.bean.Style;
import com.huawei.sc_mobile_fwd.comm.SefonConstants;

/**
 * 
 * 类名 : JsonUtil 描述: 对Json请求对象的操作
 */
public class JsonUtil
{
    /**
     * 
     * 方法名: addCondition 描述：添加查询条件
     * 
     * @param request 请求数据
     * @param id 查询条件id
     * @param operator 计算关系
     * @param values 条件取值
     * @param type 条件type
     * @param style 条件style
     */
    public static void addCondition(DataRequest request, String id, RelationOperator operator, String values,
        ColumnType type, Style style)
    {
        Condition condition = new Condition();
        condition.setId(id);
        condition.setOperator(operator);
        if (values != null)
        {
            condition.setValues(Arrays.asList(values.split(",")));
        }
        condition.setType(type);
        condition.setStyle(style);
        request.getConditions().getChilds().add(condition);
    }
    
    /**
     * 
     * 方法名: addCondition 描述：添加查询条件,operator=EQ
     * 
     * @param request 请求数据
     * @param id 查询条件id
     * @param values 条件取值
     * @param type 条件type
     * @param style 条件style
     */
    public static void addCondition(DataRequest request, String id, String values, ColumnType type, Style style)
    {
        addCondition(request, id, RelationOperator.EQ, values, type, style);
    }
    
    /**
     * 
     * 方法名: addStartTime 描述：添加开始时间查询条件
     * 
     * @param request 请求数据
     * @param id 查询条件id
     * @param startTime 开始时间取值
     */
    public static void addStartTime(DataRequest request, String id, long startTime)
    {
        addCondition(request, id, RelationOperator.GE, String.valueOf(startTime), ColumnType.TIME, Style.IDENTIFIER);
    }
    
    /**
     * 
     * 方法名: addEndTime 描述：添加结束时间查询条件
     * 
     * @param request 请求数据
     * @param id 查询条件id
     * @param endTime 结束时间取值
     */
    public static void addEndTime(DataRequest request, String id, long endTime)
    {
        addCondition(request, id, RelationOperator.LT, String.valueOf(endTime), ColumnType.TIME, Style.IDENTIFIER);
    }
    
    /**
     * 
     * 方法名: addStartAndEndTime 描述：添加开始和结束时间
     * 
     * @param request 请求数据
     * @param id 查询条件id
     * @param startTime 开始时间取值
     * @param endTime 结束时间取值
     */
    public static void addStartAndEndTime(DataRequest request, String id, long startTime, long endTime)
    {
        addStartTime(request, id, startTime);
        addEndTime(request, id, endTime);
    }
    
    /**
     * 
     * 方法名: addOrder 描述：添加排序字段
     * 
     * @param request 请求数据
     * @param id 排序字段id
     * @param type 排序字段type
     * @param style 排序字段style
     * @param desc 是否降序
     */
    public static void addOrder(DataRequest request, String id, ColumnType type, Style style, boolean desc)
    {
        List<OrderBy> orders = request.getOrders();
        OrderBy orderBy = new OrderBy();
        orders.add(orderBy);
        orderBy.setId(id);
        orderBy.setType(type);
        orderBy.setStyle(style);
        orderBy.setDescending(desc);
    }
    
    /**
     * 
     * 方法名: addDimension 描述：添加查询维度
     * 
     * @param request 请求数据
     * @param id 维度ID
     */
    public static void addDimension(DataRequest request, String id)
    {
        RequestColumn idColumn = new RequestColumn();
        request.getColumns().add(idColumn);
        idColumn.setId(id);
        idColumn.setAlias(id);
        idColumn.setStyle(Style.IDENTIFIER);
        if (SefonConstants.ID_OF_15MIN.equals(id) || SefonConstants.ID_OF_HOUR.equals(id)
            || SefonConstants.ID_OF_DAY.equals(id))
        {
            idColumn.setType(ColumnType.TIME);
        }
        else
        {
            idColumn.setType(ColumnType.DIMENSION);
            RequestColumn nameColumn = new RequestColumn();
            request.getColumns().add(nameColumn);
            nameColumn.setId(id);
            nameColumn.setAlias(id + "_NAME");
            nameColumn.setStyle(Style.NAME);
            nameColumn.setType(ColumnType.DIMENSION);
        }
    }
}
