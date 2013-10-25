package com.bus.activities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logic.UrlUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import adapter.ShopListAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.bus.activities.RibbonMenuView;
import com.bus.activities.iRibbonMenuCallback;

public class ChannelQueryInfo extends SherlockActivity implements iRibbonMenuCallback{
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		menu.add("分类筛选")
        .setIcon(R.drawable.icon_actionbar_list)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		return super.onCreateOptionsMenu(menu);
	}
	String item_click;
	String id;
	int item_num;
	String itemtitle;
	String ad_food_title;
	Resources res;
	ListView ad_list=null;
	int ad_title_num;
	String []rs2;//存储分类的数组
	
	Button btn_back;
    private ArrayList<HashMap<String, Object>>   listItems;    //存放文字、图片信息
    //private SimpleAdapter listItemAdapter;           //适配器
    private ShopListAdapter shopListAdapter;//适配器
    private RibbonMenuView rbmView;
    //private String[] mLocations;
    String []rs;
    String []menulist;
    private View viewDe = null;
    private static final int MSG_SUCCESS = 0;//获取线程信息成功的标识  
    private static final int MSG_FAILURE = 1;//获取线程信息失败的标识  
    ProgressBar progressbar;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.channel_query_info);
        //Log.d("MainActivity", "total items: " + tableView.getCount());
		rbmView = (RibbonMenuView) findViewById(R.id.ribbonMenuView1);
        rbmView.setMenuClickCallback(this);
        progressbar = (ProgressBar)findViewById(R.id.progressBar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
		ad_list = (ListView) findViewById(R.id.ad_list);
		
		Bundle bundle = this.getIntent().getExtras(); 
		item_click = bundle.getString("index");
		id = bundle.getString("id");
		res=getResources();

		item_num = res.getIdentifier(item_click,"string",getPackageName());
		//System.out.println(item_num);
		
		String title = res.getString(item_num);
		getSupportActionBar().setTitle(title);
		String s1=res.getString(res.getIdentifier(id,"string",getPackageName()));
		//System.out.println("s1"+s1);
		initListView(s1);//传递	ID
		
		//String []rs2={"中餐","快餐" };
		
        //rbmView.setMenuItems(rs2);//此处的rs即传人的string[]数组，我用的商户的那个数组，可以改成菜单的。传入之后就可以显示
		initMenu(s1);
		//progressbar.setVisibility(View.GONE);
        
		
		ad_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//设置点击监听，点击后下方出现四个按钮
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				if (viewDe == null) {
                    viewDe = view.findViewById(R.id.toolbar);
                } else {
                    ExpandAnimation expandAni = new ExpandAnimation(viewDe, 500);
                    viewDe.startAnimation(expandAni);
                    if (viewDe == view.findViewById(R.id.toolbar)) {
                        viewDe = null;
                        return;
                    }
                    viewDe = view.findViewById(R.id.toolbar);
                }
 
                ExpandAnimation expandAni = new ExpandAnimation(viewDe, 500);
 
                viewDe.startAnimation(expandAni);
            }
		});
		/**
		mLocations = getResources().getStringArray(R.array.locations);

	    Context context = getSupportActionBar().getThemedContext();
	    ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.locations, R.layout.sherlock_spinner_item);
	    list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

	    getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
	    //getSupportActionBar().setListNavigationCallbacks(list, this);
	     * 
	     */
    }
	
	
	private void initMenu(String idstr)   { 
        //listItems = new ArrayList<HashMap<String, Object>>();
        
        final String url="http://mobile.bizinfocus.com/visitor/getColList.php";			
		
		final UrlUtil uu=new UrlUtil();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("vwId", idstr));	  
        Runnable runnable2 = new Runnable(){
        	

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String r=uu.doPost(url, params);
				rs2=r.split(";");
				
				mHandler2.obtainMessage(MSG_SUCCESS,rs2).sendToTarget();
			}
        };
        Thread workerThread = new Thread(runnable2);        
		workerThread.start();		
		
        // shopListAdapter=new ShopListAdapter(this, rs);
		  
    }
	
	
	private void initListView(String idstr)   { 
        listItems = new ArrayList<HashMap<String, Object>>();
        
        //String url="http://221.202.84.168:8080/handbus/searchShopList";			
        final String url="http://mobile.bizinfocus.com/visitor/getCustomerList.php";
		final UrlUtil uu=new UrlUtil();
        final List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("nodeId", idstr));	  
					
		
	    Runnable runnable = new Runnable(){
        	

			@Override
			public void run() {
				// TODO Auto-generated method stub
				String r=uu.doPost(url, params);
				//System.out.println(r);
			    rs=  r.split(";");
				mHandler.obtainMessage(MSG_SUCCESS,rs).sendToTarget();
			}
        };
        Thread workerThread = new Thread(runnable);        
		workerThread.start();
            
    }
	private Handler mHandler2 = new Handler() {  
        @Override
		public void handleMessage (Message msg) {//此方法在ui线程运行  
            switch(msg.what) {  
            case MSG_SUCCESS:  
            	if(rs2.length>1){
            		rbmView.setMenuItems(rs2);}
                break;  
  
            case MSG_FAILURE:  
                //Toast.makeText(getApplication(), getApplication().getString(R.string.get_pic_failure), Toast.LENGTH_LONG).show();  
                break;  
            }  
        }  
    };  
    private Handler mHandler = new Handler() {  
        @Override
		public void handleMessage (Message msg) {//此方法在ui线程运行  
            switch(msg.what) {  
            case MSG_SUCCESS:  
            	shopListAdapter=new ShopListAdapter(getApplicationContext(), rs);
            	View footView=getLayoutInflater().inflate(R.layout.empty_layout, null);
        		ad_list.addFooterView(footView);
            	ad_list.setAdapter(shopListAdapter);
            	progressbar.setVisibility(View.GONE);
                break;  
  
            case MSG_FAILURE:  
               // Toast.makeText(getApplication(), getApplication().getString(R.string.get_pic_failure), Toast.LENGTH_LONG).show();  
                break;  
            }  
        }  
    };  
	
	@Override
	public void RibbonMenuItemClick(int position) {
		// TODO Auto-generated method stub
		 String url="http://mobile.bizinfocus.com/visitor/getCustomerList.php";
			UrlUtil uu=new UrlUtil();
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        
	        String idstr =rs2[position].split(",")[0];
	        
	        params.add(new BasicNameValuePair("nodeId", idstr));	  
						
			String r=uu.doPost(url, params);		
		    rs=  r.split(";");
	        shopListAdapter=new ShopListAdapter(this, rs); 
	        ad_list.setAdapter(shopListAdapter);
	}
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case android.R.id.home:
			ChannelQueryInfo.this.finish();            
	         return true;
		case 0:
			rbmView.toggleMenu();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	

	/**
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// TODO Auto-generated method stub
		return false;
	}**/
}
