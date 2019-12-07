package com.example.weatherapplication;

public class RealTimeVote {
    private int yes;
    private int no;

    public RealTimeVote() {
    }

    public RealTimeVote(String adminArea, String subLocality, int yes, int no) {
        this.yes = yes;
        this.no = no;
    }

    public int getYes() {
        return this.yes;
    }

    public void setYes(int yes) {
        this.yes = yes;
    }

    public int getNo() {
        return this.no;
    }

    public void setNo(int no) {
        this.no = no;
    }

}