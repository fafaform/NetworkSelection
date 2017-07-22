package com.example.zenbook.networkselection.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import java.io.File;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class Global {
//    public static String IPAddress = "192.168.1.45";
//    public static String IPAddress = "202.29.148.77";
    public static String IPAddress = "nbtc.ee.psu.ac.th";
//    public static String IPAddress = "172.31.137.193";
    
    public static File file;
    public static FileOutputStream fileOutputStream;
    
    public static Activity activity;
    public static Bundle savedInstanceState;
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)Global.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        
        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }
}
