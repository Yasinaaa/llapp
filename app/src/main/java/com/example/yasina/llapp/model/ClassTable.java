package com.example.yasina.llapp.model;

import java.io.Serializable;

/**
 * Created by yasina on 02.03.15.
 */
public class ClassTable implements Serializable {

    public static final String TAG = "ClassTable";
    private static final long serialVersionUID = -7406082437623008161L;

    private long teacherID, studentID;

    public ClassTable() {
    }

    public ClassTable(long teacherID, long studentID)
    {
        this.teacherID = teacherID;
        this.studentID = studentID;
    }

    public long getTeacherID() {
        return teacherID;
    }

    public long getStudentID() {
        return studentID;
    }

    public void setTeacherID(long teacherID) {
        this.teacherID = teacherID;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }
}
