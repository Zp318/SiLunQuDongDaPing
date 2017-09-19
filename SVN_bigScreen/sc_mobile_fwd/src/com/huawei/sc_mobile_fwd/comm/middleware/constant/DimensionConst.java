package com.huawei.sc_mobile_fwd.comm.middleware.constant;

/**
 * 类名: DimensionConst
 * 描述: 维度常量定义
 */
public interface DimensionConst
{
    /**
     * 维度ID：源MSC类型
     */
    String DIM_ID_ORG_MSC_TYPE = "249030103";
    
    /**
     * 维度ID：目的MSC类型
     */
    String DIM_ID_DEST_MSC_TYPE = "250030103";
    
    /**
     * MSC类型：国际关口局
     * (0:VMSC,1:国际关口局,2:国际长途局,6:TMSC(Region),7:GMSC(Region),
     * 8:GMSC(City),9:TMSC(City),1003:Other Network GMSC(National))
     */
    String DIM_MSC_TYPE_IGW = "1";
    
    /**
     * MSC类型：国际长途局
     * (0:VMSC,1:国际关口局,2:国际长途局,6:TMSC(Region),7:GMSC(Region),
     * 8:GMSC(City),9:TMSC(City),1003:Other Network GMSC(National))
     */
    String DIM_MSC_TYPE_LDI = "2";
    
    /**
     * 维度ID：CS的协议类型
     */
    String DIM_ID_FAIL_ANALYSIS_PROTOCOL = "105010101";
    
    /**
     * 维度ID：CS的子协议类型
     */
    String DIM_ID_FAIL_ANALYSIS_SUBPROT = "105010102";
    
    /**
     * 维度ID：CS的失败原因值
     */
    String DIM_ID_FAIL_ANALYSIS_FAILCAUSE = "105010103";
    
    /**
     * 维度ID：CS的失败原因消息
     */
    String DIM_ID_FAIL_ANALYSIS_FAILMSG = "105010104";
    
    /**
     * 维度ID：原因归属
     */
    String DIM_ID_FAIL_ANALYSIS_CLASS = "105010105";
    
    /**
     * 维度ID：失败场景
     */
    String DIM_ID_FAIL_ANALYSIS_SCENE = "105010107";
    
    /**
     * 维度ID：国家码
     */
    String DIM_ID_HOMECC = "2308010101";
    
    /**
     * 维度ID：终端品牌
     */
    String DIM_ID_TERMINAL_BRAND = "203010101";
    
    /**
     * 维度ID：终端型号
     */
    String DIM_ID_TERMINAL_MODEL = "203020101";
    
    /**
     * 维度ID：终端操作系统
     */
    String DIM_ID_TERMINAL_OS = "203040101";
    
    /**
     * 维度ID：终端TAC编码
     */
    String DIM_ID_TERMINAL_TAC = "203050101";
    
    /**
     * 维度ID：MSISDN
     */
    String DIM_ID_MSISDN = "204010101";
    
    /**
     * 维度ID：MSISDN（for充值）
     */
    String DIM_ID_MSISDN_FOR_RELOAD = "2522010101";
    
    /**
     * 维度ID：IMSI
     */
    String DIM_ID_IMSI = "204020101";
    
    /**
     * 维度ID：IMSI（for充值）
     */
    String DIM_ID_IMSI_RELOAD = "2523010101";
    
    /**
     * 维度ID：用户名
     */
    String DIM_ID_VIPNAME = "204010101";
    
    /**
     * 维度别名：终端品牌
     */
    String DIM_ALIAS_TERMINAL_BRAND = "brandname";
    
    /**
     * 维度别名：终端型号
     */
    String DIM_ALIAS_TERMINAL_MODEL = "modelname";
    
    /**
     * 维度别名：终端操作系统
     */
    String DIM_ALIAS_TERMINAL_OS = "osname";
    
    /**
     * 维度别名：终端操作系统ID
     */
    String DIM_ALIAS_TERMINAL_OSID = "ostypeid";
    
    /**
     * 维度别名：终端TAC码
     */
    String DIM_ALIAS_TERMINAL_TAC = "tac";
    
    /**
     * 维度ID：CELL维度 cgi
     */
    String DIM_ID_LOCALATION_CELL = "2524010101";
    
    /**
     * 维度ID：BSCRNC维度 bscrnc_id
     */
    String DIM_ID_LOCALATION_BSCRNC = "202040101";
    
    /**
     * 维度ID：MSCPOOL维度 mscpool_id
     */
    String DIM_ID_LOCALATION_MSCPOOL = "2524020101";
    
    /**
     * 维度ID：layer维度 LAYER1ID
     */
    String DIM_ID_LOCALATION_LAYER1ID = "225010101";
    
    /**
     * 维度ID：layer维度 LAYER2ID
     */
    String DIM_ID_LOCALATION_LAYER2ID = "225020101";
    
    /**
     * 维度ID：layer维度 LAYER3ID
     */
    String DIM_ID_LOCALATION_LAYER3ID = "225030101";
    
    /**
     * 维度ID：layer维度 LAYER4ID
     */
    String DIM_ID_LOCALATION_LAYER4ID = "225040101";
    
    /**
     * 维度ID：layer维度 LAYER5ID
     */
    String DIM_ID_LOCALATION_LAYER5ID = "225050101";
    
    /**
     * 维度ID：layer维度 LAYER6ID
     */
    String DIM_ID_LOCALATION_LAYER6ID = "225060101";
}
