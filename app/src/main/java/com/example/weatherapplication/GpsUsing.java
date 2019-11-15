package com.example.weatherapplication;


import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class GpsUsing implements LocationListener {

    private Context contextG;

    //GPS 사용 여부
    boolean isGPSEnabled = false;

    //네트워크 사용 여부
    boolean isNetworkEnabled = false;

    //GPS 상태값
    boolean isGetLocation = false;

    Location location;
    //위도 경도
    double lat;
    double lon;

    //도시 이름
    public String cityName = null;

    //GPS 업데이트 거리
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;

    //GPS 업데이트 시간 ( 밀리세컨드)
    private static final long MIN_TIME_BW_UPDATES = 1000;

    protected LocationManager locationManager;

    public GpsUsing (Context context){
        this.contextG=context;
        getLocation();
    }

    @TargetApi(23)
    public Location getLocation() {
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(contextG, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(contextG, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        try{
            locationManager = (LocationManager)contextG.getSystemService(Context.LOCATION_SERVICE);
//GPS 상태 확인
            isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
//네트워크 상태 확인
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if(!isGPSEnabled&&!isNetworkEnabled) {
            }else{
                this.isGetLocation = true;
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES,this);
                location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if(location!=null){
                    lat=location.getLatitude();
                    lon=location.getLongitude();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return location;
    }

    //GPS 종료
    public void stopUsingGPS(){
        if(locationManager!=null){
            locationManager.removeUpdates(GpsUsing.this);
        }
    }

    //위도값 return
    public double getLatitude(){
        if(location!=null){
            lat = location.getLatitude();
        }
        return lat;
    }

    //경도값 return
    public double getLongtitude(){
        if(location!=null){
            lon=location.getLongitude();
        }
        return lon;
    }
    //GPS가 켜져있는지 확인
    public boolean isGetLocation(){
        return this.isGetLocation;
    }


    //GPS정보 수집 실패시 경고창
    public void showSettingsAlerts(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(contextG);

        alertDialog.setTitle("GPS 설정");
        alertDialog.setMessage("GPS 설정이 꺼져있을 수도 있습니다. \n 설정하시겠습니까?");
        alertDialog.setPositiveButton("설정",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                contextG.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("취소",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int which){
                dialog.cancel();
            }
        });

        alertDialog.show();

    }

    public String getLocationCity(Context baseContext){
        List<Address> addresses;
        Address mAddress;
        Geocoder geocoder = new Geocoder(baseContext,Locale.KOREA);
        try {
            addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),5);
            if (addresses.size() > 0)
            {
                System.out.println(addresses.get(0).getLocality());
                mAddress=addresses.get(0);
                cityName = mAddress.getAddressLine(mAddress.getMaxAddressLineIndex());
            }
            else if (addresses == null || addresses.size() == 0) {
                cityName =  "주소 미발견";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    public void onLocationChanged(Location location){
        getLocation();

    }

    public void onStatusChanged(String provider, int status, Bundle extras){

    }

    public void onProviderEnabled(String provider){

    }

    public void onProviderDisabled(String provider){

    }

}