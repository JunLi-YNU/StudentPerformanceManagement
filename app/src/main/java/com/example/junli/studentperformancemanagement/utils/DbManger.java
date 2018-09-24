package com.example.junli.studentperformancemanagement.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.junli.studentperformancemanagement.bean.Student;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DbManger {
    private static MySqliteHelper helper;

    public static  MySqliteHelper getIntance(Context context){
        if(helper==null){
            helper = new MySqliteHelper(context);
        }
        return helper;
    }
    //执行SQL语句
    public static void exeSQL(SQLiteDatabase db, String sql){
        if(db!=null){
            if(sql!=null && !"".equals(sql)){
                db.execSQL(sql);
            }
        }
    }
    //执行查询操作
    public static Cursor selectDataBySql(SQLiteDatabase db,String sql,String[] selectionArgs){
        Cursor cursor = null;
        if(db!=null){
            cursor = db.rawQuery(sql,selectionArgs);
        }
        return cursor;
    }
    //将cursor
    public static List<Student> cursorToList(Cursor cursor) {
        List <Student> list = new ArrayList <>();
        while (cursor.moveToNext()) {
        int ColumnIndex = cursor.getColumnIndex(Constant._ID);
        int _id = cursor.getInt(ColumnIndex);
        String name = cursor.getString(cursor.getColumnIndex(Constant.NAME));
        Student student = new Student(_id, name);
        list.add(student);
    }
            return list;

    }
}
