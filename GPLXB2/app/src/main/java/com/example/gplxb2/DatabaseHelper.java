package com.example.gplxb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final String TABLE_NAME = "incorrect_answers";
    private static final String COL_1 = "ID"; // Tạo cột ID cho bảng
    private static final String COL_2 = "QUESTION_ID"; // Cột ID của câu hỏi

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, QUESTION_ID INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertIncorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, questionId);
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
    }

    public void deleteCorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COL_2 + " = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    public ArrayList<Integer> getIncorrectAnswers() {
        ArrayList<Integer> incorrectAnswers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                incorrectAnswers.add(cursor.getInt(1)); // Lấy ID câu hỏi
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return incorrectAnswers;
    }
}

