package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DataViewKorea extends RelativeLayout implements DataView{

    public DataViewKorea(Context context, View view) {
        super(context);
    }

    @Override
    public void setBackground(TextView view, String dataSet, String data) {

    }

    @Override
    public Drawable setColor(double blue, double green, double orange, String data) {
        return null;
    }

    @Override
    public void setData(ArrayList<Pair<String, String>> dataList, String dataSet) {

    }
}
