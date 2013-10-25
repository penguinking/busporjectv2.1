package util;

import com.bus.activities.CustomDialog;
import com.bus.activities.R;

import android.content.Context;

public class UpdateManager{
	private static Context mContext;
/**
	

	// ��ʾ��
	private String updateMsg = "�����µ������Ŷ���׿����ذ�~";

	// ���صİ�װ��url
	private static String apkUrl = "http://download.bizinfocus.com/kxcs.apk";

	private Dialog noticeDialog;

	private Dialog downloadDialog;
	/* ���ذ���װ·�� */
	/**
	private static final String savePath = Environment
			.getExternalStorageDirectory().getPath() + "/update/";

	private static final String saveFileName = savePath + "kxcs.apk";

	/* ��������֪ͨuiˢ�µ�handler��msg���� */
	/**
	private static ProgressBar mProgress;

	private static final int DOWN_UPDATE = 1;

	private static final int DOWN_OVER = 2;

	private static int progress;

	private static Thread downLoadThread;

	private static boolean interceptFlag = false;

	private static Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case DOWN_UPDATE:
				mProgress.setProgress(progress);
				break;
			case DOWN_OVER:

				installApk();
				break;
			default:
				break;
			}
		};
	};
**/
	public UpdateManager(Context context) {
		UpdateManager.mContext = context;
	}

	// �ⲿ�ӿ�����Activity����
	public void checkUpdateInfo() {

		// �������汾��
		String v[] = util.Utils.getServerVer();
		if (v[0].equals("")) {
			return;
		}

		int netver = Integer.parseInt(v[0]);
		// ��ñ��ذ汾��
		int localver = util.Utils.getVerCode(mContext);

		if (netver > localver) {
			showNoticeDialog();
		}
	}

	private void showNoticeDialog() {
		CustomDialog NoticeDialog = new CustomDialog(mContext,R.style.MyDialogStyle);
		NoticeDialog.show();
	}

	/**
	private void showDownloadDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("����汾����");
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		//builder.setView(v);
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				interceptFlag = true;
			}
		});
		downloadDialog = builder.create();
		downloadDialog.show();

		downloadApk();
	}

	

	private static Runnable mdownApkRunnable = new Runnable() {
		@Override
		public void run() {
			try {
				URL url = new URL(apkUrl);

				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
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

				int count = 0;
				byte buf[] = new byte[1024];

				do {
					int numread = is.read(buf);
					count += numread;
					System.out.println(count);
					progress = (int) (((float) count / length) * 100);
					mProgress.setProgress(progress);
					// ���½���
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// �������֪ͨ��װ
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// ���ȡ����ֹͣ����.

				fos.close();
				is.close();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	};

	/**
	 * ����apk
	 * 
	 * @param url
	 */
	/**
	public static void downloadApk() {
		downLoadThread = new Thread(mdownApkRunnable);
		downLoadThread.start();
	}
	**/

	/**
	 * ��װapk
	 * 
	 * @param url
	 */
	/**
	private static void installApk() {
		File apkfile = new File(saveFileName);
		if (!apkfile.exists()) {
			return;
		}
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + apkfile.toString()),
				"application/vnd.android.package-archive");
		mContext.startActivity(i);

	}
	**/
}