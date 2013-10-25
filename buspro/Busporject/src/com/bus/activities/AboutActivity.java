package com.bus.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class AboutActivity extends Activity {
	private static final String TAG = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.messagesactivity);
		TelephonyManager tm = (TelephonyManager) this
				.getSystemService(Context.TELEPHONY_SERVICE);
		String id = tm.getDeviceId();
		TextView tv_id = (TextView) findViewById(R.id.tv_id);
		TextView tv_ver = (TextView) findViewById(R.id.tv_ver);
		tv_id.setText("当前用户ID值为：" + id);
		tv_ver.setText("Ver:"+getVerName(this));
		TextView tv_agreement = (TextView) findViewById(R.id.tv_agreement);
		tv_agreement.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(AboutActivity.this, AgreementActivity.class);
				AboutActivity.this.startActivity(intent);
			}

		});
		// getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
			"com.bus.activities", 0).versionCode;

		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verCode;
	}

	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
			"com.bus.activities", 0).versionName;

		} catch (NameNotFoundException e) {
			Log.e(TAG, e.getMessage());
		}
		return verName;
	}
}
