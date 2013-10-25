package com.bus.activities;

import java.util.ArrayList;
import java.util.HashMap;

import com.actionbarsherlock.app.SherlockActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LineInfoActivity extends SherlockActivity {

	private adapter.StationAdapter adapter;// ������
	private ListView listView;
	private TextView text1;
	private TextView text2;
	private TextView text3;
	private ArrayList<HashMap<String, Object>> listItems; // ������֡�ͼƬ��Ϣ
	String route;// ��·
	String[] zpnames;// վ��
	com.bus.customized.BusInfoView biv;
	TextView stopName;// �·���ʾ����Ϣ
	ProgressBar porgressbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_info);
		listView = (ListView) this.findViewById(R.id.listView1);
		porgressbar=(ProgressBar)this.findViewById(R.id.progressBar2);
		Intent intent1 = getIntent();// �������
		route = intent1.getStringExtra("xl");
		// ��ʾ��·
		text1 = (TextView) this.findViewById(R.id.textView1);
		text2 = (TextView) this.findViewById(R.id.textView2);
		text3 = (TextView) this.findViewById(R.id.textView3);
		getSupportActionBar().hide();

		text3.setText(route + "·");
		initListView();

		// adapter=new adapter.StationAdapter(this,zpnames);

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				Intent intent = new Intent(LineInfoActivity.this,
						RoutelInfoActivity.class); // ����������ui��ת����ui
				String str = (String) ((TextView) (view
						.findViewById(R.id.lis_stopname))).getText();
				intent.putExtra("zpname", str);
				intent.putExtra("zpnames", zpnames);
				intent.putExtra("xl", route);
				startActivity(intent);
			}
		});
	}
	/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_line_info, menu);
		return true;
	}
	**/

	private void initListView() {
		listItems = new ArrayList<HashMap<String, Object>>();

		Thread workerThread = new Thread(new logic.RouteLogic(new mHandler(),
				route));
		workerThread.start();

	}

	class mHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {// �˷�����ui�߳�����
			if (msg.getData().getStringArray("list").length > 1) {
				zpnames = msg.getData().getStringArray("list");
				adapter = new adapter.StationAdapter(getApplicationContext(),
						zpnames);
				listView.setAdapter(adapter);
				porgressbar.setVisibility(View.GONE);
				text1.setText(zpnames[0]);
				text2.setText(zpnames[zpnames.length - 1]);
			} else
				Toast.makeText(getApplicationContext(), "��Ǹ��δ�ҵ�����·��Ϣ", 1)
						.show();
		}
	};
}