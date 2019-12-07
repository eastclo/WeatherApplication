package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewUlsan extends RelativeLayout implements DataView{

    TextView Buk, Dong, Jung, Nam, Ulju;

    public DataViewUlsan(Context context, View view) {
        super(context);
        this.Buk = view.findViewById(R.id.data_Ulsan_Buk);
        this.Dong = view.findViewById(R.id.data_Ulsan_Dong);
        this.Jung = view.findViewById(R.id.data_Ulsan_Jung);
        this.Nam = view.findViewById(R.id.data_Ulsan_Nam);
        this.Ulju = view.findViewById(R.id.data_Ulsan_Ulju);
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
                case "울주군":
                    setBackground(Ulju, dataSet, tmp.second);
                    Ulju.setText(tmp.second);
                    break;
                case "중구":
                    setBackground(Jung, dataSet, tmp.second);
                    Jung.setText(tmp.second);
                    break;
                case "북구":
                    setBackground(Buk, dataSet, tmp.second);
                    Buk.setText(tmp.second);
                    break;
                case "남구":
                    setBackground(Nam, dataSet, tmp.second);
                    Nam.setText(tmp.second);
                    break;
                case "동구":
                    setBackground(Dong, dataSet, tmp.second);
                    Dong.setText(tmp.second);
                    break;
            }
        }
    }
}
