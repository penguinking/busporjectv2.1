package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
/**
 * 站点查询
 * @author Administrator
 *
 */
public class StationLogic implements Runnable {
	private static final String TAG = StationLogic.class.getSimpleName();
	private Handler handler;
	private String stationName;
	
	public StationLogic(Handler handler,String stationName) {
		super();
		this.handler = handler;
		this.stationName = stationName;
	}
	
	@Override
	public void run() {
		try {			
			// 根据站牌名称查询线路
			String url="http://cybus.bizinfocus.com:8080/searchXlByZd";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("zpmc", "%"+stationName+"%"));	       
						
			String r=uu.doPost(url, params);
			String r1=r.replace("|", ",");
		    String []rs=  r1.split(",");
			
			
			Message msg = prepareMessage(rs);
			// message将被添加到主线程的MQ中          
			handler.sendMessage(msg);
		} catch (Exception e) { 
			Log.d(TAG, "interrupted!");        
		}   
	}
	
	private Message prepareMessage(String[] list) {
		Message result = handler.obtainMessage();
		Bundle data = new Bundle(); 		 
		data.putStringArray("list", list);
		
		result.setData(data); 
		return result;  
	}
	
}
