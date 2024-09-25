package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;

import java.util.ArrayList;

public class Objetivo {
    private int id;
    private String nombre;
    private DBHelper dbHelper;

    // Constructor vacío
    public Objetivo() {
    }

    // Constructor que recibe el contexto para inicializar la conexión a la base de datos
    public Objetivo(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Constructor con datos del objetivo
    public Objetivo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return nombre;  // Mostrar solo el nombre en el Spinner
    }

    // Insertar un objetivo en la base de datos
    public boolean insertarObjetivo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        long resultado = db.insert("objetivo", null, values);
        db.close();
        return resultado != -1;
    }

    // Actualizar un objetivo en la base de datos
    public void actualizarObjetivo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);

        db.update("Objetivo", values, "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Eliminar un objetivo de la base de datos
    public void eliminarObjetivo() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Objetivo", "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Obtener un objetivo por ID
    public Objetivo obtenerObjetivoPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Objetivo WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Objetivo objetivo = new Objetivo(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nombre"))
            );
            cursor.close();
            db.close();
            return objetivo;
        }

        db.close();
        return null;
    }

    // Obtener todos los objetivos
    public ArrayList<Objetivo> obtenerTodosLosObjetivos() {
        ArrayList<Objetivo> listaObjetivos = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Objetivo ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Objetivo objetivo = new Objetivo(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre"))
                );
                listaObjetivos.add(objetivo);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaObjetivos;
    }

}