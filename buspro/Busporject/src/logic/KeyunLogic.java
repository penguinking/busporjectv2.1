package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;


/*
 * 客运业务逻辑类
 */
public class KeyunLogic implements Runnable{

	Handler handler;
	private String zhongdianzhan;//线路名称测试测试
	
	
	public KeyunLogic(Handler handler,String zhongdianzhan) {
		super();
		this.handler = handler;
		this.zhongdianzhan = zhongdianzhan;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {			
			// 根据地址调用服务端
			// 根据地址调用服务端
			String url="http://cybus.bizinfocus.com:8080/searchKeyunList";			
			
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();	        	        
				
			
	        
	        params.add(new BasicNameValuePair("zhongdianzhan", zhongdianzhan));	        
	        String r=uu.doPost(url, params);
	        			
			Message msg = prepareMessage(zhongdianzhan,r);
			// message将被添加到主线程的MQ中          
			handler.sendMessage(msg);				        
				
			
		} catch (Exception e) { 
			//Log.d(TAG, "interrupted!");        
		}   
	}
	
	
	private Message prepareMessage(String title,String str) {
		Message result = handler.obtainMessage();
		Bundle data = new Bundle(); 	
		
		if(str!=null && !str.equals("error") ){			
			String[] keyunstr= str.split("#");				
			data.putStringArray("kuaike", keyunstr[0].split(";"));
			data.putStringArray("puke", keyunstr[1].split(";"));
			data.putString("error", "false");	
		}else
		{
			data.putString("error", "true");			
		}
		data.putString("title", title);
		result.setData(data); 
		return result;  
	}

}
