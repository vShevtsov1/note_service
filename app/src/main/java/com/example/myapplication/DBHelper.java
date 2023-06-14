package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "note.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Notes";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NOTE_TEXT = "noteText";
    private static final String COLUMN_DATE_CREATED = "dateCreated";
    private static final String COLUMN_COLOR = "color"; // New column for color

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NOTE_TEXT + " TEXT," +
                COLUMN_DATE_CREATED + " TEXT," +
                COLUMN_COLOR + " INTEGER" +
                ")";
        db.execSQL(createTableQuery);
    }
    public void deleteNoteById(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "=?", new String[]{String.valueOf(noteId)});
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insertNote(String noteText, String dateCreated, int color) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, noteText);
        values.put(COLUMN_DATE_CREATED, dateCreated);
        values.put(COLUMN_COLOR, color);

        long insertedId = db.insert(TABLE_NAME, null, values);
        db.close();
        return insertedId;
    }
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String noteText = cursor.getString(1);
                String dateCreated = cursor.getString(2);
                int color = cursor.getInt(3);

                Note note = new Note(id, noteText, dateCreated, color);
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }
    public void updateNoteById(int noteId, String noteText) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NOTE_TEXT, noteText);

        db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(noteId)});
        db.close();
    }
    public List<Note> findNotesByPartText(String searchTerm) {
        List<Note> notes = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_NOTE_TEXT + " LIKE '%" + searchTerm + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String noteText = cursor.getString(1);
                String dateCreated = cursor.getString(2);
                int color = cursor.getInt(3);

                Note note = new Note(id, noteText, dateCreated, color);
                notes.add(note);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notes;
    }
}
