package com.example.elogbookapp.model;

import android.graphics.Bitmap;

public class DrawerItem {
    String name;
    int image;

    public int getBlueimage() {
        return blueimage;
    }

    public void setBlueimage(int blueimage) {
        this.blueimage = blueimage;
    }

    int blueimage;
    String Url;

    public DrawerItem(String Nname,int Nimage,int blueimage)
    {
        this.name=Nname;
        this.image=Nimage;
        this.blueimage=blueimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
