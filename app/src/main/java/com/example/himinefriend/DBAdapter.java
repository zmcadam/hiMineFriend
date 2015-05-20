package com.example.himinefriend;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter {

	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	public static final String KEY_NUMBER = "number";
	public static final String KEY_IMAGE = "image";
	private static final String DB_TABLE = "peopleinfo";
	
	private final Context context;
	private DBOpenHelper dbOpenHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context context) {
		this.context = context;
		DBOpenHelper helper = new DBOpenHelper(this.context);
		
	}
	
	public void open() throws SQLiteException {
		dbOpenHelper = new DBOpenHelper(context);
		try {
			db = dbOpenHelper.getWritableDatabase();
		} catch(SQLiteException ex) {
			db = dbOpenHelper.getReadableDatabase();
		}
	}
	
	public void close() {
		if(db != null) {
			if(db != null) {
				db.close();
				db = null;
			}
		}
	}
	
	public long insert(People people) {
		ContentValues newValues = new ContentValues();
		
		newValues.put(KEY_NAME, people.Name);
		newValues.put(KEY_NUMBER, people.Number);
		newValues.put(KEY_IMAGE, people.Image);
		
		return db.insert(DB_TABLE, null, newValues);
	}
	
	public long deleteAllData() {
		return db.delete(DB_TABLE, null, null);
	}
	
	public long deleteOneData(long id) {
		DBOpenHelper helper = new DBOpenHelper(this.context);
		db=helper.getWritableDatabase();
		String whereClause=KEY_ID+"=?";
		String[] whereArgs={ Long.toString(id) };
		
       return db.delete(DB_TABLE, whereClause, whereArgs);	
		//return db.delete(DB_TABLE, KEY_ID+"="+id, null);
	}
	
	public Cursor queryAllData() {
		DBOpenHelper helper = new DBOpenHelper(this.context);
		db=helper.getWritableDatabase();
		Cursor results = db.query(DB_TABLE, new String[] {KEY_ID,
				KEY_NAME,KEY_NUMBER,KEY_IMAGE},null,null,null,null,null);
		return results;
	}
	
	public Cursor queryOneData(long id) {
		
		DBOpenHelper helper = new DBOpenHelper(this.context);
		db=helper.getWritableDatabase();	
		Cursor results = db.query(DB_TABLE, new String[] { KEY_ID, KEY_NAME, KEY_NUMBER, KEY_IMAGE}, KEY_ID + "=" + id, null, null, null, null);
		return results;

	}

	public long updateOneData(long id, People people) {
		ContentValues updateValues = new ContentValues();	  
		updateValues.put(KEY_NAME, people.Name);
		updateValues.put(KEY_NUMBER, people.Number);
		updateValues.put(KEY_IMAGE, people.Image);	
		return db.update(DB_TABLE, updateValues,  KEY_ID + "=" + id, null);
	}
	private People ConvertToPeople(Cursor cursor) {
		People peo= new People();
		return peo;
	}

	public long insert(String nAME, String nUMBER) {
		DBOpenHelper helper = new DBOpenHelper(this.context);
		db=helper.getWritableDatabase();
		
		ContentValues newValues = new ContentValues();
		
		newValues.put(KEY_NAME, nAME);
		newValues.put(KEY_NUMBER, nUMBER);
		
		return db.insert(DB_TABLE, null, newValues);
	}
}

