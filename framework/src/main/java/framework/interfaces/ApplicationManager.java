package framework.interfaces;

import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

public class ApplicationManager {
	
	private static Map<String,Object> getApplication(){
		return ServletActionContext.getContext().getApplication();
	}

	private static final String SEG = "_";
	
	private static final String SHOWNAVIGATION = "showNavigation";
	
	private static final String DEFAULTSHOWINSTANCE = "defaultShowInstance";
	
	private static final String SHOWINSTANCELIST = "showInstanceList";
	
	private static final String REALPATH = "realPath";
	
	private static final String ACTIONEXCUTELOG = "actionExcuteLog";
	
	private static final String ACTIONEXCEPTIONLOG = "actionExceptionLog";
	
	private static final String EMPTYSELECTVALUENAME = "emptySelectValueName";
	
	private static final String EMPTYSELECTVALUE = "";
	
	private static final String SINGLETAGHIDDENFIELD = "hiddenField";
	
	private static final String SINGLETAGTEXTFIELD = "textField";
	
	private static final String SINGLETAGMULTIPLETEXTFIELD = "multipleTextField";
	
	private static final String SINGLETAGTREEFIELD = "treeField";
	
	private static final String SINGLETAGSELECTFIELD = "selectField";
	
	private static final String SINGLETAGMULTIPLESELECTFIELD = "multipleSelectField";
	
	private static final String SINGLETAGFILEFIELD = "fileField";
	
	private static final String SINGLETAGDATEFIELD = "dateField";
	
	private static final String MODELFIELD = "modelField";
	
	private static final String SINGLETAGDATEFIELDNOSLASH = "dateFieldNoSlash";
	
	private static final String SINGLETAGDATEFIELDMONTH = "dateFieldMonth";
	
	private static final String SINGLETAGDATEFIELDHMS = "dateFieldHMS";
	
	private static final String SINGLETAGDATESLASHHMS = "dateFieldSlashHMS";
	
	private static final String SINGLETAGCARD="card";
	private static final String SINGLETAGTAB="tab";
	private static final String SINGLETAGLIST="list";
	
	private static final String SHOWLISTLEVELAUTODTOPREFIX = "ShowListLevelAUTODTO-";
	
	private static final String SHSHOWLISTLEVELAUTODTOSHPREFIX = "SHShowListLevelAUTODTOSH-";  
	
	private static final String CKSHOWLISTLEVELAUTODTOPREFIX = "CKShowListLevelAUTODTO-";                                                   
	
	private static final String CHARSETTYPE = "charsetType";
	
	private static final String LINKEDURL = "linkedUrl";
	
	private static final String OPENLINKEDURL = "openLinkedUrl";
	private static final String ZXPTSHOWLISTLEVELAUTODTOPREFIX = "ZXPTShowListLevelAUTODTO-";
	public static String getZxptshowlistlevelautodtoprefix() {
		return ZXPTSHOWLISTLEVELAUTODTOPREFIX;
	}

	public static String getShowNavigation(String category){
		return SHOWNAVIGATION + SEG + category;
	}

	public static String getDefaultInstanceName(String tName){
		return DEFAULTSHOWINSTANCE + SEG + tName;
	}
	
	public static String getShowInstanceListName(String tName){
		return SHOWINSTANCELIST + SEG + tName;
	}
	
	public static String getRealPath(){
		return REALPATH;
	}
	
	public static Logger getActionExcuteLog(){
		return Logger.getLogger(ACTIONEXCUTELOG);
	}
	
	public static Logger getActionExceptionLog(){
		return Logger.getLogger(ACTIONEXCEPTIONLOG);
	}
	
	public static String getEmptySelectValueName(){
		return EMPTYSELECTVALUENAME;
	}

	public static String getModelField(){
		return MODELFIELD;
	}
	
	public static String getEmptySelectValue(){
		return EMPTYSELECTVALUE;
	}
	
	public static String getSingleTagHidden(){
		return SINGLETAGHIDDENFIELD;
	}
	
	public static String getSingleTagTextfield(){
		return SINGLETAGTEXTFIELD;
	}
	
	public static String getSingleTagMultipleTextField(){
		return SINGLETAGMULTIPLETEXTFIELD;
	}
	
	public static String getSingleTagTreeField(){
		return SINGLETAGTREEFIELD;
	}
	
	public static String getSingleTagSelect(){
		return SINGLETAGSELECTFIELD;
	}
	
	public static String getSingleTagMultipleSelect(){
		return SINGLETAGMULTIPLESELECTFIELD;
	}
	
	public static String getSingleTagFile(){
		return SINGLETAGFILEFIELD;
	}
	
	public static String getSingleTagDate(){
		return SINGLETAGDATEFIELD;
	}
	
	public static String getSingleTagDateNoSlash(){
		return SINGLETAGDATEFIELDNOSLASH;
	}
	
	public static String getSingleTagDateMonth(){
		return SINGLETAGDATEFIELDMONTH;
	}
	
	public static String getSingleTagDateHMS(){
		return SINGLETAGDATEFIELDHMS;
	}
	
	public static String getSingleTagDateSlashHMS(){
		return SINGLETAGDATESLASHHMS;
	}

	public static String getShowlistlevelautodtoprefix() {
		return SHOWLISTLEVELAUTODTOPREFIX;
	}
	
	public static String getSHShowlistlevelautodtoSHprefix() {
		return SHSHOWLISTLEVELAUTODTOSHPREFIX;
	}
	
	public static String getCKShowlistlevelautodtoprefix() {
		return CKSHOWLISTLEVELAUTODTOPREFIX;
	}
	
	public static String getCharsetType(){
		return CHARSETTYPE;
	}
	
	public static String getCharsetTypeValue(){
		return (String)getApplication().get(CHARSETTYPE);
	}
	
	public static String getLinkedUrl(){
		return LINKEDURL + SEG;
	}
	
	public static String getOpenLinkedUrl(){
		return OPENLINKEDURL + SEG;
	}
	public static String getListSingleTag()
	{
		return SINGLETAGLIST;
	}
	public static String getCardSingleTag()
	{
		return SINGLETAGCARD;
	}
	
	public static String getTabSingleTag()
	{
		return SINGLETAGTAB;
	}
	
}
