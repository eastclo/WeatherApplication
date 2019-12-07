package com.example.weatherapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.ArrayList;
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
    RelativeLayout map;
    View dataViewBusan, dataViewChungbuk, dataViewChungnam, dataViewDaegu, dataViewDaejeon, dataViewGangwon,
            dataViewGwangju, dataViewGyeongbuk, dataViewGyeonggi, dataViewGyeongnam, dataViewIncheon,
            dataViewJeju, dataViewJeonbuk, dataViewJeonnam, dataViewKorea, dataViewSeoul, dataViewUlsan;
    View currDataView;

    //gps 수신을 위한 방송수신자
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;

    //지역 정보 저장을 위한 변수 선언
    String adminArea;
    String subLocality;
    List<Address> addresses = null;

    //데이터 파싱을 위한 변수 선언
    final String[] cityList = {"전체","강원도","경기도","경상남도","경상북도","광주광역시","대구광역시","대전광역시","부산광역시","서울특별시","울산광역시","인천광역시","전라남도","전라북도","제주특별자치도","충청남도","충청북도"};
    String[] cityArray;
    String pollution = "PM10";   //PM10, PM25, O3 등
    int position = 0;

    Handler mhandler = new Handler();
    ArrayList<Pair<String, String>> dataList;
    DataView settingView;

    private class ThreadParser extends Thread{    //파싱
        @Override
        public void run() {
            AirQualityDataParser parser = new AirQualityDataParser(cityArray[position], pollution);
            if(position != 0) {
                dataList = parser.sggParsing();
            } else {
                if(pollution.equals(DataView.yellowDust))
                    ;
                else if(pollution.equals(DataView.ultraVioletRays))
                    dataList = parser.ultraVioletRaysUrlParsing();
                else
                    dataList = parser.sidoUrlParsing();
            }

            switch (position) {
                case 0:
                    settingView = new DataViewKorea(getApplicationContext(), currDataView);
                    break;
                case 1:
                    settingView = new DataViewGangwon(getApplicationContext(), currDataView);
                    break;
                case 2:
                    settingView = new DataViewGyeonggi(getApplicationContext(), currDataView);
                    break;
                case 3:
                    settingView = new DataViewGyeongnam(getApplicationContext(), currDataView);
                    break;
                case 4:
                    settingView = new DataViewGyeongbuk(getApplicationContext(), currDataView);
                    break;
                case 5:
                    settingView = new DataViewGwangju(getApplicationContext(), currDataView);
                    break;
                case 6:
                    settingView = new DataViewDaegu(getApplicationContext(), currDataView);
                    break;
                case 7:
                    settingView = new DataViewDaejeon(getApplicationContext(), currDataView);
                    break;
                case 8:
                    settingView = new DataViewBusan(getApplicationContext(), currDataView);
                    break;
                case 9:
                    settingView = new DataViewSeoul(getApplicationContext(), currDataView);
                    break;
                case 10:
                    settingView = new DataViewUlsan(getApplicationContext(), currDataView);
                    break;
                case 11:
                    settingView = new DataViewIncheon(getApplicationContext(), currDataView);
                    break;
                case 12:
                    settingView = new DataViewJeonnam(getApplicationContext(), currDataView);
                    break;
                case 13:
                    settingView = new DataViewJeonbuk(getApplicationContext(), currDataView);
                    break;
                case 14:
                    settingView = new DataViewJeju(getApplicationContext(), currDataView);
                    break;
                case 15:
                    settingView = new DataViewChungnam(getApplicationContext(), currDataView);
                    break;
                case 16:
                    settingView = new DataViewChungbuk(getApplicationContext(), currDataView);
                    break;
            }

            mhandler.post(new Runnable() {
                @Override
                public void run() {
                        settingView.setData(dataList, pollution);
                    }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_pollution_detail_info);
        adminAreaView = findViewById(R.id.admin_area);
        subLoocalityView = findViewById(R.id.sub_locality);

        indicator1 = findViewById(R.id.tab1_indicator) ;
        indicator2 = findViewById(R.id.tab2_indicator) ;
        indicator3 = findViewById(R.id.tab3_indicator) ;
        indicator4 = findViewById(R.id.tab4_indicator) ;
        indicator5 = findViewById(R.id.tab5_indicator) ;

        indicator1.setOnClickListener(this);
        indicator2.setOnClickListener(this);
        indicator3.setOnClickListener(this);
        indicator4.setOnClickListener(this);
        indicator5.setOnClickListener(this);
        cityArray = getResources().getStringArray(R.array.cityName);
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

        //데이터 출력, Spinner 리스너에서 visibility 설정
        map = (RelativeLayout) findViewById(R.id.tab_content);
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataViewKorea = inflater.inflate(R.layout.activity_data_view_korea, null);
        map.addView(dataViewKorea);
        dataViewSeoul = inflater.inflate(R.layout.activity_data_view_seoul, null);
        map.addView(dataViewSeoul);
        dataViewBusan = inflater.inflate(R.layout.activity_data_view_busan, null);
        map.addView(dataViewBusan);
        dataViewChungbuk = inflater.inflate(R.layout.activity_data_view_chungbuk, null);
        map.addView(dataViewChungbuk);
        dataViewChungnam = inflater.inflate(R.layout.activity_data_view_chungnam, null);
        map.addView(dataViewChungnam);
        dataViewDaegu = inflater.inflate(R.layout.activity_data_view_daegu, null);
        map.addView(dataViewDaegu);
        dataViewDaejeon = inflater.inflate(R.layout.activity_data_view_daejeon, null);
        map.addView(dataViewDaejeon);
        dataViewGangwon = inflater.inflate(R.layout.activity_data_view_gangwon, null);
        map.addView(dataViewGangwon);
        dataViewGwangju = inflater.inflate(R.layout.activity_data_view_gwangju, null);
        map.addView(dataViewGwangju);
        dataViewGyeongbuk = inflater.inflate(R.layout.activity_data_view_gyeongbuk, null);
        map.addView(dataViewGyeongbuk);
        dataViewGyeonggi = inflater.inflate(R.layout.activity_data_view_gyeonggi, null);
        map.addView(dataViewGyeonggi);
        dataViewGyeongnam = inflater.inflate(R.layout.activity_data_view_gyeongnam, null);
        map.addView(dataViewGyeongnam);
        dataViewIncheon = inflater.inflate(R.layout.activity_data_view_incheon, null);
        map.addView(dataViewIncheon);
        dataViewJeju = inflater.inflate(R.layout.activity_data_view_jeju, null);
        map.addView(dataViewJeju);
        dataViewJeonbuk = inflater.inflate(R.layout.activity_data_view_jeonbuk, null);
        map.addView(dataViewJeonbuk);
        dataViewJeonnam = inflater.inflate(R.layout.activity_data_view_jeonnam, null);
        map.addView(dataViewJeonnam);
        dataViewUlsan = inflater.inflate(R.layout.activity_data_view_ulsan, null);
        map.addView(dataViewUlsan);
        currDataView = dataViewKorea;

        spinner = (Spinner)findViewById(R.id.location_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    private void setSpinnerSelection(){
        int cnt = 0;

        for (String tmp : cityList) {
            if (adminArea.equals(tmp)) {
                spinner.setSelection(cnt);
                position = cnt;
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
            pollution = "PM10";
        }else if(view == indicator2) {
            img.setImageResource(R.drawable.standard_ultrafine_dust);
            pollution = "PM25";
        }
        else if(view == indicator3) {
            img.setImageResource(R.drawable.standard_yellow_dust);
            pollution = DataView.yellowDust;
        }
        else if(view == indicator4) {
            img.setImageResource(R.drawable.standard_ultraviolet_rays);
            pollution = DataView.ultraVioletRays;
        }
        else if(view == indicator5) {
            img.setImageResource(R.drawable.standard_ozone);
            pollution = "O3";
        }
        ThreadParser parserThread = new ThreadParser();
        parserThread.start();   //탭 클릭 될 때 마다 파싱
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.position = position;
        ImageView img = findViewById(R.id.map);
        switch(position){
            case 0:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_korea);
                dataViewKorea.setVisibility(View.VISIBLE);
                currDataView = dataViewKorea;
                break;
            case 1:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gangwon);
                dataViewGangwon.setVisibility(View.VISIBLE);
                currDataView = dataViewGangwon;
                break;
            case 2:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeonggido);
                dataViewGyeonggi.setVisibility(View.VISIBLE);
                currDataView = dataViewGyeonggi;
                break;
            case 3:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeongsangnamdo);
                dataViewGyeongnam.setVisibility(View.VISIBLE);
                currDataView = dataViewGyeongnam;
                break;
            case 4:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeongsangbukdo);
                dataViewGyeongbuk.setVisibility(View.VISIBLE);
                currDataView = dataViewGyeongbuk;
                break;
            case 5:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gwangju);
                dataViewGwangju.setVisibility(View.VISIBLE);
                currDataView = dataViewGwangju;
                break;
            case 6:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_daegu);
                dataViewDaegu.setVisibility(View.VISIBLE);
                currDataView = dataViewDaegu;
                break;
            case 7:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_daejeon);
                dataViewDaejeon.setVisibility(View.VISIBLE);
                currDataView = dataViewDaejeon;
                break;
            case 8:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_busan);
                dataViewBusan.setVisibility(View.VISIBLE);
                currDataView = dataViewBusan;
                break;
            case 9:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_seoul);
                dataViewSeoul.setVisibility(View.VISIBLE);
                currDataView = dataViewSeoul;
                break;
            case 10:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_ulsan);
                dataViewUlsan.setVisibility(View.VISIBLE);
                currDataView = dataViewUlsan;
                break;
            case 11:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_incheon);
                dataViewIncheon.setVisibility(View.VISIBLE);
                currDataView = dataViewIncheon;
                break;
            case 12:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeollanamdo);
                dataViewJeonnam.setVisibility(View.VISIBLE);
                currDataView = dataViewJeonnam;
                break;
            case 13:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeollabukdo);
                dataViewJeonbuk.setVisibility(View.VISIBLE);
                currDataView = dataViewJeonbuk;
                break;
            case 14:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeju);
                dataViewJeju.setVisibility(View.VISIBLE);
                currDataView = dataViewJeju;
                break;
            case 15:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_chungcheongnamdo);
                dataViewChungnam.setVisibility(View.VISIBLE);
                currDataView = dataViewChungnam;
                break;
            case 16:
                currDataView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_chungcheongbukdo);
                dataViewChungbuk.setVisibility(View.VISIBLE);
                currDataView = dataViewChungbuk;
                break;
        }
        ThreadParser parserThread = new ThreadParser();
        parserThread.start();   //스피너 선택 될 때 마다 파싱
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}


