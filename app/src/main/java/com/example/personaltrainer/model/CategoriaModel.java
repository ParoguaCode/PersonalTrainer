package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.personaltrainer.conexion.DBHelper;

import java.util.ArrayList;

public class CategoriaModel {
    private int id;
    private String nombre;
    private DBHelper dbHelper;

    public CategoriaModel(){
    }

    public CategoriaModel(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    // Constructor con datos del objetivo
    public CategoriaModel(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

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
    public void insertarCategoria(){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);

        db.insert("Categoria", null, values);
        db.close();
    }
    public void actualizarCategoria() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);

        db.update("Categoria", values, "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    public void eliminarCategoria() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Categoria", "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    public CategoriaModel obtenerCategoriaPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Categoria WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") CategoriaModel categoriaModel = new CategoriaModel(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nombre"))
            );
            cursor.close();
            db.close();
            return categoriaModel;
        }

        db.close();
        return null;
    }

    public ArrayList<CategoriaModel> obtenerTodosLasCategorias() {
        ArrayList<CategoriaModel> listaCategoria = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Categoria ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") CategoriaModel categoriaModel = new CategoriaModel(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre"))
                );
                listaCategoria.add(categoriaModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaCategoria;
    }

}
