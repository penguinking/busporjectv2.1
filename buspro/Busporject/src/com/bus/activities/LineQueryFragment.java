package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;
import com.loopj.android.image.SmartImageView;
import com.loopj.android.image.WebImageCache;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LineQueryFragment extends Fragment {
	String linenum = "";
	private ListView listView;
	public DBManager dbHelper;
	private ArrayList<HashMap<String, Object>> listItems;
	private SimpleAdapter listItemAdapter;
	private SQLiteDatabase database;
	private EditText line_number;
	String lineNumber = "";
	private RelativeLayout toplayout;
	private TabPageIndicator indicator;
	private static WebImageCache webImageCache;
	ProgressBar progressbar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.query_line, container, false);
		ImageButton btn_search = (ImageButton) rootView
				.findViewById(R.id.qs_btn_search);
		line_number = (EditText) rootView.findViewById(R.id.lqc_queryParam);
		// linenum = line_number.getText().toString();
		listView = (ListView) rootView.findViewById(R.id.qs_list_cy);
		toplayout = (RelativeLayout) getActivity().findViewById(
				R.id.message_top);
		indicator = (TabPageIndicator) getActivity().findViewById(
				R.id.indicator);
		SmartImageView channelAd = (SmartImageView) rootView.findViewById(R.id.common_ad);
		SmartImageView LOGOAd = (SmartImageView) rootView.findViewById(R.id.logo_ad);
		if(webImageCache == null) {
            webImageCache = new WebImageCache(getActivity());
        }
	    int statu_wifi = util.IsWIFI.isWifi(getActivity());
	    if(statu_wifi==1){
	    	channelAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/busChannel/1.png");
	    	LOGOAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/index/start_top.png");
	    }else{
	    	Bitmap bitmap = null;
	    	bitmap=webImageCache.get("http://mobile.bizinfocus.com/visitor/ad/busChannel/1.png");
	    	channelAd.setImageBitmap(bitmap);
	    }
		//int id = getResources().getIdentifier("discount_ad_1",
				//"drawable", getActivity().getPackageName());
		//channelAd.setImageResource(id);
		//channelAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/busChannel/1.png");
		channelAd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Toast toast = Toast.makeText(getActivity()
						.getApplicationContext(), "请稍等", Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, 0);
				LinearLayout toastView = (LinearLayout) toast.getView();
				progressbar = new ProgressBar(getActivity()
						.getApplicationContext());
				toastView.addView(progressbar, 0);
				toast.show();
				Thread workerThread = new Thread(new logic.DiscountLogic(
						new mHandler()));
				workerThread.start();

			}

		});
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {

				lineNumber = (String) ((TextView) (view
						.findViewById(R.id.textView1))).getText(); // 线路
				lineNumber = lineNumber.replace("路", "");
				linenum = lineNumber;
				Intent intent = new Intent(getActivity(),
						LineInfoActivity.class); // 从启动动画ui跳转到主ui
				intent.putExtra("xl", linenum);
				startActivity(intent);
				toplayout.setVisibility(View.VISIBLE);
				indicator.setVisibility(View.VISIBLE);
			}
		});
		btn_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				linenum = line_number.getText().toString();
				Thread workerThread = new Thread(new logic.RouteLogic(
						new MyHandler(), linenum));
				workerThread.start();
				toplayout.setVisibility(View.GONE);
				indicator.setVisibility(View.GONE);
			}

		});
		line_number.addTextChangedListener(textWatcher);
		dbHelper = new DBManager(getActivity());
		dbHelper.openDatabase();
		dbHelper.closeDatabase();
		return rootView;
	}

	class mHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {// 此方法在ui线程运行
			String info = msg.getData().getString("info");
			if (info != null) {

				Intent intent = new Intent(getActivity(),
						DiscountActivity.class); // 从启动动画ui跳转到主ui
				intent.putExtra("info", info);
				startActivity(intent);
			} else {
				Toast.makeText(getActivity(), "目前没有秒杀活动进行中，感谢您的关注！", 1).show();
			}
			// info2 = info.split(";");
			// System.out.println(info2[1]);
		}
	}

	class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// 更新UI
			if (msg.getData().getStringArray("list").length > 1) {
				showList(msg.getData().getStringArray("list"));
			} else
				Toast.makeText(getActivity(), "抱歉，未找到该线路信息", 1).show();
		}
	}

	public void showList(String[] str) {

		// 关闭输入法
		InputMethodManager inputMethodManager = (InputMethodManager) getActivity()
				.getSystemService(getActivity().INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getActivity()
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

		if (str.length < 1) {
			Toast.makeText(getActivity(), "未查到记录", 1).show();
		} else {
			// values=str;

			Intent intent = new Intent(getActivity(), LineInfoActivity.class); // 从启动动画ui跳转到主ui

			intent.putExtra("zpnames", str);
			intent.putExtra("xl", linenum);

			startActivity(intent);

		}
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub

			String s1 = line_number.getText().toString();
			if ("".equals(s1)) {
			} else {
				database = SQLiteDatabase.openOrCreateDatabase(
						DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
				System.out.println(DBManager.DB_PATH + "/" + DBManager.DB_NAME);
				// p = Pattern.compile("[0-9]*");
				// Matcher m = p.matcher(s1);
				listItems = new ArrayList<HashMap<String, Object>>();
				Cursor cur2 = database.rawQuery(
						"SELECT DISTINCT xid from cnbus where xid like '%" + s1
								+ "%'", null);
				while (cur2.moveToNext()) {
					String line = cur2.getString(cur2.getColumnIndex("xid"))
							+ "路";
					// System.out.println(line + "路" + station);
					int j = cur2.getColumnCount();
					// System.out.println(j);

					HashMap<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < j; i++) {
						map.put("ItemTitle", line);
					}
					listItems.add(map);
				}
				listItemAdapter = new SimpleAdapter(getActivity(), listItems, // listItems数据源
						R.layout.list_item_line_stops, // ListItem的XML布局实现
						new String[] { "ItemTitle" }, // 动态数组与ImageItem对应的子项
						new int[] { R.id.textView1 } // list_item.xml布局文件里面的一个ImageView的ID,一个TextView
														// 的ID
				);
				toplayout.setVisibility(View.GONE);
				indicator.setVisibility(View.GONE);
				listView.setAdapter(listItemAdapter);
				database.close();

			}
		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}
	};
}
