package com.bus.activities;

import java.util.ArrayList;
import java.util.List;

import logic.UrlUtil;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import adapter.ShopListAdapter;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

public class AgentQueryFragment extends Fragment{
	String item_click;
	int item_num;
	String itemtitle;
	String ad_food_title;
	Resources res;
	CornerListView ad_list=null;
	int ad_title_num;
    //private ArrayList<HashMap<String, Object>>   listItems;    //������֡�ͼƬ��Ϣ
    //private SimpleAdapter listItemAdapter;           //������   
    private ShopListAdapter shopListAdapter;//������
    String []rs;
    
    private static final int MSG_SUCCESS = 0;//��ȡ�߳���Ϣ�ɹ��ı�ʶ  
    private static final int MSG_FAILURE = 1;//��ȡ�߳���Ϣʧ�ܵı�ʶ  
    private View viewDe = null;
    private View rootView = null;
  
    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    rootView = inflater.inflate(R.layout.agent_info,
                container, false);
	        //Log.d("MainActivity", "total items: " + tableView.getCount());
			ad_list = (CornerListView) rootView.findViewById(R.id.ad_list);
			res=getResources();
			
			initListView();
			//ad_list.setAdapter(listItemAdapter);
			ad_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				//���õ��������������·������ĸ���ť
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					if (viewDe == null) {
	                    viewDe = rootView.findViewById(R.id.toolbar);
	                } else {
	                    ExpandAnimation expandAni = new ExpandAnimation(viewDe, 100);
	                    viewDe.startAnimation(expandAni);
	                    if (viewDe == rootView.findViewById(R.id.toolbar)) {
	                        viewDe = null;
	                        return;
	                    }
	                    viewDe = rootView.findViewById(R.id.toolbar);
	                }
	 
	                ExpandAnimation expandAni = new ExpandAnimation(viewDe, 100);
	 
	                viewDe.startAnimation(expandAni);
	            }
			});
			return rootView;
		}
	
		private void initListView()   { 
	        //listItems = new ArrayList<HashMap<String, Object>>();
	        
	        //String url="http://221.202.84.168:8080/handbus/searchShopList";			
	        final String url="http://mobile.bizinfocus.com/visitor/getCustomerList.php";
			final UrlUtil uu=new UrlUtil();
	        final List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("nodeId", "76"));	  
						
			
		    Runnable runnable = new Runnable(){
	        	
	
				@Override
				public void run() {
					// TODO Auto-generated method stub
					String r=uu.doPost(url, params);		
				    rs=  r.split(";");
					mHandler.obtainMessage(MSG_SUCCESS,rs).sendToTarget();
				}
	        };
	        Thread workerThread = new Thread(runnable);        
			workerThread.start();
	            
	    }
		
		
		private Handler mHandler = new Handler() {  
	        @Override
			public void handleMessage (Message msg) {//�˷�����ui�߳�����  
	            switch(msg.what) {  
	            case MSG_SUCCESS:  
	            	shopListAdapter=new ShopListAdapter(getActivity().getApplicationContext(),rs  );
	            	ad_list.setAdapter(shopListAdapter);
	                break;  
	  
	            case MSG_FAILURE:  
	               // Toast.makeText(getApplication(), getApplication().getString(R.string.get_pic_failure), Toast.LENGTH_LONG).show();  
	                break;  
	            }  
	        }  
	    };  
}

