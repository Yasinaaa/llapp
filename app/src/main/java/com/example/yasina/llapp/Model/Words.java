package com.example.yasina.llapp.Model;

import java.io.Serializable;

/**
 * Created by yasina on 10.03.15.
 */
public class Words implements Serializable {

    public static final String TAG = "WordsPair";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String firstLang, secondLang, explanation;
    private byte[] image;

    public Words(String firstLang, String secondLang, byte[] image, String explanation) {
        this.firstLang = firstLang;
        this.secondLang = secondLang;
        this.image = image;
        this.explanation = explanation;
    }

    public Words() {
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public long getmId() {
        return mId;
    }

    public String getFirstLang() {
        return firstLang;
    }

    public String getSecondLang() {
        return secondLang;
    }

    public void setFirstLang(String firstLang) {
        this.firstLang = firstLang;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public void setSecondLang(String secondLang) {
        this.secondLang = secondLang;
    }
}
