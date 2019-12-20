package com.example.weatherapplication;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class ClothesRecommendationActivity extends AppCompatActivity implements View.OnClickListener {

    GpsService gpsService = null;

    //터치 이벤트 x좌표 기록
    float pointCurX = 0;
    float initialX = 0;

    //지역 정보 저장을 위한 변수 선언
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;
    String adminArea;
    String subLocality;
    List<Address> addresses = null;
    TextView adminAreaView;
    TextView subLoocalityView;

    String uniqueID = UUID.randomUUID().toString();
    Toast toast;
    String sex = "";
    int cloth = 0;
    int feeling = 0;
    boolean wasRight = true;
    int[] myPastVote = {0, 0};   // index 0 : 옷, index 1 : 체감
    // 투표를 위한 버튼
    Button voteBtn;
    RadioButton male;
    RadioButton female;
    RadioButton cloth1;
    RadioButton cloth2;
    RadioButton feeling1;
    RadioButton feeling2;
    RadioButton feeling3;
    // 파이어 베이스 데이터 송수신을 위한 변수
    FirebaseFirestore db;
    // 그래프 바를 그리기 위한 객체
    TotalClothRecommandVote maleCloth1;
    TotalClothRecommandVote maleCloth2;
    TotalClothRecommandVote femaleCloth1;
    TotalClothRecommandVote femaleCloth2;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            gpsService = new GpsService();
            gpsService = ((GpsService.LocalBinder) iBinder).getCountService();
            System.out.println(gpsService.getLocation().getLatitude() + " " + gpsService.getLocation().getLongitude());
            //gpsService에서 getlocation()호출해서 getlatitude와 getlongitude를 통해 얻어서 사용하면됨
            //호출 시간이 기므로, GPS의 값이 있어야 구현이 되는 View는 이곳에 구현
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clothes_recommendation);
        adminAreaView = (TextView) findViewById(R.id.admin_area);
        subLoocalityView = (TextView) findViewById(R.id.sub_locality);
        db = FirebaseFirestore.getInstance();

        voteBtn = (Button) findViewById(R.id.vote_button);
        voteBtn.setOnClickListener(this);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        cloth1 = findViewById(R.id.cloth1);
        cloth2 = findViewById(R.id.cloth2);
        feeling1 = findViewById(R.id.feeling1);
        feeling2 = findViewById(R.id.feeling2);
        feeling3 = findViewById(R.id.feeling3);

        //Gps서비스 시작
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);

        /*Bound서비스 호출*/
        Intent bIntent = new Intent();
        ComponentName componentName = new ComponentName("com.example.weatherapplication", "com.example.weatherapplication.GpsService");
        bIntent.setComponent(componentName);
        bindService(bIntent, mConnection, 0);


        //지역 정보 출력을 위한 로컬브로드캐스트 호출
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
                    subLocality = addresses.get(0).getSubLocality();

                    adminAreaView.setText(adminArea);
                    subLoocalityView.setText((subLocality));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1) 터치 다운 위치의 Y 위치를 기억해 둔다.
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                pointCurX = event.getX();
                //터치 다운 X 위치에서 300픽셀을 초과 이동되면 애니매이션 실행
                if (initialX - pointCurX > 300) {
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    finish();
                }
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(GpsService.ACTION_LOCATION_BROADCAST));
    }

    @Override
    public void onClick(View view) {
        if (male.isChecked() || female.isChecked()) {
            if (male.isChecked()) {
                sex = "male";
            } else {
                sex = "female";
            }
        }
        if (cloth1.isChecked() || cloth2.isChecked()) {
            if (cloth1.isChecked()) {
                cloth = 1;
            } else {
                cloth = 2;
            }
            // 옷 선택
        }
        if (feeling1.isChecked() || feeling2.isChecked() || feeling3.isChecked()) {
            if (feeling1.isChecked()) {
                feeling = 1;
            } else if (feeling2.isChecked()) {
                feeling = 2;
            } else {
                feeling = 3;
            }
        }
        if (view == voteBtn) {
            if (sex.equals("") || cloth == 0 || feeling == 0) {
                toast.makeText(getApplicationContext(), "투표가 완성되지 않았습니다.", Toast.LENGTH_LONG).show();
            } else {
                applyVoteResult();
            }
        }
    }

    public void applyVoteResult() {
        // 개인 투표 기록 검사
        DocumentReference ownDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document(uniqueID);
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                MyClothRecommandVote answer = documentSnapshot.toObject(MyClothRecommandVote.class);
                if (answer == null) {   // 해당 유저가 해당 지역에 투표 하지 않은 경우
                    answer = new MyClothRecommandVote(cloth, feeling);
                    db.collection("ClothRecommand").document(adminArea).collection(sex).document(uniqueID).set(answer);
                    DocumentReference totalDocRef;
                    if (cloth == 1) {
                        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1");
                        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                if (feeling == 1) {
                                    total.setFeeling1(total.getFeeling1() + 1);
                                } else if (feeling == 2) {
                                    total.setFeeling2(total.getFeeling2() + 1);
                                } else {    // feeling == 3
                                    total.setFeeling3(total.getFeeling3() + 1);
                                }
                                db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1").set(total);
                            }
                        });
                    } else {    // cloth == 2
                        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2");
                        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                if (feeling == 1) {
                                    total.setFeeling1(total.getFeeling1() + 1);
                                } else if (feeling == 2) {
                                    total.setFeeling2(total.getFeeling2() + 1);
                                } else {    // feeling == 3
                                    total.setFeeling3(total.getFeeling3() + 1);
                                }
                                db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2").set(total);
                            }
                        });
                    }
                } else {                   // 해당 유저가 해당 지역에 투표를 했던 경우
                    if (answer.getCloth() != cloth || answer.getfeeling() != feeling) {   // answer != myAnswer  =>  해당 유저가 이번에 투표한 내용이 이전 투표 내용과 달라졌을 경우
                        Log.d("asd", "투표 o -> 값 변경");
                        wasRight = false;
                        myPastVote[0] = answer.getCloth();
                        answer.setCloth(cloth);
                        myPastVote[1] = answer.getfeeling();
                        answer.setfeeling(feeling);
                        db.collection("ClothRecommand").document(adminArea).collection(sex).document(uniqueID).set(answer);
                        DocumentReference totalDocRef;
                        if (!wasRight) {
                            if (myPastVote[0] != cloth) { // 옷이 달라진 경우
                                if (cloth == 1) {     // 현재 cloth 1
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (feeling == 1) {
                                                total.setFeeling1(total.getFeeling1() + 1);
                                            } else if (feeling == 2) {
                                                total.setFeeling2(total.getFeeling2() + 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() + 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1").set(total);
                                        }
                                    });
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (myPastVote[1] == 1) {
                                                total.setFeeling1(total.getFeeling1() - 1);
                                            } else if (myPastVote[1] == 2) {
                                                total.setFeeling2(total.getFeeling2() - 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() - 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2").set(total);
                                        }
                                    });
                                } else if (cloth == 2) {
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (feeling == 1) {
                                                total.setFeeling1(total.getFeeling1() + 1);
                                            } else if (feeling == 2) {
                                                total.setFeeling2(total.getFeeling2() + 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() + 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2").set(total);
                                        }
                                    });
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (myPastVote[1] == 1) {
                                                total.setFeeling1(total.getFeeling1() - 1);
                                            } else if (myPastVote[1] == 2) {
                                                total.setFeeling2(total.getFeeling2() - 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() - 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1").set(total);
                                        }
                                    });
                                }
                            } else if (myPastVote[1] != feeling) {   // 체감이 달라진 경우
                                if (cloth == 1) {     // 현재 cloth 1
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (feeling == 1) {
                                                total.setFeeling1(total.getFeeling1() + 1);
                                            } else if (feeling == 2) {
                                                total.setFeeling2(total.getFeeling2() + 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() + 1);
                                            }
                                            if (myPastVote[1] == 1) {
                                                total.setFeeling1(total.getFeeling1() - 1);
                                            } else if (myPastVote[1] == 2) {
                                                total.setFeeling2(total.getFeeling2() - 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() - 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth1").set(total);
                                        }
                                    });
                                } else if (cloth == 2) {
                                    totalDocRef = db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2");
                                    totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                                            if (feeling == 1) {
                                                total.setFeeling1(total.getFeeling1() + 1);
                                            } else if (feeling == 2) {
                                                total.setFeeling2(total.getFeeling2() + 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() + 1);
                                            }
                                            if (myPastVote[1] == 1) {
                                                total.setFeeling1(total.getFeeling1() - 1);
                                            } else if (myPastVote[1] == 2) {
                                                total.setFeeling2(total.getFeeling2() - 1);
                                            } else {    // feeling == 3
                                                total.setFeeling3(total.getFeeling3() - 1);
                                            }
                                            db.collection("ClothRecommand").document(adminArea).collection(sex).document("cloth2").set(total);
                                        }
                                    });
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    public void getTotal() {
        DocumentReference totalDocRef;
        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection("male").document("cloth1");
        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                // 남자 옷 1에대한 막대
            }
        });
        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection("male").document("cloth2");
        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                // 남자 옷 2에대한 막대

            }
        });
        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection("female").document("cloth1");
        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                // 여자 옷 1에대한 막대
            }
        });
        totalDocRef = db.collection("ClothRecommand").document(adminArea).collection("female").document("cloth2");
        totalDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                TotalClothRecommandVote total = documentSnapshot.toObject(TotalClothRecommandVote.class);
                // 여자 옷 2에대한 막대
            }
        });
    }

    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
