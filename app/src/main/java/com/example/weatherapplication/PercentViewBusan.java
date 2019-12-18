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

public class PercentViewBusan extends RelativeLayout implements PercentView {
    TextView Buk, Busanjin, Dong, Dongnae, Gangseo, Geumjeong, Haeundae,
            Jung, Nam, Saha, Sasang, Seo, Suyeong, Yeongdo, Yeonje, Gijang;
    String[] subLocality = {"강서구", "금정구", "기장군", "남구", "동구", "동래구", "부산진구", "북구", "사상구", "사하구",
            "서구", "수영구", "연제구", "영도구", "중구", "해운대구"};

    public PercentViewBusan(Context context, View view) {
        super(context);
        this.Buk = view.findViewById(R.id.percent_Busan_Buk);
        this.Busanjin = view.findViewById(R.id.percent_Busan_Busanjin);
        this.Dong = view.findViewById(R.id.percent_Busan_Dong);
        this.Dongnae = view.findViewById(R.id.percent_Busan_Dongnae);
        this.Gangseo = view.findViewById(R.id.percent_Busan_Gangseo);
        this.Geumjeong = view.findViewById(R.id.percent_Busan_Geumjeong);
        this.Haeundae = view.findViewById(R.id.percent_Busan_Haeundae);
        this.Jung = view.findViewById(R.id.percent_Busan_Jung);
        this.Nam = view.findViewById(R.id.percent_Busan_Nam);
        this.Saha = view.findViewById(R.id.percent_Busan_Saha);
        this.Sasang = view.findViewById(R.id.percent_Busan_Sasang);
        this.Seo = view.findViewById(R.id.percent_Busan_Seo);
        this.Suyeong = view.findViewById(R.id.percent_Busan_Suyeong);
        this.Yeongdo = view.findViewById(R.id.percent_Busan_Yeongdo);
        this.Yeonje = view.findViewById(R.id.percent_Busan_Yeonje);
        this.Gijang = view.findViewById(R.id.percent_Busan_Gijang);
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

    @Override
    public void setPercentage() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference ownDocRef;
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[0]).document("total");
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
                setBackground(Gangseo, ratio);
                Gangseo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[1]).document("total");
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
                setBackground(Geumjeong, ratio);
                Geumjeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[2]).document("total");
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
                setBackground(Gijang, ratio);
                Gijang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[3]).document("total");
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
                setBackground(Nam, ratio);
                Nam.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[4]).document("total");
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
                setBackground(Dong, ratio);
                Dong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[5]).document("total");
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
                setBackground(Dongnae, ratio);
                Dongnae.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[6]).document("total");
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
                setBackground(Busanjin, ratio);
                Busanjin.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[7]).document("total");
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
                setBackground(Buk, ratio);
                Buk.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[8]).document("total");
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
                setBackground(Sasang, ratio);
                Sasang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[9]).document("total");
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
                setBackground(Saha, ratio);
                Saha.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[10]).document("total");
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
                setBackground(Seo, ratio);
                Seo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[11]).document("total");
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
                setBackground(Suyeong, ratio);
                Suyeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[12]).document("total");
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
                setBackground(Yeonje, ratio);
                Yeonje.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[13]).document("total");
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
                setBackground(Yeongdo, ratio);
                Yeongdo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[14]).document("total");
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
                setBackground(Jung, ratio);
                Jung.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("부산광역시").collection(subLocality[15]).document("total");
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
                setBackground(Haeundae, ratio);
                Haeundae.setText(message);
            }
        });
    }
}
