package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewDaegu extends RelativeLayout {
    //data set
    public final static String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    TextView Jung, Dong, Seo, Nam, Buk, Suseong, Dalseo, Dalseong;

    public DataViewDaegu(Context context, View view) {
        super(context);
        this.Jung = findViewById(R.id.data_Daegu_Jung);
        this.Dong = findViewById(R.id.data_Daegu_Dong);
        this.Seo = findViewById(R.id.data_Daegu_Seo);
        this.Nam = findViewById(R.id.data_Daegu_Nam);
        this.Buk = findViewById(R.id.data_Daegu_Buk);
        this.Suseong = findViewById(R.id.data_Daegu_Suseong);
        this.Dalseo = findViewById(R.id.data_Daegu_Dalseo);
        this.Dalseong = findViewById(R.id.data_Daegu_Dalseong);
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
                case "남구":
                    setBackground(Nam, dataSet, tmp.second);
                    Nam.setText(tmp.second);
                    break;
                case "달서구":
                    setBackground(Dalseo, dataSet, tmp.second);
                    Dalseo.setText(tmp.second);
                    break;
                case "달성군":
                    setBackground(Dalseong, dataSet, tmp.second);
                    Dalseong.setText(tmp.second);
                    break;
                case "동구":
                    setBackground(Dong, dataSet, tmp.second);
                    Dong.setText(tmp.second);
                    break;
                case "북구":
                    setBackground(Buk, dataSet, tmp.second);
                    Buk.setText(tmp.second);
                    break;
                case "서구":
                    setBackground(Seo, dataSet, tmp.second);
                    Seo.setText(tmp.second);
                    break;
                case "수성구":
                    setBackground(Suseong, dataSet, tmp.second);
                    Suseong.setText(tmp.second);
                    break;
                case "중구":
                    setBackground(Jung, dataSet, tmp.second);
                    Jung.setText(tmp.second);
                    break;
            }
        }
    }
}
