package com.example.yasina.llapp.model;

/**
 * Created by yasina on 02.03.15.
 */

import java.io.Serializable;

public class UserTable {

    public static final String TAG = "UserTable";
    private static final long serialVersionUID = -7406082437623008161L;

    private long userID;
    private String login, password, email;
    private boolean teacher, student, selfEducated;

    public UserTable(String login, String password, String email, boolean teacher, boolean student, boolean selfEducated) {
        this.login = login;
        this.password = password;
        this.email = email;
        this.teacher = teacher;
        this.student = student;
        this.selfEducated = selfEducated;
    }

    public UserTable(){}

    public long getUserID() {
        return userID;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public boolean isTeacher() {
        return teacher;
    }

    public boolean isStudent() {
        return student;
    }

    public boolean isSelfEducated() {
        return selfEducated;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTeacher(boolean teacher) {
        this.teacher = teacher;
    }

    public void setStudent(boolean student) {
        this.student = student;
    }

    public void setSelfEducated(boolean selfEducated) {
        this.selfEducated = selfEducated;
    }
}
