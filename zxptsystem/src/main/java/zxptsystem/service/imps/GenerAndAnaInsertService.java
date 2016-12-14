package zxptsystem.service.imps;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import zxptsystem.dto.E_RPT;
import zxptsystem.dto.E_RPT_DETAIL;
import zxptsystem.dto.I_RPT;
import zxptsystem.dto.I_RPT_DETAIL;
import framework.helper.FrameworkFactory;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.IParamVoidResultExecute;

/**
 * 生成报文及解析报文成功时操作相应表
 * @author xiajieli
 *
 */
public class GenerAndAnaInsertService {

	
	/**
	 * 生成报文成功后向I_RPT表插入数据
	 * @param strBWM 报文名
	 * @param strJRJGDM 金融机构代码
	 * @param strBWSCSJ 报文生成时间
	 * @param strBWLB 报文类别
	 * @param strBWNJLTS 报文内记录条数
	 * @throws Exception 
	 */
	public void InsertI_RPT(String strBWM, String strJRJGDM,String strBWSCSJ,String strBWLB,int strBWNJLTS) throws Exception{
		I_RPT iRPT=new I_RPT();
		iRPT.setStrBWM(strBWM);//报文名
		iRPT.setStrJRJGDM(strJRJGDM);//金融机构代码
		iRPT.setStrBWSCSJ(strBWSCSJ);//报文生成时间
		iRPT.setStrBWLB(strBWLB);//报文类别
		iRPT.setStrBWNJLTS(strBWNJLTS);//报文内记录条数
		
		iRPT.setStrZLJCSFYCW("否");//质量检查是否有错误（是/否）
		iRPT.setStrCWJLTS(0);//如质量检查发现错误，则错误记录条数
		iRPT.setStrSFSDCWFKBW("否");//是否收到错误反馈报文（是/否）
		iRPT.setStrFKCWJLTS(0);//反馈错误记录条数
		iRPT.setStrCWJLZSWXGTS(0);//错误记录中尚未纠改条数
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{iRPT,null});
	}
	
	/**
	 * 导入并解析反馈报文成功时更新I_RPT(根据报文生成名称)
	 * @param strBWM 上报报文名
	 * @param strSFSDCWFKBW 是否收到错误反馈报文（是/否）
	 * @param strFKBWDRSJ 反馈报文导入时间
	 * @param strFKBWM 反馈报文名
	 * @param strFKCWJLTS 反馈错误记录条数
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void UpdateI_RPT(String strBWM,String strSFSDCWFKBW, String strFKBWDRSJ,String strFKBWM,int strFKCWJLTS) throws Exception{
		
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(I_RPT.class);
		detachedCriteria.add(Restrictions.eq("strBWM", strBWM));
		List<I_RPT> i_RPTList = (List<I_RPT>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		
		if(i_RPTList.size()==1){
			for (I_RPT iRPT : i_RPTList) {
				iRPT.setStrSFSDCWFKBW(strSFSDCWFKBW);//是否收到错误反馈报文（是/否）
				iRPT.setStrFKBWDRSJ(strFKBWDRSJ);//反馈报文导入时间
				iRPT.setStrFKBWM(strFKBWM);//反馈报文名
				iRPT.setStrFKCWJLTS(strFKCWJLTS);//反馈错误记录条数
				
				IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectUpdateDao");
				singleObjectSaveDao.paramVoidResultExecute(new Object[]{iRPT,null});
			}
		}
		
	}
	
	
	/**
	 * 导入并解析反馈报文成功时插入I_RPT_DETAIL表
	 * @param strID  ID
	 * @param strBWM 报文名
	 * @param strBWSCSJ 报文生成时间
	 * @param strBWLB 报文类别
	 * @param strFKBWM 反馈报文名
	 * @param strFKBWDRSJ 反馈报文导入时间
	 * @param strCWXXJLXXXX 错误信息记录详细信息
	 * @throws Exception 
	 */
	public void InsertI_RPT_DETAIL(String strID, String strBWM,String strBWSCSJ,String strBWLB,String strFKBWM,String strFKBWDRSJ,String strCWXXJLXXXX) throws Exception{
		I_RPT_DETAIL iRPTDETAIL=new I_RPT_DETAIL();
		iRPTDETAIL.setStrID(strID);
		iRPTDETAIL.setStrBWM(strBWM);
		iRPTDETAIL.setStrBWSCSJ(strBWSCSJ);
		iRPTDETAIL.setStrBWLB(strBWLB);
		iRPTDETAIL.setStrFKBWM(strFKBWM);
		iRPTDETAIL.setStrFKBWDRSJ(strFKBWDRSJ);
		iRPTDETAIL.setStrCWXXJLXXXX(strCWXXJLXXXX);
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{iRPTDETAIL,null});
	}
	
	/**
	 * 生成报文成功后向E_RPT表插入数据
	 * @param strBWM 报文名
	 * @param strJRJGDM 金融机构代码
	 * @param strBWSCSJ 报文生成时间
	 * @param strBWWJZL 报文文件种类
	 * @param strBWWJSJLX 报文文件数据类型
	 * @param strBWNJLTS 报文内记录条数
	 * @throws Exception 
	 */
	public void InsertE_RPT(String strBWM, String strJRJGDM,String strBWSCSJ,String strBWWJZL,String strBWWJSJLX,int strBWNJLTS) throws Exception{
		E_RPT eRPT=new E_RPT();
		eRPT.setStrBWM(strBWM);//报文名
		eRPT.setStrJRJGDM(strJRJGDM);//金融机构代码
		eRPT.setStrBWSCSJ(strBWSCSJ);//报文生成时间
		eRPT.setStrBWWJZL(strBWWJZL);//报文文件种类
		eRPT.setStrBWWJSJLX(strBWWJSJLX);//报文文件数据类型
		eRPT.setStrBWNJLTS(strBWNJLTS);//报文内记录条数
		
		eRPT.setStrZLJCSFYCW("否");//质量检查是否有错误（是/否）
		eRPT.setStrCWJLTS(0);//如质量检查发现错误，则错误记录条数
		eRPT.setStrBWSFSDFK("否");//报文是否收到反馈（是/否）
		eRPT.setStrFKCWJLTS(0);//反馈错误记录条数
		eRPT.setStrCWJLZSWXGTS(0);//错误记录中尚未纠改条数
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{eRPT,null});
	}
	
	/**
	 * 导入并解析反馈报文成功时更新E_RPT(根据报文生成名称)
	 * @param strBWM 上报报文名
	 * @param strBWSFSDFK 报文是否收到反馈（是/否）
	 * @param strFKBWDRSJ 反馈报文导入时间
	 * @param strFKBWM 反馈报文名
	 * @param strFKBWSFTSSBCW 反馈报文是否提示上报错误（是/否）
	 * @param strFKCWJLTS 反馈错误记录条数
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void UpdateE_RPT(String strBWM,String strBWSFSDFK, String strFKBWDRSJ,String strFKBWM,String strFKBWSFTSSBCW,int strFKCWJLTS) throws Exception{

		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(E_RPT.class);
		detachedCriteria.add(Restrictions.eq("strBWM", strBWM));
		List<E_RPT> e_RPTList = (List<E_RPT>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		
		if(e_RPTList.size()==1){
			for (E_RPT e_RPT : e_RPTList) {
				e_RPT.setStrBWSFSDFK(strBWSFSDFK);//报文是否收到反馈（是/否）
				e_RPT.setStrFKBWDRSJ(strFKBWDRSJ);//反馈报文导入时间
				e_RPT.setStrFKBWM(strFKBWM);//反馈报文名
				e_RPT.setStrFKBWSFTSSBCW(strFKBWSFTSSBCW);//反馈报文是否提示上报错误（是/否）
				e_RPT.setStrFKCWJLTS(strFKCWJLTS);//反馈错误记录条数
			}																									
			IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveOrUpdateAllDao");
			singleObjectSaveDao.paramVoidResultExecute(new Object[]{e_RPTList,null});
		}
		
	}
	
	
	/**
	 * 导入并解析反馈报文成功时插入E_RPT_DETAIL表
	 * @param strID  ID
	 * @param strBWM 报文名
	 * @param strBWSCSJ 报文生成时间
	 * @param strBWWJZL 报文文件种类
	 * @param strBWWJSJLX 报文文件数据类型
	 * @param strFKBWM 反馈报文名
	 * @param strFKBWDRSJ 反馈报文导入时间
	 * @param strCWXXJLXXXX 错误信息记录详细信息
	 * @throws Exception 
	 */
	public void InsertE_RPT_DETAIL(String strID, String strBWM,String strBWSCSJ,String strBWWJZL,String strBWWJSJLX,String strFKBWM,String strFKBWDRSJ,String strCWXXJLXXXX) throws Exception{
		
		E_RPT_DETAIL eRPTDETAIL=new E_RPT_DETAIL();
		eRPTDETAIL.setStrID(strID);
		eRPTDETAIL.setStrBWM(strBWM);
		eRPTDETAIL.setStrBWSCSJ(strBWSCSJ);
		eRPTDETAIL.setStrBWWJZL(strBWWJZL);
		eRPTDETAIL.setStrBWWJSJLX(strBWWJSJLX);
		eRPTDETAIL.setStrFKBWM(strFKBWM);
		eRPTDETAIL.setStrFKBWDRSJ(strFKBWDRSJ);
		eRPTDETAIL.setStrCWXXJLXXXX(strCWXXJLXXXX);
		
		IParamVoidResultExecute singleObjectSaveDao = (IParamVoidResultExecute)FrameworkFactory.CreateBean("singleObjectSaveDao");
		singleObjectSaveDao.paramVoidResultExecute(new Object[]{eRPTDETAIL,null});
	
	}
	
	/**
	 * 根据报文名获取I_RPT对象
	 * @param strBWM 报文名
	 * @return i_RPTList 对象列表
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<I_RPT> GetI_RPTByBWM(String strBWM) throws Exception{
		List<I_RPT> i_RPTList=null;
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(I_RPT.class);
		detachedCriteria.add(Restrictions.eq("strBWM", strBWM));
		i_RPTList = (List<I_RPT>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		return i_RPTList;
	}
	
	/**
	 * 根据报文名获取E_RPT对象
	 * @param strBWM 报文名
	 * @return e_RPTList 对象列表
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public List<E_RPT> GetE_RPTByBWM(String strBWM) throws Exception{
		List<E_RPT> e_RPTList=new ArrayList<E_RPT>();
		IParamObjectResultExecute singleObjectFindByCriteriaDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("zxptsystem.dto.E_RPT"));
		detachedCriteria.add(Restrictions.eq("strBWM", strBWM));
		e_RPTList = (List<E_RPT>)singleObjectFindByCriteriaDao.paramObjectResultExecute(new Object[]{detachedCriteria, null});
		return e_RPTList;
	}
	
	
	
	

	
}
