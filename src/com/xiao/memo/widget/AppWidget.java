package com.xiao.memo.widget;

import java.util.List;

import com.xiao.memo.R;
import com.xiao.memo.activity.MainActivity;
import com.xiao.memo.db.RecordDao;
import com.xiao.memo.entiy.Record;
import com.xiao.memo.util.TimeUtil;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class AppWidget extends AppWidgetProvider {
	String[] desk_text;
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		List<Record> records= new RecordDao(context).getRecordList(null, "isOld=?", new String[] { "0" }, "expireTime asc");
		desk_text = new String[3];
		for(int i=0;i<records.size();i++){
			if(i == 3)
				break;
			if(records.get(i) != null){
				String temp = records.get(i).getContent();
				if(temp.length() > 15){
					temp = temp.substring(0, 15) + "...";
				}
				desk_text[i] = temp + "\n"+ getDaysString(records.get(i).getExpireTime());
				Log.i("dest_top", desk_text[i]);
			}
		}
		final int Num = appWidgetIds.length;
		for (int i = 0; i < Num; i++) {
			 int appWidgetId = appWidgetIds[i];
		      // Create an Intent to launch ExampleActivity
		      Intent intent = new Intent(context, MainActivity.class);
		      PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
		      // Get the layout for the App Widget and attach an on-click listener
		      // to the button
		      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
		      views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);
		      // To update a label
		      views.setTextViewText(R.id.desktop_text, array_to_string(desk_text));
		      // Tell the AppWidgetManager to perform an update on the current app
		      // widget
		      appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
		if(intent.getAction().equals("com.xiao.memo.widget")){
			List<Record> records= new RecordDao(context).getRecordList(null, "isOld=?", new String[] { "0" }, "expireTime asc");
			desk_text = new String[3];
			for(int i=0;i<records.size();i++){
				if(records.get(i) != null){
					if(i == 3)
						break;
					String temp = records.get(i).getContent();
					if(temp.length() > 15){
						temp = temp.substring(0, 15) + "...";
					}
					desk_text[i] = temp + "\n"+ getDaysString(records.get(i).getExpireTime());
				}
			}
			RemoteViews mRemoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
			mRemoteViews.setTextViewText(R.id.desktop_text, array_to_string(desk_text));
			AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context, AppWidget.class), mRemoteViews);	
		}
	}
	public String getDaysString(String date){
		String result = "";
		long days = TimeUtil.getDays(TimeUtil.parseToDate(date));
		if(days > 0){
			result =  "还有"+days+"天";
		}
		if(days == 0){
			result =  "就是今天";
		}
		return result;
	}
	//数组转化为字符显示
		public String array_to_string(String[] array){
			String temp_str = "";
			for(int i=0;i<array.length;i++){
				if(array[i]==null){
					break;
				}else {
					temp_str = temp_str+"\n* "+array[i];
				}
			}
			System.out.println("--->"+temp_str);
			return  temp_str;
		}

}
