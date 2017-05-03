package com.liberty.wikepro.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by LinJinFeng on 2017/2/27.
 */

public class SQLiteHelper extends SQLiteOpenHelper {

    private String SearchSQL="create table IF NOT EXISTS recentQuery(_id Integer primary key AUTOINCREMENT," +
            "query varchar(255) not null);";
    private String HistorySQL=
            "create table IF NOT EXISTS history(_id Integer primary key AUTOINCREMENT," +
            "course_id integer not null," +
            "course varchar(255) not null," +
            "chapter_id integer not null," +
            "chapter varchar(255) not null," +
            "cvideo varchar(255) not null," +
            "cvideo_id integer not null," +
            "time datetime," +
            "stu_id integer not null);";
    private String  dropHistorySQL="drop table if exists history;";
    private String dropSearchSQL="drop table if exists recentQuery;";

    public SQLiteHelper(Context context, String name, int version) {
        super(context, name, null, version);
        Log.d("SQLiteHelper","-----SQLiteHelper-----");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(HistorySQL);
        db.execSQL(SearchSQL);
        Log.d("SQLiteHelper","-----onCreate-----");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(dropHistorySQL);
        db.execSQL(dropSearchSQL);
        onCreate(db);
        Log.d("SQLiteHelper","-----onUpgrade-----");
    }
}
