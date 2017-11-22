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
//    public static String IPAddress = "192.168.1.11";
//    public static String IPAddress = "202.29.148.77";
//    public static String IPAddress = "nbtcrehab.eng.psu.ac.th";
    public static String IPAddress = "nbtc.ee.psu.ac.th";
//    public static String IPAddress = "172.31.137.193";
//    public static double MIN_ENERGY = 0.97;
    public static double MIN_ENERGY = 0;
//    public static double MAX_ENERGY = 10.0;
    public static double MAX_ENERGY = 4.217;
    public static boolean ALWAYS_DO_ACTIVE = false;
    public static int number_of_udp_packet = 2000;
    public static int DELAY_TH = 250;
    
//    public static File file;
//    public static FileOutputStream fileOutputStream;
    
    public static File logFile;
    public static FileOutputStream logFileOutputStream;
    
    public static File resultFile;
    public static FileOutputStream resultFileOutputStream;
    
//    public static File energyFile;
//    public static FileOutputStream energyFileOutputStream;
    
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
