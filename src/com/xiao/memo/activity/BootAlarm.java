package com.xiao.memo.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.receiver.AlarmReceiver;
import com.xiao.memo.util.TimeUtil;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;

public class BootAlarm extends Activity {
	private List<Record> records = new ArrayList<Record>();
	private Calendar mAlarmCalendar;//闹钟设置世间
	private AlarmManager mAlarm;
	Record record = new Record();
	private long alarm_id = 0;
	String time = "";
	int myear = 0, mmonth = 0, mday = 0, mhour = 0, mminute = 0;
	private Date currentTime, expireTime;
	RecordDao recordDao = new RecordDao(BootAlarm.this);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		records = recordDao.getRecordList(null, "isOld=?", new String[] { "0" }, null);
		currentTime = new Date();
		for(Record re : records){
			expireTime = TimeUtil.parseToDate(re.getExpireTime());
			if (expireTime.getTime() > currentTime.getTime()) {
				alarm_id = re.getId();
				mAlarmCalendar = Calendar.getInstance();
				time = re.getExpireTime();
				myear = Integer.valueOf(time.substring(0, time.indexOf("年"))).intValue();
				mmonth = Integer.valueOf(time.substring(time.indexOf("年") + 1, time.indexOf("月"))).intValue() - 1;
				mday = Integer.valueOf(time.substring(time.indexOf("月") + 1, time.indexOf("日"))).intValue();
				mhour = Integer.valueOf(time.substring(time.indexOf("日") + 1, time.indexOf("时"))).intValue();
				mminute = Integer.valueOf(time.substring(time.indexOf("时") + 1,time.indexOf("分"))).intValue();
				mAlarmCalendar.set(Calendar.YEAR, myear);
				mAlarmCalendar.set(Calendar.MONTH, mmonth);
				mAlarmCalendar.set(Calendar.DAY_OF_MONTH, mday);
				mAlarmCalendar.set(Calendar.HOUR_OF_DAY, mhour);
				mAlarmCalendar.set(Calendar.MINUTE, mminute);
				mAlarmCalendar.set(Calendar.SECOND, 0);
				mAlarmCalendar.set(Calendar.MILLISECOND, 0);
				mAlarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
				Intent intent = new Intent(BootAlarm.this,AlarmReceiver.class);
				intent.putExtra("record_id", String.valueOf(alarm_id));
				PendingIntent pendingIntent = PendingIntent.getBroadcast(BootAlarm.this, (int) alarm_id,intent,PendingIntent.FLAG_UPDATE_CURRENT);
				mAlarm.set(AlarmManager.RTC_WAKEUP,mAlarmCalendar.getTimeInMillis(),pendingIntent);
				
			}
		}
		//开启启动程序
//		Intent intent = new Intent(BootAlarm.this, MainActivity.class);
//		startActivity(intent);
//		BootAlarm.this.finish();
	}

}
