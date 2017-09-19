package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.huawei.seq.SpringContextUtils;
import com.huawei.seq.common.Const;
import com.huawei.seq.intf.Export;
import com.huawei.seq.tools.ExportUtils;
import com.huawei.seq.tools.PlaintextView;
import com.huawei.seq.tools.SecurityFliterUtils;
import com.huawei.sc_mobile_fwd.comm.consts.FileType;

/**
 * 
 * 类名 : ExportUtil
 * 描述: 导出工具类
 * 创建日期:  2013-4-23
 */
public class ExportUtil
{
    
    /**
     * 导出临时路径
     */
    private static final String EXPORT_TEMP_PATH = "export/temp/";

    /** 
     *时间秒格式定义 ：yyyyMMddHHmmss
     */
    private static final String C_TIME_MS_PATTON = "yyyyMMddHHmmss";

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 
     * 方法名: getModelAndViewForPlainText
     * 描述：获取简单字符串输出的ModelAndView对象。
     * @param content 要返回的内容。
     * @return ModelAndView 要返回的内容。
     */
    public static ModelAndView getModelAndViewForPlainText(String content)
    {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(new PlaintextView());
        modelAndView.addObject("backXml", SecurityFliterUtils.whiteListSecurityCode(content));
        return modelAndView;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @return [参数说明]
     * 
     */
    public static String getTempPath()
    {
        return EXPORT_TEMP_PATH;
    }
    
    /** 
     * 方法名: getExportQualifiedName
     * 描述：取得导出文件的全路径（包括路径及文件名称）
     * @param request 请求
     * @param exportFilename 导出文件名（带后缀）
     * @return String 导出文件的全路径名称
     *
     */
    public static String getExportQualifiedName(HttpServletRequest request,
            String exportFilename)
    {
        if (!StringUtils.hasLength(exportFilename))
        {
            exportFilename = "export_temp";
        }

        StringBuffer exportFile = new StringBuffer();
        exportFile.append(getExportBasePath(request));
        exportFile.append(exportFilename);
        return exportFile.toString();
    }
    
    /** 
     * 方法名: getExportQualifiedName
     * 描述：取得导出文件的全路径（包括路径及文件名称）
     * @param request 请求
     * @param exportFilename 导出文件名（带后缀）
     * @param exportPath 导出路径
     * @return String 导出文件的全路径名称
     *
     */
    public static String getExportQualifiedName(HttpServletRequest request,
            String exportFilename, String exportPath)
    {
        if (!StringUtils.hasLength(exportPath))
        {
            exportPath = EXPORT_TEMP_PATH;
        }
        
        if (!StringUtils.hasLength(exportFilename))
        {
            exportFilename = "export_temp";
        }
        
        // 文件导出路径：https://ip:port/webappname/psnpm/temp/exportFilename.zip
        // 例如：https://172.17.9.1:8443/cem//psnpm/temp/VAC分析_语音_语音业务感知接通率_20140121170002.zip

        StringBuffer exportFile = new StringBuffer();
        exportFile.append(getExportBasePath(request));
        exportFile.append(exportFilename);  
        return exportFile.toString();
        
    }
    
    /** 
     * 方法名: getExportBasePath
     * 描述：取得基础路径
     * @param request 请求
     * @return String 基础路径
     *
     */
    public static String getExportBasePath(HttpServletRequest request)
    {
        StringBuffer path = new StringBuffer();
        path.append(ExportUtils.getExportPath(request));
        path.append("export/downloadExportFile.action?filename=");
        return path.toString();
    }
    
    /** 
     * 方法名: getExportFieldList
     * 描述：将导出列头转换为List
     * @param csvExportColumns 导出列头String
     * @return List<String> 转换后的List
     *
     */
    public static List<String> getExportFieldList(String csvExportColumns)
    {
        List<String> result = new ArrayList<String>();
        
        if (StringUtils.isEmpty(csvExportColumns))
        {
            return result;
        }
        
        String[] exportColumns = csvExportColumns.split(",");
        
        return Arrays.asList(exportColumns);
    }
    
    /** 
     * 方法名: getExportDataList
     * 描述：获取导出结果数据
     * @param dataList 查询结果
     * @param csvExportColumns 导出列头
     * @param locale 国际化语言
     * @param exportInterface 接口
     * @return List<Map<String, Object>>导出结果数据
     */
    
    public static List<Map<String, Object>> getExportDataList(
            final List<Map<String, Object>> dataList, String csvExportColumns,
            String locale, Export exportInterface)
    {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        resultList.add(getExportHeaderList(csvExportColumns, locale,exportInterface));
        resultList.addAll(dataList);
        return resultList;
    }
    
    /**
     * 方法名: getExportHeaderList
     * 描述：获取国际化列头
     * @param csvExportColumns
     * @param locale
     * @return Map<String, Object> 国际化列头
     */
    
    private static Map<String, Object> getExportHeaderList(
            String csvExportColumns, String locale, Export exportInterface)
    {
        Map<String, Object> result = new HashMap<String, Object>();
        
        if (StringUtils.isEmpty(csvExportColumns))
        {
            return result;
        }
        
        if (StringUtils.isEmpty(locale))
        {
            locale = "en_US";
        }
        
        String[] exportColumns = csvExportColumns.split(",");
        
        for (String headColumn : exportColumns)
        {
            result.put(headColumn,
                    exportInterface.getTitle(headColumn, locale));
        }
        
        return result;
    }
    
    /**
     * 导出文件
     * @param exportType 导出文件类型
     * @param language 环境语言
     * @param fileNameKey 导出文件名对应在properties文件中的key
     * @param columnKey 导出列名properties文件中的key
     * @param dataList 导出数据
     * @return 导出文件
     */
    public static File exportFile(String exportType, String language, String fileNameKey, String columnKey,
        List<Map<String, Object>> dataList)
    {
        
        logger.debug("[sc_mobile_fwd]: entering into ExportUtil.exportFile()");
        
        // 导出指标的文件名
        String fileName = getExportFileName(fileNameKey, language);
        // 创建导出文件
        File file = createFile(fileName, exportType);
        
        // 导出列
        String exportColumn = PropertiesUtil.getProperties(columnKey);
        
        List<Map<String, Object>> exportData =
            ExportUtil.getExportDataList(dataList, exportColumn, language, new ExportImpl());
        List<String> headList = ExportUtil.getExportFieldList(exportColumn);
        file = ExportUtils.exportFile(file, headList, exportData, exportType);
        
        logger.debug("[sc_mobile_fwd]: quitted from ExportUtil.exportFile()");
        return file;
    }

    /**
     * 导出文件
     * @param exportType 导出文件类型
     * @param fileName 导出文件名称
     * @param keyList 导出列的key
     * @param dataList 导出数据（含表头）
     * @return 导出文件
     */
    public static File exportFile(String exportType, String fileName, List<String> keyList,
        List<Map<String, Object>> dataList)
    {
        
        logger.debug("[sc_mobile_fwd]: entering into ExportUtil.exportFile()");
        // 创建导出文件
        File file = createFile(fileName, exportType);
        file = ExportUtils.exportFile(file, keyList, dataList, exportType);
        logger.debug("[sc_mobile_fwd]: quitted from ExportUtil.exportFile()");
        return file;
    }

    /**
     * 创建导出文件
     * 
     * @param simpleFileName 文件名
     * @param exportFileType 文件类型
     * @return 文件实例
     * 
     * @return File [返回类型说明]
     * @exception throws [违例类型] [违例说明]
     * @see [类、类#方法、类#成员]
     */
    private static File createFile(String simpleFileName, String exportFileType)
    {
        // 判断文件导出格式是否是 EXCEL、CSV 中的一种
        boolean exportType =
            FileType.EXCEL.value().equals(exportFileType) || FileType.CSV.value().equals(exportFileType);
        if (!exportType)
        {
            exportFileType = FileType.EXCEL.value();
            logger.error("[sc_mobile_fwd]: exportFileType param is error!");
        }
        
        // 文件下载路径
        String basePath = ExportUtils.getBasePath();
        String filename = ExportUtils.getTempPath(Const.EXPORT_PRIVATE_TYPE, null) + simpleFileName
            + ExportUtils.getSuffix(exportFileType.toLowerCase());
        File file = new File(SecurityFliterUtils.whiteListSecurityCode(basePath + filename));
        
        return file;
    }

    /**
     * 取得导出文件名
     * 
     * @param language 语言
     * @return 导出文件名
     * 
     * @return String [返回类型说明]
     */
    private static String getExportFileName(String key, String language)
    {
        // 导出文件名为：菜单名称_业务名称_yyyymmddhhmmss
        
        String labelMenu = PropertiesUtil.getProperties(key);
        
        String lableTime = DateUtils.getFormatDateTime(new Date(), C_TIME_MS_PATTON);
        
        StringBuffer retBf = new StringBuffer();
        retBf.append(SpringContextUtils.getI18N().getI18N(labelMenu, language));
        retBf.append("_");
        retBf.append(lableTime);
        
        return retBf.toString();
    }
}
