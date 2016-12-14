package report.helper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 项目名称：itemreport<br>
 * *********************************<br>
 * <P>类名称：DateUtil</P>
 * *********************************<br>
 * <P>类描述：日期工具辅助类</P>
 * 创建人：王川<br>
 * 创建时间：2016-8-30 下午4:24:01<br>
 * 修改人：王川<br>
 * 修改时间：2016-8-30 下午4:24:01<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class DateUtil {
	
	private static String s_yyyy_MM_dd = "yyyy-MM-dd";
	private static SimpleDateFormat sf_yyyy_MM_dd = new SimpleDateFormat(s_yyyy_MM_dd);
	
	private static String s_yyyy_MM_dd_HH_mm_SS = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sf_yyyy_MM_dd_HH_mm_SS = new SimpleDateFormat(s_yyyy_MM_dd_HH_mm_SS);
	
	/**
	 * <p>方法描述:根据频度校正日期 </p>
	 *
	 * <p>方法备注: 返回 yyyy-MM-dd 字符串日期格式</p>
	 *
	 * @param date
	 * @param freq
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-30 下午4:27:00</p>
	 */
	public static String reviseDate(String strDate,String freq) throws Exception{
		Date date = parseyyyyMMddStrToDate(strDate);
		return reviseDate(date, freq);
	}
	
	public static String reviseDate(Date date,String freq){
		if(StringUtils.isBlank(freq))
			freq = "0";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, 1);
		switch(Integer.parseInt(freq)){
			case 6:	//年				
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DATE, 31);
				break;
				
			case 5:	//半年
				if (cal.get(Calendar.MONTH) <= Calendar.JUNE) {
					cal.add(Calendar.YEAR, -1);
					cal.set(Calendar.MONTH, Calendar.DECEMBER);
					cal.set(Calendar.DATE, 31);
				} else {
					cal.set(Calendar.MONTH, Calendar.JUNE);
					cal.set(Calendar.DATE, 30);
				}
				break;
				
			case 4:	//季				
				if (cal.get(Calendar.MONTH) <= Calendar.MARCH) {
					cal.add(Calendar.YEAR, -1);
					cal.set(Calendar.MONTH, Calendar.DECEMBER);
					cal.set(Calendar.DATE, 31);
				} else if (cal.get(Calendar.MONTH) <= Calendar.JUNE) {
					cal.set(Calendar.MONTH, Calendar.MARCH);
					cal.set(Calendar.DATE, 31);
				} else if (cal.get(Calendar.MONTH) <= Calendar.SEPTEMBER) {
					cal.set(Calendar.MONTH, Calendar.JUNE);
					cal.set(Calendar.DATE, 30);
				} else {
					cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
					cal.set(Calendar.DATE, 30);
				}
				break;
								
			case 3:	//月
				
				cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
				break;
				
			case 2:	//周
				cal.add(Calendar.DATE,-1);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				break;
				
			case 7:	//旬
				if (cal.get(Calendar.DATE) <= 10) {
					cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
				} else if (cal.get(Calendar.DATE) <= 20) {
					cal.set(Calendar.DATE, 10);
				} else {
					cal.set(Calendar.DATE, 20);
				}
				break;
				
			default: //日
				cal.add(Calendar.DATE,-1);
				break;
				
		}
		return formatDateToyyyyMMddStr(cal.getTime());
	}
	
	/**
	 * <p>方法描述: 根据频度返回上个时间点</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param strDate
	 * @param freq
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-30 下午6:48:10</p>
	 * @throws Exception 
	 */
	public static String preDate(String strDate,String freq) throws Exception{
		Date date = parseyyyyMMddStrToDate(strDate);
		return preDate(date, freq);
	}
	
	/**
	 * <p>方法描述: 根据频度返回上个时间点</p>
	 *		环比用
	 * <p>方法备注: </p>
	 *
	 * @param date
	 * @param freq
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-30 下午6:48:10</p>
	 */
	public static String preDate(Date date,String freq){
		if(StringUtils.isBlank(freq))
			freq = "0";
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		switch(Integer.parseInt(freq)){
			case 6:	//年				
				cal.add(Calendar.YEAR, -1);
				cal.set(Calendar.MONTH, Calendar.DECEMBER);
				cal.set(Calendar.DATE, 31);
				break;
				
			case 5:	//半年
				if (cal.get(Calendar.MONTH) <= Calendar.JUNE) {
					cal.add(Calendar.YEAR, -1);
					cal.set(Calendar.MONTH, Calendar.DECEMBER);
					cal.set(Calendar.DATE, 31);
				} else {
					cal.set(Calendar.MONTH, Calendar.JUNE);
					cal.set(Calendar.DATE, 30);
				}
				break;
				
			case 4:	//季				
				if (cal.get(Calendar.MONTH) <= Calendar.MARCH) {
					cal.add(Calendar.YEAR, -1);
					cal.set(Calendar.MONTH, Calendar.DECEMBER);
					cal.set(Calendar.DATE, 31);
				} else if (cal.get(Calendar.MONTH) <= Calendar.JUNE) {
					cal.set(Calendar.MONTH, Calendar.MARCH);
					cal.set(Calendar.DATE, 31);
				} else if (cal.get(Calendar.MONTH) <= Calendar.SEPTEMBER) {
					cal.set(Calendar.MONTH, Calendar.JUNE);
					cal.set(Calendar.DATE, 30);
				} else {
					cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
					cal.set(Calendar.DATE, 30);
				}
				break;
								
			case 3:	//月
				cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
				break;
				
			case 2:	//周
				cal.add(Calendar.DATE,-7);
				cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
				break;
				
			case 7:	//旬
				if (cal.get(Calendar.DATE) <= 10) {
					cal.add(Calendar.DATE, -cal.get(Calendar.DAY_OF_MONTH));
				} else if (cal.get(Calendar.DATE) <= 20) {
					cal.set(Calendar.DATE, 10);
				} else {
					cal.set(Calendar.DATE, 20);
				}
				break;
				
			default: //日
				cal.add(Calendar.DATE,-1);
				break;
				
		}
		return formatDateToyyyyMMddStr(cal.getTime());
	}
	
	/**
	 * <p>方法描述: 获取年初</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-31 下午6:28:34</p>
	 */
	public static String startYear(Date date){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);
		return formatDateToyyyyMMddStr(cal.getTime());
	}
	
	
	/**
	 * <p>方法描述:获取不同年的相同日期 </p>
	 * 			同比
	 * <p>方法备注:二月份如果是28日始终获取的是2月份的最后一天 </p>
	 *
	 * @param date
	 * @param num
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-1 上午10:17:51</p>
	 * @throws Exception 
	 */
	public static String preYear(String strDate,int num) throws Exception{
		Date date = parseyyyyMMddStrToDate(strDate);
		return preYear(date, num);
	}
	public static String preYear(Date date,int num){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.YEAR, num);
		// 2月份处理
		if(cal.get(Calendar.MONTH) == Calendar.FEBRUARY){
			if(cal.get(Calendar.DATE) == 28){
				cal.set(Calendar.DATE,1);
				cal.add(Calendar.MONTH, 1);
				cal.add(Calendar.DATE, -1);
			}
		}
		return formatDateToyyyyMMddStr(cal.getTime());
	}
	
	
	/**
	 * <p>方法描述: date_to_yyyyMMdd </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param date
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-8-30 下午4:46:36</p>
	 */
	public static String formatDateToyyyyMMddStr(Date date){
		return sf_yyyy_MM_dd.format(date);
	}
	
	public static String formarDate(Date date,String format){
		if(s_yyyy_MM_dd.equals(format)){
			return sf_yyyy_MM_dd.format(date);
		}
		else if(s_yyyy_MM_dd_HH_mm_SS.equals(format)){
			return sf_yyyy_MM_dd_HH_mm_SS.format(date);
		}
		else{
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.format(date);
		}
		
	}
	
	/**
	 * <p>方法描述:parse_yyyyMMdd_to_date </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * @param str
	 * @return
	 * @throws Exception
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-9-1 上午10:17:20</p>
	 */
	public static Date parseyyyyMMddStrToDate(String str) throws Exception{
		return sf_yyyy_MM_dd.parse(str);
	}
	
	public static void main(String[] args) throws Exception{
		System.out.println("年【2017-01-01】:"+reviseDate(parseyyyyMMddStrToDate("2017-01-01"), "6"));
		System.out.println("年【2016-12-31】:"+reviseDate(parseyyyyMMddStrToDate("2016-12-31"), "6"));
		System.out.println("年【2016-12-30】:"+reviseDate(parseyyyyMMddStrToDate("2016-12-30"), "6"));
		
		System.out.println("半年【2016-6-31】:"+reviseDate(parseyyyyMMddStrToDate("2016-6-31"), "5"));
		System.out.println("半年【2016-6-30】:"+reviseDate(parseyyyyMMddStrToDate("2016-6-30"), "5"));
		System.out.println("半年【2015-6-29】:"+reviseDate(parseyyyyMMddStrToDate("2015-6-29"), "5"));
		
		System.out.println("季【2016-01-01】:"+reviseDate(parseyyyyMMddStrToDate("2016-01-01"), "4"));
		System.out.println("季【2016-12-31】:"+reviseDate(parseyyyyMMddStrToDate("2016-12-31"), "4"));
		System.out.println("季【2016-12-30】:"+reviseDate(parseyyyyMMddStrToDate("2016-12-30"), "4"));
		
		System.out.println("月【2016-02-28】:"+reviseDate(parseyyyyMMddStrToDate("2016-02-28"), "3"));
		System.out.println("月【2016-02-29】:"+reviseDate(parseyyyyMMddStrToDate("2016-02-29"), "3"));
		System.out.println("月【2015-02-29】:"+reviseDate(parseyyyyMMddStrToDate("2015-02-29"), "3"));
		
		System.out.println("旬【2016-02-21】:"+reviseDate(parseyyyyMMddStrToDate("2016-02-21"), "7"));
		System.out.println("旬【2016-02-20】:"+reviseDate(parseyyyyMMddStrToDate("2016-02-20"), "7"));
		System.out.println("旬【2015-02-19】:"+reviseDate(parseyyyyMMddStrToDate("2015-02-19"), "7"));
		
		System.out.println("周【2016-09-01】:"+reviseDate(parseyyyyMMddStrToDate("2016-09-01"), "2"));
		System.out.println("周【2016-08-30】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-30"), "2"));
		System.out.println("周【2016-08-28】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-28"), "2"));
		System.out.println("周【2016-08-26】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-26"), "2"));
		System.out.println("周【2016-08-22】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-22"), "2"));
		System.out.println("周【2016-08-21】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-21"), "2"));
		System.out.println("周【2016-08-20】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-20"), "2"));
		System.out.println("周【2016-08-19】:"+reviseDate(parseyyyyMMddStrToDate("2016-08-19"), "2"));
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println("年【2017-01-01】:"+preDate(parseyyyyMMddStrToDate("2017-01-01"), "6"));
		System.out.println("年【2016-12-31】:"+preDate(parseyyyyMMddStrToDate("2016-12-31"), "6"));
		System.out.println("年【2016-12-30】:"+preDate(parseyyyyMMddStrToDate("2016-12-30"), "6"));
		
		System.out.println("半年【2016-6-31】:"+preDate(parseyyyyMMddStrToDate("2016-6-31"), "5"));
		System.out.println("半年【2016-6-30】:"+preDate(parseyyyyMMddStrToDate("2016-6-30"), "5"));
		System.out.println("半年【2015-6-29】:"+preDate(parseyyyyMMddStrToDate("2015-6-29"), "5"));
		
		System.out.println("季【2016-01-01】:"+preDate(parseyyyyMMddStrToDate("2016-01-01"), "4"));
		System.out.println("季【2016-12-31】:"+preDate(parseyyyyMMddStrToDate("2016-12-31"), "4"));
		System.out.println("季【2016-12-31】:"+preDate(parseyyyyMMddStrToDate("2016-12-30"), "4"));
		
		System.out.println("月【2016-02-28】:"+preDate(parseyyyyMMddStrToDate("2016-02-28"), "3"));
		System.out.println("月【2016-02-29】:"+preDate(parseyyyyMMddStrToDate("2016-02-29"), "3"));
		System.out.println("月【2015-02-29】:"+preDate(parseyyyyMMddStrToDate("2015-02-29"), "3"));
		
		System.out.println("旬【2016-02-21】:"+preDate(parseyyyyMMddStrToDate("2016-02-21"), "7"));
		System.out.println("旬【2016-02-20】:"+preDate(parseyyyyMMddStrToDate("2016-02-20"), "7"));
		System.out.println("旬【2015-02-19】:"+preDate(parseyyyyMMddStrToDate("2015-02-19"), "7"));
		
		System.out.println("周【2016-09-01】:"+preDate(parseyyyyMMddStrToDate("2016-09-01"), "2"));
		System.out.println("周【2016-08-30】:"+preDate(parseyyyyMMddStrToDate("2016-08-30"), "2"));
		System.out.println("周【2016-08-28】:"+preDate(parseyyyyMMddStrToDate("2016-08-28"), "2"));
		System.out.println("周【2016-08-26】:"+preDate(parseyyyyMMddStrToDate("2016-08-26"), "2"));
		System.out.println("周【2016-08-22】:"+preDate(parseyyyyMMddStrToDate("2016-08-22"), "2"));
		System.out.println("周【2016-08-21】:"+preDate(parseyyyyMMddStrToDate("2016-08-21"), "2"));
		System.out.println("周【2016-08-20】:"+preDate(parseyyyyMMddStrToDate("2016-08-20"), "2"));
		System.out.println("周【2016-08-19】:"+preDate(parseyyyyMMddStrToDate("2016-08-19"), "2"));
		System.out.println("周【2016-08-19】:"+startYear(parseyyyyMMddStrToDate("2016-08-19")));
		System.out.println("周【2016-08-19】:"+preDate(parseyyyyMMddStrToDate("2016-08-19"), ""));
		
		
		System.out.println("月【2016-02-29】:"+preYear(parseyyyyMMddStrToDate("2017-02-28"), -1));
		System.out.println("月【2016-02-29】:"+preYear(parseyyyyMMddStrToDate("2017-02-27"), -1));
		System.out.println("月【2016-02-29】:"+preYear(parseyyyyMMddStrToDate("2016-02-29"), -1));
		System.out.println("月【2016-02-29】:"+preYear(parseyyyyMMddStrToDate("2016-02-28"), -1));
	}

}
