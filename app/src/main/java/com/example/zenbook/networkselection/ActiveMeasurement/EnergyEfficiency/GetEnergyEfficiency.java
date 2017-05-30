package com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency;

import android.app.Activity;

import com.example.zenbook.networkselection.Utils.RANObject;

/**
 * Created by ZENBOOK on 5/25/2017.
 */

public class GetEnergyEfficiency {
    private RANObject ranObject;
    private EnergyCalculation energyCalculation;
    
    public GetEnergyEfficiency(Activity activity, RANObject ranObject){
        this.ranObject = ranObject;
    }
    
    public void Start(String networkType){
//        energyCalculation = new EnergyCalculation("WIFI");
//        energyCalculation = new EnergyCalculation("CELLULAR");
        energyCalculation = new EnergyCalculation(networkType);
    }
    
    public void Stop(){
//        BatteryManager batteryManager = new BatteryManager();
        double power = (energyCalculation.stop())/3.7;
        ranObject.setEnergyEfficiency((100 - power/(2400*3600))/100 + "");
    }
}
