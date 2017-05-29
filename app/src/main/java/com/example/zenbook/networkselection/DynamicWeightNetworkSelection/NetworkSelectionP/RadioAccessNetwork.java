/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.NetworkSelectionP;

import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames.UtilityResults;

/**
 * @author ZENBOOK
 */
public class RadioAccessNetwork {
    
    //<editor-fold defaultstate="collapsed" desc="variable">
    private UtilityResults currentObjective;
    private UtilityResults previousObjective;
    private double energy;
    private double successRate;
    private double delay;
    
    public RadioAccessNetwork() {
        currentObjective = new UtilityResults();
        previousObjective = new UtilityResults();
    }
    
    public UtilityResults getCurrentObjective() {
        return currentObjective;
    }
    
    public void setCurrentObjective(UtilityResults currentObjective) {
        this.currentObjective = currentObjective;
    }
    
    public UtilityResults getPreviousObjective() {
        return previousObjective;
    }
    
    public void setPreviousObjective(UtilityResults previousObjective) {
        this.previousObjective = previousObjective;
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
