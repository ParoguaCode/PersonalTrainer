package com.example.personaltrainer.controller.objetivo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;
import java.util.List;

public class ObjetivoController {
    private Context context;
    private DBHelper dbHelper;

    // Constructor
    public ObjetivoController(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    // Método para insertar un objetivo
    public boolean insertarObjetivo(Objetivo objetivo) {
        SQLiteDatabase db = dbHelper.getWritableDatabase(); // Abre la base de datos para escribir
        ContentValues values = new ContentValues();
        values.put("nombre", objetivo.getNombre());
        long resultado = db.insert("objetivo", null, values);
        db.close();

        return resultado != -1; // Si el valor de resultado es -1, significa que la inserción falló
    }


    // Método para actualizar un objetivo
    public boolean actualizarObjetivo(Objetivo objetivo) {
        try {
            objetivo.actualizarObjetivo();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para eliminar un objetivo
    public boolean eliminarObjetivo(int id) {
        try {
            Objetivo objetivo = new Objetivo(context);
            objetivo.setId(id);
            objetivo.eliminarObjetivo();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener todos los objetivos
    public ArrayList<Objetivo> obtenerObjetivos() {
        Objetivo objetivo = new Objetivo(context);
        return objetivo.obtenerTodosLosObjetivos();
    }

}