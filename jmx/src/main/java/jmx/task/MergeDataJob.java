package jmx.task;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import jmx.dto.J_S_CPU;
import jmx.service.JmxBaseService;
import jmx.vo.JCWhereVO;
import framework.helper.ExceptionLog;
import framework.helper.FrameworkFactory;

/**
 * 项目名称：jmx<br>
 * *********************************<br>
 * <P>类名称：MergeDataJob</P>
 * *********************************<br>
 * <P>类描述：汇总任务</P>
 * 创建人：王川<br>
 * 创建时间：2016-12-1 上午10:56:42<br>
 * 修改人：王川<br>
 * 修改时间：2016-12-1 上午10:56:42<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class MergeDataJob implements Serializable{

	/**  **/
	private static final long serialVersionUID = -8957420384209575614L;
	
	private Calendar odate;
	
	private String cdate;
	
	private SimpleDateFormat df;

	/**
	 * <p>方法描述: 汇总--分</p>
	 *
	 * <p>方法备注: </p>
	 *
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-11-30 上午9:15:07</p>
	 */
	public synchronized void merge_m() {
		try{
			merge("yyyy-MM-dd HH:mm", "s", "m", Calendar.MINUTE);
		}catch(Exception e){
			cdate = null;
			ExceptionLog.CreateLog(e);
		}
	}

	/**
	 * <p>方法描述:汇总--时 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-12-1 上午10:54:46</p>
	 */
	public synchronized void merge_h() {
		try{
			merge("yyyy-MM-dd HH", "m", "h", Calendar.HOUR);
		}catch(Exception e){
			cdate = null;
			ExceptionLog.CreateLog(e);
		}
	}
	
	/**
	 * <p>方法描述:汇总--天 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-12-1 上午10:55:14</p>
	 */
	public synchronized void merge_d() {
		try{
			merge("yyyy-MM-dd", "h", "d", Calendar.DAY_OF_MONTH);
		}catch(Exception e){
			cdate = null;
			ExceptionLog.CreateLog(e);
		}
	}

	/**
	 * <p>方法描述:汇总--月 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-12-1 上午10:55:21</p>
	 */
	public synchronized void merge_M() {
		try{
			merge("yyyy-MM", "d", "Mt", Calendar.MONTH);
		}catch(Exception e){
			cdate = null;
			ExceptionLog.CreateLog(e);
		}
	}

	/**
	 * <p>方法描述:汇总--年 </p>
	 *
	 * <p>方法备注: </p>
	 *
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-12-1 上午10:56:07</p>
	 */
	public synchronized void merge_y() {
		try{
			merge("yyyy", "Mt", "y", Calendar.YEAR);
		}catch(Exception e){
			cdate = null;
			ExceptionLog.CreateLog(e);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void merge(String fmt,String cfq,String cfq_,int cunit) throws Exception{
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		JmxBaseService service = (JmxBaseService) FrameworkFactory.CreateBean("jmxBaseService");
		if(cdate==null){
			df = new SimpleDateFormat(fmt);
			JCWhereVO where = new JCWhereVO();
			where.setCfq(cfq_);
			List<J_S_CPU> ls = (List<J_S_CPU>) service.getJCInfos(where, J_S_CPU.class);
			if(ls.size()>0){
				cdate = ls.get(0).getCdate();
				odate = Calendar.getInstance();
				odate.setTime(df.parse(cdate));
			}
			else{
				where.setCfq(cfq);
				where.setDesc(false);
				ls = (List<J_S_CPU>) service.getJCInfos(where, J_S_CPU.class);
				if(ls.size()>0){
					cdate = ls.get(0).getCdate();
					odate = Calendar.getInstance();
					odate.setTime(df.parse(cdate));
				}
				else
					return;
			}
		}
		if(odate==null)
			return;
		now.add(Calendar.MINUTE, -1);
		//当汇总时间小于当前时间时+1进行汇总,直到大于当前时间为止
		while(odate.before(now)){
			cdate = df.format(odate.getTime());
			service.merge(cfq, cfq_, cdate);
			odate.add(cunit, 1);
		}
	}
	
}
