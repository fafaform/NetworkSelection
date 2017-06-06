package com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate;

import com.example.zenbook.networkselection.Utils.RANObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class ServerAsync implements Runnable {
    private DatagramSocket datagramSocket;
    private RANObject ranObject;
    
    public ServerAsync(DatagramSocket datagramSocket, RANObject ranObject){
        this.datagramSocket = datagramSocket;
        this.ranObject = ranObject;
    }
    
    @Override
    public void run() {
        try {
//            datagramSocket.setSoTimeout(60000);
            datagramSocket.setSoTimeout(10000);

//            byte[] receiveData = new byte[1024];
            byte[] receiveData = new byte[32];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            String message;
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
            ranObject.setUDPSuccessRate((double)(Integer.parseInt(message)-1)/100 + "");
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
}
