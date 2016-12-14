package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class CkJdTemplate extends Template {
    private static String controlBody = "本案对被执行人（或其他法律地位）%s在我行%s账户内存款已解除冻结，" +
            "解除冻结可用金额人民币（或其他币种）%s元，解除额度冻结金额人民币（或其他币种）%s元。";

    private static String unControlBody = "因%s原因，本案对被执行人（或其他法律地位）%s在我行%s账户内存款的冻结措施未能解除。";

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
