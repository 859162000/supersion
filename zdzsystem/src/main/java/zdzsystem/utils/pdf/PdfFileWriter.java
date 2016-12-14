package zdzsystem.utils.pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.OutputStream;

public class PdfFileWriter extends FileWriter {

    private Document document;
    private BaseFont baseFont;
    private ParagraphListener pageListener;

    @Override
    protected void init(OutputStream os) throws Exception {
        document = new Document(PageSize.A4);

        PdfWriter writer = PdfWriter.getInstance(document, os);

        pageListener = new ParagraphListener();
        writer.setPageEvent(pageListener);

        // Color c = getDefaultFontColor();
        // BaseColor baseColor = new BaseColor(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());

        baseFont = BaseFont.createFont(getDefaultFontPath(), BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

        // baseFont.setColor(bc);
        document.open();
    }

    @Override
    protected void close() {
        if (document != null) {
            document.close();
        }
    }

    @Override
    protected void writeContent(String content, boolean hasIndentInFirstLine, int alignment) throws Exception {
        writeContent(content, getDefaultFontSize(), hasIndentInFirstLine, alignment);
    }

    @Override
    protected void writeContent(String content, float fontsize, boolean hasIndentInFirstLine, int alignment)
            throws Exception {
        Paragraph paragraph = new Paragraph();
        if (hasIndentInFirstLine) {
            paragraph.setFirstLineIndent(20);
            // paragraph.setIndentationLeft(20);
            // paragraph.add(Chunk.TABBING);
        }

        paragraph.add(new Chunk(content, new Font(baseFont, fontsize)));

        paragraph.setAlignment(alignment);
        document.add(paragraph);

    }

    @Override
    protected void writeImg(String filePath, float width, float height) throws Exception {
        pageListener.setActive(true);
        document.add(new Paragraph(Chunk.NEWLINE));
        pageListener.setActive(false);
        Image img = Image.getInstance(filePath);
        img.scaleAbsolute(width, height);
        Rectangle d = document.getPageSize();
        float x = d.getWidth() - document.rightMargin() - width - 10;
        float y = pageListener.getEndPosition() - 16;
        img.setAbsolutePosition(x, y);
        document.add(img);
    }

    @Override
    protected void writeNewLine() throws Exception {
        document.add(Chunk.NEWLINE);
    }
}