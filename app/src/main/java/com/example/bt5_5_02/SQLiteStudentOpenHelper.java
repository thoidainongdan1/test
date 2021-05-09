package com.example.bt5_5_02;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.bt5_5_02.model.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteStudentOpenHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 1;
    public SQLiteStudentOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE student(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "gender BOOLEAN," +
                "mark REAL)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long addStudent(Student student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("gender", student.isGender());
        contentValues.put("mark", student.getMark());
        SQLiteDatabase db = getWritableDatabase();
        return db.insert("student", null, contentValues);
    }

    public List<Student> getAll() {
        List<Student> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("student", null, null, null,
                null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            boolean gender = cursor.getInt(2) == 1;
            double mark = cursor.getDouble(3);
            list.add(new Student(id, name, gender, mark));
        }

        return list;
    }

    public Student getStudentById(int id) {
        String sql = "SELECT * FROM student where id = ?";
        String args[] = {Integer.toString(id)};
        Student student = null;
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(sql, args);
        if(cursor != null && cursor.moveToNext()) {
            String name = cursor.getString(1);
            boolean gender = cursor.getInt(2) == 1;
            double mark = cursor.getDouble(3);
            student = new Student(id, name, gender, mark);
        }

        return student;
    }

    public int deleteStudent(int id) {
        String whereClause = "id = ?";
        String whereArgs[] = {Integer.toString(id)};
        SQLiteDatabase db = getWritableDatabase();
        return db.delete("student", whereClause, whereArgs);
    }

    public int updateStudent(Student student) {
        String whereClause = "id = ?";
        String whereArgs[] = {Integer.toString(student.getId())};

        ContentValues contentValues = new ContentValues();
        contentValues.put("name", student.getName());
        contentValues.put("gender", student.isGender());
        contentValues.put("mark", student.getMark());

        SQLiteDatabase db = getWritableDatabase();
        return db.update("student", contentValues, whereClause, whereArgs);
    }

    public List<Student> searchByName(String key) {
        List<Student> list = new ArrayList<>();
        String whereClause = "name like ?";
        String whereArgs[] = {"%"+key+"%"};
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query("student", null, whereClause, whereArgs,
                null, null, null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            boolean gender = cursor.getInt(2) == 1;
            double mark = cursor.getDouble(3);
            list.add(new Student(id, name, gender, mark));
        }

        return list;
    }
}
