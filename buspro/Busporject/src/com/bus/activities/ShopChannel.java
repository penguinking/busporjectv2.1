package com.bus.activities;



import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ShopChannel extends Activity{
	String title;
	ListView cat_list;
	Resources res;
	private ArrayList<HashMap<String, Object>>   listItems;    //������֡�ͼƬ��Ϣ
    private SimpleAdapter listItemAdapter;           //������   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_list);
        TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
        title.setText("����Ƶ��");
        cat_list = (ListView) findViewById(R.id.category_list);
        res=getResources();
        initListView();
		cat_list.setAdapter(listItemAdapter);
		cat_list.setOnItemClickListener(new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				//System.out.println(id);
				Intent intent = new Intent(); 
		        intent.setClass(ShopChannel.this, ChannelQueryInfo.class);
		        Bundle bundle = new Bundle();
		        String index_click = "shop_tv_item"+id;
		        String id_click = "shop_tv_id"+id;
		        bundle.putString("index",index_click);
		        bundle.putString("id",id_click);//����ID
		        intent.putExtras(bundle);
		        
		        //��������һ��Activity 
		        ShopChannel.this.startActivity(intent);
		        Intent intent2 = new Intent(); 
		        intent.setClass(ShopChannel.this, RibbonMenuView.class);
		        Bundle bundle2 = new Bundle();
		        String id_click2 = "shop_tv_id"+id;
		        bundle.putString("id",id_click2);//����ID
		        intent2.putExtras(bundle2);

			}

			
			
		});
        //Log.d("MainActivity", "total items: " + tableView.getCount());
        
    }
	private void initListView()   { 
        listItems = new ArrayList<HashMap<String, Object>>();
            for(int i=0;i<9;i++)    {
            	HashMap<String, Object> map = new HashMap<String, Object>();
            		map.put("ItemTitle",res.getString(res.getIdentifier("shop_tv_item"+i, "string", getPackageName())));
            		map.put("ItemSummary",res.getString(res.getIdentifier("shop_tv_summary"+i, "string", getPackageName())));
                	map.put("ItemImage",res.getIdentifier("shop_tv_img"+i, "drawable", getPackageName()));
                	map.put("ItemImage2",res.getIdentifier("chevron_default", "drawable", getPackageName()));
                	listItems.add(map); 
            }
        //������������Item�Ͷ�̬�����Ӧ��Ԫ��   
        listItemAdapter = new SimpleAdapter(this,listItems,   // listItems����Դ    
                R.layout.common_channel_list_item,  //ListItem��XML����ʵ��  
                new String[] {"ItemTitle", "ItemSummary","ItemImage","ItemImage2"},     //��̬������ImageItem��Ӧ������         
                new int[ ] {R.id.list_title, R.id.list_summary,R.id.list_img,R.id.list_img_chevron}      //list_item.xml�����ļ������һ��ImageView��ID,һ��TextView ��ID  
        );   
    }
	
	private long exitTime = 0;
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK 
                &&event.getAction() == KeyEvent.ACTION_DOWN){   
            if((System.currentTimeMillis()-exitTime)>2000){  
                Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();                                
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
