package com.example.junli.studentperformancemanagement.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySqliteHelper extends SQLiteOpenHelper {


    public MySqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public MySqliteHelper(Context context){
        super(context,Constant.DATABASE_NAME,null,Constant.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table "+Constant.TABLE_NAME+"("
                +Constant._ID+" Integer primary key,"
                +Constant.NAME+" varchar(10),"
                +Constant.GRADES+" Integer,"
                +Constant.MIDGRADES+" Integer,"
                +Constant.FIANLGRADES+" Integer"
                +")";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
