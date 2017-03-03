package com.liberty.wikepro.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by LinJinFeng on 2017/2/20.
 */

public class AppPreferenceHelper implements PreferenceHelper {

    private static final ThreadLocal<AppPreferenceHelper> preferenceLocal=
            new ThreadLocal<AppPreferenceHelper>();

//    private static AppPreferenceHelper instance;

    private String name;

    private Context mContext;

    private AppPreferenceHelper(Context context,String name){
        this.name=name;
        this.mContext=context;
    }

//    private AppPreferenceHelper(Context context){
//        this.mContext=context;
//    }

    public static AppPreferenceHelper getInstance(Context context, String dbName) {
        AppPreferenceHelper appPreferenceHelper = preferenceLocal.get();
        if (appPreferenceHelper==null){
            preferenceLocal.set(new AppPreferenceHelper(context,dbName));
        }else if (!appPreferenceHelper.name.equals(dbName)){
            appPreferenceHelper.name=dbName;
        }
        return appPreferenceHelper;
//        if (instance==null){
////            synchronized (AppPreferenceHelper.class){
//                instance=new AppPreferenceHelper(context.getApplicationContext(),dbName);
////            }
//        }else if (!instance.name.equals(dbName)){
//            instance.name=dbName;
//        }
//        return instance;
    }

    @Override
    public AppPreferenceHelper putBoolean(String key, boolean value) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(key,value).apply();
        return this;
    }

    @Override
    public AppPreferenceHelper putString(String key, String value) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putString(key,value).apply();
        return this;
    }

    @Override
    public AppPreferenceHelper putInt(String key, int value) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putInt(key,value).apply();
        return this;
    }

    @Override
    public AppPreferenceHelper putFloat(String key, float value) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        preferences.edit().putFloat(key,value).apply();
        return this;
    }

    @Override
    public PreferenceHelper putStringList(String key, List<String> values) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        Set<String> stringSet=new HashSet<>();
        for (String value:values){
            stringSet.add(value);
        }
        preferences.edit().putStringSet(key,stringSet).apply();
        return this;
    }

    @Override
    public ArrayList<String> getStringList(String key) {
        ArrayList<String> list=new ArrayList<>();
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        list.addAll(preferences.getStringSet(key,new HashSet<String>()));
        return list;
    }


    @Override
    public boolean getBoolean(String key, boolean defValue) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defValue);
    }

    @Override
    public int getInt(String key, int defValue) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getInt(key, defValue);
    }

    @Override
    public double getFloat(String key, float defValue) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getFloat(key, defValue);
    }

    @Override
    public String getString(String key, String defValue) {
        SharedPreferences preferences=mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }
}
