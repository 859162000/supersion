package report.service.procese;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import report.dto.CurrencyType;
import report.dto.ItemProperty;
import report.dto.analyse.Rep_AnalyseModel;
import report.dto.analyse.Rep_AnalyseModel_Currs;
import report.dto.analyse.Rep_AnalyseModel_Dimension1;
import report.dto.analyse.Rep_AnalyseModel_Dimension2;
import report.dto.analyse.Rep_AnalyseModel_ItemPros;
import report.dto.analyse.Rep_AnalyseModel_Items;
import report.dto.analyse.Rep_AnalyseModel_Orgs;
import report.service.show.ShowAnalyseModel;
import report.service.show.ShowSaveOrUpdate_Tab;
import coresystem.dto.InstInfo;
import extend.dto.Suit;
import framework.helper.ShowInstanceHelper;
import framework.interfaces.ApplicationManager;
import framework.interfaces.RequestManager;
import framework.interfaces.TActionRule;
import framework.services.interfaces.BaseConstructor;
import framework.services.interfaces.IProcese;
import framework.services.interfaces.ShowParamManager;
import framework.show.ShowComponentSaveOrUpdate;
import framework.show.ShowField;
import framework.show.ShowFieldValue;
import framework.show.ShowSaveOrUpdate;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：ShowModelAnalyseSaveOrUpdateProcese</P>
 * *********************************<br>
 * <P>类描述：模型分析配置</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-10 下午5:13:16<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-10 下午5:13:16<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class ShowModelAnalyseSaveOrUpdateProcese extends BaseConstructor implements IProcese {

	public Object Procese(Object serviceResult) throws Exception {
		if (null == serviceResult)
			throw new Exception("该分析模型不存在!");
		Rep_AnalyseModel analyseModel = (Rep_AnalyseModel) serviceResult;
		ShowAnalyseModel showAnalyseModel = new ShowAnalyseModel();
		ShowSaveOrUpdate showSaveOrUpdate = ShowInstanceHelper.getShowClassFromInstance(RequestManager.getTName(), ShowParamManager.getShowInstanceName());
		ShowInstanceHelper.parseReulstShowData(analyseModel, showSaveOrUpdate);
		showAnalyseModel.setShowSaveOrUpdate(showSaveOrUpdate);
		List<ShowSaveOrUpdate> showCardList = new ArrayList<ShowSaveOrUpdate>();
		showAnalyseModel.setShowCardList(showCardList);

		//01 时间

		//02  指标
		Set<Rep_AnalyseModel_Items> items = analyseModel.getItems();
		ShowFieldValue fild_suit_v = new ShowFieldValue(new ShowField("suit", "suitCode", "主题", ApplicationManager.getSingleTagSelect()), null, ShowInstanceHelper.getSimpleMap("suit", Suit.class));
		ShowFieldValue fild_itemCode_v = new ShowFieldValue(new ShowField("itemInfo", "strItemCode", "指标", ApplicationManager.getSingleTagTextfield()), null, null);
		Map<String, String> itemMap = new HashMap<String, String>();
		for (Rep_AnalyseModel_Items item : items) {
			itemMap.put(item.getItemInfo().getStrItemCode(), item.getItemInfo().getStrItemCode() + "|" + item.getItemInfo().getStrItemName());
		}
		ShowFieldValue fild_item_v = new ShowFieldValue(new ShowField("items", "itemIds", "", analyseModel.isMuiltSelect("02") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), itemMap, itemMap);
		ShowSaveOrUpdate_Tab itemSave = new ShowSaveOrUpdate_Tab("分析-指标");
		itemSave.getShowFieldValueList().add(fild_suit_v);
		itemSave.getShowFieldValueList().add(fild_itemCode_v);
		itemSave.getShowFieldValueList().add(fild_item_v);
		itemSave.setTabUrl("ShowTabForAMItems.jsp");
		showCardList.add(itemSave);

		//03  机构
		Set<Rep_AnalyseModel_Orgs> orgs = analyseModel.getOrgs();
		Map<String, String> orgMap = new HashMap<String, String>();
		for (Rep_AnalyseModel_Orgs org : orgs) {
			orgMap.put(org.getInstInfo().getStrInstCode(), org.getInstInfo().getStrInstName());
		}
		ShowFieldValue fild_org_v = new ShowFieldValue(new ShowField("orgs", "orgIds", "机构", analyseModel.isMuiltSelect("03") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), orgMap, ShowInstanceHelper.getSimpleMap("instInfo", InstInfo.class));
		ShowSaveOrUpdate_Tab orgSave = new ShowSaveOrUpdate_Tab("分析-机构");
		orgSave.getShowFieldValueList().add(fild_org_v);
		showCardList.add(orgSave);

		//04  币种
		Set<Rep_AnalyseModel_Currs> currs = analyseModel.getCurrs();
		Map<String, String> currMap = new HashMap<String, String>();
		for (Rep_AnalyseModel_Currs curr : currs) {
			currMap.put(curr.getCurrencyType().getStrCurrencyCode(), curr.getCurrencyType().getStrCurrencyName());
		}
		ShowFieldValue fild_curr_v = new ShowFieldValue(new ShowField("currs", "currIds", "币种", analyseModel.isMuiltSelect("04") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), currMap, ShowInstanceHelper.getSimpleMap("currencyType", CurrencyType.class));
		ShowSaveOrUpdate_Tab currSave = new ShowSaveOrUpdate_Tab("分析-币种");
		currSave.getShowFieldValueList().add(fild_curr_v);
		showCardList.add(currSave);

		//05 指标属性
		Set<Rep_AnalyseModel_ItemPros> itemPros = analyseModel.getItemPros();
		Map<String, String> itemProMap = new HashMap<String, String>();
		for (Rep_AnalyseModel_ItemPros itemPro : itemPros) {
			itemProMap.put(itemPro.getItemProperty().getStrPropertyCode(), itemPro.getItemProperty().getStrPropertyName());
		}
		ShowFieldValue fild_itemPro_v = new ShowFieldValue(new ShowField("itemPros", "itemProIds", "指标属性", analyseModel.isMuiltSelect("05") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), itemProMap, ShowInstanceHelper.getSimpleMap("itemProperty", ItemProperty.class));
		ShowSaveOrUpdate_Tab itemProSave = new ShowSaveOrUpdate_Tab("分析-指标属性");
		itemProSave.getShowFieldValueList().add(fild_itemPro_v);
		showCardList.add(itemProSave);

		//06 扩展维度1
		Set<Rep_AnalyseModel_Dimension1> dimension1s = analyseModel.getDimension1s();
		Map<String, String> dimension1Map = new HashMap<String, String>();
		for (Rep_AnalyseModel_Dimension1 dimension1 : dimension1s) {
			dimension1Map.put(dimension1.getStrExtendDimension1(), Rep_AnalyseModel_Dimension1.getExtendPropertyTag1().get(dimension1.getStrExtendDimension1()));
		}
		ShowFieldValue fild_dimension1_v = new ShowFieldValue(new ShowField("dimension1s", "dimension1Types", "扩展维度1", analyseModel.isMuiltSelect("06") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), dimension1Map, Rep_AnalyseModel_Dimension1.getExtendPropertyTag1());
		ShowSaveOrUpdate_Tab dimension1Save = new ShowSaveOrUpdate_Tab("分析-扩展维度1");
		dimension1Save.getShowFieldValueList().add(fild_dimension1_v);
		showCardList.add(dimension1Save);

		//07 扩展维度2
		Set<Rep_AnalyseModel_Dimension2> dimension2s = analyseModel.getDimension2s();
		Map<String, String> dimension2Map = new HashMap<String, String>();
		for (Rep_AnalyseModel_Dimension2 dimension2 : dimension2s) {
			dimension2Map.put(dimension2.getStrExtendDimension2(), Rep_AnalyseModel_Dimension2.getExtendPropertyTag2().get(dimension2.getStrExtendDimension2()));
		}
		ShowFieldValue fild_dimension2_v = new ShowFieldValue(new ShowField("dimension2s", "dimension2Types", "扩展维度2", analyseModel.isMuiltSelect("07") ? ApplicationManager.getSingleTagMultipleSelect() : ApplicationManager.getSingleTagSelect()), dimension2Map, Rep_AnalyseModel_Dimension2.getExtendPropertyTag2());
		ShowSaveOrUpdate_Tab dimension2Save = new ShowSaveOrUpdate_Tab("分析-扩展维度2");
		dimension2Save.getShowFieldValueList().add(fild_dimension2_v);
		showCardList.add(dimension2Save);

		doFinally(showAnalyseModel);
		return showAnalyseModel;
	}

	private void doFinally(ShowComponentSaveOrUpdate serviceResult) throws Exception {
		String currentTName = RequestManager.getTName();
		String currentAction = RequestManager.getActionName();
		String typeName = RequestManager.getTypeName();
		String levelId = RequestManager.getLevelIdValue();
		Object id = RequestManager.getId();
		String showInstanceName = ShowParamManager.getShowInstanceName();
		RequestManager.setActionName(currentAction);
		RequestManager.setTName(currentTName);
		RequestManager.setTypeName(typeName);
		RequestManager.setLevelIdValue(levelId);
		RequestManager.setId(id);
		ShowParamManager.setShowInstanceName(showInstanceName);
		TActionRule.initActionName();
	}

}
