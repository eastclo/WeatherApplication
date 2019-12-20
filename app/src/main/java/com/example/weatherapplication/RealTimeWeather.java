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
import android.os.IBinder;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class RealTimeWeather extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {
    //터치 이벤트 x좌표 기록
    float pointCurX = 0;
    float initialX = 0;
    // 뷰
    Button yesButton;
    Button noButton;
    TextView questionText;
    FirebaseFirestore db;   // 파이어스토어
    // For GPS
    LocalBroadcastManager mLocalBroadcastManager;
    BroadcastReceiver mReceiver;
    GpsService gpsService = null;
    //지역 정보 저장을 위한 변수 선언
    String adminArea;
    String locality;
    List<Address> addresses = null;
    // GPS에 따른 지역이름 화면 표시
    TextView adminAreaView;
    TextView loocalityView;
    String uniqueID = UUID.randomUUID().toString();     // UUID 제대로 따옵시다
    boolean didVoted;
    boolean wasRight;
    TextView myRegionTotalVote;
    String myRegionTotalVoteMessage;
    // FOR 지도
    Spinner spinner;
    int position = 1;
    RelativeLayout map;
    View percentViewBusan, percentViewChungbuk, percentViewChungnam, percentViewDaegu, percentViewDaejeon, percentViewGangwon,
            percentViewGwangju, percentViewGyeongbuk, percentViewGyeonggi, percentViewGyeongnam, percentViewIncheon,
            percentViewJeju, percentViewJeonbuk, percentViewJeonnam, percentViewKorea, percentViewSeoul, percentViewUlsan;
    View currPercentView;
    PercentView percentViewSetter;
    final String[] cityList = {"전체", "강원도", "경기도", "경상남도", "경상북도", "광주광역시", "대구광역시",
            "대전광역시", "부산광역시", "서울특별시", "울산광역시", "인천광역시", "전라남도", "전라북도",
            "제주특별자치도", "충청남도", "충청북도"};

    ImageView settingBtn;

    private ServiceConnection mConnection = new ServiceConnection(){
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            gpsService = new GpsService();
            gpsService=((GpsService.LocalBinder)iBinder).getCountService();
            System.out.println(gpsService.getLocation().getLatitude()+" "+gpsService.getLocation().getLongitude());
            Geocoder coder = new Geocoder(getApplicationContext(), Locale.KOREA);
            try {
                addresses = coder.getFromLocation(gpsService.getLocation().getLatitude(), gpsService.getLocation().getLongitude(), 3); // 첫번째 파라미터로 위도, 두번째로 경도, 세번째 파라미터로 리턴할 Address객체의 개수
                adminArea = addresses.get(0).getAdminArea();
                if (adminArea.charAt(adminArea.length() - 1) == '시')
                    locality = addresses.get(0).getSubLocality();
                else
                    locality = addresses.get(0).getLocality();

                adminAreaView.setText(adminArea);
                loocalityView.setText((locality));
                setMyRegionTotalVote();
                setSpinnerSelection();   //지역 정보에 따라 spinner default 값 설정을 위한 메소드 호출
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_weather);

        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        questionText = findViewById(R.id.question);
        db = FirebaseFirestore.getInstance();
        spinner = findViewById(R.id.location_select);
        adminAreaView = findViewById(R.id.admin_area);
        loocalityView = findViewById(R.id.sub_locality);
        myRegionTotalVote = findViewById(R.id.myRegionTotalVote);
        settingBtn = findViewById(R.id.setting);
        settingBtn.setOnTouchListener(new View.OnTouchListener(){
            Intent intent;
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                initialX = motionEvent.getRawX();
                pointCurX = motionEvent.getRawX();
                System.out.println(motionEvent.getAction());
                if(motionEvent.getAction()==MotionEvent.ACTION_UP||motionEvent.getAction()==MotionEvent.ACTION_CANCEL) {
                    intent = new Intent(getApplicationContext(), SettingsActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                return false;
            }
        });

        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);



        /*Bound서비스 호출*/
        Intent bIntent = new Intent();
        ComponentName componentName = new ComponentName("com.example.weatherapplication","com.example.weatherapplication.GpsService");
        bIntent.setComponent(componentName);
        bindService(bIntent,mConnection,0);

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
                    if (adminArea.charAt(adminArea.length() - 1) == '시')
                        locality = addresses.get(0).getSubLocality();
                    else
                        locality = addresses.get(0).getLocality();

                    adminAreaView.setText(adminArea);
                    loocalityView.setText((locality));
                    setMyRegionTotalVote();
                    setSpinnerSelection();   //지역 정보에 따라 spinner default 값 설정을 위한 메소드 호출
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        //데이터 출력, Spinner 리스너에서 visibility 설정
        map = (RelativeLayout) findViewById(R.id.tab_content);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        percentViewKorea = inflater.inflate(R.layout.percent_view_korea, null);
//        map.addView(percentViewKorea);
        percentViewSeoul = inflater.inflate(R.layout.percent_view_seoul, null);
        map.addView(percentViewSeoul);
        percentViewBusan = inflater.inflate(R.layout.percent_view_busan, null);
        map.addView(percentViewBusan);
        percentViewChungbuk = inflater.inflate(R.layout.percent_view_chungbuk, null);
        map.addView(percentViewChungbuk);
        percentViewChungnam = inflater.inflate(R.layout.percent_view_chungnam, null);
        map.addView(percentViewChungnam);
        percentViewDaegu = inflater.inflate(R.layout.percent_view_daegu, null);
        map.addView(percentViewDaegu);
        percentViewDaejeon = inflater.inflate(R.layout.percent_view_daejeon, null);
        map.addView(percentViewDaejeon);
        percentViewGangwon = inflater.inflate(R.layout.percent_view_gangwon, null);
        map.addView(percentViewGangwon);
        percentViewGwangju = inflater.inflate(R.layout.percent_view_gwangju, null);
        map.addView(percentViewGwangju);
        percentViewGyeongbuk = inflater.inflate(R.layout.percent_view_gyeongbuk, null);
        map.addView(percentViewGyeongbuk);
        percentViewGyeonggi = inflater.inflate(R.layout.percent_view_gyeonggi, null);
        map.addView(percentViewGyeonggi);
        percentViewGyeongnam = inflater.inflate(R.layout.percent_view_gyeongnam, null);
        map.addView(percentViewGyeongnam);
        percentViewIncheon = inflater.inflate(R.layout.percent_view_incheon, null);
        map.addView(percentViewIncheon);
        percentViewJeju = inflater.inflate(R.layout.percent_view_jeju, null);
        map.addView(percentViewJeju);
        percentViewJeonbuk = inflater.inflate(R.layout.percent_view_jeonbuk, null);
        map.addView(percentViewJeonbuk);
        percentViewJeonnam = inflater.inflate(R.layout.percent_view_jeonnam, null);
        map.addView(percentViewJeonnam);
        percentViewUlsan = inflater.inflate(R.layout.percent_view_ulsan, null);
        map.addView(percentViewUlsan);
        currPercentView = percentViewSeoul;

        spinner = (Spinner) findViewById(R.id.location_select);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cityName, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


    }

    protected void onResume() {
        super.onResume();
        mLocalBroadcastManager.registerReceiver(mReceiver, new IntentFilter(GpsService.ACTION_LOCATION_BROADCAST));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }

    @Override
    public void onClick(View view) {
        boolean myAnswer;
        if (view == yesButton) {
            myAnswer = true;
            questionText.setText("비가 오고 있군요!");
            questionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        }
        else {
            myAnswer = false;
            questionText.setText("비가 안오고 있군요!");
            questionText.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        }
        applyVoteResult(myAnswer);
    }

    public void applyVoteResult(final boolean myAnswer) {
        // 개인 투표 기록 검사
        didVoted = true;
        wasRight = true;
        DocumentReference ownDocRef = db.collection("RealTimeWeather").document(adminArea).collection(locality).document(uniqueID);
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
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
                setMyRegionTotalVote();
            }
        });
    }

    public void setMyRegionTotalVote() {
        DocumentReference ownDocRef = db.collection("RealTimeWeather").document(adminArea).collection(locality).document("total");
        ownDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                RealTimeVote totalAnswer = documentSnapshot.toObject(RealTimeVote.class);
                int yes = totalAnswer.getYes();
                int total = yes + totalAnswer.getNo();
                myRegionTotalVoteMessage = "";
                myRegionTotalVoteMessage = myRegionTotalVoteMessage.concat(Integer.toString(total));
                myRegionTotalVoteMessage = myRegionTotalVoteMessage.concat("명 중,");
                myRegionTotalVoteMessage = myRegionTotalVoteMessage.concat(Integer.toString(yes));
                myRegionTotalVoteMessage = myRegionTotalVoteMessage.concat("명이 비가 온다 답변하였습니다.");
                myRegionTotalVote.setText(myRegionTotalVoteMessage);
            }
        });
    }

    private void setSpinnerSelection() {
        int cnt = 0;
        for (String tmp : cityList) {
            if (adminArea.equals(tmp)) {
                spinner.setSelection(cnt);
                position = cnt;
                break;
            }
            cnt++;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        this.position = position;
        ImageView img = findViewById(R.id.map);
        switch (position) {
//            case 0:
//                currPercentView.setVisibility(View.GONE);
//                img.setImageResource(R.drawable.map_korea);
//                percentViewKorea.setVisibility(View.VISIBLE);
//                currPercentView = percentViewKorea;
//                break;
            case 1:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gangwon);
                percentViewGangwon.setVisibility(View.VISIBLE);
                currPercentView = percentViewGangwon;
                percentViewSetter = new PercentViewGangWon(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 2:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeonggido);
                percentViewGyeonggi.setVisibility(View.VISIBLE);
                currPercentView = percentViewGyeonggi;
                percentViewSetter = new PercentViewGyeonggi(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 3:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeongsangnamdo);
                percentViewGyeongnam.setVisibility(View.VISIBLE);
                currPercentView = percentViewGyeongnam;
                percentViewSetter = new PercentViewGyeongnam(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 4:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gyeongsangbukdo);
                percentViewGyeongbuk.setVisibility(View.VISIBLE);
                currPercentView = percentViewGyeongbuk;
                percentViewSetter = new PercentViewGyeongbuk(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 5:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_gwangju);
                percentViewGwangju.setVisibility(View.VISIBLE);
                currPercentView = percentViewGwangju;
                percentViewSetter = new PercentViewGwangju(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 6:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_daegu);
                percentViewDaegu.setVisibility(View.VISIBLE);
                currPercentView = percentViewDaegu;
                percentViewSetter = new PercentViewDaegu(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 7:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_daejeon);
                percentViewDaejeon.setVisibility(View.VISIBLE);
                currPercentView = percentViewDaejeon;
                percentViewSetter = new PercentViewDaejeon(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 8:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_busan);
                percentViewBusan.setVisibility(View.VISIBLE);
                currPercentView = percentViewBusan;
                percentViewSetter = new PercentViewBusan(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 9:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_seoul);
                percentViewSeoul.setVisibility(View.VISIBLE);
                currPercentView = percentViewSeoul;
                percentViewSetter = new PercentViewSeoul(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 10:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_ulsan);
                percentViewUlsan.setVisibility(View.VISIBLE);
                currPercentView = percentViewUlsan;
                percentViewSetter = new PercentViewUlsan(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 11:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_incheon);
                percentViewIncheon.setVisibility(View.VISIBLE);
                currPercentView = percentViewIncheon;
                percentViewSetter = new PercentViewIncheon(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 12:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeollanamdo);
                percentViewJeonnam.setVisibility(View.VISIBLE);
                currPercentView = percentViewJeonnam;
                percentViewSetter = new PercentViewJeonnam(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 13:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeollabukdo);
                percentViewJeonbuk.setVisibility(View.VISIBLE);
                currPercentView = percentViewJeonbuk;
                percentViewSetter = new PercentViewJeonbuk(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 14:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_jeju);
                percentViewJeju.setVisibility(View.VISIBLE);
                currPercentView = percentViewJeju;
                percentViewSetter = new PercentViewJeju(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 15:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_chungcheongnamdo);
                percentViewChungnam.setVisibility(View.VISIBLE);
                currPercentView = percentViewChungnam;
                percentViewSetter = new PercentViewChungnam(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
            case 16:
                currPercentView.setVisibility(View.GONE);
                img.setImageResource(R.drawable.map_chungcheongbukdo);
                percentViewChungbuk.setVisibility(View.VISIBLE);
                currPercentView = percentViewChungbuk;
                percentViewSetter = new PercentViewChungbuk(getApplicationContext(), currPercentView);
                percentViewSetter.setPercentage();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

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
                if (initialX - pointCurX > -300) {
                    startActivity(new Intent(this, MainActivity.class));
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                    finish();
                }
        }
        return true;
    }
}