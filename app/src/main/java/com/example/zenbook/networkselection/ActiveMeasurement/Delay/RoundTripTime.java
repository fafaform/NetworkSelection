package com.example.zenbook.networkselection.ActiveMeasurement.Delay;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class RoundTripTime {
    private static int NUMBER_OF_PACKTETS = 10;
    private static int PING_PACKET_SIZE = 32;
    private static int NUMBER_OF_PING = 1;
    private static int WAITING_TIME_MILLISECOND = 1000;
    private static int DELAY_TH = 500;
    
    private boolean start = true;
    private int count = 0;
    
    private Double min;
    private Double avg;
    private Double max;
    
    public RoundTripTime(RANObject ranObject){
        while (start) {
            count++;
            if (count < NUMBER_OF_PING + 1) {
                double[] data = getLatency(Global.IPAddress);
                min = round(data[0], 2);
                avg = round(data[1], 2);
                max = round(data[2], 2);
                
            } else {
                start = false;
                    ranObject.setDelay((round(Math.abs(Math.min(0,(avg - DELAY_TH) / DELAY_TH)), 4) + ""));
            }
            if (count < NUMBER_OF_PING) {
                try{
                    synchronized (this) {
                        wait(WAITING_TIME_MILLISECOND);
                    }
                }catch(InterruptedException ex){
                    ex.printStackTrace();
                }
            }
//                                System.out.println(count);
        }
    }
    
    public double[] getLatency(String ipAddress){
        String pingCommand = "/system/bin/ping -c " + NUMBER_OF_PACKTETS + " -s " + PING_PACKET_SIZE + " " + ipAddress;
        String inputLine = "";
        double[] Rtt = new double[3];
        Boolean found = true;
        try {
            // execute the command on the environment interface
            Process process = Runtime.getRuntime().exec(pingCommand);
            // gets the input stream to get the output of the executed command
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            inputLine = bufferedReader.readLine();
            while ((inputLine != null)) {
//                System.out.println(inputLine);
                if (inputLine.length() > 0 && inputLine.contains("avg")) {  // when we get to the last line of executed ping command
                    found = true;
                    break;
                }else found = false;
                inputLine = bufferedReader.readLine();
            }
        }
        catch (IOException e){
//            Log.v(DEBUG_TAG, "getLatency: EXCEPTION");
            e.printStackTrace();
        }
        // Extracting the average round trip time from the inputLine string
        if(found) {
            try {
                String afterEqual = inputLine.substring(inputLine.indexOf("="), inputLine.length()).trim();
                String strMinRtt = afterEqual.substring(1, afterEqual.indexOf('/'));
                String afterFirstSlash = afterEqual.substring(afterEqual.indexOf('/') + 1, afterEqual.length()).trim();
                String strAvgRtt = afterFirstSlash.substring(0, afterFirstSlash.indexOf('/'));
                String afterSecondSlash = afterFirstSlash.substring(afterFirstSlash.indexOf('/') + 1, afterFirstSlash.length()).trim();
                String strMaxRtt = afterSecondSlash.substring(0, afterSecondSlash.indexOf('/'));
                
                Rtt[0] = Double.valueOf(strMinRtt);
                Rtt[1] = Double.valueOf(strAvgRtt);
                Rtt[2] = Double.valueOf(strMaxRtt);
            }catch (NullPointerException e){
                Rtt = timeout();
            }
        } else{
            Rtt = timeout();
        }
//        System.out.println("Min: " + Rtt[0] + ", AVG: " + Rtt[1] + ", Max: " + Rtt[2]);
        return Rtt;
    }
    
    public double[] timeout(){
        double[] Rtt = new double[3];
        for(int i = 0; i < 3; i++){
            Rtt[i] = 9999.99;
        }
        return Rtt;
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
