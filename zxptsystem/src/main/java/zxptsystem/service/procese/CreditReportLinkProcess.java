package zxptsystem.service.procese;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.services.interfaces.IProcese;
import framework.show.ShowContext;
import framework.show.ShowHeaderName;
import framework.show.ShowList;
import framework.show.ShowOperation;
import framework.show.ShowValue;

public class CreditReportLinkProcess implements IProcese {

	@Override
	public Object Procese(Object serviceResult) throws Exception {
		if(null != serviceResult){
			HashSet<String> hs = new HashSet<String>();
			
			IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(SystemParam.class);
			detachedCriteria.add(Restrictions.eq("strEnable", "1"));
			detachedCriteria.add(Restrictions.in("strItemCode", new String[]{"Internet_QY_GeneralReport"
																			,"Intranet_QY_GeneralReport"
																			,"Intranet_QY_DetailReport"
																			,"Intranet_QY_Online"
																			,"Internet_GR_GeneralReport"
																			,"Intranet_GR_GeneralReport"}));
			List<SystemParam> SystemParamList = (List<SystemParam>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
			String qyBaseUrl="";
			String grBaseUrl="";
			if(null!=SystemParamList && SystemParamList.size()>0){
				for(SystemParam sp:SystemParamList){
					hs.add(ShowContext.getInstance().getShowEntityMap().get("configParam").get(sp.getStrItemCode()));
					if(sp.getStrItemCode().equals("Intranet_QY_DetailReport")
							||sp.getStrItemCode().equals("Intranet_QY_GeneralReport")){
						qyBaseUrl=sp.getStrParamValue();
					}
					if(sp.getStrItemCode().equals("Intranet_GR_GeneralReport")){
						grBaseUrl=sp.getStrParamValue();
					}
				}
			}
			
			if (serviceResult instanceof ShowList) {
				ShowList showList = (ShowList)serviceResult;
				List<ShowHeaderName> lists=showList.getShowLinkNameList();
				List<ShowHeaderName> removes=new ArrayList<ShowHeaderName>();
				
				List<List<ShowOperation>> llists = showList.getLinkedList();
				for(List<ShowOperation> llistss:llists){
					List<ShowOperation> rmsos =new ArrayList<ShowOperation>();
					for(ShowOperation so : llistss){
						if(!hs.contains(so.getOperationName())){
							rmsos.add(so);
						}
					}
					for(ShowOperation so : rmsos){
						llistss.remove(so);
					}
				}
				for(ShowHeaderName showHeaderName:lists){
					if(!hs.contains(showHeaderName.getShowName())){
						removes.add(showHeaderName);
					}
				}
				for(ShowHeaderName showHeaderName:removes){
					lists.remove(showHeaderName);
				}
				
				List<List<ShowOperation>> solists = showList.getLinkedList();
				int index=0;
				for(List<ShowOperation> solist:solists){
					for(ShowOperation so:solist){
						if(so.getOperationType().equals("OpenNewWindow")){
							if(so.getOperationAction().startsWith("TestQueryIntranetQYDetailReport")
									|| so.getOperationAction().startsWith("TestQueryIntranetQYGeneralReport")){
								List<List<ShowValue>> svlists = showList.getValueTable();
								List<ShowValue> rowData = svlists.get(index);
								String strCustomerCode = "";
								String timeCretaeDate="";
								for(ShowValue sv : rowData){
									if(null==sv.getPostFieldName()){
										continue;
									}
									if(sv.getPostFieldName().equals("["+index+"].strCustomerID.strCustomerID")){										
										Map<String,String> map=(Map<String,String>)sv.getTag();
										Iterator<Entry<String, String>>  iterator= map.entrySet().iterator();
										while(iterator.hasNext()){
											Entry<String, String> entry = (Entry<String, String>) iterator.next();
											if(entry.getValue().equals(sv.getValue())){
												strCustomerCode = entry.getKey();
												break;
											}
										}
									}
									if(sv.getPostFieldName().equals("["+index+"].timeCreateDate")){
										timeCretaeDate=TypeParse.parseString(TypeParse.parseDate(sv.getValue()), "yyyyMMdd");
									}
									
									if(!strCustomerCode.isEmpty() && !timeCretaeDate.isEmpty()){
										break;
									}
								}
								String detailUrl=qyBaseUrl+"/QY/"+strCustomerCode+timeCretaeDate;
								if(so.getOperationAction().startsWith("TestQueryIntranetQYDetailReport")){
									detailUrl+="/Intranet_DetailReport/Intranet_DetailReport.html";
								}else{
									detailUrl+="/Intranet_GeneralReport/Intranet_GeneralReport.html";
								}
								
								so.setImageUrl(detailUrl);
							}
							else if(so.getOperationAction().startsWith("TestQueryOnlineReport")){
								List<List<ShowValue>> svlists = showList.getValueTable();
								List<ShowValue> rowData = svlists.get(index);
								String id="";
								for(ShowValue sv : rowData){
									if(null==sv.getPostFieldName()){
										continue;
									}									
									if(sv.getPostFieldName().equals("["+index+"].id")){
										id=sv.getValue();
										break;
									}
								}
								String detailUrl="ZXPTSystem/CreditQuery/OnlineQuery.jsp?id="+id;
								so.setImageUrl(detailUrl);
							}else if(so.getOperationAction().startsWith("TestQueryIntranetGRGeneralReport")){
								List<List<ShowValue>> svlists = showList.getValueTable();
								List<ShowValue> rowData = svlists.get(index);
								String strCustomerCode = "";
								String endDate="";
								for(ShowValue sv : rowData){
									if(null==sv.getPostFieldName()){
										continue;
									}
									if(sv.getPostFieldName().equals("["+index+"].strCustomerID.strCustomerID")){										
										Map<String,String> map=(Map<String,String>)sv.getTag();
										Iterator<Entry<String, String>>  iterator= map.entrySet().iterator();
										while(iterator.hasNext()){
											Entry<String, String> entry = (Entry<String, String>) iterator.next();
											if(entry.getValue().equals(sv.getValue())){
												strCustomerCode = entry.getKey();
												break;
											}
										}
									}
									if(sv.getPostFieldName().equals("["+index+"].dtEndDate")){
										endDate=TypeParse.parseString(TypeParse.parseDate(sv.getValue()), "yyyyMMdd");
									}
									
									if(!strCustomerCode.isEmpty() && !endDate.isEmpty()){
										break;
									}
								}
								String detailUrl=grBaseUrl+"/GR/"+strCustomerCode+endDate;
								detailUrl+="/Intranet_GeneralReport/Intranet_GeneralReport.html";								
								so.setImageUrl(detailUrl);							
							}
						}
					}
					index++;
				}
			}
		}	
		return serviceResult;
	}
}
