package com.example.yasina.llapp.Alarm;


import android.net.Uri;

import java.io.Serializable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class AlarmModel  implements Parcelable {

    private int id,
    fromDay, fromMonth, fromYear, fromHours, fromMinutes ,
    toDay, toMonth, toYear, toHours, toMinutes,
    fromSleepHours, fromSleepMinutes,
    toSleepHours, toSleepMinutes,
    repeat;
    private String themeName,fromAM_PM, toAM_PM, fromSleep_AM_PM,  toSleep_AM_PM, repeatMin_Hour;
    private boolean isEnabled;
    public String alarmTone;
    public  int cur = 0;

    private AlarmModel(Parcel in) {
        fromDay = in.readInt();
        fromMonth = in.readInt();
        fromMonth = in.readInt();
        fromYear = in.readInt();
        fromHours = in.readInt();
        fromMinutes = in.readInt();
        fromAM_PM = in.readString();
        toDay = in.readInt();
        toMonth = in.readInt();
        toYear = in.readInt();
        toDay = in.readInt();
        toMonth = in.readInt();
        toYear = in.readInt();
        toHours = in.readInt();
        toMinutes = in.readInt();
        toAM_PM = in.readString();
        fromDay = in.readInt();
        fromHours = in.readInt();
        fromSleepHours = in.readInt();
        fromSleepMinutes = in.readInt();
        fromSleep_AM_PM = in.readString();
        toSleepHours  = in.readInt();
        toSleepMinutes = in.readInt();
        toAM_PM = in.readString();
        repeatMin_Hour = in.readString();
        repeat = in.readInt();
        alarmTone = in.readString();
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public AlarmModel() {
    }

    public String getThemeName() {
        return themeName;
    }

    public String getRepeatMin_Hour() {
        return repeatMin_Hour;
    }

    public void setRepeatMin_Hour(String repeatMin_Hour) {
        this.repeatMin_Hour = repeatMin_Hour;
    }

    public void setThemeName(String themeName) {
        this.themeName = themeName;
    }

    public int getRepeat() {
        return repeat;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    public int getToSleepMinutes() {
        return toSleepMinutes;
    }

    public void setToSleepMinutes(int toSleepMinutes) {
        this.toSleepMinutes = toSleepMinutes;
    }

    public int getToSleepHours() {
        return toSleepHours;
    }

    public void setToSleepHours(int toSleepHours) {
        this.toSleepHours = toSleepHours;
    }

    public int getFromSleepMinutes() {
        return fromSleepMinutes;
    }

    public void setFromSleepMinutes(int fromSleepMinutes) {
        this.fromSleepMinutes = fromSleepMinutes;
    }

    public int getFromSleepHours() {
        return fromSleepHours;
    }

    public void setFromSleepHours(int fromSleepHours) {
        this.fromSleepHours = fromSleepHours;
    }

    public int getToMinutes() {
        return toMinutes;
    }

    public void setToMinutes(int toMinutes) {
        this.toMinutes = toMinutes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFromDay() {
        return fromDay;
    }

    public void setFromDay(int fromDay) {
        this.fromDay = fromDay;
    }

    public int getFromMonth() {
        return fromMonth;
    }

    public void setFromMonth(int fromMonth) {
        this.fromMonth = fromMonth;
    }

    public int getFromYear() {
        return fromYear;
    }

    public void setFromYear(int fromYear) {
        this.fromYear = fromYear;
    }

    public int getFromHours() {
        return fromHours;
    }

    public void setFromHours(int fromHours) {
        this.fromHours = fromHours;
    }

    public int getFromMinutes() {
        return fromMinutes;
    }

    public void setFromMinutes(int fromMinutes) {
        this.fromMinutes = fromMinutes;
    }

    public int getToDay() {
        return toDay;
    }

    public void setToDay(int toDay) {
        this.toDay = toDay;
    }

    public int getToYear() {
        return toYear;
    }

    public void setToYear(int toYear) {
        this.toYear = toYear;
    }

    public int getToMonth() {
        return toMonth;
    }

    public void setToMonth(int toMonth) {
        this.toMonth = toMonth;
    }

    public int getToHours() {
        return toHours;
    }

    public void setToHours(int toHours) {
        this.toHours = toHours;
    }

    public String getFromAM_PM() {
        return fromAM_PM;
    }

    public void setFromAM_PM(String fromAM_PM) {
        this.fromAM_PM = fromAM_PM;
    }

    public String getToAM_PM() {
        return toAM_PM;
    }

    public void setToAM_PM(String toAM_PM) {
        this.toAM_PM = toAM_PM;
    }

    public String getFromSleep_AM_PM() {
        return fromSleep_AM_PM;
    }

    public void setFromSleep_AM_PM(String fromSleep_AM_PM) {
        this.fromSleep_AM_PM = fromSleep_AM_PM;
    }

    public String getToSleep_AM_PM() {
        return toSleep_AM_PM;
    }

    public void setToSleep_AM_PM(String toSleep_AM_PM) {
        this.toSleep_AM_PM = toSleep_AM_PM;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        Log.d("parse","writeToParse");
        dest.writeInt(fromDay);
        dest.writeInt(fromMonth);
        dest.writeInt(fromYear);
        dest.writeInt(fromHours);
        dest.writeInt(fromMinutes);
        dest.writeString(fromAM_PM);
        dest.writeInt(toDay);
        dest.writeInt(toMonth);
        dest.writeInt(toYear);
        dest.writeInt(toDay);
        dest.writeInt(toMonth);
        dest.writeInt(toYear);
        dest.writeInt(toHours);
        dest.writeInt(toMinutes);
        dest.writeString(toAM_PM);
        dest.writeInt(fromDay);
        dest.writeInt(fromHours);
        dest.writeInt(fromSleepHours);
        dest.writeInt(fromSleepMinutes);
        dest.writeString(fromSleep_AM_PM);
        dest.writeInt(toSleepHours);
        dest.writeInt(toSleepMinutes);
        dest.writeString(toAM_PM);
        dest.writeString(repeatMin_Hour);
        dest.writeInt(repeat);
        dest.writeString(alarmTone);
      }

    public static final Parcelable.Creator<AlarmModel> CREATOR = new Parcelable.Creator<AlarmModel>() {

        public AlarmModel createFromParcel(Parcel in) {
            Log.d("parceable", "createFromParcel");
            return new AlarmModel(in);
        }

        public AlarmModel[] newArray(int size) {
            return new AlarmModel[size];
        }
    };
}
