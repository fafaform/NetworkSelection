package com.example.zenbook.networkselection;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.zenbook.networkselection.ActiveMeasurement.Delay.RoundTripTime;
import com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency.GetEnergyEfficiency;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.SelectNetwork;
import com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate.GetUDPSuccessRate;
import com.example.zenbook.networkselection.PassiveMeasurement.PassiveService;
import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.util.ArrayList;

public class MainActivity extends Activity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Global.activity = this;
//        BatteryManager mBatteryManager = (BatteryManager)this.getSystemService(Context.BATTERY_SERVICE);
//        int level = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        
        ArrayList<RANObject> objects = new ArrayList<>();
    
        RANObject ranObject;
        
        startService();
        
        
        
    }
    
    public void startService() {
        System.out.println("Start service");
        startService(new Intent(this, PassiveService.class));
    }
    
    // Method to stop the service
    public void stopService() {
        stopService(new Intent(getBaseContext(), PassiveService.class));
    }
}
