package com.bus.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

public class BaseFragment extends Fragment {
	Exit exit = new Exit();
	
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
	public void talk1(View view)
    {
    	
    	try{
    		startVoiceRecognitionActivity(VOICE_RECOGNITION_REQUEST_CODE);
    	}catch(Exception ex){
    		Toast.makeText(getActivity(), "�����ڵİ�׿ϵͳ�汾��֧������ʶ��", 1).show();			
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
    /**
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
	**/


	
	
}
