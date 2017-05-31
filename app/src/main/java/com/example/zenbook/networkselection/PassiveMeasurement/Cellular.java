package com.example.zenbook.networkselection.PassiveMeasurement;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.CellIdentityCdma;
import android.telephony.CellIdentityGsm;
import android.telephony.CellIdentityLte;
import android.telephony.CellIdentityWcdma;
import android.telephony.CellInfoCdma;
import android.telephony.CellInfoGsm;
import android.telephony.CellInfoLte;
import android.telephony.CellInfoWcdma;
import android.telephony.CellSignalStrengthCdma;
import android.telephony.CellSignalStrengthGsm;
import android.telephony.CellSignalStrengthLte;
import android.telephony.CellSignalStrengthWcdma;
import android.telephony.TelephonyManager;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import static android.content.Context.CONNECTIVITY_SERVICE;

/**
 * Created by ZENBOOK on 5/29/2017.
 */

public class Cellular {
    private String networkType;
    private double rss;
    private RANObject ranObject;
    
    public RANObject getRanObject(){
        return ranObject;
    }
    
    public String getNetworkType() {
        return networkType;
    }
    public String getRss() {
        return rss + "";
    }
    
    public Cellular(){
        //TODO: Cellular part
        TelephonyManager telephonyManager = (TelephonyManager) Global.activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        WifiManager wifiManager = (WifiManager) Global.activity.getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        if(telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
            System.out.println("SIM Available and cellular available");
            ConnectivityManager manager = (ConnectivityManager) Global.activity.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = null;
            if (manager != null) {
                // TODO: 9/18/2016 For API 17+
                rss = 0;
                networkType = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_LTE) {
                        System.out.println("LTE");
                        CellInfoLte cellinfogsm = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthLte cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                        CellIdentityLte cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCi()+"";
                        rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                        networkType = "LTE";
                    } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_UMTS) {
                        System.out.println("UMTS");
                        CellInfoWcdma cellinfogsm = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthWcdma cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                        CellIdentityWcdma cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                        rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                        networkType = "UMTS";
                    } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_CDMA) {
                        System.out.println("CDMA");
                        CellInfoCdma cellinfogsm = (CellInfoCdma) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthCdma cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                        CellIdentityCdma cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getBasestationId()+"";
                        rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                        networkType = "CDMA";
                    } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_EDGE) {
                        System.out.println("EDGE");
                        CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthGsm cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                        CellIdentityGsm cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                        rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                        networkType = "EDGE";
                    } else {
                        System.out.println("UNKNOWN");
                        CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                        CellSignalStrengthGsm cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                        CellIdentityGsm cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                        rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                        networkType = "UNKNOWN";
                    }
            
                }
                ranObject = new RANObject();
                ranObject.setSSID("Cellular");
                ranObject.setRSSi(rss + "");
                ranObject.setBand(telephonyManager.getNetworkType() + "");
            } else {
                System.out.println("CELLULAR NOT CONNECT");
            }
        }else{
            System.out.println("SIM STATE ABSENT");
        }
    }
}
