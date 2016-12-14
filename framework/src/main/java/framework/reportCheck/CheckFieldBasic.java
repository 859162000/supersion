package framework.reportCheck;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.xwork.StringUtils;


import framework.reportCheck.helper.CheckUtils;


/**
 * 基础校验
 * @author Transino
 *
 */
 
public class CheckFieldBasic {
	
	
	private String getUniqueStr(Object value){
		String valueStr;
		if(value == null){
			//valueStr = "null";
			valueStr = "";
		}
		else{
			valueStr = value.toString();
		}
		if(StringUtils.isBlank(valueStr)){
			valueStr = this.discription + "：";
		}
		else{
			valueStr = this.discription + "：" + valueStr + "， ";
		}
		
		return valueStr;
	}
	
	public List<String> Check(Map<String, Object> mapObject, String uniqueStr) throws Exception{
		List<String> messageList = new ArrayList<String>();
		Object value = mapObject.get(this.name.toUpperCase());
		uniqueStr = uniqueStr + getUniqueStr(value);
        if(value == null || StringUtils.isBlank(value.toString())){
        	if(this.emptyCheck){
        		messageList.add(uniqueStr + "不能为空。");
    		}
		}
        else if(!value.toString().equals(ignoreVal)) { // 为ignoreVal时，直接成功
        	if(startLength != null && endLength == null){ // 没有长度上限时
        		if(value.toString().length() < startLength){ // 最小长度
        			messageList.add(uniqueStr + "应大于等于" + startLength + "个字符。");
        		}
        		
        		if(chnCheck){ // 中文校验
            		byte[] buf = value.toString().getBytes();
            		boolean haveChn = false;
            		for (byte b : buf) {
            			if(b<0){
            				haveChn = true;
            				break;
            			}
            		}
            
            		if(haveChn){
            			String temp = null;
            			Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
            			Matcher m = p.matcher(value.toString());
            			while (m.find()){
            				temp = m.group(0);
                          if(temp.length()<startLength){
                         	messageList.add(uniqueStr + "应大于等于" + startLength + "个中文字符。");	
                          }
            			}
            		}
            	}
        	}
        	
        	if(endLength != null && startLength == null){ // 没有长度下限时
        		if(value.toString().length() > endLength){
        			messageList.add(uniqueStr + "应小于等于" + endLength + "个字符。");
        		}
        	}
        	
        	if(endLength != null && startLength != null && endLength==startLength){ // 指定长度时
        		if(value.toString().length() != endLength){
        			messageList.add(uniqueStr + "应等于" + endLength + "个字符。");
        		}
        	}
        	
        	if(endLength != null && startLength != null && endLength != startLength){ // 指定了上下限时
        		if(value.toString().length()>endLength ||value.toString().length()<startLength){
        			messageList.add(uniqueStr + "应大于等于" + startLength + "个字符，小于等于"+endLength + "个字符.");
        		}
        	}
        //startByteLength,endByteLength
        	if(startByteLength != null && endByteLength == null){ // 没有长度上限时
        		if(value.toString().getBytes("GBK").length < startByteLength){ // 最小长度
        			messageList.add(uniqueStr + "应大于等于" + startByteLength + "个字符。");
        		}
        		
        		if(chnCheck){ // 中文校验
            		byte[] buf = value.toString().getBytes("GBK");
            		boolean haveChn = false;
            		for (byte b : buf) {
            			if(b<0){
            				haveChn = true;
            				break;
            			}
            		}
            
            		if(haveChn){
            			String temp = null;
            			Pattern p = Pattern.compile("[\u4E00-\u9FA5]+");
            			Matcher m = p.matcher(value.toString());
            			while (m.find()){
            				temp = m.group(0);
                          if(temp.length()<startByteLength){
                         	messageList.add(uniqueStr + "应大于等于" + startByteLength + "个中文字符。");	
                          }
            			}
            		}
            	}
        	}
        	
        	if(endByteLength != null && startByteLength == null){ // 没有长度下限时
        		if(value.toString().getBytes("GBK").length > endByteLength){
        			messageList.add(uniqueStr + "应小于等于" + endByteLength + "个字符。");
        		}
        	}
        	
        	if(endByteLength != null && startByteLength != null && endByteLength==startByteLength){ // 指定长度时
        		if(value.toString().getBytes("GBK").length != endByteLength){
        			messageList.add(uniqueStr + "应等于" + endByteLength + "个字符。");
        		}
        	}
        	
        	if(endByteLength != null && startByteLength != null && endByteLength != startByteLength){ // 指定了上下限时
        		if(value.toString().getBytes("GBK").length > endByteLength || value.toString().getBytes("GBK").length < startByteLength){
        			messageList.add(uniqueStr + "应大于等于" + startByteLength + "个字符，小于等于"+endByteLength + "个字符.");
        		}
        	}
        	
        	if(chnCheck){ // 中文校验
        		byte[] buf = value.toString().getBytes();
        		boolean haveChn = false;
        		for (byte b : buf) {
        			if(b<0){
        				haveChn = true;
        				break;
        			}
        		}
        		if(!haveChn){
        			messageList.add(uniqueStr +"应含中文");
        		}
        	}
        	
        	if(noChnCheck){ //非中文         
        		Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
                Matcher m = p.matcher(value.toString());
        		if(m.find()){
        			messageList.add(uniqueStr +"不应含中文");
			   }
        		
        	}
        	
        	if(numCheck){ // 数字检查  
    			if(value.toString().matches("^-?[0-9]*")){
    				messageList.add(uniqueStr + "不能全为数字");
    			}
    		}
        	
        	if(repeatLetter){ // 重复字母
        		if(value.toString().matches(".*([a-zA-Z]).*\\1.*")){
        			messageList.add(uniqueStr + "不能有重复字母");
        		}
        	}
        	
        	if(errorcharCheck){
        		if(!value.toString().matches("[^～  ！ ◎ ＃ ￥ ％  ……  ※  ×  ＋  ＝   ：  , “   ” ？《   》  ，  。  、~ ! @ $ % ^ & * + | \\  / ; \\ }]+")){
        			messageList.add(uniqueStr + "不能有错误字符出现");
        		}
        	}
        	
        	if(upperCheck){
        		if(!value.toString().matches("[A-Z]*")){
        			messageList.add(uniqueStr + "应为大写字母。");
        		}
        	}
        	
        	if(digitalCheck){	// ^-?[0-9]* //[-+]*[0-9][.][0-9]+|[-+]*[1-9][0-9]*|^[0]$
            	if(!value.toString().matches("[-+]*[0-9]+|[-+]*[1-9][0-9]*|^[0]$")){
            		messageList.add(uniqueStr + "应为数字。");
        		}
        	}
        	
        	if(numAndBeginNum){
        		if(!value.toString().matches("[a-zA-Z_$][a-zA-Z_0-9$]*")){
        			messageList.add(uniqueStr + "不能全为数字且不能为数字开头");
        		}
        	}
        	                                 
        	if(valueDecimalLength != null){
        		int index = value.toString().lastIndexOf(".");
        		String firstValue=value.toString().substring(0,1);
        		String indexBeforeValue="";
        		if(index>-1){
        		  indexBeforeValue=value.toString().substring(0,index);
        		  if(firstValue.equals("0") && indexBeforeValue.toString().length()>1 && value.toString().length()>1){
        			  messageList.add(uniqueStr + "应为非零开头");
        		  }
        		}
        	    if((index==-1 && firstValue.equals("0")) && value.toString().length()>1){
        			messageList.add(uniqueStr + "应为非零开头");
        		}
        	    if(index > -1) {
        			int len = value.toString().substring(index + 1).length();
        			if(len==0){
        				messageList.add(uniqueStr + "整数不能包含小数点");
        			}
        		}
        	    //数字前面不能添加加号
        	    if(value.toString().contains("+")){
        	    	messageList.add(uniqueStr + "不能包含+号");
        	    }
        	    if(!value.toString().matches("^(?:0|[+-]?[0-9]\\d*)(\\.\\d{0,"+valueDecimalLength+"})?$")){
        			messageList.add(uniqueStr + "应最多保留" +valueDecimalLength.toString() + "位小数。");
       		    }
        	} 
        	if(mustValueDecimalLength != null){
        		int index = value.toString().lastIndexOf(".");
        		String firstValue=value.toString().substring(0,1);
        		String indexBeforeValue="";
        		if(index>-1){
        		  indexBeforeValue=value.toString().substring(0,index);
        		  if(firstValue.equals("0") && indexBeforeValue.toString().length()>1 && value.toString().length()>1){
        			  messageList.add(uniqueStr + "应为非零开头");
        		  }
        		}
        		if(index==-1){
        			messageList.add(uniqueStr + "必须保留" +mustValueDecimalLength.toString() + "位小数。");
        			 if((firstValue.equals("0") && value.toString().length()>1 ) ){
             			messageList.add(uniqueStr + "应为非零开头");
             		}
        		} 
        		//数字前面不能添加加号
        		if(value.toString().contains("+")){
        	    	messageList.add(uniqueStr + "不能包含+号");
        	    }
        	   if(!value.toString().matches("^(?:0|[+-]?[0-9]\\d*)(\\.\\d{"+mustValueDecimalLength+"})?$")){
         			messageList.add(uniqueStr + "必须保留" +mustValueDecimalLength.toString() + "位小数。");
        		}
        	} 	
       
        	if(dateCheck){
        		try {
        			String[] strVals = value.toString().split("-");
	        		if(strVals.length == 1 && strVals[0].length()==8) {
	        			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	        			format.setLenient(false);
	        			format.parse(value.toString().trim());
	        		} else if (strVals.length == 3) {
	        			if(strVals[0].length()!=4 || strVals[1].length()!=2 || strVals[2].length()!=2){
	        				messageList.add(uniqueStr + "应为合法日期(yyyy-MM-dd)。");
	        			}
	        			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        			format.setLenient(false);
	        			format.parse(value.toString().trim());
	        		}
	        		else
	        			messageList.add(uniqueStr + "应为合法日期。");
	        	}
        		catch(Exception ex) {
    				messageList.add(uniqueStr + "应为合法日期。");
    			}
        	}
        	
        	if(getValueMax() != null) { // 上限值
//        		if(!value.toString().matches("^-?[0-9]*")){  // 变动率为小数，应允许负数、小数
            	if(!value.toString().matches("^-?[0-9]+([.]{1}[0-9]+){0,1}$")){
        			messageList.add(uniqueStr + "应为数字。");
        		}else{
        			BigDecimal val = new BigDecimal(value.toString());
            		if(val.compareTo(getValueMax()) > 0 ) { // 超过上限
            			messageList.add(uniqueStr + "应小于或等于" + getValueMax().toString() + "。");
            		}
        		}
        		
        	}
        	
        	if(getValueMin() != null) { // 下限值
//        		if(!value.toString().matches("^-?[0-9]*")){  // 变动率为小数，应允许负数、小数
            	if(!value.toString().matches("^-?[0-9]+([.]{1}[0-9]+){0,1}$")){
        			messageList.add(uniqueStr + "应为数字。");
        		}else{
        			BigDecimal val = new BigDecimal(value.toString());
            		if(val.compareTo(getValueMin()) < 0 ) { // 低于下限
            			messageList.add(uniqueStr + "应大于或等于" + getValueMin().toString() + "。");
            		}
        		}
        	}
        	
        	if(getNotEqualValue()!=null){
        		if(value.equals(this.notEqualValue)){
        			messageList.add(uniqueStr + "必须不等于" + getNotEqualValue().toString() + "。");
        		}
        	}
        	if(dateCheckForSixBit){
	        	if(!value.toString().matches("^\\d{4}(0[1-9]|1[0-2])$")){
	        		messageList.add(uniqueStr + "应为YYYYMM格式的合法日期");
	        	}
        	}
        	if(getRegularExpressionCheck()!=null && !StringUtils.isBlank(getRegularExpressionCheck())){
        		if(!value.toString().matches(regularExpressionCheck)){
        			messageList.add(uniqueStr + regularExpressionCheckDisc);
        		}
        	}
        	//insert 20160705
        	if(systemIDCardNo){//身份证号码
        		if(!CheckUtils.IDCardCheck(value.toString()).equals("")){
        			messageList.add(uniqueStr + CheckUtils.IDCardCheck(value.toString()));
        		}
        	}
        	if(systemZZJGDM){//组织机构代码
        		if(!CheckUtils.OrganizationCodeCheck(value.toString()).equals("")){
        			messageList.add(uniqueStr + CheckUtils.OrganizationCodeCheck(value.toString()));
        		}
        	}
        	if(systemDKKBM){//贷款卡编码
        		if(!CheckUtils.CheckDKKBM(value.toString()).equals("")){
        			messageList.add(uniqueStr + CheckUtils.CheckDKKBM(value.toString()));
        		}
        	}
        	if(systemJRJGDM){//金融机构代码
        		if(value.toString().getBytes("GBK").length!=11){
        			messageList.add(uniqueStr +"应为11字符");
        		}else{
        			byte[] byteArry = new byte[0];
            		byteArry=value.toString().getBytes();
            		if(!CheckUtils.checkJRJG(byteArry).equals("")){
            			messageList.add(uniqueStr + CheckUtils.checkJRJG(byteArry));
            		}
        		}
        	}
        	if(systemKHXKZHZH){//开户许可证核准号
    			byte[] byteArry = new byte[0];
        		byteArry=value.toString().getBytes();
        		if(!CheckUtils.checkSaccBaseLicNO(byteArry).equals("")){
        			messageList.add(uniqueStr + CheckUtils.checkSaccBaseLicNO(byteArry));
        		}
        	}
        	
        	if(systemNSRSBH){//纳税人识别号
        		if(value.toString().getBytes("GBK").length!=15 && value.toString().getBytes("GBK").length!=18 && value.toString().getBytes("GBK").length!=20){
        			messageList.add(uniqueStr +"应为15或18或20个字符");
        		}else{
        			byte[] byteArry = new byte[0];
            		byteArry=value.toString().getBytes();
            	   if(!CheckUtils.checkTaxNO(byteArry).equals("")){
            			messageList.add(uniqueStr + CheckUtils.checkTaxNO(byteArry));
            		}
        		}
        		
        	}
        	if(systemJGXYDM){//机构信用代码
        		byte[] byteArry = new byte[0];
        		byteArry=value.toString().getBytes();
        		if(!CheckUtils.checkCreditCode(byteArry).equals("")){
        			messageList.add(uniqueStr + CheckUtils.checkCreditCode(byteArry));
        		}
        	}
        	
        }
        List<String> strMessageList = new ArrayList<String>();
		if(messageList.size()>0){
			for (String strMessage : messageList) {
				if(strMessage.contains("!=") || strMessage.contains(">=") || strMessage.contains("<=") || strMessage.contains("<") || strMessage.contains(">") || strMessage.contains("=") || strMessage.contains("notNull") || strMessage.contains("null")){
					strMessageList.add(strMessage.replace("!=","不等于").replace(">=", "大于等于").replace("<=", "小于等于").replace("<", "小于").replace(">", "大于").replace("=", "等于").replace("notNull", "非空").replace("null", "空"));
				}
				else {
					strMessageList.add(strMessage);
				}
			}
		}
		
		return strMessageList;
	}
	
	private String name;
	private String discription;
	private boolean emptyCheck; 
	private boolean digitalCheck; 
	private boolean dateCheck; 
	private boolean upperCheck; 
	private boolean chnCheck; 
	private boolean noChnCheck;
	private Integer startLength;
	private Integer endLength;
	private Integer valueDecimalLength;//最大保留小数值
	private Integer mustValueDecimalLength;//必须保留小数值
	private Double compareValue;
	private boolean numCheck;
	private boolean repeatLetter;
	private boolean errorcharCheck;
	private String ignoreVal;
	private boolean numAndBeginNum; 
	private BigDecimal valueMax = null;
	private BigDecimal valueMin = null;
	private String notEqualValue;
	private boolean dateCheckForSixBit;
	private String regularExpressionCheck;
	private String regularExpressionCheckDisc;
	private Integer startByteLength;
	private Integer endByteLength;
	
	private boolean systemIDCardNo;//身份证号码
	private boolean systemZZJGDM;//组织机构代码
	private boolean systemDKKBM;//贷款卡编码
	private boolean systemJRJGDM;//金融机构代码
	private boolean systemKHXKZHZH;//开户许可证核准号
	private boolean systemNSRSBH;//纳税人识别号
	private boolean systemJGXYDM;//机构信用代码
	
	public boolean isNumAndBeginNum() {
		return numAndBeginNum;
	}

	public void setNumAndBeginNum(boolean numAndBeginNum) {
		this.numAndBeginNum = numAndBeginNum;
	}

	public boolean isErrorcharCheck() {
		return errorcharCheck;
	}

	public void setErrorcharCheck(boolean errorcharCheck) {
		this.errorcharCheck = errorcharCheck;
	}

	public boolean isNumCheck() {
		return numCheck;
	}

	public void setNumCheck(boolean numCheck) {
		this.numCheck = numCheck;
	}

	public boolean isRepeatLetter() {
		return repeatLetter;
	}

	public void setRepeatLetter(boolean repeatLetter) {
		this.repeatLetter = repeatLetter;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStartLength() {
		return startLength;
	}

	public void setStartLength(Integer startLength) {
		this.startLength = startLength;
	}

	public Integer getEndLength() {
		return endLength;
	}

	public void setEndLength(Integer endLength) {
		this.endLength = endLength;
	}

	public Integer getValueDecimalLength() {
		return valueDecimalLength;
	}

	public void setValueDecimalLength(Integer valueDecimalLength) {
		this.valueDecimalLength = valueDecimalLength;
	}

	public Double getCompareValue() {
		return compareValue;
	}

	public void setCompareValue(Double compareValue) {
		this.compareValue = compareValue;
	}

	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public String getDiscription() {
		if(StringUtils.isBlank(discription)){
			return this.name;
		}
		return discription;
	}

	public boolean isEmptyCheck() {
		return emptyCheck;
	}

	public void setEmptyCheck(boolean emptyCheck) {
		this.emptyCheck = emptyCheck;
	}

	public boolean isDigitalCheck() {
		return digitalCheck;
	}

	public void setDigitalCheck(boolean digitalCheck) {
		this.digitalCheck = digitalCheck;
	}

	public boolean isDateCheck() {
		return dateCheck;
	}

	public void setDateCheck(boolean dateCheck) {
		this.dateCheck = dateCheck;
	}

	public boolean isUpperCheck() {
		return upperCheck;
	}

	public void setUpperCheck(boolean upperCheck) {
		this.upperCheck = upperCheck;
	}

	public boolean isChnCheck() {
		return chnCheck;
	}

	public void setChnCheck(boolean chnCheck) {
		this.chnCheck = chnCheck;
	}
	
	public static boolean isValidDate(String date) {
		if(date.matches("[0-9]*")){
			if (!date.matches("([\\d]{4}(((0[13578]|1[02])((0[1-9])|([12][0-9])|(3[01])))|(((0[469])|11)((0[1-9])|([12][1-9])|30))|(02((0[1-9])|(1[0-9])|(2[1-8])))))|((((([02468][048])|([13579][26]))00)|([0-9]{2}(([02468][048])|([13579][26]))))(((0[13578]|1[02])((0[1-9])|([12][0-9])|(3[01])))|(((0[469])|11)((0[1-9])|([12][1-9])|30))|(02((0[1-9])|(1[0-9])|(2[1-9])))))")){
				return false;	
			}
		} 
		
		else{
			if(!date.matches("((^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(10|12|0?[13578])([-\\/\\._])(3[01]|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(11|0?[469])([-\\/\\._])(30|[12][0-9]|0?[1-9])$)|(^((1[8-9]\\d{2})|([2-9]\\d{3}))([-\\/\\._])(0?2)([-\\/\\._])(2[0-8]|1[0-9]|0?[1-9])$)|(^([2468][048]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([3579][26]00)([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][0][48])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][2468][048])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([1][89][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$)|(^([2-9][0-9][13579][26])([-\\/\\._])(0?2)([-\\/\\._])(29)$))")){
	    		return false;
			} 
		}
		return true; 
	 }
	
	
	public static boolean isValidDate1(String date){
		 char dateSpace = date.charAt(4);
		 if ((dateSpace == '-') || (dateSpace == '/')||dateSpace=='.'||dateSpace=='_') {
			  String[] strs = date.split(String.valueOf(dateSpace));//以?分割字符串生成数组
			  String mothcheck = strs[1]; 
			  String daycheck=strs[2];
			  String [] num=new String[]{"1","2","3","4","5","6","7","8","9"};
			for(int i=0;i<num.length;i++ ){
				if(num[i].indexOf(mothcheck)!=-1||num[i].indexOf(daycheck)!=-1){
					return false;
				}
			}
		 }
		  			return true;
	}

	public void setIgnoreVal(String ignoreVal) {
		this.ignoreVal = ignoreVal;
	}

	public String getIgnoreVal() {
		return ignoreVal;
	}

	public void setValueMax(String valueMax) {
		if(valueMax != null && !valueMax.equals("")) {
			try {
				this.valueMax = new BigDecimal(valueMax);
			} catch (Exception ex) {
				this.valueMax = null;
			}
		}
	}

	public BigDecimal getValueMax() {
		return valueMax;
	}

	public void setValueMin(String valueMin) {
		if(valueMin != null && !valueMin.equals(""))
			try {
				this.valueMin = new BigDecimal(valueMin);
			} catch (Exception ex) {
				this.valueMin = null;
			}
	}

	public BigDecimal getValueMin() {
		return valueMin;
	}

	public void setNotEqualValue(String notEqualValue) {
		this.notEqualValue = notEqualValue;
	}

	public String getNotEqualValue() {
		return notEqualValue;
	}

	public void setDateCheckForSixBit(boolean dateCheckForSixBit) {
		this.dateCheckForSixBit = dateCheckForSixBit;
	}

	public boolean isDateCheckForSixBit() {
		return dateCheckForSixBit;
	}

	public void setNoChnCheck(boolean noChnCheck) {
		this.noChnCheck = noChnCheck;
	}

	public boolean isNoChnCheck() {
		return noChnCheck;
	}

	public void setMustValueDecimalLength(Integer mustValueDecimalLength) {
		this.mustValueDecimalLength = mustValueDecimalLength;
	}

	public Integer getMustValueDecimalLength() {
		return mustValueDecimalLength;
	}

	public void setRegularExpressionCheck(String regularExpressionCheck) {
		this.regularExpressionCheck = regularExpressionCheck;
	}

	public String getRegularExpressionCheck() {
		return regularExpressionCheck;
	}

	public void setRegularExpressionCheckDisc(String regularExpressionCheckDisc) {
		this.regularExpressionCheckDisc = regularExpressionCheckDisc;
	}

	public String getRegularExpressionCheckDisc() {
		return regularExpressionCheckDisc;
	}

	public Integer getStartByteLength() {
		return startByteLength;
	}

	public void setStartByteLength(Integer startByteLength) {
		this.startByteLength = startByteLength;
	}

	public Integer getEndByteLength() {
		return endByteLength;
	}

	public void setEndByteLength(Integer endByteLength) {
		this.endByteLength = endByteLength;
	}

	public boolean isSystemIDCardNo() {
		return systemIDCardNo;
	}

	public void setSystemIDCardNo(boolean systemIDCardNo) {
		this.systemIDCardNo = systemIDCardNo;
	}

	public boolean isSystemZZJGDM() {
		return systemZZJGDM;
	}

	public void setSystemZZJGDM(boolean systemZZJGDM) {
		this.systemZZJGDM = systemZZJGDM;
	}

	public boolean isSystemDKKBM() {
		return systemDKKBM;
	}

	public void setSystemDKKBM(boolean systemDKKBM) {
		this.systemDKKBM = systemDKKBM;
	}

	public boolean isSystemJRJGDM() {
		return systemJRJGDM;
	}

	public void setSystemJRJGDM(boolean systemJRJGDM) {
		this.systemJRJGDM = systemJRJGDM;
	}

	public boolean isSystemKHXKZHZH() {
		return systemKHXKZHZH;
	}

	public void setSystemKHXKZHZH(boolean systemKHXKZHZH) {
		this.systemKHXKZHZH = systemKHXKZHZH;
	}

	public boolean isSystemNSRSBH() {
		return systemNSRSBH;
	}

	public void setSystemNSRSBH(boolean systemNSRSBH) {
		this.systemNSRSBH = systemNSRSBH;
	}

	public boolean isSystemJGXYDM() {
		return systemJGXYDM;
	}

	public void setSystemJGXYDM(boolean systemJGXYDM) {
		this.systemJGXYDM = systemJGXYDM;
	}
}
