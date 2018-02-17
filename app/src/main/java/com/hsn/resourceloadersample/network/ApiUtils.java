package com.hsn.resourceloadersample.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by hassanshakeel on 7/28/17.
 */


public class ApiUtils {


    public static final boolean isInternetOn(Context con) {
        try {
            ConnectivityManager conMgr = (ConnectivityManager) con
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = conMgr.getActiveNetworkInfo();
            return (info != null && info.isConnected());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }


}
