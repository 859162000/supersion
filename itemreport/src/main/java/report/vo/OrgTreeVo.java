package report.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：OrgTreeVo</P>
 * *********************************<br>
 * <P>类描述：汇总机构树形结构</P>
 * 创建人：王川<br>
 * 创建时间：2016-9-14 下午2:34:22<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-14 下午2:34:22<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class OrgTreeVo implements Serializable{
	
	/**  **/
	private static final long serialVersionUID = -5373219489532886187L;

	private String orgId;
	
	private String pOrgId;
	
	private int level=0;
	
	private boolean isLeaf = false;
	
	private List<OrgTreeVo> subOrgs = null;
	
	public OrgTreeVo(){}
	
	public OrgTreeVo(String orgId,String pOrgId){
		this.orgId = orgId;
		this.pOrgId = pOrgId;
	}
	
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	public String getpOrgId() {
		return pOrgId;
	}
	public void setpOrgId(String pOrgId) {
		this.pOrgId = pOrgId;
	}
	public List<OrgTreeVo> getSubOrgs() {
		return subOrgs;
	}
	public void setSubOrgs(List<OrgTreeVo> subOrgs) {
		this.subOrgs = subOrgs;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}
}
