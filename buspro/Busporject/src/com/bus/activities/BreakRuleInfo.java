package com.bus.activities;

import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class BreakRuleInfo extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.break_rule);
		TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
        title.setText("Υ�²�ѯ");
		EditText dst_addr =(EditText) this.findViewById(R.id.lqc_queryParam);
	    String str_hint = getString(R.string.breakrule).toString();
	    dst_addr.setHint(str_hint);
	    SmartImageView channelAd = (SmartImageView) this.findViewById(R.id.common_ad);
	    //int id = getResources().getIdentifier("ad_test", "drawable", getPackageName());
		//channelAd.setImageResource(id);
	}

}
