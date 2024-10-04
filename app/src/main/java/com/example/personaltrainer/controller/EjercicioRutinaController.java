package com.example.personaltrainer.controller;
import android.content.Context;
import com.example.personaltrainer.model.EjercicioRutina;
import java.util.ArrayList;

public class EjercicioRutinaController {
    private Context context;

    public EjercicioRutinaController(Context context){
        this.context = context;
    }

    public boolean insertarEjercicioRutina(EjercicioRutina ejercicioRutina) {
        try {
            ejercicioRutina.insertarEjercicioRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();// Capturamos e imprimimos errores
            return false;
        }
    }

    public boolean actualizarEjercicioRutina(EjercicioRutina ejercicioRutina) {
        try {
            ejercicioRutina.actualizarEjercicioRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarEjercicioRutina(int id) {
        try {
            EjercicioRutina ejercicioRutina = new EjercicioRutina(context);
            ejercicioRutina.setIdEjercicio(id);
            ejercicioRutina.eliminarEjercicioRutina();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
