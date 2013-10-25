package com.bus.activities;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

public class TransInfoActivity extends SherlockActivity {

	
	
	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/**
		//图片缓存
		imageCacheManager = ImageCacheManager.getImageCacheService(this,
				ImageCacheManager.MODE_FIXED_MEMORY_USED, "memory");
		imageCacheManager.setMax_Memory(1024 * 1024);**/
		
		
		
		
		
		Intent intent1 = getIntent();//获得自身	
		String type=intent1.getStringExtra("type");	
				
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		
		if(type.equals("一次换乘")){
			setContentView(R.layout.activity_trans_info);
		}else if(type.equals("二次换乘")){
			setContentView(R.layout.activity_trans_info2);			
		}
		/**
		SharedPreferences shared=getSharedPreferences("ad",Context.MODE_PRIVATE);
		
		ImageView  bg1=  (ImageView) this.findViewById(R.id.view_bg1);	
		new DownloadTask(bg1)
		.execute(Config.imgurl+shared.getString("ad_6", ""));
		
		ImageView  bg2=  (ImageView) this.findViewById(R.id.view_bg2);	
		new DownloadTask(bg2)
		.execute(Config.imgurl+shared.getString("ad_7", ""));
		
		ImageView  bg3=  (ImageView) this.findViewById(R.id.view_bg3);	
		new DownloadTask(bg3)
		.execute(Config.imgurl+shared.getString("ad_8", ""));
		**/
		
		//TextView textView1=  (TextView) this.findViewById(R.id.textView1);
		//textView1.setText(type);
		
		String infos[]=intent1.getStringArrayExtra("infos");
			
		if(type.equals("一次换乘") || type.equals("二次换乘") ){
			TextView txt_zd1= (TextView) this.findViewById(R.id.txt_zd1);
			TextView txt_zd2= (TextView) this.findViewById(R.id.txt_zd2);
			TextView txt_zd3= (TextView) this.findViewById(R.id.txt_zd3);
			
			TextView txt_xl1= (TextView) this.findViewById(R.id.txt_xl1);
			TextView txt_xl2= (TextView) this.findViewById(R.id.txt_xl2);			
			
			txt_xl1.setText(infos[0]);
			txt_xl2.setText(infos[3]);
			
			txt_zd1.setText(infos[1]);
			txt_zd2.setText(infos[2]);
			txt_zd3.setText(infos[5]);
			getSupportActionBar().setTitle("一次换乘");
		
		}
		
		if(type.equals("二次换乘"))
		{
			TextView txt_zd4= (TextView) this.findViewById(R.id.txt_zd4);
			TextView txt_xl3= (TextView) this.findViewById(R.id.txt_xl3);
			
			txt_xl3.setText(infos[6]);
			txt_zd4.setText(infos[8]);
			getSupportActionBar().setTitle("二次换乘");
		}
		
		
		
	}
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case android.R.id.home:
			TransInfoActivity.this.finish();            
	         return true;
		
		default:
			return super.onOptionsItemSelected(item);
		}
	}

}
