package com.example.zenbook.networkselection.PassiveMeasurement;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

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
    public GetWiFi(WifiManager wifiManager){
        List<ScanResult> scanResults = wifiManager.getScanResults();
        List<WifiConfiguration> savedNetwork = wifiManager.getConfiguredNetworks();
        for (WifiConfiguration saved : savedNetwork) {
            for (ScanResult find : scanResults) {
//                System.out.println(saved.SSID.substring(1,saved.SSID.length()-1) + " : " + find.SSID);
                String savedSSID = saved.SSID.substring(1,saved.SSID.length()-1);
                if ((find.SSID).equals(savedSSID)) {
                    APCount++;
                    System.out.println(APCount + ": " + find.SSID);
                    RANObject ranObject = new RANObject();
                    ranObject.setSSID(find.SSID);
                    // number 5 will return a number between 0 and 4 in calculateSignalLevel
                    ranObject.setRSSi(WifiManager.calculateSignalLevel(find.level, 5) + "");
//                    System.out.println("Signal Level: " + WifiManager.calculateSignalLevel(find.level, 5));
                    ranObject.setBand(find.frequency + "");
                    objects.add(ranObject);
                    break;
                }
            }
        }
        if(APCount == 0){
            System.out.println("Problem now");
        }
    }
}
