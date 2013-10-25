package service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bus.activities.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

public class UpdateService extends Service {

	private static final int NOTIFY_ID = 0;
	private boolean cancelled;
	private int progress;

	private Context mContext = this;

	private NotificationManager mNotificationManager;
	private Notification mNotification;

	private DownloadBinder binder = new DownloadBinder();
	// 返回的安装包url
	private static String apkUrl = "http://download.bizinfocus.com/kxcs.apk";
	//private static String apkUrl = "http://download.bizinfocus.com/test/kxcs.apk";
	/* 下载包安装路径 */
	private static final String savePath = Environment
			.getExternalStorageDirectory().getPath() + "/update/";

	private static final String saveFileName = savePath + "kxcs.apk";
	private static boolean interceptFlag = false;
	private PendingIntent updatePendingIntent = null;

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				int rate = msg.arg1;
				if (rate < 100) {
					/**
					// 更新进度
					RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
							R.layout.update_notification);
					mNotification.contentView = contentView;
					// contentView.setTextViewText(R.id.update_progressbar, rate
					// + "%");
					contentView.setProgressBar(R.id.update_progressbar, 100,
							rate, false);
							**/
					String rate2 = String.valueOf(rate);
					mNotification.setLatestEventInfo(mContext, "智慧朝阳正在下载",
							"已下载："+rate2+"%", updatePendingIntent);
				} else {
					// 下载完毕后变换通知形式
					mNotification.flags = Notification.FLAG_AUTO_CANCEL;
					mNotification.contentView = null;

					File apkfile = new File(saveFileName);
					if (!apkfile.exists()) {
						return;
					}
					Uri uri = Uri.fromFile(apkfile);
					Intent installIntent = new Intent(Intent.ACTION_VIEW);
					installIntent.setDataAndType(uri,
							"application/vnd.android.package-archive");
					updatePendingIntent = PendingIntent.getActivity(
							UpdateService.this, 0, installIntent, 0);
					// mNotification.defaults =
					// Notification.DEFAULT_SOUND;//铃声提醒
					mNotification.setLatestEventInfo(mContext, "智慧朝阳",
							"下载完毕，点击安装", updatePendingIntent);
					stopSelf();// 停掉服务自身
				}

				// 最后别忘了通知一下,否则不会更新
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			case 0:
				// 取消通知
				mNotificationManager.cancel(NOTIFY_ID);
				break;
			}
		};
	};

	@Override
	public void onCreate() {
		super.onCreate();
		mNotificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		start();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// 返回自定义的DownloadBinder实例
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cancelled = true; // 取消下载线程
	}

	/**
	 * 创建通知
	 */
	private void setUpNotification() {
		int icon = R.drawable.app_logo_small;
		CharSequence tickerText = "开始下载";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);

		// 放置在"正在运行"栏目中
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		/**
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.update_notification);
		contentView.setTextViewText(R.id.tv_update_message, "智慧朝阳正在更新");
		// 指定个性化视图
		mNotification.contentView = contentView;
		**/
		mNotification.setLatestEventInfo(mContext, "智慧朝阳",
				"正在更新", updatePendingIntent);
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Uri uri = Uri.fromFile(apkfile);
		Intent installIntent = new Intent(Intent.ACTION_VIEW);
		installIntent.setDataAndType(uri,
				"application/vnd.android.package-archive");
		updatePendingIntent = PendingIntent.getActivity(
				UpdateService.this, 0, installIntent, 0);
		// mNotification.defaults =
		// Notification.DEFAULT_SOUND;//铃声提醒
		
		mNotification.contentIntent = updatePendingIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
		
	}

	/**
	 * 下载模块
	 */
	private void Download() {
		cancelled = false;
		try {
			URL url = new URL(apkUrl);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setConnectTimeout(4000);
			conn.connect();
			System.out.println("connect ok");
			int length = conn.getContentLength();
			System.out.println(length);
			InputStream is = conn.getInputStream();

			File file = new File(savePath);
			System.out.println(savePath);
			if (!file.exists()) {
				file.mkdir();
			}
			String apkFile = saveFileName;
			File ApkFile = new File(apkFile);
			FileOutputStream fos = new FileOutputStream(ApkFile);
			byte buf[] = new byte[1024];
			int count=0;
			do {
				int numread = is.read(buf);
				count += numread;
				progress = (int) (((float) count / length) * 100);
				//System.out.println("progress"+progress);
				if(progress%10==0){
				int progress2 = progress;
				Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.arg1 = progress2;
				handler.sendMessage(msg);
				//System.out.println("handler");
				//System.out.println("progress2"+progress2);
				}
				if (numread <= 0) {
					// 下载完成通知安装
					break;
				}
				fos.write(buf, 0, numread);
				
			} while (!interceptFlag);
			fos.flush();
			fos.close();
			is.close();
			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (cancelled) {
			Message msg = handler.obtainMessage();
			msg.what = 0;
			handler.sendMessage(msg);
			interceptFlag = true;
		}
	}
	
	public void start() {
		// 将进度归零
		progress = 0;
		// 创建通知
		setUpNotification();
		new Thread() {
			@Override
			public void run() {
				// 下载
				Download();
			};
		}.start();
	}

	/**
	 * DownloadBinder中定义了一些实用的方法
	 * 
	 * @author user
	 * 
	 */
	public class DownloadBinder extends Binder {

		/**
		 * 开始下载
		 */
		

		/**
		 * 获取进度
		 * 
		 * @return
		 */
		public int getProgress() {
			return progress;
		}

		/**
		 * 取消下载
		 */
		public void cancel() {
			cancelled = true;
		}

		/**
		 * 是否已被取消
		 * 
		 * @return
		 */
		public boolean isCancelled() {
			return cancelled;
		}
	}

}
