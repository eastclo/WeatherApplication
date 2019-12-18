package com.example.weatherapplication;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class PercentViewJeonbuk extends RelativeLayout implements PercentView {
    TextView Jeonju, Iksan, Gunsan, Jeongeup, Gimje, Namwon, Wanju, Gochang, Buan, Sunchang, Imsil, Muju, Jinan, Jangsu;
    String[] subLocality = {"고창군", "군산시", "김제시", "남원시", "무주군", "부안군", "순창군", "완주군", "익산시", "임실군",
            "장수군", "전주시", "정읍시", "진안군"};

    public PercentViewJeonbuk(Context context, View view) {
        super(context);
        this.Jeonju = view.findViewById(R.id.percent_Jeonbuk_Jeonju);
        this.Iksan = view.findViewById(R.id.percent_Jeonbuk_Iksan);
        this.Gunsan = view.findViewById(R.id.percent_Jeonbuk_Gunsan);
        this.Jeongeup = view.findViewById(R.id.percent_Jeonbuk_Jeongeup);
        this.Gimje = view.findViewById(R.id.percent_Jeonbuk_Gimje);
        this.Namwon = view.findViewById(R.id.percent_Jeonbuk_Namwon);
        this.Wanju = view.findViewById(R.id.percent_Jeonbuk_Wanju);
        this.Gochang = view.findViewById(R.id.percent_Jeonbuk_Gochang);
        this.Buan = view.findViewById(R.id.percent_Jeonbuk_Buan);
        this.Sunchang = view.findViewById(R.id.percent_Jeonbuk_Sunchang);
        this.Imsil = view.findViewById(R.id.percent_Jeonbuk_Imsil);
        this.Muju = view.findViewById(R.id.percent_Jeonbuk_Muju);
        this.Jinan = view.findViewById(R.id.percent_Jeonbuk_Jinan);
        this.Jangsu = view.findViewById(R.id.percent_Jeonbuk_Jangsu);
    }

    // 투표 결과 ratio별 각 수치별 색깔 (비가 오고 있다 답변한 비율)
    // 5 % 이하 -> 비가 오지 않는다              // 주황
    // 40% 이하 -> 비가 오는지 안오는지 모르겠다 // 하늘
    // 40% 이상 -> 비가 확실히 온다              // 파랑
    @Override
    public void setBackground(TextView view, double ratio) {
        Drawable drawable = null;
        drawable = setColor(0.1, 0.4, ratio);
        view.setBackground(drawable);
    }

    @Override
    public Drawable setColor(double orange, double skyblue, double ratio) {
        Drawable drawable;
        if (ratio <= orange)
            drawable = getResources().getDrawable(R.drawable.edge_radius_orange);
        else if (ratio <= skyblue)
            drawable = getResources().getDrawable(R.drawable.edge_radius_deepskyblue);
        else
            drawable = getResources().getDrawable(R.drawable.edge_radius_blue);
        return drawable;
    }

    public void setPercentage() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ownDocRef;
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[0]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Gochang, ratio);
                Gochang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[1]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Gunsan, ratio);
                Gunsan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[2]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Gimje, ratio);
                Gimje.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[3]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Namwon, ratio);
                Namwon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[4]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Muju, ratio);
                Muju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[5]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Buan, ratio);
                Buan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[6]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Sunchang, ratio);
                Sunchang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[7]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Wanju, ratio);
                Wanju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[8]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Iksan, ratio);
                Iksan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[9]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Imsil, ratio);
                Imsil.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[10]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Jangsu, ratio);
                Jangsu.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[11]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Jeonju, ratio);
                Jeonju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[12]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Jeongeup, ratio);
                Jeongeup.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("전라북도").collection(subLocality[13]).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                double yes = (double) total.getYes();
                double no = (double) total.getNo();
                double ratio = yes / (yes + no);
                int percent = (int) (ratio * 100);
                String message = Integer.toString(percent);
                message = message.concat("%");
                setBackground(Jinan, ratio);
                Jinan.setText(message);
            }
        });
    }
}
