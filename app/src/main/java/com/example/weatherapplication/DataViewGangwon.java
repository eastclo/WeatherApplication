package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewGangwon extends RelativeLayout implements DataView{

    TextView Wonju, Chuncheon, Donghae, Sokcho, Samcheok, Hongcheon, Cheorwon,
            Hoengseong, Pyeongchang, Jeongseon, Goseong, Yanggu;

    public DataViewGangwon(Context context, View view) {
        super(context);
        this.Wonju = view.findViewById(R.id.data_Gangwon_Wonju);
        this.Chuncheon = view.findViewById(R.id.data_Gangwon_Chuncheon);
        this.Donghae = view.findViewById(R.id.data_Gangwon_Donghae);
        this.Sokcho = view.findViewById(R.id.data_Gangwon_Sokcho);
        this.Samcheok = view.findViewById(R.id.data_Gangwon_Samcheok);
        this.Hongcheon = view.findViewById(R.id.data_Gangwon_Hongcheon);
        this.Cheorwon = view.findViewById(R.id.data_Gangwon_Cheorwon);
        this.Hoengseong = view.findViewById(R.id.data_Gangwon_Hoengseong);
        this.Pyeongchang = view.findViewById(R.id.data_Gangwon_Pyeongchang);
        this.Jeongseon = view.findViewById(R.id.data_Gangwon_Jeongseon);
        this.Goseong = view.findViewById(R.id.data_Gangwon_Goseong);
        this.Yanggu = view.findViewById(R.id.data_Gangwon_Yanggu);
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


    public void setData(ArrayList<Pair<String,String>> dataList, String dataSet) {
        //String: 도시, String: 데이터값, dataSet: 통계정보 탭
        Iterator<Pair<String, String>> it = dataList.iterator();
        Pair<String, String> tmp;
        Drawable background = null;
        while (it.hasNext()) {
            tmp = it.next();
            switch (tmp.first) {
                case "고성군":
                    setBackground(Goseong, dataSet, tmp.second);
                    Goseong.setText(tmp.second);
                    break;
                case "양구군":
                    setBackground(Yanggu, dataSet, tmp.second);
                    Yanggu.setText(tmp.second);
                    break;
                case "철원군":
                    setBackground(Cheorwon, dataSet, tmp.second);
                    Cheorwon.setText(tmp.second);
                    break;
                case "춘천시":
                    setBackground(Chuncheon, dataSet, tmp.second);
                    Chuncheon.setText(tmp.second);
                    break;
                case "횡성군":
                    setBackground(Hoengseong, dataSet, tmp.second);
                    Hoengseong.setText(tmp.second);
                    break;
                case "홍천군":
                    setBackground(Hongcheon, dataSet, tmp.second);
                    Hongcheon.setText(tmp.second);
                    break;
                case "평창군":
                    setBackground(Pyeongchang, dataSet, tmp.second);
                    Pyeongchang.setText(tmp.second);
                    break;
                case "원주시":
                    setBackground(Wonju, dataSet, tmp.second);
                    Wonju.setText(tmp.second);
                    break;
                case "삼척시":
                    setBackground(Samcheok, dataSet, tmp.second);
                    Samcheok.setText(tmp.second);
                    break;
                case "동해시":
                    setBackground(Donghae, dataSet, tmp.second);
                    Donghae.setText(tmp.second);
                    break;
                case "속초시":
                    setBackground(Sokcho, dataSet, tmp.second);
                    Sokcho.setText(tmp.second);
                    break;
                case "정선군":
                    setBackground(Jeongseon, dataSet, tmp.second);
                    Jeongseon.setText(tmp.second);
                    break;
            }
        }
    }
}
