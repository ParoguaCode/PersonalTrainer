package com.example.personaltrainer.controller;

import android.content.Context;
import android.net.Uri;

import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.Ejercicio;

import java.util.ArrayList;

public class EjercicioController {
    private DBHelper dbHelper;
    private Context context;

    // Constructor
    public EjercicioController(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    public ArrayList<Ejercicio> obtenerTodosLosEjercicios() {
        Ejercicio ejercicio = new Ejercicio(context);
        return ejercicio.obtenerTodosLosEjercicios();
    }

    public boolean insertarEjercicio(Ejercicio ejercicio) {
        try {
            ejercicio.insertarEjercicio();
            return true;
        } catch (Exception e) {
            e.printStackTrace();// Capturamos e imprimimos errores
            return false;
        }
    }
    public boolean actualizarEjercicio(Ejercicio ejercicio) {
        try {
            ejercicio.actualizarEjercicio();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEjercicio(int id) {
        try {
            Ejercicio ejercicio = new Ejercicio(context);
            ejercicio.setId(id);
            ejercicio.eliminarEjercicio();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Ejercicio obtenerEjercicioPorId(int id) {
        Ejercicio ejercicio = new Ejercicio(context);
        return ejercicio.obtenerEjercicioPorId(id);
    }
    public Uri getImagenUri(){
        Ejercicio ejercicio = new Ejercicio(context);
        return ejercicio.getImagenUri();
    }

}
