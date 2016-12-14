package report.service.imps;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import report.dao.imps.ItemDataCacheManger;
import report.dto.ItemBindInfo;
import report.dto.ItemData;
import report.dto.ItemInfo;
import report.dto.MergeRule;
import report.dto.MergeSummary;
import report.dto.TaskRptInst;
import report.dto.TopNInstance;
import report.vo.OrgTreeVo;
import coresystem.dto.InstInfo;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseTShowService;
import framework.services.interfaces.MessageResult;
import framework.show.ShowContext;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ReportTopNService</P>
 * *********************************<br>
 * <P>类描述：top-n汇总计算</P>
 * <p>单家汇总机构+多张汇总表方式</p>
 * <p>top-n只能对本机构本期数据进行汇总，如果表中有其它机构其它期数据，将视为本期数据，保存到数据库中</p>
 * 创建人：王川<br>
 * 创建时间：2016-9-12 下午2:20:41<br>
 * 修改人：王川<br>
 * 修改时间：2016-9-12 下午2:20:41<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ReportTopNService extends BaseTShowService {
	
	private static final String XLS =".xls";
	//任务实例
	private TaskRptInst taskRptInst = null;
	//汇总实例
	private MergeSummary _mergeSummary = null;
	//报表topN实例
	private TopNInstance _repTopN = null;
	//偏移后的分组_n
	private int _n = 0;
	//机构汇总规则
	protected List<MergeRule> mergeRuleList=null;
	//报表临时指标信息
	private List<String> _repItemCodes = new ArrayList<String>();
	//报表临时指标信息Map
	private Map<String,ItemBindInfo> _itemBindMap = new HashMap<String, ItemBindInfo>();
	//行列对应指标(已偏移)
	private Map<String,String> _rowColItemMap = new HashMap<String, String>();
	//临时数据区域
	private List<ItemData[]> _itemDatas = new ArrayList<ItemData[]>();
	//汇总后的临时数据区域(做节点层次汇总用)
	//private List<ItemData[]> _pItemDatas = new ArrayList<ItemData[]>();
	//单机构数据表临时数据
	//private List<ItemData[]> _tmpItemDatas = new ArrayList<ItemData[]>();
	//临时分组数据
	private Map<String,List<ItemData[]>> _goupItemDataMap = new HashMap<String, List<ItemData[]>>();
	//计算结果保存数据【删除，保存用】 
	private List<ItemData> saveItemDatas = new ArrayList<ItemData>();
	
	IParamObjectResultExecute findDao = null;
	
	public void init() throws Exception{
		super.init();
		findDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	}
	
	
	@Override
	public Object objectResultExecute() throws Exception {
		MessageResult message = new MessageResult(true);
		init();
		if (null != RequestManager.getTOject()) {
			TaskRptInst _taskRptInst = (TaskRptInst) RequestManager.getTOject();
			String taskID = _taskRptInst.getTaskFlow().getId();
			String[] instInfoIds = _taskRptInst.getInstInfoIds();
			String[] rptInfoIds = _taskRptInst.getRptInfoIds();
			if(instInfoIds.length==0||rptInfoIds.length==0){
				return new MessageResult(false, "请选择汇总机构和报表");
			}
			if(instInfoIds.length>1){
				return new MessageResult(false, "只能选择一家机构进行汇总");
			}
			for (String rptInfoId : rptInfoIds) {
				for (String instInfoId : instInfoIds) {
					DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
					detachedCriteria.add(Property.forName("taskFlow.id").eq(taskID));
					detachedCriteria.add(Property.forName("instInfo.strInstCode").eq(instInfoId));
					detachedCriteria.add(Property.forName("rptInfo.strRptCode").eq(rptInfoId));
					List<TaskRptInst> taskRptInstList = (List<TaskRptInst>) findDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
					if (taskRptInstList.size() == 1) {
						taskRptInst = taskRptInstList.get(0);
						_mergeSummary = getSummaryInfo();
						_repTopN = getTopNInstanceByRep();
						if(null == _mergeSummary){
							message.getMessageList().add("【"+taskRptInst.getRptInfo().getStrBCode()+"】无汇总实例！");
						}
						else if(null == _repTopN){
							message.getMessageList().add("【"+taskRptInst.getRptInfo().getStrBCode()+"】无Topn汇总实例！");
						}
						else {
							MessageResult mrs = cacluteTopNRptInst();
							message.getMessageList().add("【"+taskRptInst.getRptInfo().getStrBCode()+"】"+mrs.getMessage());
						}
					} 
				}
			}
		}
		message.AlertTranslate();
		return message;

	}
	
	protected MessageResult cacluteTopNRptInst() throws Exception {
		if ("2".equals(taskRptInst.getSubmitStatus()) || "2".equals(taskRptInst.getSubmitStatus())) {
			return new MessageResult(false, "已提交或审核通过的报表不能进行该操作");
		}
		try{
			String message = doTopN();
			if (message.equals("")) {
				return new MessageResult(this.getSuccessMessage());
			} else {
				return new MessageResult(false, this.getErrorMessage() + "\\r\\n" + message);
			}
		}catch(Exception e){
			return new MessageResult(false, e.toString());
		}
		
	}
	
	private String doTopN() throws Exception {
		String message = "";
		_n = _repTopN.getTotalCol()-_repTopN.getStartCol();
		Date dtDate = taskRptInst.getTaskFlow().getDtTaskDate();
		mergeRuleList = getMergeRuleList();
		OrgTreeVo root = getRoot(taskRptInst.getInstInfo().getStrInstCode());
		if (root == null) {
			message = "汇总机构不在汇总树中";
			return message;
		}
		
		Map<String,List<OrgTreeVo>> treeMap = buidTreeMap();
		Set<String> leafNodes = new HashSet<String>();
		buildOrgTree(root, treeMap, leafNodes, 0);
		validateOrgsIsCheck(new ArrayList<String>(leafNodes));
		getRepItemInfoFromRpt();
		try{
			_doTopN(root,dtDate);
		}catch(Exception e){
			ExceptionLog.CreateLog(e);
			message = "汇总失败！";
		}
		return message;
	}
	
	private void _doTopN(OrgTreeVo orgNode,Date dtDate) throws Exception {
		if(null == orgNode.getSubOrgs() || orgNode.getSubOrgs().isEmpty())
			return ;
		List<String> subOrgs = new ArrayList<String>();
		//这样汇总还是有问题的,需要先汇总最下层的
		for(OrgTreeVo subOrg : orgNode.getSubOrgs()){
			_doTopN(subOrg,dtDate);
			subOrgs.add(subOrg.getOrgId());
		}
		//加载数据
		loadItemDataByOrgs(subOrgs, dtDate);
		//分组
		group();
		//top
		top(orgNode.getOrgId());
		//插入数据
		batchInsertItemData(orgNode.getOrgId());
	}
	
	private OrgTreeVo getRoot(String orgId){
		for (MergeRule mergeRule : mergeRuleList) {
			if (mergeRule.getInstInfo().getStrInstCode().equals(orgId)) {
				return new OrgTreeVo(mergeRule.getInstInfo().getStrInstCode(),"#");
			}
		}
		return null;
	}
	
	//构建机构树，并且将叶子节点的树提取出来
	private void buildOrgTree(OrgTreeVo pOrg,Map<String,List<OrgTreeVo>> treeMap,Set<String> leafNodes,int level) {
		pOrg.setLevel(level);
		List<OrgTreeVo> subOrgs = treeMap.get(pOrg.getOrgId());
		if(null != subOrgs){
			for(OrgTreeVo org :subOrgs)
				buildOrgTree(org,treeMap,leafNodes,level+1);
			pOrg.setSubOrgs(subOrgs);
		}
		else{
			pOrg.setLeaf(true);
			leafNodes.add(pOrg.getOrgId());
		}
	}
	
	private Map<String,List<OrgTreeVo>> buidTreeMap(){
		Map<String,List<OrgTreeVo>> treeMap = new HashMap<String, List<OrgTreeVo>>();
		for (MergeRule mergeRule : mergeRuleList){
			if(null == mergeRule.getHigherInst())
				continue;
			String org = mergeRule.getInstInfo().getStrInstCode();
			String pOrg = mergeRule.getHigherInst().getStrInstCode();
			List<OrgTreeVo> list = treeMap.get(pOrg);
			if(null == list){
				list = new ArrayList<OrgTreeVo>();
				treeMap.put(pOrg, list);
			}
			list.add(new OrgTreeVo(org,pOrg));
		}
		return treeMap;
	}
	
	
	//根据报表获取TopN汇总实例
	public TopNInstance getTopNInstanceByRep() throws Exception{
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(TopNInstance.class);
		detachedCriteria.add(Restrictions.eq("rptInfo", taskRptInst.getRptInfo()));
		detachedCriteria.add(Restrictions.lt("dtStDate",taskRptInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.gt("dtEdDate",taskRptInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.eq("state", 1));
		List<TopNInstance> topNInstanceList = (List<TopNInstance>) findDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		if (topNInstanceList.size() >= 1) {
			return topNInstanceList.get(0);
		} 
		return null;
	}
	
	private List<MergeRule> getMergeRuleList() throws Exception {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(MergeRule.class);
		detachedCriteria.add(Restrictions.eq("mergeInst", _mergeSummary.getMergeInstance()));
		detachedCriteria.add(Restrictions.le("startdate", taskRptInst.getTaskFlow().getDtTaskDate()));
		detachedCriteria.add(Restrictions.ge("enddate", taskRptInst.getTaskFlow().getDtTaskDate()));
		List<MergeRule> mergeRuleList = (List<MergeRule>) findDao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		return mergeRuleList;
	}
	
	private MergeSummary getSummaryInfo() throws Exception {
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		return (MergeSummary) singleObjectFindByIdDao.paramObjectResultExecute(new Object[] { MergeSummary.class.getName(), taskRptInst.getRptInfo().getStrMergeInst(), null });
	}
	
	//获取报表指标信息 将报表指标信息转换 为对应的行列
	public void getRepItemInfoFromRpt() throws Exception {
		_repItemCodes.clear();
		String strTmpRootPath = ShowContext.getInstance().getShowEntityMap().get("sysParam").get("reportTmpPath");
		String strRootPath = ServletActionContext.getServletContext().getRealPath("/");
		String strTempPath =strRootPath + strTmpRootPath + "/" +_repTopN.getRptInfo().getStrRptPath()+XLS;
		Workbook template=getWorkbook(strTempPath);
		if(template != null){
			Sheet sheet = template.getSheetAt(0);
			int _r = _repTopN.getStartRow()-1;
			int _c = _repTopN.getStartCol()-1;
			for (int r = _r; r < _repTopN.getEndRow(); r++) {
				Row row = sheet.getRow(r);
				for (int c = _c; c < _repTopN.getEndCol(); c++) {
					Cell cell = row.getCell(c);
					String comment = getCellComment(cell);
					int nPos = comment.indexOf(ItemTemplate.Start);
					if (nPos > -1) {
						ItemBindInfo itemB = ItemTemplate.commentToItemInfo(comment);
						//省去偏移量
						itemB.row = r-_r;
						itemB.col = c-_c;
						_itemBindMap.put(itemB.itemCode, itemB);
						_repItemCodes.add(itemB.itemCode);
						_rowColItemMap.put(itemB.row+"_"+itemB.col, itemB.itemCode);
						
					}
				}
			}
		}
	}
	
	//获取模板工作薄
	private Workbook getWorkbook(String strRptFile) throws Exception
	{
		Workbook wbook=null;
		try
		{
			File file = new File(strRptFile);
			if(!file.exists()){
				throw new Exception("模板【"+strRptFile+"】打开失败！");
			}
			if( strRptFile.endsWith(XLS))
			{
				POIFSFileSystem	fs = new POIFSFileSystem(new FileInputStream(strRptFile));
				wbook= new HSSFWorkbook(fs);
			}
			else
			{
				FileInputStream ipt= new FileInputStream(strRptFile);
				wbook = new XSSFWorkbook(ipt);
			}
		}
		catch(Exception ex)
		{
			ExceptionLog.CreateLog(ex);
		}
		return wbook;
	}
	
	//无异常获取cell信息
	private String getCellComment(Cell cell)
	{
		String cellValue="";
		if(cell != null && cell.getCellComment() != null) {
			try {
			cellValue = cell.getCellComment().getString().toString();
			
			} catch(Exception ex) {
				ExceptionLog.CreateLog(ex);
				cellValue = "";
			}
		}
		return cellValue;
	}
	
	//检查最下级的上报机构数据是否被审核通过、如果有下级机构未审核通过，抛出异常
	public void validateOrgsIsCheck(List<String> orgs) throws Exception{
		DetachedCriteria detachedCriteria;
		if(taskRptInst!=null)
		{
			detachedCriteria = DetachedCriteria.forClass(TaskRptInst.class);
			detachedCriteria.add(Property.forName("instInfo.strInstCode").in(orgs));
			detachedCriteria.add(Restrictions.in("strAllowState",new String[]{"1","3"}));
			detachedCriteria.add(Restrictions.eq("taskFlow", taskRptInst.getTaskFlow()));
			detachedCriteria.add(Restrictions.eq("rptInfo", taskRptInst.getRptInfo()));
			List<TaskRptInst> lstTaskRptInst = (List<TaskRptInst>)findDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			if(lstTaskRptInst.size()>0)
			{
				String ex="上报机构【";
				for(TaskRptInst task:lstTaskRptInst)
				{
					ex+=task.getInstInfo().getStrInstCode() +"|" + task.getInstInfo().getStrInstName()+"、";
				}
				throw new Exception(ex+"】未审核通过，请确保所有机构都已审核完成，再进行汇总！");
			}
		}
	}
	
	//根据汇总关系获取下级机构上报数据
	public void loadItemDataByOrgs(List<String> subOrgs,Date dtDate) throws Exception{
		_itemDatas.clear();
		_goupItemDataMap.clear();
		for(String org:subOrgs){
			loadItemDataByOrg(org, dtDate);
		}
	}
	
	//获取机构报表上报数据
	public void loadItemDataByOrg(String org,Date dtDate) throws Exception{
		if(null == _repItemCodes|| _repItemCodes.isEmpty())
			return;
		ItemBindInfo bind = _itemBindMap.get(_repItemCodes.get(0));
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		detachedCriteria.add(Property.forName("itemInfo.strItemCode").in(_repItemCodes));
		detachedCriteria.add(Property.forName("dtDate").eq(dtDate));
		detachedCriteria.add(Property.forName("instInfo.strInstCode").eq(org));
		detachedCriteria.add(Property.forName("currencyType.strCurrencyCode").eq(bind.currency));
		detachedCriteria.add(Property.forName("itemProperty.strPropertyCode").eq(bind.property));
		if(StringUtils.isNotBlank(bind.freq))
			detachedCriteria.add(Property.forName("strFreq").eq(bind.freq));
		if(StringUtils.isNotBlank(bind.ext1))
			detachedCriteria.add(Property.forName("strExtendDimension1").eq(bind.ext1));
		if(StringUtils.isNotBlank(bind.ext2))
			detachedCriteria.add(Property.forName("strExtendDimension2").eq(bind.ext2));
		List<ItemData> dataList = (List<ItemData>) findDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
		ItemData[][] datas = new ItemData[_repTopN.getEndRow()-_repTopN.getStartRow()+1][_repTopN.getEndCol()-_repTopN.getStartCol()+1];
		for(ItemData data : dataList){
			bind = _itemBindMap.get(data.getItemInfo().getStrItemCode());
			if(null != bind){
				datas[bind.row][bind.col] = data;
			}
		}
		//分组列的数据不为空，将纳入求和排序等运算
		for(int i = 0;i<datas.length;i++){
			if(null != datas[i][_n])
				_itemDatas.add(datas[i]);
		}
	}
	
	//将数据转换为组+列模式(根据列分组)
	void group() throws Exception {
		int _g = _repTopN.getGroupCol()-_repTopN.getStartCol();
		for(ItemData[] datas : _itemDatas){
			if(null != datas[_g] && StringUtils.isNotBlank(datas[_g].getStrValue())){
				List<ItemData[]> list = _goupItemDataMap.get(datas[_g].getItemInfo().getStrItemCode());
				if(null == list){
					list = new ArrayList<ItemData[]>();
					_goupItemDataMap.put(datas[_g].getItemInfo().getStrItemCode(), list);
				}
				list.add(datas);
			}
		}
	}
	
	
	//数据求和排序运算(该功能只能对数字类型的列进行求和运算)
	void top(String org) throws Exception {
		saveItemDatas.clear();
		InstInfo _instInfo = new InstInfo();
		_instInfo.setStrInstCode(org);
		List<SumData> sumDataList = new ArrayList<SumData>();
		for(String group : _goupItemDataMap.keySet()){
			List<ItemData[]> datas = _goupItemDataMap.get(group);
			Double value = sum(datas, _n);
			sumDataList.add(new SumData(group,value));
		}
		Collections.sort(sumDataList);
		//取前n条
		int n = sumDataList.size()>_repTopN.getTopNCount()?_repTopN.getTopNCount():sumDataList.size();
		sumDataList = sumDataList.subList(0, n);
		//求和 求max
		int row = 0;
		for (SumData sumData : sumDataList) {
			List<ItemData[]> datas = _goupItemDataMap.get(sumData.group);
			ItemData[] _data= copyDefaultData(datas.get(0), _instInfo,row);
			if(null != _data[_n])
				_data[_n].setStrValue(String.valueOf(sumData.value));
			//求和
			for(int c : _repTopN.getSumCols()){
				if(null != _data[c])
					_data[c].setStrValue(String.valueOf(sum(datas, c)));
			}
			//求max
			for(int c : _repTopN.getMaxCols()){
				if(null != _data[c])
					_data[c].setStrValue(max(datas, c));
			}
			for(ItemData d : _data){
				if(null != d){
					saveItemDatas.add(d);
				}
			}
			row++;
			/*//如果节点==汇总机构，将数据保存
			if(taskRptInst.getInstInfo().getStrInstCode().equals(_mRule.getInstInfo().getStrInstCode())){
				for(ItemData d : data){
					saveItemDatas.add(newItemData(d));
				}
			}*/
		}
		
	}
	
	ItemData[] copyDefaultData(ItemData[] data,InstInfo _instInfo, int row){
		List<ItemData> _data = new ArrayList<ItemData>();
		if(data == null)
			return null;
		int col = 0;
		for(ItemData d : data){
			String itemCode = _rowColItemMap.get(row+"_"+col);
			ItemData _d = newItemData(d,itemCode,_instInfo);
			_data.add(_d);
			col++;
		}
		return _data.toArray(data);
	}
	
	//数据求和运算
	Double sum(List<ItemData[]> datas,int c) throws Exception {
		Double value = 0.0;
		for(ItemData[] data : datas){
			if(c>=data.length)
				return 0.0;
			if(null != data[c]){
				Double _d = 0.0;
				try{
					_d = Double.valueOf(data[c].getStrValue());
				}catch(Exception e){}
				value += _d;
			}
		}
		return value;
	}
	
	//new 不带id的itemdata
	ItemData newItemData(ItemData itemData, String itemCode, InstInfo _instInfo){
		if(null == itemData || itemCode == null)
			return null;
		ItemInfo item = new ItemInfo();
		item.setStrItemCode(itemCode);
		ItemData newData = new ItemData();
		newData.setDtDate(itemData.getDtDate());
		newData.setInstInfo(_instInfo);
		newData.setItemInfo(item);
		newData.setItemProperty(itemData.getItemProperty());
		newData.setCurrencyType(itemData.getCurrencyType());
		newData.setStrFreq(itemData.getStrFreq());
		newData.setStrExtendDimension1(itemData.getStrExtendDimension1());
		newData.setStrExtendDimension2(itemData.getStrExtendDimension2());
		newData.setStrValue(itemData.getStrValue());
		newData.setValue1(itemData.getStrValue());
		newData.setStrCheckState("1");
		return newData;
	}
	
	//数据max运算
	String max(List<ItemData[]> datas,int c) throws Exception {
		List<String> list = new ArrayList<String>();
		for(ItemData[] data : datas){
			if(c>=data.length)
				return "";
			if(null != data[c]){
				list.add(data[c].getStrValue());
			}
		}
		return Collections.max(list);
	}
	
	//将数据写入数据库(先删后插)
	public void batchInsertItemData(String org) throws Exception{
		if(!saveItemDatas.isEmpty()){
			IParamVoidResultExecute delDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectDeleteByCriteriaDao");  
			ItemBindInfo bind = _itemBindMap.get(_repItemCodes.get(0));
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemData.class);
			detachedCriteria.add(Property.forName("itemInfo.strItemCode").in(_repItemCodes));
			detachedCriteria.add(Property.forName("dtDate").eq(taskRptInst.getTaskFlow().getDtTaskDate()));
			detachedCriteria.add(Property.forName("instInfo.strInstCode").eq(org));
			detachedCriteria.add(Property.forName("currencyType.strCurrencyCode").eq(bind.currency));
			detachedCriteria.add(Property.forName("itemProperty.strPropertyCode").eq(bind.property));
			if(StringUtils.isNotBlank(bind.freq))
				detachedCriteria.add(Property.forName("strFreq").eq(bind.freq));
			if(StringUtils.isNotBlank(bind.ext1))
				detachedCriteria.add(Property.forName("strExtendDimension1").eq(bind.ext1));
			if(StringUtils.isNotBlank(bind.ext2))
				detachedCriteria.add(Property.forName("strExtendDimension2").eq(bind.ext2));
			delDao.paramVoidResultExecute(new Object[]{detachedCriteria,null});
			IParamVoidResultExecute saveDao = (IParamVoidResultExecute) FrameworkFactory.CreateBean("singleObjectSaveAllDao");
			saveDao.paramVoidResultExecute(new Object[]{saveItemDatas,null});
			ItemDataCacheManger.getInsance().AsynAddAll(saveItemDatas);
		}
	}

	
	//sum结果集类
	//值从大到小排序（值相同，按名称从小到大）
	class SumData implements Comparable<SumData>{
		String group;
		Double value;
		SumData(){
		}
		SumData(String group,Double value){
			this.group = group;
			this.value = value;
		}
		@Override
		public int compareTo(SumData o) {
			if(value < o.value)
				return 1;
			if(value == o.value){
				return group.compareTo(o.group);
			}
			return 0;
		}
	}
	
}
