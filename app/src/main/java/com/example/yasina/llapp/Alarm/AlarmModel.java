package com.example.yasina.llapp.Alarm;


import java.io.Serializable;

public class AlarmModel  implements Serializable {

    private static final long serialVersionUID = -7406082437623008161L;

    private int id,
    fromDay, fromMonth, fromYear, fromHours, fromMinutes ,fromAM_PM,
    toDay, toMonth, toYear, toHours, toMinutes, toAM_PM,
    fromSleepHours, fromSleepMinutes, fromSleep_AM_PM,
    toSleepHours, toSleepMinutes, toSleep_AM_PM,
    repeat;
    private String themeName;

    public AlarmModel(int fromDay, int fromYear, int fromMonth, int fromHours, int fromMinutes, int fromAM_PM, int toDay, int toMonth, int toHours, int toYear, int toMinutes, int toAM_PM, int fromSleepHours, int fromSleepMinutes, int fromSleep_AM_PM, int toSleepHours, int toSleepMinutes, int toSleep_AM_PM, int repeat, String themeName) {
        this.fromDay = fromDay;
        this.fromYear = fromYear;
        this.fromMonth = fromMonth;
        this.fromHours = fromHours;
        this.fromMinutes = fromMinutes;
        this.fromAM_PM = fromAM_PM;
        this.toDay = toDay;
        this.toMonth = toMonth;
        this.toHours = toHours;
        this.toYear = toYear;
        this.toMinutes = toMinutes;
        this.toAM_PM = toAM_PM;
        this.fromSleepHours = fromSleepHours;
        this.fromSleepMinutes = fromSleepMinutes;
        this.fromSleep_AM_PM = fromSleep_AM_PM;
        this.toSleepHours = toSleepHours;
        this.toSleepMinutes = toSleepMinutes;
        this.toSleep_AM_PM = toSleep_AM_PM;
        this.repeat = repeat;
        this.themeName = themeName;
    }

    public AlarmModel() {
    }

    public String getThemeName() {
        return themeName;
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

    public int getToSleep_AM_PM() {
        return toSleep_AM_PM;
    }

    public void setToSleep_AM_PM(int toSleep_AM_PM) {
        this.toSleep_AM_PM = toSleep_AM_PM;
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

    public int getFromSleep_AM_PM() {
        return fromSleep_AM_PM;
    }

    public void setFromSleep_AM_PM(int fromSleep_AM_PM) {
        this.fromSleep_AM_PM = fromSleep_AM_PM;
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

    public int getFromAM_PM() {
        return fromAM_PM;
    }

    public void setFromAM_PM(int fromAM_PM) {
        this.fromAM_PM = fromAM_PM;
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

    public int getToAM_PM() {
        return toAM_PM;
    }

    public void setToAM_PM(int toAM_PM) {
        this.toAM_PM = toAM_PM;
    }
}
