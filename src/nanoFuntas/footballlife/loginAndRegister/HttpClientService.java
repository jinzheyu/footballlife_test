package nanoFuntas.footballlife.loginAndRegister;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpClientService {
	private static HttpClient mHttpClient = null;
	private static HttpPost mHttpPost = null;
	private static HttpResponse mHttpResponse = null;

	final static String strUrl = "http://192.168.219.148:8080/FootballLifeServer/LoginNRegisterServlet";
	
	public static String executeHttpPose(ArrayList<NameValuePair> postParameters){
		mHttpClient = getHttpClient();
		mHttpPost = new HttpPost(strUrl);

		encodeParams2HttpPost(postParameters, mHttpPost);
		try {
			mHttpResponse = mHttpClient.execute(mHttpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return getOutputFromHttpResponse(mHttpResponse);
	}

	private static String getOutputFromHttpResponse(HttpResponse response){
		
		HttpEntity entity = response.getEntity();								
		
		InputStreamReader isr = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		
		try {
			isr = new InputStreamReader(entity.getContent());
			br = new BufferedReader(isr);
			sb = new StringBuffer("");
			String line = "";
			
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();		
			
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if(isr != null)
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(br != null)
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
				
		return sb.toString();
	}
	
	private static HttpClient getHttpClient(){
		if(mHttpClient == null){
			mHttpClient = new DefaultHttpClient();
		}		
		return mHttpClient;		
	}
	
	private static void encodeParams2HttpPost(ArrayList<NameValuePair> postParameters, HttpPost post){
		UrlEncodedFormEntity formEntity = null;
		try{
			formEntity = new UrlEncodedFormEntity(postParameters);
		} catch (UnsupportedEncodingException e1){
			e1.printStackTrace();
		}		
		post.setEntity(formEntity);
	}
}
