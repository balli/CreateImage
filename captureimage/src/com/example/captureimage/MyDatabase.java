package com.example.captureimage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDatabase {
	
	private static final int ID=1;
	private static final String NAME="name";
	private static final String IMAGE="image";
	
	private static final String DATABASE_NAME="ImageLoad";
	private static final String TABLE_NAME="P_info";
	private static final int DATABASE_VERSION=1;
	
	private static final String CREATE_TABLE="create table P_info(id integer primary key autoincrement,name text not null,image text not null);";

	private final Context context;
	private DatabaseHelper DBHhelper;
	private SQLiteDatabase db;
	
	
	public MyDatabase(Context ctx)
	{
		this.context=ctx;
		DBHhelper=new DatabaseHelper(ctx);
		
	}
	
	public static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_TABLE);
			System.out.println("Table is created");

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS tiles");
			onCreate(db);
		}

	}
	
	public MyDatabase open() throws SQLException {
		db = DBHhelper.getWritableDatabase();
		return null;

	}

	public void close() {

		DBHhelper.close();
	}

	public long insert(String name2, String imagebytecode) {
		// TODO Auto-generated method stub
		
		ContentValues init = new ContentValues();
		init.put(NAME, name2);
		init.put(IMAGE, imagebytecode);
		
		return db.insert(TABLE_NAME, null, init);
	}

	public Cursor shows() {
		String query = "select name,image,id from P_info;";
		Cursor C = db.rawQuery(query, null);
		return C;
	}
	
	public void deleteitem(int name) {

		Log.e("name : ", "" +name);
		
		//String query = "delete from P_info where name=" + name;
		String query="delete name,image from P_info where name=" + name;
		db.execSQL(query);

	}
}
