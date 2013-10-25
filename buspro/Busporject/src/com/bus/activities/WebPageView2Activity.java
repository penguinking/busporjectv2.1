package com.bus.activities;


import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Window;

import android.net.Uri;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebPageView2Activity extends SherlockActivity{
	final Activity activity = this; 
	String id;
	String url;
	String name;
	WebView webView;
	private int mProgress = 100;
	Handler mHandler = new Handler();
	Runnable mProgressRunner = new Runnable() {
        @Override
        public void run() {
            mProgress += 2;

            //Normalize our progress along the progress bar's scale
            int progress = (android.view.Window.PROGRESS_END - android.view.Window.PROGRESS_START) / 100 * mProgress;
            setSupportProgress(progress);

            if (mProgress < 100) {
                mHandler.postDelayed(mProgressRunner, 50);
            }
        }
    };
    @Override     
    public void onCreate(Bundle savedInstanceState)     
    {      
        super.onCreate(savedInstanceState);      
        requestWindowFeature(Window.FEATURE_PROGRESS);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSherlock().setProgressBarIndeterminateVisibility(true);
        
        Intent intent1 = getIntent();//获得自身        
        //id=intent1.getStringExtra("id");
        url=intent1.getStringExtra("url");
        name=intent1.getStringExtra("name");
        
        setContentView(R.layout.webpageview);    
        webView = (WebView) findViewById(R.id.webpageView);     
        webView.getSettings().setJavaScriptEnabled(true); 
        webView.setDownloadListener(new MyWebViewDownLoadListener());
        webView.getSettings().setSupportZoom(true);
        webView .setScrollbarFadingEnabled( true );
        webView .setScrollBarStyle(View. SCROLLBARS_INSIDE_OVERLAY );
        WebSettings settings = webView.getSettings(); 
        settings.setUseWideViewPort(true); 
        settings.setLoadWithOverviewMode(true); 
        settings.setLayoutAlgorithm(LayoutAlgorithm.NORMAL);
        webView.setWebChromeClient(new WebChromeClient()   
        {            
            @Override
			public void onProgressChanged(WebView view, int progress)     
            {              
            	getSupportActionBar().setTitle("页面加载中...");         
            	mProgressRunner.run();
            	if(progress == 100)              
                	getSupportActionBar().setTitle(name);         
                }        
                }        
             
        );        
        webView.setWebViewClient(new WebViewClient() {     
                     
            @Override
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)     
            {                 // Handle the error         
                  
            }               
                    
            @Override
			public boolean shouldOverrideUrlLoading(WebView view, String url)   
            {                
                view.loadUrl(url);     
                return true;         
                }        
            }
        );    
        webView.loadUrl(url); 
        
    }
    @Override
	public boolean onOptionsItemSelected(
			com.actionbarsherlock.view.MenuItem item) {
		// TODO Auto-generated method stub
		int id = item.getItemId();
		switch(id){
		case android.R.id.home:
			WebPageView2Activity.this.finish();            
	         return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
    private class MyWebViewDownLoadListener implements DownloadListener {  
    	  
        @Override  
        public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype,  
                                    long contentLength) {  
            Uri uri = Uri.parse(url);  
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);  
            startActivity(intent);  
        }  
  
    }  
    @Override	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	    	webView.goBack();	
	        return true;
	        }
	    
	    return super.onKeyDown(keyCode, event);
	
	}
}
