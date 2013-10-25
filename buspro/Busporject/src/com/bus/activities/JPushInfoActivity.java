package com.bus.activities;

import cn.jpush.android.api.JPushInterface;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class JPushInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_push_info);
		Intent intent = getIntent();
		if(null!=intent){
		Bundle bundle = getIntent().getExtras();
		
		//int id=b.getInt(JPushInterface.EXTRA_PUSH_ID);
		String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
	    String content = bundle.getString(JPushInterface.EXTRA_ALERT);			
		
		/*JSONObject obj;
		try {
			obj = new JSONObject(jsonstr);
			String InfoContent=obj.getString("infoText");	
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		//œ‘ æ–≈œ¢ 
		TextView txtTitle=  (TextView) this.findViewById(R.id.txtTitle);
		TextView txtContent=  (TextView) this.findViewById(R.id.txtConent);
		ImageView ivjump = (ImageView)this.findViewById(R.id.iv_jump);
		txtTitle.setText(title);
		txtContent.setText(content);
		ivjump.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(JPushInfoActivity.this, MainActivity.class);
				JPushInfoActivity.this.startActivity(intent);
				JPushInfoActivity.this.finish();
			}
			
		});
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.push_info, menu);
		return true;
	}

}
