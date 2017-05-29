package com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate;

import com.example.zenbook.networkselection.Utils.Global;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class ClientAsync implements Runnable {
    private String sequence;
    private DatagramSocket clientSocket;
    
    public ClientAsync(String sequence, DatagramSocket datagramSocket){
        this.sequence = sequence;
//        System.out.println(sequence);
        this.clientSocket = datagramSocket;
    }
    
    @Override
    public void run() {
        try {
            InetAddress IPAddress = InetAddress.getByName(Global.IPAddress);
            String sentence = sequence + "";
//            if(sequence.equals("end")) sentence = "end";
            byte[] sendData = sentence.getBytes();

//            ServerAsync serverAsync = new ServerAsync(clientSocket);
//            Thread receive = new Thread(serverAsync);
//            receive.start();
            
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);

                /*
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
                modifiedSentence = new String(receivePacket.getData());
                String message = new String(receivePacket.getData(),receivePacket.getOffset(),receivePacket.getLength());
                count++;
                System.out.println("FROM SERVER:" + message + " " + count);
                clientSocket.close();
                */
            
            
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        }
    }
}
