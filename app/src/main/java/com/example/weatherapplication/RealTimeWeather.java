package com.example.weatherapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;


public class RealTimeWeather extends AppCompatActivity implements View.OnClickListener {
    Button yesButton;
    Button noButton;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_weather);

        yesButton = findViewById(R.id.yesButton);
        noButton = findViewById(R.id.noButton);
        yesButton.setOnClickListener(this);
        noButton.setOnClickListener(this);

        db = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View view) {
        String state = "stateTest";      // 지역 얻어오자
        String city = "cityTest";

//        DocumentReference docRef = db.collection(state).document(city);     // 객체 추출법 추가 요망
//        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//            @Override
//            public void onSuccess(DocumentSnapshot documentSnapshot) {
//                RealTimeVote current = documentSnapshot.toObject(RealTimeVote.class);
//                cuurent.setYes(documentSnapshot.getString("yes"));
//                cuurent.setNo(documentSnapshot.getString("no"));
//            }
//        });

        int yes = 111;    // db에서 해당 지역의  true 추출
        int no = 222;   // db에서 해당 지역의 false 추출

        if (view == yesButton)
            yes++;
        else    // noButton
            no++;

        RealTimeVote vote = new RealTimeVote(yes, no);  // 추출한 data 가공후 첨가

        db.collection("WeatherApplication").document("RealtimeWeather").collection(state)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("sibal", document.getId() + " => " + document.getData());
                            }
                        } else {
                            Log.d("sibal", "Error getting documents.", task.getException());
                        }
                    }
                });

        db.collection("WeatherApplication").document("RealtimeWeather").collection(state).document(city).set(vote);
    }
}
