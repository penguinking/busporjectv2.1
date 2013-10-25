package util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;



public class Utils {

	
	public static int getVerCode(Context context) { 
		 int verCode = -1;
		 try {  
			 verCode = context.getPackageManager().getPackageInfo(  "com.bus.activities", 0).versionCode;  
	     } catch (NameNotFoundException e) 
	     {  
	    	 //Log.e(TAG, e.getMessage());  
	     }  
		 return verCode;  
	} 
	
	public static String getVerName(Context context) { 
		 String verName = "";
		 try {  
			 verName = context.getPackageManager().getPackageInfo(  "com.bus.activities", 0).versionName; 
	     } catch (NameNotFoundException e) 
	     {  
	    	 //Log.e(TAG, e.getMessage());  
	     }  
		 return verName;  
	} 
	
	
	/*
	 * 获取服务器上的版本号码* 
	 */
	public static String [] getServerVer () {
		
		String []v =new String[2];
		v[0]="";
		v[1]="";
		
		try {  
			String verjson = getJsonString("http://download.bizinfocus.com/ver.json");
			//String verjson = getJsonString("http://download.bizinfocus.com/test/ver.json");
			JSONArray array = new JSONArray(verjson); 
			if (array.length() > 0) {  
				JSONObject obj = array.getJSONObject(0);  
				try { 
					v[0] = obj.getString("verCode");  
					v[1] = obj.getString("verName");  
				} catch (Exception e) {  
					
				} 
			}  
		} catch (Exception e) { 
						
		}  
		return v;  
	}
	
	
	/*
	 * 获取远程jion数据
	 */
	public static String getJsonString(String urlPath) throws Exception {  
        URL url = new URL(urlPath);  
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();  
        connection.setConnectTimeout(4000);
        connection.connect();  
        InputStream inputStream = connection.getInputStream();  
        //对应的字符编码转换  
        Reader reader = new InputStreamReader(inputStream, "UTF-8");  
        BufferedReader bufferedReader = new BufferedReader(reader);  
        String str = null;  
        StringBuffer sb = new StringBuffer();  
        while ((str = bufferedReader.readLine()) != null) {  
            sb.append(str);  
        }  
        reader.close();  
        connection.disconnect();  
        return sb.toString();  
    }  
	
	
	
	
	
}
