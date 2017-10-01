/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames;

/**
 * @author ZENBOOK
 */
public class Weight {

    //<editor-fold defaultstate="collapsed" desc="variable">
    private double w_energy;
    private double w_successRate;
    private double w_delay;

    public double getW_energy() {
        return w_energy;
    }

    public void setW_energy(double w_energy) {
        this.w_energy = (double)Math.round(w_energy * 10000d) / 10000d;
    }

    public double getW_successRate() {
        return w_successRate;
    }

    public void setW_successRate(double w_successRate) {
        this.w_successRate = (double)Math.round(w_successRate * 10000d) / 10000d;
    }

    public double getW_delay() {
        return w_delay;
    }

    public void setW_delay(double w_delay) {
        this.w_delay = (double)Math.round(w_delay * 10000d) / 10000d;
    }
//</editor-fold>
}
