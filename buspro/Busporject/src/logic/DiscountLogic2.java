package logic;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class DiscountLogic2 implements Runnable {
	Handler handler;
	private String id;
	private String device_id;

	public DiscountLogic2(Handler handler, String id, String device_id) {
		super();
		this.handler = handler;
		this.id = id;
		this.device_id = device_id;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			// ���ݵ�ַ���÷����
			// ���ݵ�ַ���÷����

			String url = "http://miao.bizinfocus.com/interfaces/buy.aspx";

			UrlUtil uu = new UrlUtil();
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			params.add(new BasicNameValuePair("id", id));
			params.add(new BasicNameValuePair("cardid", device_id));
			String r = uu.doPost(url, params);

			Message msg = prepareMessage(r);
			// message������ӵ����̵߳�MQ��
			handler.sendMessage(msg);

		} catch (Exception e) {
			// Log.d(TAG, "interrupted!");
		}
	}

	private Message prepareMessage(String str) {
		Message result = handler.obtainMessage();
		Bundle data = new Bundle();
		data.putString("status", str);
		result.setData(data);
		return result;
	}
}
