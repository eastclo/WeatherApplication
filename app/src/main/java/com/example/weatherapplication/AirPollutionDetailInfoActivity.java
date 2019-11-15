package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TabHost;

public class AirPollutionDetailInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_pollution_detail_info);

        TabHost tabHost = findViewById(R.id.host);
        tabHost.setup();

        /*탭 위젯 텍스트 설정(미세먼지, 초미세먼지, 황사, 자외선, 오존)*/
        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setIndicator("미세먼지",null);
        spec.setContent(R.id.tab_content1);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator("초미세먼지",null);
        spec.setContent(R.id.tab_content2);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab3");
        spec.setIndicator("황사",null);
        spec.setContent(R.id.tab_content3);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab4");
        spec.setIndicator("자외선",null);
        spec.setContent(R.id.tab_content4);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab5");
        spec.setIndicator("오존",null);
        spec.setContent(R.id.tab_content5);
        tabHost.addTab(spec);
    }
}