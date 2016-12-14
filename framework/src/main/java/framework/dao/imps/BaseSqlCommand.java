package framework.dao.imps;

public class BaseSqlCommand implements ISqlCommand {

	@Override
	public String[] getCommands(String scriptType, String script) {
		return new String[]{script};
	}
	
	public static ISqlCommand getSqlCommand(String dbType)
	{
		if("mysql".equals(dbType))
		{
			return new MysqlSqlCommand();
		}
		else if("db2".equals(dbType))
		{
			return new DB2SqlCommand();
		}
		else
		{
			return new DefaultSqlCommand();
		}
	}

}
