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
	// ���صİ�װ��url
	private static String apkUrl = "http://download.bizinfocus.com/kxcs.apk";
	//private static String apkUrl = "http://download.bizinfocus.com/test/kxcs.apk";
	/* ���ذ���װ·�� */
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
					// ���½���
					RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
							R.layout.update_notification);
					mNotification.contentView = contentView;
					// contentView.setTextViewText(R.id.update_progressbar, rate
					// + "%");
					contentView.setProgressBar(R.id.update_progressbar, 100,
							rate, false);
							**/
					String rate2 = String.valueOf(rate);
					mNotification.setLatestEventInfo(mContext, "�ǻ۳�����������",
							"�����أ�"+rate2+"%", updatePendingIntent);
				} else {
					// ������Ϻ�任֪ͨ��ʽ
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
					// Notification.DEFAULT_SOUND;//��������
					mNotification.setLatestEventInfo(mContext, "�ǻ۳���",
							"������ϣ������װ", updatePendingIntent);
					stopSelf();// ͣ����������
				}

				// ��������֪ͨһ��,���򲻻����
				mNotificationManager.notify(NOTIFY_ID, mNotification);
				break;
			case 0:
				// ȡ��֪ͨ
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
		// �����Զ����DownloadBinderʵ��
		return binder;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		cancelled = true; // ȡ�������߳�
	}

	/**
	 * ����֪ͨ
	 */
	private void setUpNotification() {
		int icon = R.drawable.app_logo_small;
		CharSequence tickerText = "��ʼ����";
		long when = System.currentTimeMillis();
		mNotification = new Notification(icon, tickerText, when);

		// ������"��������"��Ŀ��
		mNotification.flags = Notification.FLAG_ONGOING_EVENT;
		/**
		RemoteViews contentView = new RemoteViews(mContext.getPackageName(),
				R.layout.update_notification);
		contentView.setTextViewText(R.id.tv_update_message, "�ǻ۳������ڸ���");
		// ָ�����Ի���ͼ
		mNotification.contentView = contentView;
		**/
		mNotification.setLatestEventInfo(mContext, "�ǻ۳���",
				"���ڸ���", updatePendingIntent);
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
		// Notification.DEFAULT_SOUND;//��������
		
		mNotification.contentIntent = updatePendingIntent;
		mNotificationManager.notify(NOTIFY_ID, mNotification);
		
	}

	/**
	 * ����ģ��
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
					// �������֪ͨ��װ
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
		// �����ȹ���
		progress = 0;
		// ����֪ͨ
		setUpNotification();
		new Thread() {
			@Override
			public void run() {
				// ����
				Download();
			};
		}.start();
	}

	/**
	 * DownloadBinder�ж�����һЩʵ�õķ���
	 * 
	 * @author user
	 * 
	 */
	public class DownloadBinder extends Binder {

		/**
		 * ��ʼ����
		 */
		

		/**
		 * ��ȡ����
		 * 
		 * @return
		 */
		public int getProgress() {
			return progress;
		}

		/**
		 * ȡ������
		 */
		public void cancel() {
			cancelled = true;
		}

		/**
		 * �Ƿ��ѱ�ȡ��
		 * 
		 * @return
		 */
		public boolean isCancelled() {
			return cancelled;
		}
	}

}
