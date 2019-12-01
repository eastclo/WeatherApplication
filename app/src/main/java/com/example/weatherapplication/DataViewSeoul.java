package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewSeoul extends RelativeLayout {
    //data set
    public final static String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    TextView Dobong, Dongdaemun, Dongjak, Eunpyeong, Gangbuk, Gangdong, Gangnam, Gangseo, Geumcheon, Guro, Gwanak, Gwangjin,
    Jongno, Jung, Jungnang, Mapo, Nowon, Seocho, Seodaemun, Seongbuk, Seongdong, Songpa, Yangcheon, Yeongdeungpo, Yongsan;

    public DataViewSeoul(Context context, View view) {
        super(context);
        this.Songpa = view.findViewById(R.id.data_Seoul_Songpa);
        this.Dobong = view.findViewById(R.id.data_Seoul_Dobong);
        this.Dongdaemun = view.findViewById(R.id.data_Seoul_Dongdaemun);
        this.Dongjak= view.findViewById(R.id.data_Seoul_Dongjak);
        this.Eunpyeong = view.findViewById(R.id.data_Seoul_Eunpyeong);
        this.Gangbuk = view.findViewById(R.id.data_Seoul_Gangbuk);
        this.Gangdong = view.findViewById(R.id.data_Seoul_Gangdong);
        this.Gangnam = view.findViewById(R.id.data_Seoul_Gangnam);
        this.Gangseo = view.findViewById(R.id.data_Seoul_Gangseo);
        this.Geumcheon = view.findViewById(R.id.data_Seoul_Geumcheon);
        this.Guro = view.findViewById(R.id.data_Seoul_Guro);
        this.Gwanak = view.findViewById(R.id.data_Seoul_Gwanak);
        this.Gwangjin = view.findViewById(R.id.data_Seoul_Gwangjin);
        this.Jongno = view.findViewById(R.id.data_Seoul_Jongno);
        this.Jung = view.findViewById(R.id.data_Seoul_Jung);
        this.Jungnang = view.findViewById(R.id.data_Seoul_Jungnang);
        this.Mapo = view.findViewById(R.id.data_Seoul_Mapo);
        this.Nowon = view.findViewById(R.id.data_Seoul_Nowon);
        this.Seocho = view.findViewById(R.id.data_Seoul_Seocho);
        this.Seodaemun = view.findViewById(R.id.data_Seoul_Seodaemun);
        this.Seongbuk = view.findViewById(R.id.data_Seoul_Seongbuk);
        this.Seongdong = view.findViewById(R.id.data_Seoul_Seongdong);
        this.Songpa = view.findViewById(R.id.data_Seoul_Songpa);
        this.Yangcheon = view.findViewById(R.id.data_Seoul_Yangcheon);
        this.Yeongdeungpo = view.findViewById(R.id.data_Seoul_Yeongdeungpo);
        this.Yongsan = view.findViewById(R.id.data_Seoul_Yongsan);
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
                case "강남구":
                    setBackground(Gangnam, dataSet, tmp.second);
                    Gangnam.setText(tmp.second);
                    break;
                case "강동구":
                    setBackground(Gangdong, dataSet, tmp.second);
                    Gangdong.setText(tmp.second);
                    break;
                case "강북구":
                    setBackground(Gangbuk, dataSet, tmp.second);
                    Gangbuk.setText(tmp.second);
                    break;
                case "강서구":
                    setBackground(Gangseo, dataSet, tmp.second);
                    Gangseo.setText(tmp.second);
                    break;
                case "관악구":
                    setBackground(Gwanak, dataSet, tmp.second);
                    Gwanak.setText(tmp.second);
                    break;
                case "광진구":
                    setBackground(Gwangjin, dataSet, tmp.second);
                    Gwangjin.setText(tmp.second);
                    break;
                case "구로구":
                    setBackground(Guro, dataSet, tmp.second);
                    Guro.setText(tmp.second);
                    break;
                case "금천구":
                    setBackground(Geumcheon, dataSet, tmp.second);
                    Geumcheon.setText(tmp.second);
                    break;
                case "노원구":
                    setBackground(Nowon, dataSet, tmp.second);
                    Nowon.setText(tmp.second);
                    break;
                case "도봉구":
                    setBackground(Dobong, dataSet, tmp.second);
                    Dobong.setText(tmp.second);
                    break;
                case "동대문구":
                    setBackground(Dongdaemun, dataSet, tmp.second);
                    Dongdaemun.setText(tmp.second);
                    break;
                case "동작구":
                    setBackground(Dongjak, dataSet, tmp.second);
                    Dongjak.setText(tmp.second);
                    break;
                case "마포구":
                    setBackground(Mapo, dataSet, tmp.second);
                    Mapo.setText(tmp.second);
                    break;
                case "서대문구":
                    setBackground(Seodaemun, dataSet, tmp.second);
                    Seodaemun.setText(tmp.second);
                    break;
                case "서초구":
                    setBackground(Seocho, dataSet, tmp.second);
                    Seocho.setText(tmp.second);
                    break;
                case "성동구":
                    setBackground(Seongdong, dataSet, tmp.second);
                    Seongdong.setText(tmp.second);
                    break;
                case "성북구":
                    setBackground(Seongbuk, dataSet, tmp.second);
                    Seongbuk.setText(tmp.second);
                    break;
                case "송파구":
                    setBackground(Songpa, dataSet, tmp.second);
                    Songpa.setText(tmp.second);
                    break;
                case "양천구":
                    setBackground(Yangcheon, dataSet, tmp.second);
                    Yangcheon.setText(tmp.second);
                    break;
                case "영등포구":
                    setBackground(Yeongdeungpo, dataSet, tmp.second);
                    Yeongdeungpo.setText(tmp.second);
                    break;
                case "용산구":
                    setBackground(Yongsan, dataSet, tmp.second);
                    Yongsan.setText(tmp.second);
                    break;
                case "은평구":
                    setBackground(Eunpyeong, dataSet, tmp.second);
                    Eunpyeong.setText(tmp.second);
                    break;
                case "종로구":
                    setBackground(Jongno, dataSet, tmp.second);
                    Jongno.setText(tmp.second);
                    break;
                case "중구":
                    setBackground(Jung, dataSet, tmp.second);
                    Jung.setText(tmp.second);
                    break;
                case "중랑구":
                    setBackground(Jungnang, dataSet, tmp.second);
                    Jungnang.setText(tmp.second);
                    break;
            }
        }
    }
}
