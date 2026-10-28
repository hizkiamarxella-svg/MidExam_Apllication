package com.example.midexamapllication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "students";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STUDENT_ID = "student_id";
    public static final String COLUMN_AGE = "age";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_STUDENT_ID + " TEXT, " +
                COLUMN_AGE + " INTEGER)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert Data
    public boolean insertStudent(String name, String studentId, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_STUDENT_ID, studentId);
        cv.put(COLUMN_AGE, age);
        long result = db.insert(TABLE_NAME, null, cv);
        return result != -1;
    }

    // Update Data
    public boolean updateStudent(int id, String name, String studentId, int age) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_STUDENT_ID, studentId);
        cv.put(COLUMN_AGE, age);
        int result = db.update(TABLE_NAME, cv, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Delete Data
    public boolean deleteStudent(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(id)});
        return result > 0;
    }

    // Read All Data
    public List<StudentModel> getAll() {
        List<StudentModel> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                String studentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STUDENT_ID));
                int age = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_AGE));

                list.add(new StudentModel(id, name, studentId, age));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    // Method alias agar dipanggil getAllStudents() maupun getAll() sama-sama bisa
    public List<StudentModel> getAllStudents() {
        return getAll();
    }
}