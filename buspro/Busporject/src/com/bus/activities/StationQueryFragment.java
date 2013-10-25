package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;

import util.UpdateManager;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.image.WebImageCache;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;


public class StationQueryFragment extends Fragment{
	String stationname="";
	String zpname;
	String routeName="";
	private adapter.LineStationAdapter adapter;//适配器 	
	private ListView listView;
	public DBManager dbHelper;
	private ArrayList<HashMap<String, Object>>   listItems;
	private SimpleAdapter listItemAdapter;
	private SQLiteDatabase database;
	private EditText station_name;
	private RelativeLayout toplayout;
	private TabPageIndicator indicator;
	private static WebImageCache webImageCache;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View rootView = inflater.inflate(R.layout.query_station,
                container, false);
	    ImageButton btn_search = (ImageButton) rootView.findViewById(R.id.qs_btn_search);
	    station_name =(EditText) rootView.findViewById(R.id.lqc_queryParam);
	    toplayout=(RelativeLayout)getActivity().findViewById(R.id.message_top);
	    indicator=(TabPageIndicator)getActivity().findViewById(R.id.indicator);
	    //广告组件
	    SmartImageView channelAd = (SmartImageView) rootView.findViewById(R.id.common_ad);
	    if(webImageCache == null) {
            webImageCache = new WebImageCache(getActivity());
        }
	    int statu_wifi = util.IsWIFI.isWifi(getActivity());
	    if(statu_wifi==1){
	    	channelAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/busChannel/2.png");
	    }else{
	    	Bitmap bitmap = null;
	    	bitmap=webImageCache.get("http://mobile.bizinfocus.com/visitor/ad/busChannel/2.png");
	    	channelAd.setImageBitmap(bitmap);
	    }
	    listView =(ListView)rootView.findViewById(R.id.qs_list_cy);
	    listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {								
				
			    			
				routeName = (String) ((TextView) (view.findViewById(R.id.textView1))).getText();	//线路
				routeName=routeName.replace("路", "");
				zpname = (String) ((TextView) (view.findViewById(R.id.textView2))).getText();	//线路
				toplayout.setVisibility(View.VISIBLE);
				indicator.setVisibility(View.VISIBLE);
				if (routeName != "抱歉，未找到该站点信息") {
				Thread workerThread = new Thread(new logic.RouteLogic(new MyHandler2(),routeName));        
				workerThread.start();}
				
				
			}		
		}			
	    );
		listView.setAdapter(adapter);
		
		
	    stationname = station_name.getText().toString();
	    btn_search.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stationname = station_name.getText().toString();
				
				Thread workerThread = new Thread(new logic.StationLogic(new MyHandler(),stationname));        
				workerThread.start();
				
			}
	    	
	    });
	    station_name.addTextChangedListener(textWatcher);
	    dbHelper = new DBManager(getActivity());
		dbHelper.openDatabase();
		dbHelper.closeDatabase();
		//这里来检测版本是否需要更新
		int statu_wifi1 = isWifi(getActivity());
		if(statu_wifi1 ==1 ){
				new Handler().postDelayed(new Runnable() {
					@Override
				public void run() {
				try{
					System.out.println("更新线程启动");
					UpdateManager mUpdateManager = new UpdateManager(getActivity());
					mUpdateManager.checkUpdateInfo();
				}catch(Exception ex)
				{
					return;			
				}
					}
				},1000);}
		else{
			
		}
	    return rootView;
	}
private void gotoRouteInfo(String[] str){
		Intent intent = new Intent(getActivity(),RoutelInfoActivity.class);  //从启动动画ui跳转到主ui
		intent.putExtra("zpname", zpname);
		intent.putExtra("zpnames", str);
		intent.putExtra("xl",routeName );
		
		startActivity(intent);
		
	}
class MyHandler extends Handler {   
    	@Override
    	public void handleMessage(Message msg) {			       
			// 更新UI
    		showList(msg.getData().getStringArray("list"));
    	}    	
    }
public void showList(String[] str) { 
		
		//关闭输入法
		InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
		InputMethodManager.HIDE_NOT_ALWAYS);	
		
		if(str.length<1)
		{
			Toast.makeText(getActivity(), "未查到记录", 1).show();			
		}else
		{	
			System.out.println(str);
			try{
			adapter=new adapter.LineStationAdapter(getActivity(),str);
			listView.setAdapter(adapter);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
}

	class MyHandler2 extends Handler {   
	@Override
	public void handleMessage(Message msg) {			       
		// 更新UI
		gotoRouteInfo(msg.getData().getStringArray("list"));
			}    	
		}
	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String s1 = station_name.getText().toString();
			if ("".equals(s1)) {
			} else {
				database = SQLiteDatabase.openOrCreateDatabase(
						DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
				//p = Pattern.compile("[0-9]*");
				//Matcher m = p.matcher(s1);
				listItems = new ArrayList<HashMap<String, Object>>();
					Cursor cur2 = database.rawQuery(
							"SELECT DISTINCT xid,zhan from cnbus where zhan like '%"
									+ s1 + "%'", null);
					while (cur2.moveToNext()) {
						String station = cur2.getString(cur2
								.getColumnIndex("zhan"));
						String line = cur2
								.getString(cur2.getColumnIndex("xid"))+"路";
						//System.out.println(line + "路" + station);
						int j=cur2.getColumnCount();
						//System.out.println(j);
						
						
						HashMap<String, Object> map = new HashMap<String, Object>();
						for(int i=0;i<j;i++){
	            		map.put("ItemTitle",line);
	            		map.put("ItemSummary",station);}
	                	listItems.add(map); }
	                	listItemAdapter = new SimpleAdapter(getActivity(),listItems,   // listItems数据源    
	                            R.layout.list_item_line_stops,  //ListItem的XML布局实现  
	                            new String[] {"ItemTitle", "ItemSummary"},     //动态数组与ImageItem对应的子项         
	                            new int[ ] {R.id.textView1, R.id.textView2}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID  
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
	public int  isWifi(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		
		if (networkinfo == null || !networkinfo.isAvailable()) {			
			return 0;
		}else			
		{
			
			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
				if (!wifi) { // 提示使用wifi
					return 2;//非wifi
				}else{					
					return 1;//wifi
				}					
			}else
			{
				return 3;
			}
					
		}
		
		
	}
	
}


