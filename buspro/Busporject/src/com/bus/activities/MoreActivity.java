package com.bus.activities;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MoreActivity extends BaseActivity {
	private MyImageButton btapp1 = null;
	private MyImageButton btapp2 = null;
	private MyImageButton btapp3 = null;
	private MyImageButton btapp4 = null;
	private MyImageButton btapp5 = null;
	private MyImageButton btapp6 = null;
	private LinearLayout llbtapp1 = null;
	private LinearLayout llbtapp2 = null;
	private LinearLayout llbtapp3 = null;
	private LinearLayout llbtapp4 = null;
	private LinearLayout llbtapp5 = null;
	private LinearLayout llbtapp6 = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		//getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
		title.setText("功能");
		//btapp5 = new MyImageButton(this, R.drawable.icon_app_common,
				//R.string.common_query);
		btapp1 = new MyImageButton(this, R.drawable.icon_app_book,
				R.string.book);
		llbtapp1 = (LinearLayout) findViewById(R.id.application1);
		llbtapp1.addView(btapp1);
		btapp2 = new MyImageButton(this, R.drawable.icon_app_appstore,
				R.string.app_store);
		llbtapp2 = (LinearLayout) findViewById(R.id.application2);
		llbtapp2.addView(btapp2);
		btapp3 = new MyImageButton(this, R.drawable.icon_app_weather,
				R.string.weather);
		llbtapp3 = (LinearLayout) findViewById(R.id.application3);
		llbtapp3.addView(btapp3);
		btapp4 = new MyImageButton(this, R.drawable.icon_app_info,
				R.string.info);
		llbtapp4 = (LinearLayout) findViewById(R.id.application4);
		llbtapp4.addView(btapp4);
		btapp5 = new MyImageButton(this, R.drawable.icon_app_contact,
				R.string.contact);
		llbtapp5 = (LinearLayout) findViewById(R.id.application5);
		llbtapp5.addView(btapp5);
		btapp6 = new MyImageButton(this, R.drawable.icon_app_local,
				R.string.local);
		llbtapp6 = (LinearLayout) findViewById(R.id.application6);
		llbtapp6.addView(btapp6);
		/**功能暂时屏蔽
		ActionItem Item1 = new ActionItem(1, "水费查询", getResources().getDrawable(R.drawable.icon_app_water));
		ActionItem Item2 = new ActionItem(2, "煤气查询", getResources().getDrawable(R.drawable.icon_app_gas));
        ActionItem Item3 = new ActionItem(3, "电费查询", getResources().getDrawable(R.drawable.icon_app_elc));
        ActionItem Item4 = new ActionItem(4, "工资查询", getResources().getDrawable(R.drawable.icon_app_cash56));
        ActionItem Item5 = new ActionItem(5, "公积金查询", getResources().getDrawable(R.drawable.icon_app_gjj56));
        ActionItem Item6 = new ActionItem(5, "社保查询", getResources().getDrawable(R.drawable.icon_app_si56));
        ActionItem Item7 = new ActionItem(5, "医保查询", getResources().getDrawable(R.drawable.icon_app_medical56));
        //ActionItem Item6 = new ActionItem(6, "云中听", getResources().getDrawable(R.drawable.icon_app_music));
        //use setSticky(true) to disable QuickAction dialog being dismissed after an item is clicked
        
		final QuickAction mQuickAction 	= new QuickAction(this);
		final QuickAction mQuickAction2 	= new QuickAction(this);
		mQuickAction.addActionItem(Item1);
		mQuickAction.addActionItem(Item2);
		mQuickAction.addActionItem(Item3);
		mQuickAction2.addActionItem(Item4);
		mQuickAction2.addActionItem(Item5);
		mQuickAction2.addActionItem(Item6);
		mQuickAction2.addActionItem(Item7);
		//setup the action item click listener
		mQuickAction.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				//ActionItem actionItem = quickAction.getActionItem(pos);
				switch(actionId){
				case 1:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
					break;
				case 2:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
					break;
				case 3:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
					break;
				
				}

			}
		});
	
        mQuickAction2.setOnActionItemClickListener(new QuickAction.OnActionItemClickListener() {
			@Override
			public void onItemClick(QuickAction quickAction, int pos, int actionId) {
				//ActionItem actionItem = quickAction.getActionItem(pos);
				switch(actionId){
				case 4:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
					break;
				case 5:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
			        break;
				case 6:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
					break;
				case 7:
					Toast.makeText(getApplicationContext(), "建设中，请继续关注", Toast.LENGTH_SHORT).show();
			        break;
				}

			}
		});
        **/
		/**关于软件**/
		btapp4.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MoreActivity.this, AboutActivity.class);
				MoreActivity.this.startActivity(intent);
			}
			
		});
		/**软件市场**/
		btapp2.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				String url = "http://www.appchina.com/";
				bundle.putString("url",url);
				bundle.putString("name",getResources().getString(R.string.app_store));
		        intent.putExtras(bundle);
				intent.setClass(MoreActivity.this, WebPageView2Activity.class);
				MoreActivity.this.startActivity(intent);
			}
			
		});
		/**商务联系**/
		btapp5.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(MoreActivity.this, BusinessContact.class);
				MoreActivity.this.startActivity(intent);
			}
			
		});
		/**掌上书城**/
		btapp1.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				String url = "http://book.sina.cn/";
				bundle.putString("url",url);
				bundle.putString("name",getResources().getString(R.string.book));
		        intent.putExtras(bundle);
				intent.setClass(MoreActivity.this, WebPageView2Activity.class);
				MoreActivity.this.startActivity(intent);
			}
			
		});
		/**位置设置**/
		btapp6.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent2 = new Intent();
				intent2.setClass(MoreActivity.this, LocationCountryActivity.class);
				MoreActivity.this.startActivity(intent2);
			}
			
		});
		/**天气预报**/
		btapp3.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				Bundle bundle = new Bundle();
				String url = "http://wap.weather.com.cn/wap/weather/101071201.shtml";
				bundle.putString("url",url);
				bundle.putString("name",getResources().getString(R.string.weather));
		        intent.putExtras(bundle);
				intent.setClass(MoreActivity.this, WebPageView2Activity.class);
				MoreActivity.this.startActivity(intent);
			}
			
		});
	}
	
	

}
