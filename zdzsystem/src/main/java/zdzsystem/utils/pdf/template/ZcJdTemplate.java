package zdzsystem.utils.pdf.template;

import zdzsystem.utils.pdf.Template;

public class ZcJdTemplate extends Template {
    private static String controlBody = "本案对被执行人（或其他法律地位）%s账户内的%s金融资产%s份（计量单位）的冻结措施已经解除。";

    private static String unControlBody = "因%s原因，本案对被执行人（或其他法律地位）%s在我行%s账户内%s金融资产的冻结措施未能解除。";

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
