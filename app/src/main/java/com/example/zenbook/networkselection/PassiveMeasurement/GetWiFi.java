package com.example.zenbook.networkselection.PassiveMeasurement;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZENBOOK on 5/29/2017.
 */

public class GetWiFi {
    private ArrayList<RANObject> objects = new ArrayList<>();
//    private RANObject ranObject;
    private int APCount = 0;
//    private int LinkSpeed = 0;
//    private String standard = "";
    
    public int getAPCount(){
        return APCount;
    }
    public ArrayList<RANObject> getObjects(){
        return objects;
    }
    public GetWiFi(WifiManager wifiManager, List<WifiConfiguration> savedNetwork){
//        if(!wifiManager.isWifiEnabled()){
//            wifiManager.setWifiEnabled(true);
//        }
        List<ScanResult> scanResults = wifiManager.getScanResults();
//        List<WifiConfiguration> savedNetwork = wifiManager.getConfiguredNetworks();
        try {
            for (WifiConfiguration saved : savedNetwork) {
                for (ScanResult find : scanResults) {
//                System.out.println(saved.SSID.substring(1,saved.SSID.length()-1) + " : " + find.SSID);
                    String savedSSID = saved.SSID.substring(1, saved.SSID.length() - 1);
                    if ((find.SSID).equals(savedSSID)) {
//                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                        APCount++;
//                        switch(find.frequency){
//                            case 2412:
//                                LinkSpeed = (11 + 54)/2;
//                                standard = "802.11b/g/n";
//                                break;
//                            case 2462:
//                                LinkSpeed = (11+100)/2;
//                                standard = "802.11b/n";
//                                break;
//                            case 2437:
//                            case 2484:
//                                LinkSpeed = 11;
//                                standard = "802.11b";
//                                break;
//                            case 2432:
//                            case 2452:
//                            case 2472:
//                                LinkSpeed = 54;
//                                standard = "802.11g/n";
//                                break;
//                            case 2422:
//                                LinkSpeed = 100;
//                                standard = "802.11n";
//                                break;
//                            default:
//                                LinkSpeed = 0;
//                                standard = "802.11x";
//                                break;
//                        }
                        System.out.println(APCount + ": " + find.SSID + " " + "// RSSi: " + find.level + ":" + WifiManager.calculateSignalLevel(find.level, 5) + "// BAND: " + find.frequency/1000);
                
//                        try {
//                            Global.fileOutputStream = new FileOutputStream(Global.file, true);
//                            Global.fileOutputStream.write((APCount + ": " + find.SSID + " " + "// RSSi: " + find.level + ":" + WifiManager.calculateSignalLevel(find.level, 5) + "// BAND: " + find.frequency).getBytes());
//                            Global.fileOutputStream.write("\n".getBytes());
//                            Global.fileOutputStream.close();
                            
//                            Global.resultFileOutputStream = new FileOutputStream(Global.resultFile, true);
//                            Global.resultFileOutputStream.write((find.SSID + "," + find.level + "," + find.frequency/1000).getBytes());
//                            Global.resultFileOutputStream.write("\n".getBytes());
//                            Global.resultFileOutputStream.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                
                        RANObject ranObject = new RANObject();
                        ranObject.setSSID(find.SSID);
                        // number 5 will return a number between 0 and 4 in calculateSignalLevel
                
                        ranObject.setRSSi(find.level + "");
//                    ranObject.setRSSi(WifiManager.calculateSignalLevel(find.level, 5) + "");

//                    System.out.println("Signal Level: " + WifiManager.calculateSignalLevel(find.level, 5));
                        ranObject.setBand((find.frequency/1000) + "");
                        objects.add(ranObject);
                        break;
                    }
                }
            }
        }catch (NullPointerException e){
            APCount = 0;
        }
        if(APCount == 0){
            if(savedNetwork != null) {
                System.out.println(savedNetwork.size());
            }else{
                System.out.println("savedNetwork is null");
            }
            System.out.println("no KNOWN network");
        }
    }
}
