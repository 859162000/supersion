package framework.helper;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.*;
import javax.crypto.spec.*;


import org.apache.commons.lang.xwork.StringUtils;
import org.w3c.dom.Element;

import com.sun.org.apache.xml.internal.security.utils.Base64;

@SuppressWarnings("restriction")
public class AESSecretKey {
	
	private static String defaultKey = "~!ocbcwdw21e312e";
	
	private static String getKeyFromFile(String key){
		if(StringUtils.isBlank(key)){
			try{

				Element AESSecretPathElement = DomXMLOperation.getElementByName("extend/configs/AESSecretKey.xml","AESSecretPath");
				Element[] AESSecretPathValueElements = DomXMLOperation.getElementsByName(AESSecretPathElement,"value");
				List<String> pathList = new ArrayList<String>();
				
				for(int i=0;i<AESSecretPathValueElements.length;i++){
					pathList.add(DomXMLOperation.getElementValue(AESSecretPathValueElements[i]));
				}
				
				String keyFile = "";
				for(int i = 0; i<pathList.size();i++){
					FileReader fr = new FileReader(pathList.get(i));  
					int ch = 0;    
					while((ch = fr.read())!=-1 ){    
						keyFile += (char)ch;    
					} 
				}

				key = keyFile;
				
			}
			catch(Exception ex){
				//ExceptionLog.CreateLog(ex);
			}

			if(StringUtils.isBlank(key)){
				key = defaultKey;
			}
		}

		return key;
	}
	
	public static String EncryString(String content, String key) {
		try {
			
			key = getKeyFromFile(key);
			
			byte[] raw = key.getBytes();  
	        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"  
	        byte[] _key1 = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte)0xAB,(byte) 0xCD, (byte)0xEF, 0x12, 0x34, 0x56, 0x78, (byte)0x90, (byte)0xAB, (byte)0xCD,(byte) 0xEF };
	        IvParameterSpec iv = new IvParameterSpec(_key1);//使用CBC模式，需要一个向量iv，可增加加密算法的强度  
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
	        byte[] encrypted = cipher.doFinal(content.getBytes());  
			return Base64.encode(encrypted);
		} 
		catch (Exception ex){
		}
		return null;

	}
	public static String DecryString(String sSrc, String key) {  
        try {  
        	
        	key = getKeyFromFile(key);
        	
            byte[] raw = key.getBytes();  
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); 
            byte[] _key1 = { 0x12, 0x34, 0x56, 0x78, (byte) 0x90, (byte)0xAB,(byte) 0xCD, (byte)0xEF, 0x12, 0x34, 0x56, 0x78, (byte)0x90, (byte)0xAB, (byte)0xCD,(byte) 0xEF };
            IvParameterSpec iv = new IvParameterSpec(_key1);  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = Base64.decode(sSrc);//先用bAES64解密  
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original);  
                return originalString;  
            } catch (Exception e) {  
                return null;  
            }  
        } 
        catch (Exception ex) {  
            return null;  
        }  
    }  
}
