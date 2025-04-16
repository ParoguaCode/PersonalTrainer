package com.example.personaltrainer.strategy;

import com.example.personaltrainer.model.Cliente;

public class PerderPeso implements EstrategiaObjetivo{

    @Override
    public void ejecutarEstrategia(Cliente cliente) {
        System.out.println("Plan para perder peso: Reducción de calorías y más ejercicio.");
    }
}
