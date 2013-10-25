package com.bus.activities;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.loopj.android.image.SmartImageView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

;

public class DiscountFragment extends Fragment {
	TextView tv_test;
	double price1;
	double price2;
	double price3;
	double dis_rate;
	int level1;
	int level2;
	Long time_begin;
	Long time_end;
	Long time_now;
	Long time_delay;
	ImageButton btn_submit;
	private TextView tv_time;
	private TextView tv_time_delay;
	private TextView tv_price_1;
	String device_id;
	String info;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View rootView = inflater.inflate(R.layout.discount_fragment, container,
				false);
		TelephonyManager tm = (TelephonyManager) getActivity()
				.getSystemService(Context.TELEPHONY_SERVICE);
		tv_time = (TextView) rootView.findViewById(R.id.tv_time);
		tv_price_1 = (TextView) rootView.findViewById(R.id.tv_price1);// ��Ʒԭ��
		final TextView tv_price_2 = (TextView) rootView
				.findViewById(R.id.tv_price2);// ��Ʒ��ɱ��
		tv_time_delay = (TextView) rootView.findViewById(R.id.tv_time_delay);// ��ɱʱ�䣬����
		final TextView tv_rate = (TextView) rootView.findViewById(R.id.tv_rate);// �ۿۣ��������
		final TextView tv_rule = (TextView) rootView.findViewById(R.id.tv_rule);// �ۿۣ��������
		final TextView tv_goods_title = (TextView) rootView
				.findViewById(R.id.tv_goods_title);// �ۿۣ��������
		final TextView tv_goods_content = (TextView) rootView
				.findViewById(R.id.tv_goods_content);
		final SmartImageView goods_image = (SmartImageView) rootView
				.findViewById(R.id.goods_image);// ��ƷͼƬ
		ImageButton btn_submit = (ImageButton) rootView
				.findViewById(R.id.btn_submit);// ��ƷͼƬ
		int i = getArguments().getInt("no");// ��ȡIDֵ
		String[] info2 = getArguments().getStringArray("info");
		String info3 = info2[i];
		String[] info4 = info3.split(",");
		String url = "http://miao.bizinfocus.com/upload/A/" + info4[3];// ��ȡ��ƷͼƬ��ַ
		goods_image.setImageUrl(url);
		price1 = Double.valueOf(info4[4]);// ��ȡ��Ʒԭ��
		price2 = Double.valueOf(info4[5]);// ��ȡ��Ʒ��ɱ��1
		price3 = Double.valueOf(info4[6]);// ��ȡ��Ʒ��ɱ��2
		level1 = Integer.parseInt(info4[7]);// ��ȡ��Ʒ��ɱ��1�ķ�Χ
		level2 = Integer.parseInt(info4[8]);// ��ȡ��Ʒ��ɱ��2�ķ�Χ
		String begin = info4[9];// ��ɱ��ʼʱ��
		String end = info4[10];// ��ɱ����ʱ��
		String now = info4[11];// ��������ǰʱ��
		/** ʱ��ת���ɺ���,�������߼��ж� **/
		time_begin = getTime(begin);
		time_end = getTime(end);
		time_now = getTime(now);
		System.out.println("time_now"+time_now+"now"+now);
		System.out.println("time_end"+time_end+"end"+end);
		if (time_now < time_end) {
			time_delay = time_begin - time_now;
			tv_time_delay.setText("������ɱ���ʼ����");
			handler.post(runnable);
		}
		if (time_now > time_begin && time_now < time_end) {
			tv_time_delay.setText("��ɱ���ʼ�������ж�");
		}
		if (time_now > time_end) {
			tv_time_delay.setText("������ɱ��ѽ�������л���Ĺ�ע��");
		}
		
		// ��ȡ��Ʒ�ۿ��ʣ�����ʽ�����
		dis_rate = ((price2 * 10) / (price1 * 10)) * 10;
		String parten = "#.##";
		DecimalFormat decimal = new DecimalFormat(parten);
		String rate = decimal.format(dis_rate);
		// ���ÿؼ���ɱ�۸�
		tv_price_1.setText("��ɱ��:��" + price2);
		tv_price_2.setText("��" + price1);
		tv_price_2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);// ���û���
		tv_rate.setText(rate + "��");
		tv_goods_title.setText(info4[1]);
		tv_goods_content.setText(info4[2]);
		final String goods_id = info4[0];// ��ȡ��ƷID
		tv_rule.setText(info4[13]);
		device_id = tm.getDeviceId();// ��ȡ�豸ID
		/* ������ɱ��ť���� */
		btn_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (time_now < time_begin) {
					Toast toast = Toast.makeText(getActivity()
							.getApplicationContext(), "�ף���ɱ���û��ʼ�����Եȣ�",
							Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					toast.show();
				} else {
					Toast toast = Toast.makeText(getActivity()
							.getApplicationContext(), "���Ե�", Toast.LENGTH_LONG);
					toast.setGravity(Gravity.CENTER, 0, 0);
					LinearLayout toastView = (LinearLayout) toast.getView();
					ProgressBar progressbar = new ProgressBar(getActivity()
							.getApplicationContext());
					toastView.addView(progressbar, 0);
					toast.show();
					Thread workerThread = new Thread(new logic.DiscountLogic2(
							new mHandler2(), goods_id, device_id));// ��ɱ�߼�ʵ��
					workerThread.start();
				}
			}

		});

		return rootView;

	}

	/** ����ʱʵ�� **/
	Handler handler = new Handler();

	Runnable runnable = new Runnable() {

		@Override
		public void run() {

			/* ����һ������ʱ���ڲ��� */
			class MyCount extends CountDownTimer {
				public MyCount(long millisInFuture, long countDownInterval) {
					super(millisInFuture, countDownInterval);
				}

				@Override
				public void onFinish() {
					// tv_time_delay.setText("������ɱ��ѽ�������л���Ĺ�ע��");
					// tv_time.setVisibility(View.GONE);
					// btn_submit.setVisibility(View.GONE);
				}

				@Override
				public void onTick(long millisUntilFinished) {
					tv_time.setText(format(millisUntilFinished));
				}
			}
			MyCount mc = new MyCount(time_delay, 1000);
			mc.start();

		}

	};

	/** ��ʽ������Ϊʱ���ʽ **/
	public static String format(long ms) {// �����������x��xʱx��x��x����
		int ss = 1000;
		int mi = ss * 60;
		int hh = mi * 60;
		int dd = hh * 24;

		long day = ms / dd;
		long hour = (ms - day * dd) / hh;
		long minute = (ms - day * dd - hour * hh) / mi;
		long second = (ms - day * dd - hour * hh - minute * mi) / ss;
		String strDay = day < 10 ? "0" + day : "" + day;
		String strHour = hour < 10 ? "0" + hour : "" + hour;
		String strMinute = minute < 10 ? "0" + minute : "" + minute;
		String strSecond = second < 10 ? "0" + second : "" + second;
		return strDay + "��" + strHour + ":" + strMinute + ":" + strSecond;
	}

	/** ��ʽ��ʱ���ʽΪ���� **/
	@SuppressLint("SimpleDateFormat")
	public static Long getTime(String user_time) {
		Long l = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.out.println(sdf);
		Date d;
		try {
			d = sdf.parse(user_time);
			l = d.getTime();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return l;

	}

	/** ��ɱ�Handlerʵ�� **/
	class mHandler2 extends Handler {
		@SuppressLint("HandlerLeak")
		@Override
		public void handleMessage(Message msg) {
			// ����UI
			showStatus(msg.getData().getString("status"));
		}
	}

	/** ��ɱ�����ֵչʾDialogʵ�� **/
	public void showStatus(String info) {

		if (info.trim().equals("")) {
			Toast.makeText(getActivity(), "��ȡ�н���Ϣʧ��", Toast.LENGTH_SHORT)
					.show();

		}
		final Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
		LayoutInflater inflater = (LayoutInflater) getActivity()
				.getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
		View layout = inflater.inflate(R.layout.dialog_discount_status, null);
		dialog.setContentView(layout);
		int level = Integer.parseInt(info);
		TextView tv_dialog_price = (TextView) layout
				.findViewById(R.id.tv_dialog_price);
		TextView tv_dialog_content = (TextView) layout
				.findViewById(R.id.tv_dialog_content);
		if (level > level1 && level < level2) {
			tv_dialog_price.setText("����" + price3 + "Ԫ�ļ۸���ɱ�и���Ʒ");
			tv_dialog_content
					.setText("���ڻ��Ч����ƾ�û�ID����һ���ɱ��Ʒ,����ǰ�û�ID����Ϊ"
							+ device_id + "���Ժ��ǰ������--��������в鿴�û�ID");
			dialog.show();
		}
		if (level < level1 && level > 0) {
			tv_dialog_price.setText("����" + price2 + "Ԫ�ļ۸���ɱ�и���Ʒ");
			tv_dialog_content
					.setText("���ڻ��Ч����ƾ�û�ID����һ���ɱ��Ʒ,����ǰ�û�ID����Ϊ"
							+ device_id + "���Ժ��ǰ������--��������в鿴�û�ID");
			dialog.show();
		} else {
			Toast toast = Toast.makeText(getActivity().getApplicationContext(),
					"�ܱ�Ǹ,��δ��ɱ�и���Ʒ", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();

		}

		Button btn_confirm = (Button) layout
				.findViewById(R.id.dialog_button_confirm);
		btn_confirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}

		});
	}
}
