package com.liberty.wikepro.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import java.util.ArrayList;

/**
 * Created by liberty on 2017/3/14.
 */

public class NetStateReceiver extends BroadcastReceiver {

    private final static String CUSTOM_ANDROID_NET_CHANGE_ACTION="com.liberty.wike.CONNECTIVITY_CHANGE";
    private final static String ANDROID_NET_CHANGE_ACTION="android.net.conn.CONNECTIVITY_CHANGE";
    private static BroadcastReceiver mBroadcastReceiver;
    private static boolean isNetAvailable=false;
    private static NetUtils.NetType mNetType;
    private static ArrayList<NetChangeObserver> mNetChangeObservers=new ArrayList<>();

    private static BroadcastReceiver getReceiver() {
        if (null == mBroadcastReceiver) {
            synchronized (NetStateReceiver.class) {
                if (null == mBroadcastReceiver) {
                    mBroadcastReceiver = new NetStateReceiver();
                }
            }
        }
        return mBroadcastReceiver;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equalsIgnoreCase(ANDROID_NET_CHANGE_ACTION)){
            if (!NetUtils.isNetworkAvailable(context)){
                isNetAvailable=false;
            }else {
                isNetAvailable=true;
                mNetType = NetUtils.getAPNType(context);
            }
        }
    }

    public static void registerNetworkStateReceiver(Context mContext) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        filter.addAction(ANDROID_NET_CHANGE_ACTION);
        mContext.getApplicationContext().registerReceiver(getReceiver(), filter);
    }

    public static void checkNetworkState(Context mContext) {
        Intent intent = new Intent();
        intent.setAction(CUSTOM_ANDROID_NET_CHANGE_ACTION);
        mContext.sendBroadcast(intent);
    }

    public static void unRegisterNetworkStateReceiver(Context mContext) {
        if (mBroadcastReceiver != null) {
            try {
                mContext.getApplicationContext().unregisterReceiver(mBroadcastReceiver);
            } catch (Exception e) {

            }
        }

    }

    public static boolean isNetworkAvailable() {
        return isNetAvailable;
    }

    public static NetUtils.NetType getAPNType() {
        return mNetType;
    }

    private void notifyObserver() {
        if (!mNetChangeObservers.isEmpty()) {
            int size = mNetChangeObservers.size();
            for (int i = 0; i < size; i++) {
                NetChangeObserver observer = mNetChangeObservers.get(i);
                if (observer != null) {
                    if (isNetworkAvailable()) {
                        observer.onNetConnected(mNetType);
                    } else {
                        observer.onNetDisconnect();
                    }
                }
            }
        }
    }

    /**
     * 添加网络监听
     *
     * @param observer
     */
    public static void registerObserver(NetChangeObserver observer) {
        if (mNetChangeObservers == null) {
            mNetChangeObservers = new ArrayList<NetChangeObserver>();
        }
        mNetChangeObservers.add(observer);
    }

    /**
     * 移除网络监听
     *
     * @param observer
     */
    public static void removeRegisterObserver(NetChangeObserver observer) {
        if (mNetChangeObservers != null) {
            if (mNetChangeObservers.contains(observer)) {
                mNetChangeObservers.remove(observer);
            }
        }
    }
}
