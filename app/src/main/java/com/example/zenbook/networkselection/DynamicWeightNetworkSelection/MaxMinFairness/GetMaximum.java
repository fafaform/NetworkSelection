/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness;

import java.util.ArrayList;
import java.util.Collections;

import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.NetworkSelectionP.RadioAccessNetwork;

/**
 * @author ZENBOOK
 */
public class GetMaximum {
    private ArrayList<RadioAccessNetwork> rans;
    private ArrayList<Minimum> maximum;
    private ArrayList<Minimum> tempMaximum;
    private ArrayList<Minimum> minimum;
    private ArrayList<Minimum> tempMinimum;
    
    public GetMaximum(ArrayList<RadioAccessNetwork> rans, ArrayList<Minimum> minimum) {
        this.rans = rans;
        this.minimum = minimum;
//        tempMinimum = minimum;
        ArrayList<Minimum> tempOfTemp = minimum;
        tempMinimum = tempOfTemp;
        tempOfTemp = new ArrayList<>();
        maximum = new ArrayList<>();
        tempMaximum = new ArrayList<>();
        Process();
    }
    
    public ArrayList<Minimum> getMaximum() {
        return maximum;
    }

    private void Process() {
        while (tempMinimum.size() != 0) {
            double minimumData = 0.0;
//            System.out.println(minimum.size());
            for (int i = 0; i < minimum.size(); i++) {
//            System.out.println(minimum.get(i).getResult()+", "+minimum.get(i).getRansNo()+", "+minimum.get(i).getOutcome());
                if (i == 0) {
                    minimumData = minimum.get(i).getResult();
                }
//            System.out.println(minimumData + " " + minimum.get(i).getResult());
                if (minimum.get(i).getResult() == minimumData) {
//                System.out.println("minimumData: "+minimumData + ", Result: " + minimum.get(i).getResult());
                    switch (minimum.get(i).getOutcome()) {
                        case 1:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 1, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO1(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO1());
                            break;
                        case 2:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 2, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO2(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO2());
                            break;
                        case 3:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 3, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO3(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO3());
                            break;
                        case 4:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 4, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO4(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO4());
                            break;
                        case 5:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 5, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO5(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO5());
                            break;
                        case 6:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 6, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO6(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO6());
                            break;
                        case 7:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 7, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO7(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO7());
                            break;
                        case 8:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 8, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO8(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO8());
                            break;
                        case 9:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 9, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO9(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO9());
                            break;
                        case 10:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 10, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO10(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO10());
                            break;
                        case 11:
                            tempMaximum.add(new Minimum(minimum.get(i).getRansNo(), 11, rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO11(), false));
//                        System.out.println(rans.get(minimum.get(i).getRansNo()).getCurrentObjective().getO11());
                            break;
                        default:
                            System.err.println("Some error");
                            break;
                    }
                }
            }
            Collections.sort(tempMaximum);
            for (int j = 0; j < tempMaximum.size(); j++) {
                minimum.remove(0);
                maximum.add(tempMaximum.get(j));
            }
            tempMaximum = new ArrayList<>();
        }

//        System.out.println("\nSelected the maximum");
//        System.out.println("Result Outcome RANs");
//        for (int i = 0; i < maximum.size(); i++) {
//            System.out.println(maximum.get(i).getResult() + " " + maximum.get(i).getOutcome() + " " + maximum.get(i).getRansNo());
//        }
        
        //<editor-fold defaultstate="collapsed" desc="Find maximum out of maximum (UNUSE)">
//        max = new ArrayList<>();
//        for (int i = 0; i < maximum.size(); i++) {
//            if (i == 0) {
//                maximumData = maximum.get(i).getResult();
//            }
////            System.out.println(maximumData + " " + maximum.get(i).getResult());
//            if (maximum.get(i).getResult() == maximumData) {
////                System.out.println(maximum.get(i).getResult());
//                switch (maximum.get(i).getOutcome()) {
//                    case 1:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 1, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO1()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO1());
//                        break;
//                    case 2:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 2, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO2()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO2());
//                        break;
//                    case 3:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 3, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO3()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO3());
//                        break;
//                    case 4:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 4, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO4()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO4());
//                        break;
//                    case 5:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 5, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO5()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO5());
//                        break;
//                    case 6:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 6, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO6()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO6());
//                        break;
//                    case 7:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 7, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO7()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO7());
//                        break;
//                    case 8:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 8, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO8()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO8());
//                        break;
//                    case 9:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 9, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO9()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO9());
//                        break;
//                    case 10:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 10, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO10()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO10());
//                        break;
//                    case 11:
//                        max.add(new Minimum(maximum.get(i).getRansNo(), 11, rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO11()));
////                        System.out.println(rans.get(maximum.get(i).getRansNo()).getCurrentObjective().getO11());
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }

//        System.out.println("\nSelected the max");
//        for(int i = 0; i < max.size(); i++){
//            System.out.println(max.get(i).getResult()+" "+max.get(i).getOutcome()+" "+max.get(i).getRansNo());
//        }
//</editor-fold>
    }
}
