package com.example.yasina.llapp.Alarm;


import java.io.Serializable;

public class AlarmModel  implements Serializable {

    private static final long serialVersionUID = -7406082437623008161L;

    private int id,
    fromDay, fromMonth, fromYear, fromHours, fromMinutes ,
    toDay, toMonth, toYear, toHours, toMinutes,
    fromSleepHours, fromSleepMinutes,
    toSleepHours, toSleepMinutes,
    repeat;
    private String themeName,fromAM_PM, toAM_PM, fromSleep_AM_PM,  toSleep_AM_PM, repeatMin_Hour;


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
}
