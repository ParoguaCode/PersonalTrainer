package com.example.personaltrainer.strategy;

import com.example.personaltrainer.model.Cliente;

public class MantenerPeso implements EstrategiaObjetivo{

    @Override
    public void ejecutarEstrategia(Cliente cliente) {
        System.out.println("Plan para mantener peso: Mantener un balance adecuado de calorías.");

    }
}
