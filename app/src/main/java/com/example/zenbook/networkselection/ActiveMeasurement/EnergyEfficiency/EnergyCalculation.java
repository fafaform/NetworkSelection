package com.example.zenbook.networkselection.ActiveMeasurement.EnergyEfficiency;

/**
 * Created by ZENBOOK on 5/25/2017.
 */

public class EnergyCalculation {
    private static double CELLULAR_BRRC_DCH = 623.4;
    private static double WIFI_BLT_TRANSMIT = 3.8;
    private static double WIFI_BLT_BASE = 173.1;
    private static double WIFI_BHT_TRANSMIT = 0.3;
    private static double WIFI_BHT_BASE = 251.4;
    private static double WIFI_THRESHOLD = 25;
    
    private boolean start = true;
    private double energyUsed = 0;
    private String service;
    private Thread thread;
    private double power;
    private int count;
    
    
    public EnergyCalculation(String service){
        this.service = service;
        power = 0.0;
        count = 0;
        thread = new Thread() {
            @Override
            public void run() {
                runable();
            }
        };
        thread.start();
    }
    
    private void runable(){
        if(start) {
            count++;
            switch (service) {
                case "WIFI":
                    power += (WIFI_BHT_TRANSMIT * 100) + WIFI_BHT_BASE;
                    break;
                case "CELLULAR":
                    power += CELLULAR_BRRC_DCH;
                    break;
                default:
                    break;
            }
        }else{
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        runable();
    }
    
    public double stop(){
        start = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return power;
    }
}
