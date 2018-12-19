package com.omniwyse.migration.sqlitehlper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.omniwyse.migration.User;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by AB on 7/14/2017.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "userDB";

    // user table name
    private static final String TABLE_USERS = "user";

    // user Table Columns names
    private static final String USER_ID = "uId";
    private static final String USER_NAME = "uName";
    private static final String USER_PH_NO = "uContact";

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + USER_ID + " INTEGER PRIMARY KEY," + USER_NAME + " TEXT,"
                + USER_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
// Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);

        // Create tables again
        onCreate(db);
    }

    public void addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(USER_NAME, user.getUName()); // Contact Name
        values.put(USER_PH_NO, user.getUContact()); // Contact Phone
        // Inserting Row
        db.insert(TABLE_USERS, null, values);
        db.close(); // Closing database connection
    }

    public User getUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USERS, new String[] { USER_ID,
                        USER_NAME, USER_PH_NO }, USER_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        User contact = new User(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }

    public List<User> getAllData()
    {
        List<User> list=new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_USERS;
        try (SQLiteDatabase db = this.getReadableDatabase()) {
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {
                // looping through all rows and adding to list
                if (cursor.moveToFirst()) {
                    do {
                        User obj = new User(null);
                        //only one column
                        obj.setUId(cursor.getLong(cursor.getColumnIndex(USER_ID)));
                        obj.setUContact(cursor.getString(cursor.getColumnIndex(USER_PH_NO)));
                        obj.setUName(cursor.getString(cursor.getColumnIndex(USER_NAME)));

                        //you could add additional columns here..

                        list.add(obj);
                    } while (cursor.moveToNext());
                }

            }

        }

        return list;

    }
}
