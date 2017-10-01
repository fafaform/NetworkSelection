package com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency;
//parcelable ICounterService;
        
import android.app.Activity;
import android.app.Application;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Arrays;

import edu.umich.PowerTutor.service.UMLoggerService;
import edu.umich.PowerTutor.service.UidInfo;
import edu.umich.PowerTutor.service.ICounterService;
import edu.umich.PowerTutor.ui.PowerTop;
import edu.umich.PowerTutor.util.Counter;
import edu.umich.PowerTutor.util.SystemInfo;

import static edu.umich.PowerTutor.ui.PowerTop.KEY_AVERAGE_POWER;
import static edu.umich.PowerTutor.ui.PowerTop.KEY_CURRENT_POWER;
import static edu.umich.PowerTutor.ui.PowerTop.KEY_TOTAL_ENERGY;

/**
 * Created by ZENBOOK on 5/25/2017.
 */

public class GetEnergyEfficiency {
    private static final double HIDE_UID_THRESHOLD = 0.1;
    private RANObject ranObject;
//    private EnergyCalculation energyCalculation;
    private SharedPreferences prefs;
    private Activity activity;
    private Bundle savedInstanceState;
//    private ICounterService counterService;
    private String[] componentNames;
    private CounterServiceConnection conn;
    private Intent serviceIntent;
    private ICounterService counterService;
    private int noUidMask;
    private double preEnergy;
    private double energy;
    private double power;
    public GetEnergyEfficiency(Activity activity, RANObject ranObject, Bundle savedInstanceState){
        this.ranObject = ranObject;
        this.activity = activity;
        this.savedInstanceState = savedInstanceState;
        
        prefs = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        serviceIntent = new Intent(activity.getApplicationContext(), UMLoggerService.class);
        conn = new CounterServiceConnection();
    
        activity.getApplicationContext().bindService(serviceIntent, conn, 0);
    
        if(savedInstanceState != null) {
            componentNames = savedInstanceState.getStringArray("componentNames");
            noUidMask = savedInstanceState.getInt("noUidMask");
        }
    }
    
    public void Start(String networkType) {
//        energyCalculation = new EnergyCalculation("WIFI");
//        energyCalculation = new EnergyCalculation("CELLULAR");
//        energyCalculation = new EnergyCalculation(networkType);
        
        //Power tutor here
        preEnergy = 0.0;
        energy = 0.0;
        refreshView();
        preEnergy = energy;
    }
    
    private void refreshView() {
        while (counterService == null){
            System.out.println("looping");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
//        int keyId = prefs.getInt("topKeyId", KEY_CURRENT_POWER);
        int keyId = prefs.getInt("topKeyId", KEY_TOTAL_ENERGY);
        try {
            byte[] rawUidInfo = counterService.getUidInfo(
                    prefs.getInt("topWindowType", Counter.WINDOW_TOTAL),
                    noUidMask | prefs.getInt("topIgnoreMask", 0));
            if(rawUidInfo != null) {
                UidInfo[] uidInfos = (UidInfo[])new ObjectInputStream(
                        new ByteArrayInputStream(rawUidInfo)).readObject();
                double total = 0;
                for(UidInfo uidInfo : uidInfos) {
                    if(uidInfo.uid == SystemInfo.AID_ALL) continue;
                    switch(keyId) {
                        case KEY_CURRENT_POWER:
                            uidInfo.key = uidInfo.currentPower;
                            uidInfo.unit = "W";
                            break;
                        case KEY_AVERAGE_POWER:
                            uidInfo.key = uidInfo.totalEnergy /
                                    (uidInfo.runtime == 0 ? 1 : uidInfo.runtime);
                            uidInfo.unit = "W";
                            break;
                        case KEY_TOTAL_ENERGY:
                            uidInfo.key = uidInfo.totalEnergy;
                            uidInfo.unit = "J";
                            break;
                        default:
                            uidInfo.key = uidInfo.currentPower;
                            uidInfo.unit = "W";
                    }
                    total += uidInfo.key;
                }
                if(total == 0) total = 1;
                for(UidInfo uidInfo : uidInfos) {
                    uidInfo.percentage = 100.0 * uidInfo.key / total;
                }
                Arrays.sort(uidInfos);

                int sz = 0;
                for(int i = 0; i < uidInfos.length; i++) {
                    if(uidInfos[i].uid == SystemInfo.AID_ALL ||
                            uidInfos[i].percentage < HIDE_UID_THRESHOLD) {
                        continue;
                    }
                    init(uidInfos[i], keyId);
                }
            }
        } catch(IOException e) {
        } catch(RemoteException e) {
        } catch(ClassNotFoundException e) {
        } catch(ClassCastException e) {
        }
    }
    public void init(UidInfo uidInfo, int keyType) {
        SystemInfo sysInfo = SystemInfo.getInstance();
        PackageManager pm = activity.getApplicationContext().getPackageManager();
        //TODO: app name here
        String name = sysInfo.getUidName(uidInfo.uid, pm);
        String prefix;
        double multiplyer = 1;
        if(uidInfo.key > 1e12) {
            prefix = "G";
            uidInfo.key /= 1e12;
            multiplyer = 1000000000;
        } else if(uidInfo.key > 1e9) {
            prefix = "M";
            uidInfo.key /= 1e9;
            multiplyer = 1000000;
        } else if(uidInfo.key > 1e6) {
            prefix = "k";
            uidInfo.key /= 1e6;
            multiplyer = 1000;
        } else if(uidInfo.key > 1e3) {
            prefix = "";
            uidInfo.key /= 1e3;
            multiplyer = 1;
        } else {
            prefix = "m";
            multiplyer = 0.001;
        }
//        long secs = (long)Math.round(uidInfo.runtime);
    
        if(name.equals("Network Selection")) {
            System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
//            System.out.println(name);
            System.out.println(uidInfo.key + " " + prefix + uidInfo.unit);
            energy = uidInfo.key * multiplyer;
            System.out.println(energy);
            System.out.println("*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/*/");
        }
//        textView.setText(String.format("%1$.1f%% [%3$d:%4$02d:%5$02d] %2$s\n" +
//                        "%6$.1f %7$s%8$s",
//                uidInfo.percentage, name, secs / 60 / 60, (secs / 60) % 60,
//                secs % 60, uidInfo.key, prefix, uidInfo.unit));
    }
    
    public double getJoule(){
        return power;
    }
    
    public void Stop(){
//        BatteryManager batteryManager = new BatteryManager();
//        double power = (energyCalculation.stop())/3.7;
    
        energy = 0.0;
        refreshView();
        power = 0.0;
        power = energy - preEnergy;
        System.out.println("Energy usage (Joule): " + power);
    
//        try {
//            Global.energyFileOutputStream = new FileOutputStream(Global.energyFile, true);
//            Global.energyFileOutputStream.write((power + "").getBytes());
//            Global.energyFileOutputStream.write("\n".getBytes());
//            Global.energyFileOutputStream.close();
//        } catch (FileNotFoundException fe) {
//            fe.printStackTrace();
//        } catch (IOException ie) {
//            ie.printStackTrace();
//        }
//        System.out.println("Energy usage (Vary Joule): " + power/(2400*3600)/100);
//        ranObject.setEnergyEfficiency((100 - power/(2400*3600))/100 + "");
        if(power > Global.MAX_ENERGY){
            ranObject.setEnergyEfficiency("0.0");
        }else if(power < Global.MIN_ENERGY){
            ranObject.setEnergyEfficiency("1.0");
        }else {
            System.out.println(1 - (power - Global.MIN_ENERGY) / (Global.MAX_ENERGY - Global.MIN_ENERGY));
            ranObject.setEnergyEfficiency(1 - ((power - Global.MIN_ENERGY) / (Global.MAX_ENERGY - Global.MIN_ENERGY)) + "");
        }
    }
    
    
    private class CounterServiceConnection implements ServiceConnection {
        public void onServiceConnected(ComponentName className,
                                       IBinder boundService ) {
            counterService = ICounterService.Stub.asInterface((IBinder)boundService);
            try {
                componentNames = counterService.getComponents();
                noUidMask = counterService.getNoUidMask();
//                filterGroup.removeAllViews();
                for(int i = 0; i < componentNames.length; i++) {
                    int ignMask = prefs.getInt("topIgnoreMask", 0);
                    if((noUidMask & 1 << i) != 0) continue;
//                    final TextView filterToggle = new TextView(PowerTop.this);
//                    final int index = i;
//                    filterToggle.setText(componentNames[i]);
//                    filterToggle.setGravity(Gravity.CENTER);
//                    filterToggle.setTextColor((ignMask & 1 << index) == 0 ?
//                            0xFFFFFFFF : 0xFF888888);
//                    filterToggle.setBackgroundColor(
//                            filterGroup.getChildCount() % 2 == 0 ? 0xFF444444 : 0xFF555555);
//                    filterToggle.setFocusable(true);
//                    filterToggle.setOnClickListener(new View.OnClickListener() {
//                        public void onClick(View v) {
//                            int ignMask = prefs.getInt("topIgnoreMask", 0);
//                            if((ignMask & 1 << index) == 0) {
//                                prefs.edit().putInt("topIgnoreMask", ignMask | 1 << index)
//                                        .commit();
//                                filterToggle.setTextColor(0xFF888888);
//                            } else {
//                                prefs.edit().putInt("topIgnoreMask", ignMask & ~(1 << index))
//                                        .commit();
//                                filterToggle.setTextColor(0xFFFFFFFF);
//                            }
//                        }
//                    });
//                    filterGroup.addView(filterToggle,
//                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
//                                    ViewGroup.LayoutParams.FILL_PARENT, 1f));
                }
            } catch(RemoteException e) {
                counterService = null;
                e.printStackTrace();
            }
        }
        
        public void onServiceDisconnected(ComponentName className) {
            counterService = null;
            activity.getApplicationContext().unbindService(conn);
            activity.getApplicationContext().bindService(serviceIntent, conn, 0);
//            Log.w(TAG, "Unexpectedly lost connection to service");
        }
    }
}
