package report.service.imps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import report.dto.ItemData;
import report.dto.analyse.Rep_AnalyseModel;
import report.dto.analyse.Rep_ItemWarning;
import report.helper.DateUtil;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.imps.BaseService;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：RepModelAnalyseService</P>
 * *********************************<br>
 * <P>类描述：模型图形分析</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-12 下午1:27:55<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-12 下午1:27:55<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class RepModelAnalyseService extends BaseService {

	private static final String date_ymd = "^(\\d{4})-(0?\\d{1}|1[0-2])-(0?\\d{1}|[12]\\d{1}|3[01])$";

	
	/**
	 * <p>方法描述:获取预警指标 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param model
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-2 上午11:31:42</p>
	 */
	public List<Rep_ItemWarning> getItemWarning(Rep_AnalyseModel model) throws Exception{
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Rep_ItemWarning.class);
		if (model.getItemIds() != null && model.getItemIds().size() > 0) {
			detachedCriteria.add(Property.forName("itemInfo.strItemCode").in(model.getItemIds()));
		}else{
			return new ArrayList<Rep_ItemWarning>();
		}
		return (List<Rep_ItemWarning>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
	}
	
	public  Map<String,Rep_ItemWarning> warningListToMap(List<Rep_ItemWarning> list){
		Map<String, Rep_ItemWarning> map = new HashMap<String, Rep_ItemWarning>();
		for(Rep_ItemWarning l:list){
			map.put(l.getItemInfo().getStrItemCode(), l);
		}
		return map;
	}

	//01时间
	//02指标
	//03机构
	//04币种
	//05 指标属性
	//06 扩展维度1
	//07 扩展维度2
	public List<ItemData> getData(Rep_AnalyseModel model) throws Exception {
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		IParamObjectResultExecute dao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(ItemData.class);
		Date nowDate = new Date();
		//校验日期格式
		if (StringUtils.isNotBlank(model.getStartDate()) && !isYmd(model.getStartDate())) {
			//年初
			if ("FC_STAYEAR".equals(model.getStartDate().toUpperCase())) {
				model.setStartDate(DateUtil.startYear(nowDate));
			}
			//至今
			if ("FC_NOWDATE".equals(model.getStartDate().toUpperCase())) {
				model.setStartDate(DateUtil.reviseDate(nowDate, model.getFreq()));
			}
		}
		if (StringUtils.isNotBlank(model.getEndDate()) && !isYmd(model.getEndDate())) {
			//年初
			if ("FC_STAYEAR".equals(model.getEndDate().toUpperCase())) {
				model.setEndDate(DateUtil.startYear(nowDate));
			}
			//至今
			if ("FC_NOWDATE".equals(model.getEndDate().toUpperCase())) {
				model.setEndDate(DateUtil.reviseDate(nowDate, model.getFreq()));
			}
		}
		//当结束时间为空时赋值为当前时间
		if(StringUtils.isBlank(model.getEndDate())){
			model.setEndDate(DateUtil.reviseDate(nowDate, model.getFreq()));
		}
		//01时间   
		//时间函数处理【时间函数只能取两个时间点】
		if (StringUtils.isNotBlank(model.getTimeFunc()) && !"yearToNow".equals(model.getTimeFunc()) ) {
			//根据 结束时间 确定另一个时间点
			String endDate = DateUtil.reviseDate(model.getEndDate(), model.getFreq());
			String startDate = model.getStartDate();
			//环比
			if("preDate".equals(model.getTimeFunc())){
				startDate = DateUtil.preDate(endDate, model.getFreq());
			}
			//同比
			if("preYear".equals(model.getTimeFunc())){
				startDate = DateUtil.preYear(endDate, -1);
			}
			detachedCriteria.add(Restrictions.in("dtDate", new Object[]{sd.parse(startDate),sd.parse(endDate)}));
		} else {
			if (model.isMuiltSelect("01"))
				detachedCriteria.add(Restrictions.between("dtDate", sd.parse(model.getStartDate()), sd.parse(model.getEndDate())));
			else {
				//参考endDate作为时间点
				detachedCriteria.add(Restrictions.eq("dtDate", sd.parse(model.getEndDate())));
			}
		}

		//02指标
		removeBlankStrFromColl(model.getItemIds());
		if (model.getItemIds() != null && model.getItemIds().size() > 0) {
			detachedCriteria.add(Restrictions.in("itemInfo.strItemCode", model.getItemIds()));
		}
		else{
			throw new Exception("模型分析：分析指标不能为空！");
		}
		//03机构
		removeBlankStrFromColl(model.getOrgIds());
		if (model.getOrgIds() != null && model.getOrgIds().size() > 0) {
			detachedCriteria.add(Restrictions.in("instInfo.strInstCode", model.getOrgIds()));
		}
		else{
			throw new Exception("模型分析：分析机构不能为空！");
		}
		//04币种
		removeBlankStrFromColl(model.getCurrIds());
		if (model.getCurrIds() != null && model.getCurrIds().size() > 0) {
			detachedCriteria.add(Restrictions.in("currencyType.strCurrencyCode", model.getCurrIds()));
		}
		//05 指标属性
		removeBlankStrFromColl(model.getItemProIds());
		if (model.getItemProIds() != null && model.getItemProIds().size() > 0) {
			detachedCriteria.add(Restrictions.in("itemProperty.strPropertyCode", model.getItemProIds()));
		}
		//06 扩展维度1
		removeBlankStrFromColl(model.getDimension1Types());
		if (model.getDimension1Types() != null && model.getDimension1Types().size() > 0) {
			detachedCriteria.add(Restrictions.in("strExtendDimension1", model.getDimension1Types()));
		}
		//07 扩展维度2
		removeBlankStrFromColl(model.getDimension2Types());
		if (model.getDimension2Types() != null && model.getDimension2Types().size() > 0) {
			detachedCriteria.add(Restrictions.in("strExtendDimension2", model.getDimension2Types()));
		}
		//频度附加条件
		/*if (StringUtils.isNotBlank(model.getFreq()))
			detachedCriteria.add(Restrictions.eq("strFreq", model.getFreq()));*/
		String orderStr;
		if (null != (orderStr = getModelOrder(model.getDimensionType1())))
			detachedCriteria.addOrder(Order.asc(orderStr));
		if (null != (orderStr = getModelOrder(model.getDimensionType2())))
			detachedCriteria.addOrder(Order.asc(orderStr));
		List<ItemData> objectList = (List<ItemData>) dao.paramObjectResultExecute(new Object[] { detachedCriteria, null });
		return objectList;
	}

	private String getModelOrder(String dimension) {
		if ("01".equals(dimension))
			return "dtDate";
		if ("02".equals(dimension))
			return "itemInfo.strItemCode";
		if ("03".equals(dimension))
			return "instInfo.strInstCode";
		if ("04".equals(dimension))
			return "currencyType.strCurrencyCode";
		if ("05".equals(dimension))
			return "itemProperty.strPropertyCode";
		if ("06".equals(dimension))
			return "strExtendDimension1";
		if ("07".equals(dimension))
			return "strExtendDimension2";
		return null;
	}

	public void removeBlankStrFromColl(Collection<String> list) {
		if (null == list)
			return;
		List<String> rm = new ArrayList<String>();
		for (String str : list) {
			if (StringUtils.isBlank(str)) {
				rm.add(str);
			}
		}
		list.removeAll(rm);
	}

	/**
	 * <p>方法描述:判断日期是否是 yyyy-MM-dd格式 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param date
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-1 上午10:56:21</p>
	 */
	public boolean isYmd(String date) {
		return Pattern.matches(date_ymd, date);
	}

}
