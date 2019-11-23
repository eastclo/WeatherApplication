package com.example.weatherapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    GpsUsing gps;
    public LatXLonY gridgps;

    private TextView Lat;
    private TextView Lon;

    private TextView text1,text2,text3,text4,text5;
    private Button btn;
    private double latitude;
    double longtitude;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.button);
        Lat = (TextView)findViewById(R.id.textView);
        Lon = (TextView)findViewById(R.id.textView2);

        text1 = (TextView)findViewById(R.id.textView3);
        text2 = (TextView)findViewById(R.id.textView4);
        text3 = (TextView)findViewById(R.id.textView5);
        text4 = (TextView)findViewById(R.id.textView6);

        if(!isPermission) {
            callPermission();
        }

        gps=new GpsUsing(MainActivity.this);
        gps.getLocation();
        if(gps.isGetLocation()){

            latitude= gps.getLatitude();
            longtitude= gps.getLongtitude();

            gridgps=new LatXLonY();

            gridgps = gridgps.convertGrid(latitude,longtitude);


            Lat.setText(String.valueOf(gridgps.x));
            Lon.setText(String.valueOf(gridgps.y));

        } else{
            gps.showSettingsAlerts();
        }

        NetworkTask networkTask = new NetworkTask(null);
        networkTask.execute();


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                gps = new GpsUsing(MainActivity.this);
                gps.getLocation();
                if(gps.isGetLocation()){

                    latitude= gps.getLatitude();
                    longtitude= gps.getLongtitude();

                    gridgps=new LatXLonY();

                    gridgps = gridgps.convertGrid(latitude,longtitude);


                    Lat.setText(String.valueOf(gridgps.x));
                    Lon.setText(String.valueOf(gridgps.y));

                } else{
                    gps.showSettingsAlerts();
                }
                NetworkTask changeTask=new NetworkTask(null);
                changeTask.execute();
            }
        });
    }

    private final int PERMISSIONS_ACCESS_FINE_LOCATION = 1000;
    private final int PERMISSIONS_ACCESS_COARSE_LOCATION = 1001;
    private boolean isAccessFineLocation = false;
    private boolean isAccessCoarseLocation = false;
    private boolean isPermission = false;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSIONS_ACCESS_FINE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            isAccessFineLocation = true;

        } else if (requestCode == PERMISSIONS_ACCESS_COARSE_LOCATION
                && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            isAccessCoarseLocation = true;
        }

        if (isAccessFineLocation && isAccessCoarseLocation) {
            isPermission = true;
        }
    }

    private void callPermission() {
// Check the SDK version and whether the permission is already granted or not.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_ACCESS_FINE_LOCATION);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_ACCESS_COARSE_LOCATION);
        } else {
            isPermission = true;
        }
    }

    public class NetworkTask extends AsyncTask<Void,Void,String> {
        String url;
        WeatherUrlParser parsing;
        NetworkTask(String url){
            this.url = url;
        }
        @Override
        protected void onPreExecute(){
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params){
            System.out.println("X : " +gridgps.x+" Y : " + gridgps.y );
            parsing = new WeatherUrlParser(gridgps.x,gridgps.y);
            return null;
        }
        @Override
        protected void onPostExecute(String result){
            text1.setText(parsing.temp.get(0).toString());
            System.out.println("1");
            text2.setText(parsing.wfKor.get(0));
            System.out.println("2");
            text3.setText(parsing.pty.get(0));
            System.out.println("3");
            text4.setText(parsing.r12.get(0).toString());
            System.out.println("4");
            //text5.setText(parsing.wdKor.get(0));

            System.out.println("5");
        }


    }

}
