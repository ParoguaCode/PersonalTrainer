package com.example.personaltrainer.strategy;

import android.content.Context;

import com.example.personaltrainer.model.Cliente;

public class ContextoEstrategia {
    private EstrategiaObjetivo estrategia;

    public void setEstrategia(EstrategiaObjetivo estrategia) {
        this.estrategia = estrategia;

    }
    public void ejecutarEstrategia(Cliente cliente) {
        System.out.println("estrategia a ejecutar" + estrategia);
        if (estrategia != null) {
            estrategia.ejecutarEstrategia(cliente);
        } else {
            System.out.println("No se ha definido una estrategia.");
        }
    }
}
