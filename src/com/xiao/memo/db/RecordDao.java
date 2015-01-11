package com.xiao.memo.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
//		Log.i("fdasfa", String.valueOf(cursor.getInt(1)));
		while (cursor.moveToNext()) {
			Log.i("cursor_id",cursor.getString(cursor.getColumnIndex("_id")));
			Record record = new Record();
			record.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex("_id"))));
			record.setContent(cursor.getString(cursor.getColumnIndex("content")));
			record.setCreateTime(cursor.getString(cursor.getColumnIndex("createTime")));
			record.setExpireTime(cursor.getString(cursor.getColumnIndex("expireTime")));
			record.setIsAlarm(Integer.parseInt(cursor.getString(cursor.getColumnIndex("isAlarm"))));
			record.setIsOld(Integer.parseInt(cursor.getString(cursor.getColumnIndex("isOld"))));
			records.add(record);
		}
		db.close();
		return records;
	}
	
	public long saveRecord(Record record){
		long id = 0;
		ContentValues cv = new ContentValues();
		cv.put("content", record.getContent());
		cv.put("expireTime", record.getExpireTime());
		cv.put("isalarm", record.getIsAlarm());
		cv.put("createTime",record.getCreateTime());
		cv.put("isOld", record.getIsOld());
		dbHelper = new DatabaseHelper(context, "memo.sql");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		id = db.insert("record", null, cv);
		db.close();
		return id;
	}
	
	public void updateRecord(Record record){
		ContentValues cv = new ContentValues();
		cv.put("content", record.getContent());
		cv.put("expireTime", record.getExpireTime());
		cv.put("isalarm", record.getIsAlarm());
		cv.put("createTime",record.getCreateTime());
		cv.put("isOld", record.getIsOld());
		dbHelper = new DatabaseHelper(context, "memo.sql");
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		db.update("record", cv, "_id=?", new String[] { String.valueOf(record.getId()) });
	}

}
