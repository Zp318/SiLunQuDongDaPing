package com.huawei.sc_mobile_fwd.comm.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;

import com.alibaba.druid.util.MapComparator;
import com.alibaba.fastjson.JSON;
import com.huawei.seq.WebCoreTools;
import com.huawei.smartcare.dac.sdk.bean.ColumnType;
import com.huawei.smartcare.dac.sdk.bean.Condition;
import com.huawei.smartcare.dac.sdk.bean.DataRequest;
import com.huawei.smartcare.dac.sdk.bean.OrderBy;
import com.huawei.smartcare.dac.sdk.bean.RequestColumn;
import com.huawei.smartcare.dac.sdk.bean.Style;
import com.huawei.sc_mobile_fwd.comm.StrUtil;
import com.huawei.sc_mobile_fwd.comm.config.DacConfig;

/**
 * 
 * 类名 : TestDataFromJson 描述: 模拟中间件查询返回结果
 */
public class TestDataFromJson
{
	/**
	 * dimMap
	 */
    private static Map<String, String> dimMap = new HashMap<String, String>();
    
    /**
     * CACHE
     */
    private static final Map<String, List<Map<String, Object>>> CACHE =
        new HashMap<String, List<Map<String, Object>>>();
        
    /**
     * 
     * 方法名: getData 描述：返回json查询结果
     * 
     * @param json 
     * @param fileName 
     * @return List<Map<String,Object>>
     */
    public static List<Map<String, Object>> getData(String json, String fileName)
    {
        if (DacConfig.getInt("CACHE_ON") == 1)
        {
            List<Map<String, Object>> result = CACHE.get(json);
            if (result != null)
            {
                return result;
            }
            result = getResult(json, fileName);
            CACHE.put(json, result);
            return result;
        }
        else
        {
            CACHE.clear();
            List<Map<String, Object>> result = getResult(json, fileName);
            return result;
        }
        
    }
    
    private static List<Map<String, Object>> getResult(String json, String fileName)
    {
        File file = WebCoreTools.getConfigFile(fileName + ".csv").getFile();
        // 将json字符串转换为DataRequest对象
        DataRequest dr = JSON.parseObject(json, DataRequest.class);
        if (file != null && file.exists())//从文件中获取数据
        {
            try
            {
                List<Map<String, Object>> result = getDataFromCsv(file);
                orderBy(dr, result);
                return result;
            }
            catch (IOException e)
            {
                e.printStackTrace();
                return new ArrayList<Map<String, Object>>();
            }
        }
        // 返回list结果
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        try
        {
            // 获取查询维度
            List<List<Map<String, Object>>> dimData = getDimData(dr);
            List<Map<String, Object>> dimResult = getDimResult(dimData);
            List<RequestColumn> columns = dr.getColumns();
            int limit = dimResult.size();
            if (dr.getLimit() != null)
            {
                int i = dr.getLimit().intValue();
                limit = limit < i ? limit : i;
            }
            for (int i = 0; i < limit; i++)
            {
                Map<String, Object> row = dimResult.get(i);
                Map<String, Object> map = getDataMap(dr, row, i + 1);
                result.add(map);
            }
            orderBy(dr, result);
            
            writeFile(WebCoreTools.getWebRootPath() + "WEB-INF/tmp/" + fileName + ".csv", columns, result);
        }
        catch (Throwable t)
        {
            t.printStackTrace();
        }
        return result;
    }
    
    private static void orderBy(DataRequest dr, List<Map<String, Object>> result)
    {
        List<OrderBy> orders = dr.getOrders();
        if (dr.getOrders() != null && !orders.isEmpty())
        {
            // 暂时只支持一个排序字段
            OrderBy order = orders.get(0);
            Comparator<Map<String, Object>> comparator =
                new MapComparator<String, Object>(getAliasById(dr.getColumns(), order.getId()), order.isDescending());
            Collections.sort(result, comparator);
        }
    }
    
    private static void writeFile(String file, List<RequestColumn> columns, List<Map<String, Object>> result)
        throws IOException
    {
        BufferedWriter bw = null;
        try
        {
            if (new File(file).exists())
            {
                return;
            }
            File path = new File(file).getParentFile();
            if (!path.exists())
            {
                path.mkdirs();
            }
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            StringBuilder sb = new StringBuilder();
            for (RequestColumn column : columns)
            {
                if (sb.length() > 0)
                {
                    sb.append(",");
                }
                sb.append(column.getAlias());
            }
            bw.write(sb.toString());
            bw.newLine();
            for (Map<String, Object> map : result)
            {
                sb.delete(0, sb.length());
                for (RequestColumn column : columns)
                {
                    if (sb.length() > 0)
                    {
                        sb.append(",");
                    }
                    sb.append(map.get(column.getAlias()));
                }
                bw.write(sb.toString());
                bw.newLine();
            }
        }
        finally
        {
            if (bw != null)
            {
                bw.close();
            }
        }
        
    }
    
    private static List<RequestColumn> getDim(List<RequestColumn> columns)
    {
        List<RequestColumn> list = new ArrayList<RequestColumn>();
        Set<String> set = new HashSet<String>();
        for (RequestColumn column : columns)
        {
            String id = column.getId();
            if ((column.getType() == ColumnType.DIMENSION || column.getType() == ColumnType.TIME) && !set.contains(id))
            {
                set.add(id);
                list.add(column);
            }
        }
        return list;
    }
    
    /**
     * 
     * 方法名: getDimData 描述：只处理一个维度
     * 
     * @param dr 
     * @return List<Object> 
     */
    private static List<List<Map<String, Object>>> getDimData(DataRequest dr)
    {
        List<RequestColumn> dim = getDim(dr.getColumns());
        List<List<Map<String, Object>>> dimData = new ArrayList<>(dim.size());
        Condition conditions = dr.getConditions();
        Collection<Condition> childs = conditions.getChilds();
        //RequestColumn column = null;
        if (dim == null || dim.isEmpty())
        {
            return Collections.emptyList();
        }

        for (RequestColumn column : dim)
        {
            dimData.add(getDimData(column, childs));
        }
        return dimData;
    }
    
    private static List<Map<String, Object>> getDimData(RequestColumn column, Collection<Condition> childs)
    {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        String id = column.getId();
        if ("900".equals(id) || "3600".equals(id) || "86400".equals(id))
        {
            long starTime = 0L;
            long endTime = 0L;
            for (Condition child : childs)
            {
                if (child.getType() == ColumnType.TIME)
                {
                    long time = Long.parseLong(child.getValues().get(0));
                    switch (child.getOperator())
                    {
                        case GE:
                            starTime = time;
                            break;
                        case LT:
                            endTime = time;
                            break;
                        case EQ:
                            starTime = time;
                            endTime = time;
                            break;
                        default:
                            break;
                    }
                }
            }
            
            for (long l = starTime; l <= endTime; l += Integer.parseInt(id))
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(id, l);
                list.add(map);
            }
        }
        else
        {
            String[] dimStrs = DacConfig.get(id).split(",");
            for (String dimStr : dimStrs)
            {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(id, dimStr);
                list.add(map);
            }
        }
        return list;
    }
    
    private static Map<String, Object> getDataMap(DataRequest dr, Map<String, Object> row, int index)
    {
        List<RequestColumn> columns = dr.getColumns();
        for (RequestColumn column : columns)
        {
            ColumnType type = column.getType();
            String key = column.getAlias();
            String id = column.getId();
            Object value = row.get(id);
            if (type == ColumnType.DIMENSION)
            {
                if (column.getStyle() == Style.IDENTIFIER)
                {
                    value = getDimValueOfId(id, value.toString());
                }
            }
            else if (type != ColumnType.TIME)
            {
                //value = Math.random() * Config.getInt("DATA_RANGE");
                value = new Random().nextInt(DacConfig.getInt("DATA_RANGE"));
            }
            row.put(key, value);
        }
        return row;
    }
    
    /**
     * 
     * 方法名: getAliasById 描述：根据id获取column的alias
     * 
     * @param columns
     * @param id
     * @return String
     */
    private static String getAliasById(List<RequestColumn> columns, String id)
    {
        for (RequestColumn column : columns)
        {
            if (column.getId().equals(id))
            {
                return column.getAlias();
            }
        }
        return null;
    }
    
    private static List<Map<String, Object>> getDataFromCsv(File file)
        throws IOException
    {
        BufferedReader br = null;
        try
        {
            List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String headerStr = br.readLine();
            if (StrUtil.isNullOrEmpty(headerStr))
            {
                return null;
            }
            String[] headers = headerStr.split(",");
            String line;
            while ((line = br.readLine()) != null)
            {
                String[] data = line.split(",");
                if (data.length < headers.length)
                {
                    continue;
                }
                Map<String, Object> map = new HashMap<String, Object>();
                int index = 0;
                for (String header : headers)
                {
                    String value = data[index++].trim();
                    if (value.matches("-?\\d+"))
                    {
                        map.put(header.trim(), Long.valueOf(value));
                    }
                    else if (value.matches("-?\\d+\\.\\d*"))
                    {
                        map.put(header.trim(), new BigDecimal(value));
                    }
                    else
                    {
                        map.put(header.trim(), value);
                    }
                }
                result.add(map);
            }
            return result;
        }
        finally
        {
            if (br != null)
            {
                br.close();
            }
        }
    }
    
    private static List<Map<String, Object>> getDimResult(List<List<Map<String, Object>>> dims)
    {
        
        if (dims.size() == 1)
        {
            return recursion(dims.get(0), null);
        }
        
        List<Map<String, Object>> list = null;
        for (int i = 0; i < dims.size();)
        {
            list = recursion(dims.get(i++), list);
        }
        return list;
        
    }
    
    private static List<Map<String, Object>> recursion(List<Map<String, Object>> dim1, List<Map<String, Object>> dim2)
    {
        if (dim2 == null)
        {
            return dim1;
        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> d2 : dim2)
        {
            for (Map<String, Object> d1 : dim1)
            {
                Map<String, Object> map = new HashMap<>();
                for (Entry<String, Object> e : d1.entrySet())
                {
                    map.put(e.getKey(), e.getValue());
                }
                for (Entry<String, Object> e : d2.entrySet())
                {
                    map.put(e.getKey(), e.getValue());
                }
                list.add(map);
            }
        }
        return list;
    }
    
    private static String getDimValueOfId(String dimId, String name)
    {
        String value = dimMap.get(dimId + "-" + name);
        if (value == null)
        {
            synchronized (TestDataFromJson.class)
            {
                if (value == null)
                {
                    String[] names = DacConfig.get(dimId).split(",");
                    String idStr = DacConfig.get(dimId + "_ID");
                    String[] ids = null;
                    if (idStr != null)
                    {
                        ids = idStr.split(",");
                    }
                    for (int i = 0; i < names.length; i++)
                    {
                        dimMap.put(dimId + "-" + names[i], ids == null ? String.valueOf(i) : ids[i]);
                    }
                }
            }
        }
        return value;
    }
}
