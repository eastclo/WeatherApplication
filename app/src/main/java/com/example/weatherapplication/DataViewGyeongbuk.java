package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewGyeongbuk extends RelativeLayout {
    //data set
    public final static String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    TextView Gyeongsan, Gyeongju, Goryeong, Gumi, Gimcheon, Bonghwa, Sangju,
            Andong, Yeongju, Yeongcheon, Ulleung, Uljin, Chilgok, Pohang;

    public DataViewGyeongbuk(Context context, View view) {
        super(context);
        this.Gyeongsan = view.findViewById(R.id.data_Gyeongbuk_Gyeongsan);
        this.Gyeongju = view.findViewById(R.id.data_Gyeongbuk_Gyeongju);
        this.Goryeong = view.findViewById(R.id.data_Gyeongbuk_Goryeong);
        this.Gumi = view.findViewById(R.id.data_Gyeongbuk_Gumi);
        this.Gimcheon = view.findViewById(R.id.data_Gyeongbuk_Gimcheon);
        this.Bonghwa = view.findViewById(R.id.data_Gyeongbuk_Bonghwa);
        this.Sangju = view.findViewById(R.id.data_Gyeongbuk_Sangju);
        this.Andong = view.findViewById(R.id.data_Gyeongbuk_Andong);
        this.Yeongju = view.findViewById(R.id.data_Gyeongbuk_Yeongju);
        this.Yeongcheon = view.findViewById(R.id.data_Gyeongbuk_Yeongcheon);
        this.Ulleung = view.findViewById(R.id.data_Gyeongbuk_Ulleung);
        this.Uljin = view.findViewById(R.id.data_Gyeongbuk_Uljin);
        this.Chilgok = view.findViewById(R.id.data_Gyeongbuk_Chilgok);
        this.Pohang = view.findViewById(R.id.data_Gyeongbuk_Pohang);
    }

    /*finedust: ~30 좋음, ~80 보통, ~150 나쁨
     * ultrafinedust: ~15 좋음, ~35 보통, ~75 나쁨
     *  yellowdust: ~0.030 좋음, ~0.090 보통, ~0.15 나쁨
     * * */
    public void setBackground(TextView view, String dataSet, String data){
        Drawable drawable = null;
        switch(dataSet){
            case fineDust:
                drawable = setColor(30,80,150,data);
                break;
            case ultraFineDust:
                drawable = setColor(15,35,75,data);
                break;
            case ozone:
                drawable = setColor(0.03,0.09,0.15, data);
                break;
        }
        view.setBackground(drawable);
    }

    public Drawable setColor(double blue, double green, double orange, String data){
        Drawable drawable;
        if(data == "-"){
            drawable = getResources().getDrawable(R.drawable.edge_radius_gray);
            return drawable;
        }
        double value = Double.parseDouble(data);
        if(value <= blue)
            drawable = getResources().getDrawable(R.drawable.edge_radius_blue);
        else if(value <= green)
            drawable = getResources().getDrawable(R.drawable.edge_radius_green);
        else if(value <= orange)
            drawable = getResources().getDrawable(R.drawable.edge_radius_orange);
        else
            drawable = getResources().getDrawable(R.drawable.edge_radius_red);
        return drawable;
    }

    public void setData(ArrayList<Pair<String,String>> dataList, String dataSet){
        //String: 도시, String: 데이터값, dataSet: 통계정보 탭
        Iterator<Pair<String,String>> it = dataList.iterator();
        Pair<String,String> tmp;
        Drawable background = null;
        while(it.hasNext()) {
            tmp = it.next();
            switch (tmp.first) {
                case "경산시":
                    setBackground(Gyeongsan, dataSet, tmp.second);
                    Gyeongsan.setText(tmp.second);
                    break;
                case "경주시":
                    setBackground(Gyeongju, dataSet, tmp.second);
                    Gyeongju.setText(tmp.second);
                    break;
                case "고령군":
                    setBackground(Goryeong, dataSet, tmp.second);
                    Goryeong.setText(tmp.second);
                    break;
                case "구미시":
                    setBackground(Gumi, dataSet, tmp.second);
                    Gumi.setText(tmp.second);
                    break;
                case "김천시":
                    setBackground(Gimcheon, dataSet, tmp.second);
                    Gimcheon.setText(tmp.second);
                    break;
                case "봉화군":
                    setBackground(Bonghwa, dataSet, tmp.second);
                    Bonghwa.setText(tmp.second);
                    break;
                case "상주시":
                    setBackground(Sangju, dataSet, tmp.second);
                    Sangju.setText(tmp.second);
                    break;
                case "안동시":
                    setBackground(Andong, dataSet, tmp.second);
                    Andong.setText(tmp.second);
                    break;
                case "영주시":
                    setBackground(Yeongju, dataSet, tmp.second);
                    Yeongju.setText(tmp.second);
                    break;
                case "영천시":
                    setBackground(Yeongcheon, dataSet, tmp.second);
                    Yeongcheon.setText(tmp.second);
                    break;
                case "울릉군":
                    setBackground(Ulleung, dataSet, tmp.second);
                    Ulleung.setText(tmp.second);
                    break;
                case "울진군":
                    setBackground(Uljin, dataSet, tmp.second);
                    Uljin.setText(tmp.second);
                    break;
                case "칠곡군":
                    setBackground(Chilgok, dataSet, tmp.second);
                    Chilgok.setText(tmp.second);
                    break;
                case "포항시":
                    setBackground(Pohang, dataSet, tmp.second);
                    Pohang.setText(tmp.second);
                    break;
            }
        }
    }
}
