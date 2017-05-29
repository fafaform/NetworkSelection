/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames;

import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness.SelectedNetwork;

/**
 * @author ZENBOOK
 */
public class ZeroSumGame {
    private Outcome outcome;
    private SelectedNetwork selectNetwork;

    public ZeroSumGame(SelectedNetwork selectNetwork) {
        this.selectNetwork = selectNetwork;
        //<editor-fold defaultstate="collapsed" desc="Test selected weight and data">
//        System.out.println("+++++++++++++++++++++++++++");
//        System.out.println("selected data and weight");
//        System.out.println(selectNetwork.getW_energy()+" "+selectNetwork.getW_successRate()+" "+selectNetwork.getW_delay()+" "+selectNetwork.getEnergy()+" "+selectNetwork.getSuccessRate()+" "+selectNetwork.getDelay());
//        System.out.println("+++++++++++++++++++++++++++");
        //</editor-fold>
        outcome = new Outcome(selectNetwork.getW_energy(), selectNetwork.getW_successRate(), selectNetwork.getW_delay(), selectNetwork.getEnergy(), selectNetwork.getSuccessRate(), selectNetwork.getDelay());
    }
    
    public Outcome getOutcome() {
        return outcome;
    }
}
