package com.example.weatherapplication;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import javax.annotation.Nullable;

public class GpsService extends Service {

    private LocationManager locationManager = null;
    private static final int LOCATION_MILLISEC = 1000;
    private static final float LOCATION_DISTANCE = 1;
    public Location slocation =null;

    public static final String
            ACTION_LOCATION_BROADCAST = GpsService.class.getName() + "LocationBroadcast",
            EXTRA_LATITUDE = "extra_latitude",
            EXTRA_LONGITUDE = "extra_longitude";

    LocationListener[] locationListeners = new LocationListener[]{ //requestLocationUpdates 메소드에 넘겨줄 LocationListener
            new LocationListener(LocationManager.GPS_PROVIDER),
            new LocationListener(LocationManager.NETWORK_PROVIDER)
    };

    private class LocationListener implements android.location.LocationListener {
        Location lastLocation = null;

        public LocationListener(String provider) {
            lastLocation = new Location(provider);
            slocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            lastLocation.set(location);
            slocation.set(location);
            sendBroadcastMessage(lastLocation); //Location이 바뀔 때 마다 sendMessage
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }


    private void sendBroadcastMessage(Location location) {  //location 값을 send한다.
        if (location != null) {
            Intent intent = new Intent(ACTION_LOCATION_BROADCAST);
            intent.putExtra(EXTRA_LATITUDE, location.getLatitude());
            intent.putExtra(EXTRA_LONGITUDE, location.getLongitude());
            LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public class LocalBinder extends Binder {
        GpsService getCountService(){
            return GpsService.this;
        }
    }
    public Location getLocation(){
        return slocation;
    }

    private final Binder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(getApplicationContext(),android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        sendBroadcastMessage(locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));
            try {
                //1초마다 확인해서 1미터 이상의 위치 변화가 있을 때마다 LocationListener라는 인터페이스의 onLocationChanged 를 호출
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, LOCATION_MILLISEC, LOCATION_DISTANCE,
                        locationListeners[1]);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, LOCATION_MILLISEC, LOCATION_DISTANCE,
                        locationListeners[0]);
            } catch (java.lang.SecurityException ex) {
            } catch (IllegalArgumentException ex){}
        slocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        }

        @Override
        public void onDestroy(){
            super.onDestroy();
            if (locationManager != null) {
                for (int i = 0; i < locationListeners.length; i++) {
                    try {
                        locationManager.removeUpdates(locationListeners[i]);
                    } catch (Exception ex) {}
                }
            }
        }
    }