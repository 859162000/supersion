package report.vo;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：RepVo</P>
 * *********************************<br>
 * <P>类描述：报表vo</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 上午11:02:51<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 上午11:02:51<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@XStreamAlias("Rep")
public class RepVO implements Serializable{
	/**  **/
	private static final long serialVersionUID = -6260727594544085054L;
	/** id **/
	@XStreamAsAttribute() 
	private String rptId;
	/** code **/
	@XStreamAsAttribute() 
	private String rptCode;
	/** 版本 **/
	/**  **/
	@XStreamAsAttribute() 
	private String rptVer;
	/** 计算实例 **/
	private String calcInst;
	/** 校验实例 **/
	private String checkInst;
	/** 汇总实例 **/
	private String mergeInst;
	private List<CalcReVO> calcReList;
	private List<CheckReVO> checkReList;
	private List<MergeReVO> mergeReList;
	public String getRptId() {
		return rptId;
	}
	public void setRptId(String rptId) {
		this.rptId = rptId;
	}
	public String getRptCode() {
		return rptCode;
	}
	public void setRptCode(String rptCode) {
		this.rptCode = rptCode;
	}
	public String getRptVer() {
		return rptVer;
	}
	public void setRptVer(String rptVer) {
		this.rptVer = rptVer;
	}
	public List<CalcReVO> getCalcReList() {
		return calcReList;
	}
	public void setCalcReList(List<CalcReVO> calcReList) {
		this.calcReList = calcReList;
	}
	public List<CheckReVO> getCheckReList() {
		return checkReList;
	}
	public void setCheckReList(List<CheckReVO> checkReList) {
		this.checkReList = checkReList;
	}
	public List<MergeReVO> getMergeReList() {
		return mergeReList;
	}
	public void setMergeReList(List<MergeReVO> mergeReList) {
		this.mergeReList = mergeReList;
	}
	public String getCalcInst() {
		return calcInst;
	}
	public void setCalcInst(String calcInst) {
		this.calcInst = calcInst;
	}
	public String getCheckInst() {
		return checkInst;
	}
	public void setCheckInst(String checkInst) {
		this.checkInst = checkInst;
	}
	public String getMergeInst() {
		return mergeInst;
	}
	public void setMergeInst(String mergeInst) {
		this.mergeInst = mergeInst;
	}

}
