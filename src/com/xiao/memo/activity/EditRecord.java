package com.xiao.memo.activity;

import java.util.Calendar;
import java.util.Date;

import com.xiao.memo.R;
import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.util.TimeUtil;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class EditRecord extends Activity {
	
	private Calendar mCalendar, mAlarmCalendar;//显示时间，闹钟设置世间
	private Button dateBtn = null;//设置日期
	private Button timeBtn = null;//设置时间
	private CheckBox isAlarmBtn = null;//是否启用闹钟
	private EditText mText = null;////文本编辑
	private Button setBtn = null;//保存
	private Button discardBtn = null;//取消
	private Date currentTime, expireTime;
	private int year, month, day, hour, minute;
	private Integer isAlarm = 0;//是否设置闹钟
	private String click_key = "";
	private Record record;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mCalendar = Calendar.getInstance();
//		year = mCalendar.get(Calendar.YEAR);
//		month = mCalendar.get(Calendar.MONTH);
//		day = mCalendar.get(Calendar.DAY_OF_MONTH);
//		hour = mCalendar.get(Calendar.HOUR_OF_DAY);
//		minute = mCalendar.get(Calendar.MINUTE);
		Intent intent = getIntent();
		click_key = intent.getStringExtra("click_key");
		if (click_key.equals("click_add")) {
			setContentView(R.layout.edit_record);
			dateBtn = (Button) findViewById(R.id.Date);
			timeBtn = (Button) findViewById(R.id.Time);
			isAlarmBtn = (CheckBox) findViewById(R.id.isAlarm);
			mText = (EditText) findViewById(R.id.mText);
			setBtn = (Button) findViewById(R.id.set);
			discardBtn = (Button) findViewById(R.id.discard);
			dateBtn.setText(mCalendar.get(Calendar.YEAR)+"年"+(mCalendar.get(Calendar.MONTH)+1)+"月"+mCalendar.get(Calendar.DAY_OF_MONTH)+"日");
			timeBtn.setText(mCalendar.get(Calendar.HOUR_OF_DAY)+"时"+mCalendar.get(Calendar.MINUTE)+"分");
			dateBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					mCalendar = Calendar.getInstance();
					// TODO Auto-generated method stub
					new DatePickerDialog(EditRecord.this,
							new DatePickerDialog.OnDateSetListener() {
								public void onDateSet(DatePicker view,int year, int monthOfYear,int dayOfMonth) {
									mAlarmCalendar.set(Calendar.YEAR, year);
									mAlarmCalendar.set(Calendar.MONTH,monthOfYear);
									mAlarmCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
									EditRecord.this.year = year;
									EditRecord.this.month = monthOfYear;
									EditRecord.this.day = dayOfMonth;
									dateBtn.setText(year + "年" + (month + 1)+ "月" + day + "日");
								}
							}, year, month, day).show();
				}
			});
			timeBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					mCalendar = Calendar.getInstance();
					new TimePickerDialog(EditRecord.this,
							new TimePickerDialog.OnTimeSetListener() {
								public void onTimeSet(TimePicker view,int hourOfDay, int minute) {
									mAlarmCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
									mAlarmCalendar.set(Calendar.MINUTE, minute);
									mAlarmCalendar.set(Calendar.SECOND, 0);
									mAlarmCalendar.set(Calendar.MILLISECOND, 0);
									EditRecord.this.hour = hourOfDay;
									EditRecord.this.minute = minute;
									timeBtn.setText(hourOfDay + "时" + minute+ "分");
								}
							}, hour, minute, false).show();
				}
			});
			isAlarmBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				@Override
	            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked){
						isAlarm = 1;
					}else{
						isAlarm = 0;
					}
	            }
	        });
//			mAlarmCalendar.set(Calendar.YEAR, year);
//			mAlarmCalendar.set(Calendar.MONTH, month);
//			mAlarmCalendar.set(Calendar.DAY_OF_MONTH, day);
//			mAlarmCalendar.set(Calendar.HOUR_OF_DAY, hour);
//			mAlarmCalendar.set(Calendar.MINUTE, minute);
//			mAlarmCalendar.set(Calendar.SECOND, 0);
//			mAlarmCalendar.set(Calendar.MILLISECOND, 0);
			setBtn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					// 
					String mtext = mText.getText().toString();
					String mtime = year + "年" + (month + 1) + "月" + day + "日" + hour + "时" + minute + "分";
					expireTime = TimeUtil.parseToDate(mtime);
					currentTime = new Date();
					if (expireTime.getTime() < currentTime.getTime()) {
						Toast.makeText(EditRecord.this, "",Toast.LENGTH_SHORT).show();
					} else {
//						addData(mtext, mtime, isAlarm);
						record.setContent(mtext);
						record.setCreateTime(mCalendar.get(Calendar.YEAR)+"年"+(mCalendar.get(Calendar.MONTH)+1)+"月"+mCalendar.get(Calendar.DAY_OF_MONTH)+"日"
								+mCalendar.get(Calendar.HOUR_OF_DAY)+"时"+mCalendar.get(Calendar.MINUTE)+"分");
						record.setExpireTime(mtime);
						record.setIsAlarm(isAlarm);
						record.setIsOld(0);
						new RecordDao(EditRecord.this).saveRecord(record);
						if (isAlarm == 1) {
							//设置闹钟
							/*
							mAlarm = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
							Intent intent = new Intent(EditRecord.this,AlarmReceiver.class);
							PendingIntent pendingIntent = PendingIntent.getBroadcast(EditRecord.this,(int) alarm_id, intent, 0);//
							mAlarm.set(AlarmManager.RTC_WAKEUP,
									mAlarmCalendar.getTimeInMillis(),
									pendingIntent);
									*/
						}
//						Intent mWidgetIntent = new Intent();
//						mWidgetIntent.setAction("com.ideal.note.widget");
//						EditRecord.this.sendBroadcast(mWidgetIntent);
						finish();
					}

				}
			});

			discardBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});
		}
	}

}
