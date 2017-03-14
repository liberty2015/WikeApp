package com.liberty.wikepro.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Locale;

/**
 * Created by liberty on 2017/3/14.
 */

public class NetUtils {

    public static enum NetType{
        WIFI,CMNET,CMWAP,NONE
    }

    public static boolean isNetworkAvailable(Context context){
        ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info=manager.getAllNetworkInfo();
        if (info!=null){
            for (int i = 0; i < info.length; i++) {
                if (info[i].getState()==NetworkInfo.State.CONNECTED){
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=manager.getActiveNetworkInfo();
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isMobileConnected(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (networkInfo!=null){
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static int getConnectedType(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=manager.getActiveNetworkInfo();
            if (networkInfo!=null){
                return networkInfo.getType();
            }
        }
        return -1;
    }

    public static NetType getAPNType(Context context){
        if (context!=null){
            ConnectivityManager manager= (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=manager.getActiveNetworkInfo();
            if (networkInfo==null){
                return NetType.NONE;
            }
            int nType=networkInfo.getType();
            if (nType==ConnectivityManager.TYPE_MOBILE){
                if (networkInfo.getExtraInfo().toLowerCase(Locale.getDefault()).equals("cmnet")){
                    return NetType.CMNET;
                }else {
                    return NetType.CMWAP;
                }
            }else if (nType==ConnectivityManager.TYPE_WIFI){
                return NetType.WIFI;
            }
        }
        return NetType.NONE;
    }
}
