package com.example.yasina.llapp.forPainting;

/**
 * Created by yasina on 04.05.15.
 */
public class MyColor {

    private int id, color;

    public MyColor(int id, int color) {
        this.id = id;
        this.color = color;
    }

    public MyColor() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
