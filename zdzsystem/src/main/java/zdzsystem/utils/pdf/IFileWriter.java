package zdzsystem.utils.pdf;

import java.awt.Color;

public interface IFileWriter {
    void setDefaultFontPath(String defaultFontPath);

    void setDefaultFontSize(float defaultFontSize);

    void setDefaultFontColor(Color defaultFontColor);

    void setDefaultDateFormat(String defaultDateFormat);

    void setDzyzPath(String dzyzPath);

    void setCreateFilePath(String filePath);

    byte[] write(FeedBack feedBack) throws Exception;
}
