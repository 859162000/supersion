package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class ZcXdTemplate extends Template {
    private static String controlBody = "被执行人（或其他法律地位）%s在我行%s账户内的金融资产%s份（计量单位）已被继续冻结，" +
            "续冻期限自%tY年%tm月%td日%tH时%tM分至%tY年%tm月%td日%tH时%tM分，该金融资产交易限制将于%tY年%tm月%td日解除。";

    private static String unControlBody = "因%s原因，被执行人（或其他法律地位）%s在我行%s账户内的%s金融资产%s份（计量单位）未能续冻。";

    @Override
    public String getControlBody() {
        return controlBody;
    }

    @Override
    public String getQlBody() {
        return "";
    }

    @Override
    public String getUnControlBody() {
        return unControlBody;
    }

}
