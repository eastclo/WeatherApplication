package com.example.weatherapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

public class WeatherUrlParser {

    URL url;
    public String POP="";
    public String SKY="";
    public String T3H="";
    public String TMN="";
    public String TMX="";
    public String PTY="";

    public String taMAX1day="";
    public String taMIN1day="";
    public String taMAX2day="";
    public String taMIN2day="";
    public String rnSt1day="";
    public String rnSt2day="";
    public String sky1day="";
    public String sky2day="";
    public String rain1day="";
    public String rain2day="";
    String tomorrow;
    String secondday;

    public List<String> POPL;
    public List<String> T3HL;
    public List<String> SKYL;
    public List<String> PTYL;

    public int x;
    public int y;

    String hourcheck;
    String daycheck;
    List<String> timeList;
    List<String> dayList;

    public int datacnt;

    public WeatherUrlParser(double x,double y) {
        datacnt = 0;
        settingGrid(x,y);

        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat month = new SimpleDateFormat("MM");
        SimpleDateFormat day = new SimpleDateFormat("dd");
        SimpleDateFormat hour = new SimpleDateFormat("HH");;
        SimpleDateFormat minute = new SimpleDateFormat("mm");
        int yearS = Integer.parseInt(year.format(date));
        int monthS=Integer.parseInt(month.format(date));
        int dayS = Integer.parseInt(day.format(date));
        int hourS = Integer.parseInt(hour.format(date));
        int minuteS=Integer.parseInt(minute.format(date));

        timeList = new ArrayList<String>();
        dayList = new ArrayList<String>();
        POPL = new ArrayList<String>();
        T3HL=new ArrayList<String>();
        SKYL=new ArrayList<String>();
        PTYL=new ArrayList<String>();

        Calendar cc= Calendar.getInstance();
        cc.setTime(date);
        cc.add(Calendar.DATE,1);
        tomorrow=new SimpleDateFormat("yyyyMMdd").format(cc.getTime());
        cc.add(Calendar.DATE,1);
        secondday=new SimpleDateFormat("yyyyMMdd").format(cc.getTime());

        if(hourS<5) {
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
        for(int i=1;i<=8;i++)
        {
            if(hourS<i*3) {
                if((i-1)*3<10)
                    hourcheck="0";
                else
                    hourcheck="";
                hourcheck = hourcheck + (i-1) * 3 + "00";
                String hourLcheck=new String();
                String dayLcheck;
                for(int j=1;j<=7;j++) {
                    if((i-1+j)*3<10)
                        hourLcheck="0";
                    else
                        hourLcheck="";
                    if((i-1+j)*3>=24)
                    {
                        int k=(i-1+j)*3-24;
                        if(k<10)
                            hourLcheck="0";
                        else
                            hourLcheck="";
                        hourLcheck=hourLcheck+k+"00";
                        Calendar c= Calendar.getInstance();
                        c.setTime(date);
                        c.add(Calendar.DATE,1);
                        dayLcheck=new SimpleDateFormat("yyyyMMdd").format(c.getTime());
                    }
                    else {
                        hourLcheck = hourLcheck + ((i - 1 + j) * 3) + "00";
                        dayLcheck = new SimpleDateFormat("yyyyMMdd").format(date);
                    }
                    timeList.add(hourLcheck);
                    dayList.add(dayLcheck);
                    POPL.add("");
                    T3HL.add("");
                    SKYL.add("");
                    PTYL.add("");
                }
                break;
            }
        }

        daycheck=new SimpleDateFormat("yyyyMMdd").format(date);
        String urlstr = "http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData?serviceKey=EqI90gI9JVO%2FedhJZoZidhwWEh5C9Mu2%2B3xX0CnGWlBjeMBZhkCuwEdprXnJhguY4a6D5bImMP7aUOQRy4uL8g%3D%3D&base_date="+dayinput+"&base_time="+"0500"+"&nx="+(int)x+"&ny="+(int)y+"&numOfRows=300&pageNo=1&_type=xml";

        try {
            url = new URL(urlstr);
            parsing();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
    public void settingGrid(double x,double y)
    {
        this.x= (int)Math.round(x);
        this.y=(int)Math.round(y);
    }
    public void parsing() {
        String TagName="";
        String dataS="";
        int data = 0;
        String categoryName="";
        boolean inTitle = false;
        boolean inBody = false;
        boolean inItem=false;
        boolean inTime=false;
        boolean inDay=false;
        boolean TMXcheck=false;
        boolean TMNcheck=false;
        boolean afterDay=false;
        boolean afterTime=false;
        boolean next1day=false;
        boolean next2day=false;
        boolean nextmax=false;

        int indexDay=0;
        int indexTime=0;
        List<List> tempList = new ArrayList<List>();
        List<Integer> tempTM=new ArrayList<Integer>();
        XmlPullParserFactory parserCreator=null;
        XmlPullParser parser=null;
        InputStream is=null;
        try {
            parserCreator = XmlPullParserFactory.newInstance();
            parser = parserCreator.newPullParser();
            is = url.openStream();
            parser.setInput(is,null);
            int parserEvent = parser.getEventType();

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
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
                        if (inBody&&inItem) {
                            if(TagName.equals("category")) {
                                categoryName = parser.getText();
                                if(categoryName.equals("TMX")) {
                                    TMXcheck = true;
                                    TMNcheck=false;
                                }
                                else if(categoryName.equals("TMN")){
                                    TMXcheck=false;
                                    TMNcheck=true;
                                }
                                else{
                                    TMXcheck=false;
                                    TMNcheck=false;
                                }

                            }
                            else if(TagName.equals("fcstDate"))
                            {
                                if(parser.getText().equals(daycheck)) {
                                    inDay = true;
                                }
                                else if(parser.getText().equals(tomorrow))
                                {
                                    next1day=true;
                                }
                                else if(parser.getText().equals(secondday))
                                {
                                    next2day=true;
                                }
                                for(int i=0;i<7;i++)
                                {
                                    if(parser.getText().equals(dayList.get(i)))
                                    {
                                        afterDay=true;
                                        indexDay=i;
                                    }
                                }
                            }
                            else if(TagName.equals("fcstTime"))
                            {
                                if(parser.getText().equals(hourcheck)){
                                    inTime=true;
                                }
                                if(parser.getText().equals("1200"))
                                {
                                    if(next1day||next2day)
                                        nextmax=true;
                                }
                                for(int i=0;i<7;i++)
                                {
                                    if(parser.getText().equals(timeList.get(i)))
                                    {
                                        afterTime=true;
                                        indexTime=i;
                                    }
                                }
                            }
                            else if(TagName.equals("fcstValue"))
                            {
                                if(inDay)
                                {
                                    dataS=parser.getText();
                                    if(categoryName.equals("T3H"))
                                        tempTM.add(Integer.parseInt(dataS));
                                    if(TMXcheck)
                                        TMX=dataS;
                                    else if(TMNcheck)
                                        TMN=dataS;
                                    if(inTime){
                                        if(categoryName.equals("POP"))
                                            POP=dataS;
                                        else if(categoryName.equals("SKY"))
                                            SKY=dataS;
                                        else if(categoryName.equals("PTY"))
                                            PTY=dataS;
                                        else if(categoryName.equals("T3H"))
                                            T3H=dataS;
                                        else if(categoryName.equals("TMX"))
                                            T3H=dataS;
                                        else if(categoryName.equals("TMN"))
                                            T3H=dataS;
                                    }
                                }
                                else if(afterDay&&afterTime)
                                {
                                    dataS=parser.getText();
                                    if(categoryName.equals("POP")) {
                                        POPL.set(indexTime, dataS);
                                    }
                                    else if(categoryName.equals("SKY")) {
                                        SKYL.set(indexTime, dataS);
                                    }
                                    else if(categoryName.equals("PTY")) {
                                        PTYL.set(indexTime,dataS);
                                    }
                                    else if(categoryName.equals("T3H")) {
                                        T3HL.set(indexTime, dataS);
                                    }
                                    else if(categoryName.equals("TMX")) {
                                        long tmxd=(Math.round(Double.parseDouble(dataS)));
                                        T3HL.set(indexTime,Long.toString(tmxd));
                                    }
                                    else if(categoryName.equals("TMN")){
                                        long tmnd=(Math.round(Double.parseDouble(dataS)));
                                        T3HL.set(indexTime,Long.toString(tmnd));
                                    }
                                }
                                if(next1day)
                                {
                                    dataS=parser.getText();
                                    if(nextmax)
                                    {
                                        if(categoryName.equals("POP"))
                                            rnSt1day=dataS;
                                        else if(categoryName.equals("SKY"))
                                            sky1day=dataS;
                                    }
                                    if(categoryName.equals("PTY"))
                                    {
                                        if(!dataS.equals("0"))
                                            rain1day=dataS;
                                    }
                                    if(categoryName.equals("TMX"))
                                        taMAX1day=dataS;
                                    else if(categoryName.equals("TMN"))
                                        taMIN1day=dataS;
                                }
                                else if(next2day)
                                {
                                    dataS=parser.getText();
                                    if(nextmax)
                                    {
                                        if(categoryName.equals("POP"))
                                            rnSt2day=dataS;
                                        else if(categoryName.equals("SKY"))
                                            sky2day=dataS;
                                    }
                                    if(categoryName.equals("PTY"))
                                    {
                                        if(!dataS.equals("0"))
                                            rain2day=dataS;
                                    }
                                    else if(categoryName.equals("TMX"))
                                        taMAX2day=dataS;
                                    else if(categoryName.equals("TMN"))
                                        taMIN2day=dataS;
                                }
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
                            if(inDay)
                                inDay=false;
                            if(inTime)
                                inTime=false;
                            if(afterDay)
                                afterDay=false;
                            if(afterTime)
                                afterTime=false;
                            if(next1day)
                                next1day=false;
                            if(next2day)
                                next2day=false;
                            if(nextmax)
                                nextmax=false;
                        }
                    }break;
                    default:break;
                }
                parserEvent = parser.next();
            }
            is.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        if(TMN.equals(""))
            TMN= Integer.toString(Collections.min(tempTM));
        if(TMX.equals(""))
            TMX=Integer.toString(Collections.max(tempTM));
    }
}
