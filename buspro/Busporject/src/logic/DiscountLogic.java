package logic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DiscountLogic implements Runnable {
	Handler handler;
	String[] rs;


	public DiscountLogic(Handler handler) {
		super();
		this.handler = handler;
	}

	@SuppressWarnings("null")
	@Override
	public void run() {
		// TODO Auto-generated method stub
		final String url = "http://miao.bizinfocus.com/interfaces/goodsList.aspx";

		final UrlUtil uu = new UrlUtil();
		String r = UrlUtil.posturl(url);
		Message msg = prepareMessage(r);
		// message将被添加到主线程的MQ中          
		handler.sendMessage(msg);	
	}
	private Message prepareMessage(String str) {
		Message result = handler.obtainMessage();
		Bundle data = new Bundle(); 		 
		data.putString("info", str);		
		result.setData(data); 
		return result;  
	}
}
