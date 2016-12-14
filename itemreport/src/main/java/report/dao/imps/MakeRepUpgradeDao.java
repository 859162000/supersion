package report.dao.imps;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MakeRepUpgradeDao extends BaseHibernateDao implements IParamVoidResultExecute{
	
	/** 报表基础信息文件夹 **/
	private String baseData = "BASEDATA";
	
	/** 计算、校验、汇总规则文件夹 **/
	private String ruleData = "RULEDATA";
	
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
	
	//更新基础数据
	void readFileUpdate(String path) throws Exception {
		ObjectInputStream oi = null;
		try{
			File file  = new File(path);
			if(!file.exists())
				return;
			oi = new ObjectInputStream(new FileInputStream(file));
			Object obj = null;
			List list = new ArrayList();
			while ((obj = oi.readObject()) != null) {
				list.add(obj);
				if(list.size()==100){
					this.getHibernateTemplate().saveOrUpdateAll(list);
					list.clear();
				}
			}
			this.getHibernateTemplate().saveOrUpdateAll(list);
			this.getHibernateTemplate().flush();
		}catch(Exception e){
			throw e;
		}finally{
			if(null != oi)
				oi.close();
		}
	}
	
	//计算、校验、汇总实例更新
	public void repXmlFileUpdate(File file,XStream xstream) throws Exception{
		RepVO rep = (RepVO) xstream.fromXML(file);
		//计算实例
		List<CalcReVO> list_CalcRe = rep.getCalcReList();
		updateCalc(list_CalcRe, rep.getCalcInst());
		//校验实例
		List<CheckReVO> list_Check = rep.getCheckReList();
		updateCheck(list_Check, rep.getCheckInst());
		//汇总实例
//		List<MergeReVO> list_MergeRe = rep.getMergeReList();
	}
	
	
	public void updateCalc(List<CalcReVO> reList,String instId) {
		if(StringUtils.isBlank(instId)||reList.isEmpty())
			return;
		String sql_del_re = "delete from "+AnnotationUtil.getTableName(CalcReVO.class) +" where autoCalculationID=? and autoCalculationRuleID not in (select autoCalculationRuleID from CalculationRule where source='1')";
		String sql_del_re_ = "delete from "+AnnotationUtil.getTableName(CalcReVO.class) +" where autoCalculationRuleID=?";
		String sql_add_re = "insert into ExampleInfoRule(autoExampleRuleID,autoCalculationID,autoCalculationRuleID) values(?,?,?)";
		String sql_del_rule = "delete from " +AnnotationUtil.getTableName(CalcRuleVO.class)+" where autoCalculationRuleID=?";
		String sql_add_rule = "insert into CalculationRule(autoCalculationRuleID,strCalculationRuleName,strDescription,intOrder,strExpression,startdate,enddate,intPrecise,strCalRoundMethod,strItemCode,strPropertyCode,currencyType,strFreq,strExtendDimension1,strExtendDimension2,source) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql_del_ruleInfo = "delete from " +AnnotationUtil.getTableName(CalcRuleInfoVO.class)+" where autoCalculationRuleID=? ";
		String sql_add_ruleInfo ="insert into ItemsCalculation(autoItemsCalculationID,strItemType,autoCalculationRuleID,intOrder,strItemValue) values(?,?,?,?,?)";
		for(CalcReVO re:reList){
			CalcRuleVO rule = re.getCalcRule();
			this.getSession().createSQLQuery(sql_del_re_).setString(0, rule.getAutoCalculationRuleID()).executeUpdate();
			this.getSession().createSQLQuery(sql_del_ruleInfo).setString(0, rule.getAutoCalculationRuleID()).executeUpdate();
			this.getSession().createSQLQuery(sql_del_rule).setString(0, rule.getAutoCalculationRuleID()).executeUpdate();
			this.getSession().createSQLQuery(sql_add_rule)
			.setString(0, rule.getAutoCalculationRuleID())
			.setString(1, rule.getStrCalculationRuleName())
			.setString(2, rule.getStrDescription())
			.setInteger(3, rule.getIntOrder())
			.setString(4, rule.getStrExpression())
			.setDate(5, rule.getStartdate())
			.setDate(6, rule.getEnddate())
			.setInteger(7, rule.getIntPrecise())
			.setString(8, rule.getStrCalRoundMethod())
			.setString(9, rule.getStrItemCode())
			.setString(10, rule.getStrPropertyCode())
			.setString(11, rule.getCurrencyType())
			.setString(12, rule.getStrFreq())
			.setString(13, rule.getStrExtendDimension1())
			.setString(14, rule.getStrExtendDimension1())
			.setString(15, rule.getSource())
			.executeUpdate();
			List<CalcRuleInfoVO> ruleInfoList = rule.getRuleInfoList();
			for(CalcRuleInfoVO ruleInfo : ruleInfoList){
				this.getSession().createSQLQuery(sql_add_ruleInfo)
				.setString(0, ruleInfo.getAutoItemsCalculationID())
				.setString(1, ruleInfo.getStrItemType())
				.setString(2, ruleInfo.getAutoCalculationRuleID())
				.setInteger(3, ruleInfo.getIntOrder())
				.setString(4, ruleInfo.getStrItemValue())
				.executeUpdate();
			}
		}
		this.getSession().createSQLQuery(sql_del_re).setString(0, instId).executeUpdate();
		for(CalcReVO re:reList){
			this.getSession().createSQLQuery(sql_add_re)
			.setString(0, re.getAutoExampleRuleID())
			.setString(1, re.getAutoCalculationID())
			.setString(2, re.getAutoCalculationRuleID())
			.executeUpdate();
		}
		
	}
	
	public void updateCheck(List<CheckReVO> reList,String instId) {
		if(StringUtils.isBlank(instId))
			return;
		String sql_del_re = "delete from "+AnnotationUtil.getTableName(CheckReVO.class) +" where autoCheckInstanceID=? and autoCalculationRuleID not in (select autoCheckulationRuleID from CheckulationRule where source='1')";
		String sql_del_re_ = "delete from "+AnnotationUtil.getTableName(CheckReVO.class) +" where autoCalculationRuleID=?";
		String sql_add_re = "insert into CheckInstanceRule(autoCheckInstanceRuleID,autoCheckInstanceID,autoCalculationRuleID) values(?,?,?)";
		String sql_del_rule = "delete from " +AnnotationUtil.getTableName(CheckRuleVO.class)+" where autoCalculationRuleID=?";
		String sql_add_rule = "insert into CheckRule(autoCalculationRuleID,strCalculationRuleName,strItemCode,strPropertyCode,currencyType,startdate,enddate,strExtendDimension1,strExtendDimension2,strDescription,intOrder,strExpression,strFreq,dblTolerance,compareType,source) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		String sql_del_ruleInfo = "delete from " +AnnotationUtil.getTableName(CheckRuleInfoVO.class)+" where autoCalculationRuleID=? ";
		String sql_add_ruleInfo ="insert into ItemsCheck(autoItemsCalculationID,strItemType,autoCalculationRuleID,intOrder,strItemValue) values(?,?,?,?,?)";
		for(CheckReVO re:reList){
			CheckRuleVO rule = re.getCheckRule();
			this.getSession().createSQLQuery(sql_del_re_).setString(0, rule.getAutoCalculationRuleID());
			this.getSession().createSQLQuery(sql_del_ruleInfo).setString(0, rule.getAutoCalculationRuleID());
			this.getSession().createSQLQuery(sql_del_rule).setString(0, rule.getAutoCalculationRuleID());
			this.getSession().createSQLQuery(sql_add_rule)
			.setString(0, rule.getAutoCalculationRuleID())
			.setString(1, rule.getStrCalculationRuleName())
			.setString(2, rule.getStrItemCode())
			.setString(3, rule.getStrPropertyCode())
			.setString(4, rule.getCurrencyType())
			.setParameter(5, rule.getStartdate())
			.setParameter(6, rule.getEnddate())
			.setString(7, rule.getStrExtendDimension1())
			.setString(8, rule.getStrExtendDimension2())
			.setString(9, rule.getStrDescription())
			.setParameter(10, rule.getIntOrder())
			.setString(11, rule.getStrExpression())
			.setString(12, rule.getStrFreq())
			.setParameter(13, rule.getDblTolerance())
			.setString(14, rule.getCompareType())
			.setString(15, rule.getSource());
			List<CheckRuleInfoVO> ruleInfoList = rule.getRuleInfoList();
			for(CheckRuleInfoVO ruleInfo : ruleInfoList){
				this.getSession().createSQLQuery(sql_add_ruleInfo)
				.setString(0, ruleInfo.getAutoItemsCalculationID())
				.setString(1, ruleInfo.getStrItemType())
				.setString(2, ruleInfo.getAutoCalculationRuleID())
				.setInteger(3, ruleInfo.getIntOrder())
				.setString(4, ruleInfo.getStrItemValue());
			}
		}
		this.getSession().createSQLQuery(sql_del_re).setString(0, instId);
		for(CheckReVO re:reList){
			this.getSession().createSQLQuery(sql_add_re)
			.setString(0, re.getAutoCheckInstanceRuleID())
			.setString(1, re.getAutoCheckInstanceID())
			.setString(2, re.getAutoCalculationRuleID());
		}
	}
	
	public void other(){
		/*Work work = new Work() {
			@Override
			public void execute(Connection connection) throws SQLException {
				PreparedStatement stm_del_re = connection.prepareStatement(sql_del_re);
				PreparedStatement stm_add_re = connection.prepareStatement(sql_add_re);
				PreparedStatement stm_del_rule = connection.prepareStatement(sql_del_rule);
				PreparedStatement stm_add_rule = connection.prepareStatement(sql_add_rule);
				PreparedStatement stm_del_ruleInfo = connection.prepareStatement(sql_del_ruleInfo);
				PreparedStatement stm_add_ruleInfo = connection.prepareStatement(sql_add_ruleInfo);
				for(CalcReVO re:reList){
					CalcRuleVO rule = re.getCalcRule();
					stm_del_ruleInfo.setString(0, rule.getAutoCalculationRuleID());
					stm_del_ruleInfo.addBatch();
					stm_del_rule.setString(0, rule.getAutoCalculationRuleID());
					stm_del_rule.addBatch();
					stm_add_rule.setString(0, rule.getAutoCalculationRuleID());
					stm_add_rule.setString(1, rule.getStrCalculationRuleName());
					stm_add_rule.setString(2, rule.getStrDescription());
					stm_add_rule.setInt(3, rule.getIntOrder());
					stm_add_rule.setString(4, rule.getStrExpression());
					stm_add_rule.setDate(5, rule.getStartdate());
					stm_add_rule.setDate(6, rule.getEnddate());
					stm_add_rule.setInt(7, rule.getIntPrecise());
					stm_add_rule.setString(8, rule.getStrCalRoundMethod());
					stm_add_rule.setString(9, rule.getStrItemCode());
					stm_add_rule.setString(10, rule.getStrPropertyCode());
					stm_add_rule.setString(11, rule.getCurrencyType());
					stm_add_rule.setString(12, rule.getStrFreq());
					stm_add_rule.setString(13, rule.getStrExtendDimension1());
					stm_add_rule.setString(14, rule.getStrExtendDimension1());
					
				}
			}
		};*/
	}
	

	@Override
	public void paramVoidResultExecute(Object param) throws Exception {
		Object[] objects = (Object[])param;
		if(objects.length>1 && null!=objects[objects.length -1]){
			super.initSessionFactory(objects[objects.length -1]);
		}
		//基础文件
		baseData = objects[0] + File.separator + baseData;
		File baseDataDir = new File(baseData);
		if(baseDataDir.exists()){
			readFileUpdate(baseData+File.separator+"RptInfo.txt");
			readFileUpdate(baseData+File.separator+"ItemInfo.txt");
			readFileUpdate(baseData+File.separator+"CalculationExampleInfo.txt");
			readFileUpdate(baseData+File.separator+"CheckInstance.txt");
			readFileUpdate(baseData+File.separator+"MergeInstance.txt");
		}
		
		//关系文件
		ruleData = param + File.separator + ruleData;
		File ruleDataDir = new File(ruleData);
		if(ruleDataDir.exists()){
			XStream xstream = new XStream();
			initXstream(xstream);
			File[] files = ruleDataDir.listFiles();
			for(File file : files){
				repXmlFileUpdate(file,xstream);
			}
		}
		
	}

}
