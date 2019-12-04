package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewGyeongnam extends RelativeLayout {
    //data set
    public final static String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    TextView Changwon, Gimhae, Jinju, Yangsan, Geoje, Tongyeong, Sacheon, Miryang, Haman, Geochang,
            Changnyeong, Goseong, Namhae, Hapcheon, Hadong, Hamyang, Sancheong, Uiryeong;

    public DataViewGyeongnam(Context context, View view) {
        super(context);
        this.Changwon = view.findViewById(R.id.data_Gyeongnam_Changwon);
        this.Gimhae = view.findViewById(R.id.data_Gyeongnam_Gimhae);
        this.Jinju = view.findViewById(R.id.data_Gyeongnam_Jinju);
        this.Yangsan = view.findViewById(R.id.data_Gyeongnam_Yangsan);
        this.Geoje = view.findViewById(R.id.data_Gyeongnam_Geoje);
        this.Tongyeong = view.findViewById(R.id.data_Gyeongnam_Tongyeong);
        this.Sacheon = view.findViewById(R.id.data_Gyeongnam_Sacheon);
        this.Miryang = view.findViewById(R.id.data_Gyeongnam_Miryang);
        this.Haman = view.findViewById(R.id.data_Gyeongnam_Haman);
        this.Geochang = view.findViewById(R.id.data_Gyeongnam_Geochang);
        this.Changnyeong = view.findViewById(R.id.data_Gyeongnam_Changnyeong);
        this.Goseong = view.findViewById(R.id.data_Gyeongnam_Goseong);
        this.Namhae = view.findViewById(R.id.data_Gyeongnam_Namhae);
        this.Hapcheon = view.findViewById(R.id.data_Gyeongnam_Hapcheon);
        this.Hadong = view.findViewById(R.id.data_Gyeongnam_Hadong);
        this.Hamyang = view.findViewById(R.id.data_Gyeongnam_Hamyang);
        this.Sancheong = view.findViewById(R.id.data_Gyeongnam_Sancheong);
        this.Uiryeong = view.findViewById(R.id.data_Gyeongnam_Uiryeong);
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
                case "거제시":
                    setBackground(Geoje, dataSet, tmp.second);
                    Geoje.setText(tmp.second);
                    break;
                case "거창군":
                    setBackground(Geochang, dataSet, tmp.second);
                    Geochang.setText(tmp.second);
                    break;
                case "고성군":
                    setBackground(Goseong, dataSet, tmp.second);
                    Goseong.setText(tmp.second);
                    break;
                case "김해시":
                    setBackground(Gimhae, dataSet, tmp.second);
                    Gimhae.setText(tmp.second);
                    break;
                case "남해군":
                    setBackground(Namhae, dataSet, tmp.second);
                    Namhae.setText(tmp.second);
                    break;
                case "밀양시":
                    setBackground(Miryang, dataSet, tmp.second);
                    Miryang.setText(tmp.second);
                    break;
                case "사천시":
                    setBackground(Sacheon, dataSet, tmp.second);
                    Sacheon.setText(tmp.second);
                    break;
                case "산청군":
                    setBackground(Sancheong, dataSet, tmp.second);
                    Sancheong.setText(tmp.second);
                    break;
                case "양산시":
                    setBackground(Yangsan, dataSet, tmp.second);
                    Yangsan.setText(tmp.second);
                    break;
                case "의령군":
                    setBackground(Uiryeong, dataSet, tmp.second);
                    Uiryeong.setText(tmp.second);
                    break;
                case "진주시":
                    setBackground(Jinju, dataSet, tmp.second);
                    Jinju.setText(tmp.second);
                    break;
                case "창녕군":
                    setBackground(Changnyeong, dataSet, tmp.second);
                    Changnyeong.setText(tmp.second);
                    break;
                case "창원시":
                    setBackground(Changwon, dataSet, tmp.second);
                    Changwon.setText(tmp.second);
                    break;
                case "통영시":
                    setBackground(Tongyeong, dataSet, tmp.second);
                    Tongyeong.setText(tmp.second);
                    break;
                case "하동군":
                    setBackground(Hadong, dataSet, tmp.second);
                    Hadong.setText(tmp.second);
                    break;
                case "함안군":
                    setBackground(Haman, dataSet, tmp.second);
                    Haman.setText(tmp.second);
                    break;
                case "함양군":
                    setBackground(Hamyang, dataSet, tmp.second);
                    Hamyang.setText(tmp.second);
                    break;
                case "합천군":
                    setBackground(Hapcheon, dataSet, tmp.second);
                    Hapcheon.setText(tmp.second);
                    break;
            }
        }
    }
}
