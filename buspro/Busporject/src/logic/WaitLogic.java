package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class WaitLogic implements Runnable {
	Handler handler;	
	private String route;//��·����
	private String station ;//վ������
	
	
	public WaitLogic(Handler handler,String route,String station) {
		super();
		this.handler = handler;		
		this.route=route;
		this.station=station;
		
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {			
			// ���ݵ�ַ���÷����
			String url="http://cybus.bizinfocus.com:8080/searchWait";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("zdmc", station));
	        params.add(new BasicNameValuePair("xlbh", route));
			
	        String r=uu.doPost(url, params);
			
			Message msg = prepareMessage(r);
			// message�������ӵ����̵߳�MQ��          
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