package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewJeonnam extends RelativeLayout implements DataView{

    TextView Yeosu, Mokpo, Suncheon, Gwangyang, Naju, Muan, Haenam, Goheung, Hwasun, Yeongam, Yeonggwang, Wando,
            Damyang, Boseong, Jangseong, Jangheung, Gangjin, Sinan, Hampyeong, Jindo, Gokseong, Gurye;

    public DataViewJeonnam(Context context, View view) {
        super(context);
        this.Yeosu = view.findViewById(R.id.data_Jeonnam_Yeosu);
        this.Mokpo = view.findViewById(R.id.data_Jeonnam_Mokpo);
        this.Suncheon = view.findViewById(R.id.data_Jeonnam_Suncheon);
        this.Gwangyang = view.findViewById(R.id.data_Jeonnam_Gwangyang);
        this.Naju = view.findViewById(R.id.data_Jeonnam_Naju);
        this.Muan = view.findViewById(R.id.data_Jeonnam_Muan);
        this.Haenam = view.findViewById(R.id.data_Jeonnam_Haenam);
        this.Goheung = view.findViewById(R.id.data_Jeonnam_Goheung);
        this.Hwasun = view.findViewById(R.id.data_Jeonnam_Hwasun);
        this.Yeongam = view.findViewById(R.id.data_Jeonnam_Yeongam);
        this.Yeonggwang = view.findViewById(R.id.data_Jeonnam_Yeonggwang);
        this.Wando = view.findViewById(R.id.data_Jeonnam_Wando);
        this.Damyang = view.findViewById(R.id.data_Jeonnam_Damyang);
        this.Boseong = view.findViewById(R.id.data_Jeonnam_Boseong);
        this.Jangseong = view.findViewById(R.id.data_Jeonnam_Jangseong);
        this.Jangheung = view.findViewById(R.id.data_Jeonnam_Jangheung);
        this.Gangjin = view.findViewById(R.id.data_Jeonnam_Gangjin);
        this.Sinan = view.findViewById(R.id.data_Jeonnam_Sinan);
        this.Hampyeong = view.findViewById(R.id.data_Jeonnam_Hampyeong);
        this.Jindo = view.findViewById(R.id.data_Jeonnam_Jindo);
        this.Gokseong = view.findViewById(R.id.data_Jeonnam_Gokseong);
        this.Gurye = view.findViewById(R.id.data_Jeonnam_Gurye);
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
                case "강진군":
                    setBackground(Gangjin, dataSet, tmp.second);
                    Gangjin.setText(tmp.second);
                    break;
                case "고흥군":
                    setBackground(Goheung, dataSet, tmp.second);
                    Goheung.setText(tmp.second);
                    break;
                case "곡성군":
                    setBackground(Gokseong, dataSet, tmp.second);
                    Gokseong.setText(tmp.second);
                    break;
                case "광양시":
                    setBackground(Gwangyang, dataSet, tmp.second);
                    Gwangyang.setText(tmp.second);
                    break;
                case "구례군":
                    setBackground(Gurye, dataSet, tmp.second);
                    Gurye.setText(tmp.second);
                    break;
                case "나주시":
                    setBackground(Naju, dataSet, tmp.second);
                    Naju.setText(tmp.second);
                    break;
                case "담양군":
                    setBackground(Damyang, dataSet, tmp.second);
                    Damyang.setText(tmp.second);
                    break;
                case "목포시":
                    setBackground(Mokpo, dataSet, tmp.second);
                    Mokpo.setText(tmp.second);
                    break;
                case "무안군":
                    setBackground(Muan, dataSet, tmp.second);
                    Muan.setText(tmp.second);
                    break;
                case "보성군":
                    setBackground(Boseong, dataSet, tmp.second);
                    Boseong.setText(tmp.second);
                    break;
                case "순천시":
                    setBackground(Suncheon, dataSet, tmp.second);
                    Suncheon.setText(tmp.second);
                    break;
                case "신안군":
                    setBackground(Sinan, dataSet, tmp.second);
                    Sinan.setText(tmp.second);
                    break;
                case "여수시":
                    setBackground(Yeosu, dataSet, tmp.second);
                    Yeosu.setText(tmp.second);
                    break;
                case "영광군":
                    setBackground(Yeonggwang, dataSet, tmp.second);
                    Yeonggwang.setText(tmp.second);
                    break;
                case "영암군":
                    setBackground(Yeongam, dataSet, tmp.second);
                    Yeongam.setText(tmp.second);
                    break;
                case "완도군":
                    setBackground(Wando, dataSet, tmp.second);
                    Wando.setText(tmp.second);
                    break;
                case "장성군":
                    setBackground(Jangseong, dataSet, tmp.second);
                    Jangseong.setText(tmp.second);
                    break;
                case "장흥군":
                    setBackground(Jangheung, dataSet, tmp.second);
                    Jangheung.setText(tmp.second);
                    break;
                case "진도군":
                    setBackground(Jindo, dataSet, tmp.second);
                    Jindo.setText(tmp.second);
                    break;
                case "함평군":
                    setBackground(Hampyeong, dataSet, tmp.second);
                    Hampyeong.setText(tmp.second);
                    break;
                case "해남군":
                    setBackground(Haenam, dataSet, tmp.second);
                    Haenam.setText(tmp.second);
                    break;
                case "화순군":
                    setBackground(Hwasun, dataSet, tmp.second);
                    Hwasun.setText(tmp.second);
                    break;
            }
        }
    }
}
