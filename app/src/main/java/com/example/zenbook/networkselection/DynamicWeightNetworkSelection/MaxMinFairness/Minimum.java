/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.MaxMinFairness;

/**
 * @author ZENBOOK
 */
public class Minimum implements Comparable<Minimum> {

    private int ransNo;
    private int outcome;
    private double result;
    private boolean minimum;
    public Minimum(int ransNo, int outcome, double result, boolean minimum) {
        
        this.ransNo = ransNo;
        this.outcome = outcome;
        this.result = (double)Math.round(result * 10000d) / 10000d;;
//        this.result = result;
        this.minimum = minimum;
    }

    public int getRansNo() {
        return ransNo;
    }

    public int getOutcome() {
        return outcome;
    }
    
    public double getResult() {
        return result;
    }

    @Override
    public int compareTo(Minimum o) {
        double compareQuantity = ((Minimum) o).getResult();

        //ascending order
//        System.out.println((int)(this.result*1000 - compareQuantity*1000));
        if (minimum)
            return (int) (this.result * 1000 - compareQuantity * 1000);

            //descending order
        else return (int) (compareQuantity * 1000 - this.result * 1000);
        //return compareQuantity - this.quantity;    
    }
}
