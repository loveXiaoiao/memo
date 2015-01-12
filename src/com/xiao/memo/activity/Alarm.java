package com.xiao.memo.activity;

import java.util.ArrayList;
import java.util.List;

import com.xiao.memo.R;
import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.service.AlarmService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class Alarm extends Activity {
	private List<Record> records = new ArrayList<Record>();
	String mtime = "";
	String mtext = "";
	String id = "";
	RecordDao recordDao = new RecordDao(Alarm.this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); 
		//下面是为了在锁屏时也能启动闹铃
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		// 播放闹铃
		Intent intentSV = new Intent(Alarm.this, AlarmService.class);
		startService(intentSV);
		Intent intent = getIntent();
		id = String.valueOf(intent.getIntExtra("record_id", 0));
		records = recordDao.getRecordList(null, "_id=?", new String[] { id }, null);
		Record record = new Record();
		if(records.size() > 0){
			mtext = records.get(0).getContent();
			record = records.get(0);
			record.setIsOld(1);
			recordDao.updateRecord(record);
		}
		new AlertDialog.Builder(Alarm.this)
		.setIcon(R.drawable.bell)
		.setTitle("有事情要做了！")
		.setMessage(mtext)
		.setPositiveButton("完成",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,
							int whichButton) {
						//关闭闹铃声
						Intent intentSV = new Intent(Alarm.this, AlarmService.class);
						stopService(intentSV);
						//更新桌面widget	
//						Intent mWidgetIntent = new Intent();
//						mWidgetIntent.setAction("com.ideal.note.widget");
//						Alarm.this.sendBroadcast(mWidgetIntent);
						Alarm.this.finish();
					}
				}).show();
	}
	// 返回键时回到主Acitvity
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent intent = new Intent(Alarm.this, MainActivity.class);
		startActivity(intent);
		Alarm.this.finish();
	}

}
