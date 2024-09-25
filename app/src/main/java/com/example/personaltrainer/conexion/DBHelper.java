package com.example.personaltrainer.conexion;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import  android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "entrenador.db";
    private static final int DATABASE_VERSION = 3;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tabla Objetivo
        String sqlObjetivo = "CREATE TABLE Objetivo (id INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";
        db.execSQL(sqlObjetivo);

        // Crear tabla Cliente
        String sqlCliente = "CREATE TABLE Cliente (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "nombre TEXT, " +
                "telefono TEXT, " +
                "peso REAL, " +
                "altura REAL, " +
                "imc REAL, " +
                "idObjetivo INTEGER, " +
                "FOREIGN KEY (idObjetivo) REFERENCES Objetivo(id))";
        db.execSQL(sqlCliente);

        //Tabla categoria
        String CREATE_CATEGORY_TABLE = "CREATE TABLE Categoria ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "nombre TEXT)";
        db.execSQL(CREATE_CATEGORY_TABLE);

        //Tabla Ejercicio

        //Tabla Rutina
        // Esta es mi tabla intermedia EjercicioRutina


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS Objetivo");
        db.execSQL("DROP TABLE IF EXISTS Cliente");
        db.execSQL("DROP TABLE IF EXISTS Categoria");
        onCreate(db);
    }
}



