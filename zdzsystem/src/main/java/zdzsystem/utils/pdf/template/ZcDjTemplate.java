package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class ZcDjTemplate extends Template {
    private static String controlBody = "被执行人（或其他法律地位）%s在我行%s账户内的金融资产%s份（计量单位）已被冻结，" +
            "冻结期限自%tY年%tm月%td日%tH时%tM分至%tY年%tm月%td日%tH时%tM分，该金融资产交易限制将于%tY年%tm月%td日解除。";

    private static String qlBody = "上述账户中，被执行人（或其他法律地位）%s的%s账户为%s（优先受偿权类" +
            "型）账户，权利人（/监管机关）%s对该账户中的资产%s份享有%s权（权利类型）。";

    private static String unControlBody = "因%s原因，被执行人（或其他法律地位）%s在我行%s账户内的%s金融资产未能冻结。";

    @Override
    public String getControlBody() {
        return controlBody;
    }

    @Override
    public String getQlBody() {
        return qlBody;
    }

    @Override
    public String getUnControlBody() {
        return unControlBody;
    }

}
