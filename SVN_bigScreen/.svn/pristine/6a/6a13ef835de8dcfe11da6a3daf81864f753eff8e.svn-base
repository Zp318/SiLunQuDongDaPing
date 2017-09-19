package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.huawei.seq.WebCoreTools;
import com.huawei.seq.tools.SecurityFliterUtils;


/**
 * 文件读写工具类
 * 
 */
public abstract class FileUtil
{
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
    
    /**
     * 获取文件输出流对象
     * @param filePath 文件路径
     * @return 流对象
     */
    public static OutputStream getOutputStream(String filePath)
    {
        try
        {
            // Mod by wWX222227 for Fortify清理 20140829
            return Files.newOutputStream(new File(filePath).toPath(), 
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        }
        catch (FileNotFoundException e)
        {
            // mod by gKF73638 for Fortify问题 20140424
            logger.error("[cem_common]: file not found: {}", filePath);
        }
        catch (IOException e)
        {
            logger.error("[cem_common]: file not found: {}", filePath);
        }
        
        return null;
    }
    
    /**
     * 获取参数map
     * @param params 页面传入的参数字符串 以&分割
     * @return 返回参数map
     */
    public static Map<String, String> getParamsMap(String params)
    {
        Map<String, String> paramsMap = new HashMap<String, String>();
        
        String[] paramsArray = params.split("&");
        String value = "";
        
        for (int i = 0; i < paramsArray.length; i++)
        {
            String keyValue = paramsArray[i];
            
            value = (keyValue.split("=").length == 1) ? ""
                    : keyValue.split("=")[1];
            
            paramsMap.put(keyValue.split("=")[0], value);
        }
        
        return paramsMap;
    }
    
    /**
     * 将String类型转为String[]
     * @param columns 列名字符串
     * @return String[] 将字符串拆分后返回数组
     */
    public static String[] disColumns(String columns)
    {
        
        String[] columnArray = null;
        
        if (!"".equals(columns) && columns.contains(","))
        {
            columnArray = columns.split(",");
        }
        else
        {
            columnArray = new String[1];
            columnArray[0] = "exportColumn";
        }
        
        return columnArray;
    }
    
    /**
     * 删除临时图片文件
     * @param list 文件集合
     */
    public static void deleteFiles(List<File> list)
    {
        for (File f : list)
        {
            if (f.exists())
            {
                boolean bl = f.delete();
                if (!bl)
                {
                    logger.debug("[cem_common]: deleteFiles(List<File> list)"
                            + "ERROR : Temp file can't be deleted!");
                }
            }
        }
    }
    
    /**
     * 按列名取出结果集
     * <功能详细描述>
     * @param datamap 结果集（查询到的结果，存储了表格数据、柱状图数据等）
     * @param colNames 列名
     * @param queryDataKey  结果集中存储数据的key
     * @param tableDataKey  结果集中表格数据的key 
     * @return mapString 列名对应的值
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static List<String[]> changeExportList(Map<String, Object> datamap,
            String[] colNames, String queryDataKey, String tableDataKey)
    {
        
        Map<String, Object> dataList = (Map<String, Object>)datamap.get(queryDataKey);
        List<List<Map<String, String>>> dataListMap = (List<List<Map<String, String>>>)dataList.get(tableDataKey);
        
        List<String[]> mapString = new ArrayList<String[]>();
        
        for (List<Map<String, String>> list : dataListMap)
        {
            List<Map<String, String>> list2 = list;
            
            String[] s = new String[colNames.length];
            int i = 0;
            for (String colName : colNames)
            {
                for (Map<String, String> map : list2)
                {
                    if (map.get("id").equalsIgnoreCase(colName)
                            && i < colNames.length)
                    {
                        s[i] = map.get("value");
                        i += 1;
                    }
                }
            }
            mapString.add(s);
        }
        return mapString;
    }
    
    /**
     * 
     * 方法名: getDataList
     * <br>描述：<按列名取出结果集>
     * @param datamap  结果集（查询到的结果，存储了表格数据、柱状图数据等
     * @param colNames 列名
     * @param queryDataKey  结果集中存储数据的key
     * @param tableDataKey  结果集中表格数据的key
     * @return List<String[]> 列名对应的值
     *
     */
    @SuppressWarnings("unchecked")
    public static List<String[]> getDataList(Map<String, Object> datamap,
            String[] colNames, String queryDataKey, String tableDataKey)
    {
        
        Map<String, Object> dataList = (Map<String, Object>)datamap.get(queryDataKey);
        List<Map<String, String>> srcData = (List<Map<String, String>>)dataList.get(tableDataKey);
        
        List<String[]> mapString = new ArrayList<String[]>();
        
        int length = srcData.size();
        for (int i = 0; i < length; i++)
        {
            int j = 0;
            String[] rowData = new String[colNames.length];
            for (String col : colNames)
            {
                rowData[j] = srcData.get(i).get(col);
                j++;
            }
            mapString.add(rowData);
        }
        return mapString;
    }

    /** 
     * 方法名: readFileContent
     * 描述：读取文件内容
     * @param fileName 文件名
     * @return String 文件内容
     *
     * 创建人: gKF73638
     * 创建日期: 2014年11月30日
     * 
     * 修改历史
     * 修改日期：<创建日期，格式：YYYY-MM-DD>
     * 修改人： <姓名/工号>
     * 修改原因/修改内容:  <修改原因描述> <问题单DTSXXXXXXXX>
     */
    public static String readFileContent(String fileName)
    {
        if (StringUtils.isEmpty(fileName))
        {
            return "";
        }
        
        byte[] buf = null;
        
        try
        {
            buf = Files.readAllBytes(WebCoreTools.getConfigFile(fileName).getFile().toPath());
        }
        catch (IOException e)
        {
            logger.error("[sc_mobile_fwd]: file read fail! ",
                    SecurityFliterUtils.loggerwhiteListSecurityCode(fileName));
            return "";
        }
        
        if (null == buf || buf.length == 0)
        {
            return "";
        }
        
        StringBuffer content = new StringBuffer();
        
        for (byte b : buf)
        {
            content.append((char)b);
        }
        
        return content.toString();
    }
    
    /**
     * 
     * 方法名: readFileContent
     * 描述：<方法的功能和实现思路>
     * @param fileName 文件名
     * @param charset 编码格式
     * @return String 文件内容
     */
    public static String readFileContent(String fileName, String charset)
    {
        if (StringUtils.isEmpty(fileName))
        {
            return "";
        }
        
        byte[] buf = null;
        
        try
        {
            buf = Files.readAllBytes(WebCoreTools.getConfigFile(fileName).getFile().toPath());
            if (null == buf || buf.length == 0)
            {
                return "";
            }
            return new String(buf, charset);
        }
        catch (IOException e)
        {
            logger.error("[sc_mobile_fwd]: file read fail! ",
                    SecurityFliterUtils.loggerwhiteListSecurityCode(fileName));
            return "";
        }
        
    }
}
