package com.example.weatherapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AirPollutionDetailInfoActivity extends AppCompatActivity implements OnItemSelectedListener, OnClickListener {

    Spinner spinner;
    TextView indicator1;
    TextView indicator2;
    TextView indicator3;
    TextView indicator4;
    TextView indicator5;
    TextView adminAreaView;
    TextView subLoocalityView;

    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;

    //지역 정보 저장을 위한 변수 선언
    String adminArea;
    String subLocality;
    List<Address> addresses = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_pollution_detail_info);
        adminAreaView = (TextView)findViewById(R.id.admin_area);
        subLoocalityView = (TextView)findViewById(R.id.sub_locality);

        indicator1 = (TextView)findViewById(R.id.tab1_indicator) ;
        indicator2 = (TextView)findViewById(R.id.tab2_indicator) ;
        indicator3 = (TextView)findViewById(R.id.tab3_indicator) ;
        indicator4 = (TextView)findViewById(R.id.tab4_indicator) ;
        indicator5 = (TextView)findViewById(R.id.tab5_indicator) ;

        indicator1.setOnClickListener(this);
        indicator2.setOnClickListener(this);
        indicator3.setOnClickListener(this);
        indicator4.setOnClickListener(this);
        indicator5.setOnClickListener(this);

       /*GPS 서비스 시작*/
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);

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

                    setSpinnerSelection();   //지역 정보에 따라 spinner default 값 설정을 위한 메소드 호출
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        spinner = (Spinner)findViewById(R.id.location_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setSpinnerSelection(){
        String[] array = getResources().getStringArray(R.array.cityName);
        int cnt = 0;

        for (String tmp : array){
            if(adminArea.contains(tmp)){
                spinner.setSelection(cnt);
                break;
            }
            cnt++;
    }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(GpsService.ACTION_LOCATION_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,GpsService.class));
    }

    @Override
    public void onClick(View view) {
        ImageView img = findViewById(R.id.atmosphere_environment_standard);
        if(view == indicator1){
            img.setImageResource(R.drawable.standard_fin_dust);
        }else if(view == indicator2) {
            img.setImageResource(R.drawable.standard_ultrafine_dust);
        }
        else if(view == indicator3) {
            img.setImageResource(R.drawable.standard_yellow_dust);
        }
        else if(view == indicator4) {
            img.setImageResource(R.drawable.standard_ultraviolet_rays);
        }
        else if(view == indicator5) {
            img.setImageResource(R.drawable.standard_ozone);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ImageView img = findViewById(R.id.map);
        switch(position){
            case 0:
                img.setImageResource(R.drawable.map_korea);
                break;
            case 1:
                img.setImageResource(R.drawable.map_gangwon);
                break;
            case 2:
                img.setImageResource(R.drawable.map_gyeonggido);
                break;
            case 3:
                img.setImageResource(R.drawable.map_gyeongsangnamdo);
                break;
            case 4:
                img.setImageResource(R.drawable.map_gyeongsangbukdo);
                break;
            case 5:
                img.setImageResource(R.drawable.map_gwangju);
                break;
            case 6:
                img.setImageResource(R.drawable.map_daegu);
                break;
            case 7:
                img.setImageResource(R.drawable.map_daejeon);
                break;
            case 8:
                img.setImageResource(R.drawable.map_busan);
                break;
            case 9:
                img.setImageResource(R.drawable.map_seoul);
                break;
            case 10:
                img.setImageResource(R.drawable.map_sejong);
                break;
            case 11:
                img.setImageResource(R.drawable.map_ulsan);
                break;
            case 12:
                img.setImageResource(R.drawable.map_incheon);
                break;
            case 13:
                img.setImageResource(R.drawable.map_jeollanamdo);
                break;
            case 14:
                img.setImageResource(R.drawable.map_jeollabukdo);
                break;
            case 15:
                img.setImageResource(R.drawable.map_jeju);
                break;
            case 16:
                img.setImageResource(R.drawable.map_chungcheongnamdo);
                break;
            case 17:
                img.setImageResource(R.drawable.map_chungcheongbukdo);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}


