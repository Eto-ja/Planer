package com.example.planer;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.Arrays.*;

public class dbmanager extends SQLiteOpenHelper {

    private static String dbname = "users_planer.db";
    static final String COLUMN_ID = "userId";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_PASSWORD = "password";
    static final String ELEMENTAL = "elemental";

    public dbmanager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String gry = "CREATE TABLE users (\n" +
                "    userId    INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    email     VARCHAR (254) NOT NULL\n" +
                "                            UNIQUE,\n" +
                "    password  VARCHAR (128) NOT NULL,\n" +
                "    elemental VARCHAR (128) NOT NULL\n" +
                ");";
        db.execSQL(gry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }

    public int add(String email, String password){
        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // курсор
        Cursor c = null;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("password", password);
        selection = "email = ?";
        selectionArgs = new String[] { email };
        c = db.query("users", null, selection, selectionArgs, null, null,
                null);

        if (c.getCount() == 1){
            return 1;
        }
        c.close();

        cv.put("elemental", "облако");

        long res = db.insert("users", null, cv);

        db.close();

        if (res==-1){
            return -1;
        }
        else{
//            Cursor cursor = db.query("users",  // Имя таблицы
//                    new String[] {COLUMN_ID}, // Имена столбцов (в данном случае, user_id)
//                    "email = ?",  // Условие для запроса (например, по имени пользователя)
//                    new String[] {email}, // Значение для условия
//                    null, null, null);
//            int userId = cursor.getInt(0);
//            cursor.close();
//            db.close();
            return 0;
        }
    }

    public int sign_in(String email, String password){
        // переменные для query
        String[] columns = null;
        String selection = null;
        String[] selectionArgs = null;
        String groupBy = null;
        String having = null;
        String orderBy = null;

        // курсор
        Cursor c = null;

        SQLiteDatabase db = this.getReadableDatabase();
        selection = "email = ? and password = ?";
        selectionArgs = new String[] { email, password };
        c = db.query("users", null, selection, selectionArgs, null, null,
                null);

        if (c.getCount() == 0){
            c.close();
            db.close();
            return -1;
        }
        else {
            c.close();
            db.close();
            return 0;
        }
    }

    public String fortune(String email){
        String user = email;
        if(email.equals("help")){
            return user;
        }
        else{
            return "ok";
        }
    }
}
