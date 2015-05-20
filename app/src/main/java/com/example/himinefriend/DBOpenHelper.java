package com.example.himinefriend;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{
	
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_IMAGE = "image";
	private static final String DB_NAME = "people.db";
	private static final String DB_TABLE = "peopleinfo";
	private static final int DB_VERSION = 1;
	
	public DBOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}
	String str = "create table DB_TABLE (KEY_ID integer primary key autoincrement,KEY_NAME text not null, KEY_NUMBER integer, KEY_IMAGE text);";
	private static final String DB_CREATE =
			"create table "+ DB_TABLE + "(" + KEY_ID + " integer primary key autoincrement," 
        	+KEY_NAME + " text not null," + KEY_NUMBER + " long," + KEY_IMAGE + " text);";
	
	@Override
	public void onCreate(SQLiteDatabase _db) {
		_db.execSQL(DB_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion) {
		_db.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
		onCreate(_db);
	}
}





