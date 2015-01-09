package com.xiao.memo.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xiao.memo.entiy.Record;

public class RecordDao {
	DatabaseHelper dbHelper;
	private final Context context;

	public RecordDao(Context context) {
		this.context = context;
	}
	public List<Record> getRecordList(String[] columns, String selection, String[] selectionArgs, String orderBy) 
	{
		List<Record> records = new ArrayList<Record>();
		dbHelper = new DatabaseHelper(context, "memo.sql");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		//groupBy和having默认为null
		Cursor cursor = db.query("record", columns, selection, selectionArgs, null, null, orderBy);
		while (cursor.moveToNext()) {
			Record record = new Record();
			record.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
			record.setContent(cursor.getString(cursor.getColumnIndex("content")));
			record.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
			record.setExpireTime(cursor.getString(cursor.getColumnIndex("expireTime")));
			record.setIsAlarm(Integer.parseInt(cursor.getString(cursor.getColumnIndex("isAlarm"))));
			record.setIsOld(Integer.parseInt(cursor.getString(cursor.getColumnIndex("isOld"))));
			records.add(record);
		}
		return records;
	}
	
	public void saveRecord(Record record){
		ContentValues cv = new ContentValues();
		cv.put("content", record.getContent());
		cv.put("expireTime", record.getExpireTime());
		cv.put("isalarm", record.getIsAlarm());
		cv.put("createTime",record.getCreateTime());
		cv.put("isOld", record.getIsOld());
		dbHelper = new DatabaseHelper(context, "memo.sql");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.insert("record", null, cv);
		db.close();
		
	}

}
