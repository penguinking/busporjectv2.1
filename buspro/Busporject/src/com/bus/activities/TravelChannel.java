package com.bus.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

public class TravelChannel extends FragmentActivity{
	private static final String[] CONTENT = new String[] { "航班", "客运", "旅行社"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.travelchannel);
        TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
        title.setText("出行建议");

        FragmentPagerAdapter adapter = new GoogleMusicAdapter(getSupportFragmentManager());

        ViewPager pager = (ViewPager)findViewById(R.id.pager);
        pager.setAdapter(adapter);

        TabPageIndicator indicator = (TabPageIndicator)findViewById(R.id.indicator);
        indicator.setViewPager(pager);
        pager.setCurrentItem(1);
    }

    class GoogleMusicAdapter extends FragmentPagerAdapter {
        public GoogleMusicAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
        	switch(position) {
        	case 0:
        		return new AirplaneQueryFragment();
        	case 1:
        		return new CoachQueryFragment();
        	case 2:
        		return new AgentQueryFragment();
        	default:
        		return null;
        	}
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return CONTENT[position % CONTENT.length].toUpperCase();
        }

        @Override
        public int getCount() {
            return CONTENT.length;
        }
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
