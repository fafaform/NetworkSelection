package com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate;

import com.example.zenbook.networkselection.Utils.Global;
import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class ServerAsync implements Runnable {
    private DatagramSocket datagramSocket;
    private RANObject ranObject;
    private long processingTime;
    
    public ServerAsync(DatagramSocket datagramSocket, RANObject ranObject){
        this.datagramSocket = datagramSocket;
        this.ranObject = ranObject;
    }
    
    @Override
    public void run() {
        try {
            datagramSocket.setSoTimeout(60000);
//            datagramSocket.setSoTimeout(10000);
//            datagramSocket.setSoTimeout(20000);

//            byte[] receiveData = new byte[1024];
            byte[] receiveData = new byte[32];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            String message;
            
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            processingTime = dateFormat.getCalendar().getTime().getTime();
            
            try {
                datagramSocket.receive(receivePacket);
                message = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
//                System.out.println("Received Return");
            } catch (SocketTimeoutException e){
                e.printStackTrace();
                message = "1";
            }
//            Global.lossRate++;
//            if(message.equals(Global.lossRate+"")) {
//                System.out.println("RECEIVED: " + message);
//            System.out.println((Integer.parseInt(message)-1) + "");
            try {
                dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                processingTime = dateFormat.getCalendar().getTime().getTime() - processingTime;
//                System.out.println((double)(Integer.parseInt(message)-1)/processingTime + " " + processingTime);
                Global.logFileOutputStream = new FileOutputStream(Global.logFile, true);
                Global.logFileOutputStream.write((round((double)(Integer.parseInt(message)-1)/processingTime, 4) + ",").getBytes());
//                Global.logFileOutputStream.write("\n".getBytes());
                Global.logFileOutputStream.close();
            } catch (FileNotFoundException fe) {
                fe.printStackTrace();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
            ranObject.setUDPSuccessRate((double)(Integer.parseInt(message)-1)/(Global.number_of_udp_packet) + "");
//            }else{
//                System.err.println("RECEIVED: " + message);
//                Global.lossRate = Integer.parseInt(message)-1;
//            }
            datagramSocket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
