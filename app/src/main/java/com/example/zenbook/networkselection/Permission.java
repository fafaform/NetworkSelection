package com.example.zenbook.networkselection;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;
import android.widget.Toast;

/**
 * Created by ZENBOOK on 10/6/2017.
 */

public class Permission {
    private static final int WIFI_STATE_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int NETWORK_PERMISSION_REQUEST_CODE = 3;
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    private Activity activity;
    
    
    public Permission(Activity activity) {
        this.activity = activity;
    }
    
    public boolean checkPermissionForExternalStorage(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readStorage = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            readStorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        
        return result == PackageManager.PERMISSION_GRANTED && readStorage == PackageManager.PERMISSION_GRANTED;
    }
    
    public void requestPermissionForExternalStorage(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            Toast.makeText(activity, "External Storage permission required. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
            }
        }
    }
    
    public boolean checkPermissionForAccessFineLocation(){
        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int readStorage = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            readStorage = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        }
    
        return result == PackageManager.PERMISSION_GRANTED && readStorage == PackageManager.PERMISSION_GRANTED;
    }
    
    public void requestPermissionForAccessFineLocation(){
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(activity, "External Storage permission required. Please allow in App Settings for additional functionality.", Toast.LENGTH_LONG).show();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
            }else{
                ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},LOCATION_PERMISSION_REQUEST_CODE);
            }
            
        }
    }

}
