package com.example.yasina.llapp.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yasina.llapp.model.LearningLanguagesTable;
import com.example.yasina.llapp.model.UserTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yasina on 02.03.15.
 */
public class LearningLanguagesDAO {
    public static final String TAG = "LearningLanguagesDAO";
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = { DBHelper.COLUMN_USERID_LEARN,  DBHelper.COLUMN_FIRST_LANGUAGE_ID,  DBHelper.COLUMN_SECOND_LANGUAGE_ID};

    public LearningLanguagesDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on openning database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public LearningLanguagesTable createLearningLanguagesTable(long userID, long firstLangID, long secondLangID) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_USERID_LEARN, userID);
        values.put(DBHelper.COLUMN_FIRST_LANGUAGE_ID, firstLangID);
        values.put(DBHelper.COLUMN_SECOND_LANGUAGE_ID, secondLangID);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_LEARNING_LANGUAGES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LEARNING_LANGUAGES, mAllColumns,
                null, null, null,
                null, null);
        cursor.moveToFirst();
        LearningLanguagesTable learningLanguagesTable = cursorToLearningLanguagesTable(cursor);
        cursor.close();
        return learningLanguagesTable;
    }

    public void deleteLearnLang(LearningLanguagesTable learningLanguagesTable) {
      //  long id = userTable.getUserID();

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

    public List<LearningLanguagesTable> getAllLearningLanguagesTable() {
        List<LearningLanguagesTable> listOfAllLearningLangs = new ArrayList<LearningLanguagesTable>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_LEARNING_LANGUAGES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
               LearningLanguagesTable learningLanguagesTable = cursorToLearningLanguagesTable(cursor);
                listOfAllLearningLangs.add(learningLanguagesTable);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return listOfAllLearningLangs;
    }

    public LearningLanguagesTable getUserById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_LEARNING_LANGUAGES, mAllColumns,
                DBHelper.COLUMN_USERID_LEARN + " = ?",
                new String[] { String.valueOf(id) }, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        LearningLanguagesTable learningLanguagesTable= cursorToLearningLanguagesTable(cursor);
        return learningLanguagesTable;
    }

    protected LearningLanguagesTable cursorToLearningLanguagesTable(Cursor cursor) {
        LearningLanguagesTable learningLanguagesTable = new LearningLanguagesTable();
        learningLanguagesTable.setUserID(cursor.getLong(0));
        learningLanguagesTable.setFirstLanguageID(cursor.getLong(1));
        learningLanguagesTable.setSecondLanguageID(cursor.getLong(2));
        return learningLanguagesTable;
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }
}

