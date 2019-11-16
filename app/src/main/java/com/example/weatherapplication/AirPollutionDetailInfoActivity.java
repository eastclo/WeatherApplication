package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.AdapterView.OnItemSelectedListener;

public class AirPollutionDetailInfoActivity extends AppCompatActivity implements OnItemSelectedListener{

    Spinner spinner;
    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_pollution_detail_info);


        tabHost = findViewById(R.id.host);
        tabHost.setup();

        TabHost.TabSpec spec = tabHost.newTabSpec("tab1");
        spec.setIndicator("미세먼지",null);
        spec.setContent(R.id.tab1);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab2");
        spec.setIndicator("초미세먼지",null);
        spec.setContent(R.id.tab2);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab3");
        spec.setIndicator("황사",null);
        spec.setContent(R.id.tab3);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab4");
        spec.setIndicator("오존",null);
        spec.setContent(R.id.tab4);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("tab5");
        spec.setIndicator("자외선",null);
        spec.setContent(R.id.tab5);
        tabHost.addTab(spec);


        spinner = (Spinner)findViewById(R.id.location_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

       spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        ImageView img1 = findViewById(R.id.tab1);
        ImageView img2 = findViewById(R.id.tab2);
        ImageView img3 = findViewById(R.id.tab3);
        ImageView img4 = findViewById(R.id.tab4);
        ImageView img5 = findViewById(R.id.tab5);
        switch(position){
            case 0:
                img1.setImageResource(R.drawable.map_korea);
                img2.setImageResource(R.drawable.map_korea);
                img3.setImageResource(R.drawable.map_korea);
                img4.setImageResource(R.drawable.map_korea);
                img5.setImageResource(R.drawable.map_korea);
                break;
            case 1:
                img1.setImageResource(R.drawable.map_gangwon);
                img2.setImageResource(R.drawable.map_gangwon);
                img3.setImageResource(R.drawable.map_gangwon);
                img4.setImageResource(R.drawable.map_gangwon);
                img5.setImageResource(R.drawable.map_gangwon);
                break;
            case 2:
                img1.setImageResource(R.drawable.map_gyeonggido);
                img2.setImageResource(R.drawable.map_gyeonggido);
                img3.setImageResource(R.drawable.map_gyeonggido);
                img4.setImageResource(R.drawable.map_gyeonggido);
                img5.setImageResource(R.drawable.map_gyeonggido);
                break;
            case 3:
                img1.setImageResource(R.drawable.map_gyeongsangnamdo);
                img2.setImageResource(R.drawable.map_gyeongsangnamdo);
                img3.setImageResource(R.drawable.map_gyeongsangnamdo);
                img4.setImageResource(R.drawable.map_gyeongsangnamdo);
                img5.setImageResource(R.drawable.map_gyeongsangnamdo);
                break;
            case 4:
                img1.setImageResource(R.drawable.map_gyeongsangbukdo);
                img2.setImageResource(R.drawable.map_gyeongsangbukdo);
                img3.setImageResource(R.drawable.map_gyeongsangbukdo);
                img4.setImageResource(R.drawable.map_gyeongsangbukdo);
                img5.setImageResource(R.drawable.map_gyeongsangbukdo);
                break;
            case 5:
                img1.setImageResource(R.drawable.map_gwangju);
                img2.setImageResource(R.drawable.map_gwangju);
                img3.setImageResource(R.drawable.map_gwangju);
                img4.setImageResource(R.drawable.map_gwangju);
                img5.setImageResource(R.drawable.map_gwangju);
                break;
            case 6:
                img1.setImageResource(R.drawable.map_daegu);
                img2.setImageResource(R.drawable.map_daegu);
                img3.setImageResource(R.drawable.map_daegu);
                img4.setImageResource(R.drawable.map_daegu);
                img5.setImageResource(R.drawable.map_daegu);
                break;
            case 7:
                img1.setImageResource(R.drawable.map_daejeon);
                img2.setImageResource(R.drawable.map_daejeon);
                img3.setImageResource(R.drawable.map_daejeon);
                img4.setImageResource(R.drawable.map_daejeon);
                img5.setImageResource(R.drawable.map_daejeon);
                break;
            case 8:
                img1.setImageResource(R.drawable.map_busan);
                img2.setImageResource(R.drawable.map_busan);
                img3.setImageResource(R.drawable.map_busan);
                img4.setImageResource(R.drawable.map_busan);
                img5.setImageResource(R.drawable.map_busan);
                break;
            case 9:
                img1.setImageResource(R.drawable.map_seoul);
                img2.setImageResource(R.drawable.map_seoul);
                img3.setImageResource(R.drawable.map_seoul);
                img4.setImageResource(R.drawable.map_seoul);
                img5.setImageResource(R.drawable.map_seoul);
                break;
            case 10:
                img1.setImageResource(R.drawable.map_sejong);
                img2.setImageResource(R.drawable.map_sejong);
                img3.setImageResource(R.drawable.map_sejong);
                img4.setImageResource(R.drawable.map_sejong);
                img5.setImageResource(R.drawable.map_sejong);
                break;
            case 11:
                img1.setImageResource(R.drawable.map_ulsan);
                img2.setImageResource(R.drawable.map_ulsan);
                img3.setImageResource(R.drawable.map_ulsan);
                img4.setImageResource(R.drawable.map_ulsan);
                img5.setImageResource(R.drawable.map_ulsan);
                break;
            case 12:
                img1.setImageResource(R.drawable.map_incheon);
                img2.setImageResource(R.drawable.map_incheon);
                img3.setImageResource(R.drawable.map_incheon);
                img4.setImageResource(R.drawable.map_incheon);
                img5.setImageResource(R.drawable.map_incheon);
                break;
            case 13:
                img1.setImageResource(R.drawable.map_jeollanamdo);
                img2.setImageResource(R.drawable.map_jeollanamdo);
                img3.setImageResource(R.drawable.map_jeollanamdo);
                img4.setImageResource(R.drawable.map_jeollanamdo);
                img5.setImageResource(R.drawable.map_jeollanamdo);
                break;
            case 14:
                img1.setImageResource(R.drawable.map_jeollabukdo);
                img2.setImageResource(R.drawable.map_jeollabukdo);
                img3.setImageResource(R.drawable.map_jeollabukdo);
                img4.setImageResource(R.drawable.map_jeollabukdo);
                img5.setImageResource(R.drawable.map_jeollabukdo);
                break;
            case 15:
                img1.setImageResource(R.drawable.map_jeju);
                img2.setImageResource(R.drawable.map_jeju);
                img3.setImageResource(R.drawable.map_jeju);
                img4.setImageResource(R.drawable.map_jeju);
                img5.setImageResource(R.drawable.map_jeju);
                break;
            case 16:
                img1.setImageResource(R.drawable.map_chungcheongnamdo);
                img2.setImageResource(R.drawable.map_chungcheongnamdo);
                img3.setImageResource(R.drawable.map_chungcheongnamdo);
                img4.setImageResource(R.drawable.map_chungcheongnamdo);
                img5.setImageResource(R.drawable.map_chungcheongnamdo);
                break;
            case 17:
                img1.setImageResource(R.drawable.map_chungcheongbukdo);
                img2.setImageResource(R.drawable.map_chungcheongbukdo);
                img3.setImageResource(R.drawable.map_chungcheongbukdo);
                img4.setImageResource(R.drawable.map_chungcheongbukdo);
                img5.setImageResource(R.drawable.map_chungcheongbukdo);
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) { }
}


