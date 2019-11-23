package com.example.weatherapplication;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class WeatherUrlParser {

    URL url;
    public ArrayList<Object> temp;
    public ArrayList<String> wfKor;
    public ArrayList<String> pty;
    public ArrayList<Object> r12;
    public ArrayList<String> wdKor;

    public int x;
    public int y;

    String data;

    public int datacnt;

    public WeatherUrlParser(double x,double y) {
        temp = new ArrayList<Object>();
        wfKor = new ArrayList<String>();
        pty = new ArrayList<String>();
        r12 = new ArrayList<Object>();
        wdKor = new ArrayList<String>();
        datacnt = 0;
        settingGrid(x,y);
        String urlstr = "https://www.kma.go.kr/wid/queryDFS.jsp?gridx="+(int)x+"&gridy="+(int)y;
        System.out.println(urlstr);
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
        String dataText="";
        boolean inTitle = false;
        boolean inBody = false;
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
                        else if(inBody)
                            TagName = parser.getName();
                    }break;
                    case XmlPullParser.TEXT:{
                        if (inBody) {
                            if(TagName.equals("temp"))
                            {
                                dataText = parser.getText();
                                if(dataText!="")
                                    temp.add(Double.parseDouble(dataText));
                            }
                            else if(TagName.equals("wfKor"))
                            {
                                dataText = parser.getText();
                                if(dataText!="")
                                    wfKor.add(dataText);
                            }
                            else if(TagName.equals("pty"))
                            {
                                dataText = parser.getText();
                                if(dataText=="")
                                    dataText="0";
                                switch(Integer.parseInt(dataText)){
                                    case 0 : pty.add("NULL");break;
                                    case 1 : pty.add("비");break;
                                    case 2 : pty.add("비/눈");break;
                                    case 3 : pty.add("눈/비");break;
                                    case 4 : pty.add("눈");break;
                                }
                            }
                            else if(TagName.equals("r12"))
                            {
                                dataText = parser.getText();
                                if(dataText!="")
                                    r12.add(Double.parseDouble(dataText));
                            }
                            else if(TagName.equals("wdKor"))
                            {
                                dataText = parser.getText();
                                if(dataText!="")
                                    wdKor.add(dataText);
                            }
                        }
                    }break;
                    case XmlPullParser.END_TAG:{
                        TagName="";

                        if (parser.getName().equals("body")) {
                            if (inBody)
                                inBody = false;
                        }
                        else if(parser.getName().equals("data"))
                            datacnt++;
                    }break;
                    default:break;
                }
                parserEvent = parser.next();

            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
