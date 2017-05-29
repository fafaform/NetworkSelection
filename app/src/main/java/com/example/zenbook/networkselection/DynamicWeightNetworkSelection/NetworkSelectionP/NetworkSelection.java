/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.NetworkSelectionP;

import java.util.ArrayList;

import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness.GetMaximum;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness.GetMinimum;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness.Minimum;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness.SelectedNetwork;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames.UtilityResults;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames.ZeroSumGame;

/**
 * @author ZENBOOK
 */
public class NetworkSelection {
    private ArrayList<RadioAccessNetwork> rans;
    private SelectedNetwork selectNetwork;
    private Minimum tempSelected;
    private int loopCount;
    
    public NetworkSelection(ArrayList<RadioAccessNetwork> rans) {
        this.rans = new ArrayList<>();
        this.rans = rans;
        selectNetwork = new SelectedNetwork();
        tempSelected = new Minimum(0, 0, 0, false);
        loopCount = 0;
        LoopCalculation();
    }
    
    public int getSelectNetwork() {
        int NUM = 0;
        double score = 0;
        for (int i = 0; i < rans.size(); i++) {
//            System.out.println(selectNetwork.getEnergy()*rans.get(i).getEnergy()+"+"+selectNetwork.getW_successRate()*rans.get(i).getSuccessRate()+"+"+selectNetwork.getW_delay()*rans.get(i).getDelay());
//            System.out.println(i +" : "+(selectNetwork.getW_energy()*rans.get(i).getEnergy()+selectNetwork.getW_successRate()*rans.get(i).getSuccessRate()+selectNetwork.getW_delay()*rans.get(i).getDelay()));
            if ((selectNetwork.getW_energy() * rans.get(i).getEnergy() + selectNetwork.getW_successRate() * rans.get(i).getSuccessRate() + selectNetwork.getW_delay() * rans.get(i).getDelay()) > score) {
                score = selectNetwork.getW_energy() * rans.get(i).getEnergy() + selectNetwork.getW_successRate() * rans.get(i).getSuccessRate() + selectNetwork.getW_delay() * rans.get(i).getDelay();
                NUM = i;
            }
        }
//        System.out.println("--------------------------------------------");
//        System.out.println(selectNetwork.getW_energy() + " " + selectNetwork.getW_successRate() + " " + selectNetwork.getW_delay() + " " + selectNetwork.getEnergy() + " " + selectNetwork.getSuccessRate() + " " + selectNetwork.getDelay());
//        System.out.println("--------------------------------------------");
//        return "RAN: " + (NUM + 1);
        return NUM;
    }
    
    public int getLoopCount() {
        return loopCount;
    }
    
    public void LoopCalculation() {
        loopCount++;
        ZeroSumGame zeroSum = new ZeroSumGame(selectNetwork);
        ArrayList<Minimum> minimum = new ArrayList<Minimum>();
        ArrayList<Minimum> maximum = new ArrayList<Minimum>();
        ArrayList<Minimum> max = new ArrayList<Minimum>();
        minimum = new GetMinimum(selectNetwork, rans, zeroSum).getMinimum();
        maximum = new GetMaximum(rans, minimum).getMaximum();
        
        double previous = 0.0;
        Boolean gotten = false;
        Minimum selectOne = new Minimum(0, 0, 0, false);
//<editor-fold defaultstate="collapsed" desc="Temp to select network">
//        for (int i = 0; i < max.size(); i++) {
//            previous = 0.0;
////        System.out.println(max.get(0).getRansNo()+" "+max.get(0).getOutcome());
//            selectNetwork.setEnergy(rans.get(max.get(i).getRansNo()).getEnergy());
//            selectNetwork.setSuccessRate(rans.get(max.get(i).getRansNo()).getSuccessRate());
//            selectNetwork.setDelay(rans.get(max.get(i).getRansNo()).getDelay());
//            switch (max.get(i).getOutcome()) {
//                case 1:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO1().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO1().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO1().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO1();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 2:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO2().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO2().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO2().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO2();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 3:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO3().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO3().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO3().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO3();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 4:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO4().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO4().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO4().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO4();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 5:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO5().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO5().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO5().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO5();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 6:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO6().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO6().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO6().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO6();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 7:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO7().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO7().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO7().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO7();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 8:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO8().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO8().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO8().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO8();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 9:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO9().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO9().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO9().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO9();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 10:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO10().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO10().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO10().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO10();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                case 11:
//                    selectNetwork.setW_energy(zeroSum.getOutcome().getO11().getW_energy());
//                    selectNetwork.setW_successRate(zeroSum.getOutcome().getO11().getW_successRate());
//                    selectNetwork.setW_delay(zeroSum.getOutcome().getO11().getW_delay());
//                    previous = rans.get(max.get(i).getRansNo()).getPreviousObjective().getO11();
//                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
//                        gotten = true;
//                    }
//                    break;
//                default:
//                    System.out.println("ERROR AT SELECTEDNETWORK");
//                    break;
//            }
//            if (gotten) {
//                selectOne = max.get(i);
//                System.out.println("Got at: " + i);
//                break;
//            }
//        }
//</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Runnable to get select network">
        selectNetwork = new SelectedNetwork();
        for (int i = 0; i < maximum.size(); i++) {
            previous = 0.0;
//        System.out.println(max.get(0).getRansNo()+" "+max.get(0).getOutcome());
            selectNetwork.setEnergy(rans.get(maximum.get(i).getRansNo()).getEnergy());
            selectNetwork.setSuccessRate(rans.get(maximum.get(i).getRansNo()).getSuccessRate());
            selectNetwork.setDelay(rans.get(maximum.get(i).getRansNo()).getDelay());
            switch (maximum.get(i).getOutcome()) {
                case 1:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO1().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO1().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO1().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO1());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 1");
                    }
                    break;
                case 2:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO2().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO2().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO2().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO2());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 2");
                    }
                    break;
                case 3:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO3().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO3().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO3().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO3());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 3");
                    }
                    break;
                case 4:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO4().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO4().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO4().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO4());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 4");
                    }
                    break;
                case 5:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO5().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO5().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO5().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO5());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 5");
                    }
                    break;
                case 6:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO6().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO6().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO6().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO6());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 6");
                    }
                    break;
                case 7:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO7().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO7().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO7().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO7());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 7");
                    }
                    break;
                case 8:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO8().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO8().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO8().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO8());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 8");
                    }
                    break;
                case 9:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO9().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO9().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO9().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO9());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 9");
                    }
                    break;
                case 10:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO10().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO10().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO10().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO10());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 10");
                    }
                    break;
                case 11:
                    selectNetwork.setW_energy(roundNumber(zeroSum.getOutcome().getO11().getW_energy()));
                    selectNetwork.setW_successRate(roundNumber(zeroSum.getOutcome().getO11().getW_successRate()));
                    selectNetwork.setW_delay(roundNumber(zeroSum.getOutcome().getO11().getW_delay()));
                    previous = roundNumber(rans.get(maximum.get(i).getRansNo()).getPreviousObjective().getO11());
                    if (selectNetwork.getW_energy() >= 0 && selectNetwork.getW_successRate() >= 0 && selectNetwork.getW_delay() >= 0) {
                        gotten = true;
//                        System.out.println("Outcome 11");
                    }
                    break;
                default:
                    System.out.println("ERROR AT SELECTEDNETWORK");
                    break;
            }
            if (gotten) {
                selectOne = maximum.get(i);
//                System.out.println("Got at: " + i);
                tempSelected = selectOne;
                break;
            } else if (i == maximum.size() - 1) {
                //if the weight of all maximum of minimum is negative
                System.err.println("All weight is less than 0");
                selectOne = tempSelected;
                break;
            }
        }
//</editor-fold>

//        System.out.println(previous+" "+selectOne.getResult());
//        System.out.println(roundNumber(selectOne.getResult()) + " - " + previous + " = " + roundNumber(selectOne.getResult() - previous));
//        System.out.println("-------------------------------------------------------------\n\n\n");
        if (roundNumber(selectOne.getResult()) != previous && loopCount < 1000) {
//            System.out.println(max.get(0).getOutcome());
            for (int i = 0; i < rans.size(); i++) {
//                System.out.println(rans.get(i).getPreviousObjective().getO1());
                rans.get(i).setPreviousObjective(rans.get(i).getCurrentObjective());
                rans.get(i).setCurrentObjective(new UtilityResults());
//                System.out.println(rans.get(i).getPreviousObjective().getO1());
            }
            LoopCalculation();
        } else {
//            System.out.println(max.get(0).getResult());
//            System.out.println("DONE");
        }
    }
    
    private double roundNumber(double value){
        return (double)Math.round(value * 10000d) / 10000d;
    }
}
