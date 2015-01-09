package com.xiao.memo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xiao.memo.R;
import com.xiao.memo.adapter.ListViewAdapter;
import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;

public class MainActivity extends Activity {

	private TextView add;
	private ListView listView;
	private List<Record> records = new ArrayList<Record>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("备忘录");
		records = new RecordDao(this).getRecordList(null, "isOld=?", new String[] { "0" }, null);
		ListViewAdapter adapter = new ListViewAdapter(this, records);
		listView = (ListView) this.findViewById(R.id.MyListItem);
		listView.setAdapter(adapter);
		add = (TextView) findViewById(R.id.add);
		add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, EditRecord.class);
				intent.putExtra("click_key", "click_add");
				startActivity(intent);
			}

		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
