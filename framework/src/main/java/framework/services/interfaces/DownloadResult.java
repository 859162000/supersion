package framework.services.interfaces;

import java.io.InputStream;

public class DownloadResult {
	
	private String contentType;
    private String contentDisposition;
    private Integer bufferSize;
    private InputStream inputStream;
    private boolean success;

	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getContentDisposition() {
		return contentDisposition;
	}
	public void setContentDisposition(String contentDisposition) {
		this.contentDisposition = contentDisposition;
	}
	public Integer getBufferSize() {
		return bufferSize;
	}
	public void setBufferSize(Integer bufferSize) {
		this.bufferSize = bufferSize;
	}
	
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}

