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
                        System.out.println(APCount + ": " + find.SSID + " " + "// RSSi: " + find.level + ":" + WifiManager.calculateSignalLevel(find.level, 5) + "// BAND: " + find.frequency);
                
                        try {
                            Global.fileOutputStream = new FileOutputStream(Global.file, true);
                            Global.fileOutputStream.write((APCount + ": " + find.SSID + " " + "// RSSi: " + find.level + ":" + WifiManager.calculateSignalLevel(find.level, 5) + "// BAND: " + find.frequency).getBytes());
                            Global.fileOutputStream.write("\n".getBytes());
                            Global.fileOutputStream.close();
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                
                        RANObject ranObject = new RANObject();
                        ranObject.setSSID(find.SSID);
                        // number 5 will return a number between 0 and 4 in calculateSignalLevel
                
                        ranObject.setRSSi(find.level + "");
//                    ranObject.setRSSi(WifiManager.calculateSignalLevel(find.level, 5) + "");

//                    System.out.println("Signal Level: " + WifiManager.calculateSignalLevel(find.level, 5));
                        ranObject.setBand(find.frequency + "");
                        objects.add(ranObject);
                        break;
                    }
                }
            }
        }catch (NullPointerException e){
            APCount = 0;
        }
        if(APCount == 0){
            System.out.println("Problem now");
        }
    }
}
