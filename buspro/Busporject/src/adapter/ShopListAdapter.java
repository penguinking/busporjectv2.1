package adapter;



import com.bus.activities.R;
import com.bus.activities.WebPageViewActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.image.SmartImageView;

public class ShopListAdapter extends BaseAdapter {

	private Context context;//������
	private String [] shopList;//վ�㼯��
	private LayoutInflater inflater;//���������
	private ViewHolder holder;//����ViewHolder��

	
	//���캯��
	public ShopListAdapter(Context context,String [] shopList) {
		this.context = context;
		this.shopList = shopList;
		//ʵ����LayoutInflater
		inflater = LayoutInflater.from(context);
	}	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shopList.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView,ViewGroup parent) {
		// TODO Auto-generated method stub
			convertView = inflater.inflate(R.layout.common_list_item, null);
			holder = new ViewHolder();
			//��ø����ؼ���ʵ��
			holder.txtQuerytitle = (TextView) convertView.findViewById(R.id.querytitle);
			holder.txtQuerysummary= (TextView) convertView.findViewById(R.id.querysummary);
			holder.imgQueryimg=(SmartImageView)convertView.findViewById(R.id.queryimg);
			holder.imgQueryimg_phone=(RadioButton)convertView.findViewById(R.id.dial_btn);
			//holder.imgQueryimg_map=(RadioButton)convertView.findViewById(R.id.map_btn);
			holder.imgQueryimg_info=(RadioButton)convertView.findViewById(R.id.info_btn);
			View toolbar = convertView.findViewById(R.id.toolbar);
			((LinearLayout.LayoutParams) toolbar.getLayoutParams()).bottomMargin = -50;
			toolbar.setVisibility(View.GONE);
			convertView.setTag(holder);					
		String str= shopList[position];
		//��������
		String ss[]=str.split(",");
		if(ss.length>5){
			holder.txtQuerytitle.setText(ss[4]);		
			holder.txtQuerysummary.setText(ss[3]);
			
			//holder.imgQueryimg.setImageResource(R.drawable.ad_list_img);
			holder.imgQueryimg_info.setTag(R.id.tag_1,ss[0]);//������ID
			holder.imgQueryimg_info.setTag(R.id.tag_2,ss[4]);//������ID
			
			
			//holder.imgQueryimg_map.setTag(R.id.tag_1, ss[4]);//����
			//holder.imgQueryimg_map.setTag(R.id.tag_2, ss[3]);//��ַ
			
			if(ss.length>8){
				//holder.imgQueryimg_map.setTag(R.id.tag_3, ss[8]);//γ��
				//holder.imgQueryimg_map.setTag(R.id.tag_4, ss[9]);//γ��
			}
			//holder.imgQueryimg.seti
			
			
			try {		     
			     //byte[] data=NetTool.getImage("http://mobile.bizinfocus.com/"+ss[2]);
			     //Bitmap bm=BitmapFactory.decodeByteArray(data, 0, data.length);			    
			     holder.imgQueryimg.setImageUrl("http://mobile.bizinfocus.com/"+ss[2]);
			     //System.out.println("@@@@http://cybus.bizinfocus.com/"+ss[2]);
			     if(holder.imgQueryimg==null){
			    	 holder.imgQueryimg.setImageResource(R.drawable.ad_list_img);
			     }
			} catch (Exception e) {
			    
			}
			if(ss.length>6){
				holder.imgQueryimg_phone.setTag(ss[6]);
			}
		}else{
			holder.txtQuerytitle.setText("Ƶ�������У���л���Ĺ�ע");
			holder.txtQuerysummary.setText("Ƶ����ϵ�绰:0421-2610873");
			holder.imgQueryimg_phone.setTag("0421-2610873");
		}
		//����绰
		holder.imgQueryimg_phone.setOnClickListener(new View.OnClickListener() {  
            @Override  
            public void onClick(View v) {  
                String phone=(String)v.getTag();
                if(!phone.equals("")){
            	Uri uri=Uri.parse("tel:"+phone.replace(" ", ""));
            	Intent intent=new Intent("android.intent.action.CALL",uri);
            	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            	context.startActivity(intent);
                }else{
                	Toast.makeText(context, "��Ǹ��δ��¼���̻��绰��Ϣ", 1).show();
                }
            	
            }  
        });  
		/**�̻���ͼ��ť
		holder.imgQueryimg_map.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(); 
		        intent.setClass(context, ShopMapInfoActivity.class); 
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        
		        //���Ӳ���
		        intent.putExtra("name", (String)v.getTag(R.id.tag_1));
		        intent.putExtra("address", (String)v.getTag(R.id.tag_2));
		        intent.putExtra("weidu", (String)v.getTag(R.id.tag_3));
		        intent.putExtra("jingdu", (String)v.getTag(R.id.tag_4));
		        
		        context.startActivity(intent);
			}
		});
		**/
		holder.imgQueryimg_info.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
								
				Intent intent = new Intent();
				String id2 = (String)v.getTag(R.id.tag_1);
				if(id2!=null){
		        intent.setClass(context, WebPageViewActivity.class);
		        intent.putExtra("id", (String)v.getTag(R.id.tag_1));
		        intent.putExtra("name", (String)v.getTag(R.id.tag_2));
		        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		        context.startActivity(intent);
		        }else{
		        	Toast.makeText(context, "��Ǹ��δ��¼���̻���ϸ��Ϣ", 1).show();
		        }
				
		        
			}
		});
		//����ֵ
		return convertView;
		
	}
	
	
	
	
	public final class ViewHolder{
		public TextView txtQuerytitle;//�̼����� 
		public TextView txtQuerysummary;//��ַ
		public SmartImageView imgQueryimg;//��ַ
		public RadioButton imgQueryimg_phone;//��ַ
		//public RadioButton imgQueryimg_map;//��ͼ
		public RadioButton imgQueryimg_info;//��Ϣ
	}

}