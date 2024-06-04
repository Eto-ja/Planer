package com.example.planer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

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

    protected List<String> show_tasks(String email){
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.query("tasks", new String[] { COLUMN_TITLE },
                "email = ? and type = ?",
                new String[] { email, "Задача" }, null, null, null);
        List<String> titles = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                int index = c.getColumnIndex(COLUMN_TITLE);
                String title1 = c.getString(index);
                titles.add(title1);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return titles;
    }

    protected List<String> show_targets(String email){
        Cursor c = null;
        SQLiteDatabase db = this.getWritableDatabase();
        c = db.query("tasks", new String[] { COLUMN_TITLE },
                "email = ? and type = ?",
                new String[] { email, "Цель / привычка" }, null, null, null);
        List<String> titles = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                int index = c.getColumnIndex(COLUMN_TITLE);
                String title1 = c.getString(index);
                titles.add(title1);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return titles;
    }

    protected List<String> show_date(String email, String date){
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.query("tasks", new String[] { COLUMN_TITLE },
                "email = ? and type = ? and stop = ?",
                new String[] { email, "Задача", date}, null, null, null);
        List<String> titles = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                String title = c.getString(c.getColumnIndexOrThrow(COLUMN_TITLE));
                titles.add(title);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return titles;
    }

    protected void del_task(String email, String title){
        SQLiteDatabase db = getWritableDatabase();

        long rowCount = db.delete("tasks", "email = ? and title = ?",
                new String[] { email, title });

        db.close();
    }

    protected String[] edit_task(String email, String title){
        Cursor c = null;
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.query("tasks", new String[] { COLUMN_TYPE, COLUMN_DESCRIPTION, COLUMN_STOP },
                "email = ? and title = ?",
                new String[] { email, title }, null, null, null);
        String [] titles = new String[3];
        if (c.moveToFirst()) {
            String type = c.getString(c.getColumnIndexOrThrow(COLUMN_TYPE));
            String description = c.getString(c.getColumnIndexOrThrow(COLUMN_DESCRIPTION));
            if (description.equals("")){
                description = "Описание к задаче";
            }
            String date = c.getString(c.getColumnIndexOrThrow(COLUMN_STOP));
            titles = new String[] { type, description, date};
        }
        c.close();
        db.close();
        return titles;
    }
}
