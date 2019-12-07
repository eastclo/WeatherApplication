package com.example.weatherapplication;

import android.util.Pair;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimeZone;

public class AirQualityDataParser {

    private String parameter;    //시도 or 분류(O3,PM15,25)
    private String sggUrl; //시군구(각 시도 데이터)
    private String sidoUrl;    //시도(전체 데이터)
    private String ultraVioletRaysUrl; //자외선 파싱주소;

    private final String[] areaNo = {"1100000000","2600000000","2700000000","2800000000","2900000000","3000000000","3100000000","3600000000",
            "4100000000","4200000000","4300000000","4400000000","4500000000","4600000000","4700000000","4800000000","5000000000"};
    //서울,부산,대구,인천,광주,대전,울산,세종,경기,강원,충북,충남,전북,전남,경북,경남,제주

    XmlPullParserFactory parserCreator; //parser객체 생성자
    XmlPullParser parser;
    InputStream is;
    ArrayList<Pair<String,String>> dataList;    //리턴 값


    public AirQualityDataParser(String location, String parameter){  // parameter: 시도 or 분류(O3,PM15,25)
        this.parameter = parameter;
        this.sggUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureSidoLIst?sidoName="+location+"&searchCondition=DAILY&pageNo=1&numOfRows=31&ServiceKey=I67W08oiGKZ90ScEaeHayj9xOE8BcfBuOJoGLM%2BIoBBukrj53xaZNrACxxCH5Uay75aXixJqNuZlkGDD%2BLVTVQ%3D%3D";
        this.sidoUrl = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnMesureLIst?itemCode="+parameter+"&dataGubun=HOUR&searchCondition=MONTH&pageNo=1&numOfRows=1&ServiceKey=I67W08oiGKZ90ScEaeHayj9xOE8BcfBuOJoGLM%2BIoBBukrj53xaZNrACxxCH5Uay75aXixJqNuZlkGDD%2BLVTVQ%3D%3D";
    }

    public ArrayList<Pair<String,String>> ultraVioletRaysUrlParsing(){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String time = df.format(date);  //현재 시간 설정

        DateFormat dfH = new SimpleDateFormat("HH");
        dfH.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
        String timeHour = dfH.format(date);

        if(Integer.parseInt(timeHour)<6 || 17 <Integer.parseInt(timeHour))
            timeHour = "17";
        //        if(timeHour.equals("18") || timeHour.equals("19") || timeHour.equals("20") || timeHour.equals("21") || timeHour.equals("22") || timeHour.equals("23") ||
//                timeHour.equals("01") ||timeHour.equals("02") ||timeHour.equals("03") ||timeHour.equals("04") ||timeHour.equals("05")){
        time = time.concat(timeHour);

        dataList = new ArrayList<Pair<String,String>>();
        for(String areaNumber : areaNo) {
            ultraVioletRaysUrl = "http://newsky2.kma.go.kr/iros/RetrieveLifeIndexService3/getUltrvLifeList?serviceKey=I67W08oiGKZ90ScEaeHayj9xOE8BcfBuOJoGLM%2BIoBBukrj53xaZNrACxxCH5Uay75aXixJqNuZlkGDD%2BLVTVQ%3D%3D&areaNo=" + areaNumber + "&time="+time;

            URL urlVioletRays = null;
            boolean IndexModel = false;
            boolean isFirst = false;
            String tagName = null;
            String first = null;
            String second = null;

            try {
                urlVioletRays = new URL(ultraVioletRaysUrl);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                parserCreator = XmlPullParserFactory.newInstance();
                parser = parserCreator.newPullParser();
                is = urlVioletRays.openStream();
                parser.setInput(is, null);  //parser 생성
                int parserEvent = parser.getEventType();

                while  (parserEvent != XmlPullParser.END_DOCUMENT) { //파싱 데이터가 끝날 때까지 탐색
                    if (parser.getName() == null && parser.getText() != null && (parser.getText().contains("\n") || parser.getText().contains("\t"))) {
                        parserEvent = parser.next();
                        continue;
                    }
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG:
                            if (parser.getName().equals("IndexModel")) { //IndexModel태그 안에 들어감
                                IndexModel   = true;
                            } else if (IndexModel)
                                tagName = parser.getName();
                            break;
                        case XmlPullParser.TEXT:
                            if (IndexModel && tagName.equals("today")) {
                                switch (areaNumber) {  //지역 구분
                                    case "1100000000":
                                        first = "서울특별시";
                                        break;
                                    case "2600000000":
                                        first = "부산광역시";
                                        break;
                                    case "2700000000":
                                        first = "대구광역시";
                                        break;
                                    case "2800000000":
                                        first = "인천광역시";
                                        break;
                                    case "2900000000":
                                        first = "광주광역시";
                                        break;
                                    case "3000000000":
                                        first = "대전광역시";
                                        break;
                                    case "3100000000":
                                        first = "울산광역시";
                                        break;
                                    case "4100000000":
                                        first = "경기도";
                                        break;
                                    case "4200000000":
                                        first = "강원도";
                                        break;
                                    case "4300000000":
                                        first = "충청북도";
                                        break;
                                    case "4400000000":
                                        first = "충청남도";
                                        break;
                                    case "4500000000":
                                        first = "전라북도";
                                        break;
                                    case "4600000000":
                                        first = "전라남도";
                                        break;
                                    case "4700000000":
                                        first = "경상북도";
                                        break;
                                    case "4800000000":
                                        first = "경상남도";
                                        break;
                                    case "5000000000":
                                        first = "제주특별자치도";
                                        break;
                                    case "3600000000":
                                        first = "세종시";
                                        break;
                                }
                                isFirst = true;
                                second = parser.getText();
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if(parser.getName().equals("today")){
                                if(isFirst){
                                    dataList.add(new Pair(first,second));
                                    isFirst = false;
                                    IndexModel = false;
                                }
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (XmlPullParserException e){} catch (IOException e) {e.printStackTrace();}
        }
        return dataList;
    }

    /*전체 시,도 데이터파싱*/
    public ArrayList<Pair<String,String>> sidoUrlParsing(){ //전국 데이터
        //String: 시도, String: 데이터값
        URL urlSido = null;
        boolean inItem = false, inBody = false;
        boolean isFirst = false;
        String tagName = null;
        String first = null;
        String second = null;


        dataList = new ArrayList<Pair<String,String>>();

        try {
            urlSido = new URL(sidoUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            parserCreator = XmlPullParserFactory.newInstance();
            parser = parserCreator.newPullParser();
            is = urlSido.openStream();
            parser.setInput(is, null);  //parser 생성
            int parserEvent = parser.getEventType();

            while  (parserEvent != XmlPullParser.END_DOCUMENT) { //파싱 데이터가 끝날 때까지 탐색
                if (parser.getName() == null && parser.getText() != null && (parser.getText().contains("\n") || parser.getText().contains("\t"))) {
                    parserEvent = parser.next();
                    continue;
                }
                switch (parserEvent) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equals("body")) { //item태그 안에 들어감
                            inBody = true;
                        } else if (parser.getName().equals("item")) {
                            inItem = true;
                        } else if (inBody && inItem)
                            tagName = parser.getName();
                        break;
                    case XmlPullParser.TEXT:
                        if (inItem && inBody) {
                            switch (tagName) {  //지역 구분
                                case "seoul":
                                    first = "서울특별시";
                                    break;
                                case "busan":
                                    first = "부산광역시";
                                    break;
                                case "daegu":
                                    first = "대구광역시";
                                    break;
                                case "incheon":
                                    first = "인천광역시";
                                    break;
                                case "gwangju":
                                    first = "광주광역시";
                                    break;
                                case "daejeon":
                                    first = "대전광역시";
                                    break;
                                case "ulsan":
                                    first = "울산광역시";
                                    break;
                                case "gyeonggi":
                                    first = "경기도";
                                    break;
                                case "gangwon":
                                    first = "강원도";
                                    break;
                                case "chungbuk":
                                    first = "충청북도";
                                    break;
                                case "chungnam":
                                    first = "충청남도";
                                    break;
                                case "jeonbuk":
                                    first = "전라북도";
                                    break;
                                case "jeonnam":
                                    first = "전라남도";
                                    break;
                                case "gyeongbuk":
                                    first = "경상북도";
                                    break;
                                case "gyeongnam":
                                    first = "경상남도";
                                    break;
                                case "jeju":
                                    first = "제주특별자치도";
                                    break;
                                case "sejong":
                                    first = "세종시";
                                    break;
                            }
                            isFirst = true;
                            second = parser.getText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        switch (parser.getName()) {
                            case "body":
                                inBody = false;
                                break;
                            case "item":
                                inItem = false;
                                break;
                            case "seoul":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "busan":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "daegu":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "incheon":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "gwangju":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "daejeon":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "ulsan":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "gyeonggi":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "gangwon":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "chungbuk":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "chungnam":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "jeonbuk":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "jeonnam":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "gyeongbuk":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "gyeongnam":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "jeju":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                            case "sejong":
                                if (isFirst)
                                    dataList.add(new Pair(first, second));
                                break;
                        }
                        isFirst = false;
                        break;
                }
                parserEvent = parser.next();
            }
        } catch (XmlPullParserException e){} catch (IOException e) {e.printStackTrace();}

        return dataList;
    }

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
            parser.setInput(is,null);   //parser 생성
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