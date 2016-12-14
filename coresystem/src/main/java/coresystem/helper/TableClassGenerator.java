package coresystem.helper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import javax.tools.JavaFileManager.Location;



import org.apache.commons.lang.xwork.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean;


import extend.dto.AutoETL_DataSource;
import extend.dto.ReportModel_Field;
import extend.dto.ReportModel_FieldCondition;
import framework.helper.BeanFactoryUtils;
import framework.helper.FastClasspathScanner;
import framework.helper.FrameworkFactory;
import framework.helper.ReflectOperation;
import framework.interfaces.ApplicationManager;
import framework.interfaces.IParamObjectResultExecute;
import framework.show.ShowContext;

public class TableClassGenerator {
	private static String strPackage = "coresystem.dto";
	private static String strDTOPath = "coresystem/dto/";

	// 递归生成目录
	public static void mkDir(File file) {
		if (file.getParentFile().exists()) {
			file.mkdir();
		} else {
			mkDir(file.getParentFile());
			file.mkdir();
		}
	}

	// 生成实体类到WEB-INF\classes下，类名为AutoDTO_+表名,编译生成在同目录下
	@SuppressWarnings("unchecked")
	public static String generateDTO(String strTableName, String strTableChineseName,ArrayList<ReportModel_Field> map,
			AutoETL_DataSource dataSource, boolean isOveride, String strTablePrefix) {
		//if(appRootPath == null || appRootPath.equals(""))
		//	appRootPath = ServletActionContext.getServletContext().getRealPath("/");
		
		if(strTablePrefix == null){
			strTablePrefix = "";
		}
		
		String appRootPath="";
		if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
			appRootPath = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
		}
		else{
			appRootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath().substring(0).replace("%20", " ");
		}
		appRootPath = appRootPath.substring(0, appRootPath.length()-16)+"/";
		String strClassPath="";
		if(ShowContext.getInstance().getShowEntityMap().get("currentResourcePath") != null && ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath") != null){
			strClassPath = ShowContext.getInstance().getShowEntityMap().get("currentResourcePath").get("resourcePath");
		}
		else{
			URL url=Thread.currentThread().getContextClassLoader().getResource("");
			strClassPath= URLDecoder.decode(url.getPath());
			
		}
		File appFile=new File(appRootPath);
		appRootPath=appFile.getAbsolutePath();
		File classFile=new File(strClassPath);
		strClassPath=classFile.getAbsolutePath();
		
		String strClassName = "AutoDTO_" + strTableName;
		String strPath = strClassPath + strDTOPath; // dto目录
		
		String sessionFactory;
		if(dataSource == null || dataSource.getSessionFactory() == null || dataSource.getSessionFactory().equals(""))
			sessionFactory = "sessionFactory";
		else
			sessionFactory = dataSource.getSessionFactory();
		
		strPackage = getEntityPackageName(strTableName,sessionFactory);
		String pathSep=strClassPath.endsWith("/")?"":"/";
		strPath =strClassPath+pathSep+strPackage.replace('.', '/')+"/" ;
		if(strPath == null || strPath.equals(""))
			return "";
		
		mkDir(new File(strPath));
		strPackage = getEntityPackageName(strTableName,sessionFactory);
		
		if(map.size() <= 0) return ""; // 没有字段的表不生成类

		try{
			File testFile = new File(strPath + strClassName + ".java");
			if(isOveride) { // 覆盖原文件
				new File(strPath + strClassName + ".java").delete();
			}
			else if(testFile.exists()) { // 不覆盖则已有文件时，不再生成
				return "";
			}
			ReflectOperation.getDirtyDTOTableMap().put(strClassName, "");// 存入本次应用运行AutoDTO修改记录表
			
			FileWriter writer = new FileWriter(strPath + strClassName + ".java");
			writer.write("package " + strPackage + ";\r\n\r\n");
			writer.write("import java.util.Map;\r\n");
			writer.write("import java.util.Date;\r\n");
			writer.write("import java.sql.Timestamp;\r\n");
			writer.write("import java.math.BigDecimal;\r\n");
			writer.write("import java.math.BigInteger;\r\n");
			writer.write("import java.lang.Double;\r\n");
			writer.write("import java.lang.Float;\r\n");
			writer.write("import java.sql.Time;\r\n");
			writer.write("import framework.interfaces.IColumn;\r\n");
			writer.write("import framework.show.ShowContext;\r\n");
			writer.write("import framework.helper.TypeParse;\r\n");
			writer.write("import extend.helper.HelpTool;\r\n");
			writer.write("import javax.persistence.Column;\r\n");
			writer.write("import javax.persistence.Entity;\r\n");
			writer.write("import javax.persistence.GeneratedValue;\r\n");
			writer.write("import javax.persistence.GenerationType;\r\n");
			writer.write("import javax.persistence.Id;\r\n");
			writer.write("import javax.persistence.Lob;\r\n");
			writer.write("import javax.persistence.Basic;\r\n");
			writer.write("import javax.persistence.Table;\r\n");
			writer.write("import javax.persistence.Temporal;\r\n");
			writer.write("import javax.persistence.CascadeType;\r\n");
			writer.write("import javax.persistence.JoinColumn;\r\n");
			writer.write("import javax.persistence.ManyToOne;\r\n");
			writer.write("import javax.persistence.OneToMany;\r\n");
			writer.write("import javax.persistence.FetchType;\r\n");
			writer.write("import org.hibernate.annotations.GenericGenerator;\r\n");
			writer.write("import javax.persistence.EmbeddedId;\r\n");
			writer.write("import javax.persistence.TemporalType;\r\n\r\n");
			writer.write("import java.util.HashSet;\r\n");
			writer.write("import framework.interfaces.IEntity;\r\n");
			writer.write("import java.util.Set;\r\n\r\n");

			int nKeyCount = 0;
			Iterator iter = map.iterator();

			// 检查所有的外键，加入相应外键类的import语句
			while(iter.hasNext()){ // 每个字段 
				ReportModel_Field field = (ReportModel_Field)iter.next();
				String constList = field.getStrConstList();
				if(constList != null && constList.contains(".")) {
					constList = constList.substring(0, constList.indexOf('-'));
					writer.write("import " + constList + ";\r\n");
				}
				
				if(field.getIsKey().equals("1")) nKeyCount++;
			}
			
			String idLeft = "";//"["; // 标识符前后标志
			String idRight = "";//"]";
			
			writer.write("\r\n@Entity\r\n");
			writer.write("@Table(name = \"" + idLeft + strTablePrefix + strTableName + idRight + "\")\r\n");
			if(!StringUtils.isBlank(strTableChineseName)){
				writer.write("@IEntity(description= \"" + strTableChineseName + "\")\r\n");
			}
			writer.write("public class AutoDTO_" + strTableName + " implements java.io.Serializable{\r\n\r\n");
			
			writer.write("	private static final long serialVersionUID = 1L;\r\n\r\n");

			// 多个主键时，为联合主键,创建联合主键字段
			if(nKeyCount > 1) {
				writer.write("	" + strTableName + "UionPKID uionPKID = new " + strTableName + "UionPKID();\r\n");
				
				writer.write("\r\n	@EmbeddedId \r\n");
				writer.write("	public " + strTableName + "UionPKID getUionPKID() { \r\n");
				writer.write("		return uionPKID; \r\n");
				writer.write("	} \r\n\r\n"); 

				writer.write("	public void setUionPKID(" + strTableName + "UionPKID uionPKID) { \r\n");
				writer.write("		this.uionPKID = uionPKID; \r\n");
				writer.write("	 } \r\n\r\n");
			}
			
			// 每个字段生成
			iter = map.iterator();
			while(iter.hasNext()){ // 每个字段
				ReportModel_Field field = (ReportModel_Field)iter.next();
				String strFieldType = ShowContext.getInstance().getShowEntityMap().get("fieldTypeJava").get(field.getStrFieldType());
				String constList = field.getStrConstList();
				String dbConstList = field.getStrDBConstList();
				String strOriType = strFieldType;
				boolean isStringType = strFieldType.equals("String");
				if(!isStringType)
					strFieldType = "String";
				
				if(nKeyCount > 1 && field.getIsKey().equals("1")) // 多个主键时，为联合主键
					continue;
				
				// 自动主键，按uuid生成
				if(field.getStrFieldName().equalsIgnoreCase("AUTOID"))
				{
					writer.write("	@Id\r\n");
					writer.write("	@Column(name = \"" + idLeft + "auto" + "ID\"" + idRight + ", length = 32)\r\n");
					writer.write("	@GeneratedValue(generator = \"system-uuid\")\r\n");
					writer.write("	@GenericGenerator(name = \"system-uuid\", strategy = \"framework.interfaces.AssignedGUIDGenerator\")\r\n");
					writer.write("	private String auto" + "ID;\r\n\r\n");
					
					// get
					writer.write("	public String getAuto" + "ID() {\r\n");
					writer.write("		return auto" + "ID;\r\n");
					writer.write("	}\r\n\r\n");
					
					// set
					writer.write("	public void setAuto"  + "ID(String in) {\r\n");
					writer.write("		auto" + "ID = in;\r\n");
					writer.write("	}\r\n\r\n");
					
					continue;
				}
				
				// clob字段生成
				String DBType = ShowContext.getInstance().getShowEntityMap().get("fieldType").get(field.getStrFieldType());
				if(DBType.toUpperCase().equals("CLOB"))
				{
					String strDBCLOB = "CLOB"; // oracle
					if(dataSource.getStrDatabaseType().equals("2")
							|| dataSource.getStrDatabaseType().equals("3")){ // sql
						strDBCLOB = "CLOB"; 
					}
					else if(dataSource.getStrDatabaseType().equals("4")){
						strDBCLOB = "BLOB";
					}
					else{
						strDBCLOB = "text";
					}	
					writer.write("	@Lob\r\n");
					writer.write("	@Basic(fetch = FetchType.EAGER)\r\n");
					writer.write("	@Column(name=\"" + idLeft + field.getStrFieldName() + idRight + "\", columnDefinition=\"" + strDBCLOB + "\",nullable=true)" +"\r\n");
					writer.write("	private String " + field.getStrFieldName() + ";\r\n\r\n");
					
					String FuncName = field.getStrFieldName().substring(0,1).toUpperCase()
						+ field.getStrFieldName().substring(1);
					
					// get
					writer.write("	public String get" + FuncName + "() {\r\n");
					writer.write("		return " + field.getStrFieldName() + ";\r\n");
					writer.write("	}\r\n\r\n");
					
					// set
					writer.write("	public void set"  + FuncName + "(String in) {\r\n");
					writer.write("		" + field.getStrFieldName() + " = in;\r\n");
					writer.write("	}\r\n\r\n");
					
					continue;
				}
				
				// 外键表时，要修正类为外键类DTO
				if(constList != null && constList.contains(".")) {
					String[] constListArray=constList.split("-");
					if(constListArray.length==2){
						strFieldType = constList.substring(0, constList.indexOf('-'));
						
					}else if(constListArray.length==3){
						strFieldType= "Set<" + constList.substring(0, constList.indexOf('-')) + ">";
						
					}
					strOriType = strFieldType;
				}
				
				// 注解
				writer.write(getNotation(field, nKeyCount==1, idLeft, idRight));

				// 时间字段加@Temporal(TemporalType.DATE)
				if(strOriType.equals("Date"))
					writer.write("	@Temporal(TemporalType.DATE)\r\n");
				
				// declare
				if(strOriType.startsWith("Set")){
					String setType = strOriType.replace("Set<", "").replace(">", "");
					writer.write("	private " + strOriType + " " + field.getStrFieldName() + "= new HashSet<"+setType+">(0);\r\n\r\n");
				}else
				{
					writer.write("	private " + strOriType + " " + field.getStrFieldName() + ";\r\n\r\n");
				}

				//CONST tag method
				if(constList != null && !constList.equals(ApplicationManager.getEmptySelectValue()) 
						&& !constList.contains(".")) {// 常量表，需要增加常量取值函数
					writer.write("	public static Map<String,String> get" + field.getStrFieldName() + "Tag() {\r\n");
					writer.write("		return ShowContext.getInstance().getShowEntityMap().get(\"" + constList + "\");\r\n");
					writer.write("	}\r\n\r\n");
				}
				
				//DB CONST tag method
				if(dbConstList != null && !dbConstList.equals(ApplicationManager.getEmptySelectValue()) 
						) {// 数据库常量表，需要增加常量取值函数
					writer.write("	public static Map<String,String> get" + field.getStrFieldName() + "Tag() {\r\n");
					writer.write("		try {\r\n");
					writer.write("			return HelpTool.getSystemConstVal(\"" + dbConstList + "\");\r\n");
					writer.write("		} catch (Exception e) {\r\n");
					writer.write("			e.printStackTrace();\r\n");
					writer.write("			return null;\r\n");
					writer.write("		}\r\n");
					writer.write("	}\r\n\r\n");
				}
				
				// get
				writer.write("	public " + strOriType + " get"
						+ field.getStrFieldName().substring(0,1).toUpperCase()
						+ field.getStrFieldName().substring(1)
						+ "() {\r\n");
				writer.write("		return " + field.getStrFieldName() + ";\r\n");
				writer.write("	}\r\n\r\n");
				
				// set
				writer.write("	public void set"
						+ field.getStrFieldName().substring(0,1).toUpperCase()
						+ field.getStrFieldName().substring(1)
						+ "(" + strFieldType + " in) {\r\n");
				if(isStringType)
					writer.write("		" + field.getStrFieldName() + " = in;\r\n");
				else if(strOriType.equals("Integer"))
					writer.write("		" + field.getStrFieldName() + " = TypeParse.parseInt(in);\r\n");
				else if(strOriType.equals("Double"))
					writer.write("		" + field.getStrFieldName() + " = TypeParse.parseDouble(in);\r\n");
				else if(strOriType.equals("Date"))
					writer.write("		" + field.getStrFieldName() + " = TypeParse.parseDate(in);\r\n");
				else if(strOriType.equals("BigDecimal"))
					writer.write("		" + field.getStrFieldName() + " = TypeParse.parseBigDecimal(in);\r\n");
				else if(strOriType.equals("Timestamp"))
					writer.write("		" + field.getStrFieldName() + " = TypeParse.parseTimestamp(in);\r\n");
				else
					writer.write("		" + field.getStrFieldName() + " = in;\r\n");
				writer.write("	}\r\n\r\n");
			}
			
			writer.write("}\r\n\r\n"); // 主类结束
			
			// 多个主键时，为联合主键,创建联合主键类
			String strEquals = "";
			if(nKeyCount > 1) {
				writer.write("class " + strTableName + "UionPKID implements java.io.Serializable{\r\n\r\n");
				
				// 加入每个关键字段
				iter = map.iterator();
				while(iter.hasNext()){ // 每个字段
					ReportModel_Field field = (ReportModel_Field)iter.next();
					String strFieldType = ShowContext.getInstance().getShowEntityMap().get("fieldTypeJava").get(field.getStrFieldType());
					
					if(!field.getIsKey().equals("1")) continue; // 跳过非关键字段
					
					if(field.getNLength() == null)
						writer.write("	@Column(length=20)  ");
					else
						writer.write("	@Column(length=" + field.getNLength() + ")  ");
					
					// declare
					writer.write("	private " + strFieldType + " " + field.getStrFieldName() + ";\r\n\r\n");
					
					// get
					writer.write("	public " + strFieldType + " get"
							+ field.getStrFieldName().substring(0,1).toUpperCase()
							+ field.getStrFieldName().substring(1)
							+ "() {\r\n");
					writer.write("		return " + field.getStrFieldName() + ";\r\n");
					writer.write("	}\r\n\r\n");
					
					// set
					writer.write("	public void set"
							+ field.getStrFieldName().substring(0,1).toUpperCase()
							+ field.getStrFieldName().substring(1)
							+ "(" + strFieldType + " in) {\r\n");
					writer.write("		" + field.getStrFieldName() + " = in;\r\n");
					writer.write("	}\r\n\r\n");
					
					// 生成equals需要的比较条件
					if(!strEquals.equals(""))
						strEquals += " && ";
					
					strEquals += "this." + field.getStrFieldName() + ".equals(pk." + field.getStrFieldName() + ")";
				}
				
				writer.write("	@Override \r\n");
				writer.write("	public boolean equals(Object obj) { \r\n");
				writer.write("	     if(obj instanceof " + strTableName + "UionPKID){ \r\n");
				writer.write("	         " + strTableName + "UionPKID pk=(" + strTableName + "UionPKID)obj; \r\n");
				writer.write("	         if(" + strEquals + ") { \r\n");
				writer.write("	             return true; \r\n");
				writer.write("	         } \r\n");
				writer.write("	     } \r\n");
				writer.write("	     return false; \r\n");
				writer.write("	 } \r\n\r\n");

				writer.write("	 @Override  \r\n");
				writer.write("	  public int hashCode() {  \r\n");
				writer.write("	      return super.hashCode();  \r\n");
				writer.write("	  } \r\n");

			    writer.write("} \r\n\r\n"); // 联合主键类结束
			}
			
			writer.flush();
			writer.close();

			
			FastClasspathScanner scanner=new FastClasspathScanner();
			scanner.parseSystemClasspath();
			ApplicationManager.getActionExceptionLog().info("classpath==="+scanner.getClassPathStr()); 
			
			
			//strLib += splitter + appRootPath + "WEB-INF/lib/hibernate-annotations.jar";
			
			String javaFile=strPath+strClassName + ".java";
			String compileOutInfo=strPath+strClassName + ".txt";
			String libpath=scanner.getClassPathStr();//getClassPath(appRootPath,strClassPath);
			String outPath=strClassPath;
			String[] source = {"-Xstdout",compileOutInfo,"-verbose", "-d",  outPath, "-classpath",libpath ,javaFile};
			StringBuilder cmdBuilder =new StringBuilder();
			for(String str:source)
			{
				cmdBuilder.append(str);
				
			}
			//compile(javaFile,outPath);
			ApplicationManager.getActionExceptionLog().info("javac==="+cmdBuilder.toString());
			
			com.sun.tools.javac.Main.compile(source);

			generateDTOCondition(dataSource, strTableName, map, appRootPath,libpath,strClassPath); // 产生条件类
		}catch(Exception ex) {
			ex.printStackTrace();
			
			String msg = "生成失败：" + ex.getMessage().replace("\r\n", "\\r\\n");
				msg = msg.replace('\'', '|').replace('\"', '|').replace("\n", "\\r\\n");
			return msg;
		}
		
		return "";
	}
	private static String getClassPath(String appRootPath,String curClassPath)
	{
		String splitter = ":";
		if(isWindowsOS()) splitter = ";";
		String strLib = appRootPath +File.separator+ "WEB-INF"+File.separator+"lib";
		File libFile=new File(strLib);
		StringBuilder libBuilder=new StringBuilder();
		if(libFile.isDirectory())
		{
			File[] aryFile=libFile.listFiles();
			for(File f:aryFile)
			{
				libBuilder.append(f.getAbsolutePath()).append(splitter);
			}
		}
		libBuilder.append(curClassPath);
		return libBuilder.toString();
	}
	
	
	
	private static String getNotation(ReportModel_Field field, boolean needID, String idLeft, String idRight){
		String strColumn = "";
		String strIColumn = "";
		String strNotation = "";
		String strFieldLen = "";
		String strID = "";
		
		// 处理常量列表
		if(field.getStrConstList() != null && !field.getStrConstList().equals(ApplicationManager.getEmptySelectValue())) {
			if(field.getStrConstList().contains(".")) { // 外键表
				
				String[] constListArray=field.getStrConstList().split("-");
				if(constListArray.length==2){
					//String strForeign = field.getStrConstList();
					//String strForeignFieldName = strForeign.substring(strForeign.indexOf('-')+1);
					strNotation = "	@ManyToOne(fetch = FetchType.EAGER)\r\n";
					
					strNotation += "	@JoinColumn(name = \"" + field.getStrFieldName() + "\"";
					if(field.getStrEmptyType().equals("2")) { // 不为空
						strNotation += ", nullable = false";
					}
					strNotation += ")\r\n";
					
					strIColumn = "	@IColumn(isNullable = false)\r\n";
					
				}else if(constListArray.length==3){
					strNotation = "	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = \""+constListArray[2]+"\")\r\n";
					
				}

			}
			else { // 一般常量
				strIColumn = "	@IColumn(tagMethodName=\"get" + field.getStrFieldName() + "Tag\""; 
				if(field.getStrChinaName() != null && !field.getStrChinaName().equals(""))
					strIColumn += ",description=\"" + field.getStrChinaName() + "\"";
				if(field.getIsEnable().equals("2")) // 不可用
					strIColumn += ",isSingleTagHidden=true";
				strIColumn += ")\r\n";
				
				/*strColumn = "	@Column(name = \"" + idLeft + field.getStrFieldName() + idRight + "\", nullable =";
				if(field.getStrEmptyType().equals("2"))  // 不为空
					strColumn += "false";
				else
					strColumn += "true";*/
				strColumn = "	@Column(name = \"" + idLeft + field.getStrFieldName() + idRight + "\", nullable =true";
				strColumn += ")\r\n";
				
				strNotation = strIColumn + strColumn;
			}
		}
		else if (field.getStrDBConstList() != null  // 常量表中的常量
				&& !field.getStrDBConstList().equals(ApplicationManager.getEmptySelectValue())) {
			strIColumn = "	@IColumn(tagMethodName=\"get" + field.getStrFieldName() + "Tag\""; 
			if(field.getStrChinaName() != null && !field.getStrChinaName().equals(""))
				strIColumn += ",description=\"" + field.getStrChinaName() + "\"";
			if(field.getIsEnable() !=null && field.getIsEnable().equals("2")) // 不可用
				strIColumn += ",isSingleTagHidden=true";
			strIColumn += ")\r\n";
			
			/*strColumn = "	@Column(name = \"" + idLeft + field.getStrFieldName() + idRight + "\", nullable =";
			if(field.getStrEmptyType().equals("2"))  // 不为空
				strColumn += "false";
			else
				strColumn += "true";*/
			strColumn = "	@Column(name = \"" + idLeft + field.getStrFieldName() + idRight + "\", nullable =true";
			strColumn += ")\r\n";
			
			strNotation = strIColumn + strColumn;
		}
		else { // 一般字段
			if(field.getNLength() != null && field.getNLength() > 0)
				strFieldLen = ", length = " + field.getNLength().toString();
			
			strColumn = "	@Column(name = \"" + idLeft + field.getStrFieldName() + idRight + "\"" + strFieldLen;
			/*if(field.getStrEmptyType().equals("2")) // 为空
				strColumn += ", nullable = false";
			else
				strColumn += ", nullable = true";*/
			strColumn += ", nullable = true";
			strColumn += ")\r\n";
			
			// 处理字段中文名和主键名
			if(field.getStrChinaName() != null && !field.getStrChinaName().equals(""))
				strIColumn = "	@IColumn(description=\"" + field.getStrChinaName() + "\"";
			if(field.getIsKey().equals("1")) { // 主键名
				
				if(needID)
					strID += "	@Id\r\n";// @ID 要标在主键列前
				
				if(strIColumn.equals(""))
					strIColumn = " 	@IColumn(";
				else
					strIColumn += ",";
				
				strIColumn += "isKeyName=true";
			}
			
			if(field.getStrLogicEmptyType() != null && field.getStrLogicEmptyType().equals("2")) // 逻辑不为空
				strIColumn += ", isNullable = false";
			
			if(field.getIsEnable() != null && field.getIsEnable().equals("2")) // 不可用
				strIColumn += ",isSingleTagHidden=true";
			
			if(!strIColumn.equals(""))
				strIColumn += ")\r\n";
			
			strNotation= strID + strColumn + strIColumn;
		}
		
		return strNotation;
	}
	
	@SuppressWarnings("unchecked")
	public static String generateDTOCondition(AutoETL_DataSource dataSource, String strTableName, 
			ArrayList<ReportModel_Field> map, String appRootPath,String libpath,String curClassPath) {
		String strClassPath = curClassPath;
		String strPath = strClassPath + strDTOPath + "condition/"; // 放到dto.condition目录下
		String strClassName = "AutoDTO_" + strTableName + "_Condition";
		
		String sessionFactory;
		if(dataSource == null || dataSource.getSessionFactory() == null || dataSource.getSessionFactory().equals(""))
			sessionFactory = "sessionFactory";
		else
			sessionFactory = dataSource.getSessionFactory();
		strPackage = getEntityPackageName(strTableName,sessionFactory);
		String pathSep=strClassPath.endsWith("/")?"":"/";
		strPath =strClassPath+pathSep+strPackage.replace('.', '/')+"/condition/" ;
		
		if(strPath == null || strPath.equals(""))
			return "";
		
		mkDir(new File(strPath));
		
		if(map.size() <= 0) return ""; // 没有字段的表不生成类

		try{
			new File(strPath + strClassName + ".java").delete();
			FileWriter writer = new FileWriter(strPath + strClassName + ".java");
			writer.write("package " + strPackage + ".condition;\r\n\r\n");
			writer.write("import framework.interfaces.ICondition;\r\n");
			writer.write("import framework.interfaces.ICondition.Comparison;\r\n");
			writer.write("import java.util.Map;\r\n");
			writer.write("import java.util.Date;\r\n");
			writer.write("import java.sql.Timestamp;\r\n");
			writer.write("import java.math.BigDecimal;\r\n");
			writer.write("import framework.helper.TypeParse;\r\n");

			Iterator iter = map.iterator();

			// 检查所有的外键，加入相应外键类的import语句
			while(iter.hasNext()){ // 每个字段 
				ReportModel_Field field = (ReportModel_Field)iter.next();
				String constList = field.getStrConstList();
				if(constList != null && constList.contains(".")) {
					constList = constList.substring(0, constList.indexOf('-'));
					writer.write("import " + constList + ";\r\n");
				}
			}
			
			writer.write("\r\n");
			writer.write("public class AutoDTO_" + strTableName + "_Condition implements java.io.Serializable{\r\n\r\n");
			writer.write("	private static final long serialVersionUID = 1L;\r\n\r\n");
			boolean hasCondition = false;
			
			// 每个字段生成
			iter = map.iterator();
			while(iter.hasNext()){ // 每个字段
				ReportModel_Field field = (ReportModel_Field)iter.next();
				
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Class.forName("extend.dto.ReportModel_FieldCondition"));
				detachedCriteria.add(Restrictions.eq("reportModel_Field", field));
				
				// 查询 singleObjectFindByCriteriaDao
				IParamObjectResultExecute dao = (IParamObjectResultExecute)FrameworkFactory.CreateBean("singleObjectFindByCriteriaDao");
		    	List<ReportModel_FieldCondition> objectListSub = (List<ReportModel_FieldCondition>)dao.paramObjectResultExecute(new Object[]{detachedCriteria,null});
				if(objectListSub.size() <= 0) continue; // 字段没有条件设置则跳过
				
				hasCondition = true;
				int conditionIndex = 0;
				for(ReportModel_FieldCondition condition : objectListSub) { // 每个条件生成一个类属性
					conditionIndex++;
					String strFieldType = ShowContext.getInstance().getShowEntityMap().get("fieldTypeJava").get(field.getStrFieldType());
					String constList = field.getStrConstList();
					
					// 外键表时，要修正类为外键类DTO
					if(constList != null && constList.contains(".")) {
						strFieldType = constList.substring(0, constList.indexOf('-'));
					}
					
					// 注解
					writer.write("	@ICondition(target=\"" + field.getStrFieldName() + "\"");
					if(condition.getTargetField() != null && !condition.getTargetField().equals("")) // 目标字段
						writer.write(", targetField=\"" + condition.getTargetField() + "\"");
					if(condition.getDescription() != null && !condition.getDescription().equals("")) // 描述
						writer.write(", description=\"" + condition.getDescription() + "\"");
					if(condition.getIsVisible() != null && !condition.getIsVisible().equals("")) {// 是否可见
						if(condition.getIsVisible().equals("2")) // No
							writer.write(", isVisible=false");
						else
							writer.write(", isVisible=true");
					}
					if(condition.getIsASC() != null && !condition.getIsASC().equals("")) {// 是否升序
						if(condition.getIsASC().equals("2")) // No
							writer.write(", isASC=false");
						else
							writer.write(", isASC=true");
					}
					if(condition.getConditionOrder() != null) // 排序
						writer.write(", order=" + condition.getConditionOrder());
					if(condition.getComparison() != null && !condition.getComparison().equals("")) { // 比较条件
						writer.write(", comparison=Comparison.");
						if(condition.getComparison().equals("1"))
							writer.write("NONE");
						else if(condition.getComparison().equals("2"))
							writer.write("EQ");
						else if(condition.getComparison().equals("3"))
							writer.write("GT");
						else if(condition.getComparison().equals("4"))
							writer.write("GE");
						else if(condition.getComparison().equals("5"))
							writer.write("LT");
						else if(condition.getComparison().equals("6"))
							writer.write("LE");
						else if(condition.getComparison().equals("7"))
							writer.write("ANYWHERE");
						else if(condition.getComparison().equals("8"))
							writer.write("START");
						else if(condition.getComparison().equals("9"))
							writer.write("END");
						else if(condition.getComparison().equals("10"))
							writer.write("EXACT");
						else
							writer.write("NONE");
					}
					writer.write(")\r\n");
					
					if(strFieldType.equals("Date")){
						strFieldType = "String";
					}
	
					// declare
					writer.write("	private " + strFieldType + " " + field.getStrFieldName() + conditionIndex + ";\r\n\r\n");
	
					// get
					writer.write("	public " + strFieldType + " get"
							+ field.getStrFieldName().substring(0,1).toUpperCase()
							+ field.getStrFieldName().substring(1) + conditionIndex
							+ "() {\r\n");
					writer.write("		return " + field.getStrFieldName() + conditionIndex + ";\r\n");
					writer.write("	}\r\n\r\n");
					
					// set
					writer.write("	public void set"
							+ field.getStrFieldName().substring(0,1).toUpperCase()
							+ field.getStrFieldName().substring(1) + conditionIndex
							+ "(" + "String" + " in) {\r\n");
					

					if(strFieldType.equals("Integer"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseInt(in);\r\n");
					else if(strFieldType.equals("Double"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseDouble(in);\r\n");
					else if(strFieldType.equals("Date"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseDate(in);\r\n");
					else if(strFieldType.equals("BigDecimal"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseBigDecimal(in);\r\n");
					else if(strFieldType.equals("Timestamp"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseTimestamp(in);\r\n");
					else if(strFieldType.equals("Time"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseTime(in);\r\n");
					else if(strFieldType.equals("BigInteger"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseBigInteger(in);\r\n");
					else if(strFieldType.equals("Float"))
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = TypeParse.parseFloat(in);\r\n");
					else
						writer.write("		" + field.getStrFieldName() + conditionIndex + " = in;\r\n");

					writer.write("	}\r\n\r\n");
				}
			}
			
			writer.write("}");
			writer.flush();
			writer.close();

			
			
			

			/*
			JavaCompiler cmp = ToolProvider.getSystemJavaCompiler();
			if(cmp.run(null, null, null, "-d",  strClassPath, "-classpath", strLib + ";" + strClassPath,
					strPath+strClassName + ".java") != 0) { // 编译失败
				
				return "生成失败, 请查看日志";
			}
			*/
			
			// 如果没有任何字段有条件设置，则删除此类
			if(!hasCondition) {
				new File(strPath + strClassName + ".java").delete();
				new File(strPath + strClassName + ".class").delete();
			}
			else
			{
				String javaFile=strPath+strClassName + ".java";
				String compileOutInfo=strPath+strClassName + ".txt";
				String outPath=strClassPath;
				String[] source = {"-Xstdout",compileOutInfo,"-verbose", "-d",  outPath, "-classpath",libpath ,javaFile};
				
				StringBuilder cmdBuilder =new StringBuilder();
				for(String str:source)
				{
					cmdBuilder.append(str);
					
				}
				ApplicationManager.getActionExcuteLog().info("javac==="+cmdBuilder.toString());
				
				com.sun.tools.javac.Main.compile(source);
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
			
			String msg = "生成失败：" + ex.getMessage().replace("\r\n", "\\r\\n");
				msg = msg.replace('\'', '|').replace('\"', '|').replace("\n", "\\r\\n");
			return msg;
		}
		
		return "";
	}
	
	public static boolean isWindowsOS(){
	     boolean isWindowsOS = false;
	     String osName = System.getProperty("os.name");
	     if(osName.toLowerCase().indexOf("windows")>-1){
	       isWindowsOS = true;
	     }
	     return isWindowsOS;
	  }
	public static String getEntityPackageName(String entityName,String sessionFactoryName)
	{
		String packageName=null;
		Map<String, String> constMap = ShowContext.getInstance().getShowEntityMap().get("GetNamespaceByEntityName");
		if(constMap!=null)
		{
			String prefix=entityName;
			int firstLineIndex =entityName.indexOf("_");
			if(firstLineIndex>0)
			{
				prefix=entityName.substring(0,firstLineIndex+1);
			}
			packageName=constMap.get(prefix);
			if(packageName!=null)
			{
				int lastDot=packageName.lastIndexOf(".");
				if(lastDot>0)
				{
					packageName=packageName.substring(0, lastDot);
				}
				
			}
		}
		if(packageName==null)
		{
			BeanDefinition beanDefine=BeanFactoryUtils.getInstance().getBeanDefine(sessionFactoryName);
			 MutablePropertyValues pvs = beanDefine.getPropertyValues();
			 PropertyValue pv = pvs.getPropertyValue("packagesToScan");
			 if(pv!=null)
			 {
				 Object pvValue=pv.getValue();
				 if(pvValue!=null && pvValue instanceof List<?>)
				 {
					 List<Object> listDto=(List<Object>)pvValue;
					 
					 if(listDto.size()>0)
					 {
						 TypedStringValue tsv=(TypedStringValue)listDto.get(0);
						 packageName=tsv.getValue();
					 }
				 } 
			 }
			
			 
			
		}
		
		
		return packageName==null?"":packageName;
		
	}
}
