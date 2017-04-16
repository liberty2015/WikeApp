package com.liberty.wikepro.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LinJinFeng on 2017/2/20.
 */

public interface PreferenceHelper {
    PreferenceHelper putBoolean(String key, boolean value);

    PreferenceHelper putString(String key, String value);

    PreferenceHelper putInt(String key, int value);

    PreferenceHelper putFloat(String key, float value);

    PreferenceHelper putStringList(String key, List<String> values);

    ArrayList<String> getStringList(String key);

    boolean getBoolean(String key, boolean defValue);

    int getInt(String key, int defValue);

    double getFloat(String key, float defValue);

    String getString(String key, String defValue);

    void clear();
}
