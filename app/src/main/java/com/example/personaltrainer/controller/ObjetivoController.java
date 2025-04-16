package com.example.personaltrainer.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Objetivo;
import com.example.personaltrainer.strategy.AumentarPeso;
import com.example.personaltrainer.strategy.ContextoEstrategia;
import com.example.personaltrainer.strategy.EstrategiaObjetivo;
import com.example.personaltrainer.strategy.MantenerPeso;
import com.example.personaltrainer.strategy.PerderPeso;

import java.util.ArrayList;

public class ObjetivoController {
    private Context context;
    private DBHelper dbHelper;
    private ContextoEstrategia contextoEstrategia;

    // Constructor
    public ObjetivoController(Context context) {
        this.context = context;
        this.dbHelper = new DBHelper(context);
        this.contextoEstrategia = new ContextoEstrategia();
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
    public String sugerirObjetivo(float imc) {
        if (imc < 18.5) {
            return "Aumentar peso";
        } else if (imc >= 18.5 && imc < 25) {
            return "Mantener peso";
        } else {
            return "Perder peso";
        }
    }

    public void seleccionarEstrategia(float imc, Cliente cliente) {
        if (imc < 18.5) {
            contextoEstrategia.setEstrategia(new AumentarPeso());  // Estrategia para aumentar peso
        } else if (imc >= 18.5 && imc < 25) {
            contextoEstrategia.setEstrategia(new MantenerPeso());  // Estrategia para mantener peso
        } else {
            contextoEstrategia.setEstrategia(new PerderPeso());  // Estrategia para perder peso
        }

        // Ejecutamos la estrategia seleccionada
        contextoEstrategia.ejecutarEstrategia(cliente);
    }

}