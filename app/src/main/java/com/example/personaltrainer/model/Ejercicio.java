package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.personaltrainer.conexion.DBHelper;

import java.util.ArrayList;

public class Ejercicio {
    private int id;
    private String nombre;
    private String descripcion;
    private String imagen; // La imagen se almacena como una URL o ruta local (String).
    private int idCategoria; // Llave foránea que conecta a la categoría.
    private DBHelper dbHelper;

    private String categoriaNombre;

    // Constructor vacío
    public Ejercicio() {}

    // Constructor para inicializar el DBHelper
    public Ejercicio(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Constructor con todos los parámetros (imagen como String)
    public Ejercicio(int id, String nombre, String descripcion, String imagen, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.imagen = imagen;  // Almacenamos la cadena de la URI como String
        this.idCategoria = idCategoria;
    }

    // Constructor con Uri para la imagen (con manejo de null)
    public Ejercicio(int id, String nombre, String descripcion, Uri imagenUri, int idCategoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        if (imagenUri != null) {
            this.imagen = imagenUri.toString();  // Convertir Uri a String si no es null
        } else {
            this.imagen = null;  // O un valor predeterminado si prefieres
        }
        this.idCategoria = idCategoria;
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

    // Obtener la URI como objeto Uri
    public Uri getImagenUri() {
        if (imagen != null) {
            return Uri.parse(imagen);  // Convertir String a Uri
        }
        return null;
    }

    // Establecer la imagen desde un objeto Uri
    public void setImagenUri(Uri imagenUri) {
        if (imagenUri != null) {
            this.imagen = imagenUri.toString();  // Convertir Uri a String
        } else {
            this.imagen = null;
        }
    }

    // Obtener la imagen como String
    public String getImagen() {
        return imagen;
    }

    // Establecer la imagen como String
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getCategoriaNombre() {
        return categoriaNombre;
    }

    public void setCategoriaNombre(String categoriaNombre) {
        this.categoriaNombre = categoriaNombre;
    }

    // Insertar un ejercicio en la base de datos
    public void insertarEjercicio() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        values.put("descripcion", this.descripcion);
        values.put("imagen", this.imagen);  // Almacenar el String de la URI
        values.put("idCategoria", this.idCategoria);

        db.insert("Ejercicio", null, values);
        db.close();
    }

    // Actualizar un ejercicio en la base de datos
    public void actualizarEjercicio() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        values.put("descripcion", this.descripcion);
        values.put("imagen", this.imagen);  // Almacenar el String de la URI
        values.put("idCategoria", this.idCategoria);

        db.update("Ejercicio", values, "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Eliminar un ejercicio de la base de datos
    public void eliminarEjercicio() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Ejercicio", "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    // Obtener un ejercicio por ID
    public Ejercicio obtenerEjercicioPorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Ejercicio WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range")
            Ejercicio ejercicio = new Ejercicio(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("descripcion")),
                    Uri.parse(cursor.getString(cursor.getColumnIndex("imagen"))),  // Convertir String a Uri
                    cursor.getInt(cursor.getColumnIndex("idCategoria"))
            );
            cursor.close();
            db.close();
            return ejercicio;
        }

        db.close();
        return null;
    }

    // Obtener todos los ejercicios
    public ArrayList<Ejercicio> obtenerTodosLosEjercicios() {
        ArrayList<Ejercicio> listaEjercicios = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM Ejercicio ORDER BY id DESC", null);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range")
                String imagenString = cursor.getString(cursor.getColumnIndex("imagen"));
                Uri imagenUri = null;

                if (imagenString != null && !imagenString.isEmpty()) {
                    imagenUri = Uri.parse(imagenString);
                }

                @SuppressLint("Range") Ejercicio ejercicio = new Ejercicio(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("descripcion")),
                        imagenUri,  // Manejar la URI, incluso si es null
                        cursor.getInt(cursor.getColumnIndex("idCategoria"))
                );
                listaEjercicios.add(ejercicio);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return listaEjercicios;
    }
}
