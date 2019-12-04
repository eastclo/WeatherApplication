package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewJeonbuk extends RelativeLayout {
    //data set
    public final static String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    TextView Jeonju, Iksan, Gunsan, Jeongeup, Gimje, Namwon, Wanju, Gochang, Buan, Sunchang, Imsil, Muju, Jinan, Jangsu;

    public DataViewJeonbuk(Context context, View view) {
        super(context);
        Jeonju = view.findViewById(R.id.data_Jeonbuk_Jeonju);
        Iksan = view.findViewById(R.id.data_Jeonbuk_Iksan);
        Gunsan = view.findViewById(R.id.data_Jeonbuk_Gunsan);
        Jeongeup = view.findViewById(R.id.data_Jeonbuk_Jeongeup);
        Gimje = view.findViewById(R.id.data_Jeonbuk_Gimje);
        Namwon = view.findViewById(R.id.data_Jeonbuk_Namwon);
        Wanju = view.findViewById(R.id.data_Jeonbuk_Wanju);
        Gochang = view.findViewById(R.id.data_Jeonbuk_Gochang);
        Buan = view.findViewById(R.id.data_Jeonbuk_Buan);
        Sunchang = view.findViewById(R.id.data_Jeonbuk_Sunchang);
        Imsil = view.findViewById(R.id.data_Jeonbuk_Imsil);
        Muju = view.findViewById(R.id.data_Jeonbuk_Muju);
        Jinan = view.findViewById(R.id.data_Jeonbuk_Jinan);
        Jangsu = view.findViewById(R.id.data_Jeonbuk_Jangsu);
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
                case "고창군":
                    setBackground(Gochang, dataSet, tmp.second);
                    Gochang.setText(tmp.second);
                    break;
                case "군산시":
                    setBackground(Gunsan, dataSet, tmp.second);
                    Gunsan.setText(tmp.second);
                    break;
                case "김제시":
                    setBackground(Gimje, dataSet, tmp.second);
                    Gimje.setText(tmp.second);
                    break;
                case "남원시":
                    setBackground(Namwon, dataSet, tmp.second);
                    Namwon.setText(tmp.second);
                    break;
                case "무주군":
                    setBackground(Muju, dataSet, tmp.second);
                    Muju.setText(tmp.second);
                    break;
                case "부안군":
                    setBackground(Buan, dataSet, tmp.second);
                    Buan.setText(tmp.second);
                    break;
                case "순창군":
                    setBackground(Sunchang, dataSet, tmp.second);
                    Sunchang.setText(tmp.second);
                    break;
                case "완주군":
                    setBackground(Wanju, dataSet, tmp.second);
                    Wanju.setText(tmp.second);
                    break;
                case "익산시":
                    setBackground(Iksan, dataSet, tmp.second);
                    Iksan.setText(tmp.second);
                    break;
                case "임실군":
                    setBackground(Imsil, dataSet, tmp.second);
                    Imsil.setText(tmp.second);
                    break;
                case "장수군":
                    setBackground(Jangsu, dataSet, tmp.second);
                    Jangsu.setText(tmp.second);
                    break;
                case "전주시":
                    setBackground(Jeonju, dataSet, tmp.second);
                    Jeonju.setText(tmp.second);
                    break;
                case "정읍시":
                    setBackground(Jeongeup, dataSet, tmp.second);
                    Jeongeup.setText(tmp.second);
                    break;
                case "진안군":
                    setBackground(Jinan, dataSet, tmp.second);
                    Jinan.setText(tmp.second);
                    break;
            }
        }
    }
}
