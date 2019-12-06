package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewBusan extends RelativeLayout implements DataView {

    TextView Buk, Busanjin, Dong, Dongnae, Gangseo, Geumjeong, Haeundae,
            Jung, Nam, Saha, Sasang, Seo, Suyeong, Yeongdo, Yeonje, Gijang;

    public DataViewBusan(Context context, View view) {
        super(context);
        this.Buk = view.findViewById(R.id.data_Busan_Buk);
        this.Busanjin = view.findViewById(R.id.data_Busan_Busanjin);
        this.Dong = view.findViewById(R.id.data_Busan_Dong);
        this.Dongnae = view.findViewById(R.id.data_Busan_Dongnae);
        this.Gangseo = view.findViewById(R.id.data_Busan_Gangseo);
        this.Geumjeong = view.findViewById(R.id.data_Busan_Geumjeong);
        this.Haeundae = view.findViewById(R.id.data_Busan_Haeundae);
        this.Jung = view.findViewById(R.id.data_Busan_Jung);
        this.Nam = view.findViewById(R.id.data_Busan_Nam);
        this.Saha = view.findViewById(R.id.data_Busan_Saha);
        this.Sasang = view.findViewById(R.id.data_Busan_Sasang);
        this.Seo = view.findViewById(R.id.data_Busan_Seo);
        this.Suyeong = view.findViewById(R.id.data_Busan_Suyeong);
        this.Yeongdo = view.findViewById(R.id.data_Busan_Yeongdo);
        this.Yeonje = view.findViewById(R.id.data_Busan_Yeonje);
        this.Gijang = view.findViewById(R.id.data_Busan_Gijang);
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
        if(data.equals("-")){
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
                case "강서구":
                    setBackground(Gangseo, dataSet, tmp.second);
                    Gangseo.setText(tmp.second);
                    break;
                case "금정구":
                    setBackground(Geumjeong, dataSet, tmp.second);
                    Geumjeong.setText(tmp.second);
                    break;
                case "기장군":
                    setBackground(Gijang, dataSet, tmp.second);
                    Gijang.setText(tmp.second);
                    break;
                case "남구":
                    setBackground(Nam, dataSet, tmp.second);
                    Nam.setText(tmp.second);
                    break;
                case "동구":
                    setBackground(Dong, dataSet, tmp.second);
                    Dong.setText(tmp.second);
                    break;
                case "동래구":
                    setBackground(Dongnae, dataSet, tmp.second);
                    Dongnae.setText(tmp.second);
                    break;
                case "부산진구":
                    setBackground(Busanjin, dataSet, tmp.second);
                    Busanjin.setText(tmp.second);
                    break;
                case "북구":
                    setBackground(Buk, dataSet, tmp.second);
                    Buk.setText(tmp.second);
                    break;
                case "사상구":
                    setBackground(Sasang, dataSet, tmp.second);
                    Sasang.setText(tmp.second);
                    break;
                case "사하구":
                    setBackground(Saha, dataSet, tmp.second);
                    Saha.setText(tmp.second);
                    break;
                case "서구":
                    setBackground(Seo, dataSet, tmp.second);
                    Seo.setText(tmp.second);
                    break;
                case "수영구":
                    setBackground(Suyeong, dataSet, tmp.second);
                    Suyeong.setText(tmp.second);
                    break;
                case "연제구":
                    setBackground(Yeonje, dataSet, tmp.second);
                    Yeonje.setText(tmp.second);
                    break;
                case "영도구":
                    setBackground(Yeongdo, dataSet, tmp.second);
                    Yeongdo.setText(tmp.second);
                    break;
                case "중구":
                    setBackground(Jung, dataSet, tmp.second);
                    Jung.setText(tmp.second);
                    break;
                case "해운대구":
                    setBackground(Haeundae, dataSet, tmp.second);
                    Haeundae.setText(tmp.second);
                    break;
            }
        }
    }

}
