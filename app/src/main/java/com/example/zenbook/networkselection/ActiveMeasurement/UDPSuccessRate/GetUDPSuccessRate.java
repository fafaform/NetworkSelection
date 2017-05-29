package com.example.zenbook.networkselection.ActiveMeasurement.UDPSuccessRate;

import com.example.zenbook.networkselection.Utils.RANObject;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.text.DecimalFormat;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class GetUDPSuccessRate {
    
    private static int number_of_packet = 100;
    private static int number_of_data_per_packet = 1;
    private static int time_for_another_packet_in_millisecond = 1;
    private static int time_before_end_in_millisecond = 1000;
    
    public GetUDPSuccessRate(RANObject ranObject) {
//        System.out.println("*********************************************************************START*************************************************");
        Thread receive = new Thread();
        Thread send;
    
        DatagramSocket clientSocket = null;
        try {
            clientSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        ServerAsync serverAsync = new ServerAsync(clientSocket, ranObject);
        receive = new Thread(serverAsync);
        receive.start();
    
        int number = number_of_packet;
        int count = 0;
        String sendingMessage = "";
        for (int i = 0; i < number; i++) {
            ClientAsync clientAsync;
//            System.out.println(i+":"+count);
//            sendingMessage += Double.parseDouble(new DecimalFormat("###.##").format(Math.sin(i * 0.1 * Math.PI / 2))) + "&";
            count++;
            if (count == number_of_data_per_packet) {
                sendingMessage += Double.parseDouble(new DecimalFormat("###.##").format(Math.sin(i * 0.1 * Math.PI / 2)));
//                System.out.println(sendingMessage.getBytes().length);
                clientAsync = new ClientAsync(sendingMessage, clientSocket);
//                System.out.println("SEND FRAGMENT");
//                System.out.println("*********************************************************************FRAGMENT*************************************************");
                sendingMessage = "";
                count = 0;
        
                send = new Thread(clientAsync);
                send.start();
            } else {
                sendingMessage += Double.parseDouble(new DecimalFormat("###.##").format(Math.sin(i * 0.1 * Math.PI / 2))) + "&";
            }
            if (i == (number - 1)) {
                try {
                    Thread.sleep(time_before_end_in_millisecond);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (sendingMessage.equals("")) {
                    clientAsync = new ClientAsync("end", clientSocket);
                } else {
                    clientAsync = new ClientAsync(sendingMessage + "&end", clientSocket);
                }
                count = 0;
                sendingMessage = "";
//                System.out.println("Sent end");
        
                send = new Thread(clientAsync);
                send.start();
            }
        }
        try {
            receive.join();
            Thread.sleep(time_for_another_packet_in_millisecond);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
