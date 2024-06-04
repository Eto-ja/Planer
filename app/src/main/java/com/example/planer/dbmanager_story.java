package com.example.planer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class dbmanager_story extends SQLiteOpenHelper {
    private static final String DBNAME = "users_planer.db";
    private static int DBVERSION = 1;
    static final String COLUMN_ID = "storyId";
    static final String COLUMN_COUNT = "count";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_DATE = "date";
    static final String COLUMN_TITLE = "title";

    public dbmanager_story(@Nullable Context context) {
        super(context, DBNAME, null, DBVERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String gry = "CREATE TABLE tasks (\n" +
                "    storyId    INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    count INTEGER ,\n" +
                "    email     VARCHAR (254) NOT NULL,\n" +
                "    date       VARCHAR (64)   NOT NULL,\n" +
                "    title        VARCHAR (1024)      NOT NULL\n" +
                ");";
        db.execSQL(gry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }
}
