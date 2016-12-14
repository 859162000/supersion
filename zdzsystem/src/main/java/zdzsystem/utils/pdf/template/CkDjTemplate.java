package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class CkDjTemplate extends Template {
    private static final String controlBody = "被执行人（或其他法律地位）%s在我行%s账户内的存款已被冻结，" +
            "已冻结可用金额人民币（或其他币种）%s元，额度冻结金额人民币（或其他币种）%s元，冻结期限自%tY年%tm" +
            "月%td日%tH时%tM分至%tY年%tm月%td日%tH时%tM分。";

    private static final String unControlBody = "因%s原因，被执行人（或其他法律地位）%s在我行%s账户内的存款未能冻结。";

    private static final String qlBody = "上述账户中，被执行人（或其他法律地位）%s的%s账户为%s（优先受偿权类" +
            "型）账户，权利人（/监管机关）%s对该账户中的存款%s元享有%s权（权利类型）。";

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
