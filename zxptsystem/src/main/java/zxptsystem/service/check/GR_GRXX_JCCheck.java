package zxptsystem.service.check;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import framework.helper.TypeParse;
import framework.services.interfaces.ICheckWithParam;
import framework.services.interfaces.MessageResult;
/**
 * 个人征信特殊校验
 * @author Administrator
 *
 */
public class GR_GRXX_JCCheck implements ICheckWithParam {
	
	
	@Override
	public MessageResult Check(Map<String, Object> mapObject) throws Exception {
		
		MessageResult messageResult = new MessageResult();
		
		// 校验业务种类
		checkYWZL(messageResult, mapObject);
		// 校验24个月（账户）还款状态
		checkHKZT_24(messageResult, mapObject);
		
		return messageResult;
	}
	
	// 校验业务种类
	private void checkYWZL(MessageResult messageResult, Map<String, Object> mapObject) {
		String regex = null;// 正则表达式
		String YE = "";
		String YWZL = "";
		String KHRQ = "";
		String HKZT = "";
		String HKZT_24 = "";// 24个月（账户）还款状态（当月）
		String WZFYE = "";
		String YWZLXF = "";
		String JSYHKRQ = "";
		String ZHYYZXXTS = "";
		String YQ31_60HDKBJ = "";
		String YQ61_90HDKBJ = "";
		String YQ91_180HDKBJ = "";
		String YQ180YSHDKBJ = "";
		
		if(mapObject.get("YE")!=null){
			YE=mapObject.get("YE").toString();
		}
		if(mapObject.get("YWZL")!=null){
			YWZL=mapObject.get("YWZL").toString();
		}
		if(mapObject.get("KHRQ")!=null){
			KHRQ=mapObject.get("KHRQ").toString();
		}
		if(mapObject.get("HKZT")!=null){
			HKZT=mapObject.get("HKZT").toString();
		}
		if(mapObject.get("HKZT_24")!=null){
			HKZT_24=mapObject.get("HKZT_24").toString();
		}
		if(mapObject.get("WZFYE")!=null){
			WZFYE=mapObject.get("WZFYE").toString();
		}
		if(mapObject.get("YWZLXF")!=null){
			YWZLXF=mapObject.get("YWZLXF").toString();
		}
		if(mapObject.get("JSYHKRQ")!=null){
			JSYHKRQ=mapObject.get("JSYHKRQ").toString();
		}
		if(mapObject.get("ZHYYZXXTS")!=null){
			ZHYYZXXTS=mapObject.get("ZHYYZXXTS").toString();
		}
		if(mapObject.get("YQ31_60HDKBJ")!=null){
			YQ31_60HDKBJ=mapObject.get("YQ31_60HDKBJ").toString();
		}
		if(mapObject.get("YQ61_90HDKBJ")!=null){
			YQ61_90HDKBJ=mapObject.get("YQ61_90HDKBJ").toString();
		}
		if(mapObject.get("YQ91_180HDKBJ")!=null){
			YQ91_180HDKBJ=mapObject.get("YQ91_180HDKBJ").toString();
		}
		if(mapObject.get("YQ180YSHDKBJ")!=null){
			YQ180YSHDKBJ=mapObject.get("YQ180YSHDKBJ").toString();
		}
		
		// 业务种类为信用卡
		if(!StringUtils.isBlank(YWZL) && YWZL.equals("2")) {
			// 业务种类细分为“准贷记卡”
			if(!StringUtils.isBlank(YWZLXF) && YWZLXF.equals("71")) {
				if(!StringUtils.isBlank(WZFYE) && !WZFYE.equals("0000000000")) {
					regex = "([/*N1-7DZCG#]{23}[1-7]{1})";
					if(!StringUtils.isBlank(HKZT) && HKZT.matches(regex)) {
						if(!HKZT_24.equals("7")) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“2-信用卡”，" +
									"且“业务种类细分”为“71-准贷记卡”时，若“透支180天以上未支付余额”不为“0000000000”且“24个月（账户）还款状态”最后一位" +
									"为数字，则“24个月（账户）还款状态”最后一位必须为“7”。");
							
						}// 24个月还款状态最后一位为数字必须为7
						
						// 透支180天以上未付余额与余额
						if(StringUtils.isBlank(WZFYE)) {
							WZFYE = "";
						}
						if(StringUtils.isBlank(YE)) {
							YE = "";
						}
						if(!WZFYE.equals(YE)) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“2-信用卡”，" +
									"且“业务种类细分”为“71-准贷记卡”时，若“透支180天以上未支付余额”不为“0000000000”且“24个月（账户）还款状态”最后一位" +
									"为数字，则“透支180天以上未付余额”必须等于“余额”。");
						}
					}// 24个月还款状态最后一位为数字
				}
				
				if(!StringUtils.isBlank(WZFYE) && WZFYE.equals("0")) {
					if(HKZT_24.equals("7")) {
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("当“业务种类”为“2-信用卡”，" +
								"且“业务种类细分”为“71-准贷记卡”时，若“透支180天以上未支付余额”为“0”，则“24个月（账户）还款状态”最后一位必须不为“7”。");
					}
				}
			}// 准贷记卡
		}
		
		// 业务种类为贷款
		if(!StringUtils.isBlank(YWZL) && YWZL.equals("1")) {
			// 新开立账户
			if(!StringUtils.isBlank(ZHYYZXXTS) && ZHYYZXXTS.equals("2")) {
				if(!StringUtils.isBlank(JSYHKRQ) && !StringUtils.isBlank(KHRQ)) {
					Calendar cal = Calendar.getInstance();
					
					Date date = TypeParse.parseDate(JSYHKRQ);
					cal.setTime(date);
					int JSYHKYear = cal.get(Calendar.YEAR);// 结算应还款年数
					int JSYHKMoth = cal.get(Calendar.MONTH);// 结算应还款月数
					
					date = TypeParse.parseDate(KHRQ);
					cal.setTime(date);
					int KHRQYear = cal.get(Calendar.YEAR);// 开户日期年数
					int KHRQMoth = cal.get(Calendar.MONTH);// 开户日期月数
					
					// 结算应还款日期所在月数等于开户日期所在月数
					if(JSYHKYear == KHRQYear && JSYHKMoth == KHRQMoth) {
						// 逾期31-60天未归还贷款本金
						if(!StringUtils.isBlank(YQ31_60HDKBJ) && !YQ31_60HDKBJ.equals("0")) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“1-贷款”时，" +
								"若“账户拥有者信息提示”为“2-新开立账户”且“结算/应还款日期”所在月等于“开户日期”所在月，则“逾期31-60天未归还贷款本金”必须填0。");
							
						}
						
						// 逾期61-90天未归还贷款本金
						if(!StringUtils.isBlank(YQ61_90HDKBJ) && !YQ61_90HDKBJ.equals("0")) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“1-贷款”时，" +
								"若“账户拥有者信息提示”为“2-新开立账户”且“结算/应还款日期”所在月等于“开户日期”所在月，则“逾期61-90天未归还贷款本金”必须填0。");
						}
						
						// 逾期91-180天未归还贷款本金
						if(!StringUtils.isBlank(YQ91_180HDKBJ) && !YQ91_180HDKBJ.equals("0")) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“1-贷款”时，" +
								"若“账户拥有者信息提示”为“2-新开立账户”且“结算/应还款日期”所在月等于“开户日期”所在月，则“逾期91-180天未归还贷款本金”必须填0。");
						}
						
						// 逾期180天以上未归还贷款本金
						if(!StringUtils.isBlank(YQ180YSHDKBJ) && !YQ180YSHDKBJ.equals("0")) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("当“业务种类”为“1-贷款”时，" +
								"若“账户拥有者信息提示”为“2-新开立账户”且“结算/应还款日期”所在月等于“开户日期”所在月，则“逾期180天以上未归还贷款本金”必须填0。");
						}
					}
				}
			}
		}
	}
	
	// 校验24个月（账户）还款状态
	@SuppressWarnings("deprecation")
	private void checkHKZT_24(MessageResult messageResult, Map<String, Object> mapObject) {
		String KHRQ = "";
		String HKZT = "";
		String JSYHKRQ = "";
		
		if(mapObject.get("KHRQ")!=null){
			KHRQ=mapObject.get("KHRQ").toString();
		}
		if(mapObject.get("HKZT")!=null){
			HKZT=mapObject.get("HKZT").toString();
		}
		if(mapObject.get("JSYHKRQ")!=null){
			JSYHKRQ=mapObject.get("JSYHKRQ").toString();
		}
		if(HKZT.length()==24){
			Date KHRQDate = TypeParse.parseDate(KHRQ);
			Date JSYHKRQDate = TypeParse.parseDate(JSYHKRQ);
			int mothDiff =0;
			if(KHRQDate!=null && JSYHKRQDate!=null){
				mothDiff = (JSYHKRQDate.getYear() - KHRQDate.getYear())*12 + (JSYHKRQDate.getMonth() - KHRQDate.getMonth());
			}
			if(mothDiff >= 0 ){
				if(mothDiff >= 23) {
					if(HKZT.substring(1).trim().indexOf("/") > -1) {
						messageResult.setSuccess(false);
						messageResult.getMessageSet().add("“24个月（账户）还款状态”数据项值中，“开户日期”所在月之后各月还款状态不能为“/”。");
					}
				} else {
					String left = null;
					String right = null;
					if(mothDiff == 0) {
						left = HKZT.substring(0, HKZT.length()-1);
					} else {
						left = HKZT.substring(0, HKZT.length()-mothDiff-1);
						right = HKZT.substring(HKZT.length()-mothDiff-1);//右边包括开户日期当月
					}
					
					if(!StringUtils.isBlank(left)) {
						String regex = "([/]{"+left.length()+"})";
						if(!left.matches(regex)) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("“24个月（账户）还款状态”数据项值中，“开户日期”所在月之前各月还款状态必须为“/”。");
						}
					}
					
					if(!StringUtils.isBlank(right)) {
						if(right.indexOf("/") > -1) {
							messageResult.setSuccess(false);
							messageResult.getMessageSet().add("“24个月（账户）还款状态”数据项值中，“开户日期”所在月及之后各月还款状态不能为“/”。");
						}
					}
				}
			}
		}
		
	}
	
}
