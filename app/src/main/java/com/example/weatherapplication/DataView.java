package com.example.weatherapplication;

import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.widget.TextView;

import java.util.ArrayList;

public interface DataView {
    //data set
    String fineDust = "FINE_DUST", ultraFineDust = "ULTRA_FINE_DUST", ozone = "OZONE";

    void setBackground(TextView view, String dataSet, String data);
    Drawable setColor(double blue, double green, double orange, String data);
    void setData(ArrayList<Pair<String,String>> dataList, String dataSet);
}
