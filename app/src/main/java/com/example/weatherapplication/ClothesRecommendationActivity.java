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
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ClothesRecommendationActivity extends AppCompatActivity {

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

    Button voteBtn;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            gpsService = new GpsService();
            gpsService = ((GpsService.LocalBinder) iBinder).getCountService();
            System.out.println(gpsService.getLocation().getLatitude()+" "+gpsService.getLocation().getLongitude());
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
        adminAreaView = (TextView)findViewById(R.id.admin_area);
        subLoocalityView = (TextView)findViewById(R.id.sub_locality);

        voteBtn = (Button)findViewById(R.id.vote_button);
        voteBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // 1) 터치 다운 위치의 Y 위치를 기억해 둔다.
                        initialX = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        pointCurX = motionEvent.getX();
                        //터치 다운 X 위치에서 300픽셀을 초과 이동되면 애니매이션 실행
                        if (initialX - pointCurX > 300) {
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            finish();
                        }
                }
                return false;
            }
        });

        //Gps서비스 시작
        Intent intent = new Intent(getApplicationContext(), GpsService.class);
        startService(intent);

        /*Bound서비스 호출*/
        Intent bIntent = new Intent();
        ComponentName componentName = new ComponentName("com.example.weatherapplication","com.example.weatherapplication.GpsService");
        bIntent.setComponent(componentName);
        bindService(bIntent,mConnection,0);


        //지역 정보 출력을 위한 로컬브로드캐스트 호출
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                double latitude = intent.getDoubleExtra(GpsService.EXTRA_LATITUDE,0);
                double longitude = intent.getDoubleExtra(GpsService.EXTRA_LONGITUDE,0);
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
        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                // 1) 터치 다운 위치의 Y 위치를 기억해 둔다.
                initialX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                pointCurX = event.getX();
                //터치 다운 X 위치에서 300픽셀을 초과 이동되면 애니매이션 실행
                if(initialX-pointCurX > 300){
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
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
