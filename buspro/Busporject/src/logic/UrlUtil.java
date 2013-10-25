package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/*
 * URL������
 */
public class UrlUtil {
	
	
	 private HttpParams httpParams;

	 private HttpClient httpClient;


	
	public HttpClient getHttpClient() {

        // ���� HttpParams ���������� HTTP ��������һ���ֲ��Ǳ���ģ�

        this.httpParams = new BasicHttpParams();

        // �������ӳ�ʱ�� Socket ��ʱ���Լ� Socket �����С

        HttpConnectionParams.setConnectionTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSoTimeout(httpParams, 20 * 1000);

        HttpConnectionParams.setSocketBufferSize(httpParams, 8192);

        // �����ض���ȱʡΪ true

        HttpClientParams.setRedirecting(httpParams, true);

        // ���� user agent

        String userAgent = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6";
        HttpProtocolParams.setUserAgent(httpParams, userAgent);

        httpClient = new DefaultHttpClient(httpParams);

        return httpClient;
    }

	
	public String doPost(String url, List<NameValuePair> params) {

		httpClient=this.getHttpClient();		
        /* ����HTTPPost���� */
        HttpPost httpRequest = new HttpPost(url);

        String strResult = "doPostError";
        
        try {
            /* �������������������� */
            httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            System.out.println(httpRequest);
            /* �������󲢵ȴ���Ӧ */
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            /* ��״̬��Ϊ200 ok */
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                /* ���������� */
                strResult = EntityUtils.toString(httpResponse.getEntity());

            } else {
                strResult ="error";// "Error Response: "+ httpResponse.getStatusLine().toString();
            }
        } catch (ClientProtocolException e) {
        	strResult ="error";
            //strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (IOException e) {
        	strResult ="error";
            //strResult = e.getMessage().toString();
            e.printStackTrace();
        } catch (Exception e) {
        	strResult ="error";
            //strResult = e.getMessage().toString();
            e.printStackTrace();
        }
        //Log.v("strResult", strResult);
        return strResult;
    }


	
	
	 /**  
     * ��ȡ����ָ������ҳ���룬���䷵�ظ������ߣ��ɵ����߶������  
     * ����String  
     * qian.long  
     * 2011-06-07  
     */  
    public static String posturl(String url) {  
    	
    	/*
    	try {
			url=URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	*/
        InputStream is = null;   
        String result = "";   
        
        try {   
           HttpClient httpclient = new DefaultHttpClient();   
           HttpPost httppost = new HttpPost(url);           
           
           HttpResponse response = httpclient.execute(httppost);   
            HttpEntity entity = response.getEntity();   
            is = entity.getContent();   
        } catch(Exception e) {   
            return "-2";   
        }
        
        try{   
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"));   
            StringBuilder sb = new StringBuilder();   
            String line = null;   
            while ((line = reader.readLine()) != null) {   
                sb.append(line + "\n");   
            }   
            is.close();   
        
            result=sb.toString();   
        } catch (Exception e) {   
            return "-3";
        }
        
        return result;   
    }
	

}