package dbgssystem.actions.imps;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.Naming;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import coresystem.dto.InstInfo;
import dbgssystem.actions.interfaces.IRemoteService;
import dbgssystem.dto.AutoDTO_DB_BFJNMXXX;
import dbgssystem.dto.AutoDTO_DB_DBHTXX;
import dbgssystem.dto.AutoDTO_DB_DBXX_JC;
import dbgssystem.dto.AutoDTO_DB_DCGKXX;
import dbgssystem.dto.AutoDTO_DB_DCMXXX;
import dbgssystem.dto.AutoDTO_DB_FDBRXX;
import dbgssystem.dto.AutoDTO_DB_SJZBZRXX;
import dbgssystem.dto.AutoDTO_DB_ZCMXXX;
import dbgssystem.dto.AutoDTO_DB_ZQRJZHTXX;
import dbgssystem.dto.condition.DBGSDownload_Condition;
import framework.services.interfaces.MessageResult;
import framework.services.interfaces.MessageResult.ErrorField;

@SuppressWarnings("serial")
public class RemoteTestCase implements java.io.Serializable  {
	public static void main(String[] args) throws Exception{
		//SaveOrUpdate();
		 //Delete();
		Download();
		//Upload();
		/*AutoDTO_DB_DBXX_JC jc = new AutoDTO_DB_DBXX_JC();
		jc.setAutoID(UUID.randomUUID().toString().replace("-", ""));
		System.out.println("基础段=AutoDTO_DB_DBXX_JC=AutoID=" + jc.getAutoID());
		jc.setBDBRLX("1");
		jc.setDBJGDM("10000000001");//担保机构代码
		jc.setBDBRMC("张三");
		jc.setDBYWBH("123");//担保业务编号
		jc.setDBHTHM("HT001");//担保合同号码
		jc.setBDBRZJLX("A");//被担保人类型
		jc.setBDBRZJHM("513624XXXXXXXXXXXX");//被担保人证件号码
		jc.setSJBGRQ("2016-09-12");//数据报告日期
		jc.setLastUpdateDate("2016-09-13");//最后修改时间
		InstInfo ii = new InstInfo();
		ii.setStrInstCode("0000");
		jc.setInstInfo(ii);
		jc.setDtDate("2016-09-11");
		String fieldName="RPTCheckType";
		Object value="2";
		Object object=jc;
		String methodName = "set" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
		Method method = null;
		method = ReflectOperation.getReflectMethod(object.getClass(), methodName, value.getClass());
		method.invoke(object,value);
		
		Object a=object.getClass();
		Object b=value.getClass();*/
	}
	
	public static void Upload(){
		try {
			IRemoteService service = (IRemoteService)Naming.lookup("rmi://192.168.1.245:6600/RemoteService");
			
			Object obj= service.upload("C:\\Users\\张秋\\Desktop\\担保业务\\反馈报文\\1100000000000001160920151000310.txt","jiuxiba","WBWdLO1UQYla7CD+z8YSVw==");
			if(obj instanceof MessageResult){
				print((MessageResult)obj);
			}else{
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void Download(){
		try {
			//IRemoteService service = (IRemoteService)Naming.lookup("rmi://localhost:6600/RemoteService");
			IRemoteService service = (IRemoteService)Naming.lookup("rmi://222.222.12.179:6600/RemoteService");
			DBGSDownload_Condition  condition= new DBGSDownload_Condition();
			condition.setSJBGRQ("20160928");
			System.out.println(condition.getSJBGRQ());
			condition.setStrJRJGCode("00000000000001");
			//condition.setStrJRJGCode("12345678903");
			condition.setStrReportType("15");
			
			Object obj= service.download(condition,"WBWdLO1UQYla7CD+z8YSVw==");
			dbgssystem.actions.imps.DownloadResult dr  = (dbgssystem.actions.imps.DownloadResult)obj;
			String outFilePath = "d:\\";  
	        //生成文件
	        getFile(dr.getBytes(),outFilePath,StringUtils.substringAfterLast(dr.getContentDisposition(), "="));
			System.out.println("22222222222222jjjjjjjjjjjjjj");
			/*if(obj instanceof MessageResult){
				System.out.println("我失败了");
				print((MessageResult)obj);
			}else{
				System.out.println("我成功了");
				@SuppressWarnings("unused")
				dbgssystem.actions.imps.DownloadResult dr  = (dbgssystem.actions.imps.DownloadResult)obj;
				System.out.println("xxxxxx");
			}*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 public static void getFile(byte[] bfile, String filePath,String fileName) {  
	        BufferedOutputStream bos = null;  
	        FileOutputStream fos = null;  
	        File file = null;  
	        try {  
	            File dir = new File(filePath);  
	            if(!dir.exists()&&dir.isDirectory()){//判断文件目录是否存在  
	                dir.mkdirs();  
	            }  
	            file = new File(filePath+"/"+fileName);  
	            fos = new FileOutputStream(file);  
	            bos = new BufferedOutputStream(fos);  
	            bos.write(bfile);  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            if (bos != null) {  
	                try {  
	                    bos.close();  
	                } catch (IOException e1) {  
	                   e1.printStackTrace();  
	                }  
	            }  
	            if (fos != null) {  
	                try {  
	                    fos.close();  
	                } catch (IOException e1) {  
	                    e1.printStackTrace();  
	                }  
	            }  
	        }  
	    } 
	public static void Delete(){
		try {
			IRemoteService service = (IRemoteService)Naming.lookup("rmi://192.168.1.245:6600/RemoteService");
			
			AutoDTO_DB_DBXX_JC jc= new AutoDTO_DB_DBXX_JC();
			jc.setAutoID("1");
			MessageResult messageResult = service.delete(jc, "WBWdLO1UQYla7CD+z8YSVw==");
			print(messageResult);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void SaveOrUpdate(){
		try {
					
			/*
		    LocateRegistry.createRegistry(6600);
		  	RemoteServiceImpl impl = new RemoteServiceImpl();
			Naming.rebind("rmi://192.168.1.245:6600/RemoteService", impl);*/
			
			//IRemoteService service = (IRemoteService)Naming.lookup("rmi://222.222.12.179:6600/RemoteService");
			
			
			//IRemoteService service = (IRemoteService)Naming.lookup("rmi://192.168.1.245:6600/RemoteService");
			
			IRemoteService service = (IRemoteService)Naming.lookup("rmi://localhost:6600/RemoteService");
			
			
			/*AutoDTO_DB_DBXX_JC jcc= new AutoDTO_DB_DBXX_JC();
			jcc.setAutoID("1");
			jcc.setBDBRLX("1");
			jcc.setDBJGDM("10000000001");
			jcc.setBDBRMC("张三");
			jcc.setDBYWBH("123");
			jcc.setDBHTHM("HT001");
			jcc.setBDBRZJLX("A");
			jcc.setBDBRZJHM("513624XXXXXXXXXXXX");
			jcc.setSJBGRQ("2016-09-12");
			jcc.setLastUpdateDate("2016-09-13");
			InstInfo ii = new InstInfo();
			ii.setStrInstCode("0000");
			jcc.setInstInfo(ii);
			jcc.setDtDate("2016-09-11");*/
			
			List<Object> reportList = new ArrayList<Object>();
			

			//基础段
			AutoDTO_DB_DBXX_JC jc= new AutoDTO_DB_DBXX_JC();
			jc = generateDB_DBXX_JC();
			//reportList.add(jc);
			
			//债权人及主合同信息段
			AutoDTO_DB_ZQRJZHTXX autoDTO_DB_ZQRJZHTXX = new AutoDTO_DB_ZQRJZHTXX();
			autoDTO_DB_ZQRJZHTXX = generateDB_ZQRJZHTXX(jc);
			reportList.add(autoDTO_DB_ZQRJZHTXX);
			
			
			//担保合同信息段
			AutoDTO_DB_DBHTXX autoDTO_DB_DBHTXX = new AutoDTO_DB_DBHTXX();
			autoDTO_DB_DBHTXX = generateDB_DB_DBHTXX(jc);
			reportList.add(autoDTO_DB_DBHTXX);
			
			//反担保人信息段
			AutoDTO_DB_FDBRXX autoDTO_DB_FDBRXX = new AutoDTO_DB_FDBRXX();
			autoDTO_DB_FDBRXX = generate_DB_FDBRXX(jc);
			reportList.add(autoDTO_DB_FDBRXX);
			
			
			
			//保费缴纳明细信息段
			AutoDTO_DB_BFJNMXXX autoDTO_DB_BFJNMXXX = new AutoDTO_DB_BFJNMXXX();
			autoDTO_DB_BFJNMXXX = generate_DB_BFJNMXXX(jc);
			reportList.add(autoDTO_DB_BFJNMXXX);
			
			
			//实际在保责任段
			AutoDTO_DB_SJZBZRXX autoDTO_DB_SJZBZRXX = new AutoDTO_DB_SJZBZRXX();
			autoDTO_DB_SJZBZRXX = generate_DB_SJZBZRXX(jc);
			reportList.add(autoDTO_DB_SJZBZRXX);
			
			MessageResult messageResult1 = service.saveOrUpdate(reportList, "WBWdLO1UQYla7CD+z8YSVw==");	
			print(messageResult1);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	// 基础段
	private static AutoDTO_DB_DBXX_JC generateDB_DBXX_JC() {
		AutoDTO_DB_DBXX_JC jc = new AutoDTO_DB_DBXX_JC();
		jc.setAutoID("1e0da415-5794-4129-93e2-f2cff912bdf6");
		System.out.println("基础段=AutoDTO_DB_DBXX_JC=AutoID=" + jc.getAutoID());
		/*jc.setDBJGDM("00000000000001");// 担保机构代码
		jc.setDBYWBH(UUID.randomUUID().toString());// 担保业务编号
		jc.setDBHTHM("HT00000001");// 担保合同号码
		jc.setBDBRLX("1");// 被担保人类型
		jc.setBDBRMC("张三");// 被担保人名称
		jc.setBDBRZJLX("c");// 被担保人证件类型
		jc.setBDBRZJHM("513624XXXXXXXXXXXX");// 被担保人证件号码
		jc.setSJBGRQ("20160912");// 数据报告日期
		//jc.setLastUpdateDate("2016-09-12");// 最后修改时间
		InstInfo ii = new InstInfo();// 机构信息
		ii.setStrInstCode("0000");// TODO 机构代码---需求或者正邦给出一个固定值
		jc.setInstInfo(ii);
		jc.setDtDate("1900-01-01");// TODO 数据时间---固定时间1900-01-01
*/		return jc;
	}

	// 担保合同信息段
	private static AutoDTO_DB_DBHTXX generateDB_DB_DBHTXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_DBHTXX autoDTO_DB_DBHTXX = new AutoDTO_DB_DBHTXX();
		autoDTO_DB_DBHTXX.setAutoID(UUID.randomUUID().toString());
		autoDTO_DB_DBHTXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("担保合同信息段=AutoDTO_DB_DBHTXX=AutoID=" + autoDTO_DB_DBHTXX.getAutoID());
		autoDTO_DB_DBHTXX.setDBYWZL("01");// 担保业务种类
		autoDTO_DB_DBHTXX.setDBFS("1");// 担保方式
		autoDTO_DB_DBHTXX.setDBJE("20000");// 担保金额
		autoDTO_DB_DBHTXX.setDBQSRQ("20160101");// 担保起始日期
		autoDTO_DB_DBHTXX.setDBDQRQ("20161010");// 担保到期日期
		autoDTO_DB_DBHTXX.setCCBZJBL("0");// 存出保证金比例
		autoDTO_DB_DBHTXX.setFDBFS("0");// 反担保方式
		autoDTO_DB_DBHTXX.setFL("1.5%");// 费率
		autoDTO_DB_DBHTXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_DBHTXX;

	}

	// 债权人及主合同信息段
	private static AutoDTO_DB_ZQRJZHTXX generateDB_ZQRJZHTXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_ZQRJZHTXX autoDTO_DB_ZQRJZHTXX = new AutoDTO_DB_ZQRJZHTXX();
		autoDTO_DB_ZQRJZHTXX.setAutoID(UUID.randomUUID().toString());
		autoDTO_DB_ZQRJZHTXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("债权人及主合同信息段=AutoDTO_DB_ZQRJZHTXX=AutoID=" + autoDTO_DB_ZQRJZHTXX.getAutoID());
		autoDTO_DB_ZQRJZHTXX.setZQRLX("2");// 债权人类型---1,2
		autoDTO_DB_ZQRJZHTXX.setZQRMC("廊坊银行股份有限公司");// 债权人名称
		autoDTO_DB_ZQRJZHTXX.setZQRZJLX("c");// 债权人证件类型
		autoDTO_DB_ZQRJZHTXX.setZQRZJHM("1234567890");// 债权人证件号码
		autoDTO_DB_ZQRJZHTXX.setZHTHM("1234567");// 主合同编号
		autoDTO_DB_ZQRJZHTXX.setZTW("1");// 状态位
		autoDTO_DB_ZQRJZHTXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_ZQRJZHTXX;

	}

	// 反担保人信息段
	private static AutoDTO_DB_FDBRXX generate_DB_FDBRXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_FDBRXX autoDTO_DB_FDBRXX = new AutoDTO_DB_FDBRXX();
		autoDTO_DB_FDBRXX.setAutoID(UUID.randomUUID().toString());
		autoDTO_DB_FDBRXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("反担保人信息段=AutoDTO_DB_FDBRXX=AutoID=" + autoDTO_DB_FDBRXX.getAutoID());
		autoDTO_DB_FDBRXX.setFDBRLX("1");// 反担保人类型
		autoDTO_DB_FDBRXX.setFDBRMC("崔艳艳");// 反担保人名称
		autoDTO_DB_FDBRXX.setFDBRZJLX("c");// 反担保人证件类型---0,1
		autoDTO_DB_FDBRXX.setFDBRZJHM("12345678");// 反担保人证件号码
		autoDTO_DB_FDBRXX.setFDBZRJE("200000");// 反担保责任金额
		autoDTO_DB_FDBRXX.setZTW("1");// 状态位
		autoDTO_DB_FDBRXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_FDBRXX;
	}

	// 实际在保责任段
	private static AutoDTO_DB_SJZBZRXX generate_DB_SJZBZRXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_SJZBZRXX autoDTO_DB_SJZBZRXX = new AutoDTO_DB_SJZBZRXX();
		autoDTO_DB_SJZBZRXX.setAutoID(UUID.randomUUID()+"20160922" + 1);
		autoDTO_DB_SJZBZRXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("实际在保责任段=AutoDTO_DB_SJZBZRXX=AutoID=" + autoDTO_DB_SJZBZRXX.getAutoID());
		autoDTO_DB_SJZBZRXX.setDBHTZT("1");// 担保合同状态
		autoDTO_DB_SJZBZRXX.setDBZRJCRQ("");// 担保责任解除日期
		autoDTO_DB_SJZBZRXX.setZBYE("10000");// 在保余额
		autoDTO_DB_SJZBZRXX.setYEBHRQ("20160606");// 余额变化日期
		autoDTO_DB_SJZBZRXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_SJZBZRXX;
	}

	// 保费缴纳明细信息段
	private static AutoDTO_DB_BFJNMXXX generate_DB_BFJNMXXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_BFJNMXXX autoDTO_DB_BFJNMXXX = new AutoDTO_DB_BFJNMXXX();
		autoDTO_DB_BFJNMXXX.setAutoID(UUID.randomUUID()+"20160922");
		autoDTO_DB_BFJNMXXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("保费缴纳明细信息段=AutoDTO_DB_BFJNMXXX=AutoID=" + autoDTO_DB_BFJNMXXX.getAutoID());
		autoDTO_DB_BFJNMXXX.setYJRQ("20140417");// 应缴日期
		autoDTO_DB_BFJNMXXX.setYJJE("20000");// 应缴金额
		autoDTO_DB_BFJNMXXX.setSJRQ("20140417");// 实缴日期
		autoDTO_DB_BFJNMXXX.setQJJE("0");// 欠缴金额
		autoDTO_DB_BFJNMXXX.setBQBFJNZT("00");// 本期保费缴纳状态
		autoDTO_DB_BFJNMXXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_BFJNMXXX;
	}


	// 代偿概况信息段
	private static AutoDTO_DB_DCGKXX generate_DB_DCGKXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_DCGKXX autoDTO_DB_DCGKXX = new AutoDTO_DB_DCGKXX();
		autoDTO_DB_DCGKXX.setAutoID(UUID.randomUUID()+"20160922" );
		autoDTO_DB_DCGKXX.setFOREIGNID(jc);// 主外键关联基础段
		System.out.println("代偿概况信息段=AutoDTO_DB_DCGKXX=AutoID=" + autoDTO_DB_DCGKXX.getAutoID());
		autoDTO_DB_DCGKXX.setJZRQ("20160909");// 记账日期
		autoDTO_DB_DCGKXX.setJXZCBZ("1");// 继续追偿标志
		autoDTO_DB_DCGKXX.setZJYCZCRQ("20160909");// 最近一次代偿日期
		autoDTO_DB_DCGKXX.setLJDCJE("20000");// 累计代偿金额
		autoDTO_DB_DCGKXX.setBJGCDDCJE("20000");// 本机构承担代偿金额
		autoDTO_DB_DCGKXX.setLJZCJE("20160909");// 最近一次追偿日期
		autoDTO_DB_DCGKXX.setDCYE("20000");// 代偿余额
		autoDTO_DB_DCGKXX.setBJGCDDCYE("2000");// 本机构承担代偿余额
		autoDTO_DB_DCGKXX.setLJZCJE("20000");// 累计追偿金额
		autoDTO_DB_DCGKXX.setLJSSJE("20000");// 累计损失金额
		autoDTO_DB_DCGKXX.setExtend2("20160922");//业务发生时间
		return autoDTO_DB_DCGKXX;
	}

	// 代偿明细信息段
	private static AutoDTO_DB_DCMXXX generate_DB_DCMXXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_DCMXXX autoDTO_DB_DCMXXX = new AutoDTO_DB_DCMXXX();
		autoDTO_DB_DCMXXX.setAutoID(UUID.randomUUID().toString()+"20160922" + 1);
		autoDTO_DB_DCMXXX.setFOREIGNID(jc);// 主外键关联基础段
		autoDTO_DB_DCMXXX.setDCRQ("20160909");// 代偿日期
		autoDTO_DB_DCMXXX.setDCJE("20000");// 代偿金额
		autoDTO_DB_DCMXXX.setExtend2("20160922");//业务发生时间
		System.out.println("代偿明细信息段=AutoDTO_DB_DCMXXX=AutoID=" + autoDTO_DB_DCMXXX.getAutoID());

		return autoDTO_DB_DCMXXX;
	}

	// 追偿明细信息段
	private static AutoDTO_DB_ZCMXXX generate_DB_ZCMXXX( AutoDTO_DB_DBXX_JC jc) {
		AutoDTO_DB_ZCMXXX autoDTO_DB_ZCMXXX = new AutoDTO_DB_ZCMXXX();
		autoDTO_DB_ZCMXXX.setAutoID(UUID.randomUUID().toString()+"20160922" + 1);
		autoDTO_DB_ZCMXXX.setFOREIGNID(jc);// 主外键关联基础段
		autoDTO_DB_ZCMXXX.setZCRQ("20160909");// 追偿日期
		autoDTO_DB_ZCMXXX.setZCJE("2000");// 追偿金额
		autoDTO_DB_ZCMXXX.setExtend2("20160922");//业务发生时间
		System.out.println("追偿明细信息段=AutoDTO_DB_ZCMXXX=AutoID=" + autoDTO_DB_ZCMXXX.getAutoID());

		return autoDTO_DB_ZCMXXX;
	}


	
	public static void print(MessageResult messageResult){
		System.out.println(messageResult.isSuccess()+"   "+messageResult.getMessage());
		
		if(!messageResult.isSuccess()){
			if(messageResult.getMessageList().size()>0){
				System.out.println("MessageList:");
				for(String msg:messageResult.getMessageList()){
					System.out.println("\t"+msg);
				}
			}
			if(messageResult.getErrorFieldList().size()>0){
				System.out.println("ErrorFieldList:");
				for(ErrorField msg:messageResult.getErrorFieldList()){
					System.out.println("\t"+msg.getFieldName()+" "+msg.getMessage());
				}
			}
			if(messageResult.getMessageSet().size()>0){
				System.out.println("getMessageSet:");
				for(String msg:messageResult.getMessageSet()){
					System.out.println("\t"+msg);
				}
			}
		}
	}
}
