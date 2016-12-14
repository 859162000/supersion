package extend.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

//import report.dto.ReportInstInfo;
//import report.dto.ReportInstSubInfo;

//import autoETLsystem.dto.AutoETL_WorkflowParamMV;

/*import coresystem.dto.InstInfo;
import coresystem.dto.UserInfo;

import extend.dto.SystemCodeSet;
import extend.dto.SystemCodeValue;
import extend.dto.SystemParam;*/
import extend.dto.SystemCodeSet;
import extend.dto.SystemCodeValue;
import extend.dto.SystemParam;
import framework.helper.FrameworkFactory;
import framework.helper.TypeParse;
import framework.interfaces.IParamObjectResultExecute;
import framework.security.SecurityContext;
import freemarker.template.utility.StringUtil;

public class HelpTool {
	@SuppressWarnings("unchecked")
	public static Map<String, String> getSystemConstSet() throws Exception {
		// 创建DAO
		Map<String, String> map = new HashMap<String, String>();
		String beanID = "singleObjectFindAllDao";
		IParamObjectResultExecute dao =
		(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
		// 调用DAO查询到tObject对象(表)
		Object tObject = dao.paramObjectResultExecute(new Object[]{"extend.dto.SystemCodeSet", "sessionFactory"});
		if (tObject != null) { // 找不到对象,设置SQL为空
			ArrayList<SystemCodeSet> codes = (ArrayList<SystemCodeSet>)tObject;
			for(SystemCodeSet code : codes)
				map.put(code.getStrCodeID(), code.getStrSetName());
			
			
			
		}
		
		return map;
		
	}
	
	public static Map<String, String> getSystemConstVal(String id) throws Exception {
		Map<String, String> map = new LinkedHashMap<String, String>();
		// 创建DAO
		String beanID = "singleObjectFindByIdDao";
		IParamObjectResultExecute dao =
		(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
		// 调用DAO查询到tObject对象(表)
		Object tObject = dao.paramObjectResultExecute(new Object[]{"extend.dto.SystemCodeSet", id, "sessionFactory"});
		if (tObject != null) { // 找不到对象,设置SQL为空
			SystemCodeSet codeSet = (SystemCodeSet)tObject;
			
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.SystemCodeValue"));
			detachedCriteria.add(Restrictions.eq("codeSet", codeSet));
			detachedCriteria.addOrder(Order.asc("strOrder"));
			detachedCriteria.addOrder(Order.asc("strCode"));

			// 查询 singleObjectFindByCriteriaDao
			dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
	    	List<SystemCodeValue> objectListSub = (List<SystemCodeValue>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,"sessionFactory"});
			
			
			for(SystemCodeValue code : objectListSub) {
				map.put(code.getStrCode(), code.getStrValue());
			}
		}
		
		
		

		return map;
	}
	
	// 将整个文件读取到String中
	public static String readToString(File file, String encoding) {
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}
	
	public static void copyFile(File sourcefile, File targetFile) throws IOException{
        // 新建文件输入流并对它进行缓冲
        FileInputStream input = new FileInputStream(sourcefile);
        BufferedInputStream inbuff = new BufferedInputStream(input);
        
        // 新建文件输出流并对它进行缓冲
        FileOutputStream out = new FileOutputStream(targetFile);
        BufferedOutputStream outbuff = new BufferedOutputStream(out);
        
        // 缓冲数组
        byte[] b = new byte[1024*5];
        int len = 0;
        while((len=inbuff.read(b)) != -1){
            outbuff.write(b, 0, len);
        }
        
        // 刷新此缓冲的输出流
        outbuff.flush();
        
        // 关闭流
        inbuff.close();
        outbuff.close();
        out.close();
        input.close();
    }
	
	public static SystemParam getSystemParam(String strParamName) throws Exception{
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute) FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		SystemParam systemParam = (SystemParam)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{"extend.dto.SystemParam", strParamName , null});
		return systemParam;
	}
 
	/**
	 * 得到系统时间前一天
	 * @param format
	 * @return
	 */
	public static String getBeforeDate(String format){
		Date dNow = new Date();   //当前时间
		Date dBefore = new Date();
		Calendar calendar = Calendar.getInstance(); //得到日历
		calendar.setTime(dNow);//把当前时间赋给日历
		calendar.add(Calendar.DAY_OF_MONTH, -1);  //设置为前一天
		dBefore = calendar.getTime();   //得到前一天的时间
		SimpleDateFormat sdf=new SimpleDateFormat(format); //设置时间格式
		String defaultStartDate = sdf.format(dBefore);    //格式化前一天
		return defaultStartDate;
	}
	
	/**
	 * 获取系统当前日期
	 * @return yyyyMMdd
	 */
	public static String getSystemCurrentDate() {
		Date date=new Date();
		String systemCurrentDate = TypeParse.parseString(date, "yyyyMMdd");
		return systemCurrentDate;
	}
	
	/**
	 * 获取系统当前年份
	 * @return yyyy
	 */
	public static String getSystemCurrentYear() {
		Date date=new Date();
		String systemCurrentYear =TypeParse.parseString(date, "yyyyMMdd").substring(0, 4);
		return systemCurrentYear;
	}
	
	/**
	 * 获取系统当前月份
	 * @return yyyyMM
	 */
	public static String getSystemCurrentMonth() {
		Date date=new Date();
		String systemCurrentMonth = TypeParse.parseString(date, "yyyyMMdd").substring(4, 6);
		return systemCurrentMonth;
	}
	
	
	/*
	 * 去掉List<String> 中的重复元素
	 */
	public static List<String> removeDuplicate(List<String> list){  
        HashSet<String> hashSet = new HashSet<String>(list);  
        list.clear();  
        list.addAll(hashSet);  
        return list;  
    }
	 
     /* 通过递归调用删除一个文件夹及下面的所有文件 
     * @param file 
     */  
    public static void deleteFile(File file){  
    	try {
	        if(file.isFile()){//表示该文件不是文件夹  
	            file.delete();  
	        }else{  
	            //首先得到当前的路径  
	            String[] childFilePaths = file.list();  
	            for(String childFilePath : childFilePaths){  
	                File childFile=new File(file.getAbsolutePath()+"\\"+childFilePath);  
	                deleteFile(childFile);  
	            }  
	            file.delete();  
	        } 
		} catch (Exception e) {
			e.printStackTrace();
		} 
    } 
    
    
    public static void copyFolderWithSelf(String oldPath, String newPath) {  
        try {  
            new File(newPath).mkdirs(); //如果文件夹不存在 则建立新文件夹  
            File dir = new File(oldPath);  
            // 目标  
            newPath +=  File.separator + dir.getName();  
            File moveDir = new File(newPath);  
            if(dir.isDirectory()){  
                if (!moveDir.exists()) {  
                    moveDir.mkdirs();  
                }  
            }  
            String[] file = dir.list();  
            File temp = null;  
            for (int i = 0; i < file.length; i++) {  
                if (oldPath.endsWith(File.separator)) {  
                    temp = new File(oldPath + file[i]);  
                } else {  
                    temp = new File(oldPath + File.separator + file[i]);  
                }  
                if (temp.isFile()) {  
                    FileInputStream input = new FileInputStream(temp);  
                    FileOutputStream output = new FileOutputStream(newPath +  
                            "/" +  
                            (temp.getName()).toString());  
                    byte[] b = new byte[1024 * 5];  
                    int len;  
                    while ((len = input.read(b)) != -1) {  
                        output.write(b, 0, len);  
                    }  
                    output.flush();  
                    output.close();  
                    input.close();  
                }  
                if (temp.isDirectory()) { //如果是子文件夹  
                    copyFolderWithSelf(oldPath + "/" + file[i], newPath);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }
    
    
    /**
     * 当离线查询中in的个数超过1000时 ，拆分成几个不同的in分句中
     * @author 此方法从网上复制而来，引用自CSDN的网名为luzhiyonghello的人
     * @param source              满足条件的集合
     * @param dc                  DetachedCriteria对象
     * @param inOrNot             in 或者 not
     * @param field               字段名称
     * @param size                拆分每个in中放多少个
     * @return DetachedCriteria
     * 
     * Remark:此方法用于解决Hibernate的查询DetachedCriteria，Restrictions.in中的参数大于1000时
     * 		oracle报错（“ORA-01795: 列表中的最大表达式数为 1000”的异常），怎样动态嵌套or in语句
     */
    @SuppressWarnings("unchecked")
    public static DetachedCriteria splitSourceInDc(List source , DetachedCriteria dc ,String  inOrNot , String field , int size){
	    if(source!=null&&source.size()>0){
	    	if(source.size()>size) {
			    if(("in").equals(inOrNot)){
				    int k = source.size()/size + (source.size()%size >0 ? 1 : 0);
				    Criterion criterion = null;
				        for(int i=0 ;i<k ;i++){
				        	if(i==0){
					        	List sublist = source.subList(0, size>source.size()?source.size():size);
					        	criterion = Restrictions.in(field, sublist) ;
				        	}
				        	else{
					        	List sublist = source.subList(i*size, (i+1)*size>source.size()?source.size():(i+1)*size);
					        	criterion =Restrictions.or(criterion, Restrictions.in(field, sublist));
				        	}
				        }
				        dc.add(criterion);
			    }
			    else if("not".equals(inOrNot)){
			    	int pageTotal = source.size()/size + (source.size()%size >0 ? 1 : 0);
			        for(int i=0 ;i<pageTotal;i++){
			        	int  start = i*size ;
			        	int  end   = (i+1)*size > source.size()?source.size():(i+1)*size;
			        	List sublist = source.subList(start, end);	
			        	dc.add(Restrictions.not(Restrictions.in(field, sublist)));
			        }	
			    }
		    }
	    	else{
			    if(("in").equals(inOrNot)){
			    	dc.add(Restrictions.in(field, source));
			    }
			    else if("not".equals(inOrNot)){
			    	dc.add(Restrictions.not(Restrictions.in(field, source)));
			    }
		    }
	    }
	    return  dc;
    }


}
