package com.example.personaltrainer.view.Objetivo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.ObjetivoController;
import com.example.personaltrainer.model.Objetivo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListObjetivoActivity extends AppCompatActivity {

    private FloatingActionButton btnAgregarObjetivo;
    ListView listView;
    ArrayList<String> nombreObjetivo;
    ArrayList<Integer> idObjetivos;
    ObjetivoController objetivoController;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_objetivo);
        // Inicializar el controlador antes de usarlo
        objetivoController = new ObjetivoController(this);
        init();
    }

    private void init() {
        // Vinculando vistas
        context = this.getApplicationContext();
        btnAgregarObjetivo = findViewById(R.id.btnAgregarObjetivo);
        listView = findViewById(R.id.listaObjetivos);

        // Inicializando controlador
        ObjetivoController objetivoController = new ObjetivoController(this);
        objetivoController = new ObjetivoController(ListObjetivoActivity.this);

        // Llenando el ListView
        llenarListView();

        // Acción del botón para agregar objetivos
        btnAgregarObjetivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListObjetivoActivity.this, AgregarObjetivoActivity.class));
            }
        });
    }

    private void llenarListView() {
        nombreObjetivo = new ArrayList<String>();
        idObjetivos = new ArrayList<Integer>();

        List<Objetivo> listaObjetivo = objetivoController.obtenerObjetivos();
        for (Objetivo objetivo : listaObjetivo) {
            nombreObjetivo.add(objetivo.getNombre());
            idObjetivos.add(objetivo.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ListObjetivoActivity.this,
                android.R.layout.simple_list_item_1,
                nombreObjetivo
        );listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Objetivo objetivo = listaObjetivo.get(i); // Corregido
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", objetivo.getId());
                bolsa.putString("nombre", objetivo.getNombre());

                Intent intent = new Intent(ListObjetivoActivity.this,AgregarObjetivoActivity.class);
                intent.putExtras(bolsa);
                startActivity(intent);
            }
        });
    }
}
