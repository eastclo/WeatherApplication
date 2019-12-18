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

public class PercentViewSeoul extends RelativeLayout implements PercentView {
    TextView Dobong, Dongdaemun, Dongjak, Eunpyeong, Gangbuk, Gangdong, Gangnam, Gangseo, Geumcheon, Guro, Gwanak, Gwangjin,
            Jongno, Jung, Jungnang, Mapo, Nowon, Seocho, Seodaemun, Seongbuk, Seongdong, Songpa, Yangcheon, Yeongdeungpo, Yongsan;
    String[] subLocality = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구",
            "도봉구", "동대문구", "동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구",
            "영등포구", "용산구", "은평구", "종로구", "중구", "중랑구"};

    public PercentViewSeoul(Context context, View view) {
        super(context);
        this.Songpa = view.findViewById(R.id.percent_Seoul_Songpa);
        this.Dobong = view.findViewById(R.id.percent_Seoul_Dobong);
        this.Dongdaemun = view.findViewById(R.id.percent_Seoul_Dongdaemun);
        this.Dongjak = view.findViewById(R.id.percent_Seoul_Dongjak);
        this.Eunpyeong = view.findViewById(R.id.percent_Seoul_Eunpyeong);
        this.Gangbuk = view.findViewById(R.id.percent_Seoul_Gangbuk);
        this.Gangdong = view.findViewById(R.id.percent_Seoul_Gangdong);
        this.Gangnam = view.findViewById(R.id.percent_Seoul_Gangnam);
        this.Gangseo = view.findViewById(R.id.percent_Seoul_Gangseo);
        this.Geumcheon = view.findViewById(R.id.percent_Seoul_Geumcheon);
        this.Guro = view.findViewById(R.id.percent_Seoul_Guro);
        this.Gwanak = view.findViewById(R.id.percent_Seoul_Gwanak);
        this.Gwangjin = view.findViewById(R.id.percent_Seoul_Gwangjin);
        this.Jongno = view.findViewById(R.id.percent_Seoul_Jongno);
        this.Jung = view.findViewById(R.id.percent_Seoul_Jung);
        this.Jungnang = view.findViewById(R.id.percent_Seoul_Jungnang);
        this.Mapo = view.findViewById(R.id.percent_Seoul_Mapo);
        this.Nowon = view.findViewById(R.id.percent_Seoul_Nowon);
        this.Seocho = view.findViewById(R.id.percent_Seoul_Seocho);
        this.Seodaemun = view.findViewById(R.id.percent_Seoul_Seodaemun);
        this.Seongbuk = view.findViewById(R.id.percent_Seoul_Seongbuk);
        this.Seongdong = view.findViewById(R.id.percent_Seoul_Seongdong);
        this.Songpa = view.findViewById(R.id.percent_Seoul_Songpa);
        this.Yangcheon = view.findViewById(R.id.percent_Seoul_Yangcheon);
        this.Yeongdeungpo = view.findViewById(R.id.percent_Seoul_Yeongdeungpo);
        this.Yongsan = view.findViewById(R.id.percent_Seoul_Yongsan);
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
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[0]).document("total");
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
                setBackground(Gangnam, ratio);
                Gangnam.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[1]).document("total");
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
                setBackground(Gangdong, ratio);
                Gangdong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[2]).document("total");
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
                setBackground(Gangbuk, ratio);
                Gangbuk.setText(message);
            }
        });
        //
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[3]).document("total");
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
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[4]).document("total");
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
                setBackground(Gwanak, ratio);
                Gwanak.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[5]).document("total");
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
                setBackground(Gwangjin, ratio);
                Gwangjin.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[6]).document("total");
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
                setBackground(Guro, ratio);
                Guro.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[7]).document("total");
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
                setBackground(Geumcheon, ratio);
                Geumcheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[8]).document("total");
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
                setBackground(Nowon, ratio);
                Nowon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[9]).document("total");
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
                setBackground(Dobong, ratio);
                Dobong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[10]).document("total");
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
                setBackground(Dongdaemun, ratio);
                Dongdaemun.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[11]).document("total");
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
                setBackground(Dongjak, ratio);
                Dongjak.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[12]).document("total");
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
                setBackground(Mapo, ratio);
                Mapo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[13]).document("total");
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
                setBackground(Seodaemun, ratio);
                Seodaemun.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[14]).document("total");
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
                setBackground(Seocho, ratio);
                Seocho.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[15]).document("total");
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
                setBackground(Seongdong, ratio);
                Seongdong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[16]).document("total");
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
                setBackground(Seongbuk, ratio);
                Seongbuk.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[17]).document("total");
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
                setBackground(Songpa, ratio);
                Songpa.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[18]).document("total");
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
                setBackground(Yangcheon, ratio);
                Yangcheon.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[19]).document("total");
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
                setBackground(Yeongdeungpo, ratio);
                Yeongdeungpo.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[20]).document("total");
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
                setBackground(Yongsan, ratio);
                Yongsan.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[21]).document("total");
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
                setBackground(Eunpyeong, ratio);
                Eunpyeong.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[22]).document("total");
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
                setBackground(Jongno, ratio);
                Jongno.setText(message);
            }
        });
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[23]).document("total");
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
        ownDocRef = db.collection("RealTimeWeather").document("서울특별시").collection(subLocality[24]).document("total");
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
                setBackground(Jungnang, ratio);
                Jungnang.setText(message);
            }
        });
    }
}
