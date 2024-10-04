package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;

import java.util.ArrayList;

public class EjercicioRutina {
    private int idRutina;
    private int idEjercicio;
    private int repeticion;
    private DBHelper dbHelper;

    // Constructor vacío
    public EjercicioRutina(Ejercicio ejercicioSeleccionado, int repeticiones) {}

    // Constructor para inicializar el DBHelper
    public EjercicioRutina(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Constructor con todos los parámetros
    public EjercicioRutina(int idRutina, int idEjercicio, int repeticion) {
        this.idRutina = idRutina;
        this.idEjercicio = idEjercicio;
        this.repeticion = repeticion;
    }

    // Getters y Setters
    public int getIdRutina() {
        return idRutina;
    }

    public void setIdRutina(int idRutina) {
        this.idRutina = idRutina;
    }

    public int getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(int idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public int getRepeticion() {
        return repeticion;
    }

    public void setRepeticion(int repeticion) {
        this.repeticion = repeticion;
    }
    // Método para insertar una relación de ejercicio y rutina en la base de datos
    public void insertarEjercicioRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idRutina", this.idRutina);
        values.put("idEjercicio", this.idEjercicio);
        values.put("repeticion", this.repeticion);

        db.insert("EjercicioRutina", null, values);
        db.close();
    }

    // Método para actualizar la relación en la base de datos
    public void actualizarEjercicioRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("idRutina", this.idRutina);
        values.put("idEjercicio", this.idEjercicio);
        values.put("repeticion", this.repeticion);

        db.update("EjercicioRutina", values, "idRutina = ? AND idEjercicio = ?",
                new String[]{String.valueOf(this.idRutina), String.valueOf(this.idEjercicio)});
        db.close();
    }

    // Método para eliminar la relación de la base de datos
    public void eliminarEjercicioRutina() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("EjercicioRutina", "idRutina = ? AND idEjercicio = ?",
                new String[]{String.valueOf(this.idRutina), String.valueOf(this.idEjercicio)});
        db.close();
    }

    // Método para obtener una relación por IDs
    public EjercicioRutina obtenerEjercicioRutinaPorId(int idRutina, int idEjercicio) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EjercicioRutina WHERE idRutina = ? AND idEjercicio = ?",
                new String[]{String.valueOf(idRutina), String.valueOf(idEjercicio)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range")
            EjercicioRutina ejercicioRutina = new EjercicioRutina(
                    cursor.getInt(cursor.getColumnIndex("idRutina")),
                    cursor.getInt(cursor.getColumnIndex("idEjercicio")),
                    cursor.getInt(cursor.getColumnIndex("repeticion"))
            );
            cursor.close();
            db.close();
            return ejercicioRutina;
        }

        db.close();
        return null;
    }

    // Método para obtener todas las relaciones de una rutina específica
    public ArrayList<EjercicioRutina> obtenerTodosLosEjerciciosDeRutina(int idRutina) {
        ArrayList<EjercicioRutina> listaEjercicioRutina = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM EjercicioRutina WHERE idRutina = ? ORDER BY idEjercicio DESC",
                new String[]{String.valueOf(idRutina)});

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") EjercicioRutina ejercicioRutina = new EjercicioRutina(
                        cursor.getInt(cursor.getColumnIndex("idRutina")),
                        cursor.getInt(cursor.getColumnIndex("idEjercicio")),
                        cursor.getInt(cursor.getColumnIndex("repeticion"))
                );
                listaEjercicioRutina.add(ejercicioRutina);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaEjercicioRutina;
    }
}

