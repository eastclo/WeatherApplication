package com.example.weatherapplication;

import android.util.Pair;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class AirQualityDataParser {

    private String parameter;    //시도 or 분류(O3,PM15,25)
    private String sggUrl; //시군구(각 시도 데이터)
    private String sidoUrl;    //시도(전체 데이터)

    XmlPullParserFactory parserCreator; //parser객체 생성자
    XmlPullParser parser;
    InputStream is;
    ArrayList<Pair<String,String>> dataList;    //리턴 값


    public AirQualityDataParser(String location, String parameter){  // parameter: 시도 or 분류(O3,PM15,25)
        this.parameter = parameter;
        this.sggUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?sidoName="+location+"&searchCondition=DAILY&pageNo=1&numOfRows=31&ServiceKey=I67W08oiGKZ90ScEaeHayj9xOE8BcfBuOJoGLM%2BIoBBukrj53xaZNrACxxCH5Uay75aXixJqNuZlkGDD%2BLVTVQ%3D%3D";
        this.sidoUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureLIst?itemCode="+location+"&dataGubun=HOUR&searchCondition=MONTH&pageNo=1&numOfRows=1&ServiceKey=I67W08oiGKZ90ScEaeHayj9xOE8BcfBuOJoGLM%2BIoBBukrj53xaZNrACxxCH5Uay75aXixJqNuZlkGDD%2BLVTVQ%3D%3D";
    }

    /*전체 시,도 데이터파싱*/
//    public ArrayList<Pair<String,String>> sidoUrlParsing(){ //전국 데이터
//        //String: 시도, String: 데이터값
//        URL urlSido = null;
//
//        try{
//            urlSido = new URL(sidoUrl);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            parserCreator = XmlPullParserFactory.newInstance();
//            parser = parserCreator.newPullParser();
//            is = urlSido.openStream();
//            parser.setInput(is, null);
//            int parserEvent = parser.getEventType();
//
//
//            while (parserEvent != XmlPullParser.END_DOCUMENT) { //파싱 데이터가 끝날 때까지 탐색
//
//                parserEvent = parser.next();
//            }
//        } catch (XmlPullParserException e){} catch (IOException e) {e.printStackTrace();}
//
//        return dataList;
//    }

    /*각 시도별, 시군구 데이터 파싱*/
    public ArrayList<Pair<String,String>> sggParsing(){ //각 시도 데이터
        //String: 시군구, String: 데이터값
        URL urlSgg = null;
        boolean inItem = false, inBody = false;
        boolean isFirst = false, isSecond = false;
        String tagName = null;
        String first = null;
        String second = null;


        dataList = new ArrayList<Pair<String,String>>();

        try {
            urlSgg = new URL(sggUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            parserCreator = XmlPullParserFactory.newInstance();
            parser = parserCreator.newPullParser();
            is = urlSgg.openStream();
            parser.setInput(is,null);
            int parserEvent = parser.getEventType();

            while  (parserEvent != XmlPullParser.END_DOCUMENT){ //파싱 데이터가 끝날 때까지 탐색
                if(parser.getName()==null && parser.getText() !=null && (parser.getText().contains("\n") || parser.getText().contains("\t"))) {
                    parserEvent = parser.next();
                    continue;
                }
                switch (parserEvent){
                    case XmlPullParser.START_TAG:
                        if(parser.getName().equals("body")) { //item태그 안에 들어감
                            inBody = true;
                        } else if(parser.getName().equals("item")) {
                            inItem = true;
                        } else if(inBody&&inItem)
                            tagName = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if(inItem && inBody) {
                            if (tagName.equals("cityName")) {   //cityName 태그 안에 들어감
                                first = parser.getText();
                                isFirst = true;
                            } else if (parameter.equals("PM10") && tagName.equals("pm10Value")) {
                                second = parser.getText();
                                isSecond = true;
                            } else if (parameter.equals("PM25") && tagName.equals("pm25Value")) {
                                second = parser.getText();
                                isSecond = true;
                            } else if (parameter.equals("O3") && tagName.equals("o3Value")){
                                second = parser.getText();
                                isSecond = true;
                            }
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            if(isFirst && isSecond ) {
                                dataList.add(new Pair(first,second));
                                inItem = false;
                                isFirst = false;
                                isSecond = false;
                            }
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (XmlPullParserException e){} catch (IOException e) {e.printStackTrace();}
        return dataList;
    }

}