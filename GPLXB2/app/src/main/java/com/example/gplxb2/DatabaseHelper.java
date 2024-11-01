package com.example.gplxb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database name and version
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 2;

    // Table and column names for incorrect answers
    private static final String TABLE_INCORRECT_ANSWERS = "incorrect_answers";
    private static final String COL_1 = "ID"; // Primary key
    private static final String COL_2 = "QUESTION_ID"; // Foreign key referring to questions

    // Table and column names for exam results
    private static final String TABLE_EXAM_RESULTS = "exam_results";
    private static final String COL_EXAM_INDEX = "EXAM_INDEX"; // Unique exam index
    private static final String COL_EXAM_STATUS = "EXAM_STATUS"; // Status of the exam

    // Constructor to create or open the database
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create table for storing incorrect answers
        db.execSQL("CREATE TABLE " + TABLE_INCORRECT_ANSWERS + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " INTEGER)");

        // Create table for storing exam results
        db.execSQL("CREATE TABLE " + TABLE_EXAM_RESULTS + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EXAM_INDEX + " INTEGER UNIQUE, "
                + COL_EXAM_STATUS + " INTEGER)");

        // Insert initial data for exam results with default status 0
        for (int i = 0; i <= 17; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EXAM_INDEX, i);
            contentValues.put(COL_EXAM_STATUS, 0); // Default status is 0 (not completed)
            db.insert(TABLE_EXAM_RESULTS, null, contentValues);
        }
    }

    // Called when the database needs to be upgraded
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop existing tables if they exist
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCORRECT_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM_RESULTS);
        // Recreate the tables
        onCreate(db);
    }

    // Method to insert an incorrect answer into the database
    public void insertIncorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, questionId); // Add question ID
        db.insert(TABLE_INCORRECT_ANSWERS, null, contentValues); // Insert into table
        db.close();
    }

    // Method to delete a correct answer from the incorrect answers table
    public void deleteCorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INCORRECT_ANSWERS, COL_2 + " = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    // Method to retrieve all incorrect answers from the database
    public ArrayList<Integer> getIncorrectAnswers() {
        ArrayList<Integer> incorrectAnswers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCORRECT_ANSWERS, null);
        // Iterate through cursor and collect question IDs
        if (cursor.moveToFirst()) {
            do {
                incorrectAnswers.add(cursor.getInt(1)); // Get QUESTION_ID from column index 1
            } while (cursor.moveToNext());
        }
        cursor.close(); // Close cursor
        db.close(); // Close database
        return incorrectAnswers; // Return list of incorrect answers
    }

    // Method to insert or update the exam result in the database
    public void insertOrUpdateExamResult(int examIndex, int examStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EXAM_INDEX, examIndex); // Add exam index
        contentValues.put(COL_EXAM_STATUS, examStatus); // Add exam status
        // Insert or replace the existing exam result
        db.insertWithOnConflict(TABLE_EXAM_RESULTS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Method to retrieve the exam status based on the exam index
    public Integer getExamStatus(int examIndex) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXAM_RESULTS,
                new String[]{COL_EXAM_STATUS}, // Select exam status
                COL_EXAM_INDEX + "=?",
                new String[]{String.valueOf(examIndex)}, // Where clause
                null, null, null);

        Integer examStatus = null;
        // Get the exam status if the cursor has results
        if (cursor.moveToFirst()) {
            examStatus = cursor.getInt(0); // Get status from column index 0
        }
        cursor.close(); // Close cursor
        db.close(); // Close database
        return examStatus; // Return exam status
    }


    // Method to reset the data in the database
    public void resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INCORRECT_ANSWERS, null, null); // Delete all incorrect answers
        db.delete(TABLE_EXAM_RESULTS, null, null); // Delete all exam results

        // Re-insert initial data for exam results with default status 0
        for (int i = 0; i <= 17; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EXAM_INDEX, i);
            contentValues.put(COL_EXAM_STATUS, 0);
            db.insert(TABLE_EXAM_RESULTS, null, contentValues);
        }
        db.close();
    }
}
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        