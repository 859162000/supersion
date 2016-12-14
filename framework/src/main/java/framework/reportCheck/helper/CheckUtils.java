package framework.reportCheck.helper;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class CheckUtils {
	static int[] WEIGHT=new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
	static String[] FACTOR=new String[]{"1","0","X","9","8","7","6","5","4","3","2"};
	static int[] Wi=new int[]{3,7,9,10,5,8,4,2};
	
	//http://www.exceltip.net/thread-10833-1-1.html
	/**
	 * 组织机构代码校验
	 */
	public static String OrganizationCodeCheck(String strOrganizationCode){
		String messageResult="";
		if(!IsNullOrEmpty(strOrganizationCode)){
			if(strOrganizationCode.length()!=10){
				messageResult="长度不对!";
				return messageResult;
			}
			if (strOrganizationCode.equals("00000000-0")) {
				messageResult="校验不对!";
				return messageResult;
			   }
			if(!strOrganizationCode.substring(8, 9).equals("-")){
				messageResult="格式不对，缺少“-”!";
				return messageResult;
			}
			if(strOrganizationCode.matches("[a-z0-9A-Z\\-]")){
				messageResult="格式不对!";
				return messageResult;
			}
			boolean f=true;
			for(int index=0;index<8;index++){
				int c =strOrganizationCode.charAt(index);
				if(c>=65 && c<=90){
					continue;
				}else if(c>=48 && c<=57){
					continue;
				}else{
					f=false;
					break;
				}
			}
			if(!f){
				messageResult="格式不对!";
				return messageResult;
			}
			int sum=0;
			for(int index=0;index<8;index++){
				int c =strOrganizationCode.charAt(index);
				if(c>=65 &&c<=90){
					c-=55;
				}else
					c-=48;
				sum+=Wi[index]*c;
			}
			int c9=11-sum%11;
			String s9=String.valueOf(c9);
			if(c9==10){
				s9="X";
			}
			else if(c9==11){
				s9="0";
			}
			if(!strOrganizationCode.substring(9, 10).equals(s9)){
				messageResult="校验位不对!";
				return messageResult;
			}
		}
		return messageResult;
	}
	public static boolean IsNullOrEmpty(String v){
		if(null==v || v.equals(""))
			return true;
		return false;
	}
	
	/**
	 * 身份证号码校验
	 * @param strCertNo
	 * @return
	 */
	public static String IDCardCheck(String strCertNo){
		String messageResult = "";
		if(strCertNo.length()==18){
			int sum=0;
			for(int index=0;index<strCertNo.length()-1;index++){
				char c = strCertNo.charAt(index);
				if(c>=48 &&c<=57){
					sum+=(c-48)*WEIGHT[index];
				}else{
					messageResult="本体码应为数字！";
					break;
				}
			}
			
			if(messageResult.equals("")){
				int y=sum%11;
				if(!FACTOR[y].equals(strCertNo.substring(17))){
					messageResult="校验码不对！";
				}
			}
		}else if(strCertNo.length()==15){
			for(int index=0;index<strCertNo.length();index++){
				char c = strCertNo.charAt(index);
				if(c<48 || c>58){
					messageResult="应为数字！";
					break;
				}
			}
			if(messageResult.equals("")){
				Format sf = new SimpleDateFormat("yyMMdd");
				Date a;
				try {
					a = (Date)sf.parseObject((strCertNo.substring(6,12)));
					String t =sf.format(a);
					if(!t.equals(strCertNo.substring(6,12))){
						messageResult="出生日期不是有效日期";
					}
				} catch (ParseException e) {
					messageResult="出生日期不是有效日期";
				}
				
			}
		}else{
			messageResult="位数应为15位或18位！";
		}
		
		return messageResult;
	}
	
	/**
	 * 贷款卡编码验证
	 * @param financecode
	 * @return
	 */
	public static boolean checkDKK(String financecode){
		boolean result=true;
		int[] weightValue = new int[]{1,3,5,7,11,2,13,1,1,17,19,97,23,29};
	    int[] checkValue = new int[14];
	    int sum=0;
	    
		if(null!=financecode && financecode.getBytes().length==16){
			for(int index=0;index<financecode.length()-1;index++){
				char c=financecode.charAt(index);
				if(index<3){
					if((c>=48&&c<=57) || (c>=65&&c<=90)){
						continue;
					}else{
						result=false;
						break;
					}
				}else{
					if(c>=48&&c<=57){
						continue;
					}else{
						result=false;
						break;
					}
				}
			}
		}else{
			result=false;
		}
	    if(!result){
	    	return result;
	    }
	    for(int index=0;index<14;index++){
	    	char c=financecode.charAt(index);
	    	if ((c >= 65) && (c <= 90)) {
	    		checkValue[index]=c-55;
	    	}else{
	    		checkValue[index]=c-48;
	    	}
	    	sum+=weightValue[index]*checkValue[index];
	    }
	    int m=sum%97+1;
	    String ss=""+m;
	    if(m<10){
	    	ss="0"+m;
	    }
	    if(ss.equals(financecode.substring(14))){
	    	return true;
	    }
	    return false;
	  }

	/**
	 * 调用贷款卡编码的校验 
	 */
	public  static String CheckDKKBM(Object valueDKKBM){
		String messageResult="";
		if(valueDKKBM!=null && !valueDKKBM.toString().equals("")){
			if(valueDKKBM.toString().length()==16){
				boolean isTrue=CheckUtils.checkDKK(valueDKKBM.toString());
				if(!isTrue){
			    	messageResult="校验不对！";
				}
			}else{
				messageResult="应为16位！";
			}
		}
		return messageResult;
	}
	
	/**
	 * 企业金融机构代码验证
	 * @param financecode
	 * @return
	 */
	public static String checkJRJG(byte[] financecode){
		String messageResult = "";
		int M = 10;
	    int s = M;
	    int k = 9;
	    for (int i = k; i >= 0; i--) {
	      int temp = financecode[(k - i)];

	      if ((temp >= 48) && (temp <= 57)) {
	        temp -= 48;
	      }
	      else if (((temp >= 65) && (temp <= 90)) || ((temp >= 97) && (temp <= 122))) {
	        temp = 0;
	      }
	      else {
	    	  messageResult="校验不对！";
		      return messageResult;
	      }

	      if ((s + temp) % M == 0)
	      {
	        s = 9;
	      }
	      else {
	        s = (s + temp) % M * 2 % (M + 1);
	      }
	    }

	    s = M + 1 - s;
	    if (s == 10) {
	      s = 0;
	    }

	    if (((s == 11) && (financecode[10] == 88)) || (s == financecode[10] - 48)) {
	    	messageResult="";
	    	return messageResult;
	    }

	    return messageResult;
	  }
	
	/**
	 * 中征码验证(机构信用代码)
	 * @param creditCode
	 * @return
	 */
	  public static String checkCreditCode(byte[] creditCode) {
		  String messageResult = "";
		  if (creditCode.length != 18) {
			  messageResult="长度不对";
		      return messageResult;
		  }
	    if (!Pattern.matches("[A-Z]{1}[0-9]{16}[0-9A-Z\\*]{1}", new String(creditCode))){
	    	 messageResult="校验不对";
		     return messageResult;
	    }
	    int m = 36;
	    int s = m;
	    for (int i = 0; i <= creditCode.length - 2; i++) {
	      s = (s + char2num(creditCode[i])) % m;
	      if (s == 0) s = m;
	      s = s * 2 % (m + 1);
	    }
	   if(!((s + char2num(creditCode[(creditCode.length - 1)])) % m == 1)){
		   messageResult="校验不对";
		   return messageResult;
	   }
	    
	    return messageResult;
	  }
	  
	  private static int char2num(byte a) {
	    if (a == 42)
	      return 36;
	    if ((a >= 48) && (a <= 57)) {
	      return a - 48;
	    }

	    return a - 55;
	  }
	 
	  /**
	   * 开户许可证号校验
	   * @param saccBaseLicNO
	   * @return
	   */
	  public static String checkSaccBaseLicNO(byte[] saccBaseLicNO) {
		  	String messageResult = "";
		    String tmp = new String(saccBaseLicNO).trim();
		    if (tmp.length() != 14) {
		    	messageResult="位数必须为14位";
		    	return messageResult;
		    }
		    String orgCode = tmp.substring(0, 1);
		    if (!"J".equals(orgCode)) {
		    	messageResult="校验位不对";
		    	return messageResult;
		    }
		    Pattern p = Pattern.compile("[0-9]*");
		    boolean isNum = p.matcher(tmp.substring(1, 14)).matches();
		    if(!isNum){
		    	messageResult="校验不对";
		    	return messageResult;
		    }
		    return messageResult;
		  }
	  
	  /**
	   * 纳税人识别号
	   * @param taxNO
	   * @return
	   */
	  public static String checkTaxNO(byte[] taxNO){
		  String messageResult="";
	    String tmp = new String(taxNO).trim();
	    if(!(tmp.getBytes().length >= 15)){
	    	messageResult="校验不对";
	    	return messageResult;
	    }
	    return messageResult;
	  }
	  
}
