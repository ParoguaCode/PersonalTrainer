package com.example.personaltrainer.view.Categoria;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.CategoriaController;
import com.example.personaltrainer.model.CategoriaModel;

import java.util.ArrayList;

public class GestionarCategoriaActivity extends AppCompatActivity implements View.OnClickListener{
    Context context;
    Button btnGuardarCategoria, btnActualizarCategoria, btnEliminarCategoria;
    EditText txtNombre;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_categoria);
        init();
    }
    private void init(){
        context = getApplicationContext();
        btnGuardarCategoria = findViewById(R.id.btnGuardarCategoria);
        btnActualizarCategoria = findViewById(R.id.btnActualizarCategoria);
        btnEliminarCategoria = findViewById(R.id.btnEliminarCategoria);
        txtNombre = findViewById(R.id.txtNombre);

        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        if (bolsa!=null){
            id = bolsa.getInt("id");
            String nombre = bolsa.getString("nombre");

            txtNombre.setText(nombre);

        }else{

        }

        btnGuardarCategoria.setOnClickListener(this);
        btnActualizarCategoria.setOnClickListener(this);
        btnEliminarCategoria.setOnClickListener(this);
    }

    private CategoriaModel llenarDatosCategoria(){
        CategoriaModel categoriaModel = new CategoriaModel(this);
        String nombre = txtNombre.getText().toString();
        categoriaModel.setId(id);
        categoriaModel.setNombre(nombre);
        return categoriaModel;
    }

    private void guardar() {
        CategoriaModel categoriaModel = llenarDatosCategoria();
        CategoriaController categoriaController = new CategoriaController(context);

        boolean resultado = categoriaController.insertarCategoria(categoriaModel);
        if (resultado) {
            Toast.makeText(context, "Nueva categoria agregada", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaCategoriaActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al agregar la categoria", Toast.LENGTH_LONG).show();
        }
    }
    private void actualizar(){
        CategoriaModel categoriaModel = llenarDatosCategoria();
        categoriaModel.setId(id);
        CategoriaController categoriaController = new CategoriaController(context);

        boolean resultado = categoriaController.actualizarCategoria(categoriaModel);
        if (resultado) {
            Toast.makeText(context, "Categoria actualizada", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaCategoriaActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al actualizar la cateforia", Toast.LENGTH_LONG).show();
        }
    }
    private  void eliminar() {
        if (id == 0) {
            Toast.makeText(context, "No es posible eliminar una categoria sin ID", Toast.LENGTH_LONG).show();
            return; // Si el ID es 0, no continuamos.
        }
        CategoriaController categoriaController = new CategoriaController(context);
        // Confirmar si se quiere eliminar
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Categoria")
                .setMessage("¿Estás seguro de que deseas eliminar esta categoria?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario confirma, eliminamos
                        boolean resultado = categoriaController.eliminarCategoria(id);

                        if (resultado) {
                            Toast.makeText(context, "Categoria eliminada", Toast.LENGTH_LONG).show();
                            // Redirigir a la lista de objetivos
                            Intent intent = new Intent(GestionarCategoriaActivity.this, ListaCategoriaActivity.class);
                            startActivity(intent);
                            finish(); // Finalizamos la actividad actual
                        } else {
                            Toast.makeText(context, "Error al eliminar categoria", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null) // Si el usuario cancela, no hacemos nada
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnGuardarCategoria:
                guardar();
                break;
            case R.id.btnActualizarCategoria:
                actualizar();
                break;
            case R.id.btnEliminarCategoria:
                eliminar();
                break;
        }
    }
}