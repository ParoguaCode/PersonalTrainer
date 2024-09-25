package com.example.personaltrainer.view.Categoria;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.CategoriaController;
import com.example.personaltrainer.model.CategoriaModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaCategoriaActivity extends AppCompatActivity {
    private FloatingActionButton btnAgregarCategoria;
    ListView listView;
    ArrayList<String> nombreCategoria;
    ArrayList<Integer> idCategorias;
    CategoriaController categoriaController;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_categoria);
        categoriaController = new CategoriaController(this);
        init();
    }

    private void init(){
        context = this.getApplicationContext();
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
        listView = findViewById(R.id.listaCategorias);
        nombreCategoria = new ArrayList<>();
        idCategorias = new ArrayList<>();
        llenarListView();

        btnAgregarCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaCategoriaActivity.this, GestionarCategoriaActivity.class));
            }
        });
    }
    private void llenarListView() {
        nombreCategoria.clear();  // Limpiar antes de llenar
        idCategorias.clear();

        List<CategoriaModel> listaCategoria = categoriaController.obtenerCategorias();
        for (CategoriaModel categoriaModel : listaCategoria) {
            nombreCategoria.add(categoriaModel.getNombre());
            idCategorias.add(categoriaModel.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ListaCategoriaActivity.this,
                android.R.layout.simple_list_item_1,
                nombreCategoria
        );
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                CategoriaModel categoriaModel = listaCategoria.get(i);
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", categoriaModel.getId());
                bolsa.putString("nombre", categoriaModel.getNombre());

                Intent intent = new Intent(ListaCategoriaActivity.this, GestionarCategoriaActivity.class);
                intent.putExtras(bolsa);
                startActivity(intent);
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        llenarListView();
    }

}