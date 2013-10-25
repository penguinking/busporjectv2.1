package com.bus.activities;

import com.loopj.android.image.WebImageCache;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BusChannel extends FragmentActivity {
	private static final String[] CONTENT = new String[] { "候车线路", "候车站点",
			"公交卡", "换乘查询" };
	private RelativeLayout toplayout;
	private TabPageIndicator indicator;
	private static WebImageCache webImageCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.query_bus);
		TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
		title.setText("公交查询");
		if (webImageCache == null) {
			webImageCache = new WebImageCache(this);
		}
		int statu_wifi = util.IsWIFI.isWifi(this);
		System.out.println("statu_wifi" + statu_wifi);
		if (statu_wifi == 1) {
			webImageCache.clear();
		}
		toplayout = (RelativeLayout) this.findViewById(R.id.message_top);
		indicator = (TabPageIndicator) this.findViewById(R.id.indicator);
		FragmentPagerAdapter adapter = new GoogleMusicAdapter(
				getSupportFragmentManager());

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);
		int limit = 4;
		pager.setOffscreenPageLimit(limit);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);
		pager.setCurrentItem(0);
	}

	class GoogleMusicAdapter extends FragmentPagerAdapter {
		public GoogleMusicAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			switch (position) {
			case 0:
				return new LineQueryFragment();
			case 1:
				return new StationQueryFragment();
			case 2:
				return new ICQueryFragment();
			case 3:
				return new TransFragment();
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
		if (toplayout.isShown()) {
			if (keyCode == KeyEvent.KEYCODE_BACK
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				if ((System.currentTimeMillis() - exitTime) > 2000) {
					Toast.makeText(getApplicationContext(), "再按一次退出程序",
							Toast.LENGTH_SHORT).show();
					exitTime = System.currentTimeMillis();
				} else {
					finish();
					System.exit(0);
				}
				return true;
			}
		} else if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				exitTime = System.currentTimeMillis();
				toplayout.setVisibility(View.VISIBLE);
				indicator.setVisibility(View.VISIBLE);
			} else {
				finish();
				System.exit(0);
			}
			return true;

		}
		return super.onKeyDown(keyCode, event);
	}

}
