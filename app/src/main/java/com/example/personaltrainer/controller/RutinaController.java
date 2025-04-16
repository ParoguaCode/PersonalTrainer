package com.example.personaltrainer.controller;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.Ejercicio;
import com.example.personaltrainer.model.Rutina;
import com.example.personaltrainer.view.Rutina.ListaRutinasActivity;

import java.util.ArrayList;

public class RutinaController {

    private final Context context;
    private final Rutina rutinaModel;
    private DBHelper dbHelper;

    public RutinaController(Context context){
        this.context = context;
        this.rutinaModel = new Rutina(context);
        this.dbHelper = new DBHelper(context);
    }

    // Método para crear una nueva rutina y obtener su ID
    public long crearRutina(String nombre, int idCliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("idCliente", idCliente);

        long idRutina = db.insert("Rutina", null, values);
        return idRutina;  // Retorna el ID de la rutina creada
    }

    public ArrayList<Rutina> obtenerTodasLasRutinas(){
        Rutina rutina = new Rutina(context);
        return rutina.obtenerTodasLasRutinas();
    }
    public boolean actualizarRutina(Rutina rutina) {
        try {
            rutina.actualizarRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eliminarRutina(int id) {
        try {
           Rutina rutina = new Rutina(context);
           rutina.setId(id);
           rutina.eliminarRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public Rutina obtenerRutinaPorId(int id) {
        Rutina rutina = new Rutina(context);
        return rutina.obtenerRutinaPorId(id);
    }
    // Método para obtener un ejercicio por nombre
    public Ejercicio obtenerEjercicioPorNombre(String nombreEjercicio) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("Ejercicio",
                new String[] { "id", "nombre" },
                "nombre = ?",
                new String[] { nombreEjercicio },
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
            @SuppressLint("Range") String nombre = cursor.getString(cursor.getColumnIndex("nombre"));

            Ejercicio ejercicio = new Ejercicio();
            ejercicio.setId(id);
            ejercicio.setNombre(nombre);

            cursor.close();
            return ejercicio;
        }

        cursor.close();
        return null;  // Si no se encontró el ejercicio, devolver null
    }

    // Método para insertar una relación ejercicio-rutina
    public boolean insertarEjercicioEnRutina(int idRutina, int idEjercicio, String repeticiones) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("idRutina", idRutina);
        values.put("idEjercicio", idEjercicio);
        values.put("repeticiones", repeticiones);

        long id = db.insert("ejercicio_rutina", null, values);
        return id != -1;  // Retorna true si la inserción fue exitosa, sino false
    }
}
