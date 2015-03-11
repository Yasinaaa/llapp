package com.example.yasina.llapp.Model;

import java.io.Serializable;

/**
 * Created by yasina on 10.03.15.
 */
public class WordsPair implements Serializable {

    public static final String TAG = "WordsPair";
    private static final long serialVersionUID = -7406082437623008161L;

    private long mId;
    private String firstLang, secondLang;

    public WordsPair(String firstLang, String secondLang) {
        this.firstLang = firstLang;
        this.secondLang = secondLang;
    }

    public WordsPair() {
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
