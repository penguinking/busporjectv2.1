package com.bus.activities;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Pattern;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.image.WebImageCache;

import logic.RechangeLogic;
import logic.StationLogic2;



import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
/*
 * 换乘查询
 */
public class TransFragment extends BaseFragment {

	EditText editStationBegin;
	EditText editStationEnd;
	TextView txtResult;
	TextView title;
	private adapter.StationAdapter adapter;//适配器 
	ListView listview1;
	ListView listview2;
	Button btnquery;
	ImageButton btnstart;
	ImageButton btnend;
	public DBManager dbHelper;
	private Pattern p;
	private ArrayList<HashMap<String, Object>>   listItems;
	private SimpleAdapter listItemAdapter;
	private SQLiteDatabase database;
	private static WebImageCache webImageCache;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View rootView = inflater.inflate(R.layout.trans,
                container, false);
	    SmartImageView channelAd = (SmartImageView) rootView.findViewById(R.id.common_ad);
	    if(webImageCache == null) {
            webImageCache = new WebImageCache(getActivity());
        }
	    int statu_wifi = util.IsWIFI.isWifi(getActivity());
	    if(statu_wifi==1){
	    	channelAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/busChannel/4.png");
	    }else{
	    	Bitmap bitmap = null;
	    	bitmap=webImageCache.get("http://mobile.bizinfocus.com/visitor/ad/busChannel/4.png");
	    	channelAd.setImageBitmap(bitmap);
	    }
		
	    editStationBegin=(EditText) rootView.findViewById(R.id.trans_start);
		editStationEnd=(EditText) rootView.findViewById(R.id.trans_end);
		txtResult=(TextView) rootView.findViewById(R.id.txtResult);	
		//模糊搜索1
		listview1= (ListView) rootView.findViewById(R.id.listView1);
		listview1.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {								
				 String str = (String) ((TextView) (view.findViewById(R.id.lis_stopname))).getText();				 
				 editStationBegin.setText(str);				 
				 showSelectList(listview1,false);//显示
			}		
		}			
	    );		
		
		//模糊搜索2
		listview2= (ListView) rootView.findViewById(R.id.listView2);
		listview2.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {								
				 String str = (String) ((TextView) (view.findViewById(R.id.lis_stopname))).getText();				 
				 editStationEnd.setText(str);				 
				 showSelectList(listview2,false);//显示
			}		
		}			
	    );
		btnquery = (Button) rootView.findViewById(R.id.trans_query);
		btnstart = (ImageButton) rootView.findViewById(R.id.trans_p1);
		btnend = (ImageButton) rootView.findViewById(R.id.trans_p2);
		btnquery.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String stationBegin=editStationBegin.getText().toString();
				String stationEnd=editStationEnd.getText().toString();
				
				if(stationBegin.equals("")){
					Toast.makeText(getActivity(), "请输入出发地", Toast.LENGTH_SHORT).show();
					//return ;
				}
				
				if(stationEnd.equals("")){
					Toast.makeText(getActivity(), "请输入到达地", Toast.LENGTH_SHORT).show();	
					//return ;
				}else
				
				Toast.makeText(getActivity(), "正在查询，请稍后...", Toast.LENGTH_SHORT).show();
				
				Thread workerThread = new Thread(new RechangeLogic(new MyHandler(),stationBegin,stationEnd));        
				workerThread.start();
			}
			
		});
		btnstart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search1(arg0);
			}
			
		});
		btnend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				search2(arg0);
			}
			
		});
		editStationBegin.addTextChangedListener(textWatcher1);
		editStationEnd.addTextChangedListener(textWatcher2);
	    dbHelper = new DBManager(getActivity());
		dbHelper.openDatabase();
		dbHelper.closeDatabase();
		return rootView;
	}
		/**
		SharedPreferences shared=getSharedPreferences("ad",Context.MODE_PRIVATE);
		ImageView  bg1=  (ImageView) rootView.findViewById(R.id.view_bg1);	
		new DownloadTask(bg1)
		.execute(Config.imgurl+shared.getString("ad_3", ""));
		
		ImageView  bg2=  (ImageView) rootView.findViewById(R.id.view_bg2);	
		new DownloadTask(bg2)
		.execute(Config.imgurl+shared.getString("ad_4", ""));
		
		ImageView  bg3=  (ImageView) rootView.findViewById(R.id.view_bg3);	
		new DownloadTask(bg3)
		.execute(Config.imgurl+shared.getString("ad_5", ""));
		
	}**/


	//public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_trans, menu);
		//return true;
	//}
	
	/**
	public void exchange_search(View v)
	{
		
			
			String stationBegin=this.editStationBegin.getText().toString();
			String stationEnd=this.editStationEnd.getText().toString();
			
			if(stationBegin.equals("")){
				Toast.makeText(getActivity(), "请输入出发地", 1);
				return ;
			}
			
			if(stationEnd.equals("")){
				Toast.makeText(getActivity(), "请输入目的地", 1);	
				return ;
			}
			
			this.txtResult.setText("正在查询请等候......");		
			
			Thread workerThread = new Thread(new RechangeLogic(new MyHandler(),stationBegin,stationEnd));        
			workerThread.start();		
		}**/
	
	
	
	
	/*
	 * 出发地站盘
	 */
	public void search1(View v)
	{
		
		if(note_Intent(getActivity())){
		
			closeInputWindow();			
			String stationBegin=this.editStationBegin.getText().toString();				
			Thread workerThread = new Thread(new StationLogic2(new MyHandler1(),stationBegin));        
			workerThread.start();
		}
		
	}
	
	private void closeInputWindow()
	{
		//关闭输入法
		InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(  Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
		InputMethodManager.HIDE_NOT_ALWAYS);	
		
	}
	
	/*
	 * 出发地站盘
	 */
	public void search2(View v)
	{
		if(note_Intent(getActivity())){
			closeInputWindow();
			String stationEnd=this.editStationEnd.getText().toString();					
			Thread workerThread = new Thread(new StationLogic2(new MyHandler2(),stationEnd));        
			workerThread.start();
		}
	}
	
	
	public void appendText(String msg) {        
    	
		txtResult.setText("");
		
		if(msg.equals("error"))
    	{
			this.txtResult.setText("服务器维护中，请稍后访问");		
    		return ;
    	}
		
		
		
    	msg=msg.replace("|", ".#");
    	String str[]=msg.split(".#");
    	
    	
    	
    	
    	if(str[0].equals("一次换乘"))
    	{
    		if(str.length==2){
    			
    			String[]infos= str[1].split("，");
    			if(infos.length==6){    		
    				Intent intent = new Intent(getActivity(),TransInfoActivity.class);//从启动动画ui跳转到主ui
    				intent.putExtra("type", str[0]);
    				intent.putExtra("infos", infos);    				
    				startActivity(intent);
    			}
    		
    		}else
    		{
    			//无法查找到数据     	
    			Toast.makeText(getActivity(), "系统维护中，稍后访问。。。。。。", 1);
    		}    		
    	}
    	
    	if(str[0].equals("二次换乘"))
    	{
    		if(str.length==2){
    			
    			String[]infos= str[1].split("，");
    			if(infos.length==9){    		
    				Intent intent = new Intent(getActivity(),TransInfoActivity.class);//从启动动画ui跳转到主ui
    				intent.putExtra("type", str[0]);
    				intent.putExtra("infos", infos);    				
    				startActivity(intent);
    			}
    		
    		}else
    		{
    			//无法查找到数据      			
    			Toast.makeText(getActivity(), "系统维护中，稍后访问。。。。。。", 1);
    		}    		
    	}
    	
    	if(str[0].equals("直达"))
    	{
    		msg=msg.replace("直达|", "无须换乘，以下线路可直达：");
    		txtResult.setText(msg);
    	}
    	
    	if(!str[0].equals("直达")  &&  !str[0].equals("二次换乘")  && !str[0].equals("一次换乘")){
    		
    		txtResult.setText(str[0]);
    	}
		
    }
    
    class MyHandler extends Handler {        
    	@Override
    	public void handleMessage(Message msg) {			       
			// 更新UI
			appendText(msg.getData().getString("message"));		
    	}    	
    }
    
    public void showList1(String [] msg) {   
    	
    	if(msg[0].equals("error"))
    	{
    		this.txtResult.setText("服务器维护中，请稍后访问");		
    		return ;
    	}
    	
    	if(msg.length>1){
	    	showSelectList(listview1,true);	//显示
			adapter=new adapter.StationAdapter(getActivity(),msg  );
			listview1.setAdapter(adapter);
    	}else
    	{
    		Toast.makeText(getActivity(), "没有找到相关数据", 1);   		
    	}
    	
    }
    
    class MyHandler1 extends Handler {        
    	@Override
    	public void handleMessage(Message msg) {			       
			// 更新UI
    		showList1(msg.getData().getStringArray("list"));		
    	}    	
    }
    
    
    public void showList2(String [] msg) {    
    	if(msg[0].equals("error"))
    	{
    		this.txtResult.setText("服务器维护中，请稍后访问");		
    		return ;
    	}
    	
    	if(msg.length>1){
	    	showSelectList(listview2,true);	//显示
			adapter=new adapter.StationAdapter(getActivity(),msg  );
			listview2.setAdapter(adapter);	
    	}else
    	{
    		Toast.makeText(getActivity(), "没有找到相关数据", 1);   		    		
    	}
    }
    
    class MyHandler2 extends Handler {        
    	@Override
    	public void handleMessage(Message msg) {			       
			// 更新UI
    		showList2(msg.getData().getStringArray("list"));		
    	}    	
    }
    
    
    
    private void showSelectList(ListView listview,boolean x)
    {
		if(x){
			
			listview.setVisibility(View.VISIBLE);
		}else
		{
			//params.height=0;	
			listview.setVisibility(View.GONE);
		}		
		
		//listview.setLayoutParams(params);  	
    	
    }

    
    //语音识别    
    @Override
	public void talk1(View view)
    {
    	
    	try{
    		startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
    	}catch(Exception ex){
    		Toast.makeText(getActivity(), "您现在的安卓系统版本不支持语音识别", 1).show();			
    	}
    }
    //语音识别    
    public void talk2(View view)
    {
    	try{
    		startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE2);
    	}catch(Exception ex){
    		Toast.makeText(getActivity(), "您现在的安卓系统版本不支持语音识别", 1).show();			
    	}
    }
    
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    private static final int VOICE_RECOGNITION_REQUEST_CODE2 = 5678;
    
    private void startVoiceRecognitionActivity(int code) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        
        if(code==VOICE_RECOGNITION_REQUEST_CODE){
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请告诉我您要从哪里出发？");
        }else
        {
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "请告诉我您要去哪里？");
        }
        
        
        startActivityForResult(intent, code);
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        int RESULT_OK = 0;
		if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            //        matches));            
            
            editStationBegin.setText(matches.get(0));
        }
        
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE2 && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            //        matches));            
            editStationEnd.setText(matches.get(0));
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private TextWatcher textWatcher1 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String s1 = editStationBegin.getText().toString();
			if ("".equals(s1)) {
			} else {
				database = SQLiteDatabase.openOrCreateDatabase(
						DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
				//p = Pattern.compile("[0-9]*");
				//Matcher m = p.matcher(s1);
				listItems = new ArrayList<HashMap<String, Object>>();
					Cursor cur2 = database.rawQuery(
							"SELECT DISTINCT zhan from cnbus where zhan like '%"
									+ s1 + "%'", null);
					while (cur2.moveToNext()) {
						String station = cur2.getString(cur2
								.getColumnIndex("zhan"));
						//System.out.println(line + "路" + station);
						int j=cur2.getColumnCount();
						//System.out.println(j);
						
						
						HashMap<String, Object> map = new HashMap<String, Object>();
						for(int i=0;i<j;i++){
	            		map.put("ItemSummary",station);}
	                	listItems.add(map); }
	                	listItemAdapter = new SimpleAdapter(getActivity(),listItems,   // listItems数据源    
	                            R.layout.list_item_stopinfo,  //ListItem的XML布局实现  
	                            new String[] { "ItemSummary"},     //动态数组与ImageItem对应的子项         
	                            new int[ ] {R.id.lis_stopname}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID  
	                    );
	                	showSelectList(listview1,true);	//显示
	                	listview1.setAdapter(listItemAdapter);
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
	private TextWatcher textWatcher2 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			String s1 = editStationEnd.getText().toString();
			if ("".equals(s1)) {
			} else {
				database = SQLiteDatabase.openOrCreateDatabase(
						DBManager.DB_PATH + "/" + DBManager.DB_NAME, null);
				//p = Pattern.compile("[0-9]*");
				//Matcher m = p.matcher(s1);
				listItems = new ArrayList<HashMap<String, Object>>();
					Cursor cur2 = database.rawQuery(
							"SELECT DISTINCT zhan from cnbus where zhan like '%"
									+ s1 + "%'", null);
					while (cur2.moveToNext()) {
						String station = cur2.getString(cur2
								.getColumnIndex("zhan"));
						//System.out.println(line + "路" + station);
						int j=cur2.getColumnCount();
						//System.out.println(j);
						
						
						HashMap<String, Object> map = new HashMap<String, Object>();
						for(int i=0;i<j;i++){
	            		map.put("ItemSummary",station);}
	                	listItems.add(map); }
	                	listItemAdapter = new SimpleAdapter(getActivity(),listItems,   // listItems数据源    
	                            R.layout.list_item_stopinfo,  //ListItem的XML布局实现  
	                            new String[] { "ItemSummary"},     //动态数组与ImageItem对应的子项         
	                            new int[ ] {R.id.lis_stopname}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID  
	                    );
	                	showSelectList(listview2,true);	//显示
	                	listview2.setAdapter(listItemAdapter);
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
