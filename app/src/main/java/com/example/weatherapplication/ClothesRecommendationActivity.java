package com.example.weatherapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ClothesRecommendationActivity extends AppCompatActivity {

    //지역 정보 저장을 위한 변수 선언
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;
    String adminArea;
    String subLocality;
    List<Address> addresses = null;
    TextView adminAreaView;
    TextView subLoocalityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_recommendation);
        adminAreaView = (TextView)findViewById(R.id.admin_area);
        subLoocalityView = (TextView)findViewById(R.id.sub_locality);

        //Gps서비스 시작
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);

        //지역 정보 출력을 위한 로컬브로드캐스트 호출
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double latitude = intent.getDoubleExtra(GpsService.EXTRA_LATITUDE,0);
                double longitude = intent.getDoubleExtra(GpsService.EXTRA_LONGITUDE,0);
                Geocoder coder = new Geocoder(getApplicationContext(), Locale.KOREA);
                try {
                    addresses = coder.getFromLocation(latitude, longitude, 3); // 첫번째 파라미터로 위도, 두번째로 경도, 세번째 파라미터로 리턴할 Address객체의 개수
                    adminArea = addresses.get(0).getAdminArea();
                    subLocality = addresses.get(0).getSubLocality();

                    adminAreaView.setText(adminArea);
                    subLoocalityView.setText((subLocality));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(GpsService.ACTION_LOCATION_BROADCAST));
    }
}
