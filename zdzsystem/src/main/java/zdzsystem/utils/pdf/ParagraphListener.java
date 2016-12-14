package zdzsystem.utils.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

class ParagraphListener extends PdfPageEventHelper {
    private boolean active = false;

    private float offset = 5;
    private float startPosition;
    private float endPosition;

    @Override
    public void onParagraph(PdfWriter writer, Document document, float paragraphPosition) {
        this.startPosition = paragraphPosition;
    }

    @Override
    public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition) {
        if (active) {
            endPosition = paragraphPosition;
        }
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setOffset(float offset) {
        this.offset = offset;
    }

    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition;
    }

    public void setEndPosition(float endPosition) {
        this.endPosition = endPosition;
    }

    public boolean isActive() {
        return active;
    }

    public float getOffset() {
        return offset;
    }

    public float getStartPosition() {
        return startPosition;
    }

    public float getEndPosition() {
        return endPosition;
    }
}
