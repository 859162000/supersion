package szzxpt.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import framework.helper.TypeParse;
import framework.interfaces.IColumn;
import framework.interfaces.IEntity;

@Entity
@Table(name = "YWDATA")
@IEntity(navigationName="每日业务发生量统计表",description="每日业务发生量统计表")
public class YWDATA implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@IColumn(description="编号")
	@Column(name = "AUTOID", length = 64)
	private String AUTOID;
	
	@IColumn(description="数据日期")
	@Column(name = "DATTIME", length = 8,nullable=false)
	private String DATTIME;
	
	@IColumn(description="贷款新增数")
	@Column(name = "DKXZNUM")
	private Integer DKXZNUM;
	
	@IColumn(description="贷款还款数")
	@Column(name = "DKHKNUM")
	private Integer DKHKNUM;
	
	@IColumn(description="贷款结清数")
	@Column(name = "DKJQNUM")
	private Integer DKJQNUM;
	
	@IColumn(description="贷款展期数")
	@Column(name = "DKZQNUM")
	private Integer DKZQNUM;
	
	@IColumn(description="融资新增数")
	@Column(name = "RZXZNUM")
	private Integer RZXZNUM;
	
	@IColumn(description="融资还款数")
	@Column(name = "RZHKNUM")
	private Integer RZHKNUM;
	
	@IColumn(description="融资结清数")
	@Column(name = "RZJQNUM")
	private Integer RZJQNUM;
	
	@IColumn(description="融资展期数")
	@Column(name = "RZZQNUM")
	private Integer RZZQNUM;
	
	@IColumn(description="保理新增数")
	@Column(name = "BLXZNUM")
	private Integer BLXZNUM;
	
	@IColumn(description="保理还款数")
	@Column(name = "BLHKNUM")
	private Integer BLHKNUM;
	
	@IColumn(description="授信新增数")
	@Column(name = "SXXZNUM")
	private Integer SXXZNUM;
	
	@IColumn(description="财票电子开票数")
	@Column(name = "CPKPNUM")
	private Integer CPKPNUM;
	
	@IColumn(description="财票纸票贴现数")
	@Column(name = "CPZPTXNUM")
	private Integer CPZPTXNUM;
	
	@IColumn(description="财票电票贴现数")
	@Column(name = "CPDPTXNUM")
	private Integer CPDPTXNUM;
	
	@IColumn(description="财票纸票承兑数")
	@Column(name = "CPZPCDNUM")
	private Integer CPZPCDNUM;
	
	@IColumn(description="财票电票承兑数")
	@Column(name = "CPDPCDNUM")
	private Integer CPDPCDNUM;
	
	@IColumn(description="财票转贴现数")
	@Column(name = "CPZTXNUM")
	private Integer CPZTXNUM;
	
	@IColumn(description="财票再贴现数")
	@Column(name = "CPGTXNUM")
	private Integer CPGTXNUM;
	
	@IColumn(description="银票纸票贴现数")
	@Column(name = "YPZPTXNUM")
	private Integer YPZPTXNUM;
	
	@IColumn(description="银票电票贴现数")
	@Column(name = "YPDPTXNUM")
	private Integer YPDPTXNUM;
	
	
	@IColumn(description="银票纸票销账数")
	@Column(name = "YPZPXZNUM")
	private Integer YPZPXZNUM;
	
	@IColumn(description="银票电票销账数")
	@Column(name = "YPDPXZNUM")
	private Integer YPDPXZNUM;
	
	@IColumn(description="银票转贴现数")
	@Column(name = "YPZTXNUM")
	private Integer YPZTXNUM;
	
	@IColumn(description="票据作废")
	@Column(name = "YPGTXNUM")
	private Integer YPGTXNUM;

	public String getAUTOID() {
		return AUTOID;
	}

	public void setAUTOID(String aUTOID) {
		AUTOID = aUTOID;
	}

	

	public Integer getDKXZNUM() {
		return DKXZNUM;
	}

	public void setDKXZNUM(String dKXZNUM) {
		DKXZNUM =TypeParse.parseInt(dKXZNUM);
	}

	public Integer getDKHKNUM() {
		return DKHKNUM;
	}

	public void setDKHKNUM(String dKHKNUM) {
		DKHKNUM = TypeParse.parseInt(dKHKNUM);
	}

	public Integer getDKJQNUM() {
		return DKJQNUM;
	}

	public void setDKJQNUM(String dKJQNUM) {
		DKJQNUM = TypeParse.parseInt(dKJQNUM);
	}

	public Integer getDKZQNUM() {
		return DKZQNUM;
	}

	public void setDKZQNUM(String dKZQNUM) {
		DKZQNUM = TypeParse.parseInt(dKZQNUM);
	}

	public Integer getRZXZNUM() {
		return RZXZNUM;
	}

	public void setRZXZNUM(String rZXZNUM) {
		RZXZNUM = TypeParse.parseInt(rZXZNUM);
	}

	public Integer getRZHKNUM() {
		return RZHKNUM;
	}

	public void setRZHKNUM(String rZHKNUM) {
		RZHKNUM = TypeParse.parseInt(rZHKNUM);
	}

	public Integer getRZJQNUM() {
		return RZJQNUM;
	}

	public void setRZJQNUM(String rZJQNUM) {
		RZJQNUM = TypeParse.parseInt(rZJQNUM);
	}

	public Integer getRZZQNUM() {
		return RZZQNUM;
	}

	public void setRZZQNUM(String rZZQNUM) {
		RZZQNUM = TypeParse.parseInt(rZZQNUM);
	}

	public Integer getBLXZNUM() {
		return BLXZNUM;
	}

	public void setBLXZNUM(String bLXZNUM) {
		BLXZNUM = TypeParse.parseInt(bLXZNUM);
	}

	public Integer getBLHKNUM() {
		return BLHKNUM;
	}

	public void setBLHKNUM(String bLHKNUM) {
		BLHKNUM = TypeParse.parseInt(bLHKNUM);
	}

	public Integer getSXXZNUM() {
		return SXXZNUM;
	}

	public void setSXXZNUM(String sXXZNUM) {
		SXXZNUM = TypeParse.parseInt(sXXZNUM);
	}

	public Integer getCPKPNUM() {
		return CPKPNUM;
	}

	public void setCPKPNUM(String cPKPNUM) {
		CPKPNUM = TypeParse.parseInt(cPKPNUM);
	}

	public Integer getCPZPTXNUM() {
		return CPZPTXNUM;
	}

	public void setCPZPTXNUM(String cPZPTXNUM) {
		CPZPTXNUM = TypeParse.parseInt(cPZPTXNUM);
	}

	public Integer getCPDPTXNUM() {
		return CPDPTXNUM;
	}

	public void setCPDPTXNUM(String cPDPTXNUM) {
		CPDPTXNUM = TypeParse.parseInt(cPDPTXNUM);
	}

	public Integer getCPZPCDNUM() {
		return CPZPCDNUM;
	}

	public void setCPZPCDNUM(String cPZPCDNUM) {
		CPZPCDNUM = TypeParse.parseInt(cPZPCDNUM);
	}

	public Integer getCPDPCDNUM() {
		return CPDPCDNUM;
	}

	public void setCPDPCDNUM(String cPDPCDNUM) {
		CPDPCDNUM = TypeParse.parseInt(cPDPCDNUM);
	}

	public Integer getCPZTXNUM() {
		return CPZTXNUM;
	}

	public void setCPZTXNUM(String cPZTXNUM) {
		CPZTXNUM = TypeParse.parseInt(cPZTXNUM);
	}

	public Integer getCPGTXNUM() {
		return CPGTXNUM;
	}

	public void setCPGTXNUM(String cPGTXNUM) {
		CPGTXNUM = TypeParse.parseInt(cPGTXNUM);
	}

	public Integer getYPZPTXNUM() {
		return YPZPTXNUM;
	}

	public void setYPZPTXNUM(String yPZPTXNUM) {
		YPZPTXNUM = TypeParse.parseInt(yPZPTXNUM);
	}

	public Integer getYPDPTXNUM() {
		return YPDPTXNUM;
	}

	public void setYPDPTXNUM(String yPDPTXNUM) {
		YPDPTXNUM = TypeParse.parseInt(yPDPTXNUM);
	}

	public Integer getYPZPXZNUM() {
		return YPZPXZNUM;
	}

	public void setYPZPXZNUM(String yPZPXZNUM) {
		YPZPXZNUM = TypeParse.parseInt(yPZPXZNUM);
	}

	public Integer getYPDPXZNUM() {
		return YPDPXZNUM;
	}

	public void setYPDPXZNUM(String yPDPXZNUM) {
		YPDPXZNUM = TypeParse.parseInt(yPDPXZNUM);
	}

	public Integer getYPZTXNUM() {
		return YPZTXNUM;
	}

	public void setYPZTXNUM(String yPZTXNUM) {
		YPZTXNUM = TypeParse.parseInt(yPZTXNUM);
	}

	public Integer getYPGTXNUM() {
		return YPGTXNUM;
	}

	public void setYPGTXNUM(String yPGTXNUM) {
		YPGTXNUM = TypeParse.parseInt(yPGTXNUM);
	}

	public String getDATTIME() {
		return DATTIME;
	}

	public void setDATTIME(String dATTIME) {
		DATTIME = dATTIME;
	}

}

