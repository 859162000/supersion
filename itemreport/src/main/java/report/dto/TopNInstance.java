package report.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import framework.interfaces.IColumn;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：TopNInstance</P>
 * *********************************<br>
 * <P>类描述：Top-n汇总实例</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-12 上午9:27:44<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-12 上午9:27:44<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@Entity
@Table(name="TopNInstance")
public class TopNInstance implements Serializable {
	
	/**  **/
	private static final long serialVersionUID = -1759524471187616970L;

	@Id
	@Column(name = "uuid", length = 32)
	@GeneratedValue(generator = "system-uuid")   
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")  
	private String uuid;
	
	@IColumn(isNullable = false)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "strRptCode", nullable = false)
	private RptInfo rptInfo;
	
	@Column(name = "startRow")
	@IColumn(description="起始行")
	private int startRow;
	
	@Column(name = "startCol")
	@IColumn(description="起始列")
	private int startCol;
	
	@Column(name = "endRow")
	@IColumn(description="结束行")
	private int endRow;
	
	@Column(name = "endCol")
	@IColumn(description="结束列")
	private int endCol;
	
	@Column(name = "topNCount")
	@IColumn(description="top-n")
	private int topNCount;
	
	@Column(name = "groupCol")
	@IColumn(description="分组列")
	private int groupCol;
	
	@Column(name = "totalCol")
	@IColumn(description="汇总列")
	private int totalCol;
	
	@Column(name = "sumCol")
	@IColumn(description="SUM列",isSpecialCharCheck=true)
	private String sumCol;
	
	@Column(name = "maxCol")
	@IColumn(description="MAX列")
	private String maxCol;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtStDate",nullable = false,length = 16)
	@IColumn(description = "开始时间")
	private Date dtStDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dtEdDate",nullable = false,length = 16)
	@IColumn(description = "结束时间")
	private Date dtEdDate;
	
	@Column(name = "state",nullable = false,length = 2)
	@IColumn(description = "启用",tagMethodName="getTopNInstanceStateTag", isNullable = false)
	private int state = 1;
	//topNInstanceState
	
	public static Map<String,String> getTopNInstanceStateTag(){
		return ShowContext.getInstance().getShowEntityMap().get("topNInstanceState");
	}
	
	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public RptInfo getRptInfo() {
		return rptInfo;
	}

	public void setRptInfo(RptInfo rptInfo) {
		this.rptInfo = rptInfo;
	}

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	public int getStartCol() {
		return startCol;
	}

	public void setStartCol(int startCol) {
		this.startCol = startCol;
	}

	public int getEndCol() {
		return endCol;
	}

	public void setEndCol(int endCol) {
		this.endCol = endCol;
	}

	public int getTopNCount() {
		return topNCount;
	}

	public void setTopNCount(int topNCount) {
		this.topNCount = topNCount;
	}

	public int getGroupCol() {
		return groupCol;
	}

	public void setGroupCol(int groupCol) {
		this.groupCol = groupCol;
	}

	public int getTotalCol() {
		return totalCol;
	}

	public void setTotalCol(int totalCol) {
		this.totalCol = totalCol;
	}

	public Date getDtStDate() {
		return dtStDate;
	}

	public void setDtStDate(Date dtStDate) {
		this.dtStDate = dtStDate;
	}

	public Date getDtEdDate() {
		return dtEdDate;
	}

	public void setDtEdDate(Date dtEdDate) {
		this.dtEdDate = dtEdDate;
	}

	public String getSumCol() {
		return sumCol;
	}

	public void setSumCol(String sumCol) {
		this.sumCol = sumCol;
	}

	public String getMaxCol() {
		return maxCol;
	}

	public void setMaxCol(String maxCol) {
		this.maxCol = maxCol;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
	
	public List<Integer> getSumCols(){
		List<Integer> list = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(sumCol)){
			String[] cols = sumCol.split(",");
			for(String col:cols)
				list.add(Integer.parseInt(col)-startCol);
		}
		return list;
	}
	
	public List<Integer> getMaxCols(){
		List<Integer> list = new ArrayList<Integer>();
		if(StringUtils.isNotBlank(maxCol)){
			String[] cols = maxCol.split(",");
			for(String col:cols)
				list.add(Integer.parseInt(col)-startCol);
		}
		return list;
	}
	
}
