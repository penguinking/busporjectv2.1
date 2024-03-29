package util;

import com.bus.activities.CustomDialog;
import com.bus.activities.R;

import android.content.Context;

public class UpdateManager{
	private static Context mContext;
/**
	

	// 提示语
	private String updateMsg = "有最新的软件包哦，亲快下载吧~";

	// 返回的安装包url
	private static String apkUrl = "http://download.bizinfocus.com/kxcs.apk";

	private Dialog noticeDialog;

	private Dialog downloadDialog;
	/* 下载包安装路径 */
	/**
	private static final String savePath = Environment
			.getExternalStorageDirectory().getPath() + "/update/";

	private static final String saveFileName = savePath + "kxcs.apk";

	/* 进度条与通知ui刷新的handler和msg常量 */
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

	// 外部接口让主Activity调用
	public void checkUpdateInfo() {

		// 获得网络版本号
		String v[] = util.Utils.getServerVer();
		if (v[0].equals("")) {
			return;
		}

		int netver = Integer.parseInt(v[0]);
		// 获得本地版本号
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
		builder.setTitle("软件版本更新");
		
		final LayoutInflater inflater = LayoutInflater.from(mContext);
		View v = inflater.inflate(R.layout.progress, null);
		mProgress = (ProgressBar) v.findViewById(R.id.progress);

		//builder.setView(v);
		builder.setNegativeButton("取消", new OnClickListener() {
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
					// 更新进度
					mHandler.sendEmptyMessage(DOWN_UPDATE);
					if (numread <= 0) {
						// 下载完成通知安装
						mHandler.sendEmptyMessage(DOWN_OVER);
						break;
					}
					fos.write(buf, 0, numread);
				} while (!interceptFlag);// 点击取消就停止下载.

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
	 * 下载apk
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
	 * 安装apk
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