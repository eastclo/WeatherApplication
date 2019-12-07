package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewChungbuk extends RelativeLayout implements DataView{

    TextView Cheongju, Chungju, Jecheon, Eumseong, Jincheon, Okcheon, Yeongdong, Goesan, Jeungpyeong, Boeun, Danyang;

    public DataViewChungbuk(Context context, View view) {
        super(context);
        this.Cheongju = view.findViewById(R.id.data_Chungbuk_Cheongju);
        this.Chungju = view.findViewById(R.id.data_Chungbuk_Chungju);
        this.Jecheon = view.findViewById(R.id.data_Chungbuk_Jecheon);
        this.Eumseong = view.findViewById(R.id.data_Chungbuk_Eumseong);
        this.Jincheon = view.findViewById(R.id.data_Chungbuk_Jincheon);
        this.Okcheon = view.findViewById(R.id.data_Chungbuk_Okcheon);
        this.Yeongdong = view.findViewById(R.id.data_Chungbuk_Yeongdong);
        this.Goesan = view.findViewById(R.id.data_Chungbuk_Goesan);
        this.Jeungpyeong = view.findViewById(R.id.data_Chungbuk_Jeungpyeong);
        this.Boeun = view.findViewById(R.id.data_Chungbuk_Boeun);
        this.Danyang = view.findViewById(R.id.data_Chungbuk_Danyang);
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
                case "괴산군":
                    setBackground(Goesan, dataSet, tmp.second);
                    Goesan.setText(tmp.second);
                    break;
                case "단양군":
                    setBackground(Danyang, dataSet, tmp.second);
                    Danyang.setText(tmp.second);
                    break;
                case "보은군":
                    setBackground(Boeun, dataSet, tmp.second);
                    Boeun.setText(tmp.second);
                    break;
                case "영동군":
                    setBackground(Yeongdong, dataSet, tmp.second);
                    Yeongdong.setText(tmp.second);
                    break;
                case "옥천군":
                    setBackground(Okcheon, dataSet, tmp.second);
                    Okcheon.setText(tmp.second);
                    break;
                case "음성군":
                    setBackground(Eumseong, dataSet, tmp.second);
                    Eumseong.setText(tmp.second);
                    break;
                case "제천시":
                    setBackground(Jecheon, dataSet, tmp.second);
                    Jecheon.setText(tmp.second);
                    break;
                case "진천군":
                    setBackground(Jincheon, dataSet, tmp.second);
                    Jincheon.setText(tmp.second);
                    break;
                case "청주시":
                    setBackground(Cheongju, dataSet, tmp.second);
                    Cheongju.setText(tmp.second);
                    break;
                case "충주시":
                    setBackground(Chungju, dataSet, tmp.second);
                    Chungju.setText(tmp.second);
                    break;
                case "증평군":
                    setBackground(Jeungpyeong, dataSet, tmp.second);
                    Jeungpyeong.setText(tmp.second);
                    break;
            }
        }
    }
}
