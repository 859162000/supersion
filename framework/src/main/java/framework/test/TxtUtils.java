package framework.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;

public class TxtUtils {
	
	/******************************DTO相关操作start******************************/
	
	/**
	 * 将当前测试的基础段写入文本文件（.txt）中
	 * @param jcDtoList
	 */
	public static void setCurrentTestJCDTOListToTextFile(List<String> jcDtoList){
		OutputStream os = null;
		try {
			if(jcDtoList != null){
				File path = new File(System.getProperty("java.io.tmpdir")+"allTestJCDTOListFile.txt");
				if(path.exists()){
					path.delete();
				}
				path.createNewFile();
				os = new FileOutputStream(path);
				String text = null;
				if(jcDtoList.size() > 0) {
					for(String content : jcDtoList){
						text = null;
						text = content+System.getProperty("line.separator");
						os.write(text.getBytes());
					}
				} else {
					os.write(null);
				}
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(os != null){
					os.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 从文件中获取当前测试的基础段信息
	 */
	@SuppressWarnings("finally")
	public static List<String> getCurrentTestJCDTOListFromTextFile(){
		FileReader reader = null;
		BufferedReader br = null;
		List<String> jcDtoList = null;
		try {
			String path = System.getProperty("java.io.tmpdir")+"allTestJCDTOListFile.txt";
			reader = new FileReader(path);
			br = new BufferedReader(reader);
			String line = null;
			jcDtoList = new ArrayList<String>();
			while(!StringUtils.isBlank(line = br.readLine())){
				jcDtoList.add(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
			} catch (Exception e) {
				e.printStackTrace();
			}
			return jcDtoList;
		}
	}
	
	/******************************DTO相关操作end******************************/
	
}
