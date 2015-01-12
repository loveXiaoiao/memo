package com.xiao.memo.receiver;

import com.xiao.memo.activity.Alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String id = intent.getStringExtra("record_id");
		Log.i("record_id", id);
		Intent i = new Intent(context, Alarm.class);
		i.putExtra("record_id", id);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这一句很重要，不然会出错
		context.startActivity(i);
	}

}
