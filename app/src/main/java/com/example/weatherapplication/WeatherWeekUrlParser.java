package com.example.weatherapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class WeatherWeekUrlParser {

    URL url;

    List<String>taMAX;
    List<String>taMIN;
    List<String>rnSt;
    List<String>wfkor;
    List<String>dayNum;
    String urlstr1;
    String urlstr2;

    public WeatherWeekUrlParser(String citycode,String weathercode){
        taMAX=new ArrayList<String>();
        taMIN=new ArrayList<String>();
        rnSt=new ArrayList<String>();
        wfkor=new ArrayList<String>();
        dayNum=new ArrayList<String>();

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        int yearS = Integer.parseInt(year.format(date));
        int monthS=Integer.parseInt(month.format(date));
        int dayS = Integer.parseInt(day.format(date));
        int hourS = Integer.parseInt(hour.format(date));
        Calendar cal= Calendar.getInstance();
        cal.setTime(date);
        int dayN=cal.get(Calendar.DAY_OF_WEEK);
        for(int i=1;i<7;i++)
        {
            if(dayN+i>7)
                dayN-=7;
            switch(dayN+i){
                case 1:dayNum.add("일"); break;
                case 2:dayNum.add("월"); break;
                case 3:dayNum.add("화"); break;
                case 4:dayNum.add("수"); break;
                case 5:dayNum.add("목"); break;
                case 6:dayNum.add("금"); break;
                case 7:dayNum.add("토"); break;
            }
        }
        if(hourS<6) {
            if (dayS < 1) {
                if (monthS < 1) {
                    yearS--;
                    monthS = 12;
                    dayS = 31;
                } else {
                    monthS--;
                    if (monthS == 1 || monthS == 3 || monthS == 5 || monthS == 7 || monthS == 8 || monthS == 10) dayS = 31;
                    else
                        dayS = 30;
                }
            } else
                dayS--;
        }
        String dayinput=Integer.toString(yearS);
        if(monthS<10)
            dayinput+="0";
        dayinput+=Integer.toString(monthS);
        if(dayS<10)
            dayinput+="0";
        dayinput+=Integer.toString(dayS);


        urlstr1 = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleTemperature?" + "serviceKey=EqI90gI9JVO%2FedhJZoZidhwWEh5C9Mu2%2B3xX0CnGWlBjeMBZhkCuwEdprXnJhguY4a6D5bImMP7aUOQRy4uL8g%3D%3D&regId="+citycode+"&tmFc="+dayinput+"0600&pageNo=1&numOfRows=10";

        urlstr2 = "http://newsky2.kma.go.kr/service/MiddleFrcstInfoService/getMiddleLandWeather?" + "serviceKey=EqI90gI9JVO%2FedhJZoZidhwWEh5C9Mu2%2B3xX0CnGWlBjeMBZhkCuwEdprXnJhguY4a6D5bImMP7aUOQRy4uL8g%3D%3D&regId="+weathercode+"&tmFc="+dayinput+"0600&pageNo=1&numOfRows=10";

        try {
            url = new URL(urlstr1);
            parsing();
            url = new URL(urlstr2);
            parsing();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }
    public void parsing() {
        boolean inBody = false;
        boolean inItem=false;
        String TagName="";

        List<List> tempList = new ArrayList<List>();
        List<Integer> tempTM = new ArrayList<Integer>();
        XmlPullParserFactory parserCreator = null;
        XmlPullParser parser = null;
        InputStream is = null;
        if(url.toString().equals(urlstr1))
        {
            for(int i=0;i<4;i++)
            {
                taMAX.add("");
                taMIN.add("");
            }
        }
        if(url.toString().equals(urlstr2))
        {
            for(int i=0;i<4;i++) {
                rnSt.add("");
                wfkor.add("");
            }
        }

        try {
            parserCreator = XmlPullParserFactory.newInstance();
            parser = parserCreator.newPullParser();
            is = url.openStream();
            parser.setInput(is, null);
            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch(parserEvent){
                    case XmlPullParser.START_TAG:{
                        if (parser.getName().equals("body")) {
                            inBody = true;
                        }
                        else if(parser.getName().equals("item")){
                            inItem = true;
                        }
                        else if(inBody&&inItem)
                            TagName = parser.getName();
                    }break;
                    case XmlPullParser.TEXT:{
                        if(inBody&&inItem)
                        {
                            if(url.toString().equals(urlstr1))
                            {
                                if(TagName.equals("taMax3"))
                                    taMAX.set(0,parser.getText());
                                else if(TagName.equals("taMax4"))
                                    taMAX.set(1,parser.getText());
                                else if(TagName.equals("taMax5"))
                                    taMAX.set(2,parser.getText());
                                else if(TagName.equals("taMax6"))
                                    taMAX.set(3,parser.getText());
                                else if(TagName.equals("taMin3"))
                                    taMIN.set(0,parser.getText());
                                else if(TagName.equals("taMin4"))
                                    taMIN.set(1,parser.getText());
                                else if(TagName.equals("taMin5"))
                                    taMIN.set(2,parser.getText());
                                else if(TagName.equals("taMin6"))
                                    taMIN.set(3,parser.getText());
                            }
                            else if(url.toString().equals(urlstr2))
                            {
                                if(TagName.equals("rnSt3Pm"))
                                    rnSt.set(0,parser.getText());
                                else if(TagName.equals("rnSt4Pm"))
                                    rnSt.set(1,parser.getText());
                                else if(TagName.equals("rnSt5Pm"))
                                    rnSt.set(2,parser.getText());
                                else if(TagName.equals("rnSt6Pm"))
                                    rnSt.set(3,parser.getText());
                                else if(TagName.equals("wf3Pm"))
                                    wfkor.set(0,parser.getText());
                                else if(TagName.equals("wf4Pm"))
                                    wfkor.set(1,parser.getText());
                                else if(TagName.equals("wf5Pm"))
                                    wfkor.set(2,parser.getText());
                                else if(TagName.equals("wf6Pm"))
                                    wfkor.set(3,parser.getText());
                            }
                        }
                    }break;
                    case XmlPullParser.END_TAG:{

                        TagName="";
                        if (parser.getName().equals("body")) {
                            if (inBody)
                                inBody = false;
                        }
                        else if(parser.getName().equals("item"))
                        {
                            if(inItem)
                                inItem=false;
                        }
                    }break;
                    default:break;
                }
                parserEvent=parser.next();
            }
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
