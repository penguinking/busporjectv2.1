package com.bus.activities;

import java.util.Set;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * For developer startup JPush SDK
 * 
 * һ�㽨�����Զ��� Application �����ʼ����Ҳ�������� Activity �
 */
public class JPushApplication extends Application {
    private static final String TAG = "JPushApplication";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "onCreate");
         super.onCreate();
         JPushInterface.setDebugMode(false); 	//���ÿ�����־,����ʱ��ر���־
         JPushInterface.init(this);     		// ��ʼ�� JPush
    }
    
}
