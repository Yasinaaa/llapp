package com.example.yasina.llapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by yasina on 02.03.15.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    //columns of the userTable
    public static final String TABLE_USER = "users";
    public static final String COLUMN_USERID = "userID";
    public static final String COLUMN_LOGIN = "login";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TEACHER ="teacher";
    public static final String COLUMN_STUDENT = "student";
    public static final String COLUMN_SELF_EDUCATED = "selfEducated";

    //columns of the ClassTable
    public static final String TABLE_CLASS = "class";
    public static final String COLUMN_TEACHER_ID = "teacherID";
    public static final String COLUMN_STUDENT_ID = "studentID";

    //columns of the LanguageTable
    public static final String TABLE_LANGUAGE = "language";
    public static final String COLUMN_LANGUAGE_ID = "languageID";
    public static final String COLUMN_LANGUAGE_NAME = "languageName";

    //columns of the learningLanguagesTable
    public static final String TABLE_LEARNING_LANGUAGES = "learningLanguages";
    public static final String COLUMN_USERID_LEARN = "userID";
    public static final String COLUMN_FIRST_LANGUAGE_ID = "firstLanguageID";
    public static final String COLUMN_SECOND_LANGUAGE_ID = "secondtLanguageID";

    private static final String DATABASE_NAME = "first.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USERID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LOGIN + " TEXT NOT NULL, "
            + COLUMN_PASSWORD + " TEXT NOT NULL, "
            + COLUMN_EMAIL + " TEXT NOT NULL, "
            + COLUMN_TEACHER + " BOOL, "
            + COLUMN_STUDENT + " BOOL, "
            + COLUMN_SELF_EDUCATED + " BOOL"
            +");";

    private static final String SQL_CREATE_TABLE_LEARNING_LANGUAGES= "CREATE TABLE " + TABLE_LEARNING_LANGUAGES + "("
            + COLUMN_USERID_LEARN + " INTEGER PRIMARY, "
            + COLUMN_FIRST_LANGUAGE_ID + " INTEGER NOT NULL, "
            + COLUMN_SECOND_LANGUAGE_ID + " INTEGER NOT NULL"
            +");";

    private static final String SQL_CREATE_TABLE_CLASS= "CREATE TABLE " + TABLE_CLASS + "("
            + COLUMN_TEACHER_ID + " INTEGER PRIMARY KEY, "
            + COLUMN_STUDENT_ID + " INTEGER NOT NULL"
            +");";

    private static final String SQL_CREATE_TABLE_LANGUAGE= "CREATE TABLE " + TABLE_LANGUAGE + "("
            + COLUMN_LANGUAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_LANGUAGE_NAME + " INTEGER NOT NULL"
            +");";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_USERS);
        database.execSQL(SQL_CREATE_TABLE_CLASS);
        database.execSQL(SQL_CREATE_TABLE_LANGUAGE);
        database.execSQL(SQL_CREATE_TABLE_LEARNING_LANGUAGES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLASS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEARNING_LANGUAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUAGE);

        onCreate(db);
    }
}
