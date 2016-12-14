package report.dao.imps;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;

import org.hibernate.SQLQuery;

import report.dto.CalculationExampleInfo;
import report.dto.CheckInstance;
import report.dto.ItemInfo;
import report.dto.MergeInstance;
import report.dto.RptInfo;
import report.helper.AnnotationUtil;
import report.vo.CalcReVO;
import report.vo.CalcRuleInfoVO;
import report.vo.CalcRuleVO;
import report.vo.CheckReVO;
import report.vo.CheckRuleInfoVO;
import report.vo.CheckRuleVO;
import report.vo.MergeReVO;
import report.vo.MergeRuleInfoVO;
import report.vo.MergeRuleVO;
import report.vo.RepVO;

import com.thoughtworks.xstream.XStream;

import framework.dao.imps.BaseHibernateDao;
import framework.interfaces.IParamVoidResultExecute;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：MakeRepPackDao</P>
 * *********************************<br>
 * <P>类描述：制度包制作dao</P>
 * 创建人：王川<br>
 * 创建时间：2016-10-14 下午3:59:19<br>
 * 修改人：王川<br>
 * 修改时间：2016-10-14 下午3:59:19<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class MakeRepPackDao extends BaseHibernateDao implements IParamVoidResultExecute{
	
	/** 报表基础信息文件夹 **/
	private String baseData = "BASEDATA";
	
	/** 计算、校验、汇总规则文件夹 **/
	private String ruleData = "RULEDATA";
	
	//报表信息
	public List<RptInfo> getAllRptInfo(){
		return this.getHibernateTemplate().find("from RptInfo");
	}
	
	//指标信息
	public List<ItemInfo> getAllItemInfo(){
		return this.getHibernateTemplate().find("from ItemInfo");
	}
	
	//计算实例
	public List<CalculationExampleInfo> getAllCalculationExampleInfo(){
		return this.getHibernateTemplate().find("from CalculationExampleInfo");
	}
	
	//校验实例
	public List<CheckInstance> getAllCheckInstance(){
		return this.getHibernateTemplate().find("from CheckInstance");
	}
	
	//汇总实例
	public List<MergeInstance> getAllMergeInstance(){
		return this.getHibernateTemplate().find("from MergeInstance");
	}
	
	public RepVO getRepVo(RptInfo reptInfo) {
		RepVO repVO = new RepVO();
		repVO.setRptId(reptInfo.getStrRptCode());
		repVO.setRptCode(reptInfo.getStrBCode());
		repVO.setRptVer(reptInfo.getStrRptVersion());
		repVO.setCalcInst(reptInfo.getStrCalcInst());
		repVO.setCheckInst(reptInfo.getStrCheckInst());
		repVO.setMergeInst(reptInfo.getStrMergeInst());
		//计算实例
		List<CalcReVO> list_CalcRe = getCalcReVOList(reptInfo);
		repVO.setCalcReList(list_CalcRe);
		for(CalcReVO calcRe:list_CalcRe){
			CalcRuleVO calcRule = getCalcRuleVO(calcRe);
			calcRe.setCalcRule(calcRule);
			List<CalcRuleInfoVO> calcRuleInfoList = getCalcRuleInfoVOList(calcRule);
			calcRule.setRuleInfoList(calcRuleInfoList);
		}
		//校验实例
		List<CheckReVO> list_CheckRe = getCheckReVOList(reptInfo);
		repVO.setCheckReList(list_CheckRe);
		for(CheckReVO checkRe:list_CheckRe){
			CheckRuleVO checkRule = getCheckRuleVO(checkRe);
			checkRe.setCheckRule(checkRule);
			List<CheckRuleInfoVO> checkRuleInfoList = getCheckRuleInfoVOList(checkRule);
			checkRule.setRuleInfoList(checkRuleInfoList);
		}
		//汇总实例
		/*List<MergeReVO> list_MergeRe = getMergeReVOList(reptInfo);
		repVO.setMergeReList(list_MergeRe);
		for(MergeReVO mergeRe:list_MergeRe){
			List<MergeRuleVO> mergeRuleList = getMergeRuleVO(mergeRe);
			mergeRe.setRuleList(mergeRuleList);
			List<MergeRuleInfoVO> mergeRuleInfoList = getMergeRuleInfoVOList(mergeRe);
			mergeRe.setRuleInfoList(mergeRuleInfoList);
		}*/
		return repVO;
	}
	
	/**-------------------------------------计算实例 start----------------------------------------**/
	List<CalcReVO> getCalcReVOList(RptInfo reptInfo){
		String sql = AnnotationUtil.getTSelectSqlStr(CalcReVO.class).append(" where autoCalculationID=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, reptInfo.getStrCalcInst());
		query.setResultTransformer(new ResultTransformerByWC(CalcReVO.class));
		return query.list();
	}
	
	CalcRuleVO getCalcRuleVO(CalcReVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(CalcRuleVO.class).append(" where autoCalculationRuleID=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getAutoCalculationRuleID());
		query.setResultTransformer(new ResultTransformerByWC(CalcRuleVO.class));
		return (CalcRuleVO) query.uniqueResult();
	}
	
	List<CalcRuleInfoVO> getCalcRuleInfoVOList(CalcRuleVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(CalcRuleInfoVO.class).append(" where autoCalculationRuleID=? ").toString();
		sql += " order by intOrder asc";
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getAutoCalculationRuleID());
		query.setResultTransformer(new ResultTransformerByWC(CalcRuleInfoVO.class));
		return query.list();
	}
	/**-------------------------------------计算实例 end------------------------------------------**/
	
	/**-------------------------------------校验实例 start----------------------------------------**/
	List<CheckReVO> getCheckReVOList(RptInfo reptInfo){
		String sql = AnnotationUtil.getTSelectSqlStr(CheckReVO.class).append(" where autoCheckInstanceID=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, reptInfo.getStrCheckInst());
		query.setResultTransformer(new ResultTransformerByWC(CheckReVO.class));
		return query.list();
	}
	
	CheckRuleVO getCheckRuleVO(CheckReVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(CheckRuleVO.class).append(" where autoCalculationRuleID=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getAutoCalculationRuleID());
		query.setResultTransformer(new ResultTransformerByWC(CheckRuleVO.class));
		return (CheckRuleVO) query.uniqueResult();
	}
	
	List<CheckRuleInfoVO> getCheckRuleInfoVOList(CheckRuleVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(CheckRuleInfoVO.class).append(" where autoCalculationRuleID=? ").toString();
		sql += " order by intOrder asc";
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getAutoCalculationRuleID());
		query.setResultTransformer(new ResultTransformerByWC(CheckRuleInfoVO.class));
		return query.list();
	}
	/**-------------------------------------校验实例 end------------------------------------------**/
	
	/**-------------------------------------汇总实例 start----------------------------------------**/
	List<MergeReVO> getMergeReVOList(RptInfo reptInfo){
		String sql = AnnotationUtil.getTSelectSqlStr(MergeReVO.class).append(" where MergeSummary=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, reptInfo.getStrMergeInst());
		query.setResultTransformer(new ResultTransformerByWC(MergeReVO.class));
		return query.list();
	}
	
	List<MergeRuleVO> getMergeRuleVO(MergeReVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(MergeRuleVO.class).append(" where autoMergeID=?").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getStrMergeInstanceid());
		query.setResultTransformer(new ResultTransformerByWC(MergeRuleVO.class));
		return query.list();
	}
	
	List<MergeRuleInfoVO> getMergeRuleInfoVOList(MergeReVO vo){
		String sql = AnnotationUtil.getTSelectSqlStr(MergeRuleInfoVO.class).append(" where strMergeSummaryid=? ").toString();
		SQLQuery query= this.getSession().createSQLQuery(sql);
		query.setString(0, vo.getMergeSummary());
		query.setResultTransformer(new ResultTransformerByWC(MergeRuleInfoVO.class));
		return query.list();
	}
	/**-------------------------------------汇总实例  end------------------------------------------**/
	@SuppressWarnings("rawtypes") 
	void writeFile(String path,List list) throws Exception{
		File file = new File(path);
		ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(file));
		Iterator iter = list.iterator();
		while(iter.hasNext()){
			oo.writeObject(iter.next());
		}
		oo.writeObject(null);
		oo.flush();
		oo.close();
	}
	
	void initXstream(XStream xstream){
		xstream.alias("Report", RepVO.class);
		xstream.alias("CalcRe", CalcReVO.class);
		xstream.alias("CalcRuleInfo",CalcRuleInfoVO.class);
		xstream.alias("CalcRule",CalcRuleVO.class);
		xstream.alias("CheckRe", CheckReVO.class);
		xstream.alias("CheckRuleInfo",CheckRuleInfoVO.class);
		xstream.alias("CheckRule",CheckRuleVO.class);
		xstream.alias("MergeRe", MergeReVO.class);
		xstream.alias("MergeRuleInfo",MergeRuleInfoVO.class);
		xstream.alias("MergeRule",MergeRuleVO.class);
	}

	@Override
	public void paramVoidResultExecute(Object param) throws Exception {
		baseData = param + File.separator + baseData;
		File baseDataDir = new File(baseData);
		baseDataDir.mkdir();
		ruleData = param + File.separator + ruleData;
		File ruleDataDir = new File(ruleData);
		ruleDataDir.mkdir();
		List<RptInfo> rptList = getAllRptInfo();
		//基础信息
		writeFile(baseData+File.separator+"RptInfo.txt", rptList);
		writeFile(baseData+File.separator+"ItemInfo.txt", getAllItemInfo());
		writeFile(baseData+File.separator+"CalculationExampleInfo.txt", getAllCalculationExampleInfo());
		writeFile(baseData+File.separator+"CheckInstance.txt", getAllCheckInstance());
		writeFile(baseData+File.separator+"MergeInstance.txt", getAllMergeInstance());
		
		//报表对应关系信息
		XStream xstream = new XStream();
		initXstream(xstream);
		OutputStream xmlFile = null;
		try{
			for(RptInfo rpt:rptList){
				RepVO rep =	getRepVo(rpt);
				File file = new File(ruleData+File.separator+rep.getRptId()+".xml");
				xmlFile = new FileOutputStream(file);
				xstream.toXML(rep,xmlFile);
				xmlFile.close();
			}
		}catch(Exception e){
			throw e;
		}finally{
			if(null != xmlFile){
				xmlFile.close();
			}
		}
	}

}
