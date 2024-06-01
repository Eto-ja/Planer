package com.example.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class dbmanager_task extends SQLiteOpenHelper {
    private static final String DBNAME = "users_planer.db";
    static final String COLUMN_ID = "taskId";
    static final String COLUMN_TYPE = "type";
    static final String COLUMN_DESCRIPTION = "description";
    static final String COLUMN_EMAIL = "email";
    static final String COLUMN_TITLE = "title";
    static final String COLUMN_START = "start";
    static final String COLUMN_STOP = "stop";

    public dbmanager_task(@Nullable Context context) {
        super(context, DBNAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String gry = "CREATE TABLE tasks (\n" +
                "    taskId    INTEGER       PRIMARY KEY AUTOINCREMENT,\n" +
                "    type  VARCHAR (64) NOT NULL,\n" +
                "    description VARCHAR (1024),\n" +
                "    email     VARCHAR (254) NOT NULL,\n" +
                "    title       VARCHAR (64)   NOT NULL,\n" +
                "    start        VARCHAR (64)      NOT NULL,\n" +
                "    stop        VARCHAR (64)      NOT NULL\n" +
                ");";
        db.execSQL(gry);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(db);
    }

    public int add(String title, String description, String stop, String type, String email){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("email", email);
        cv.put("title", title);
        cv.put("description", description);
        cv.put("start", "31.05.24");
        cv.put("stop", stop);
        cv.put("type", type);

        long res = db.insert("tasks", null, cv);

        db.close();

        if (res==-1){
            return -1;
        }
        else{
            return 0;
        }
    }
}
