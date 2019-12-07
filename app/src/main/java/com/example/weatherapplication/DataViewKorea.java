package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewKorea extends RelativeLayout implements DataView{

    TextView Seoul, Busan, Daegu, Incheon, Gwangju, Daejeon, Ulsan, Sejong, Gyeonggi, Gangwon,
          Chungbuk, Chungnam, Jeonbuk, Jeonnam, Gyeongbuk, Gyeongnam, Jeju;

    public DataViewKorea(Context context, View view) {
        super(context);
        this.Seoul = view.findViewById(R.id.data_seoul);
        this.Busan = view.findViewById(R.id.data_busan);
        this.Daegu = view.findViewById(R.id.data_daegu);
        this.Incheon = view.findViewById(R.id.data_incheon);
        this.Gwangju = view.findViewById(R.id.data_gwangju);
        this.Daejeon = view.findViewById(R.id.data_daejeon);
        this.Ulsan = view.findViewById(R.id.data_ulsan);
        this.Sejong = view.findViewById(R.id.data_sejong);
        this.Gyeonggi = view.findViewById(R.id.data_gyeonggi);
        this.Gangwon = view.findViewById(R.id.data_gangwon);
        this.Chungbuk = view.findViewById(R.id.data_chungbuk);
        this.Chungnam = view.findViewById(R.id.data_chungnam);
        this.Jeonbuk = view.findViewById(R.id.data_jeonbuk);
        this.Jeonnam = view.findViewById(R.id.data_jeonnam);
        this.Gyeongbuk = view.findViewById(R.id.data_gyeongbuk);
        this.Gyeongnam = view.findViewById(R.id.data_gyeongnam);
        this.Jeju = view.findViewById(R.id.data_jeju);
    }

    /*finedust: ~30 좋음, ~80 보통, ~150 나쁨
     * ultrafinedust: ~15 좋음, ~35 보통, ~75 나쁨
     * ozone: ~0.030 좋음, ~0.090 보통, ~0.15 나쁨
     * yellowdust: ~199 보통, ~399 약간나쁨, ~799  나쁨
     * ultravioletrays: ~2 낮음, ~5 보통, ~7 높음, ~10 매우높음
     * * */
    @Override
    public void setBackground(TextView view, String dataSet, String data) {
        Drawable drawable = null;
        switch(dataSet){
            case fineDust:
                drawable = setColor(30,80,150, data);
                break;
            case ultraFineDust:
                drawable = setColor(15,35,75, data);
                break;
            case ozone:
                drawable = setColor(0.03,0.09,0.15, data);
                break;
            case yellowDust:
                drawable = setColor(199,399,799, data);
                break;
            case ultraVioletRays:
                drawable = setColor(2,5,7,10, data);
                break;
        }
        view.setBackground(drawable);
    }

    @Override
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

    public Drawable setColor(double blue, double green, double yellowOrange, double orange, String data){
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
        else if(value <= yellowOrange)
            drawable = getResources().getDrawable(R.drawable.edge_radius_yelloworange);
        else if(value <= orange)
            drawable = getResources().getDrawable(R.drawable.edge_radius_orange);
        else
            drawable = getResources().getDrawable(R.drawable.edge_radius_red);
        return drawable;
    }

    @Override
    public void setData(ArrayList<Pair<String, String>> dataList, String dataSet) {
        Iterator<Pair<String,String>> it = dataList.iterator();
        Pair<String,String> tmp;
        Drawable background = null;
        while(it.hasNext()) {
            tmp = it.next();
            switch (tmp.first) {
                case "서울특별시":
                    setBackground(Seoul, dataSet, tmp.second);
                    Seoul.setText(tmp.second);
                    break;
                case "부산광역시":
                    setBackground(Busan, dataSet, tmp.second);
                    Busan.setText(tmp.second);
                    break;
                case "대구광역시":
                    setBackground(Daegu, dataSet, tmp.second);
                    Daegu.setText(tmp.second);
                    break;
                case "인천광역시":
                    setBackground(Incheon, dataSet, tmp.second);
                    Incheon.setText(tmp.second);
                    break;
                case "광주광역시":
                    setBackground(Gwangju, dataSet, tmp.second);
                    Gwangju.setText(tmp.second);
                    break;
                case "대전광역시":
                    setBackground(Daejeon, dataSet, tmp.second);
                    Daejeon.setText(tmp.second);
                    break;
                case "울산광역시":
                    setBackground(Ulsan, dataSet, tmp.second);
                    Ulsan.setText(tmp.second);
                    break;
                case "경기도":
                    setBackground(Gyeonggi, dataSet, tmp.second);
                    Gyeonggi.setText(tmp.second);
                    break;
                case "강원도":
                    setBackground(Gangwon, dataSet, tmp.second);
                    Gangwon.setText(tmp.second);
                    break;
                case "충청북도":
                    setBackground(Chungbuk, dataSet, tmp.second);
                    Chungbuk.setText(tmp.second);
                    break;
                case "충청남도":
                    setBackground(Chungnam, dataSet, tmp.second);
                    Chungnam.setText(tmp.second);
                    break;
                case "전라북도":
                    setBackground(Jeonbuk, dataSet, tmp.second);
                    Jeonbuk.setText(tmp.second);
                    break;
                case "전라남도":
                    setBackground(Jeonnam, dataSet, tmp.second);
                    Jeonnam.setText(tmp.second);
                    break;
                case "경상북도":
                    setBackground(Gyeongbuk, dataSet, tmp.second);
                    Gyeongbuk.setText(tmp.second);
                    break;
                case "경상남도":
                    setBackground(Gyeongnam, dataSet, tmp.second);
                    Gyeongnam.setText(tmp.second);
                    break;
                case "제주특별자치도":
                    setBackground(Jeju, dataSet, tmp.second);
                    Jeju.setText(tmp.second);
                    break;
                case "세종시":
                    setBackground(Sejong, dataSet, tmp.second);
                    Sejong.setText(tmp.second);
                    break;
            }
        }
    }
}
