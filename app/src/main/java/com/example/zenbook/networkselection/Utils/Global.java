package com.example.zenbook.networkselection.Utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.net.InetAddress;
import java.util.ArrayList;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class Global {
    //            InetAddress IPAddress = InetAddress.getByName("172.31.193.231");
//    InetAddress IPAddress = InetAddress.getByName("192.168.1.46");
    //            InetAddress IPAddress = InetAddress.getByName("172.31.16.5");
//            InetAddress IPAddress = InetAddress.getByName("nbtc.ee.psu.ac.th");
    public static String IPAddress = "192.168.1.40";
    public static Activity activity;
    public static boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager)Global.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connectivityManager != null) {
            networkInfo = connectivityManager.getActiveNetworkInfo();
        }
        
        return networkInfo != null && networkInfo.getState() == NetworkInfo.State.CONNECTED;
    }
}
