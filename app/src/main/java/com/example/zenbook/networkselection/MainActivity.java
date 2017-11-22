package com.example.zenbook.networkselection;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;

import com.example.zenbook.networkselection.ActiveMeasurement.Delay.RoundTripTime;
import com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency.GetEnergyEfficiency;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.SelectNetwork;
import com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate.GetUDPSuccessRate;
import com.example.zenbook.networkselection.PassiveMeasurement.PassiveService;
import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.util.ArrayList;

import edu.umich.PowerTutor.service.UMLoggerService;

public class MainActivity extends Activity {
    
//    protected PowerManager.WakeLock mWakeLock;
    
    @Override
    protected void onResume(){
        super.onResume();
//        stopService();
    }
    
//    @Override
//    protected void onNewIntent(Intent intent) {
//        stopService();
//    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    
        Thread.setDefaultUncaughtExceptionHandler(new CustomizedExceptionHandler(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath()));
        
        Global.activity = this;
        Global.savedInstanceState = savedInstanceState;
//        BatteryManager mBatteryManager = (BatteryManager)this.getSystemService(Context.BATTERY_SERVICE);
//        int level = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        
        ArrayList<RANObject> objects = new ArrayList<>();
    
        RANObject ranObject;
    
        Permission marshMallowPermission = new Permission(this);
        marshMallowPermission.checkPermissionForExternalStorage();
        marshMallowPermission.requestPermissionForExternalStorage();
                
        final Button startService = (Button)findViewById(R.id.startService);
        final Button stopService = (Button)findViewById(R.id.stopService);
        CheckedTextView powerTutor = (CheckedTextView)findViewById(R.id.checkedTextView);
        if(!isMyServiceRunning(UMLoggerService.class)){
            powerTutor.setChecked(false);
        }else{
            powerTutor.setChecked(true);
        }
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService.setEnabled(false);
                stopService.setEnabled(true);
                startService();
                sendNotification();
            }
        });
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService.setEnabled(true);
                stopService.setEnabled(false);
                stopService();
            }
        });
        Toast.makeText(this, "Create again", Toast.LENGTH_LONG).show();
        
//        startService();
//        sendNotification();
        
        
    }
    
    public void startService() {
        System.out.println("Start service");
        if(!isMyServiceRunning(PassiveService.class)) {
//            System.out.println("Open service");
            startService(new Intent(this, PassiveService.class));
        }
        if(!isMyServiceRunning(UMLoggerService.class)) {
//            System.out.println("Open sub service");
            startService(new Intent(this, UMLoggerService.class));
        }
    }
    
    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), PassiveService.class));
    }
    
    public void sendNotification() {

//Get an instance of NotificationManager//
        
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");
    
        final Intent intent = new Intent(this, MainActivity.class);

        final PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, 0);


// Gets an instance of the NotificationManager service//
        
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

//When you issue multiple notifications about the same type of event, it’s best practice for your app to try to update an existing notification with this new information, rather than immediately creating a new notification. If you want to update this notification at a later date, you need to assign it an ID. You can then use this ID whenever you issue a subsequent notification. If the previous notification is still visible, the system will update this existing notification, rather than create a new one. In this example, the notification’s ID is 001//
        
        mNotificationManager.notify(001, mBuilder.build());
        
    }
    
    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
