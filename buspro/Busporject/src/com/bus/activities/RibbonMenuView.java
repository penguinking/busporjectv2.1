package com.bus.activities;


import java.util.ArrayList;
import java.util.HashMap;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RibbonMenuView extends LinearLayout {

	private ListView rbmListView;
	private View rbmOutsideView;
	
	private iRibbonMenuCallback callback;
	
	private ArrayList<HashMap<String, Object>>   menuItems;
	
	
	public RibbonMenuView(Context context) {
		super(context);		
		load();
	}
	
	public RibbonMenuView(Context context, AttributeSet attrs) {
		super(context, attrs);
		load();
	}

	
	private void load(){		
		if(isInEditMode()) return;		
		inflateLayout();			
		initUi();		
	}
	
	
	private void inflateLayout(){
		
		try{
			LayoutInflater.from(getContext()).inflate(R.layout.rbm_menu, this, true);
		} catch(Exception e){				
		}
	}
	
	private void initUi(){
		
		rbmListView = (ListView) findViewById(R.id.rbm_listview);
		rbmOutsideView = findViewById(R.id.rbm_outside_view);
				
		rbmOutsideView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				hideMenu();				
			}
		});
		
		
		rbmListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				if(callback != null)					
					callback.RibbonMenuItemClick(position);
				
				hideMenu();
			}			
		});				
	}
	
	
	public void setMenuClickCallback(iRibbonMenuCallback callback){
		this.callback = callback;
	}
	
	public void setMenuItems(String[] menulist){		
		//parseXml(menu);
		if(menulist != null && menulist.length > 0)
		{
			rbmListView.setAdapter(new Adapter(getContext(),menulist));			
		}		
	}
	
	
	@Override
	public void setBackgroundResource(int resource){
		rbmListView.setBackgroundResource(resource);
		
	}
	
	public void showMenu(){
		rbmOutsideView.setVisibility(View.VISIBLE);	
				
		rbmListView.setVisibility(View.VISIBLE);	
		rbmListView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rbm_in_from_right));
		
	}
	
	
	public void hideMenu(){
		
		rbmOutsideView.setVisibility(View.GONE);
		rbmListView.setVisibility(View.GONE);	
		
		rbmListView.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.rbm_out_to_right));
		
	}
	
	
	public void toggleMenu(){
		
		if(rbmOutsideView.getVisibility() == View.GONE){
			showMenu();
		} else {
			hideMenu();
		}
	}
	public void setmenuitem(){
		
	}
	
	
	
	private String resourceIdToString(String text){
		
		if(!text.contains("@")){
			return text;
		} else {
									
			String id = text.replace("@", "");
									
			return getResources().getString(Integer.valueOf(id));
			
		}
		
	}
	
	
	public boolean isMenuVisible(){		
		return rbmOutsideView.getVisibility() == View.VISIBLE;		
	}
	
		
	
	
	@Override 
	protected void onRestoreInstanceState(Parcelable state)	{
	    SavedState ss = (SavedState)state;
	    super.onRestoreInstanceState(ss.getSuperState());

	    if (ss.bShowMenu)
	        showMenu();
	    else
	        hideMenu();
	}
	
	

	@Override 
	protected Parcelable onSaveInstanceState()	{
	    Parcelable superState = super.onSaveInstanceState();
	    SavedState ss = new SavedState(superState);

	    ss.bShowMenu = isMenuVisible();

	    return ss;
	}

	static class SavedState extends BaseSavedState {
	    boolean bShowMenu;

	    SavedState(Parcelable superState) {
	        super(superState);
	    }

	    private SavedState(Parcel in) {
	        super(in);
	        bShowMenu = (in.readInt() == 1);
	    }

	    @Override
	    public void writeToParcel(Parcel out, int flags) {
	        super.writeToParcel(out, flags);
	        out.writeInt(bShowMenu ? 1 : 0);
	    }

	    public static final Parcelable.Creator<SavedState> CREATOR
	            = new Parcelable.Creator<SavedState>() {
	        @Override
			public SavedState createFromParcel(Parcel in) {
	            return new SavedState(in);
	        }

	        @Override
			public SavedState[] newArray(int size) {
	            return new SavedState[size];
	        }
	    };
	}
	
	
	
	class RibbonMenuItem{
		
		int id;
		String text;
		int icon;
		
	}
	
	
	
	
	private class Adapter extends BaseAdapter {
        
		private LayoutInflater inflater;
		private Context context;//上下文
		private String [] menulist;//站点集合
		
		//构造函数
		public Adapter(Context context,String [] menulist) {
			this.context = context;
			this.menulist = menulist;
			//实例化LayoutInflater
			inflater = LayoutInflater.from(context);
		}	
		
		
		
		@Override
		public int getCount() {
			
			return menulist.length;
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			final ViewHolder holder;
			
			if(convertView == null || convertView instanceof TextView){
				convertView = inflater.inflate(R.layout.rbm_item, null);
				
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.list_img_chevron);
				holder.text = (TextView) convertView.findViewById(R.id.rbm_item_text);
						
				convertView.setTag(holder);
			
			} else {
			
				holder = (ViewHolder) convertView.getTag();
			}
			String str= menulist[position];
			//设置内容
			String ss[]=str.split(",");
			//holder.image.setImageResource();
			holder.text.setText(ss[1]);	
			System.out.println("menulist "+ss[1]);
			return convertView;
		}
		
		
		class ViewHolder {
			TextView text;
			ImageView image;		
		}		
		
	}
}
