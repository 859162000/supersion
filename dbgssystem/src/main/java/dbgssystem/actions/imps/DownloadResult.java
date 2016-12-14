package dbgssystem.actions.imps;

import java.io.Serializable;

public class DownloadResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5180834455899207184L;

	private String contentType;
    private String contentDisposition;
    private Integer bufferSize;
    private byte[] bytes;
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
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public byte[] getBytes() {
		return bytes;
	}
	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}    
}
