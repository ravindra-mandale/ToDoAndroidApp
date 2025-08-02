package com.programminginmyway.todoappnew.Database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.programminginmyway.todoappnew.Model.Users;
import java.util.ArrayList;
import java.util.List;

public class UserSQLiteOpenHelper extends SQLiteOpenHelper {
    //Database version
    private static final int DATABASE_VERSION = 1;
    //Database name
    private static final String DATABASE_NAME = "USERDB.db";
    //Table details such as Table name, column names
    private static final String TABLE_USERS = "USERS";
    private static final String USER_ID = "USERID";
    private static final String KEY_EMAILID = "EMAILID";
    private static final String KEY_FULLNAME = "FULLNAME";
    private static final String KEY_MOBILENO = "MOBILENO";
    private static final String KEY_PASSWORD = "PASSWORD";
    //private static final String KEY_CONFIRM_PASSWORD = "CONFIRM_PASSWORD";

    public UserSQLiteOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + "INTEGER PRIMARY KEY,"
                + KEY_EMAILID + " TEXT, "
                + KEY_FULLNAME + " TEXT,"
                + KEY_MOBILENO + " TEXT,"
                + KEY_PASSWORD + " TEXT)";

        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        // Create table again
        onCreate(db);
    }

    //insert user records into table
    public boolean insertUser(Users users) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        //Insert values into ContentValues first using key-value pair
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_EMAILID, users.getEmailId());
        contentValues.put(KEY_FULLNAME, users.getFullName());
        contentValues.put(KEY_MOBILENO, users.getMobileNo());
        contentValues.put(KEY_PASSWORD, users.getPassword());
        //contentValues.put(KEY_CONFIRM_PASSWORD, users.getConfirmPassword());
        //Insert row for User into table
        long result = sqLiteDatabase.insert(TABLE_USERS, null, contentValues);
        if (result == -1) {
            return false;
        } else {
            sqLiteDatabase.close();
            return true;
        }
    }

    @SuppressLint("Range")
    public List<Users> checkAllUsers() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        List<Users> usersList = new ArrayList<Users>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TABLE_USERS, null);
        if (cursor.moveToFirst()) {
            do {
                Users users = new Users();
                users.setEmailId(cursor.getString(cursor.getColumnIndex(KEY_EMAILID)));
                users.setPassword(cursor.getString(cursor.getColumnIndex(KEY_PASSWORD)));
                usersList.add(users);
            } while (cursor.moveToNext());
        }
        return usersList;
    }
}
