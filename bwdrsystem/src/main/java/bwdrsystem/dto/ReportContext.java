package bwdrsystem.dto;

import java.util.LinkedList;

/**
 * 
 * @description <p>报文对象实体类，记录每个报文信息</P>
 * @createTime 2016-8-10 下午02:38:58
 * @updateTime 2016-8-10 下午02:38:58
 * @author Liutao
 * @updater Liutao
 */
public class ReportContext  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private String header;
	private String footer;
	private LinkedList<String> bodyList;
	
	public ReportContext() {
		this.header = "";
		this.footer = "";
		this.bodyList = new LinkedList<String>();
	}

	public ReportContext(String header, String footer) {
		this.header = header;
		this.footer = footer;
		this.bodyList = new LinkedList<String>();
	}
	
	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public LinkedList<String> getBodyList() {
		return bodyList;
	}

	public void setBodyList(LinkedList<String> bodyList) {
		this.bodyList = bodyList;
	}

	public String getFooter() {
		return footer;
	}

	public void setFooter(String footer) {
		this.footer = footer;
	}
	
	
	
}
