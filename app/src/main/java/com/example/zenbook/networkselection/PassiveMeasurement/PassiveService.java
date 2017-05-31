package com.example.zenbook.networkselection.PassiveMeasurement;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.example.zenbook.networkselection.ActiveMeasurement.Delay.RoundTripTime;
import com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency.GetEnergyEfficiency;
import com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate.GetUDPSuccessRate;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.SelectNetwork;
import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

public class PassiveService extends Service {
    private static final int WIFI_FREQUENCY_BAND_AUTO = 0;
    private static final int WIFI_FREQUENCY_BAND_5GHZ = 1;
    private static final int WIFI_FREQUENCY_BAND_2GHZ = 2;
    private int notificationID = 1;
    private IBinder mBinder;
    /**
     * indicates whether onRebind should be used
     */
    private boolean mAllowRebind;
    private Notification n;
    private WifiManager wifiManager;
    private ArrayList<RANObject> SAVEDDATA;
    private RANObject CELLULAR;
    private int APCOUNT;
    
    public PassiveService() {
    }
    
    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
        System.out.println("Service Starting.....");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
            System.out.println("Enable");
            wifiManager.setWifiEnabled(true);
        }else{
            System.out.println("Already Enabled");
        }
        
        registerReceiver(new ScanReceiver(), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        Scanning();
        
        return START_STICKY;
    }
    
    private void Scanning(){
        System.out.println("--------------------------------------------------------------------------");
        //TODO: Cellular part
        Cellular cellular = new Cellular();
        //TODO: Wifi part
        GetWiFi getWiFi = new GetWiFi(wifiManager);
        
        boolean calculate = false;
        if(SAVEDDATA == null) {
            System.out.println("SAVE NEW SAVED DATA");
            SAVEDDATA = getWiFi.getObjects();
            APCOUNT = getWiFi.getAPCount();
            
            CELLULAR = cellular.getRanObject();
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//            System.out.println(CELLULAR.getSSID() + " : " + CELLULAR.getRSSi() + " : " + CELLULAR.getBand());
//            System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
    
            calculate = true;
        }else {
            if(!CELLULAR.getRSSi().equals(cellular.getRss()) || !CELLULAR.getBand().equals(cellular.getNetworkType())) {
                System.out.println("Cellular Changed");
                calculate = true;
            }
            if(!calculate) {
outterloop:
                for (RANObject savedData : SAVEDDATA) {
                    for (RANObject currentData : getWiFi.getObjects()) {
                        if (APCOUNT != getWiFi.getAPCount()) {
                            System.out.println("AP count change");
                            calculate = true;
                            break outterloop;
                        }
                        if (savedData.getSSID().equals(currentData.getSSID())) {
                            if (!savedData.getRSSi().equals(currentData.getRSSi())) {
//                            if (Math.abs(Integer.parseInt(savedData.getRSSi()) - Integer.parseInt(currentData.getRSSi())) > 10) {
                                System.out.println("RSSi change too much");
                                calculate = true;
                                break outterloop;
                            }
                            if (!savedData.getBand().equals(currentData.getBand())) {
                                System.out.println("Band change");
                                calculate = true;
                                break outterloop;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("--------------------------------------------------------------------------");
    
        if(calculate){
            SAVEDDATA = getWiFi.getObjects();
            new Thread() {
                @Override
                public void run() {
                    doActive();
                }
            }.start();
        }
        
        
    }
    
    private void doActive(){
        //TODO: edit here
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> savedNetwork = wifiManager.getConfiguredNetworks();
        for(RANObject ranObject : SAVEDDATA){
            //TODO: Connect to network
            for(WifiConfiguration wifiConfiguration : savedNetwork) {
                String save = wifiConfiguration.SSID.substring(1,wifiConfiguration.SSID.length()-1);
                if(ranObject.getSSID().equals(save)){
                    System.out.println("wifi priority: " + wifiConfiguration.priority);
                    wifiManager.disconnect();
                    wifiManager.updateNetwork(wifiConfiguration);
                    wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                    wifiManager.reconnect();
//                    System.out.println("SSID: " + wifiManager.getConnectionInfo().getSSID() + ", Save: " + save + ", network id: " + wifiConfiguration.networkId);
                    break;
                }
            }
            //TODO: end Connect to network
    
//            ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    
            
            int loop = 0;
            System.out.println("SSID: " + wifiManager.getConnectionInfo().getSSID() + ", Save: " + ranObject.getSSID());
            while (!Global.isConnected() || !wifiManager.getConnectionInfo().getSSID().substring(1,wifiManager.getConnectionInfo().getSSID().length()-1).equals(ranObject.getSSID())) {
                loop++;
                System.out.println(loop);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
    
            String ipAddressString;
            do {
                int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
    
                // Convert little-endian to big-endianif needed
                if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                    ipAddress = Integer.reverseBytes(ipAddress);
                }
    
                byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();
                try {
                    ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
                    Thread.sleep(1000);
                } catch (UnknownHostException ex) {
                    ipAddressString = null;
                } catch (InterruptedException e) {
                    ipAddressString = null;
                    e.printStackTrace();
                }
//                System.out.println(ipAddressString);
            }while (ipAddressString == null);
            
    
            //TODO: WiFi Active Measurement
            System.out.println("SSID: " + wifiManager.getConnectionInfo().getSSID() + ", Save: " + ranObject.getSSID());
            System.out.println("Connected");
            GetEnergyEfficiency getEnergyEfficiency = new GetEnergyEfficiency(Global.activity, ranObject);
            getEnergyEfficiency.Start("WIFI");
            new RoundTripTime(ranObject);
            new GetUDPSuccessRate(ranObject);
            getEnergyEfficiency.Stop();
            
            System.out.println("---------------------------------------------------------------");
            System.out.println(ranObject.getSSID());
            System.out.println(wifiManager.getConnectionInfo().getSSID());
            System.out.println("Energy Efficiency: " + ranObject.getEnergyEfficiency());
            System.out.println("UDP Succes Rate: " + ranObject.getUDPSuccessRate());
            System.out.println("Delay: " + ranObject.getDelay());
            System.out.println("---------------------------------------------------------------");
            //TODO: End WiFi Active Measurement
        }
        //TODO: Cellular Active Measurement
        wifiManager.setWifiEnabled(false);
        String ipAddressString;
        do {
            int ipAddress = wifiManager.getConnectionInfo().getIpAddress();
        
            // Convert little-endian to big-endianif needed
            if (ByteOrder.nativeOrder().equals(ByteOrder.LITTLE_ENDIAN)) {
                ipAddress = Integer.reverseBytes(ipAddress);
            }
        
            byte[] ipByteArray = BigInteger.valueOf(ipAddress).toByteArray();
            try {
                ipAddressString = InetAddress.getByAddress(ipByteArray).getHostAddress();
                Thread.sleep(1000);
            } catch (UnknownHostException ex) {
                ipAddressString = null;
            } catch (InterruptedException e) {
                ipAddressString = null;
                e.printStackTrace();
            }
//                System.out.println(ipAddressString);
        }while (ipAddressString == null);
        
        GetEnergyEfficiency getEnergyEfficiency = new GetEnergyEfficiency(Global.activity, CELLULAR);
        getEnergyEfficiency.Start("CELLULAR");
        new RoundTripTime(CELLULAR);
        new GetUDPSuccessRate(CELLULAR);
        getEnergyEfficiency.Stop();
    
        System.out.println("---------------------------------------------------------------");
        System.out.println(CELLULAR.getSSID());
        System.out.println("Energy Efficiency: " + CELLULAR.getEnergyEfficiency());
        System.out.println("UDP Succes Rate: " + CELLULAR.getUDPSuccessRate());
        System.out.println("Delay: " + CELLULAR.getDelay());
        System.out.println("---------------------------------------------------------------");
        //TODO: END Cellular Active Measurement

//
//        objects.add(ranObject);

//        //TODO: Simulation here
//
//        ranObject = new RANObject();
//        ranObject.setSSID("Faform");
//        ranObject.setRSSi("-70");
//        ranObject.setBand("2.4");
//
//        ranObject.setEnergyEfficiency("0.9");
//        ranObject.setUDPSuccessRate("0.977");
//        ranObject.setDelay("0.822");
//
//        objects.add(ranObject);
//        //TODO
//
//        ranObject = new RANObject();
//        ranObject.setSSID("form");
//        ranObject.setRSSi("-70");
//        ranObject.setBand("2.4");
//
//        ranObject.setEnergyEfficiency("0.87");
//        ranObject.setUDPSuccessRate("0.989");
//        ranObject.setDelay("0.810");
//
//        objects.add(ranObject);
//        //TODO
//
//        ranObject = new RANObject();
//        ranObject.setSSID("Faform");
//        ranObject.setRSSi("-70");
//        ranObject.setBand("2.4");
//
//        ranObject.setEnergyEfficiency("0.89");
//        ranObject.setUDPSuccessRate("0.988");
//        ranObject.setDelay("0.866");
//
//        objects.add(ranObject);
//        //TODO: end simulation here
        SAVEDDATA.add(0,CELLULAR);
        SelectNetwork selectNetwork = new SelectNetwork(SAVEDDATA);
        if(!SAVEDDATA.get(selectNetwork.getRANObject()).getSSID().equals("CELLULAR")) {
            wifiManager.setWifiEnabled(true);
            List<ScanResult> scanResults = wifiManager.getScanResults();
            while (scanResults.size() < 1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (WifiConfiguration wifiConfiguration : savedNetwork) {
                String save = wifiConfiguration.SSID.substring(1, wifiConfiguration.SSID.length() - 1);
                if (SAVEDDATA.get(selectNetwork.getRANObject()).getSSID().equals(save)) {
                    System.out.println("Selected Networkkkkkkkkkkkk");
                    wifiManager.disconnect();
                    wifiManager.updateNetwork(wifiConfiguration);
                    wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                    wifiManager.reconnect();
                    break;
                }
            }
        }
    }
    
    private class ScanReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Scanning();
        }
    }
    
    /**
     * Called when all clients have unbound with unbindService()
     */
    @Override
    public boolean onUnbind(Intent intent) {
        return mAllowRebind;
    }
    
    /**
     * Called when a client is binding to the service with bindService()
     */
    @Override
    public void onRebind(Intent intent) {
    }
    
    /**
     * Called when The service is no longer used and is being destroyed
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        wifiManager.setWifiEnabled(false);
    }
}
