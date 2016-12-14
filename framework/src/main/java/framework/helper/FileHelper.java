package framework.helper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;

import org.apache.commons.lang.StringUtils;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class FileHelper {
	/**
	 * 读TXT文件内容
	 * 
	 * @param fileName
	 * @return
	 */
	private static final String XML_DECLARE="<?xml version=\"1.0\"?>";
	
	public static String readTxtFile(String fileName,String charset) throws Exception {
		StringBuilder result =new StringBuilder();
		File file=new File(fileName);
		if(file.exists())
		{
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			try {
				FileInputStream  fis=new FileInputStream(file);;
				InputStreamReader is;
				if(!StringUtils.isBlank(charset))
				{
					is=new InputStreamReader(fis,charset);
				}
				else
				{
					is=new InputStreamReader(fis);
				}
				bufferedReader = new BufferedReader(is);
				try {
					String read = bufferedReader.readLine();
					if(read!=null)
					{
						result.append(procUTFBOM(read));
						result.append("\r\n");
					}
					while ((read = bufferedReader.readLine()) != null) {
						result.append(read);
						result.append("\r\n");
						
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw e;
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			} finally {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (fileReader != null) {
					fileReader.close();
				}
			}
		}
		
		return result.toString();
	}
    public static String procUTFBOM(String s)
    {
    	byte[] bytAry = s.getBytes();
    	String newStr=s;
    	
		 if ((bytAry.length>=4)&&(bytAry[0] == (byte) 0x00) && (bytAry[1] == (byte) 0x00)  
                && (bytAry[2] == (byte) 0xFE) && (bytAry[3] == (byte) 0xFF)) {  
            // "UTF-32BE";  
            newStr=new String(bytAry,4,bytAry.length-4);  
        } else if ((bytAry.length>=4)&&(bytAry[0] == (byte) 0xFF) && (bytAry[1] == (byte) 0xFE)  
                && (bytAry[2] == (byte) 0x00) && (bytAry[3] == (byte) 0x00)) {  
            //encoding = "UTF-32LE";  
            newStr=new String(bytAry,4,bytAry.length-4); 
        } else if ((bytAry.length>=3)&&(bytAry[0] == (byte) 0xEF) && (bytAry[1] == (byte) 0xBB)  
                && (bytAry[2] == (byte) 0xBF)) {  
            //encoding = "UTF-8";  
        	 newStr=new String(bytAry,3,bytAry.length-3); 
        } else if ((bytAry.length>=2)&&(bytAry.length>=3)&&(bytAry[0] == (byte) 0xFE) && (bytAry[1] == (byte) 0xFF)) {  
            //encoding = "UTF-16BE";  
        	 newStr=new String(bytAry,2,bytAry.length-2);   
        } else if ((bytAry.length>=2)&&(bytAry[0] == (byte) 0xFF) && (bytAry[1] == (byte) 0xFE)) {  
            //encoding = "UTF-16LE";  
            newStr=new String(bytAry,2,bytAry.length-2);
        }
		return newStr;
    }

	public static String readTxtFile(String fileName) throws Exception {
		return readTxtFile(fileName,"");
	}
	public static File writeTxtFile(String content, String fileName,String encode){
		FileOutputStream o = null;
		File file=null;
		try {
			file=new File(fileName);
			if(file.exists())
			{
				file.delete();
			}
			o = new FileOutputStream(file);
			o.write(content.getBytes(encode));
			o.close();
		} catch (Exception e) {
			ExceptionLog.CreateLog(e);
			e.printStackTrace();
		}
		return file;
	}
	
	public static File writeXmlFile(Object obj,String fileName,String encode)
	{
		
		FileOutputStream out = null;
		File file = null;
		try
		{
			 XStream xstream = new XStream(new DomDriver(encode));
			 file = new File(fileName);
			 if(file.exists()){
					file.delete();
			 }
			 
			 xstream.autodetectAnnotations(true);
			 out = new FileOutputStream(file);
			 out.write(XML_DECLARE.getBytes(encode));
			 out.write(String.format("<!DOCTYPE %s[]>", obj.getClass().getSimpleName().toLowerCase()).getBytes(encode));
			 xstream.toXML(obj, out);
			 out.close();
		}
		catch(Exception ex)
		{
		
			ExceptionLog.CreateLog(ex);
			ex.printStackTrace();
		
		}
		
		/*if(obj != null){
			// 定义换行符
			String lineSeparator = System.getProperty("line.separator");
			String tabSeprartorOne = "\t";
			String tabSeprartorTwo = "\t\t";
			String tabSeprartorThree = "\t\t\t";
			try {
				// 定义XML固有的信息
				String fileStateMent = "<?xml version='1.0'?>"+lineSeparator;
				String docType = "<!DOCTYPE report[]>"+lineSeparator;
				file = new File(fileName);
				if(file.exists()){
					file.delete();
				}
				out = new FileOutputStream(file);
				// 写入固定信息
				out.write(fileStateMent.getBytes(encode));
				out.write(docType.getBytes(encode));
				// 写入根节点的开始标记
				String rootStart = "<report reporttype='"+ReflectOperation.getFieldValue(obj, "reporttype").toString()+
					"' reportlayer='"+ReflectOperation.getFieldValue(obj, "reportlayer").toString()+
					"' xmllns='"+ReflectOperation.getFieldValue(obj, "xmllns").toString()+"'>"+lineSeparator;
				out.write((new String(rootStart)).getBytes(encode));

				// 写入数据节点
//				out.write((new String(new StringBuffer("<common>"))).getBytes(encode));
				ReportCommon common = (ReportCommon) ReflectOperation.getFieldValue(obj, "common");
				if(common != null){
					out.write((new String(new StringBuffer(tabSeprartorOne+"<common>"+lineSeparator))).getBytes(encode));
					// 遍历common中的数据
					Object fitechTitle = ReflectOperation.getFieldValue(common, "fitechTitle");
					Object fitechRange = ReflectOperation.getFieldValue(common, "fitechRange");
					Object fitechUnit = ReflectOperation.getFieldValue(common, "fitechUnit");
					Object fitechSubmitYear = ReflectOperation.getFieldValue(common, "fitechSubmitYear");
					Object fitechSubmitMonth = ReflectOperation.getFieldValue(common, "fitechSubmitMonth");
					Object fitechCurrency = ReflectOperation.getFieldValue(common, "fitechCurrency");
					Object fitechVersion = ReflectOperation.getFieldValue(common, "fitechVersion");
					Object fitechSubtitle = ReflectOperation.getFieldValue(common, "fitechSubtitle");
					ReportSign sign = (ReportSign) ReflectOperation.getFieldValue(common, "sign");
					// 定义common数据节点
					String fitechTitleNode = null;
					String fitechRangeNode = null;
					String fitechUnitNode = null;
					String fitechSubmitYearNode = null;
					String fitechSubmitMonthNode = null;
					String fitechCurrencyNode = null;
					String fitechVersionNode = null;
					String fitechSubtitleNode = null;
//					String signNode = null;
					
					if(fitechTitle != null && !StringUtils.isBlank(fitechTitle.toString())){
						fitechTitleNode = tabSeprartorTwo+"<fitechTitle>"+fitechTitle.toString()+"</fitechTitle>"+lineSeparator;
					}else{
						fitechTitleNode = tabSeprartorTwo+"<fitechTitle />"+lineSeparator;
					}
					
					if(fitechRange != null && !StringUtils.isBlank(fitechRange.toString())){
						fitechRangeNode = tabSeprartorTwo+"<fitechRange>"+fitechRange.toString()+"</fitechRange>"+lineSeparator;
					}else{
						fitechRangeNode = tabSeprartorTwo+"<fitechRange />"+lineSeparator;
					}
					
					if(fitechUnit != null && !StringUtils.isBlank(fitechUnit.toString())){
						fitechUnitNode = tabSeprartorTwo+"<fitechUnit>"+fitechUnit.toString()+"</fitechUnit>"+lineSeparator;
					}else{
						fitechUnitNode = tabSeprartorTwo+"<fitechUnit />"+lineSeparator;
					}
					
					if(fitechSubmitYear != null && !StringUtils.isBlank(fitechSubmitYear.toString())){
						fitechSubmitYearNode = tabSeprartorTwo+"<fitechSubmitYear>"+fitechSubmitYear.toString()+"</fitechSubmitYear>"+lineSeparator;
					}else{
						fitechSubmitYearNode = tabSeprartorTwo+"<fitechSubmitYear />"+lineSeparator;
					}
					
					if(fitechSubmitMonth != null && !StringUtils.isBlank(fitechSubmitMonth.toString())){
						fitechSubmitMonthNode = tabSeprartorTwo+"<fitechSubmitMonth>"+fitechSubmitMonth.toString()+"</fitechSubmitMonth>"+lineSeparator;
					}else{
						fitechSubmitMonthNode = tabSeprartorTwo+"<fitechSubmitMonth />"+lineSeparator;
					}
					
					if(fitechCurrency != null && !StringUtils.isBlank(fitechCurrency.toString())){
						fitechCurrencyNode = tabSeprartorTwo+"<fitechCurrency>"+fitechCurrency.toString()+"</fitechCurrency>"+lineSeparator;
					}else{
						fitechCurrencyNode = tabSeprartorTwo+"<fitechCurrency />"+lineSeparator;
					}
					
					if(fitechVersion != null && !StringUtils.isBlank(fitechVersion.toString())){
						fitechVersionNode = tabSeprartorTwo+"<fitechVersion>"+fitechVersion.toString()+"</fitechVersion>"+lineSeparator;
					}else{
						fitechVersionNode = tabSeprartorTwo+"<fitechVersion />"+lineSeparator;
					}
					
					if(fitechSubtitle != null && !StringUtils.isBlank(fitechSubtitle.toString())){
						fitechSubtitleNode = tabSeprartorTwo+"<fitechSubtitle>"+fitechSubtitle.toString()+"</fitechSubtitle>"+lineSeparator;
					}else{
						fitechSubtitleNode = tabSeprartorTwo+"<fitechSubtitle />"+lineSeparator;
					}
					
					out.write(fitechTitleNode.getBytes(encode));
					out.write(fitechRangeNode.getBytes(encode));
					out.write(fitechUnitNode.getBytes(encode));
					out.write(fitechSubmitYearNode.getBytes(encode));
					out.write(fitechSubmitMonthNode.getBytes(encode));
					out.write(fitechCurrencyNode.getBytes(encode));
					out.write(fitechVersionNode.getBytes(encode));
					out.write(fitechSubtitleNode.getBytes(encode));
					
					if(sign != null){
						out.write((new String(new StringBuffer(tabSeprartorTwo+"<sign>"+lineSeparator))).getBytes(encode));
						// 获取sign节点子节点的数据
						Object fitechFiller = ReflectOperation.getFieldValue(sign, "fitechFiller");
						Object fitechChecker = ReflectOperation.getFieldValue(sign, "fitechChecker");
						Object fitechPrincipal = ReflectOperation.getFieldValue(sign, "fitechPrincipal");
						// 定义sign节点子节点对象
						String fitechFillerNode = null;
						String fitechCheckerNode = null;
						String fitechPrincipalNode = null;
						
						if(fitechFiller != null && !StringUtils.isBlank(fitechFiller.toString())){
							fitechFillerNode = tabSeprartorThree+"<fitechFiller>"+fitechFiller.toString()+"<fitechFiller/>"+lineSeparator;
						}else{
							fitechFillerNode = tabSeprartorThree+"<fitechFiller/>"+lineSeparator;
						}
						
						if(fitechChecker != null && !StringUtils.isBlank(fitechChecker.toString())){
							fitechCheckerNode = tabSeprartorThree+"<fitechChecker>"+fitechChecker.toString()+"<fitechChecker/>"+lineSeparator;
						}else{
							fitechCheckerNode = tabSeprartorThree+"<fitechChecker/>"+lineSeparator;
						}
						
						if(fitechPrincipal != null && !StringUtils.isBlank(fitechPrincipal.toString())){
							fitechPrincipalNode = tabSeprartorThree+"<fitechPrincipal>"+fitechPrincipal.toString()+"<fitechPrincipal/>"+lineSeparator;
						}else{
							fitechPrincipalNode = tabSeprartorThree+"<fitechPrincipal/>"+lineSeparator;
						}
						
						out.write(fitechFillerNode.getBytes(encode));
						out.write(fitechCheckerNode.getBytes(encode));
						out.write(fitechPrincipalNode.getBytes(encode));
						
						out.write((new String(new StringBuffer(tabSeprartorTwo+"</sign>"+lineSeparator))).getBytes(encode));
					}else{
						out.write((new String(new StringBuffer(tabSeprartorTwo+"<sign />"+lineSeparator))).getBytes(encode));
					}
					
					out.write((new String(new StringBuffer(tabSeprartorOne+"</common>"+lineSeparator))).getBytes(encode));
				}else{
					out.write((new String(new StringBuffer(tabSeprartorOne+"<common />"+lineSeparator))).getBytes(encode));
				}
				
				//写入datas节点数据
				List<ReportData> datas = (List<ReportData>) ReflectOperation.getFieldValue(obj, "datas");
				if(datas.size() > 0){
					out.write((new String(new StringBuffer(tabSeprartorOne+"<datas>"+lineSeparator))).getBytes(encode));
					// 写入data节点及其数据
					for(ReportData data :datas){
						out.write((new String(new StringBuffer(tabSeprartorTwo+"<data>"+lineSeparator))).getBytes(encode));
						Object cellRow = ReflectOperation.getFieldValue(data, "cellRow");
						Object cellCol = ReflectOperation.getFieldValue(data, "cellCol");
						Object cellValue = ReflectOperation.getFieldValue(data, "cellValue");
						Object cellType = ReflectOperation.getFieldValue(data, "cellType");
						// 定义data数据节点
						String cellRowNode = null;
						String cellColNode = null;
						String cellValueNode = null;
						String cellTypeNode = null;
						
						if(cellRow != null && !StringUtils.isBlank(cellRow.toString())){
							cellRowNode = tabSeprartorThree+"<cellRow>"+cellRow.toString()+"</cellRow>"+lineSeparator;
						}else{
							cellRowNode = tabSeprartorThree+"<cellRow />"+lineSeparator;
						}
						
						if(cellCol != null && !StringUtils.isBlank(cellCol.toString())){
							cellColNode = tabSeprartorThree+"<cellCol>"+cellCol.toString()+"</cellCol>"+lineSeparator;
						}else{
							cellColNode = tabSeprartorThree+"<cellCol />"+lineSeparator;
						}
						
						if(cellValue != null && !StringUtils.isBlank(cellValue.toString())){
							cellValueNode = tabSeprartorThree+"<cellValue>"+cellValue.toString()+"</cellValue>"+lineSeparator;
						}else{
							cellValueNode = tabSeprartorThree+"<cellValue />"+lineSeparator;
						}
						
						if(cellType != null && !StringUtils.isBlank(cellType.toString())){
							cellTypeNode = tabSeprartorThree+"<cellType>"+cellType.toString()+"</cellType>"+lineSeparator;
						}else{
							cellTypeNode = tabSeprartorThree+"<cellType />"+lineSeparator;
						}
						
						out.write(cellRowNode.getBytes(encode));
						out.write(cellColNode.getBytes(encode));
						out.write(cellValueNode.getBytes(encode));
						out.write(cellTypeNode.getBytes(encode));

						out.write((new String(new StringBuffer(tabSeprartorTwo+"<data>"+lineSeparator))).getBytes(encode));
					}
					
					
					out.write((new String(new StringBuffer(tabSeprartorOne+"<datas>"+lineSeparator))).getBytes(encode));
				}
				
				// 写入根节点的结束标记
				out.write((new String(new StringBuffer("</report>"+lineSeparator))).getBytes(encode));
				out.close();
			} catch (Exception e) {
				ExceptionLog.CreateLog(e);
				e.printStackTrace();
				return null;
			}
			
		}// 参数对象obj不为空
		*/
		return file;
	}

}

