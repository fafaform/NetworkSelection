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
import android.os.Environment;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

import com.example.zenbook.networkselection.ActiveMeasurement.Delay.RoundTripTime;
import com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency.GetEnergyEfficiency;
import com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate.GetUDPSuccessRate;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.SelectNetwork;
import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteOrder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import static android.R.attr.max;

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
    private boolean doScanning;
    private WifiManager wifiManager;
    private ArrayList<RANObject> SAVEDDATA;
    private RANObject CELLULAR;
    private int APCOUNT;
    private List<WifiConfiguration> savedNetwork;
    
    private long processingTime;
    
    public PassiveService() {
    }
    
    /** Called when the service is being created. */
    @Override
    public void onCreate() {
        doScanning = true;
        createFile();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    
    @Override
    public int onStartCommand(Intent intent, int flags, int startid) {
//        System.out.println("Service Starting.....");
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(!wifiManager.isWifiEnabled()){
//            System.out.println("Enable");
            wifiManager.setWifiEnabled(true);
        }else{
//            System.out.println("Already Enabled");
        }
        savedNetwork = wifiManager.getConfiguredNetworks();
    
    
        new Thread(new Runnable() {
            @Override
            public void run() {
                alwaysScanWifi();
            }
        }).start();
        
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        intentFilter.addAction(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE);
//        registerReceiver(new ScanReceiver(), new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
//        registerReceiver(new ScanReceiver(), new IntentFilter(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE));
        registerReceiver(new ScanReceiver(){
            @Override
            public void onReceive(Context context, Intent intent1){
                if(doScanning) {
                    doScanning = false;
                    Scanning();
                }
            }
        }, intentFilter);
        
//        if(doScanning) {
//            doScanning = false;
//            System.out.println("Time doing scanning");
//            Scanning();
//        }
        
        return START_STICKY;
    }
    
    private void Scanning(){
        //TODO: Start text Write to File
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        processingTime = dateFormat.getCalendar();
        processingTime = dateFormat.getCalendar().getTime().getTime();
        Date date = new Date();
        try {
            Global.fileOutputStream = new FileOutputStream(Global.file, true);
            Global.fileOutputStream.write((dateFormat.format(date) + "+++++++++++++++++++++++++++++++++++++++++++++++").getBytes());
            Global.fileOutputStream.write("\n".getBytes());
            Global.fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: End Start text Write to File
        System.out.println("--------------------------------------------------------------------------");
        //TODO: Cellular part
        Cellular cellular = new Cellular();
        //TODO: Wifi part
        GetWiFi getWiFi = new GetWiFi(wifiManager, savedNetwork);
        
        boolean calculate = false;
        if(SAVEDDATA == null) {
            System.out.println("SAVE NEW SAVED DATA");
            calculate = true;

            try {
                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                Global.fileOutputStream.write(("SAVE NEW SAVED DATA").getBytes());
                Global.fileOutputStream.write("\n".getBytes());
                Global.fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if(!CELLULAR.getRSSi().equals(cellular.getRss()) || !CELLULAR.getBand().equals(cellular.getNetworkType())) {
                System.out.println("Cellular Changed: " + CELLULAR.getRSSi() + ":" + cellular.getRss() + ", " + CELLULAR.getBand() + ":" + cellular.getNetworkType());
                calculate = true;
    
                try {
                    Global.fileOutputStream = new FileOutputStream(Global.file, true);
                    Global.fileOutputStream.write(("Cellular Changed: " + CELLULAR.getRSSi() + ":" + cellular.getRss() + ", " + CELLULAR.getBand() + ":" + cellular.getNetworkType()).getBytes());
                    Global.fileOutputStream.write("\n".getBytes());
                    Global.fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(!calculate) {
outterloop:
                for (RANObject savedData : SAVEDDATA) {
                    for (RANObject currentData : getWiFi.getObjects()) {
                        if (APCOUNT != getWiFi.getAPCount()) {
                            System.out.println("AP count change (from, to): " + "(" + APCOUNT + ", " + getWiFi.getAPCount() + ")");
                            calculate = true;
    
                            try {
                                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                Global.fileOutputStream.write(("AP count change (from, to): " + "(" + APCOUNT + ", " + getWiFi.getAPCount() + ")").getBytes());
                                Global.fileOutputStream.write("\n".getBytes());
                                Global.fileOutputStream.close();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            
                            break outterloop;
                        }
                        if (savedData.getSSID().equals(currentData.getSSID())) {
                            if (!savedData.getRSSi().equals(currentData.getRSSi())) {
//                            if (Math.abs(Integer.parseInt(savedData.getRSSi()) - Integer.parseInt(currentData.getRSSi())) > 10) {
                                System.out.println("RSSi change too much of: " + currentData.getSSID());
                                calculate = true;
    
                                try {
                                    Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                    Global.fileOutputStream.write(("RSSi change too much of: " + currentData.getSSID()).getBytes());
                                    Global.fileOutputStream.write("\n".getBytes());
                                    Global.fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                
                                break outterloop;
                            }
                            if (!savedData.getBand().equals(currentData.getBand())) {
                                System.out.println("Band change of: " + currentData.getSSID());
                                calculate = true;
    
                                try {
                                    Global.fileOutputStream = new FileOutputStream(Global.file, true);
                                    Global.fileOutputStream.write(("Band change of: " + currentData.getSSID()).getBytes());
                                    Global.fileOutputStream.write("\n".getBytes());
                                    Global.fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                
                                break outterloop;
                            }
                        }
                    }
                }
            }
        }
        System.out.println("--------------------------------------------------------------------------");
    
        if(calculate){
            SAVEDDATA = new ArrayList<>();
            CELLULAR = new RANObject();
            
            SAVEDDATA = getWiFi.getObjects();
            CELLULAR = cellular.getRanObject();
            APCOUNT = getWiFi.getAPCount();
            new Thread() {
                @Override
                public void run() {
                    doActive();
                    doScanning = true;
                    //TODO: End text Write to File
                    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//                    int number = dateFormat.getCalendar().compareTo(processingTime);
                    processingTime = dateFormat.getCalendar().getTime().getTime() - processingTime;
                    Date date = new Date();
                    try {
                        Global.fileOutputStream = new FileOutputStream(Global.file, true);
                        Global.fileOutputStream.write(("Processing Time: " + TimeUnit.MILLISECONDS.toMinutes(processingTime) + ":" + (TimeUnit.MILLISECONDS.toSeconds(processingTime) % 60) + "\n" + dateFormat.format(date) + "+++++++++++++++++++++++++++++++++++++++++++++++").getBytes());
                        Global.fileOutputStream.write("\n".getBytes());
                        Global.fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO: End End text Write to File
                }
            }.start();
        }else {
            doScanning = true;
        }
        
    }
    
    private void doActive(){
        //TODO: edit here
        if(wifiManager.isWifiEnabled() == false){
            wifiManager.setWifiEnabled(true);
        }
        List<WifiConfiguration> savedNetwork = wifiManager.getConfiguredNetworks();
        Iterator<RANObject> iterator = SAVEDDATA.iterator();
        while(iterator.hasNext()){
            RANObject ranObject = iterator.next();
//        for(RANObject ranObject : SAVEDDATA){
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
            
            //TODO: Write to file
            String writeTo = "---------------------------------------------------------------" + "\n";
            writeTo += wifiManager.getConnectionInfo().getSSID() + "\n";
            writeTo += "Energy Efficiency: " + ranObject.getEnergyEfficiency() + "\n";
            writeTo += "UDP Succes Rate: " + ranObject.getUDPSuccessRate() + "\n";
            writeTo += "Delay: " + ranObject.getDelay() + "\n";
            writeTo += "---------------------------------------------------------------";
            try {
                Global.fileOutputStream = new FileOutputStream(Global.file, true);
                Global.fileOutputStream.write((writeTo).getBytes());
                Global.fileOutputStream.write("\n".getBytes());
                Global.fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            //TODO: End Write to File
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
    
        TelephonyManager telephonyManager = (TelephonyManager) Global.activity.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        while(telephonyManager.getDataState() != TelephonyManager.DATA_CONNECTED){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        
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
        //TODO: Write to file
        String writeTo = "---------------------------------------------------------------" + "\n";
        writeTo += CELLULAR.getSSID() + "\n";
        writeTo += "Energy Efficiency: " + CELLULAR.getEnergyEfficiency() + "\n";
        writeTo += "UDP Succes Rate: " + CELLULAR.getUDPSuccessRate() + "\n";
        writeTo += "Delay: " + CELLULAR.getDelay() + "\n";
        writeTo += "---------------------------------------------------------------";
        try {
            Global.fileOutputStream = new FileOutputStream(Global.file, true);
            Global.fileOutputStream.write((writeTo).getBytes());
            Global.fileOutputStream.write("\n".getBytes());
            Global.fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //TODO: End Write to File
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
        
        //TODO: Network Selection Start Here
        SAVEDDATA.add(0,CELLULAR);
        SelectNetwork selectNetwork = new SelectNetwork(SAVEDDATA);
        if(!SAVEDDATA.get(selectNetwork.getRANObject()).getSSID().equals("Cellular")) {
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
                    System.out.println("Selected Network: " + save);
    
                    //TODO: Write to file
                    try {
                        Global.fileOutputStream = new FileOutputStream(Global.file, true);
                        Global.fileOutputStream.write(("Selected Network: " + save).getBytes());
                        Global.fileOutputStream.write("\n".getBytes());
                        Global.fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO: End Write to File
                    
                    wifiManager.disconnect();
                    wifiManager.updateNetwork(wifiConfiguration);
                    wifiManager.enableNetwork(wifiConfiguration.networkId, true);
                    wifiManager.reconnect();
                    break;
                }
            }
        }else{
            wifiManager.setWifiEnabled(false);
        }
    }
    
    private void createFile(){
        File externalD = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
        if (!externalD.exists()) {
//            System.out.println("Created Folder");
            File dir = new File(Environment.getExternalStorageDirectory() + "/Download/");
            dir.mkdirs();
            Global.file = new File(externalD, "์์NetworkSelection.txt");
            try {
                Global.file.createNewFile();
                Global.fileOutputStream = new FileOutputStream(Global.file,false);
                Global.fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
//            System.out.println("Already have folder: " + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath());
            if(!new File(externalD + "์์NetworkSelection.txt").exists()){
//                System.out.println("Created File");
                Global.file = new File(externalD, "์์NetworkSelection.txt");
                try {
                    Global.file.createNewFile();
                    Global.fileOutputStream = new FileOutputStream(Global.file,false);
                    Global.fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                System.out.println(file.exists());
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
//        wifiManager.setWifiEnabled(false);
    }
    
    private void alwaysScanWifi(){
        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                // Your database code here
                if(wifiManager.isWifiEnabled() == false && doScanning){
                    wifiManager.startScan();
//                    wifiManager.setWifiEnabled(true);
                }
            }
        }, 5*60*1000, 5*60*1000);
    }
}
