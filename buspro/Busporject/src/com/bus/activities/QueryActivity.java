package com.bus.activities;
import java.util.ArrayList;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
/*
 * ��ѯ
 */
public class QueryActivity extends BaseActivity {

	int querytype;//��ѯ���� 
	EditText editRoute;	
	TextView title;
	private adapter.LineStationAdapter adapter;//������ 	
	private ListView listView;
	
	//String[] values;
	String zpname;
	String routeName="";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.querysites);
		/**
		//ͼƬ����
		imageCacheManager = ImageCacheManager.getImageCacheService(this,
				ImageCacheManager.MODE_FIXED_MEMORY_USED, "memory");
		imageCacheManager.setMax_Memory(1024 * 1024);
		
		
		editRoute=(EditText) this.findViewById(R.id.lqc_queryParam);
		title=(TextView)this.findViewById(R.id.commonTextViewTitle);
		title.setText("��·��ѯ");
		querytype=1;//��ѯ����
		listView=(ListView) this.findViewById(R.id.qs_list_cy);**/
		
		
		
		//String sss=
				
		//editRoute.setText(sss);
		
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {								
				
			    			
				routeName = (String) ((TextView) (view.findViewById(R.id.textView1))).getText();	//��·
				routeName=routeName.replace("·", "");
				zpname = (String) ((TextView) (view.findViewById(R.id.textView2))).getText();	//��·
				
				Thread workerThread = new Thread(new logic.RouteLogic(new MyHandler2(),routeName));        
				workerThread.start();
				
				
			}		
		}			
	    );
		listView.setAdapter(adapter);	
		

		
		//File file;
		//��ù����
		
		//file=new File("/sdcard/busimg/1.png");
		/*try{
			Bitmap bm1 = BitmapFactory.decodeFile("/sdcard/busAd/ad1.png");
			if(bm1!=null )
			{
				ImageView  bg1=  (ImageView) this.findViewById(R.id.view_bg1);		
				bg1.setImageBitmap(bm1);	
			}			
		}catch(Exception ex)
		{						
		}*/
		/*
		try {		     
		     byte[] data=NetTool.getImage("http://221.202.84.168:8080/handbus/img/2.png");
		     Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);
		     ImageView  bg1=  (ImageView) this.findViewById(R.id.view_bg1);	
		     bg1.setImageBitmap(bm);
		} catch (Exception e) {
		     //Log.i(TAG, e.toString());
		     //Toast.makeText(DataActivity.this, "���ͼƬʧ��", 1).show();
		}*/
		/*
		SharedPreferences shared=getSharedPreferences("ad",Context.MODE_PRIVATE);
		ImageView  bg1=  (ImageView) this.findViewById(R.id.view_bg1);	
		new DownloadTask(bg1)
		.execute(Config.imgurl+shared.getString("ad_0", ""));
		
		ImageView  bg2=  (ImageView) this.findViewById(R.id.view_bg2);	
		new DownloadTask(bg2)
		.execute(Config.imgurl+shared.getString("ad_1", ""));
		
		ImageView  bg3=  (ImageView) this.findViewById(R.id.view_bg3);	
		new DownloadTask(bg3)
		.execute(Config.imgurl+shared.getString("ad_2", ""));
		
		View vi= QueryActivity.this.findViewById(R.id.main_ads);
		vi.setVisibility(0);
		*/
		//�󶨽����¼�
		editRoute.setOnFocusChangeListener(new View.OnFocusChangeListener() {   
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
            	View vi= QueryActivity.this.findViewById(R.id.main_ads);
            	if(hasFocus){//��������ý���                	
                	vi.setVisibility(8);
                     
                }else{
                	vi.setVisibility(0);
                }
            }
        });
		
		
		note_Intent(this);//�������
		
	}

	
	//���򳵲�ѯҳ��
	private void gotoRouteInfo(String[] str){
		Intent intent = new Intent(QueryActivity.this,RoutelInfoActivity.class);  //����������ui��ת����ui
		intent.putExtra("zpname", zpname);
		intent.putExtra("zpnames", str);
		intent.putExtra("xl",routeName );
		
		startActivity(intent);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.activity_query, menu);
		return true;
	}
	
	
	/*
	 * ��ѯ
	 */
	public void search(View v){
		if(note_Intent(this)){
			
			routeName= editRoute.getText().toString();			
			if(querytype==1)
			{				
				Thread workerThread = new Thread(new logic.RouteLogic(new MyHandler(),routeName));        
				workerThread.start();
			}else if(querytype==2)
			{
				Thread workerThread = new Thread(new logic.StationLogic(new MyHandler(),routeName));        
				workerThread.start();			
			}else{
				
				final String [] items=new String[]{"��ͨ��","��Ʊ��","Сѧ����"};
				AlertDialog.Builder builder=new Builder(this);
				
				builder.setTitle("��ѡ�񹫽�������");
				builder.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
						dialog.dismiss();
						//builder.
						
						int x=7-routeName.length();
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
						
						Thread workerThread = new Thread(new logic.ICKLogic(new MyHandlerICK(),t+b0+routeName));        
						workerThread.start();	
												
					}
				}  );
				
				builder.show();				
			}
			
		}
		
	}
		
	
	/*
	 * ��ѯ��·(ѡ��1)
	 */
	public void query1(View v){
		this.findViewById(R.id.main_querytypeimg1).setVisibility(View.VISIBLE);		
		this.findViewById(R.id.main_querytypeimg2).setVisibility(View.INVISIBLE);	
		this.findViewById(R.id.main_querytypeimg3).setVisibility(View.INVISIBLE);	
		editRoute.setHint("��������·");
		title.setText("��·��ѯ");
		editRoute.setText("");
		querytype=1;
	}
	
	/*
	 * ��ѯվ��(ѡ��2)
	 */
	public void query2(View v){
		this.findViewById(R.id.main_querytypeimg1).setVisibility(View.INVISIBLE);		
		this.findViewById(R.id.main_querytypeimg2).setVisibility(View.VISIBLE);	
		this.findViewById(R.id.main_querytypeimg3).setVisibility(View.INVISIBLE);	
		editRoute.setHint("������վ��");
		title.setText("վ���ѯ");
		editRoute.setText("");
		querytype=2;
	}
	
	/*
	 * ��ѯվ��(ѡ��3)
	 */
	public void query3(View v){
		this.findViewById(R.id.main_querytypeimg1).setVisibility(View.INVISIBLE);		
		this.findViewById(R.id.main_querytypeimg2).setVisibility(View.INVISIBLE);	
		this.findViewById(R.id.main_querytypeimg3).setVisibility(View.VISIBLE);	
		editRoute.setHint("�����빫������");
		title.setText("����������ѯ");
		editRoute.setText("");
		querytype=3;
	}
	
	public void showIck(String info)
	{
		
		if(info.trim().equals(""))
		{
			info="�޴˿���!";
			
		}
		
		LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);         
        View layout = inflater.inflate(R.layout.activity_query,null);         
        TextView text = (TextView) layout.findViewById(R.id.search);         
        text.setText(info);
		
		        
		        
		 new AlertDialog.Builder(QueryActivity.this)  
	        .setIcon(R.drawable.bus)  
	        .setTitle("IC������ѯ ") 
	        .setView(layout)
	        .setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {  
	            @Override  
	            public void onClick(DialogInterface dialog, int which) {  
	                // TODO Auto-generated method stub  
	            }  
	        }).show();  
		
	}
	
	
	public void showList(String[] str) { 
		
		//�ر����뷨
		InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(  Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(QueryActivity.this.getCurrentFocus().getWindowToken(),
		InputMethodManager.HIDE_NOT_ALWAYS);	
		
		if(str.length<1)
		{
			Toast.makeText(this, "δ�鵽��¼", 1).show();			
		}else
		{			
			//values=str;
		
			if(querytype==1)
			{
				Intent intent = new Intent(QueryActivity.this,LineInfoActivity.class);  //����������ui��ת����ui	   
				
				intent.putExtra("zpnames", str);
				intent.putExtra("xl",routeName );
				
				startActivity(intent);
			}else
			{
				adapter=new adapter.LineStationAdapter(this,str  );
				listView.setAdapter(adapter);
			}
			
			
		}
	}
    
    class MyHandler extends Handler {   
    	@Override
    	public void handleMessage(Message msg) {			       
			// ����UI
    		showList(msg.getData().getStringArray("list"));
    	}    	
    }
    
    
    //IC��
    class MyHandlerICK extends Handler {   
    	@Override
    	public void handleMessage(Message msg) {			       
			// ����UI
    		showIck(msg.getData().getString("info"));
    	}    	
    }
    
    class MyHandler2 extends Handler {   
    	@Override
    	public void handleMessage(Message msg) {			       
			// ����UI
    		gotoRouteInfo(msg.getData().getStringArray("list"));
    	}    	
    }

    
    //����ʶ��    
    public void talk1(View view)
    {
    	
    	try{
    		startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
    	}catch(Exception ex){
    		Toast.makeText(this, "�����ڵİ�׿ϵͳ�汾��֧������ʶ��", 1).show();			
    	}
    }
      
    private static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
        
    private void startVoiceRecognitionActivity(int code) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        
        if(code==VOICE_RECOGNITION_REQUEST_CODE){
        	intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "���������Ҫ�����������");
        }        
        
        startActivityForResult(intent, code);
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it could have heard
            ArrayList<String> matches = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            //mList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
            //        matches));            
            
            editRoute.setText(matches.get(0));
        }
        
      

        super.onActivityResult(requestCode, resultCode, data);
    }
    
    
    
   

}
