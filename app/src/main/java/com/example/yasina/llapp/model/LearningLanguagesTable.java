package com.example.yasina.llapp.model;

/**
 * Created by yasina on 02.03.15.
 */
public class LearningLanguagesTable {
    public static final String TAG = "LearningLanguagesTable";
    private static final long serialVersionUID = -7406082437623008161L;

    private long userID, firstLanguageID, secondLanguageID;

    public LearningLanguagesTable() {
    }

    public LearningLanguagesTable(long secondLanguageID, long firstLanguageID, long userID) {
        this.secondLanguageID = secondLanguageID;
        this.firstLanguageID = firstLanguageID;
        this.userID = userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setFirstLanguageID(long firstLanguageID) {
        this.firstLanguageID = firstLanguageID;
    }

    public void setSecondLanguageID(long secondLanguageID) {
        this.secondLanguageID = secondLanguageID;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public long getUserID() {
        return userID;
    }

    public long getFirstLanguageID() {
        return firstLanguageID;
    }

    public long getSecondLanguageID() {
        return secondLanguageID;
    }
}
