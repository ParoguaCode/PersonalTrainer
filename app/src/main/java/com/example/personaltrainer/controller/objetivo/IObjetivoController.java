package com.example.personaltrainer.controller.objetivo;

import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;

public interface IObjetivoController {
    Objetivo obtenerObjetivoPorId(int id);
    Objetivo obtenerObjetivoNombre(String nombre);

    ArrayList<Objetivo> obtenerObjetivos();

    boolean insertarObjetivo(Objetivo objetivo);
    boolean actualizarObjetivo(Objetivo objetivo);
    boolean eliminarObjetivo(int id);

}
