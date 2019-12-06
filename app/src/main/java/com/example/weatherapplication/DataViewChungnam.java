package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewChungnam extends RelativeLayout implements DataView{

    TextView Cheonan, Asan, Seosan, Dangjin, Gongju, Nonsan, Boryeong, Hongseong, Yesan,
            Buyeo, Seocheon, Taean, Geumsan, Cheongyang;

    public DataViewChungnam(Context context, View view) {
        super(context);
        this.Cheonan = view.findViewById(R.id.data_Chungnam_Cheonan);
        this.Asan = view.findViewById(R.id.data_Chungnam_Asan);
        this.Seosan = view.findViewById(R.id.data_Chungnam_Seosan);
        this.Dangjin = view.findViewById(R.id.data_Chungnam_Dangjin);
        this.Gongju = view.findViewById(R.id.data_Chungnam_Gongju);
        this.Nonsan = view.findViewById(R.id.data_Chungnam_Nonsan);
        this.Boryeong = view.findViewById(R.id.data_Chungnam_Boryeong);
        this.Hongseong = view.findViewById(R.id.data_Chungnam_Hongseong);
        this.Yesan = view.findViewById(R.id.data_Chungnam_Yesan);
        this.Buyeo = view.findViewById(R.id.data_Chungnam_Buyeo);
        this.Seocheon = view.findViewById(R.id.data_Chungnam_Seocheon);
        this.Taean = view.findViewById(R.id.data_Chungnam_Taean);
        this.Geumsan = view.findViewById(R.id.data_Chungnam_Geumsan);
        this.Cheongyang = view.findViewById(R.id.data_Chungnam_Cheongyang);
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
                case "공주시":
                    setBackground(Gongju, dataSet, tmp.second);
                    Gongju.setText(tmp.second);
                    break;
                case "금산군":
                    setBackground(Geumsan, dataSet, tmp.second);
                    Geumsan.setText(tmp.second);
                    break;
                case "논산시":
                    setBackground(Nonsan, dataSet, tmp.second);
                    Nonsan.setText(tmp.second);
                    break;
                case "당진시":
                    setBackground(Dangjin, dataSet, tmp.second);
                    Dangjin.setText(tmp.second);
                    break;
                case "보령시":
                    setBackground(Boryeong, dataSet, tmp.second);
                    Boryeong.setText(tmp.second);
                    break;
                case "부여군":
                    setBackground(Buyeo, dataSet, tmp.second);
                    Buyeo.setText(tmp.second);
                    break;
                case "서산시":
                    setBackground(Seosan, dataSet, tmp.second);
                    Seosan.setText(tmp.second);
                    break;
                case "서천군":
                    setBackground(Seocheon, dataSet, tmp.second);
                    Seocheon.setText(tmp.second);
                    break;
                case "아산시":
                    setBackground(Asan, dataSet, tmp.second);
                    Asan.setText(tmp.second);
                    break;
                case "예산군":
                    setBackground(Yesan, dataSet, tmp.second);
                    Yesan.setText(tmp.second);
                    break;
                case "천안시":
                    setBackground(Cheonan, dataSet, tmp.second);
                    Cheonan.setText(tmp.second);
                    break;
                case "청양군":
                    setBackground(Cheongyang, dataSet, tmp.second);
                    Cheongyang.setText(tmp.second);
                    break;
                case "태안군":
                    setBackground(Taean, dataSet, tmp.second);
                    Taean.setText(tmp.second);
                    break;
                case "홍성군":
                    setBackground(Hongseong, dataSet, tmp.second);
                    Hongseong.setText(tmp.second);
                    break;
            }
        }
    }
}
