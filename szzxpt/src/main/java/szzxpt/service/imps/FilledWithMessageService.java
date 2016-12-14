package szzxpt.service.imps;

import java.lang.reflect.Field;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionContext;

import zxptsystem.dto.AutoDTO_JZ_JZCYXX_JC;
import zxptsystem.dto.AutoDTO_QY_GJGLRQK;
import zxptsystem.dto.UserModel;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.imps.BaseService;

public class FilledWithMessageService extends BaseService {

	public void initSuccessResult() throws Exception {
		// TODO Auto-generated method stub

		HttpServletRequest request=(HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		//1.获取当前的主要个人的基本信息
		Object valueZJHM=request.getParameter("ZJHM");
		Object valueFieldName=request.getParameter("FIELDNAME");
		String className=request.getParameter("className");
		if(className.endsWith(".action")){
			className=className.substring(0,className.lastIndexOf("."));
		}
		//2.获取到该Name对应的所有信息
		String fieldNameArray="";
		List<Field> fieldlist=ReflectOperation.getColumnFieldList(className);
		for(Field field : fieldlist){
			if(!className.equals(AutoDTO_JZ_JZCYXX_JC.class)){
				if(field.getName().indexOf("XM")>=0 || field.getName().indexOf("GDMC")>=0 
						&& field.getName().indexOf("CZF")>=0 || field.getName().indexOf("RMC")>=0){
					fieldNameArray =fieldNameArray+field.getName()+"|";
				}
				else if(className.equals(AutoDTO_QY_GJGLRQK.class)){
					if(field.getName().indexOf("XB")>=0 || field.getName().indexOf("GGRYCSRQ")>=0){
						fieldNameArray =fieldNameArray+field.getName()+"|";
					}
				}
			}
			else if(className.equals(AutoDTO_JZ_JZCYXX_JC.class) &&field.getName().equals(valueFieldName) && valueFieldName.equals("ZJHM_1")){
				fieldNameArray="ZYGXRXM";
			}
			else if(className.equals(AutoDTO_JZ_JZCYXX_JC.class) &&field.getName().equals(valueFieldName) && valueFieldName.equals("ZJHM_2")){
				fieldNameArray="JZCYXM";
			}
		}
		if(fieldNameArray.endsWith("|")){
			fieldNameArray=fieldNameArray.substring(0,fieldNameArray.length()-1);
		}
		
		IParamObjectResultExecute singleObjectFindByIdDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByIdDao");
		UserModel userModel  = (UserModel)singleObjectFindByIdDao.paramObjectResultExecute(new Object[]{UserModel.class.getName(),valueZJHM,null}); 
		if(userModel !=null){
			userModel.setClassName(className);
			userModel.setFieldNameArray(fieldNameArray);
		}
		//JSONArray jsonArray = JSONArray.fromObject(userModellist);  
		//JSONObject json = JSONObject.fromObject(userModel);
		JSONObject json = JSONObject.fromObject(userModel);//
		String context = json.toString();//将json对象转换为字符串
		//3.返回信息处理
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/json");
		response.setCharacterEncoding("utf-8");
	     //写入到前台
	    response.getWriter().write(context);
		// return new MessageResult();
	}

}
