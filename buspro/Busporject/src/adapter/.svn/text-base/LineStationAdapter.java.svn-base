package adapter;

import com.bus.activities.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LineStationAdapter extends BaseAdapter {

	private Context context;//上下文
	private String [] stationList;//站点集合
	private LayoutInflater inflater;//布局填充器
	private ViewHolder holder;//声明ViewHolder类
	
	//构造函数
	public LineStationAdapter(Context context,String [] stationList) {
		this.context = context;
		this.stationList = stationList;
		//实例化LayoutInflater
		inflater = LayoutInflater.from(context);
	}	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return stationList.length;
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
		//如果convertView为null
				if(convertView == null){
					convertView = inflater.inflate(R.layout.list_item_line_stops, null);
					holder = new ViewHolder();
					//获得各个控件的实例
					holder.txtStationInfo = (TextView) convertView.findViewById(R.id.textView2);
					holder.txtLineInfo= (TextView) convertView.findViewById(R.id.textView1);
					
					convertView.setTag(holder);
				}else{
					holder = (ViewHolder) convertView.getTag();					
				}		
				
				String str= stationList[position];
				//设置内容
				String ss[]=str.split("~");
				if(str.length()>0){
					try{
					holder.txtLineInfo.setText(ss[0]+"路");		
					holder.txtStationInfo.setText(ss[1]);
					}catch(ArrayIndexOutOfBoundsException e){
						System.out.println("ArrayIndexOutOfBoundsException");
					}
					//返回值
					return convertView;
					}else
					holder.txtLineInfo.setText("抱歉，未找到该站点信息");		
					holder.txtStationInfo.setText("");
					return convertView;
	}
	
	
	private final class ViewHolder{
		private TextView txtStationInfo;//站点信息 
		private TextView txtLineInfo;//线路信息 
	}

}
