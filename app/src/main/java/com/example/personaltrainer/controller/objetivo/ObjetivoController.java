package com.example.personaltrainer.controller.objetivo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;
import java.util.List;

public class ObjetivoController implements IObjetivoController {
    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public ObjetivoController(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Método para insertar un objetivo
    public boolean insertarObjetivo(Objetivo objetivo) {
        db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", objetivo.getNombre());

        long resultado = db.insert("Objetivo", null, valores);
        db.close();
        return resultado != -1;
    }

    // Método para obtener todos los objetivos
    public ArrayList<Objetivo> obtenerObjetivos() {
        ArrayList<Objetivo> listaObjetivos = new ArrayList<>();
        db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Objetivo ORDER BY id DESC", null);
        if (cursor.moveToFirst()) {
            do {
                Objetivo objetivo = new Objetivo();
                objetivo.setId(cursor.getInt(0));
                objetivo.setNombre(cursor.getString(1));

                listaObjetivos.add(objetivo);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listaObjetivos;
    }

    // Método para actualizar un objetivo
    public boolean actualizarObjetivo(Objetivo objetivo) {
        db = dbHelper.getWritableDatabase();
        ContentValues valores = new ContentValues();
        valores.put("nombre", objetivo.getNombre());

        int resultado = db.update("Objetivo", valores, "id = ?", new String[]{String.valueOf(objetivo.getId())});
        db.close();
        return resultado > 0;
    }

    // Método para eliminar un objetivo
    public boolean eliminarObjetivo(int id) {
        db = dbHelper.getWritableDatabase();
        int resultado = db.delete("Objetivo", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        return resultado > 0;
    }

    // Método para obtener un objetivo por su id
    public Objetivo obtenerObjetivoPorId(int id) {
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Objetivo WHERE id = ?", new String[]{String.valueOf(id)});
        Objetivo objetivo = null;
        if (cursor.moveToFirst()) {
            objetivo = new Objetivo();
            objetivo.setId(cursor.getInt(0));
            objetivo.setNombre(cursor.getString(1));
        }
        cursor.close();
        db.close();
        return objetivo;
    }

    @Override
    public Objetivo obtenerObjetivoNombre(String nombre) {
        return null;
    }


}
