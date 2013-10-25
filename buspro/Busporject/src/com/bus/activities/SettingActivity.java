package com.bus.activities;


import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;


public class SettingActivity extends SherlockActivity{
	ListView setting_list;
	Resources res;
	private ArrayList<HashMap<String, Object>>   listItems;    //������֡�ͼƬ��Ϣ
    private SimpleAdapter listItemAdapter;           //������   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        res=getResources();
        setting_list = (ListView)findViewById(R.id.setting_list);
        initListView();
        setting_list.setAdapter(listItemAdapter);
        setting_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				if(position==0){
					Intent intent = new Intent(); 
			        intent.setClass(SettingActivity.this, AboutActivity.class);
			        
			        //��������һ��Activity 
			        SettingActivity.this.startActivity(intent); 
				}
				if(position == 2){
					Intent intent = new Intent(); 
					intent.setClass(SettingActivity.this, LocationCountryActivity.class);
			        intent.putExtra("parentId", 1);
			        //��������һ��Activity 
			        SettingActivity.this.startActivity(intent); 
				}
				
			}

			
			
		});
	}
	private void initListView()   { 
        listItems = new ArrayList<HashMap<String, Object>>();
            for(int i=0;i<3;i++)    {
            	HashMap<String, Object> map = new HashMap<String, Object>();
            		map.put("ItemTitle",res.getString(res.getIdentifier("setting_option_"+i, "string", getPackageName())));
            		//map.put("ItemSummary",res.getString(res.getIdentifier("setting_option"+i, "string", getPackageName())));
                	map.put("ItemImage",res.getIdentifier("setting_option_img"+i, "drawable", getPackageName()));
                	//System.out.println(res.getIdentifier("setting_option_img"+i, "drawable", getPackageName()));
                	map.put("ItemImage2",res.getIdentifier("chevron_default", "drawable", getPackageName()));
                	listItems.add(map); 
            }
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��   
        listItemAdapter = new SimpleAdapter(this,listItems,   // listItems����Դ    
                R.layout.setting_list_item,  //ListItem��XML����ʵ��  
                new String[] {"ItemTitle","ItemImage","ItemImage2"},     //��̬������ImageItem��Ӧ������         
                new int[ ] {R.id.list_title,R.id.list_img,R.id.list_img_chevron}      //list_item.xml�����ļ������һ��ImageView��ID,һ��TextView ��ID  
        );   
    }
	
}	
