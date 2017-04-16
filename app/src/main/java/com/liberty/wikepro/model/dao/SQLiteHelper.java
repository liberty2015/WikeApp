package com.liberty.wikepro.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private String SQL= "create table history(_id Integer primary key AUTOINCREMENT," +
            "course_id integer not null," +
            "course varchar(255) not null," +
            "chapter_id integer not null," +
            "chapter varchar(255) not null," +
            "cvideo varchar(255) not null," +
            "cvideo_id integer not null," +
            "time datetime," +
            "stu_id integer not null)";
    private String  dropSQL="drop table if exists history";

    public SQLiteHelper(Context context, String name, int version) {
        super(context, name, null, version);
        Log.d("SQLiteHelper","-----SQLiteHelper-----");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL);
        Log.d("SQLiteHelper","-----onCreate-----");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropSQL);
        onCreate(db);
        Log.d("SQLiteHelper","-----onUpgrade-----");
    }
}
