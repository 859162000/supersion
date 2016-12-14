package report.helper;

import java.util.List;

import report.service.imps.ItemTemplate;

public class ExcelCellInfo {

	private String strCell;
	private String strItemCode;
	private boolean isFormula;
	private String strFormula;
	private String strComment;
	private String strCurrency;
	private String strProp;
	private String strFreq;
	private String strExt1;
	private String strExt2;
	private String strPeriod;
	private String dtDate;
	private String instInfo;
	
	public String getStrCurrency() {
		return strCurrency;
	}
	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}
	public String getStrProp() {
		return strProp;
	}
	public void setStrProp(String strProp) {
		this.strProp = strProp;
	}
	public String getStrFreq() {
		return strFreq;
	}
	public void setStrFreq(String strFreq) {
		this.strFreq = strFreq;
	}
	public String getStrExt1() {
		return strExt1;
	}
	public void setStrExt1(String strExt1) {
		this.strExt1 = strExt1;
	}
	public String getStrExt2() {
		return strExt2;
	}
	public void setStrExt2(String strExt2) {
		this.strExt2 = strExt2;
	}
	public String getStrPeriod() {
		return strPeriod;
	}
	public void setStrPeriod(String strPeriod) {
		this.strPeriod = strPeriod;
	}
	private List<WordAnalyClass> wordAnalyClass;
	
	public String getStrCell() {
		return strCell;
	}
	public void setStrCell(String strCell) {
		this.strCell = strCell;
	}
	public String getStrItemCode() {
		return strItemCode;
	}
	public void setStrItemCode(String strItemCode) {
		this.strItemCode = strItemCode;
	}
	public boolean isFormula() {
		return isFormula;
	}
	public void setFormula(boolean isFormula) {
		this.isFormula = isFormula;
	}
	public String getStrFormula() {
		return strFormula;
	}
	public void setStrFormula(String strFormula) {
		this.strFormula = strFormula;
	}
	public void setWordAnalyClass(List<WordAnalyClass> wordAnalyClass) {
		this.wordAnalyClass = wordAnalyClass;
	}
	public List<WordAnalyClass> getWordAnalyClass() {
		return wordAnalyClass;
	}
	public void setStrComment(String strComment) {
		this.strComment = strComment;
		
		//20160623 Liaoxl repair 只有指标报表的指标批注需要进行以下处理，明细报表的指标批注不需要进行以下处理
		if(strComment.startsWith(ItemTemplate.Start))
		{
			strItemCode=strComment.substring(strComment.indexOf(ItemTemplate.Item)+ItemTemplate.Item.length()+1);
			int endIndex=strItemCode.indexOf(ItemTemplate.SectionSpliter);
			strItemCode=strItemCode.substring(0, endIndex);
			
			strCurrency=strComment.substring(strComment.indexOf(ItemTemplate.Currency)+ItemTemplate.Currency.length()+1);
			endIndex=strCurrency.indexOf(ItemTemplate.SectionSpliter);
			strCurrency=strCurrency.substring(0, endIndex);
					
			strProp=strComment.substring(strComment.indexOf(ItemTemplate.Prop)+ItemTemplate.Prop.length()+1);
			endIndex=strProp.indexOf(ItemTemplate.SectionSpliter);
			strProp=strProp.substring(0, endIndex);
			
			strFreq=strComment.substring(strComment.indexOf(ItemTemplate.Freq)+ItemTemplate.Freq.length()+1);
			endIndex=strFreq.indexOf(ItemTemplate.SectionSpliter);
			strFreq=strFreq.substring(0, endIndex);
			
			strExt1=strComment.substring(strComment.indexOf(ItemTemplate.Ext1)+ItemTemplate.Ext1.length()+1);
			endIndex=strExt1.indexOf(ItemTemplate.SectionSpliter);
			strExt1=strExt1.substring(0, endIndex);
			
			strExt2=strComment.substring(strComment.indexOf(ItemTemplate.Ext2)+ItemTemplate.Ext2.length()+1);
			endIndex=strExt2.indexOf(ItemTemplate.SectionSpliter);
			strExt2=strExt2.substring(0, endIndex);
			
			strPeriod=strComment.substring(strComment.indexOf(ItemTemplate.Period)+ItemTemplate.Period.length()+1);
			endIndex=strPeriod.indexOf(ItemTemplate.SectionSpliter);
			strPeriod=strPeriod.substring(0, endIndex);
			
			dtDate=strComment.substring(strComment.indexOf(ItemTemplate.DtDate)+ItemTemplate.DtDate.length()+1);
			endIndex=dtDate.indexOf(ItemTemplate.SectionSpliter);
			dtDate=dtDate.substring(0, endIndex);
			
			instInfo=strComment.substring(strComment.indexOf(ItemTemplate.Inst)+ItemTemplate.Inst.length()+1);
			endIndex=instInfo.indexOf(ItemTemplate.SectionSpliter);
			instInfo=instInfo.substring(0, endIndex);
		}
		
	}
	public String getStrComment() {
		return strComment;
	}
	public void setDtDate(String dtDate) {
		this.dtDate = dtDate;
	}
	public String getDtDate() {
		return dtDate;
	}
	public void setInstInfo(String instInfo) {
		this.instInfo = instInfo;
	}
	public String getInstInfo() {
		return instInfo;
	}	
}
