import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.mysql.jdbc.Field;

import coresystem.helper.TableClassGenerator;
import extend.dto.AutoETL_DataSource;
import extend.dto.ReportModel_Field;
import framework.test.SpringBeanTest;


public class TableClassGeneratorTest extends SpringBeanTest {
	@Test
	@Ignore
	public void testGetPackageName()
	{
		Assert.assertNotSame("",TableClassGenerator.getEntityPackageName("aa","sessionFactory"));
	}
	@Test
	@Ignore
	public void testGetPackageNameInEntityXml()
	{
		Assert.assertNotSame("",TableClassGenerator.getEntityPackageName("GR_aa","sessionFactory"));
	}
	
	@Test
	public void testGenerateDTO()
	{
		ArrayList<ReportModel_Field> map=new ArrayList<ReportModel_Field>();
		ReportModel_Field field=new ReportModel_Field();
		field.setAutoFieldID("00194182ADDB4063B67F84283572B7CC");
		field.setIsKey("2");
		field.setIsEnable("1");
		field.setNLength("50");
		field.setNPrecise("0");
		field.setNSeq("3");
		field.setStrChinaName("贷款卡编码");
		field.setStrFieldName("DKKBM");
		field.setStrEmptyType("1");
		field.setStrFieldType("2");
		field.setStrLogicEmptyType("1");
		map.add(field);
		AutoETL_DataSource dataSource=new AutoETL_DataSource();
		dataSource.setSessionFactory("sessionFactory");
		Assert.assertSame("",TableClassGenerator.generateDTO("GR_aa", "测试", map, dataSource, true, ""));
	}
}
