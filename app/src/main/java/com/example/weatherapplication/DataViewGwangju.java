package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewGwangju extends RelativeLayout implements DataView{

    TextView Buk, Dong, Gwangsan, Nam, Seo;

    public DataViewGwangju(Context context, View view) {
        super(context);
        this.Buk = view.findViewById(R.id.data_Gwangju_Buk);
        this.Dong = view.findViewById(R.id.data_Gwangju_Dong);
        this.Gwangsan = view.findViewById(R.id.data_Gwangju_Gwangsan);
        this.Nam = view.findViewById(R.id.data_Gwangju_Nam);
        this.Seo = view.findViewById(R.id.data_Gwangju_Seo);
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
                case "북구":
                    setBackground(Buk, dataSet, tmp.second);
                    Buk.setText(tmp.second);
                    break;
                case "동구":
                    setBackground(Dong, dataSet, tmp.second);
                    Dong.setText(tmp.second);
                    break;
                case "남구":
                    setBackground(Nam, dataSet, tmp.second);
                    Nam.setText(tmp.second);
                    break;
                case "서구":
                    setBackground(Seo, dataSet, tmp.second);
                    Seo.setText(tmp.second);
                    break;
                case "광산구":
                    setBackground(Gwangsan, dataSet, tmp.second);
                    Gwangsan.setText(tmp.second);
                    break;
            }
        }
    }
}
