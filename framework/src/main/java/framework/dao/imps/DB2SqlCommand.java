package framework.dao.imps;



public class DB2SqlCommand extends BaseSqlCommand {
	private String dmlDelimiter=";";
	private String ddlDelimiter="\r\nGO\r\n";
	@Override
	public String[] getCommands(String scriptType, String script) {
		if("data".equals(scriptType))
		{
			return getDMLCommands(script);
		}
		else
		{
			return getDDLCommands(script);
		}
	}
	private String procCommentAndSpace(String s)
	{
		String[] strAry=s.split("\r\n");
		StringBuilder newSb=new StringBuilder();
		int i=0;
		while(i<strAry.length)
		{
			String noSpace=strAry[i].trim();
			if(!(noSpace.startsWith("--")|| noSpace.isEmpty())
					)
			{
				break;
			}
			
		    i++;
		}
		for(;i<strAry.length;i++)
		{
			newSb.append(strAry[i].trim());
			newSb.append(" ");
			
		}
		return newSb.toString().trim();
	}
	private String[] getDMLCommands(String script)
	{
		String[] result=script.trim().split(dmlDelimiter);
		for(int i=0;i<result.length;i++)
		{
			
			result[i]=procCommentAndSpace(result[i]);
		}
		return result;
	}
	private String[] getDDLCommands(String script)
	{
		script=script.trim();
		
		String[] result=script.split(ddlDelimiter);
		int len=result.length;
		
		for(int i=0;i<len;i++)
		{
			result[i]=procCommentAndSpace(result[i]);
		}
		
		return result;
		
	}
}
