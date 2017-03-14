package com.example.administrator.childreneduction.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Administrator on 2017/3/14 0014.
 */

public class NetUtils {

    /**
     * @param mContext
     * @return
     */
    public static boolean isNetWorkConnection(Context mContext) {
        if (mContext != null) {
            ConnectivityManager mConnetcion = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConnetcion.getActiveNetworkInfo();
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }

    /**
     * @param mContext
     * @return
     */
    public static boolean isWIFIConnection(Context mContext) {
        if (mContext != null ){
            ConnectivityManager mConnetcion = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = mConnetcion.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (info != null) {
                return info.isAvailable();
            }
        }
        return false;
    }
}
