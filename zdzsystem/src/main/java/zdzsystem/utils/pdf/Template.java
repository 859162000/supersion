package zdzsystem.utils.pdf;

import zdzsystem.Constants;
import zdzsystem.utils.pdf.template.CkDjTemplate;
import zdzsystem.utils.pdf.template.CkJdTemplate;
import zdzsystem.utils.pdf.template.CkKhTemplate;
import zdzsystem.utils.pdf.template.CkXdTemplate;
import zdzsystem.utils.pdf.template.ZcDjTemplate;
import zdzsystem.utils.pdf.template.ZcJdTemplate;
import zdzsystem.utils.pdf.template.ZcXdTemplate;

public abstract class Template {
    private static final String TITLE = "%s\r\n协助执行通知书\r\n(回执)";
    private static final String HEAD = "你院%s、%s收悉，我行处理结果如下:";

    public static String getTitle() {
        return TITLE;
    }

    public static String getHead() {
        return HEAD;
    }

    // 控制成功信息
    public abstract String getControlBody();

    // 控制失败信息
    public abstract String getUnControlBody();

    // 控制权利人信息
    public abstract String getQlBody();

    /**
     *
     * @param kzlx 控制类型
     * @param kzcs 控制措施
     * @return
     */
    public static Template getTemplate(String kzlx, String kzcs) {
        Template template = null;

        if (Constants.KZLX_CK.equals(kzlx)) {
            if (Constants.KZCS_DJ.equals(kzcs)) {
                template = new CkDjTemplate();
            } else if (Constants.KZCS_JD.equals(kzcs)) {
                template = new CkJdTemplate();
            } else if (Constants.KZCS_XD.equals(kzcs)) {
                template = new CkXdTemplate();
            } else if (Constants.KZCS_KH.equals(kzcs)) {
                template = new CkKhTemplate();
            }
        } else if (Constants.KZLX_ZC.equals(kzlx)) {
            if (Constants.KZCS_DJ.equals(kzcs)) {
                template = new ZcDjTemplate();
            } else if (Constants.KZCS_JD.equals(kzcs)) {
                template = new ZcJdTemplate();
            } else if (Constants.KZCS_XD.equals(kzcs)) {
                template = new ZcXdTemplate();
            }
        }

        return template;
    }
}