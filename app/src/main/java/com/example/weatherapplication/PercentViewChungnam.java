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

public class PercentViewChungnam extends RelativeLayout implements PercentView {
    TextView Cheonan, Asan, Seosan, Dangjin, Gongju, Nonsan, Boryeong, Hongseong, Yesan,
            Buyeo, Seocheon, Taean, Geumsan, Cheongyang;    // 계룡
    String[] subLocality = {"공주시", "금산군", "논산시", "당진시", "보령시", "부여군", "서산시", "서천군", "아산시", "예산군",
            "천안시", "청양군", "태안군", "홍성군"};

    public PercentViewChungnam(Context context, View view) {
        super(context);
        this.Cheonan = view.findViewById(R.id.percent_Chungnam_Cheonan);
        this.Asan = view.findViewById(R.id.percent_Chungnam_Asan);
        this.Seosan = view.findViewById(R.id.percent_Chungnam_Seosan);
        this.Dangjin = view.findViewById(R.id.percent_Chungnam_Dangjin);
        this.Gongju = view.findViewById(R.id.percent_Chungnam_Gongju);
        this.Nonsan = view.findViewById(R.id.percent_Chungnam_Nonsan);
        this.Boryeong = view.findViewById(R.id.percent_Chungnam_Boryeong);
        this.Hongseong = view.findViewById(R.id.percent_Chungnam_Hongseong);
        this.Yesan = view.findViewById(R.id.percent_Chungnam_Yesan);
        this.Buyeo = view.findViewById(R.id.percent_Chungnam_Buyeo);
        this.Seocheon = view.findViewById(R.id.percent_Chungnam_Seocheon);
        this.Taean = view.findViewById(R.id.percent_Chungnam_Taean);
        this.Geumsan = view.findViewById(R.id.percent_Chungnam_Geumsan);
        this.Cheongyang = view.findViewById(R.id.percent_Chungnam_Cheongyang);
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
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[0]).document("total");
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
                setBackground(Gongju, ratio);
                Gongju.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[1]).document("total");
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
                setBackground(Geumsan, ratio);
                Geumsan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[2]).document("total");
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
                setBackground(Nonsan, ratio);
                Nonsan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[3]).document("total");
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
                setBackground(Dangjin, ratio);
                Dangjin.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[4]).document("total");
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
                setBackground(Boryeong, ratio);
                Boryeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[5]).document("total");
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
                setBackground(Buyeo, ratio);
                Buyeo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[6]).document("total");
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
                setBackground(Seosan, ratio);
                Seosan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[7]).document("total");
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
                setBackground(Seocheon, ratio);
                Seocheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[8]).document("total");
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
                setBackground(Asan, ratio);
                Asan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[9]).document("total");
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
                setBackground(Yesan, ratio);
                Yesan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[10]).document("total");
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
                setBackground(Cheonan, ratio);
                Cheonan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[11]).document("total");
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
                setBackground(Cheongyang, ratio);
                Cheongyang.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[12]).document("total");
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
                setBackground(Taean, ratio);
                Taean.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("충청남도").collection(subLocality[13]).document("total");
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
                setBackground(Hongseong, ratio);
                Hongseong.setText(message);
            }
        });
    }
}
