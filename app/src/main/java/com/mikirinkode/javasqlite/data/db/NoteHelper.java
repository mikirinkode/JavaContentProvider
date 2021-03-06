package com.mikirinkode.javasqlite.data.db;

import static android.provider.BaseColumns._ID;
import static com.mikirinkode.javasqlite.data.db.DatabaseContract.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NoteHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static SQLiteDatabase database;

    private NoteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    private static NoteHelper INSTANCE;

    public static NoteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NoteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    //  metode pertama adalah untuk mengambil data.
    public Cursor queryAll() {
        return database.query(
                DATABASE_TABLE,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC");
    }

    // metode untuk mengambil data dengan id tertentu.
    public Cursor queryById(String id) {
        return database.query(
                DATABASE_TABLE,
                null,
                _ID + " = ?",
                new String[]{id},
                null,
                null,
                null,
                null);
    }

    // metode untuk menyimpan data.
    public long insert(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    // metode untuk memperbarui data.
    public int update(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    //  metode untuk menghapus data.
    public int deleteById(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = " + id, null);
    }
}