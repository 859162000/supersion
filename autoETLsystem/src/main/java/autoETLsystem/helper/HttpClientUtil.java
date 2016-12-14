package autoETLsystem.helper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


/**
 * 项目名称：autoETLsystem<br>
 * *********************************<br>
 * <P>类名称：HttpClientUtil</P>
 * *********************************<br>
 * <P>类描述：HTPP请求帮助类</P>
 * 创建人：王川<br>
 * 创建时间：2016-5-12 上午10:35:20<br>
 * 修改人：王川<br>
 * 修改时间：2016-5-12 上午10:35:20<br>
 * 修改备注：<br>
 * @version 1.0<br>    
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class HttpClientUtil {
	
	/**
	 * <p>方法描述: post请求 form表单发送</p>
	 * <p>方法备注: </p>
	 * @param url 请求地址，必须写完整【+http/https头】
	 * @param map 参数
	 * @param charset 编码
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-5-12 上午10:29:46</p>
	 *
	 */
	public static String doPost(String url,Map<String,String> map,String charset) throws Exception{
		boolean isHttps = false;
		if(url.toLowerCase().startsWith("https"))
			isHttps = true;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = isHttps? new SSLClient() :new DefaultHttpClient();
			httpPost = new HttpPost(url);
			//设置参数
			if(map != null){
				List<NameValuePair> list = new ArrayList<NameValuePair>();
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String,String> elem = (Entry<String, String>) iterator.next();
					list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
				}
				if(list.size() > 0){
					UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
					httpPost.setEntity(entity);
				}
			}
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			throw new Exception("短信发送失败："+ex);
		}
		return result;
	}
	
	/**
	 * <p>方法描述: post/json请求</p>
	 * <p>方法备注: </p>
	 * @param url
	 * @param jsonData
	 * @param charset
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-5-12 上午10:31:47</p>
	 *
	 */
	public static String doPostJson(String url,String jsonData,String charset) throws Exception{
		boolean isHttps = false;
		if(url.toLowerCase().startsWith("https"))
			isHttps = true;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = isHttps? new SSLClient() :new DefaultHttpClient();
			httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(jsonData,charset);
			entity.setContentEncoding("UTF-8");    
            entity.setContentType("application/json");    
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			throw new Exception("短信发送失败："+ex);
		}
		return result;
	}
	
	
	/**
	 * <p>方法描述:post/text请求 </p>
	 * <p>方法备注: </p>
	 * @param url
	 * @param postData
	 * @param charset
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-5-12 上午10:32:16</p>
	 *
	 */
	public static String doPostText(String url,String postData,String charset) throws Exception{
		boolean isHttps = false;
		if(url.toLowerCase().startsWith("https"))
			isHttps = true;
		HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try{
			httpClient = isHttps? new SSLClient() :new DefaultHttpClient();
			httpPost = new HttpPost(url);
			StringEntity entity = new StringEntity(postData,charset);
			entity.setContentEncoding("UTF-8");    
            entity.setContentType("application/x-www-form-urlencoded");    
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			throw new Exception("短信发送失败："+ex);
		}
		return result;
	}
	
	/**
	 * <p>方法描述:get请求 </p>
	 * <p>方法备注: </p>
	 * @param url
	 * @param map
	 * @param charset
	 * @return
	 * <p>创建人：王川</p>
	 * <p>创建时间：2016-5-12 上午10:32:57</p>
	 *
	 */
	public static String doGet(String url,Map<String,String> map,String charset) throws Exception{
		boolean isHttps = false;
		if(url.toLowerCase().startsWith("https"))
			isHttps = true;
		HttpClient httpClient = null;
		HttpGet httpGet = null;
		String result = null;
		try{
			httpClient = isHttps? new SSLClient() :new DefaultHttpClient();
			httpGet = new HttpGet(url);
			//设置参数
			if(map != null){
				HttpParams params = new BasicHttpParams();
				Iterator iterator = map.entrySet().iterator();
				while(iterator.hasNext()){
					Entry<String,String> elem = (Entry<String, String>) iterator.next();
					params.setParameter(elem.getKey(),elem.getValue());
				}
			}
			HttpResponse response = httpClient.execute(httpGet);
			if(response != null){
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null){
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}catch(Exception ex){
			throw new Exception("短信发送失败："+ex);
		}
		return result;
	}
}