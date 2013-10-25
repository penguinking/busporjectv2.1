package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import adapter.CityListAdapter;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;

public class LocationCountryActivity extends SherlockActivity {
	ListView location_list;
	Resources res;
	private ArrayList<HashMap<String, Object>> listItems; // ������֡�ͼƬ��Ϣ
	private CityListAdapter listItemAdapter; // ������
	
	public DBManager dbHelper;
	String city;
	String state;
	
	int parentId;//����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location_setting);
		TextView title = (TextView) findViewById(R.id.commonTextViewTitle);
		title.setText("ѡ�������ڳ��У�������");
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(false);
		location_list = (ListView) findViewById(R.id.location_list);
		res = getResources();
		//Bundle bundle = this.getIntent().getExtras();
		parentId =1;// bundle.getInt("parentId");
				
		initListView(parentId,"");
		
		location_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v,
					int position, long id) {
								
				TextView tv= (TextView)v.findViewById(R.id.list_title);				
				parentId=  Integer.parseInt((String)tv.getTag());
				city=(String) tv.getText();				
				initListView(parentId,city);		
			}

		});
		
		// Log.d("MainActivity", "total items: " + tableView.getCount());

	}

	private void initListView(int parentId,String name) {
		listItems = new ArrayList<HashMap<String, Object>>();
		List list=  logic.CityLogic.getCityList(LocationCountryActivity.this, parentId);
		
		SharedPreferences shared = getSharedPreferences("city",Context.MODE_PRIVATE);
		int cityId=shared.getInt("cityId",0);
		
		if(list.size()>0)
		{
			listItemAdapter=new CityListAdapter(LocationCountryActivity.this, list,cityId);
			location_list.setAdapter(listItemAdapter);
		}else			
		{			
			 dialog(parentId,name);
		}
		
	}
	
	/*
	 * �洢���õĳ���
	 */
	private void saveCity()
	{
		SharedPreferences shared = getSharedPreferences("city",Context.MODE_PRIVATE);
		Editor editor = shared.edit();

		editor.putInt("cityId",parentId);
		editor.putString("cityName",city);
		
		editor.commit();
		
	}
	
	
	protected void dialog(int parentId ,String cityname) {
		
		
		  AlertDialog.Builder builder = new Builder(LocationCountryActivity.this);
		  builder.setMessage("ȷ�Ͻ���ǰ��������Ϊ" +  cityname+ "��");

		  builder.setTitle("��ʾ");

		  builder.setPositiveButton("ȷ��", new OnClickListener() {

		   @Override
		   public void onClick(DialogInterface dialog, int which) {
			    dialog.dismiss();			   
			    saveCity();		
			    finish();
		   }
		  });

		  builder.setNegativeButton("ȡ��", new OnClickListener() {

			   @Override
			   public void onClick(DialogInterface dialog, int which) {
				   dialog.dismiss();
			   }
		  });

		  builder.create().show();
		 }
	
	@Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case android.R.id.home:
			LocationCountryActivity.this.finish();            
	         return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	
}
