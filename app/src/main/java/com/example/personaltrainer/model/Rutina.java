package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;

import java.util.ArrayList;

public class Rutina {
    private int id;
    private String nombre;
    private String descripcion;
    private int idUsuario; // Llave foránea que conecta a un usuario
    private DBHelper dbHelper;

    // Constructor vacío
    public Rutina() {}

    // Constructor para inicializar el DBHelper
    public Rutina(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Constructor con todos los parámetros
    public Rutina(int id, String nombre, String descripcion, int idUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.idUsuario = idUsuario;
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    // Insertar una rutina en la base de datos
    public void insertarRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        values.put("descripcion", this.descripcion);
        values.put("idUsuario", this.idUsuario);

        db.insert("Rutina", null, values);
        db.close();
    }

    // Actualizar una rutina en la base de datos
    public void actualizarRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        values.put("descripcion", this.descripcion);
        values.put("idUsuario", this.idUsuario);

        db.update("Rutina", values, "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Eliminar una rutina de la base de datos
    public void eliminarRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Rutina", "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Obtener una rutina por ID
    public Rutina obtenerRutinaPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Rutina WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Rutina rutina = new Rutina(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("descripcion")),
                    cursor.getInt(cursor.getColumnIndex("idUsuario"))
            );
            cursor.close();
            db.close();
            return rutina;
        }

        db.close();
        return null;
    }

    // Obtener todas las rutinas
    public ArrayList<Rutina> obtenerTodasLasRutinas() {
        ArrayList<Rutina> listaRutinas = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Rutina ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Rutina rutina = new Rutina(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("descripcion")),
                        cursor.getInt(cursor.getColumnIndex("idUsuario"))
                );
                listaRutinas.add(rutina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaRutinas;
    }
}

