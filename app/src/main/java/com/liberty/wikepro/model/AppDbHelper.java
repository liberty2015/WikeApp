package com.liberty.wikepro.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.liberty.wikepro.model.bean.Search;
import com.liberty.wikepro.model.bean.history;
import com.liberty.wikepro.model.dao.SQLiteHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by liberty on 2017/4/5.
 */

public class AppDbHelper{

    private static AppDbHelper instance;

    private SQLiteHelper helper;

    private static final String DB_name="wike_db";

    private static final int version=9;

    private static final String insertSQL="insert into history(course_id,course,chapter_id,chapter,cvideo_id,cvideo,time,stu_id) values(?,?,?,?,?,?,?,?)";

    private static final String insertSearch="insert into recentQuery(query) values(?)";

    private static final String selectSQL="select * from history where stu_id=?";

    private static final String selectSearch="select * from recentQuery";

    private static final String selectSQLbyTime="select * from history where stu_id=? order by time desc";

    private static final String deleteSQL="delete from history where _id=? ";

    private static final String deleteSearch="delete from recentQuery";

    private SimpleDateFormat dateFormat;

    private AppDbHelper(Context context){
        Log.d("SQLiteHelper","-----AppDbHelper-----");
        helper=new SQLiteHelper(context,DB_name,version);
        int currentVersion=helper.getWritableDatabase().getVersion();
        if (currentVersion<version){
            helper.onUpgrade(helper.getWritableDatabase(),currentVersion,version);
        }
        /**
         * 关于SQLite datetime类型，写入时可以使用date或者java.sql.timestamp类型直接写入，但是当读取时需要先以string类型读取出来，
         * 然后通过以下格式的SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy",Locale.US)转化为Date类型：
         * 其中 E：星期  z：时区,
         * 这里需要指出，使用timestamp类型写入，它默认以yyyy-MM-dd HH:mm:ss.ffff格式
         */
        dateFormat=new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+:08:00"));
    }

    public static AppDbHelper getInstance(Context context){
        if (instance==null){
            synchronized (AppDbHelper.class){
                if (instance==null){
                    instance=new AppDbHelper(context);
                }
            }
        }
        return instance;
    }


    public void insertBean(history bean) {
        helper.getWritableDatabase().execSQL(insertSQL,
                new Object[]{bean.getCourse_id(),
                bean.getCourse(),
                bean.getChapter_id(),
                bean.getChapter(),
                bean.getCvideo_id(),
                bean.getCvideo(),
                bean.getTime(),
                bean.getStu_id()});
    }

    public void insertQuery(Search search){
        SQLiteDatabase writableDatabase = helper.getWritableDatabase();
        Cursor cursor = writableDatabase.rawQuery("select * from recentQuery where query=?", new String[]{search.getQuery()});
        if (!cursor.moveToFirst()){
            helper.getWritableDatabase().execSQL(insertSearch,new Object[]{search.getQuery()});
        }
    }

    public List<history> selectBeans(history clazz){
        Cursor cursor = helper.getReadableDatabase().rawQuery(selectSQL, new String[]{Integer.toString(clazz.getStu_id())});
        List<history> histories=new ArrayList<>();
        if (cursor.moveToFirst()){
            try{
                do{
                    history history=new history();
                    history.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    history.setCourse_id(cursor.getInt(cursor.getColumnIndex("course_id")));
                    history.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                    history.setChapter(cursor.getString(cursor.getColumnIndex("chapter")));
                    history.setChapter_id(cursor.getInt(cursor.getColumnIndex("chapter_id")));
                    String dateTime=cursor.getString(cursor.getColumnIndex("time"));
                    Date time=dateFormat.parse(dateTime);
                    history.setTime(time);
                    histories.add(history);
                }while (cursor.moveToNext());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cursor.close();
            }
        }
        return histories;
    }

    public List<Search> selectSearchs(){
        Cursor cursor=helper.getReadableDatabase().rawQuery(selectSearch,null);
        List<Search> searches=new ArrayList<>();
        if (cursor.moveToFirst()){
            do{
                Search search=new Search();
                search.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                search.setQuery(cursor.getString(cursor.getColumnIndex("query")));
                searches.add(search);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return searches;
    }

    public List<history> selectBeansByTime(history clazz){
        Cursor cursor = helper.getReadableDatabase().rawQuery(selectSQLbyTime, new String[]{Integer.toString(clazz.getStu_id())});
        List<history> histories=new ArrayList<>();
        if (cursor.getCount()>0&&cursor.moveToFirst()){
            try{
                do{
                    history history=new history();
                    history.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    history.setCourse_id(cursor.getInt(cursor.getColumnIndex("course_id")));
                    history.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                    history.setCvideo_id(cursor.getInt(cursor.getColumnIndex("cvideo_id")));
                    history.setCvideo(cursor.getString(cursor.getColumnIndex("cvideo")));
                    history.setChapter(cursor.getString(cursor.getColumnIndex("chapter")));
                    history.setChapter_id(cursor.getInt(cursor.getColumnIndex("chapter_id")));
                    history.setStu_id(cursor.getInt(cursor.getColumnIndex("stu_id")));
                    String dateTime=cursor.getString(cursor.getColumnIndex("time"));
                    Log.d("dateTime",dateTime);
                    Date time=dateFormat.parse(dateTime);
                    history.setTime(time);
                    histories.add(history);
                }while (cursor.moveToNext());
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                cursor.close();
            }
        }
        return histories;
    }

    public void updateBean(history bean){
        helper.getWritableDatabase().execSQL("update history set chapter_id=?,chapter=?,cvideo=?,cvideo_id=?,time=? where stu_id=? and course_id=? and course=?",
                new Object[]{bean.getChapter_id(),bean.getChapter(),
                        bean.getCvideo(),bean.getCvideo_id(),bean.getTime(),bean.getStu_id(),bean.getCourse_id(),bean.getCourse()});
    }


    public history selectBean(history n) {
        Cursor cursor=helper.getReadableDatabase().rawQuery("select * from history where course_id=? and stu_id=?",
                new String[]{Integer.toString(n.getCourse_id()),Integer.toString(n.getStu_id())});
        if (cursor.moveToFirst()){
            history history=new history();
            try {
                history.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                history.setCourse_id(cursor.getInt(cursor.getColumnIndex("course_id")));
                history.setCourse(cursor.getString(cursor.getColumnIndex("course")));
                history.setChapter(cursor.getString(cursor.getColumnIndex("chapter")));
                history.setChapter_id(cursor.getInt(cursor.getColumnIndex("chapter_id")));
                String dateTime=cursor.getString(cursor.getColumnIndex("time"));
                Date time= null;
                time = dateFormat.parse(dateTime);
                history.setTime(time);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return history;
        }else {
            return null;
        }
    }


    public void deleteBean(history bean) {
        helper.getWritableDatabase().execSQL(deleteSQL, new Object[]{bean.get_id()});
    }

    public void deleteSearch(){
        helper.getWritableDatabase().execSQL(deleteSearch);
    }
}
