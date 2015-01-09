package com.xiao.memo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	String DATABASE_CREATE = "create table record(_id INTEGER PRIMARY KEY AUTOINCREMENT,content varchar(8000),createTime varchar(200), expireTime varchar(200), isAlarm INTEGER, isOld INTEGER)";

	public DatabaseHelper(Context context, String name) {
		super(context, name, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
