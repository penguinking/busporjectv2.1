package com.bus.activities;



import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.widget.Toast;

public class BaseActivity extends Activity {
	Exit exit = new Exit();
	
	

	

	@Override	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	    if (keyCode == KeyEvent.KEYCODE_BACK) {	
	        pressAgainExit();	
	        return true;	
	    }	
	    return super.onKeyDown(keyCode, event);
	
	}
	
	 
	
	/**	
	 * �ٰ�һ���˳�����	
	 */	
	private void pressAgainExit() {
	
	    if (exit.isExit()) {	
	        finish();	
	    } else {	
	        Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",1000).show();	
	        exit.doExitInOneSecond();	
	    }
	
	}
	
	
	public boolean note_Intent(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		boolean isAvalible = false;
		if (networkinfo == null || !networkinfo.isAvailable()) {
			// ��ǰ���粻����
			Toast.makeText(context.getApplicationContext(), "����û������Internet��������Internet��", Toast.LENGTH_SHORT).show();
			return false;
		}

		if (networkinfo != null && networkinfo.isConnected()) {
			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				isAvalible = true;
			}
		}
		
		/*boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
		if (!wifi) { // ��ʾʹ��wifi
			Toast.makeText(context.getApplicationContext(), "��û��ʹ��WIFI����Internet��������ʹ��WIFI�Լ���������", Toast.LENGTH_SHORT).show();
		}*/
		return isAvalible;
	}
	
	public int  isWifi(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		
		if (networkinfo == null || !networkinfo.isAvailable()) {			
			return 0;
		}else			
		{
			
			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
				if (!wifi) { // ��ʾʹ��wifi
					return 2;//��wifi
				}else{					
					return 1;//wifi
				}					
			}else
			{
				return 3;
			}
					
		}
		
		
	}
	
	/**
	public ImageCacheManager imageCacheManager;
	
	public class DownloadTask extends AsyncTask<String, Void, Bitmap> {
		
		private ImageView m;		
		public DownloadTask(ImageView mv){
			m=mv;
		}
		
		@Override
		protected Bitmap doInBackground(String... params) {
			try {
				
				return imageCacheManager.downlaodImage(new URL(params[0]));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			if(result==null )
			{
				m.setImageResource(R.drawable.gg_02);				
				
			}else
			{
				m.setImageBitmap(result);
			}
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			m.setImageResource(R.drawable.gg_03);
			super.onPreExecute();
		}

	}**/
	
	
}
