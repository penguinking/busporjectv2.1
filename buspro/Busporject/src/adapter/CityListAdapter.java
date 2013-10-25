package adapter;

import java.util.List;
import com.bus.activities.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ����������
 * @author Administrator
 *
 */
public class CityListAdapter extends BaseAdapter {

	private Context context;//������
	private List list;//վ�㼯��
	private int xuanze;//ѡ���ID
	private LayoutInflater inflater;//���������
	private ViewHolder holder;//����ViewHolder��

	
	//���캯��
	public CityListAdapter(Context context,List list,int xuanze) {
		this.context = context;
		this.list = list;
		this.xuanze=xuanze;
		//ʵ����LayoutInflater
		inflater = LayoutInflater.from(context);
	}	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		//if(convertView == null){
			convertView = inflater.inflate(R.layout.setting_location_list, null);
			holder = new ViewHolder();
			//��ø����ؼ���ʵ��
			holder.list_title =	 (TextView) convertView.findViewById(R.id.list_title);
			holder.image_view=(ImageView) convertView.findViewById(R.id.imageView1);
			
			//convertView.setTag(holder);
		//}else{
		//	holder = (ViewHolder) convertView.getTag();					
		//}		
		
		String str[]= (String[])list.get(position);
		//convertView.setBackgroundColor(R.color.red);
		
		holder.list_title.setText(str[1]);		
		holder.list_title.setTag( str[0]);
		
		
		int cityId= Integer.parseInt( str[0]);
		
		if(this.xuanze==cityId){
			holder.image_view.setVisibility(0);
		}
		
		//�ж����Ϊѡ���ĳ������޸ı���ɫ
		
		
		
		//����ֵ
		return convertView;
	}
	
	
	
	
	public final class ViewHolder{
		public TextView list_title;//����
		public ImageView image_view;
		
	}

}
