package com.example.weatherapplication;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

public interface PercentView {
    void setBackground(TextView view, double percentage);
    Drawable setColor(double orange, double skyblue, double percentage);
    void setPercentage();
}
