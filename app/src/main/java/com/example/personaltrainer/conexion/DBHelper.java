package com.example.personaltrainer.conexion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import  android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "entrenador.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Objetivo
        String sqlObjetivo = "CREATE TABLE Objetivo (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
        db.execSQL(sqlObjetivo);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Objetivo");
        onCreate(db);
    }

    // Inserción de Objetivo
    public long insertarObjetivo(Objetivo objetivo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", objetivo.getNombre());
        return db.insert("Objetivo", null, values);
    }

    // Actualización de Objetivo
    public int actualizarObjetivo(Objetivo objetivo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", objetivo.getNombre());
        return db.update("Objetivo", values, "id = ?", new String[]{String.valueOf(objetivo.getId())});
    }
    public ArrayList<Objetivo> getAllObjetivos(String orderBy) {
        ArrayList<Objetivo> recordsList = new ArrayList<>();
        String selectQuery = "SELECT * FROM Objetivo ORDER BY " + orderBy;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Objetivo objetivo = new Objetivo(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre"))
                );
                recordsList.add(objetivo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return recordsList;
    }

}


