package zxptsystem.service.imps;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.icfcc.batch.ui.PreConditionCheck;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		test t = new test();
		String path = "D:/grzx";
		String filepath = "D:/grzx/reportFile/S10153123H00022012010461000.txt";
		File file = new File(filepath);
		t.initConfigForQyzx(path);
		PreConditionCheck prc = new PreConditionCheck(path);
		
		String sresult = prc.checkHead(filepath);
		if (sresult != null && sresult.trim().equals("")) {
			try {
				ArrayList list = t.getBytesFromFile(file);
				ArrayList result = prc.checkrecord(list);
				//读取校验结果
				for (int i = 0; i < result.size(); i++) {
					if(result.get(i)!=null&&!(result.get(i).equals(""))){
						String errorCode = result.get(i).toString();
						String regex = "\\w{7}";
						Matcher matcher =Pattern.compile(regex).matcher(errorCode);
						while(matcher.find()){
							System.out.println(matcher.group());
						} 
						System.out.println("第" + (i+3) + "行:" + errorCode);
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("报文头校验失败:" + sresult);
		}

	}

	private void initConfigForQyzx(String path) {
		com.icfcc.batch.Constants.SYSTEM_CONFIG_FILE_NAME = "conf\\batch.xml";
		com.icfcc.batch.Constants.SYSTEM_MESSAGE_DESCRIPTOR_CONFIG_NAME = "conf\\message.xml";
		com.icfcc.batch.Constants.SYSTEM_PREPARE_CONFIG_NAME = "conf\\prepare.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_CONFIG_NAME = "conf\\database.xml";
		com.icfcc.batch.Constants.SYSTEM_DATABASE_PROXY_NAME = "conf\\dispatchProxy.xml";
		com.icfcc.batch.Constants.SYSTEM_PUBLIC_KEY = "conf\\public.key";
		com.icfcc.batch.Constants.SYSTEM_KEYSORE_NAME = "conf\\.keystore";
		com.icfcc.batch.BatchContext.getInstance().setBaseHome("\\");
	}

	public ArrayList getBytesFromFile(File file) throws Exception {
		InputStreamReader read = new InputStreamReader(new FileInputStream(file),"GBK");
        BufferedReader bufferedReader = new BufferedReader(read); 
        String str;
        int line = 0;
        ArrayList list = new ArrayList();
        while ((str = bufferedReader.readLine()) != null) {
        	
            line++;
            if(line==1||line==2){
            	continue;
            }else{
            	list.add(str.getBytes());
            }
        }
        return list;
	}

}
