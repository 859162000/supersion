package zdzsystem;

/**
 * @author veggieg
 * @since 2016-09-23 14:42
 */
public interface Constants {

    // 查询请求
    String BUSINESS_TYPE_CXQQ = "CXQQ";

    // 控制请求
    String BUSINESS_TYPE_KZQQ = "KZQQ";

    // 查询反馈
    String BUSINESS_TYPE_CXFK = "CXFK";

    // 控制反馈
    String BUSINESS_TYPE_KZFK = "KZFK";

    // 查询反馈失败回执
    String BUSINESS_TYPE_CXHZ = "CXHZ";

    // 控制反馈失败回执
    String BUSINESS_TYPE_KZHZ = "KZHZ";

    // 结构化数据文件
    String FILE_TYPE_QA = "QA";

    // 法律文书文件
    String FILE_TYPE_QI = "QI";

    // 证件文件
    String FILE_TYPE_QC = "QC";

    // 查控反馈文件
    String FILE_TYPE_QR = "QR";

    // 查控反馈回执
    String FILE_TYPE_QRI = "QRI";

    // 查控回退文件
    String FILE_TYPE_QNA = "QNA";

    // 查询反馈结果处理回执
    String FILE_TYPE_QNR = "QNR";

    // 控制类型(被执行人账户信息) 1-存款 2-非存款类金融资产
    String KZLX_CK = "1";

    String KZLX_ZC = "2";

    // 控制措施(被执行人账户信息) 01-冻结 02-继续冻结 03-轮候冻结 04-解除冻结 05-解除轮候冻结 06-划拨 07-提取收入 08-冻结并划拨
    String KZCS_DJ = "01";

    String KZCS_XD = "02";

    String KZCS_JD = "04";

    String KZCS_KH = "06";

    // 控制状态 1-已控 2-未控
    String KZZT_YK = "1";

    String KZZT_WK = "2";


}
