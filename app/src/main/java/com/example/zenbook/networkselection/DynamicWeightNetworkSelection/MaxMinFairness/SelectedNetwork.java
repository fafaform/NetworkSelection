/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness;

/**
 * @author ZENBOOK
 */
public class SelectedNetwork {
    
    //<editor-fold defaultstate="collapsed" desc="Variable">
    private double w_energy;
    private double w_successRate;
    private double w_delay;
    private double energy;
    private double successRate;
    private double delay;

    public SelectedNetwork() {
        w_energy = 1.0 / 3.0;
        w_successRate = 1.0 / 3.0;
        w_delay = 1.0 / 3.0;
        energy = 1;
        successRate = 1;
        delay = 1;
    }

    public double getW_energy() {
        return w_energy;
    }

    public void setW_energy(double w_energy) {
        this.w_energy = w_energy;
    }

    public double getW_successRate() {
        return w_successRate;
    }

    public void setW_successRate(double w_successRate) {
        this.w_successRate = w_successRate;
    }

    public double getW_delay() {
        return w_delay;
    }

    public void setW_delay(double w_delay) {
        this.w_delay = w_delay;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getSuccessRate() {
        return successRate;
    }

    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }

    public double getDelay() {
        return delay;
    }

    public void setDelay(double delay) {
        this.delay = delay;
    }
//</editor-fold>
}
