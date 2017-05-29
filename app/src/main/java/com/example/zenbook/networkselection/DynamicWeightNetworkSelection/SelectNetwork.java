package com.example.zenbook.networkselection.DynamicWeightNetworkSelection;

import java.util.ArrayList;
import com.example.zenbook.networkselection.DynamicWeightNetworkSelection.NetworkSelectionP.*;
import com.example.zenbook.networkselection.Utils.RANObject;

/**
 * Created by ZENBOOK on 5/24/2017.
 */

public class SelectNetwork {
    
    private NetworkSelection network;
    private ArrayList<RANObject> objects;
    
    public SelectNetwork(ArrayList<RANObject> objects) {
        
        ArrayList<RadioAccessNetwork> rans = new ArrayList<>();
        RadioAccessNetwork ran;
        
        System.out.println("objects size: " + objects.size());
        for(int i = 0; i < objects.size();i++) {
            ran = new RadioAccessNetwork();
            ran.setEnergy(Double.parseDouble(objects.get(i).getEnergyEfficiency()));
            ran.setSuccessRate(Double.parseDouble(objects.get(i).getUDPSuccessRate()));
            ran.setDelay(Double.parseDouble(objects.get(i).getDelay()));
            rans.add(ran);
        }

    
        network = new NetworkSelection(rans);
        
        System.out.println("Select network number: "+network.getSelectNetwork());
    }
    public int getRANObject(){
//        System.out.println("Again: "+network.getSelectNetwork());
    
        return network.getSelectNetwork();
    }
}
