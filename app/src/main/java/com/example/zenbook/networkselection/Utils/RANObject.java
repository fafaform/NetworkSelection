package com.example.zenbook.networkselection.Utils;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class RANObject {
    
    public String getSSID() {
        return SSID;
    }
    
    public void setSSID(String SSID) {
        this.SSID = SSID;
    }
    
    public String getRSSi() {
        return RSSi;
    }
    
    public void setRSSi(String RSSi) {
        this.RSSi = RSSi;
    }
    
    public String getBand() {
        return Band;
    }
    
    public void setBand(String band) {
        Band = band;
    }
    
    public String getEnergyEfficiency() {
        return EnergyEfficiency;
    }
    
    public void setEnergyEfficiency(String energyEfficiency) {
        EnergyEfficiency = energyEfficiency;
    }
    
    public String getUDPSuccessRate() {
        return UDPSuccessRate;
    }
    
    public void setUDPSuccessRate(String UDPSuccessRate) {
        this.UDPSuccessRate = UDPSuccessRate;
    }
    
    public String getDelay() {
        return Delay;
    }
    
    public void setDelay(String delay) {
        Delay = delay;
    }
    
    private String SSID = "1";
    private String RSSi = "1";
    private String Band = "1";
    private String EnergyEfficiency = "1";
    private String UDPSuccessRate = "1";
    private String Delay = "1";
}
