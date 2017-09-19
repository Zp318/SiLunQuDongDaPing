package com.huawei.sc_mobile_fwd.comm;

import java.io.File;
import java.util.Map;

/**
 * 文件导出接口
 */
public interface ExportFile
{
    
    /**
     * 导出文件
     * @param params 输入参数
     * @return File 文件
     * @see [类、类#方法、类#成员]
     */
    File exportFile(Map<String, Object> params);
}
