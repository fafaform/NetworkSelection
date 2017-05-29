/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.zenbook.networkselection.DynamicWeightNetworkSelection.ZeroSumGames;

/**
 * @author ZENBOOK
 */
public class Outcome {
    
    //<editor-fold defaultstate="collapsed" desc="variable">
    private Weight O1;
    private Weight O2;
    private Weight O3;
    private Weight O4;
    private Weight O5;
    private Weight O6;
    private Weight O7;
    private Weight O8;
    private Weight O9;
    private Weight O10;
    private Weight O11;
    private double w_energy;
    private double w_successRate;
    private double w_delay;
    private double e;
    private double s;
    private double d;

    public Outcome(double w_energy, double w_successRate, double w_delay, double energy, double successRate, double delay) {
        //<editor-fold defaultstate="collapsed" desc="initialize">
        O1 = new Weight();
        O2 = new Weight();
        O3 = new Weight();
        O4 = new Weight();
        O5 = new Weight();
        O6 = new Weight();
        O7 = new Weight();
        O8 = new Weight();
        O9 = new Weight();
        O10 = new Weight();
        O11 = new Weight();
        this.w_energy = w_energy;
        this.w_successRate = w_successRate;
        this.w_delay = w_delay;
//        e = energy*w_energy;
        e = (1 - energy) * w_energy;
//        s = successRate*w_successRate;
        s = (1 - successRate) * w_successRate;
        d = (1 - delay) * w_delay;
        //</editor-fold>
        //<editor-fold defaultstate="collapsed" desc="Update Weight for all possible game outcomes">
        setO1();
        setO2();
        setO3();
        setO4();
        setO5();
        setO6();
        setO7();
        setO8();
        setO9();
        setO10();
        setO11();
//</editor-fold>
    }

    private void setO1() {
        this.O1.setW_energy(w_energy + e);
        this.O1.setW_successRate(w_successRate + s);
        this.O1.setW_delay(w_delay - s - e);
//        System.out.println("O1 : "+O1.getW_energy()+" "+O1.getW_successRate()+" "+O1.getW_delay());
    }

    private void setO2() {
        this.O2.setW_energy(w_energy + e);
        this.O2.setW_successRate(w_successRate - e);
        this.O2.setW_delay(w_delay);
//        System.out.println("O2 : "+O2.getW_energy()+" "+O2.getW_successRate()+" "+O2.getW_delay());
    }

    private void setO3() {
        this.O3.setW_energy(w_energy + e);
        this.O3.setW_successRate(w_successRate);
        this.O3.setW_delay(w_delay - e);
//        System.out.println("O3 : "+O3.getW_energy()+" "+O3.getW_successRate()+" "+O3.getW_delay());
    }

    private void setO4() {
        this.O4.setW_energy(w_energy + e);
        this.O4.setW_successRate(w_successRate - d);
        this.O4.setW_delay(w_delay + d - e);
//        System.out.println("O4 : "+O4.getW_energy()+" "+O4.getW_successRate()+" "+O4.getW_delay());
    }

    private void setO5() {
        this.O5.setW_energy(w_energy + e);
        this.O5.setW_successRate(w_successRate - e - d);
        this.O5.setW_delay(w_delay + d);
//        System.out.println("O5 : "+O5.getW_energy()+" "+O5.getW_successRate()+" "+O5.getW_delay());
    }

    private void setO6() {
        this.O6.setW_energy(w_energy - s - d);
        this.O6.setW_successRate(w_successRate + s);
        this.O6.setW_delay(w_delay + d);
//        System.out.println("O6 : "+O6.getW_energy()+" "+O6.getW_successRate()+" "+O6.getW_delay());
    }

    private void setO7() {
        this.O7.setW_energy(w_energy - s);
        this.O7.setW_successRate(w_successRate + s);
        this.O7.setW_delay(w_delay);
//        System.out.println("O7 : "+O7.getW_energy()+" "+O7.getW_successRate()+" "+O7.getW_delay());
    }

    private void setO8() {
        this.O8.setW_energy(w_energy - d);
        this.O8.setW_successRate(w_successRate);
        this.O8.setW_delay(w_delay + d);
//        System.out.println("O8 : "+O9.getW_energy()+" "+O8.getW_successRate()+" "+O8.getW_delay());
    }

    private void setO9() {
        this.O9.setW_energy(w_energy);
        this.O9.setW_successRate(w_successRate + s);
        this.O9.setW_delay(w_delay - s);
//        System.out.println("O9 : "+O9.getW_energy()+" "+O9.getW_successRate()+" "+O9.getW_delay());
    }

    private void setO10() {
        this.O10.setW_energy(w_energy);
        this.O10.setW_successRate(w_successRate);
        this.O10.setW_delay(w_delay);
//        System.out.println("O10 : "+O10.getW_energy()+" "+O10.getW_successRate()+" "+O10.getW_delay());
    }

    private void setO11() {
        this.O11.setW_energy(w_energy);
        this.O11.setW_successRate(w_successRate - d);
        this.O11.setW_delay(w_delay + d);
//        System.out.println("O11 : "+O11.getW_energy()+" "+O11.getW_successRate()+" "+O11.getW_delay());
    }

    public Weight getO1() {
        return O1;
    }

    public Weight getO2() {
        return O2;
    }

    public Weight getO3() {
        return O3;
    }

    public Weight getO4() {
        return O4;
    }

    public Weight getO5() {
        return O5;
    }
    
    public Weight getO6() {
        return O6;
    }

    public Weight getO7() {
        return O7;
    }

    public Weight getO8() {
        return O8;
    }

    public Weight getO9() {
        return O9;
    }

    public Weight getO10() {
        return O10;
    }

    public Weight getO11() {
        return O11;
    }
//</editor-fold>
}
