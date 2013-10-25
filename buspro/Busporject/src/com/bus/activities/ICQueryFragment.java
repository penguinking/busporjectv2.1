package com.bus.activities;

import com.loopj.android.image.SmartImageView;
import com.loopj.android.image.WebImageCache;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class ICQueryFragment extends Fragment{
	String icnum="";
	private static WebImageCache webImageCache;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
	    View rootView = inflater.inflate(R.layout.query_ic,
                container, false);
	    ImageButton btn_ic = (ImageButton) rootView.findViewById(R.id.qs_btn_search);
	    final EditText ic_number =(EditText) rootView.findViewById(R.id.lqc_queryParam);
	    SmartImageView channelAd = (SmartImageView) rootView.findViewById(R.id.common_ad);
	    if(webImageCache == null) {
            webImageCache = new WebImageCache(getActivity());
        }
	    int statu_wifi = util.IsWIFI.isWifi(getActivity());
	    if(statu_wifi==1){
	    	channelAd.setImageUrl("http://mobile.bizinfocus.com/visitor/ad/busChannel/3.png");
	    }else{
	    	
	    	Bitmap bitmap = null;
	    	bitmap=webImageCache.get("http://mobile.bizinfocus.com/visitor/ad/busChannel/3.png");
	    	channelAd.setImageBitmap(bitmap);
	    }
	   btn_ic.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String [] items=new String[]{"普通卡","月票卡","小学生卡","老年优惠卡"};
				icnum = ic_number.getText().toString();
				AlertDialog.Builder builder=new Builder(getActivity());
				builder.setTitle("请选择公交卡类型");
				builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();
						//builder.
						
						int x=7-icnum.length();
						String b0="";
						for(int i=0;i<x;i++)
						{
							b0+="0";			
						}
				        
						String t="";
						if(which==0)
						{
							t="00";
						}
						if(which==1)
						{
							t="02";
						}
						if(which==2)
						{
							t="03";
						}
						if(which==3){
							t="06";
						}
						Thread workerThread = new Thread(new logic.ICKLogic(new MyHandlerICK(),t+b0+icnum));        
						workerThread.start();
					}
				});
				builder.show();
	    	
			}
	    });
	    return rootView;
}
	class MyHandlerICK extends Handler {   
    	@Override
    	public void handleMessage(Message msg) {			       
			// 更新UI
    		showIck(msg.getData().getString("info"));
    	}    	
    }
	public void showIck(String info)
	{
		
		if(info.trim().equals(""))
		{
			info="无此卡号!";
			
		}
		final Dialog dialog = new Dialog(getActivity(),R.style.MyDialogStyle);
		LayoutInflater inflater = (LayoutInflater)          
		        getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);         
		        View layout = inflater.inflate(R.layout.ic_dialog,null); 
				dialog.setContentView(layout);
		        TextView text = (TextView) layout.findViewById(R.id.IC_remainder);         
		        text.setText(info);
		        dialog.show();
		        Button btn_confirm = (Button) layout.findViewById(R.id.dialog_button_confirm);
		        btn_confirm.setOnClickListener(new OnClickListener(){

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
		        	
		        });
		        
		 /**       
		 new AlertDialog.Builder(getActivity())  
	        .setIcon(R.drawable.bus)  
	        .setTitle("IC卡余额查询 ") 
	        .setView(layout)
	        .setPositiveButton("确定", new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                // TODO Auto-generated method stub  
	            }  
	        }).show(); **/
		
	}
}