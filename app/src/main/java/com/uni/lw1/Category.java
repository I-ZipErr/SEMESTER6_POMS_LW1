package com.uni.lw1;

import android.graphics.drawable.Drawable;

public class Category {
    private int icon;
    private String name;
    public Category(int icon, String name){
        this.icon = icon;
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }
}
