package com.twist.navtest2.Bean;

public class People {
    private String name;

    public String getT02() {
        return t02;
    }

    public void setT02(String t02) {
        this.t02 = t02;
    }

    private String t02;
    private int imgRes;
    public People(String s, String s2,int imgRes){
        name = s;
        t02 = s2;
        this.imgRes = imgRes;
    }
    public String getName(){
        return name == null?"":name;
    }


    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public void setName(String name) {
        this.name = name;
    }
}
