package framework.services.imps;

import java.util.List;
import java.util.Map;

import framework.services.interfaces.ShowParamManager;

// 用于获取树的配置项：是否将显示项目作为叶子节点，树节点图标，根节点名
public class SingleObjectShowTreeService extends BaseObjectDaoResultService{

	private List<String> imgList;
	
	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	private String rootName;
	
	public void setRootName(String rootName) {
		this.rootName = rootName;
	}
	
	private String rootUrl;
	
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	private String constructType;
	
	public void setConstructType(String constructType) {
		this.constructType = constructType;
	}

	private Map<String,String> foreignFieldMap;
	
	public void setForeignFieldMap(Map<String,String> foreignFieldMap) {
		this.foreignFieldMap = foreignFieldMap;
	}
	
	private Map<String,String> treeServiceMap;
	
	public void setTreeServiceMap(Map<String,String> treeServiceMap) {
		this.treeServiceMap = treeServiceMap;
	}
	
	@Override
	public void init() throws Exception {
		super.init();
		ShowParamManager.setShowTreeRootName(this.rootName);
		ShowParamManager.setShowTreeRootUrl(this.rootUrl);
		ShowParamManager.setConstructType(constructType);
		ShowParamManager.setShowTreeImageUrlList(imgList);
		ShowParamManager.setForeignFieldMap(foreignFieldMap);
		ShowParamManager.setTreeServiceMap(treeServiceMap);
	}
	
	@Override
	public void initSuccessResult() throws Exception {
		if(this.treeServiceMap == null){
			super.initSuccessResult();
		}
	}
}
