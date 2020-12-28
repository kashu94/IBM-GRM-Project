package com.example.ibm_proj2;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;

/**
 * Created by ProgrammingKnowledge on 4/3/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "IBM.db";
    public static String TABLE_NAME = "app";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "TOPIC";
    public static final String COL_4 = "DATE_TIME";
    public static final String COL_5 = "SUM";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,TOPIC TEXT,DATE_TIME DATE,SUM NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String topic, String date, String sum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,topic);
        contentValues.put(COL_4,date);
        contentValues.put(COL_5,sum);

        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

//    public Cursor getAllData() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
//        return res;
//    }

    public boolean updatesum(String username,int sum) {



        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        //contentValues.put(COL_1,id);
//        contentValues.put(COL_3,topic);
//        contentValues.put(COL_4,date);
        contentValues.put(COL_5,sum);

        db.update(TABLE_NAME, contentValues, "NAME = ?",new String[] { username });
        return true;
    }

    public int select(int index){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor c = db.rawQuery("SELECT SUM FROM "+TABLE_NAME+" WHERE ID="+index+" ;", null);
        int sum = 0;
        while(c.moveToNext()){

            sum = c.getInt(0);

        }
        c.close();

        return sum;


    }

//    public Integer deleteData (String id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(TABLE_NAME, "ID = ?",new String[] {id});
//    }
}