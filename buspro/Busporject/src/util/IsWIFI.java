package util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class IsWIFI {
	public static int  isWifi(Context context) {
		ConnectivityManager con = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkinfo = con.getActiveNetworkInfo();
		
		if (networkinfo == null || !networkinfo.isAvailable()) {			
			return 0;
		}else			
		{
			
			if (networkinfo.getState() == NetworkInfo.State.CONNECTED) {
				boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
				if (!wifi) { // 提示使用wifi
					return 2;//非wifi
				}else{					
					return 1;//wifi
				}					
			}else
			{
				return 3;
			}
					
		}
		
		
	}
}
