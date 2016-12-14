package framework.services.check;

import java.lang.reflect.Field;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.IParamObjectResultExecute;
import framework.interfaces.RequestManager;
import framework.services.interfaces.ICheck;
import framework.services.interfaces.LogicParamManager;
import framework.services.interfaces.MessageResult;
import framework.show.ShowInstance;

// 删除时检查是否有其它表的关联数据存在
// 在应用的service bean中配置dependTableList属性来选择需检查的表
public class DeleteDependentDataCheck implements ICheck {

	public MessageResult Check() throws Exception {
		MessageResult messageResult = new MessageResult();
		Object[] idList = (Object[]) RequestManager.getIdList();
		if(idList != null) { // deleteByIdList,一一查对
			int i = 0;
			for( Object obj: idList) {
				i++;
				RequestManager.setId(obj);
				messageResult = checkOneTObject(i); // 检查每个被删对象
				if(!messageResult.isSuccess()) // 只要有一条失败则终止
					return messageResult;
			}
		}
		else // deleteById
			return checkOneTObject(null);
		
		return messageResult;
	}

	@SuppressWarnings("unchecked")
	private MessageResult checkOneTObject(Integer row) throws Exception,
			ClassNotFoundException {
		MessageResult messageResult = new MessageResult();
		
		// 根据service配置的defaultLogicDaoBeanId属性创建dao
		String beanID = LogicParamManager.getDefaultLogicDaoBeanId();
		IParamObjectResultExecute SingleObjectFindByIdDao =
		(IParamObjectResultExecute) FrameworkFactory.CreateBean(beanID);
		// 调用dao查询到tObject对象
		Object tObject = SingleObjectFindByIdDao.paramObjectResultExecute(null);
		if (tObject == null) {
			messageResult.setSuccess(false);
			messageResult.getMessageSet().add("执行删除出错！");

			return messageResult;
		}

		// 取配置的关联表
		
		if(LogicParamManager.getDependTableList() != null){
			Object[] dependTableList = (Object[]) LogicParamManager.getDependTableList();

			for(int i =0;i<dependTableList.length;i++){
				Class<?> type = Class.forName(dependTableList[i].toString());

				for(Field field : ReflectOperation.getReflectFields(type)){
					if(field.getType().equals(Class.forName(RequestManager.getTName()))){

						DetachedCriteria detachedCriteria = DetachedCriteria.forClass(type);
						detachedCriteria.add(Restrictions.eq(field.getName(), tObject));
						
						IParamObjectResultExecute defaultLogicDaoDao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
				    	List<Object> objectList = (List<Object>)defaultLogicDaoDao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				    	if(objectList.size() > 0){
				    		ShowInstance showInstance = ReflectOperation.getDefaultShowInstance(type.getName());
				    		
				    		messageResult.setSuccess(false);
				    		if(row == null){
				    			messageResult.getMessageSet().add(showInstance.getShowEntityName() + "中有关联数据，不能删除");
				    		}
				    		else{
				    			messageResult.getMessageSet().add("第" + row + "条数据：" + showInstance.getShowEntityName() + "中有关联数据，不能删除");
				    		}
							
							break;
				    	}
					}
				}
			}
		}
		
		return messageResult;
	}
}
