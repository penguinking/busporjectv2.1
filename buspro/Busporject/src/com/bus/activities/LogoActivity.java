package com.bus.activities;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.image.WebImageCache;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;


public class LogoActivity extends BaseActivity {
	private static WebImageCache webImageCache;
	private SmartImageView MainAd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		MainAd = (SmartImageView) findViewById(R.id.common_ad);
		if(webImageCache == null) {
            webImageCache = new WebImageCache(this);
        }
	    	Bitmap bitmap = null;
	    	bitmap=webImageCache.get("http://mobile.bizinfocus.com/visitor/ad/index/start_top.png");
	    	if(bitmap!=null){
	    	MainAd.setImageBitmap(bitmap);
	    	}else{
	    		int id = getResources().getIdentifier("img_company_logo",
	    				"drawable", this.getPackageName());
	    	MainAd.setImageResource(id);
	    	}
	    	
		// String v[]= util.Utils.getServerVer();

		// v[1]= util.Utils.getVerName(this);
		// Toast.makeText(this, v[1], 3).show();
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {


				// WIFI下下载更新图片
				// int netx=isWifi(LogoActivity.this );

				// if(netx==1){
				// new UpdateAd().downloadAD();
				// }

				// 启动下载广告服务
				// Intent service=new Intent(LogoActivity.this,AdService.class);
				// startService(service);

				// 读取最新广告列表

				// 存在网络
				/**广告列表
				if (note_Intent(LogoActivity.this)) {

					UrlUtil uu = new UrlUtil();
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					String adStr = uu
							.doPost("http://cybus.bizinfocus.com/visitor/getColADList.php",
									params);

					if (!adStr.equals("null")) {

						String[] adList = adStr.split(";");

						SharedPreferences shared = getSharedPreferences("ad",
								Context.MODE_PRIVATE);
						Editor editor = shared.edit();

						// 存储每个广告名
						/** BUG注释
						for (int i = 0; i < adList.length; i++) {
							editor.putString("ad_" + i, adList[i].split(",")[1]);
						}
						editor.commit();
						
					}
					
		}**/

				

				Intent intent = new Intent(LogoActivity.this,
						MainActivity.class); // 从启动动画ui跳转到主ui
				startActivity(intent);
				LogoActivity.this.finish(); // 结束启动动画界面
			}
		}, 2000); // 启动动画持续2秒钟

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.activity_logo, menu);
		return true;
	}

	

	
}
