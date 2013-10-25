package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * ICK��ѯ
 * @author Administrator
 *
 */
public class ICKLogic implements Runnable{


	Handler handler;
	private String kh;//��·����
	
	//���� һ��vsn��������
	 
	
	public ICKLogic(Handler handler,String kh) {
		super();
		this.handler = handler;
		this.kh = kh;
	}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {			
			// ���ݵ�ַ���÷����
			// ���ݵ�ַ���÷����
					
			
			String url="http://cybus.bizinfocus.com:8080/searchICK";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();	        	        
				
			
	        
	        params.add(new BasicNameValuePair("kh", kh));	        
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
		data.putString("info", str);		
		result.setData(data); 
		return result;  
	}

	
	

}