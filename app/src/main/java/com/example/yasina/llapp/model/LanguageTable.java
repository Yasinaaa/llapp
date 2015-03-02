package com.example.yasina.llapp.model;

/**
 * Created by yasina on 02.03.15.
 */
public class LanguageTable {
    public static final String TAG = "LanguageTable";
    private static final long serialVersionUID = -7406082437623008161L;

    private long languageID;
    private String languageName;

    public LanguageTable(String languageName) {
        this.languageName = languageName;
    }

    public LanguageTable() {
    }

    public long getLanguageID() {
        return languageID;
    }

    public String getLanguageName() {
        return languageName;
    }

    public void setLanguageID(long languageID) {
        this.languageID = languageID;
    }

    public void setLanguageName(String languageName) {
        this.languageName = languageName;
    }
}
