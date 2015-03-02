package com.example.yasina.llapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.model.ClassTable;
import com.example.yasina.llapp.model.LearningLanguagesTable;
import com.example.yasina.llapp.model.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 02.03.15.
 */
public class ClassDAO {
    public static final String TAG = "ClassDAO";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {DBHelper.COLUMN_TEACHER_ID, DBHelper.COLUMN_STUDENT_ID};

    public ClassDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public ClassTable createClassTable(long teacherID, long studentID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TEACHER_ID, teacherID);
        values.put(DBHelper.COLUMN_STUDENT_ID, studentID);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_CLASS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLASS, mAllColumns,
                null, null, null,
                null, null);

        cursor.moveToFirst();
        ClassTable classTable = cursorToClassTable(cursor);
        cursor.close();
        return classTable;
    }

    public void deleteClassByTeacheaID(ClassTable classTable) {
        long id = classTable.getTeacherID();
        mDatabase.delete(DBHelper.TABLE_CLASS, DBHelper.COLUMN_TEACHER_ID + " = " + id, null);
    }

    public void deleteClassByStudentID(ClassTable classTable) {
        long id = classTable.getStudentID();
        mDatabase.delete(DBHelper.TABLE_CLASS, DBHelper.COLUMN_STUDENT_ID + " = " + id, null);
    }

    public List<ClassTable> getAllClassTable() {
        List<ClassTable> listOfAllClasses = new ArrayList<ClassTable>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLASS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                ClassTable classTable = cursorToClassTable(cursor);
                listOfAllClasses.add(classTable);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listOfAllClasses;
    }

    public ClassTable getTeacherIdByStudentId(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLASS, mAllColumns,
                DBHelper.COLUMN_STUDENT_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ClassTable classTable= cursorToClassTable(cursor);
        return classTable;
    }

    public ClassTable getStudentIdByTeacherId(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_CLASS, mAllColumns,
                DBHelper.COLUMN_TEACHER_ID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        ClassTable classTable= cursorToClassTable(cursor);
        return classTable;
    }

    protected ClassTable cursorToClassTable(Cursor cursor) {
        ClassTable classTable = new ClassTable();
        classTable.setTeacherID(cursor.getLong(0));
        classTable.setStudentID(cursor.getLong(1));
        return classTable;
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}


