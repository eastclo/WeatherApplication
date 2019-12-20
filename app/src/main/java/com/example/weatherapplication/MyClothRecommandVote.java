package com.example.weatherapplication;

public class MyClothRecommandVote {
    private int cloth;
    private int feeling;

    public MyClothRecommandVote() {
    }

    public MyClothRecommandVote(int cloth, int feeling) {
        this.cloth = cloth;
        this.feeling = feeling;
    }

    public void setCloth(int cloth) {
        this.cloth = cloth;
    }

    public int getCloth() {
        return cloth;
    }

    public void setfeeling(int feeling) {
        this.feeling = feeling;
    }

    public int getfeeling() {
        return feeling;
    }
}
