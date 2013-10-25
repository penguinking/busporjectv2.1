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
 * 一般建议在自定义 Application 类里初始化。也可以在主 Activity 里。
 */
public class JPushApplication extends Application {
    private static final String TAG = "JPushApplication";

    @Override
    public void onCreate() {    	     
    	 Log.d(TAG, "onCreate");
         super.onCreate();
         JPushInterface.setDebugMode(false); 	//设置开启日志,发布时请关闭日志
         JPushInterface.init(this);     		// 初始化 JPush
    }
    
}
