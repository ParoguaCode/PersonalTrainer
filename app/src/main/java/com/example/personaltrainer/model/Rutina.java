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
    private String tipo;
    private int idCliente; // Llave foránea que conecta a un cliente
    private DBHelper dbHelper;

    // Constructor vacío
    public Rutina() {}

    // Constructor para inicializar el DBHelper
    public Rutina(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Constructor con todos los parámetros
    public Rutina(int id, String tipo, int idCliente) {
        this.id = id;
        this.tipo = tipo;
        this.idCliente = idCliente;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return tipo;
    }

    public void setNombre(String tipo) {
        this.tipo = tipo;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    // Insertar una rutina en la base de datos
    public boolean insertarRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("tipo", this.tipo); // Ajusta los campos según tu modelo
        valores.put("idCliente", this.idCliente);

        long resultado = db.insert("Rutina", null, valores);
        return resultado != -1; // Devuelve true si se insertó correctamente
    }

    // Actualizar una rutina en la base de datos
    public void actualizarRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipo", this.tipo);
        values.put("idCliente", this.idCliente);

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
                    cursor.getString(cursor.getColumnIndex("tipo")),
                    cursor.getInt(cursor.getColumnIndex("idCliente"))
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
                        cursor.getString(cursor.getColumnIndex("tipo")),
                        cursor.getInt(cursor.getColumnIndex("idCliente"))
                );
                listaRutinas.add(rutina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaRutinas;
    }
}

