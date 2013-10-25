package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 换乘查询
 * @author Administrator
 *
 */
public class RechangeLogic implements Runnable{

	private Handler handler;
	private String stationBegin;//开始站点
	private String stationEnd;//截止站点
	
	public RechangeLogic(Handler handler,String stationBegin,String stationEnd) {
		super();
		this.handler = handler;
		this.stationBegin=stationBegin;
		this.stationEnd=stationEnd;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {			
			// 根据地址调用服务端
			String url="http://cybus.bizinfocus.com:8080/searchHc";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("zdmc1", stationBegin));
	        params.add(new BasicNameValuePair("zdmc2", stationEnd));
			
	        String r=uu.doPost(url, params);
			
			Message msg = prepareMessage(r);
			// message将被添加到主线程的MQ中          
			handler.sendMessage(msg);
		} catch (Exception e) { 
			//Log.d(TAG, "interrupted!");        
		}   
	}
	
	
	private Message prepareMessage(String str) {
		Message result = handler.obtainMessage();
		Bundle data = new Bundle(); 
		data.putString("message", str);  
		result.setData(data); 
		return result;  
	}
	
	

}
