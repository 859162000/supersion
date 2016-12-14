package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class CkKhTemplate extends Template {
    private static String controlBody = "被执行人（或其他法律地位）%s在我行%s账户内的存款已扣划人民币（或其他币种）%s元至你院执行款专户。";

    private static String unControlBody = "因%s原因，被执行人（或其他法律地位）%s在我行%s账户内的存款未能扣划。";

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
