package framework.dao.imps;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;


import framework.helper.FileHelper;
import framework.interfaces.ApplicationManager;

/**
 * 项目名称：framework<br>
 * *********************************<br>
 * <P>类名称：DBUtils</P>
 * *********************************<br>
 * <P>类描述：初始化数据---表、视图、存储过程创建；数据插入</P>
 * 创建人：王川<br>
 * 创建时间：2016-5-31 上午10:40:21<br>
 * 修改人：王川<br>
 * 修改时间：2016-5-31 上午10:40:21<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
public class DBUtils {
	private final static Logger logger =ApplicationManager.getActionExcuteLog();// LogFactory.getLog(DBUtils.class);
	private static final String TABLE_TYPE="table";
	private static final String VIEWS_TYPE="view";
	private static final String DATA_TYPE="data";
	private static final String PROC_TYPE="procedure";
	private static final String pathSep= File.separator;
	
	public static String getDBType(Connection connection) throws SQLException
	{
		String dbType="";
		
		try {
			if(connection.getMetaData().getDriverName().equals("Oracle JDBC driver")) {
				dbType="oracle";
			}
			else if(connection.getMetaData().getDriverName().startsWith("Microsoft SQL Server")){
				dbType="sqlserver";
			}
			else if(connection.getMetaData().getDriverName().startsWith("MySQL-AB JDBC Driver")){
				dbType="mysql";
			}
			else{
				dbType="db2";
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(),e);
			throw e;
			
		}
		return dbType;
	}
	public static void initDB(Connection connection,String scriptDir) throws Exception 
	{
		
		try
		{
			if(!isInit(connection))
			{
				
				boolean isAutoCommit=connection.getAutoCommit();
				
				try
				{
					connection.setAutoCommit(false);
					String dbType=getDBType(connection);
					String[] paths=scriptDir.split(",");
					for(String path:paths)
					{
						String dbTypeScriptDir=path.concat(pathSep).concat(dbType);
						ISqlCommand command=BaseSqlCommand.getSqlCommand(dbType);
						
						initTable(connection,command,dbTypeScriptDir);
						
						initViews(connection,command,dbTypeScriptDir);
						
						initData(connection,command,dbTypeScriptDir);
						
						initProcedure(connection,command,dbTypeScriptDir);
					}
					
					
					setInitFlag(connection);
					//connection.commit();
				}
				
				finally
				{
					connection.setAutoCommit(isAutoCommit);
				}
				
			}
		}
		catch(Exception ex)
		{
			logger.error(ex);
			throw ex;
		}
		
		
		
	}
	private static void setInitFlag(Connection connection) throws SQLException
	{
		try
		{
			Statement s=connection.createStatement();
			s.execute("create table dbinit(flag varchar(30))");
		}
		catch(SQLException ex)
		{
			logger.error("创建数据库初始化标志表失败，请手动创建...");
			throw ex;
		}
		
	}
	private static boolean isInit(Connection connection)
	{
		try
		{
			Statement s=connection.createStatement();
			s.execute("select 1 from dbinit where 1=0");
		}
		catch(SQLException ex)
		{
			return false;
		}
		return true;
		
	}
	
	private static boolean initTable(Connection connection,ISqlCommand command,String scriptDir) throws Exception
	{
		
		String scriptType=TABLE_TYPE;
		executeScript(connection,command, scriptDir, scriptType);
		return true;
	}
	/**
	 * @param connection
	 * @param scriptDir
	 * @param scriptType
	 * @throws Exception
	 * @throws SQLException
	 */
	private static void executeScript(Connection connection,ISqlCommand command, String scriptDir,
			String scriptType) throws Exception{
		try
		{
			String scriptdir=scriptDir.concat(pathSep).concat(scriptType).concat(pathSep);
			URL url=Thread.currentThread().getContextClassLoader().getResource(scriptdir);
			if(url!=null)
			{   
				 
				
				String path=url.getFile();
				String decodePath=URLDecoder.decode(path,"GBK");
				File parentFile=new File(decodePath);
				Map<String,String> mapFiles=getAllFiles(parentFile);
				logger.info(String.format("====================create %s start======================",scriptType));
				Statement statement = connection.createStatement();
				for(Entry<String,String> entry:mapFiles.entrySet())
				{
					logger.info(entry.getKey()+" start...");
					String[] commands=command.getCommands(scriptType,entry.getValue());
					for(String strcommand:commands)
					{
						logger.info(strcommand);
						if(strcommand.length()>0)
						{
							statement.execute(strcommand);
						}
						
					}
					
					
					logger.info(entry.getKey()+" end...");
					
				}
				connection.commit();
				logger.info(String.format("====================create %s end======================",scriptType));
				
			}
	
		}
		catch(Exception ex)
		{
			connection.rollback();
			throw ex;
		}
		
		
		
	}
	
	private static Map<String,String> getAllFiles(File parentFile) throws Exception
	{
		File[] chidrenFile = parentFile.listFiles();
		Map<String,String> fileMap=new HashMap<String,String>();
		for(File f:chidrenFile)
		{
			if(f.isDirectory())
			{
				fileMap.putAll(getAllFiles(f));
			}
			else if(f.isFile())
				
				
			{
				String content=FileHelper.readTxtFile(f.getAbsolutePath(),"GBK");
				if(content.length()>0)
				{
					fileMap.put(f.getAbsolutePath(), content);
				}
			    
			}
		}
		return fileMap;
	}
	private static boolean initViews(Connection connection,ISqlCommand command,String scriptDir) throws Exception
	{
		String scriptType=VIEWS_TYPE;
		executeScript(connection,command, scriptDir, scriptType);
		return true;
	}
	private static boolean initData(Connection connection,ISqlCommand command,String scriptDir) throws Exception
	{
		String scriptType=DATA_TYPE;
		executeScript(connection,command, scriptDir, scriptType);
		return true;
	}
	private static boolean initProcedure(Connection connection,ISqlCommand command,String scriptDir) throws Exception
	{
		String scriptType=PROC_TYPE;
		executeScript(connection,command, scriptDir, scriptType);
		return true;
	}


}
