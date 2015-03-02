package com.example.yasina.llapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.model.ClassTable;
import com.example.yasina.llapp.model.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 02.03.15.
 */
public class UserDAO {
    public static final String TAG = "UserDAO";

    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_USERID,DBHelper.COLUMN_LOGIN, DBHelper.COLUMN_PASSWORD,
            DBHelper.COLUMN_EMAIL, DBHelper.COLUMN_TEACHER, DBHelper.COLUMN_STUDENT, DBHelper.COLUMN_SELF_EDUCATED};

    public UserDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public UserTable createUserTable(String login, String password, String email, boolean teacher,
                                     boolean student, boolean selfEducated) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_LOGIN,login);
        values.put(DBHelper.COLUMN_PASSWORD,password);
        values.put(DBHelper.COLUMN_EMAIL,email);
        values.put(DBHelper.COLUMN_TEACHER,teacher);
        values.put(DBHelper.COLUMN_STUDENT,student);
        values.put(DBHelper.COLUMN_SELF_EDUCATED,selfEducated);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_USER, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_USER, mAllColumns,
                DBHelper.COLUMN_USERID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        UserTable newUser = cursorToUserTable(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteUser(UserTable userTable) {
        long id = userTable.getUserID();

       /* EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        }*/

       // ClassDAO classDAO = new ClassDAO(mContext);
       // List<ClassTable> listClass = classDAO

     /*   System.out.println("the deleted company has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_COMPANIES, DBHelper.COLUMN_COMPANY_ID
                + " = " + id, null);*/
    }

    public List<UserTable> getAllUsers() {
        List<UserTable> listUsers = new ArrayList<UserTable>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_USER, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                UserTable userTable = cursorToUserTable(cursor);
                listUsers.add(userTable);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listUsers;
    }

    public UserTable getUserById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_USER, mAllColumns,
                DBHelper.COLUMN_USERID + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        UserTable userTable= cursorToUserTable(cursor);
        return userTable;
    }

    protected UserTable cursorToUserTable(Cursor cursor) {
        UserTable userTable = new UserTable();
        userTable.setUserID(cursor.getLong(0));
        userTable.setLogin(cursor.getString(1));
        userTable.setPassword(cursor.getString(2));
        userTable.setEmail(cursor.getString(3));
        userTable.setTeacher(cursor.getBlob(4));
        userTable.setStudent(cursor.getBlob(5));
        userTable.setSelfEducated(cursor.getBlob(6));
        return userTable;
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}
