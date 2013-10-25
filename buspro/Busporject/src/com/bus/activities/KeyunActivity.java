package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class KeyunActivity extends Activity {

	ListView plane_list;//�б�
	private ArrayList<HashMap<String, String>>   listItems;
	private SimpleAdapter listItemAdapter; 
	Resources res;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_keyun);
		
		Intent intent1 = getIntent();//�������
		//String [] zpnames=intent1.getStringArrayExtra("zpnames");	
		
		String title=intent1.getStringExtra("title");
		TextView title2 = (TextView) findViewById(R.id.commonTextViewTitle);
        title2.setText("������"+title);
		String [] kuaike=intent1.getStringArrayExtra("kuaike");	
		String [] puke=intent1.getStringArrayExtra("puke");	
		plane_list = (ListView)this.findViewById(R.id.plane_list);	
		initListView(kuaike,puke);
		
		plane_list.setAdapter(listItemAdapter);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.keyun, menu);
		return true;
	}
	
	private void initListView(String [] kuaike,String [] puke)   { 
		//String[] array = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i" };
	    listItems = new ArrayList<HashMap<String, String>>();
	        //HashMap<String, Object> map = new HashMap<String, Object>(); 
	        
	    	if(kuaike!=null){
		    	for(int i=0;i<kuaike.length;i++)    {
		        	
		        	String[] kuaikeList = kuaike[i].split(",");
		        	if(kuaikeList.length>3){
		        	
			        	HashMap<String, String> map = new HashMap<String, String>();
			        		map.put("leixing","���");
			        		map.put("banci",kuaikeList[1]);
			        		map.put("shijian",kuaikeList[2]);
			        		map.put("piaojia",kuaikeList[3]);
			        		
			        		listItems.add(map); 
		        	}      	
		        	
		        }
	    	}
	    	
	    	
	    	

	    	if(puke!=null){
		    	for(int i=0;i<puke.length;i++)    {
		        	
		    		 String[] pukeList = puke[i].split(",");
		         	if(pukeList.length>3){
		         	
		 	        	HashMap<String, String> map = new HashMap<String, String>();
		 	        		map.put("leixing","�տ�");
		 	        		map.put("banci",pukeList[1]);
		 	        		map.put("shijian",pukeList[2]);
		 	        		map.put("piaojia",pukeList[3]);
		 	        		
		 	        		listItems.add(map); 
		         	}
		        	
		        }
	    	}        
	             
	        
	        
	        
	    //������������Item�Ͷ�̬�����Ӧ��Ԫ��   
	    listItemAdapter = new SimpleAdapter(this,listItems,   // listItems����Դ    
	            R.layout.plane_list_item,  //ListItem��XML����ʵ��  
	            new String[] {"leixing", "banci","shijian","piaojia"},     //��̬������ImageItem��Ӧ������         
	            new int[ ] {R.id.dst, R.id.price,R.id.shijian,R.id.tvsina}      //list_item.xml�����ļ������һ��ImageView��ID,һ��TextView ��ID  
	    ); 
	
	}
	
	

}
