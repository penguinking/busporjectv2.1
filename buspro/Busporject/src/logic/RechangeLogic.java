package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * ���˲�ѯ
 * @author Administrator
 *
 */
public class RechangeLogic implements Runnable{

	private Handler handler;
	private String stationBegin;//��ʼվ��
	private String stationEnd;//��ֹվ��
	
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
			// ���ݵ�ַ���÷����
			String url="http://cybus.bizinfocus.com:8080/searchHc";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("zdmc1", stationBegin));
	        params.add(new BasicNameValuePair("zdmc2", stationEnd));
			
	        String r=uu.doPost(url, params);
			
			Message msg = prepareMessage(r);
			// message������ӵ����̵߳�MQ��          
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
