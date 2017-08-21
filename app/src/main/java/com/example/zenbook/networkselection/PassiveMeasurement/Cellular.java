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
import android.telephony.gsm.GsmCellLocation;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.SYSTEM_HEALTH_SERVICE;

/**
 * Created by ZENBOOK on 5/29/2017.
 */

public class Cellular {
    private String networkType;
    private double rss;
    private int rssi;
    private RANObject ranObject;
    
    public RANObject getRanObject(){
        return ranObject;
    }
    
    public String getNetworkType() {
        return networkType;
    }
    public String getRss() {
        return rssi + "";
    }
    
    public Cellular(){
        try {
            //TODO: Cellular part
            TelephonyManager telephonyManager = (TelephonyManager) Global.activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);

            if (telephonyManager.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
                ConnectivityManager manager = (ConnectivityManager) Global.activity.getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = null;
                if (manager != null) {
                    // TODO: 9/18/2016 For API 17+
                    rss = 0;
                    rssi = 0;
                    networkType = "";
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_LTE) {
                            CellInfoLte cellinfogsm = (CellInfoLte) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthLte cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityLte cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCi()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: LTE");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: LTE" + "// MCC: " + cellIdentityLte.getMcc() + ", MNC: " + cellIdentityLte.getMnc() + ", CI: " + cellIdentityLte.getCi() + ", LAC: " + cellIdentityLte.getTac()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println("MCC: " + cellIdentityLte.getMcc() + ", MNC: " + cellIdentityLte.getMnc() + ", CI: " + cellIdentityLte.getCi() + ", LAC: " + cellIdentityLte.getTac());
                        } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_UMTS) {
                            CellInfoWcdma cellinfogsm = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthWcdma cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityWcdma cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: UMTS");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: UMTS" + "// " + cellinfogsm.getCellIdentity().getCid()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(cellinfogsm.getCellIdentity().getCid());
                        } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_CDMA) {
                            CellInfoCdma cellinfogsm = (CellInfoCdma) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthCdma cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityCdma cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getBasestationId()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: CDMA");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: CDMA" + "// " + cellinfogsm.getCellIdentity().getBasestationId()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(cellinfogsm.getCellIdentity().getBasestationId());
                        } else if (telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_EDGE) {
                            CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthGsm cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityGsm cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: EDGE");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: EDGE" + "// " + cellinfogsm.getCellIdentity().getCid()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(cellinfogsm.getCellIdentity().getCid());
                        }else if(telephonyManager.getNetworkType() == telephonyManager.NETWORK_TYPE_HSDPA){
                            CellInfoWcdma cellinfogsm = (CellInfoWcdma) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthWcdma cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityWcdma cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: UNKNOWN");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: UNKNOW" + "// " + cellinfogsm.getCellIdentity().getCid()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(cellinfogsm.getCellIdentity().getCid());
                        } else {
                            CellInfoGsm cellinfogsm = (CellInfoGsm) telephonyManager.getAllCellInfo().get(0);
                            CellSignalStrengthGsm cellSignalStrengthLte = cellinfogsm.getCellSignalStrength();
                            CellIdentityGsm cellIdentityLte = cellinfogsm.getCellIdentity();
//                    cid = cellIdentityLte.getCid()+"";
                            rss = Double.parseDouble(cellSignalStrengthLte.getLevel() + "");
                            rssi = cellSignalStrengthLte.getDbm();
                            networkType = telephonyManager.getNetworkType() + "";
                            System.out.println("CELLULAR " + "// RSSi: " + rssi + ":" + rss + "// BAND: UNKNOWN");
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("CELLULAR " + "// RSSi: " + rssi + "// BAND: UNKNOW" + "// " + cellinfogsm.getCellIdentity().getCid()).getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            System.out.println(cellinfogsm.getCellIdentity().getCid());
                        }
                
                    }
                    ranObject = new RANObject();
                    ranObject.setSSID("Cellular");
                    ranObject.setRSSi(rssi + "");
                    ranObject.setBand(telephonyManager.getNetworkType() + "");
//                    try {
//                        Global.resultFileOutputStream = new FileOutputStream(Global.resultFile, true);
//                        Global.resultFileOutputStream.write(("Cellular" + "," + rssi + "," + telephonyManager.getNetworkType()).getBytes());
//                        Global.resultFileOutputStream.write("\n".getBytes());
//                        Global.resultFileOutputStream.close();
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                } else {
//                System.out.println("CELLULAR NOT CONNECT");
                    ranObject = new RANObject();
                    ranObject.setSSID("Cellular");
                    ranObject.setRSSi("0");
                    ranObject.setBand("0");
                }
            } else {
//                ranObject = new RANObject();
//                ranObject.setSSID("Cellular");
//                ranObject.setRSSi("0");
//                ranObject.setBand("0");
            System.out.println("SIM STATE ABSENT or UNKNOWN");
            }
        }catch (NullPointerException e){
//            e.printStackTrace();
        }
    }
    
    private boolean isCellularEnable(){
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) Global.activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean)method.invoke(cm);
//            System.out.println("CELLULAR ENABLE");
            return true;
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
//            System.out.println("CELLULAR NOT ENABLE");
            return false;
        }
    }
}
