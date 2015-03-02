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
    private byte[] teacher = {0,1};
    private byte[] student = {0,1};
    private byte[] selfEducated = {0,1};

    public UserTable(String login, String password, String email,byte[] teacher, byte[] student, byte[] selfEducated) {
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

    public byte[] isTeacher() {
        return teacher;
    }

    public byte[] isStudent() {
        return student;
    }

    public byte[] isSelfEducated() {
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

    public void setTeacher(byte[] teacher) {
        this.teacher = teacher;
    }

    public void setStudent(byte[] student) {
        this.student = student;
    }

    public void setSelfEducated(byte[] selfEducated) {
        this.selfEducated = selfEducated;
    }
}
