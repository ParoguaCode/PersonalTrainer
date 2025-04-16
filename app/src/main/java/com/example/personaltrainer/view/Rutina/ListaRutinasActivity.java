package com.example.personaltrainer.view.Rutina;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.RutinaController;
import com.example.personaltrainer.model.Rutina;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaRutinasActivity extends AppCompatActivity {

    private FloatingActionButton btnAgregarRutina;
    private RutinaController rutinaController;
    ListView listView;
    ArrayList<String> tipoRutina;
    ArrayList<Integer> idRutinas;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_rutinas);
        rutinaController = new RutinaController(this);
        init();
    }
    private void init(){
        context = this.getApplicationContext();
        btnAgregarRutina = findViewById(R.id.btnAgregarRutina);
        listView = findViewById(R.id.listaRutinas);

        llenarListView();
        btnAgregarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaRutinasActivity.this, GestionarRutinaActivity.class));
            }
        });
    }

    private void llenarListView() {
        tipoRutina = new ArrayList<>();
        idRutinas = new ArrayList<>();

        List<Rutina> listaRutina = rutinaController.obtenerTodasLasRutinas();
        for (Rutina rutina : listaRutina) {
            tipoRutina.add(rutina.getNombre());
            idRutinas.add(rutina.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ListaRutinasActivity.this,
                android.R.layout.simple_list_item_1,
                tipoRutina
        );
        listView.setAdapter(adapter);
        // en ves de usar selectListenerRutina
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Rutina rutina = listaRutina.get(i);
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", rutina.getId());
                bolsa.putString("tipo", rutina.getNombre());

                bolsa.putInt("idCliente", rutina.getIdCliente()); // Si es necesario

                Intent intent = new Intent(ListaRutinasActivity.this, GestionarRutinaActivity.class);
                intent.putExtras(bolsa);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Volver a llenar la lista cuando se regrese a esta actividad
        llenarListView();
    }
}

