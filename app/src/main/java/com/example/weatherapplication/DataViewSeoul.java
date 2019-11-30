package com.example.weatherapplication;

import android.content.Context;
import android.util.Pair;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;

public class DataViewSeoul extends RelativeLayout {
    TextView Dobong, Dongdaemun, Dongjak, Eunpyeong, Gangbuk, Gangdong, Gangnam, Gangseo, Geumcheon, Guro, Gwanak, Gwangjin,
    Jongno, Jung, Jungnang, Mapo, Nowon, Seocho, Seodaemun, Seongbuk, Seongdong, Songpa, Yangcheon, Yeongdeungpo, Yongsan;

    public DataViewSeoul(Context context) {
        super(context);
        this.Dobong = findViewById(R.id.data_Seoul_Dobong);
        this.Dongdaemun = findViewById(R.id.data_Seoul_Dongdaemun);
        this.Dongjak= findViewById(R.id.data_Seoul_Dongjak);
        this.Eunpyeong = findViewById(R.id.data_Seoul_Eunpyeong);
        this.Gangbuk = findViewById(R.id.data_Seoul_Gangbuk);
        this.Gangdong = findViewById(R.id.data_Seoul_Gangdong);
        this.Gangnam = findViewById(R.id.data_Seoul_Gangnam);
        this.Gangseo = findViewById(R.id.data_Seoul_Gangseo);
        this.Geumcheon = findViewById(R.id.data_Seoul_Geumcheon);
        this.Guro = findViewById(R.id.data_Seoul_Guro);
        this.Gwanak = findViewById(R.id.data_Seoul_Gwanak);
        this.Gwangjin = findViewById(R.id.data_Seoul_Gwangjin);
        this.Jongno = findViewById(R.id.data_Seoul_Jongno);
        this.Jung = findViewById(R.id.data_Seoul_Jung);
        this.Jungnang = findViewById(R.id.data_Seoul_Jungnang);
        this.Mapo = findViewById(R.id.data_Seoul_Mapo);
        this.Nowon = findViewById(R.id.data_Seoul_Nowon);
        this.Seocho = findViewById(R.id.data_Seoul_Seocho);
        this.Seodaemun = findViewById(R.id.data_Seoul_Seodaemun);
        this.Seongbuk = findViewById(R.id.data_Seoul_Seongbuk);
        this.Seongdong = findViewById(R.id.data_Seoul_Seongdong);
        this.Songpa = findViewById(R.id.data_Seoul_Songpa);
        this.Yangcheon = findViewById(R.id.data_Seoul_Yangcheon);
        this.Yeongdeungpo = findViewById(R.id.data_Seoul_Yeongdeungpo);
        this.Yongsan = findViewById(R.id.data_Seoul_Yongsan);
    }

    public void setData(ArrayList<Pair<String,Integer>> data){  //String: 도시, Integer: 데이터값
        Iterator<Pair<String,Integer>> it = data.iterator();
        Pair<String,Integer> tmp;
        while(it.hasNext()) {
           tmp = it.next();
            switch (tmp.first) {
                case "강남구":
                    Gangnam.setText(tmp.second);
                    break;
                case "강동구":
                    Gangdong.setText(tmp.second);
                    break;
                case "강북구":
                    Gangbuk.setText(tmp.second);
                    break;
                case "강서구":
                    Gangseo.setText(tmp.second);
                    break;
                case "관악구":
                    Gwanak.setText(tmp.second);
                    break;
                case "광진구":
                    Gwangjin.setText(tmp.second);
                    break;
                case "구로구":
                    Guro.setText(tmp.second);
                    break;
                case "금천구":
                    Geumcheon.setText(tmp.second);
                    break;
                case "노원구":
                    Nowon.setText(tmp.second);
                    break;
                case "도봉구":
                    Dobong.setText(tmp.second);
                    break;
                case "동대문구":
                    Dongdaemun.setText(tmp.second);
                    break;
                case "동작구":
                    Dongjak.setText(tmp.second);
                    break;
                case "마포구":
                    Mapo.setText(tmp.second);
                    break;
                case "서대문구":
                    Seodaemun.setText(tmp.second);
                    break;
                case "서초구":
                    Seocho.setText(tmp.second);
                    break;
                case "성동구":
                    Seongdong.setText(tmp.second);
                    break;
                case "성북구":
                    Seongbuk.setText(tmp.second);
                    break;
                case "송파구":
                    Songpa.setText(tmp.second);
                    break;
                case "양천구":
                    Yangcheon.setText(tmp.second);
                    break;
                case "영등포구":
                    Yeongdeungpo.setText(tmp.second);
                    break;
                case "용산구":
                    Yongsan.setText(tmp.second);
                    break;
                case "은평구":
                    Eunpyeong.setText(tmp.second);
                    break;
                case "종로구":
                    Jongno.setText(tmp.second);
                    break;
                case "중구":
                    Jung.setText(tmp.second);
                    break;
                case "중랑구":
                    Jungnang.setText(tmp.second);
                    break;
            }
        }
    }
}
