package com.example.weatherapplication;

public class TotalClothRecommandVote {
    private  int feeling1;
    private  int feeling2;
    private  int feeling3;

    public TotalClothRecommandVote () {
    }

    public TotalClothRecommandVote (int feeling1, int feeling2, int feeling3) {
        this.feeling1 = feeling1;
        this.feeling2 = feeling2;
        this.feeling3 = feeling3;
    }

    public int getFeeling1() {
        return feeling1;
    }

    public int getFeeling2() {
        return feeling2;
    }

    public int getFeeling3() {
        return feeling3;
    }

    public void setFeeling1(int feeling1) {
        this.feeling1 = feeling1;
    }

    public void setFeeling2(int feeling2) {
        this.feeling2 = feeling2;
    }

    public void setFeeling3(int feeling3) {
        this.feeling3 = feeling3;
    }
}
