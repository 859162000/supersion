package report.service.imps;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import report.dao.imps.ConstCacheTypeKey;
import report.dao.imps.ItemDataCacheManger;
import report.dto.ItemDataHistory;
import report.dto.ItemDataType;
import report.dto.ItemInfo;
import report.dto.RptInfo;
import report.helper.DoubleUtil;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseObjectDaoResultService;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ShowItemDataHistoryService</P>
 * *********************************<br>
 * <P>类描述：单个指标历史变更记录</P>
 * 创建人：王川<br>
 * 创建时间：2016-7-19 下午5:12:27<br>
 * 修改人：王川<br>
 * 修改时间：2016-7-19 下午5:12:27<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings("unchecked")
public class ShowItemDataHistoryService extends BaseObjectDaoResultService{
	

	private boolean isShowThousand;
	private Integer nRptPrecise = 2; // 精度
	private Integer nRptUnit = 1; // 单位
	private String dataType;
	
	public void initSuccessResult() throws Exception {
		super.setServiceResult(null); 
		String strRptCode = ServletActionContext.getRequest().getParameter("strRptCode");
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		String strName = (String) RequestManager.getId();
		ItemDataCacheManger cacheManager = ItemDataCacheManger.getInsance();
		try{
			if (strName.startsWith("ItemCode_")) { // 指标前缀
				// 解析出指标代码，属性编码
				String strItemCode = strName.substring(9, strName.indexOf("Property_"));
				String strPropertyCode = strName.substring(strName.indexOf("Property_") + "Property_".length(), strName.indexOf("Currency_"));
				String currencyType = strName.substring(strName.indexOf("Currency_") + "Currency_".length(), strName.indexOf("Date_"));
				String dtDate = strName.substring(strName.indexOf("Date_") + "Date_".length(), strName.indexOf("InstCode_"));
				String strInstCode = strName.substring(strName.indexOf("InstCode_") + "InstCode_".length(), strName.indexOf("ext1_"));
				String strExt1 = strName.substring(strName.indexOf("ext1_") + "ext1_".length(), strName.indexOf("ext2_"));
				String strExt2 = strName.substring(strName.indexOf("ext2_") + "ext2_".length(), strName.indexOf("freq_"));
				String strFreq = strName.substring(strName.indexOf("freq_") + "freq_".length());
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("report.dto.ItemDataHistory"));
				detachedCriteria.add(Restrictions.eq("strItemCode", strItemCode));
				detachedCriteria.add(Restrictions.eq("dtDate", TypeParse.parseDate(dtDate)));
				detachedCriteria.add(Restrictions.eq("strInstCode", strInstCode));
				detachedCriteria.add(Restrictions.eq("currencyType", currencyType));
				detachedCriteria.add(Restrictions.eq("strPropertyCode", strPropertyCode));
				if(StringUtils.isNotBlank(strFreq))
					detachedCriteria.add(Restrictions.eq("strFreq", strFreq));
				if(StringUtils.isNotBlank(strExt1))
					detachedCriteria.add(Restrictions.eq("strExtendDimension1", strExt1));
				if(StringUtils.isNotBlank(strExt2))
					detachedCriteria.add(Restrictions.eq("strExtendDimension2", strExt2));
				detachedCriteria.addOrder(Order.desc("updateTime"));
				IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				List<ItemDataHistory> itemDataHisSets = (List<ItemDataHistory>) singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[] { detachedCriteria,null });
				
				ItemInfo itemInfo = cacheManager.getItem(strItemCode);
				
				out.write("指标代码["+strItemCode+"]");
				out.write(" 币种["+cacheManager.getConstData("currencyType",currencyType)+"]");
				out.write(" 属性["+cacheManager.getConstData(ConstCacheTypeKey.ItemPropertyConstType, strPropertyCode)+"]");
				out.write(" 期数[本期]");
				out.write(" 频度["+cacheManager.getConstData(ConstCacheTypeKey.FreqConstType,strFreq)+"]");
				out.write("<br>");
				out.write("指标名称："+itemInfo.getStrItemName().trim());
				out.write("<br>");
				out.write("===============================================");
				out.write("<br>");
				out.write("<br>");
				
				if(itemDataHisSets.size()==0){
					out.write("该指标无修改历史记录!");
				}
				else{
					
					IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
					RptInfo rptInfo = (RptInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.RptInfo",strRptCode,null});
//					ItemInfo itemInfo = (ItemInfo)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"report.dto.ItemInfo",strItemCode,null});
					if(null != rptInfo){
						if("1".equals(rptInfo.getStrIsThousands()))
							isShowThousand=true;
						if(rptInfo.getIntPrecise() != null)
							nRptPrecise = rptInfo.getIntPrecise();
						if(rptInfo.getIntRptUnit() != null)
							nRptUnit = rptInfo.getIntRptUnit();
					}
					if(null != itemInfo){
						dataType = itemInfo.getStrDataType();
					}

					for(ItemDataHistory his:itemDataHisSets){
						out.write("数据调整的新值:"+handleAmoutValue(his.getStrValue()));
						out.write("<br>");
						out.write("数据调整的旧值:"+handleAmoutValue(his.getStrPreValue()));
						out.write("<br>");
						out.write("调整人:"+his.getAuthor());
						out.write("<br>");
						out.write("调整日期:"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(his.getUpdateTime()));
						out.write("<br>");
						out.write("<br>");
						out.write("<br>");
					}
				}
			}else{
				out.write("指标格式错误!["+strName+"]");
			}
		}catch(Exception e){
			out.write("系统异常![" + e + "]");
		}finally{
			out.flush();
			out.close();
		}
		
	}
	
	
	public String handleAmoutValue(String val){
		if (ItemDataType.Amount.toString().equals(dataType) && !StringUtils.isBlank(val)) {
			Double dVal = TypeParse.parseDouble(val);
			if (Double.compare(dVal, Double.MAX_VALUE) < 0) {
				if (nRptUnit != 0)
					dVal /= nRptUnit;

				if (nRptPrecise < 0) {
					nRptPrecise = 2;
				}
				val = DoubleUtil.format(dVal, nRptPrecise, isShowThousand);
			} else
				val = "0";
		}
		return val;
	}
}
