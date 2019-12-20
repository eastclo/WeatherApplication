package com.example.weatherapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class MainBackView extends LinearLayout {

    float initialX=0;
    float pointCurX=0;
    Context Appcontext;
    int cnt = 0;

    public MainBackView(Context context)
    {
        super(context);
    }
    public MainBackView(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
    }
    public MainBackView(Context context, @Nullable AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                return true;
            case MotionEvent.ACTION_UP:
                performClick();
                return true;
        }
        return false;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);

        if(ev.getAction()==MotionEvent.ACTION_UP)
        {
            if(pointCurX- initialX> 500){
                return true;
            }
            else if(pointCurX- initialX< -500) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN) {
            super.dispatchTouchEvent(ev);
            initialX = ev.getX();
            cnt=0;
            return true;
        }
        else if(ev.getAction()==MotionEvent.ACTION_UP){
            super.dispatchTouchEvent(ev);
            pointCurX = ev.getX();
        }
        return super.dispatchTouchEvent(ev);
    }
}
