package com.bus.activities;

import service.UpdateService;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog {
	Context context;
	public CustomDialog(Context context, int theme) {
		super(context, theme);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	/**
	private UpdateService.DownloadBinder binder;
	private boolean binded;
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			binder = (UpdateService.DownloadBinder) service;
			binded = true;
			// 开始下载
			binder.start();
			// 监听进度信息
			// listenProgress();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	**/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialog_custom);
		Button btn_update = (Button) findViewById(R.id.btn_update);
		Button btn_cancel = (Button) findViewById(R.id.btn_cancel);
		String v[] = util.Utils.getServerVer();
		String ver_name = v[1];
		TextView tv_message = (TextView) findViewById(R.id.tv_update_message);
		tv_message.setText("  尊敬的用户，" + ver_name + "版本已经发布，请您及时更新软件，感谢您的使用！");
		btn_update.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				// util.UpdateManager.downloadApk();
				// showCustomizeNotification();
				start(view);
				dismiss();
				// r.setVisibility(4);
			}

		});
		btn_cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dismiss();
			}

		});
	}

	

	public void start(View view) {
		Intent intent = new Intent(context, UpdateService.class);
		context.startService(intent); // 如果先调用startService,则在多个服务绑定对象调用unbindService后服务仍不会被销毁
		//context.bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
}