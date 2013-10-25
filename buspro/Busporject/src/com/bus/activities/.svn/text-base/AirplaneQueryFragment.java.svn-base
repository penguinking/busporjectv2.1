package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class AirplaneQueryFragment extends Fragment{
	ListView plane_list;
	private ArrayList<HashMap<String, String>>   listItems;
	private SimpleAdapter listItemAdapter; 
	Resources res;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View rootView = inflater.inflate(R.layout.plane_list,
                container, false);
	    //
	    //String[] array = new String[] { "鄂尔多斯", "徐州", "兰州", "温州", "长沙", "银川", "包头", "昆明", "杭州", "成都", "西安", "南宁", "广州", "深圳", "重庆", "三亚", "海口", "上海", "南京", "宁波", "珠海", "厦门", "汕头", "武汉", "贵阳", "无锡", "乌鲁木齐" };
        //plane_list.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.plane_list_item,
        //       R.id.dst, array));
	    plane_list = (ListView)rootView.findViewById(R.id.plane_list);
	    res=getResources();

	    initListView();

		plane_list.setAdapter(listItemAdapter);
	    return rootView;
	}
	


	private void initListView()   { 
		//String[] array = new String[] { "a", "b", "c", "d", "e", "f", "g", "h", "i" };
	    listItems = new ArrayList<HashMap<String, String>>();
	        //HashMap<String, Object> map = new HashMap<String, Object>(); 
	        for(int i=0;i<27;i++)    {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        		map.put("dst",res.getString(res.getIdentifier("dst_"+i, "string", getActivity().getPackageName())));
	        		map.put("price",res.getString(res.getIdentifier("price_"+i, "string", getActivity().getPackageName())));
	        		listItems.add(map); 
	        }
	    //生成适配器的Item和动态数组对应的元素   
	    listItemAdapter = new SimpleAdapter(getActivity(),listItems,   // listItems数据源    
	            R.layout.plane_list_item,  //ListItem的XML布局实现  
	            new String[] {"dst", "price"},     //动态数组与ImageItem对应的子项         
	            new int[ ] {R.id.dst, R.id.price}      //list_item.xml布局文件里面的一个ImageView的ID,一个TextView 的ID  
	    ); 
	
	}

	class ClickEvent implements OnClickListener {
	    @Override
	    public void onClick (View v)  {
	        // 向ListView里添加一项
	    	for(int i=0;i<27;i++)    {
	        	HashMap<String, String> map = new HashMap<String, String>();
	        		map.put("dst",res.getString(res.getIdentifier("dst_"+i, "string", getActivity().getPackageName())));
	        		map.put("price",res.getString(res.getIdentifier("price_"+i, "string", getActivity().getPackageName())));
	        		listItems.add(map); 
	        }
	    }
	}
}
