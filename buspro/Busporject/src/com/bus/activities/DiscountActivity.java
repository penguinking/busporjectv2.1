package com.bus.activities;
import com.bus.activities.R;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.TextView;

public class DiscountActivity extends FragmentActivity{
    ViewPager mPager;
    PageIndicator mIndicator;
    String info;
    String[] info2;
    String[] info3;
    TextView tv;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        //The look of this sample is set via a style in the manifest
        setContentView(R.layout.discount_fragmentactivity);
        PagerAdapter mAdapter = new DiscountAdapter(getSupportFragmentManager());
        Intent intent1 = getIntent();// 获得自身
        info = intent1.getStringExtra("info");
        info2 = info.split(";");
        mPager = (ViewPager)findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);
        
        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mPager);
    }
    
    class DiscountAdapter extends FragmentStatePagerAdapter {
    	public DiscountAdapter(FragmentManager fm) {
    		super(fm);
    		// TODO Auto-generated constructor stub
    	}
    	@Override
    	public Fragment getItem(int position) {	//返回Fragment
    		Fragment fragment = new DiscountFragment();

    		Bundle args = new Bundle();

    		args.putInt("no", position);
    		args.putStringArray("info", info2);
    		fragment.setArguments(args);
    		return fragment;

    	}

    	@Override
    	public int getCount() {		//根据图片数组长度，确定生成的Fragment的数量
    		// TODO Auto-generated method stub
    		return info2.length-1;
    	}
    	
    	
    }
    
}
    
