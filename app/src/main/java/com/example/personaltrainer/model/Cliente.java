package com.example.personaltrainer.model;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.proxy.ClienteInterface;

import java.util.ArrayList;

public class Cliente implements ClienteInterface {
    private int id;
    private String nombre;
    private String telefono;
    private float peso;
    private float altura;
    private float imc;
    private int idObjetivo;

    private DBHelper dbHelper;
    public Cliente(){
    }

    // Constructor que recibe el contexto, tiene proposito de inicializar la conexion
    public Cliente(Context context) {
        dbHelper = new DBHelper(context);
    }

    // Constructor con datos del cliente
    public Cliente(int id, String nombre, String telefono, float peso, float altura, float imc, int idObjetivo) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.peso = peso;
        this.altura = altura;
        this.imc = imc;
        this.idObjetivo = idObjetivo;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public int getIdObjetivo() {
        return idObjetivo;
    }

    public void setIdObjetivo(int idObjetivo) {
        this.idObjetivo = idObjetivo;
    }
    @Override
    public String toString() {
        return nombre;
    }
    @Override
    public void insertarCliente(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Usamos los valores del objeto cliente
        values.put("nombre", cliente.getNombre());
        values.put("telefono", cliente.getTelefono());
        values.put("peso", cliente.getPeso());
        values.put("altura", cliente.getAltura());
        values.put("imc", cliente.getImc());
        values.put("idObjetivo", cliente.getIdObjetivo());

        // Insertamos los valores en la tabla Cliente
        db.insert("Cliente", null, values);

        // Cerramos la base de datos
        db.close();
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", this.nombre);
        values.put("telefono", this.telefono);
        values.put("peso", this.peso);
        values.put("altura", this.altura);
        values.put("imc", this.imc);
        values.put("idObjetivo", this.idObjetivo);

        db.update("Cliente", values, "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    @Override
    public void eliminarCliente(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("Cliente", "id = ?", new String[]{String.valueOf(this.id)});
        db.close();
    }

    @Override
    public Cliente obtenerClientePorId(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Cliente WHERE id = ?", new String[]{String.valueOf(id)});

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") Cliente cliente = new Cliente(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("nombre")),
                    cursor.getString(cursor.getColumnIndex("telefono")),
                    cursor.getFloat(cursor.getColumnIndex("peso")),
                    cursor.getFloat(cursor.getColumnIndex("altura")),
                    cursor.getFloat(cursor.getColumnIndex("imc")),
                    cursor.getInt(cursor.getColumnIndex("idObjetivo"))
            );
            cursor.close();
            db.close();
            return cliente;
        }
        db.close();
        return null;
    }

    @Override
    public ArrayList<Cliente> obtenerTodosLosClientes() {
        ArrayList<Cliente> listaClientes = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Realizar la consulta
        Cursor cursor = db.rawQuery("SELECT * FROM Cliente ORDER BY id DESC", null);

        // Validar si el cursor no es nulo y tiene registros
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Cliente cliente = new Cliente(
                        cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("nombre")),
                        cursor.getString(cursor.getColumnIndex("telefono")),
                        cursor.getFloat(cursor.getColumnIndex("peso")),
                        cursor.getFloat(cursor.getColumnIndex("altura")),
                        cursor.getFloat(cursor.getColumnIndex("imc")),
                        cursor.getInt(cursor.getColumnIndex("idObjetivo"))
                );
                listaClientes.add(cliente);
            } while (cursor.moveToNext());

            cursor.close(); // Cerrar el cursor si fue exitoso
        } else {
            // Log de depuración para tablas vacías
            Log.d("Cliente", "No se encontraron clientes en la base de datos.");
        }

        db.close(); // Cerrar la conexión a la base de datos
        return listaClientes;
    }
}
