package itemreport;

import java.util.HashMap;
import java.util.Map;

import extend.dto.Suit;
import framework.interfaces.RequestManager;
import framework.test.ActionTestCase;
import framework.test.TestDataProvider;

import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.annotation.Rollback;

import com.alibaba.fastjson.support.odps.udf.CodecCheck.A;
import com.ibm.db2.jcc.a.b;

import report.dto.CurrencyType;
import report.dto.ItemInfo;
import report.dto.ItemProperty;

import coresystem.dto.InstInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ForKeyAddTest extends ActionTestCase {
	Map<String, Object> context = new HashMap<String, Object>();
	public final static Map<String, String> IdList = new HashMap<String, String>();

	@Test
	@Rollback(true)
	public void step00_forKeySuitAdd() throws Exception {
		// Suit
		context.put("windowId", "Suit");
		context.put("type", "extend.dto.Suit");
		testAction("/framework/ShowListForTree-extend.dto.Suit.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("type", context.get("type").toString());
						datas.put("id", "");
						return datas;
					}
				}, context);
		testAction("/framework/ShowTree-extend.dto.Suit.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/ShowSave-extend.dto.Suit.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);

		testAction("/framework/TreeNodeSave-extend.dto.Suit.action","forKeyAdd.xls", 1);
		Suit suit = (Suit) RequestManager.getTOject();
		String suitCode = suit.getStrSuitCode();
		IdList.put("suit", suitCode);
	}

	// InstInfo
	@Test
	@Rollback(true)
	public void step01_forKeyInstInfoAdd() throws Exception {
		context.put("windowId", "InstInfo");
		testAction("/framework/ShowTree-coresystem.dto.InstInfo.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		context.put("type", "coresystem.dto.InstInfo");
		testAction("/framework/ShowListForTree-coresystem.dto.InstInfo.action",
				new TestDataProvider() {

					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						datas.put("type", context.get("type").toString());
						datas.put("id", "");
						return datas;
					}
				}, context);
		initServletMockObjects();

		testAction("/framework/ShowSave-coresystem.dto.InstInfo.action",
				new TestDataProvider() {
					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		initServletMockObjects();

		testAction("/framework/TreeNodeSave-coresystem.dto.InstInfo.action","forKeyAdd.xls", 2);
		InstInfo instInfo = (InstInfo) RequestManager.getTOject();
		String instCode = instInfo.getStrInstCode();
		IdList.put("instInfo", instCode);

	}

	// CurrencyType
	@Test
	@Rollback(true)
	public void step02_forKeyCurrencyTypeAdd() throws Exception {
		context.put("windowId", "CurrencyType");
		testAction("/framework/ShowList-report.dto.CurrencyType.action",new TestDataProvider() {

					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/ShowSave-report.dto.CurrencyType.action",
				new TestDataProvider() {

					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/Save-report.dto.CurrencyType.action","forKeyAdd.xls", 3);
		CurrencyType currencyType = (CurrencyType) RequestManager.getTOject();
		String currencyTypeCode = currencyType.getStrCurrencyCode();
		IdList.put("currencyType", currencyTypeCode);

	}

	// ItemInfo
	@Test
	@Rollback(true)
	public void step03_forKeyItemInfoAdd() throws Exception {
		context.put("windowId", "ItemInfo");
		testAction("/framework/ShowTree-report.dto.ItemInfo.action",new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		context.put("type", "extend.dto.Suit");
		testAction("/framework/ShowListForTree-report.dto.ItemInfo.action",new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						datas.put("type", context.get("type").toString());
						datas.put("id", "");
						return datas;
					}
				}, context);
		testAction("/framework/ShowSave-report.dto.ItemInfo.action",new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId")
								.toString());
						return datas;
					}
				}, context);
		initServletMockObjects();
		testAction("/framework/TreeNodeSave-report.dto.ItemInfo.action","forKeyAdd.xls", 5);
		ItemInfo itemInfo = (ItemInfo) RequestManager.getTOject();
		String itemInfoCode = itemInfo.getStrItemCode();
		IdList.put("itemInfo", itemInfoCode);
	}

	// ItemProperty
	@Test
	@Rollback(true)
	public void step04_forKeyItemPropertyAdd() throws Exception {
		context.put("windowId", "ItemProperty");
		testAction("/framework/ShowList-report.dto.ItemProperty.action",new TestDataProvider() {
					@Override
					public Map<String, String> getData(
							Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/ShowSave-report.dto.ItemProperty.action",
				new TestDataProvider() {
					@Override
					public Map<String, String> getData(Map<String, Object> context) {
						Map<String, String> datas = new HashMap<String, String>();
						datas.put("windowId", context.get("windowId").toString());
						return datas;
					}
				}, context);
		testAction("/framework/Save-report.dto.ItemProperty.action","forKeyAdd.xls", 4);
		ItemProperty itemProperty = (ItemProperty) RequestManager.getTOject();
		String itemPropertyCode = itemProperty.getStrPropertyCode();
		IdList.put("itemProperty", itemPropertyCode);
		System.out.println(IdList);
	}

}
