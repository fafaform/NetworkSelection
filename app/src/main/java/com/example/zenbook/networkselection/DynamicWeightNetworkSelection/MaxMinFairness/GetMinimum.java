/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness;

import java.util.ArrayList;
import java.util.Collections;

import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames.ZeroSumGame;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.NetworkSelectionP.RadioAccessNetwork;

/**
 * @author ZENBOOK
 */
public class GetMinimum {

    private ZeroSumGame zeroSum;
    private ArrayList<Minimum> minimum;
    private ArrayList<RadioAccessNetwork> rans;

    public GetMinimum(SelectedNetwork selectNetwork, ArrayList<RadioAccessNetwork> rans, ZeroSumGame zeroSum) {
        this.rans = rans;
        this.zeroSum = zeroSum;
        minimum = new ArrayList<Minimum>();
        Process();
        
    }

    public ArrayList<Minimum> getMinimum() {
        return minimum;
    }

    private void Process() {
        //        System.out.println("Outcome Results");
        for (int i = 0; i < rans.size(); i++) {
            rans.get(i).getCurrentObjective().setO1(zeroSum.getOutcome().getO1().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO1().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO1().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 1, rans.get(i).getPreviousObjective().getO1(), true));
            rans.get(i).getCurrentObjective().setO2(zeroSum.getOutcome().getO2().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO2().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO2().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 2, rans.get(i).getPreviousObjective().getO2(), true));
            rans.get(i).getCurrentObjective().setO3(zeroSum.getOutcome().getO3().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO3().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO3().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 3, rans.get(i).getPreviousObjective().getO3(), true));
            rans.get(i).getCurrentObjective().setO4(zeroSum.getOutcome().getO4().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO4().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO4().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 4, rans.get(i).getPreviousObjective().getO4(), true));
            rans.get(i).getCurrentObjective().setO5(zeroSum.getOutcome().getO5().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO5().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO5().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 5, rans.get(i).getPreviousObjective().getO5(), true));
            rans.get(i).getCurrentObjective().setO6(zeroSum.getOutcome().getO6().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO6().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO6().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 6, rans.get(i).getPreviousObjective().getO6(), true));
            rans.get(i).getCurrentObjective().setO7(zeroSum.getOutcome().getO7().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO7().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO7().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 7, rans.get(i).getPreviousObjective().getO7(), true));
            rans.get(i).getCurrentObjective().setO8(zeroSum.getOutcome().getO8().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO8().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO8().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 8, rans.get(i).getPreviousObjective().getO8(), true));
            rans.get(i).getCurrentObjective().setO9(zeroSum.getOutcome().getO9().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO9().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO9().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 9, rans.get(i).getPreviousObjective().getO9(), true));
            rans.get(i).getCurrentObjective().setO10(zeroSum.getOutcome().getO10().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO10().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO10().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 10, rans.get(i).getPreviousObjective().getO10(), true));
            rans.get(i).getCurrentObjective().setO11(zeroSum.getOutcome().getO11().getW_energy() * rans.get(i).getEnergy()
                    + zeroSum.getOutcome().getO11().getW_successRate() * rans.get(i).getSuccessRate()
                    + zeroSum.getOutcome().getO11().getW_delay() * rans.get(i).getDelay());
            minimum.add(new Minimum(i, 11, rans.get(i).getPreviousObjective().getO11(), true));

            //To test objective
            //<editor-fold defaultstate="collapsed" desc="Test Zero-Sum Outcome">
//            System.out.println("Old outcome");
//            System.out.println("O1 : "+rans.get(i).getPreviousObjective().getO1());
//            System.out.println("O2 : "+rans.get(i).getPreviousObjective().getO2());
//            System.out.println("O3 : "+rans.get(i).getPreviousObjective().getO3());
//            System.out.println("O4 : "+rans.get(i).getPreviousObjective().getO4());
//            System.out.println("O5 : "+rans.get(i).getPreviousObjective().getO5());
//            System.out.println("O6 : "+rans.get(i).getPreviousObjective().getO6());
//            System.out.println("O7 : "+rans.get(i).getPreviousObjective().getO7());
//            System.out.println("O8 : "+rans.get(i).getPreviousObjective().getO8());
//            System.out.println("O9 : "+rans.get(i).getPreviousObjective().getO9());
//            System.out.println("O10 : "+rans.get(i).getPreviousObjective().getO10());
//            System.out.println("O11 : "+rans.get(i).getPreviousObjective().getO11());
//            System.out.println("-----------------------------------");
//            System.out.println("New outcome : "+i);
//            System.out.println("O1 : "+rans.get(i).getCurrentObjective().getO1());
//            System.out.println("O2 : "+rans.get(i).getCurrentObjective().getO2());
//            System.out.println("O3 : "+rans.get(i).getCurrentObjective().getO3());
//            System.out.println("O4 : "+rans.get(i).getCurrentObjective().getO4());
//            System.out.println("O5 : "+rans.get(i).getCurrentObjective().getO5());
//            System.out.println("O6 : "+rans.get(i).getCurrentObjective().getO6());
//            System.out.println("O7 : "+rans.get(i).getCurrentObjective().getO7());
//            System.out.println("O8 : "+rans.get(i).getCurrentObjective().getO8());
//            System.out.println("O9 : "+rans.get(i).getCurrentObjective().getO9());
//            System.out.println("O10 : "+rans.get(i).getCurrentObjective().getO10());
//            System.out.println("O11 : "+rans.get(i).getCurrentObjective().getO11());
//            System.out.println("************************************");
//</editor-fold>
            //end testing
        }
        Collections.sort(minimum);

//        System.out.println("Selected the minimum");
//        System.out.println("Result Outcome RANs");
//        for (int i = 0; i < minimum.size(); i++) {
//            System.out.println(minimum.get(i).getResult() + " " + minimum.get(i).getOutcome() + " " + minimum.get(i).getRansNo());
//        }
    }
}
