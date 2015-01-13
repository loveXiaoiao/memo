package com.xiao.memo.receiver;

import com.xiao.memo.activity.BootAlarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String action = intent.getAction();
		if(action.equals(Intent.ACTION_BOOT_COMPLETED)){
			Intent i = new Intent(context, BootAlarm.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//这一句很重要，不然会出错
			context.startActivity(i);
		}
	}

}
