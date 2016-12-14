package zxptsystem.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import framework.interfaces.IColumn;
import framework.interfaces.IEntity;


@Entity
@Table(name = "UserModel")
@IEntity(description="个人身份信息")
public class UserModel  implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ZJHM", length = 50)
	@IColumn(description="证件号码")
	private String ZJHM;
	
	@Column(name = "ZJLX", length = 50)
	@IColumn(description="证件类型")
	private String ZJLX;
	
	@Column(name = "XM", length = 50, nullable = false)
	@IColumn(description="姓名", isKeyName=true, isNullable = false)
	private String XM;
	
	@IColumn(description="性别")
	@Column(name = "XB", nullable =true, length = 50)
	private String XB;
	
	@Column(name = "CSRQ", length = 50, nullable = true)
	@IColumn(description="出生日期")
	private String CSRQ;
	
	@IColumn(description="婚姻状况")
	@Column(name = "HYZK", nullable =true, length = 50)
	private String HYZK;
	
	@IColumn(description="最高学历")
	@Column(name = "ZGXL",length = 50,  nullable =true)
	private String ZGXL;

	@IColumn(description="最高学位")
	@Column(name = "ZGXW",length = 50,  nullable =true)
	private String ZGXW;
	
	@Column(name = "ZZDH", length = 50, nullable = true)
	@IColumn(description="住宅电话")
	private String ZZDH;
	
	@Column(name = "SJHM", length = 50, nullable = true)
	@IColumn(description="手机号码")
	private String SJHM;
	
	@Column(name = "DWDH", length = 50, nullable = true)
	@IColumn(description="单位电话")
	private String DWDH;
	
	@Column(name = "DZYX", length = 100, nullable = true)
	@IColumn(description="电子邮箱")
	private String DZYX;
	
	@Column(name = "TXDZ", length = 200, nullable = true)
	@IColumn(description="通讯地址")
	private String TXDZ;
	
	@Column(name = "TXDZYZBM", length = 50, nullable = true)
	@IColumn(description="通讯地址邮政编码")
	private String TXDZYZBM;

	@Column(name = "HJDZ", length = 200, nullable = true)
	@IColumn(description="户籍地址")
	private String HJDZ;

	@Column(name = "POXM", length = 100, nullable = true)
	@IColumn(description="配偶姓名")
	private String POXM;
	
	@IColumn(description="配偶证件类型")
	@Column(name = "POZJLX", nullable =true)
	private String POZJLX;
	
	@Column(name = "POZJHM", length = 50, nullable = true)
	@IColumn(description="配偶证件号码")
	private String POZJHM;
	
	@Column(name = "POGZDW", length = 200, nullable = true)
	@IColumn(description="配偶工作单位")
	private String POGZDW;
	
	@Column(name = "POLXDH", length = 50, nullable = true)
	@IColumn(description="配偶联系电话")
	private String POLXDH;
	
	@Column(name = "JZDZ", length = 200, nullable = true)
	@IColumn(description="居住地址")
	private String JZDZ;
	
	@Column(name = "ZJDZYZBM", length = 50, nullable = true)
	@IColumn(description="居住地址邮政编码")
	private String ZJDZYZBM;

	@IColumn(description="居住状况")
	@Column(name = "ZJZK", length = 50,nullable =true)
	private String ZJZK;

	@IColumn(description="职业")
	@Column(name = "ZY", length = 50,nullable =true)
	private String ZY;
	
	@Column(name = "DWMC", length = 200, nullable = true)
	@IColumn(description="单位名称")
	private String DWMC;
	
	@IColumn(description="单位所属行业")
	@Column(name = "DWSSHY",length = 50, nullable =true)
	private String DWSSHY;
	
	@Column(name = "DWDZ", length = 200, nullable = true)
	@IColumn(description="单位地址")
	private String DWDZ;
	
	@Column(name = "DWDZYZBM", length = 50, nullable = true)
	@IColumn(description="单位地址邮政编码")
	private String DWDZYZBM;

	@Column(name = "BDWGZQSNF", length = 50, nullable = true)
	@IColumn(description="本单位工作起始年份")
	private String BDWGZQSNF;
	
	@IColumn(description="职务")
	@Column(name = "ZW",length = 50, nullable =true)
	private String ZW;
	
	@IColumn(description="职称")
	@Column(name = "ZC", length = 50,nullable =true)
	private String ZC;
	
	@Column(name = "NSR", length = 50, nullable = true)
	@IColumn(description="年收入")
	private String NSR;
	
	@Column(name = "GZZH", length = 100, nullable = true)
	@IColumn(description="工资账号")
	private String GZZH;
	

	@Column(name = "GZZHKHYH", length = 50, nullable = true)
	@IColumn(description="工资账户开户银行")
	private String GZZHKHYH;

	@Transient
	@IColumn(isListShow=true,description="类名")
	private String className;
	
	@Transient
	@IColumn(isListShow=true,description="字段名")
	private String fieldNameArray;

	public String getZJHM() {
		return ZJHM;
	}


	public void setZJHM(String zJHM) {
		ZJHM = zJHM;
	}


	public String getZJLX() {
		return ZJLX;
	}


	public void setZJLX(String zJLX) {
		ZJLX = zJLX;
	}


	public String getXM() {
		return XM;
	}


	public void setXM(String xM) {
		XM = xM;
	}


	public String getXB() {
		return XB;
	}


	public void setXB(String xB) {
		XB = xB;
	}


	public String getCSRQ() {
		return CSRQ;
	}


	public void setCSRQ(String cSRQ) {
		CSRQ = cSRQ;
	}


	public String getHYZK() {
		return HYZK;
	}


	public void setHYZK(String hYZK) {
		HYZK = hYZK;
	}


	public String getZGXL() {
		return ZGXL;
	}


	public void setZGXL(String zGXL) {
		ZGXL = zGXL;
	}


	public String getZGXW() {
		return ZGXW;
	}


	public void setZGXW(String zGXW) {
		ZGXW = zGXW;
	}


	public String getZZDH() {
		return ZZDH;
	}


	public void setZZDH(String zZDH) {
		ZZDH = zZDH;
	}


	public String getSJHM() {
		return SJHM;
	}


	public void setSJHM(String sJHM) {
		SJHM = sJHM;
	}


	public String getDWDH() {
		return DWDH;
	}


	public void setDWDH(String dWDH) {
		DWDH = dWDH;
	}


	public String getDZYX() {
		return DZYX;
	}


	public void setDZYX(String dZYX) {
		DZYX = dZYX;
	}


	public String getTXDZ() {
		return TXDZ;
	}


	public void setTXDZ(String tXDZ) {
		TXDZ = tXDZ;
	}


	public String getTXDZYZBM() {
		return TXDZYZBM;
	}


	public void setTXDZYZBM(String tXDZYZBM) {
		TXDZYZBM = tXDZYZBM;
	}


	public String getHJDZ() {
		return HJDZ;
	}


	public void setHJDZ(String hJDZ) {
		HJDZ = hJDZ;
	}


	public String getPOXM() {
		return POXM;
	}


	public void setPOXM(String pOXM) {
		POXM = pOXM;
	}


	public String getPOZJLX() {
		return POZJLX;
	}


	public void setPOZJLX(String pOZJLX) {
		POZJLX = pOZJLX;
	}


	public String getPOZJHM() {
		return POZJHM;
	}


	public void setPOZJHM(String pOZJHM) {
		POZJHM = pOZJHM;
	}


	public String getPOGZDW() {
		return POGZDW;
	}


	public void setPOGZDW(String pOGZDW) {
		POGZDW = pOGZDW;
	}


	public String getPOLXDH() {
		return POLXDH;
	}


	public void setPOLXDH(String pOLXDH) {
		POLXDH = pOLXDH;
	}


	public String getJZDZ() {
		return JZDZ;
	}


	public void setJZDZ(String jZDZ) {
		JZDZ = jZDZ;
	}


	public String getZJDZYZBM() {
		return ZJDZYZBM;
	}


	public void setZJDZYZBM(String zJDZYZBM) {
		ZJDZYZBM = zJDZYZBM;
	}


	public String getZJZK() {
		return ZJZK;
	}


	public void setZJZK(String zJZK) {
		ZJZK = zJZK;
	}


	public String getZY() {
		return ZY;
	}


	public void setZY(String zY) {
		ZY = zY;
	}


	public String getDWMC() {
		return DWMC;
	}


	public void setDWMC(String dWMC) {
		DWMC = dWMC;
	}


	public String getDWSSHY() {
		return DWSSHY;
	}


	public void setDWSSHY(String dWSSHY) {
		DWSSHY = dWSSHY;
	}


	public String getDWDZ() {
		return DWDZ;
	}


	public void setDWDZ(String dWDZ) {
		DWDZ = dWDZ;
	}


	public String getDWDZYZBM() {
		return DWDZYZBM;
	}


	public void setDWDZYZBM(String dWDZYZBM) {
		DWDZYZBM = dWDZYZBM;
	}


	public String getBDWGZQSNF() {
		return BDWGZQSNF;
	}


	public void setBDWGZQSNF(String bDWGZQSNF) {
		BDWGZQSNF = bDWGZQSNF;
	}


	public String getZW() {
		return ZW;
	}


	public void setZW(String zW) {
		ZW = zW;
	}


	public String getZC() {
		return ZC;
	}


	public void setZC(String zC) {
		ZC = zC;
	}


	public String getNSR() {
		return NSR;
	}


	public void setNSR(String nSR) {
		NSR = nSR;
	}


	public String getGZZH() {
		return GZZH;
	}


	public void setGZZH(String gZZH) {
		GZZH = gZZH;
	}


	public String getGZZHKHYH() {
		return GZZHKHYH;
	}


	public void setGZZHKHYH(String gZZHKHYH) {
		GZZHKHYH = gZZHKHYH;
	}


	public String getClassName() {
		return className;
	}


	public void setClassName(String className) {
		this.className = className;
	}


	public String getFieldNameArray() {
		return fieldNameArray;
	}


	public void setFieldNameArray(String fieldNameArray) {
		this.fieldNameArray = fieldNameArray;
	}
	
	
	
	
}
