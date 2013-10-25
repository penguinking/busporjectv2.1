package com.bus.activities;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class BusinessContact extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_businesscontact);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ImageView iv_phone = (ImageView) findViewById(R.id.iv_phone);
		ImageView iv_phone_400 = (ImageView) findViewById(R.id.iv_phone_400);
		iv_phone.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String number = getResources().getString(R.string.phone_number);
				// 用intent启动拨打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ number));
				startActivity(intent);
			}
		});
		iv_phone_400.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String number = getResources().getString(
						R.string.phone_number_400);
				;
				// 用intent启动拨打电话
				Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
						+ number));
				startActivity(intent);
			}
		});
	}
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case android.R.id.home:
			BusinessContact.this.finish();            
	         return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
