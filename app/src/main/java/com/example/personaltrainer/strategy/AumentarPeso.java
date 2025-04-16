package com.example.personaltrainer.strategy;

import android.content.Context;
import android.widget.Toast;

import com.example.personaltrainer.model.Cliente;

public class AumentarPeso implements EstrategiaObjetivo{

    @Override
    public void ejecutarEstrategia(Cliente cliente) {
        System.out.println("Plan para aumentar peso: Incrementar calorías y ejercicios de fuerza.");

    }
}
