package com.example.personaltrainer.view.Ejercicio;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.EjercicioController;
import com.example.personaltrainer.model.Ejercicio;

import java.util.ArrayList;

public class ListaEjercicioActivity extends AppCompatActivity {
    RecyclerView recyclerEjercicios;
    EjercicioController ejercicioController;
    ArrayList<Ejercicio> listaEjercicios;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicio);

        context = this;
        recyclerEjercicios = findViewById(R.id.recyclerEjercicios);
        recyclerEjercicios.setLayoutManager(new LinearLayoutManager(this));

        // Obtener los ejercicios desde el controlador
        ejercicioController = new EjercicioController(context);
        listaEjercicios = ejercicioController.obtenerTodosLosEjercicios();

        if (listaEjercicios.isEmpty()) {
            Toast.makeText(context, "No hay ejercicios registrados", Toast.LENGTH_LONG).show();
        } else {
            EjercicioAdapter adapter = new EjercicioAdapter(listaEjercicios, context);
            recyclerEjercicios.setAdapter(adapter);
        }
    }
}