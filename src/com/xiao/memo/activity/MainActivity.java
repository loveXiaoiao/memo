package com.xiao.memo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

	private ListView listView;
	private List<Record> records = new ArrayList<Record>();
	ListViewAdapter adapter = new ListViewAdapter(MainActivity.this, records);
	RecordDao recordDao = new RecordDao(MainActivity.this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setTitle("备忘录");
		records = recordDao.getRecordList(null, "isOld=?", new String[] { "0" }, "expireTime asc");
		adapter = new ListViewAdapter(this, records);
		listView = (ListView) this.findViewById(R.id.MyListItem);
		listView.setAdapter(adapter);
		findViewById(R.id.add).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this, EditRecord.class);
				intent.putExtra("click_key", "click_add");
				startActivityForResult(intent,0);
			}
		});
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
		case RESULT_OK:
			Bundle b=data.getExtras();  //data为B中回传的Intent
			String content=b.getString("content");//str即为回传的值"Hello, this is B speaking"
			String expireTime = b.getString("expireTime");
			Record record = new Record();
			record.setContent(content);
			record.setExpireTime(expireTime);
			adapter.addItem(record);
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
