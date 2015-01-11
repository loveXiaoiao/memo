package com.xiao.memo.activity;

import com.xiao.memo.service.AlarmService;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

public class Alarm extends Activity {
	String mtime = "";
	String mtext = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState); 
		//下面是为了在锁屏时也能启动闹铃
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
                                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
		// 播放闹铃
		Intent intentSV = new Intent(Alarm.this, AlarmService.class);
		startService(intentSV);
	}

}
