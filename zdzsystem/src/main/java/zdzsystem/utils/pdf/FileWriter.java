package zdzsystem.utils.pdf;

import org.apache.commons.lang.StringUtils;
import zdzsystem.Constants;
import zdzsystem.oxm.KzFkKzxx;
import zdzsystem.oxm.KzFkQlxx;
import zdzsystem.oxm.KzFkQlxxList;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class FileWriter implements IFileWriter {
    private String defaultFontPath;
    private float defaultFontSize;
    private Color defaultFontColor;
    private String defaultDateFormat;
    private String dzyzPath;
    private String createFilePath;

    public FileWriter() {
        defaultFontPath = "zdzsystem/font/STKAITI.TTF";
        defaultFontSize = 12f;
        defaultFontColor = Color.BLACK;
        defaultDateFormat = "yyyy/MM/dd HH:mm:ss";
        createFilePath = "";
    }

    public String getDefaultFontPath() {
        return defaultFontPath;
    }

    @Override
    public void setDefaultFontPath(String defaultFontPath) {
        this.defaultFontPath = defaultFontPath;
    }

    public float getDefaultFontSize() {
        return defaultFontSize;
    }

    @Override
    public void setDefaultFontSize(float defaultFontSize) {
        this.defaultFontSize = defaultFontSize;
    }

    public Color getDefaultFontColor() {
        return defaultFontColor;
    }

    @Override
    public void setDefaultFontColor(Color defaultFontColor) {
        this.defaultFontColor = defaultFontColor;
    }

    public String getDefaultDateFormat() {
        return defaultDateFormat;
    }

    @Override
    public void setDefaultDateFormat(String defaultDateFormat) {
        this.defaultDateFormat = defaultDateFormat;
    }

    public String getDzyzPath() {
        return dzyzPath;
    }

    @Override
    public void setDzyzPath(String dzyzPath) {
        this.dzyzPath = dzyzPath;
    }

    @Override
    public void setCreateFilePath(String filePath) {
        this.createFilePath = filePath;
    }

    @Override
    public byte[] write(FeedBack feedback) throws Exception {
        byte[] result = null;

        OutputStream os = new ByteArrayOutputStream();

        try {
            init(os);

            writeContent(String.format(Template.getTitle(), feedback.getFymc()), 14f, false, 1);
            writeContent(feedback.getFymc() + ":", false, 0);
            writeContent(String.format(Template.getHead(), feedback.getCkwh(), feedback.getCktz()), true, 3);

            for (KzFkKzxx kzxx : feedback.getKzFk().getKzxxList()) {
                String kzlx = kzxx.getKzlx();
                String kzcs = kzxx.getKzcs();

                Template template = Template.getTemplate(kzlx, kzcs);
                if (template != null) {
                    if (Constants.KZLX_CK.equals(kzlx)) {
                        writeCk(feedback, kzxx, kzcs, template);
                    } else if (Constants.KZLX_ZC.equals(kzlx)) {
                        writeZc(feedback, kzxx, kzcs, template);
                    }
                }
            }

            writeContent(" ", false, 2);
            writeContent(" ", false, 2);
            writeContent(" ", false, 2);
			// writeNewLine();
            // writeContent(feedback.getYhmc(), false, 2);

            Date date = parseDate(feedback.getWssj());
            writeContent(String.format("%tY年%tm月%td日%tH时%tM分", date, date, date, date, date), false, 2);
            if (StringUtils.isNotBlank(dzyzPath)) {
                writeImg(dzyzPath, 80f, 80f);
            }

            close();

            result = ((ByteArrayOutputStream) os).toByteArray();
        } finally {
            os.close();
        }

        // createFile(result);

        return result;

    }

    private void writeCk(FeedBack feedback, KzFkKzxx kzxx, String kzcs, Template template) throws Exception {
        String bodyContent = "";
        String kzzt = kzxx.getKzzt();
        if (Constants.KZZT_YK.equals(kzzt)) {
            if (Constants.KZCS_DJ.equals(kzcs) || Constants.KZCS_XD.equals(kzcs)) {
                Date csksrq = parseDate(kzxx.getCsksrq());
                Date csjsrq = parseDate(kzxx.getCsjsrq());
                bodyContent = String.format(//
                        template.getControlBody(), feedback.getBzxrxm(), kzxx.getKhzh(), kzxx.getSkje(), kzxx.getDjxe(),//
                        csksrq, csksrq, csksrq, csksrq, csksrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq);
            } else if (Constants.KZCS_JD.equals(kzcs)) {
                bodyContent = String.format(//
                        template.getControlBody(), feedback.getBzxrxm(), kzxx.getKhzh(), kzxx.getSkje(), kzxx.getDjxe());
            } else if (Constants.KZCS_KH.equals(kzcs)) {
                bodyContent = String.format(//
                        template.getControlBody(), feedback.getBzxrxm(), kzxx.getKhzh(), kzxx.getSkje());
            }
        } else {
            bodyContent = String.format(//
                    template.getUnControlBody(), kzxx.getWnkzyy(), feedback.getBzxrxm(), kzxx.getKhzh());
        }

        writeContent(bodyContent, true, 3);

        writeQl(feedback, kzxx, kzcs, template);
    }

    private void writeZc(FeedBack feedback, KzFkKzxx kzxx, String kzcs, Template template) throws Exception {
        String bodyContent = "";

        String kzzt = kzxx.getKzzt();
        if (Constants.KZZT_YK.equals(kzzt)) {
            String cb = template.getControlBody();
            if (Constants.KZCS_DJ.equals(kzcs) || Constants.KZCS_XD.equals(kzcs)) {
                Date csksrq = parseDate(kzxx.getCsksrq());
                Date csjsrq = parseDate(kzxx.getCsjsrq());
                bodyContent = String.format(//
                        cb, feedback.getBzxrxm(), kzxx.getKhzh(), kzxx.getDjxe(), csksrq, csksrq, csksrq, csksrq,//
                        csksrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq, csjsrq);
            } else if (Constants.KZCS_JD.equals(kzcs)) {
                bodyContent = String.format(//
                        cb, feedback.getBzxrxm(), kzxx.getKhzh(), kzxx.getZcmc(), kzxx.getDjxe());
            }
        } else {
            if (Constants.KZCS_XD.equals(kzcs)) {
                bodyContent = String.format(//
                        template.getUnControlBody(), kzxx.getWnkzyy(), feedback.getBzxrxm(),//
                        kzxx.getKhzh(), kzxx.getZcmc(), kzxx.getDjxe());
            } else {
                bodyContent = String.format(//
                        template.getUnControlBody(), kzxx.getWnkzyy(), feedback.getBzxrxm(),//
                        kzxx.getKhzh(), kzxx.getZcmc());
            }
        }

        writeContent(bodyContent, true, 3);

        writeQl(feedback, kzxx, kzcs, template);
    }

    private Date parseDate(String strDate) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat(defaultDateFormat);
        return format.parse(strDate);
    }


    private void createFile(byte[] fileContent) throws Exception {
        if (StringUtils.isNotBlank(createFilePath) && fileContent != null) {
            FileOutputStream fos = new FileOutputStream(createFilePath);
            try {
                fos.write(fileContent);
            } finally {
                fos.close();
            }
        }
    }

    private void writeQl(FeedBack feedback, KzFkKzxx kzxx, String kzcs, Template template) throws Exception {
        if (Constants.KZCS_DJ.equals(kzcs)) {
            String qlContent = "";
            String qlTemplate = template.getQlBody();
            if (StringUtils.isNotBlank(qlTemplate)) {
                try {
                    for (KzFkQlxx qlxx : kzxx.getQlxxLists().get(0).getQlxxList()) {
                        qlContent = String.format(//
                                qlTemplate, feedback.getBzxrxm(), kzxx.getKhzh(), qlxx.getYxscqlx(), qlxx.getQlr(), //
                                qlxx.getQlje(), qlxx.getQllx());
                        writeContent(qlContent, true, 3);
                    }
                } catch (NullPointerException e) {}
            }
        }
    }

    protected abstract void init(OutputStream os) throws Exception;

    protected abstract void close();

    /**
     *
     * @param content 内容
     * @param hasIndentInFirstLine 缩进
     * @param alignment 设置段落居中，其中1为居中对齐，2为右对齐，3为左对齐
     * @throws Exception
     */
    protected abstract void writeContent(String content, boolean hasIndentInFirstLine, int alignment) throws Exception;

    protected abstract void writeContent(String content, float fontsize, boolean hasIndentInFirstLine, int alignment)
            throws Exception;

    protected abstract void writeImg(String filePath, float width, float height) throws Exception;

    protected abstract void writeNewLine() throws Exception;

    public static IFileWriter getFileWriter() {
        return new PdfFileWriter();
    }
}