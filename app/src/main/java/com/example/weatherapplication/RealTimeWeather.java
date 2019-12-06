package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class RealTimeWeather extends AppCompatActivity implements View.OnClickListener {
    // 버튼
    Button yesButton;
    Button noButton;
    Spinner spinner;
    FirebaseFirestore db;
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;
    //지역 정보 저장을 위한 변수 선언
    String adminArea;
    String locality;
    List<Address> addresses = null;
    TextView adminAreaView;
    TextView loocalityView;
    String uniqueID = UUID.randomUUID().toString();     // UUID 제대로 따옵시다
    boolean didVoted;
    boolean wasRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_weather);

        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        db = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.location_select);
        adminAreaView = findViewById(R.id.admin_area);
        loocalityView = findViewById(R.id.sub_locality);

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        /*GPS 서비스 시작*/
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double latitude = intent.getDoubleExtra(GpsService.EXTRA_LATITUDE, 0);
                double longitude = intent.getDoubleExtra(GpsService.EXTRA_LONGITUDE, 0);
                Geocoder coder = new Geocoder(getApplicationContext(), Locale.KOREA);
                try {
                    addresses = coder.getFromLocation(latitude, longitude, 3); // 첫번째 파라미터로 위도, 두번째로 경도, 세번째 파라미터로 리턴할 Address객체의 개수
                    adminArea = addresses.get(0).getAdminArea();
                    if (adminArea.charAt(adminArea.length()-1) == '시')
                        locality = addresses.get(0).getSubLocality();
                    else
                        locality = addresses.get(0).getLocality();

                    adminAreaView.setText(adminArea);
                    loocalityView.setText((locality));

                    //setSpinnerSelection();   //지역 정보에 따라 spinner default 값 설정을 위한 메소드 호출
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(GpsService.ACTION_LOCATION_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this,GpsService.class));
    }

    @Override
    public void onClick(View view) {
        boolean myAnswer;
        if (view == yesButton)
            myAnswer = true;
        else
            myAnswer = false;
        applyVoteResult(myAnswer);
    }

    public void applyVoteResult(final boolean myAnswer) {
        // 개인 투표 기록 검사
        DocumentReference ownDocRef = db.collection("RealTimeWeather").document(adminArea).collection(locality).document(uniqueID);
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                didVoted = true;
                wasRight = true;
                myAnswer answer = documentSnapshot.toObject(myAnswer.class);
                if (answer == null) {   // 해당 유저가 해당 지역에 투표 하지 않은 경우
                    answer = new myAnswer(myAnswer);
                    db.collection("RealTimeWeather").document(adminArea).collection(locality).document(uniqueID).set(answer);
                    didVoted = false;
                } else {                   // 해당 유저가 해당 지역에 투표를 했던 경우
                    if (answer.getAnswer() != myAnswer) {   // answer != myAnswer  =>  해당 유저가 이번에 투표한 내용이 이전 투표 내용과 달라졌을 경우
                        answer.setAnswer(myAnswer);
                        db.collection("RealTimeWeather").document(adminArea).collection(locality).document(uniqueID).set(answer);
                        wasRight = false;
                    }
                }
            }
        });
        //  해당 지역 투표 총량 조정
        DocumentReference totalDocRef = db.collection("RealTimeWeather").document(adminArea).collection(locality).document("total");
        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote total = documentSnapshot.toObject(RealTimeVote.class);
                if (!didVoted) {   // 투표를 하지 않았던 경우, 이번 투표 내용을 +1
                    if (myAnswer == true)
                        total.setYes(total.getYes() + 1);
                    else // myAnswer == false
                        total.setNo(total.getNo() + 1);
                } else {  // 투표를 했던 경우
                    if (!wasRight) {  // 투표 기록이 바뀐 경우
                        if (myAnswer == true) {
                            total.setYes(total.getYes() + 1);
                            total.setNo(total.getNo() - 1);
                        } else { // myAnswer == false
                            total.setNo(total.getNo() + 1);
                            total.setYes(total.getYes() - 1);
                        }
                    }
                }
                db.collection("RealTimeWeather").document(adminArea).collection(locality).document("total").set(total);
            }
        });
    }
}