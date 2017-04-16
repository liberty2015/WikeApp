package com.liberty.wikepro.util;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by liberty on 2017/3/15.
 */

public class ToastHelper {

    public static void showToast(Context context,String message,boolean isLong){
        Toast.makeText(context,message,isLong?Toast.LENGTH_LONG:Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message){
        showToast(context, message,true);
    }
    
    public static void showCenterToast(Context context,String message,View view){
        Toast toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(view);
        toast.show();
    }
}
