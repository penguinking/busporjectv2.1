package com.bus.activities;



//import com.baidu.location.LocationClient;
//import com.baidu.location.LocationClientOption;

import util.UpdateAd;
import util.UpdateManager;

import android.os.Bundle;
import android.os.Vibrator;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.TabHost;
import android.widget.Toast;

public class MainActivity extends  TabActivity  {

	TabHost tb;
	private UpdateManager mUpdateManager;
	private UpdateAd mUpdateAd;
	private Vibrator mVibrator01 =null;
	Context context;
	//private LocationClient mLocClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		
		
		setContentView(R.layout.maintabs);
		/**
		 * 百度地图sdk基站定位实现
		 
		mLocClient = ((ApplicationLocation)getApplication()).mLocationClient;
		mVibrator01 =(Vibrator)getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		((ApplicationLocation)getApplication()).mVibrator01 = mVibrator01;
		LocationClientOption option = new LocationClientOption();
		option.setCoorType("bd09ll");
		option.setAddrType("all");
		int time = (int) 0.5;
		option.setScanSpan(time);
		option.setPriority(LocationClientOption.NetWorkFirst);
		option.setPoiNumber(10);
		option.disableCache(true);		
		mLocClient.setLocOption(option);
		mLocClient.start();
		*/
		

		
		
		//更新广告
		//mUpdateAd=new UpdateAd(this);
		//mUpdateAd.checkUpdateInfo();

		
		tb = this.getTabHost();
		
		tb.addTab(tb.newTabSpec("tab1")		
				.setIndicator("bus1")
				.setContent(new Intent(this, MoreActivity.class)));		
		tb.addTab(tb.newTabSpec("tab2")
				.setIndicator("bus2")
				.setContent(new Intent(this, LifeChannel.class)));
		tb.addTab(tb.newTabSpec("tab3")
				.setIndicator("bus3")
				.setContent(new Intent(this, BusChannel.class)));
		tb.addTab(tb.newTabSpec("tab4")
				.setIndicator("bus4")
				.setContent(new Intent(this, ShopChannel.class)));
		tb.addTab(tb.newTabSpec("tab5")
				.setIndicator("bus5")
				.setContent(new Intent(this, CarChannel.class)));
		tb.setCurrentTab(2);
		
	}

	
	
	
	public void goTab1(View  v)
	{			
		tb.setCurrentTab(0);		
	}
	public void goTab2(View  v)
	{			
		tb.setCurrentTab(1);		
	}
	public void goTab3(View  v)
	{			
		tb.setCurrentTab(2);		
	}
	public void goTab4(View  v)
	{			
		tb.setCurrentTab(3);		
	}
	public void goTab5(View  v)
	{			
		tb.setCurrentTab(4);		
	}
private long exitTime = 0;
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK 
                &&event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime)>2000){  
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();                                
                exitTime = System.currentTimeMillis();   
            } else {
                finish();
                System.exit(0);
            }
            return true;   
        }
        return super.onKeyDown(keyCode, event);
    }
		
}
