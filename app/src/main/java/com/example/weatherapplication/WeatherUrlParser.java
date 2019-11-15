package com.example.weatherapplication;

import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.app.Service;
import android.os.Handler;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class WeatherUrlParser extends Service implements Runnable {


    private final IBinder mBinder = new ServiceBinder();
    public static String WEATHER_URL = "http://www.kma.go.kr/wid/queryDFS.jsp?";

    private Handler mHandler;
    private boolean mRunning = true;
    private long DELAY_TIME = 30 * 60 * 1000;
    public static boolean first = true;

    public class UrlParser extends DefaultHandler{

        private int COUNT_OF_TIME = 7;

        private String DATA_ELEMENT = "data seq=";
        private String CLOSE_DATA_ELEMENT = "data";
        private String BODY_ELEMENT = "body";
        private String TIME_ELEMENT = "hour";
        private String TEMP_ELEMENT = "temp";
        private String WFEN_ELEMENT = "wfEn";
        private String element;
        boolean inBody=false;
        private int seq_count = 0;

        private StringBuffer resourceIdBuffer;
        private StringBuffer timeBuffer;
        private StringBuffer tempBuffer;
        private String[] temp=new String[COUNT_OF_TIME];
        private String[] times=new String[COUNT_OF_TIME];
        private String[] resourceIds=new String[COUNT_OF_TIME];

        public static final String INFORMATION_RECIEVER = "org.techtown.weatherapplication";
        public static final String RESOURCE_IDS = "resource_ids";
        public static final String TIME = "time";
        public static final String TEMPERATURE = "temp";

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes)throws SAXException {
            element = localName;
            if(localName.equals(DATA_ELEMENT+"\""+seq_count+"\"")) {
                resourceIdBuffer = new StringBuffer();
                timeBuffer = new StringBuffer();
            }else if(localName.equals(BODY_ELEMENT)){
                inBody=true;
            }
        }

        @Override
        public void characters(char[] ch,int start, int length) throws SAXException
        {
            if(!inBody) {
                return;
            }
            if(element.equals(TIME_ELEMENT)){
                timeBuffer.append(ch,start,length);
            }else if(element.equals(TEMP_ELEMENT)){
                tempBuffer.append(ch,start,length);
            }else if(element.equals(WFEN_ELEMENT)) {
                resourceIdBuffer.append(ch,start,length);
            }
        }

        @Override
        public void endElement(String uri,String localName,String qName)throws SAXException{
            if(localName.equals(CLOSE_DATA_ELEMENT)){
                if(seq_count==COUNT_OF_TIME){
                    inBody=false;
                }
                else{
                    resourceIds[seq_count]=resourceIdBuffer.toString().trim();
                    temp[seq_count]=tempBuffer.toString().trim();
                    times[seq_count]=timeBuffer.toString().trim();
                    seq_count++;
                    resourceIdBuffer=null;
                    tempBuffer=null;
                    timeBuffer=null;

                }
            }else if(localName.equals(BODY_ELEMENT)){
                Intent intent = new Intent(INFORMATION_RECIEVER);
                intent.putExtra(RESOURCE_IDS,resourceIds);
                intent.putExtra(TIME,times);
                intent.putExtra(TEMPERATURE,temp);
                sendBroadcast(intent);
                inBody=false;
            }
        }

    }

    public class ServiceBinder extends Binder {

        WeatherUrlParser getService(){
            return WeatherUrlParser.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent){

        return mBinder;
    }

    @Override
    public void onCreate(){
        mHandler = new Handler();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mRunning){
            if(first){
                mHandler.postDelayed(this,100);
                first = false;
            }else{
                mHandler.postDelayed(this,DELAY_TIME);
            }
            mRunning =true;
        }
        else{
            if(first){
                mHandler.postDelayed(this,100);
                first = false;
            }
        }
        return START_NOT_STICKY;

    }

    @Override
    public void onDestroy() {
        mRunning = false;

    }

    public void run(){
        if (mRunning) {
            urlParser();
            if(first){
                mHandler.postDelayed(this,100);
            }else{
                mHandler.postDelayed(this,DELAY_TIME);
            }
        }
    }
    private void urlParser(){
        try{
            URL url = new URL(WEATHER_URL);
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            UrlParser urlParser = new UrlParser();
            reader.setContentHandler(urlParser);
            reader.parse(new InputSource(url.openStream()));

        }catch(Exception e){

        }
    }

    public static void SettingUrl(int gridx,int gridy){
        WEATHER_URL = WEATHER_URL +"&gridx=" + gridx + "&gridy="+gridy;

    }
}
