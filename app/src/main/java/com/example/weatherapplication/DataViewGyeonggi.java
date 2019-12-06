package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewGyeonggi extends RelativeLayout implements DataView{

    TextView Suwon, Seongnam, Goyang, Yongin, Bucheon, Ansan, Anyang, Namyangju, Hwaseong, Uijeongbu, Siheung,
            Pyeongtaek, Gwangmyeong, Paju, Gunpo, Gwangju, Gimpo, Icheon, Yangju, Guri, Osan, Anseong, Uiwang,
            Hanam, Pocheon, Dongducheon, Gwacheon, Yeoju, Yangpyeong, Gapyeong, Yeoncheon;

    public DataViewGyeonggi(Context context, View view) {
        super(context);
        this.Suwon = view.findViewById(R.id.data_Gyeonggi_Suwon);
        this.Seongnam = view.findViewById(R.id.data_Gyeonggi_Seongnam);
        this.Goyang = view.findViewById(R.id.data_Gyeonggi_Goyang);
        this.Yongin = view.findViewById(R.id.data_Gyeonggi_Yongin);
        this.Bucheon = view.findViewById(R.id.data_Gyeonggi_Bucheon);
        this.Ansan = view.findViewById(R.id.data_Gyeonggi_Ansan);
        this.Anyang = view.findViewById(R.id.data_Gyeonggi_Anyang);
        this.Namyangju = view.findViewById(R.id.data_Gyeonggi_Namyangju);
        this.Hwaseong = view.findViewById(R.id.data_Gyeonggi_Hwaseong);
        this.Uijeongbu = view.findViewById(R.id.data_Gyeonggi_Uijeongbu);
        this.Siheung = view.findViewById(R.id.data_Gyeonggi_Siheung);
        this.Pyeongtaek = view.findViewById(R.id.data_Gyeonggi_Pyeongtaek);
        this.Gwangmyeong = view.findViewById(R.id.data_Gyeonggi_Gwangmyeong);
        this.Paju = view.findViewById(R.id.data_Gyeonggi_Paju);
        this.Gunpo = view.findViewById(R.id.data_Gyeonggi_Gunpo);
        this.Gwangju = view.findViewById(R.id.data_Gyeonggi_Gwangju);
        this.Gimpo = view.findViewById(R.id.data_Gyeonggi_Gimpo);
        this.Icheon = view.findViewById(R.id.data_Gyeonggi_Icheon);
        this.Yangju = view.findViewById(R.id.data_Gyeonggi_Yangju);
        this.Guri = view.findViewById(R.id.data_Gyeonggi_Guri);
        this.Osan = view.findViewById(R.id.data_Gyeonggi_Osan);
        this.Anseong = view.findViewById(R.id.data_Gyeonggi_Anseong);
        this.Uiwang = view.findViewById(R.id.data_Gyeonggi_Uiwang);
        this.Hanam = view.findViewById(R.id.data_Gyeonggi_Hanam);
        this.Pocheon = view.findViewById(R.id.data_Gyeonggi_Pocheon);
        this.Dongducheon = view.findViewById(R.id.data_Gyeonggi_Dongducheon);
        this.Gwacheon = view.findViewById(R.id.data_Gyeonggi_Gwacheon);
        this.Yeoju = view.findViewById(R.id.data_Gyeonggi_Yeoju);
        this.Yangpyeong = view.findViewById(R.id.data_Gyeonggi_Yangpyeong);
        this.Gapyeong = view.findViewById(R.id.data_Gyeonggi_Gapyeong);
        this.Yeoncheon = view.findViewById(R.id.data_Gyeonggi_Yeoncheon);
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
                case "가평군":
                    setBackground(Gapyeong, dataSet, tmp.second);
                    Gapyeong.setText(tmp.second);
                    break;
                case "고양시":
                    setBackground(Goyang, dataSet, tmp.second);
                    Goyang.setText(tmp.second);
                    break;
                case "과천시":
                    setBackground(Gwacheon, dataSet, tmp.second);
                    Gwacheon.setText(tmp.second);
                    break;
                case "광명시":
                    setBackground(Gwangmyeong, dataSet, tmp.second);
                    Gwangmyeong.setText(tmp.second);
                    break;
                case "광주시":
                    setBackground(Gwangju, dataSet, tmp.second);
                    Gwangju.setText(tmp.second);
                    break;
                case "구리시":
                    setBackground(Guri, dataSet, tmp.second);
                    Guri.setText(tmp.second);
                    break;
                case "군포시":
                    setBackground(Gunpo, dataSet, tmp.second);
                    Gunpo.setText(tmp.second);
                    break;
                case "김포시":
                    setBackground(Gimpo, dataSet, tmp.second);
                    Gimpo.setText(tmp.second);
                    break;
                case "남양주시":
                    setBackground(Namyangju, dataSet, tmp.second);
                    Namyangju.setText(tmp.second);
                    break;
                case "동두천시":
                    setBackground(Dongducheon, dataSet, tmp.second);
                    Dongducheon.setText(tmp.second);
                    break;
                case "부천시":
                    setBackground(Bucheon, dataSet, tmp.second);
                    Bucheon.setText(tmp.second);
                    break;
                case "성남시":
                    setBackground(Seongnam, dataSet, tmp.second);
                    Seongnam.setText(tmp.second);
                    break;
                case "수원시":
                    setBackground(Suwon, dataSet, tmp.second);
                    Suwon.setText(tmp.second);
                    break;
                case "시흥시":
                    setBackground(Siheung, dataSet, tmp.second);
                    Siheung.setText(tmp.second);
                    break;
                case "안산시":
                    setBackground(Ansan, dataSet, tmp.second);
                    Ansan.setText(tmp.second);
                    break;
                case "안성시":
                    setBackground(Anseong, dataSet, tmp.second);
                    Anseong.setText(tmp.second);
                    break;
                case "안양시":
                    setBackground(Anyang, dataSet, tmp.second);
                    Anyang.setText(tmp.second);
                    break;
                case "양주시":
                    setBackground(Yangju, dataSet, tmp.second);
                    Yangju.setText(tmp.second);
                    break;
                case "양평군":
                    setBackground(Yangpyeong, dataSet, tmp.second);
                    Yangpyeong.setText(tmp.second);
                    break;
                case "여주시":
                    setBackground(Yeoju, dataSet, tmp.second);
                    Yeoju.setText(tmp.second);
                    break;
                case "연천군":
                    setBackground(Yeoncheon, dataSet, tmp.second);
                    Yeoncheon.setText(tmp.second);
                    break;
                case "오산시":
                    setBackground(Osan, dataSet, tmp.second);
                    Osan.setText(tmp.second);
                    break;
                case "용인시":
                    setBackground(Yongin, dataSet, tmp.second);
                    Yongin.setText(tmp.second);
                    break;
                case "의왕시":
                    setBackground(Uiwang, dataSet, tmp.second);
                    Uiwang.setText(tmp.second);
                    break;
                case "의정부시":
                    setBackground(Uijeongbu, dataSet, tmp.second);
                    Uijeongbu.setText(tmp.second);
                    break;
                case "이천시":
                    setBackground(Icheon, dataSet, tmp.second);
                    Icheon.setText(tmp.second);
                    break;
                case "파주시":
                    setBackground(Paju, dataSet, tmp.second);
                    Paju.setText(tmp.second);
                    break;
                case "평택시":
                    setBackground(Pyeongtaek, dataSet, tmp.second);
                    Pyeongtaek.setText(tmp.second);
                    break;
                case "포천시":
                    setBackground(Pocheon, dataSet, tmp.second);
                    Pocheon.setText(tmp.second);
                    break;
                case "하남시":
                    setBackground(Hanam, dataSet, tmp.second);
                    Hanam.setText(tmp.second);
                    break;
                case "화성시":
                    setBackground(Hwaseong, dataSet, tmp.second);
                    Hwaseong.setText(tmp.second);
                    break;
            }
        }
    }
}
