package com.example.personaltrainer.controller;

import android.content.Context;
import com.example.personaltrainer.model.Rutina;

import java.util.ArrayList;

public class RutinaController {
    private Context context;

    public RutinaController(Context context){
        this.context = context;
    }
    public ArrayList<Rutina> obtenerTodasLasRutinas(){
        Rutina rutina = new Rutina(context);
        return rutina.obtenerTodasLasRutinas();
    }
    public boolean insertarRutina(Rutina rutina) {
        try {
            rutina.insertarRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();// Capturamos e imprimimos errores
            return false;
        }
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
}
