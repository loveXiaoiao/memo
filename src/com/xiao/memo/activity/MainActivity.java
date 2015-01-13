package com.xiao.memo.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.xiao.memo.R;
import com.xiao.memo.adapter.ListViewAdapter;
import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.receiver.AlarmReceiver;
import com.xiao.memo.util.TimeUtil;

public class MainActivity extends Activity {

	private ListView listView;
	private List<Record> records = new ArrayList<Record>();
	int index = 0;// 长按删除指定数据的索引
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
		// 添加长按点击,得到点中的index，即参数arg2
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				// TODO Auto-generated method stub
				index = arg2;
				return false;
			}
		});
		// 长按之后跳出可选的Menu框
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("选择操作");
						menu.add(0, 0, 0, "删除");
						menu.add(0, 1, 1, "取消");
					}
				});
	}
	
	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if(item.getItemId() == 0){
			String deleteText = records.get(index).getContent();
			String deleteTime = records.get(index).getExpireTime();
//			int deleteId = 0;
			List<Record> recordTemp = recordDao.getRecordList(null, "isOld=?", new String[] { "0" }, null);
			AlarmManager mAlarm;
			mAlarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
			Intent intent = new Intent(MainActivity.this,AlarmReceiver.class);
			for(Record re: recordTemp){
//				String expireTime = TimeUtil.parseToString(TimeUtil.parseToDate(re.getExpireTime()));
				if(re.getContent().equals(deleteText) && re.getExpireTime().equals(deleteTime)){
					//设为已过期
					re.setIsOld(1);
					recordDao.updateRecord(re);
					//刷新listview
					adapter.removeItem(index);
					PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, re.getId(), intent, 0);// 第二个参数为区别不同闹铃的唯一标识
					// 闹铃删除
					mAlarm.cancel(pendingIntent);
				}
			}
			Intent mWidgetIntent = new Intent();
			mWidgetIntent.setAction("com.xiao.memo.widget");
			MainActivity.this.sendBroadcast(mWidgetIntent);
		}
		return super.onContextItemSelected(item);

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
