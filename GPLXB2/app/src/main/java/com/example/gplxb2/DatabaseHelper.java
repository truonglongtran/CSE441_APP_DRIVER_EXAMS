package com.example.gplxb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "quiz.db";
    private static final int DATABASE_VERSION = 2; // Tăng số phiên bản lên 2

    // Bảng cho câu trả lời sai
    private static final String TABLE_INCORRECT_ANSWERS = "incorrect_answers";
    private static final String COL_1 = "ID"; // Tạo cột ID cho bảng
    private static final String COL_2 = "QUESTION_ID"; // Cột ID của câu hỏi

    // Bảng cho trạng thái bài thi
    private static final String TABLE_EXAM_RESULTS = "exam_results"; // Bảng mới cho trạng thái bài thi
    private static final String COL_EXAM_INDEX = "EXAM_INDEX"; // Mã bài thi
    private static final String COL_EXAM_STATUS = "EXAM_STATUS"; // Trạng thái bài thi

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION); // Sử dụng số phiên bản mới
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng incorrect_answers
        db.execSQL("CREATE TABLE " + TABLE_INCORRECT_ANSWERS + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_2 + " INTEGER)");

        // Tạo bảng exam_results
        db.execSQL("CREATE TABLE " + TABLE_EXAM_RESULTS + " ("
                + COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COL_EXAM_INDEX + " INTEGER UNIQUE, " // UNIQUE để mỗi examIndex chỉ có 1 trạng thái
                + COL_EXAM_STATUS + " INTEGER)");

        // Khởi tạo trạng thái bài thi với mặc định là 0 cho tất cả examIndex từ 0 đến 17
        for (int i = 0; i <= 17; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EXAM_INDEX, i);
            contentValues.put(COL_EXAM_STATUS, 0); // Mặc định là 0
            db.insert(TABLE_EXAM_RESULTS, null, contentValues);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INCORRECT_ANSWERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAM_RESULTS);
        onCreate(db); // Tạo lại bảng mới
    }

    // Phương thức thêm câu trả lời sai
    public void insertIncorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, questionId);
        db.insert(TABLE_INCORRECT_ANSWERS, null, contentValues);
        db.close();
    }

    // Phương thức xóa câu trả lời đúng
    public void deleteCorrectAnswer(int questionId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INCORRECT_ANSWERS, COL_2 + " = ?", new String[]{String.valueOf(questionId)});
        db.close();
    }

    // Phương thức lấy danh sách các câu trả lời sai
    public ArrayList<Integer> getIncorrectAnswers() {
        ArrayList<Integer> incorrectAnswers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_INCORRECT_ANSWERS, null);
        if (cursor.moveToFirst()) {
            do {
                incorrectAnswers.add(cursor.getInt(1)); // Lấy ID câu hỏi
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return incorrectAnswers;
    }

    // Phương thức thêm hoặc cập nhật trạng thái bài thi
    public void insertOrUpdateExamResult(int examIndex, int examStatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_EXAM_INDEX, examIndex);
        contentValues.put(COL_EXAM_STATUS, examStatus);

        // Thêm mới hoặc cập nhật nếu đã tồn tại examIndex
        db.insertWithOnConflict(TABLE_EXAM_RESULTS, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    // Phương thức lấy trạng thái bài thi dựa vào examIndex
    public Integer getExamStatus(int examIndex) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EXAM_RESULTS,
                new String[]{COL_EXAM_STATUS},
                COL_EXAM_INDEX + "=?",
                new String[]{String.valueOf(examIndex)},
                null, null, null);

        Integer examStatus = null;
        if (cursor.moveToFirst()) {
            examStatus = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return examStatus; // Trả về null nếu không tìm thấy
    }
    public void resetData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_INCORRECT_ANSWERS, null, null); // Xóa tất cả dữ liệu trong bảng incorrect_answers
        db.delete(TABLE_EXAM_RESULTS, null, null); // Xóa tất cả dữ liệu trong bảng exam_results

        // Khởi tạo lại dữ liệu mặc định cho bảng exam_results
        for (int i = 0; i <= 17; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COL_EXAM_INDEX, i);
            contentValues.put(COL_EXAM_STATUS, 0); // Mặc định là 0
            db.insert(TABLE_EXAM_RESULTS, null, contentValues);
        }
        db.close();
    }
}
