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

public class PercentViewGyeonggi extends RelativeLayout implements PercentView {
    TextView Suwon, Seongnam, Goyang, Yongin, Bucheon, Ansan, Anyang, Namyangju, Hwaseong, Uijeongbu, Siheung,
            Pyeongtaek, Gwangmyeong, Paju, Gunpo, Gwangju, Gimpo, Icheon, Yangju, Guri, Osan, Anseong, Uiwang,
            Hanam, Pocheon, Dongducheon, Gwacheon, Yeoju, Yangpyeong, Gapyeong, Yeoncheon;
    String[] subLocality = {"가평군", "고양시", "과천시", "광명시", "광주시", "구리시", "군포시", "김포시", "남양주시", "동두천시",
            "부천시", "성남시", "수원시", "시흥시", "안산시", "안성시", "안양시", "양주시", "양평군", "여주시",
            "연천군", "오산시", "용인시", "의왕시", "의정부시", "이천시", "파주시", "평택시", "포천시", "하남시",
            "화성시"};

    public PercentViewGyeonggi(Context context, View view) {
        super(context);
        this.Suwon = view.findViewById(R.id.percent_Gyeonggi_Suwon);
        this.Seongnam = view.findViewById(R.id.percent_Gyeonggi_Seongnam);
        this.Goyang = view.findViewById(R.id.percent_Gyeonggi_Goyang);
        this.Yongin = view.findViewById(R.id.percent_Gyeonggi_Yongin);
        this.Bucheon = view.findViewById(R.id.percent_Gyeonggi_Bucheon);
        this.Ansan = view.findViewById(R.id.percent_Gyeonggi_Ansan);
        this.Anyang = view.findViewById(R.id.percent_Gyeonggi_Anyang);
        this.Namyangju = view.findViewById(R.id.percent_Gyeonggi_Namyangju);
        this.Hwaseong = view.findViewById(R.id.percent_Gyeonggi_Hwaseong);
        this.Uijeongbu = view.findViewById(R.id.percent_Gyeonggi_Uijeongbu);
        this.Siheung = view.findViewById(R.id.percent_Gyeonggi_Siheung);
        this.Pyeongtaek = view.findViewById(R.id.percent_Gyeonggi_Pyeongtaek);
        this.Gwangmyeong = view.findViewById(R.id.percent_Gyeonggi_Gwangmyeong);
        this.Paju = view.findViewById(R.id.percent_Gyeonggi_Paju);
        this.Gunpo = view.findViewById(R.id.percent_Gyeonggi_Gunpo);
        this.Gwangju = view.findViewById(R.id.percent_Gyeonggi_Gwangju);
        this.Gimpo = view.findViewById(R.id.percent_Gyeonggi_Gimpo);
        this.Icheon = view.findViewById(R.id.percent_Gyeonggi_Icheon);
        this.Yangju = view.findViewById(R.id.percent_Gyeonggi_Yangju);
        this.Guri = view.findViewById(R.id.percent_Gyeonggi_Guri);
        this.Osan = view.findViewById(R.id.percent_Gyeonggi_Osan);
        this.Anseong = view.findViewById(R.id.percent_Gyeonggi_Anseong);
        this.Uiwang = view.findViewById(R.id.percent_Gyeonggi_Uiwang);
        this.Hanam = view.findViewById(R.id.percent_Gyeonggi_Hanam);
        this.Pocheon = view.findViewById(R.id.percent_Gyeonggi_Pocheon);
        this.Dongducheon = view.findViewById(R.id.percent_Gyeonggi_Dongducheon);
        this.Gwacheon = view.findViewById(R.id.percent_Gyeonggi_Gwacheon);
        this.Yeoju = view.findViewById(R.id.percent_Gyeonggi_Yeoju);
        this.Yangpyeong = view.findViewById(R.id.percent_Gyeonggi_Yangpyeong);
        this.Gapyeong = view.findViewById(R.id.percent_Gyeonggi_Gapyeong);
        this.Yeoncheon = view.findViewById(R.id.percent_Gyeonggi_Yeoncheon);
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
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[0]).document("total");
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
                setBackground(Gapyeong, ratio);
                Gapyeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[1]).document("total");
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
                setBackground(Goyang, ratio);
                Goyang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[2]).document("total");
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
                setBackground(Gwacheon, ratio);
                Gwacheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[3]).document("total");
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
                setBackground(Gwangmyeong, ratio);
                Gwangmyeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[4]).document("total");
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
                setBackground(Gwangju, ratio);
                Gwangju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[5]).document("total");
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
                setBackground(Guri, ratio);
                Guri.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[6]).document("total");
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
                setBackground(Gunpo, ratio);
                Gunpo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[7]).document("total");
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
                setBackground(Gimpo, ratio);
                Gimpo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[8]).document("total");
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
                setBackground(Namyangju, ratio);
                Namyangju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[9]).document("total");
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
                setBackground(Dongducheon, ratio);
                Dongducheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[10]).document("total");
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
                setBackground(Bucheon, ratio);
                Bucheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[11]).document("total");
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
                setBackground(Seongnam, ratio);
                Seongnam.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[12]).document("total");
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
                setBackground(Suwon, ratio);
                Suwon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[13]).document("total");
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
                setBackground(Siheung, ratio);
                Siheung.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[14]).document("total");
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
                setBackground(Ansan, ratio);
                Ansan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[15]).document("total");
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
                setBackground(Anseong, ratio);
                Anseong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[16]).document("total");
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
                setBackground(Anyang, ratio);
                Anyang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[17]).document("total");
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
                setBackground(Yangju, ratio);
                Yangju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[18]).document("total");
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
                setBackground(Yangpyeong, ratio);
                Yangpyeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[19]).document("total");
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
                setBackground(Yeoju, ratio);
                Yeoju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[20]).document("total");
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
                setBackground(Yeoncheon, ratio);
                Yeoncheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[21]).document("total");
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
                setBackground(Osan, ratio);
                Osan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[22]).document("total");
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
                setBackground(Yongin, ratio);
                Yongin.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[23]).document("total");
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
                setBackground(Uiwang, ratio);
                Uiwang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[24]).document("total");
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
                setBackground(Uijeongbu, ratio);
                Uijeongbu.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[25]).document("total");
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
                setBackground(Icheon, ratio);
                Icheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[26]).document("total");
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
                setBackground(Paju, ratio);
                Paju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[27]).document("total");
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
                setBackground(Pyeongtaek, ratio);
                Pyeongtaek.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[28]).document("total");
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
                setBackground(Pocheon, ratio);
                Pocheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[29]).document("total");
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
                setBackground(Hanam, ratio);
                Hanam.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("경기도").collection(subLocality[30]).document("total");
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
                setBackground(Hwaseong, ratio);
                Hwaseong.setText(message);
            }
        });
    }
}
