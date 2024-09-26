package com.example.personaltrainer.view.Ejercicio;

import android.net.Uri;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.CategoriaController;
import com.example.personaltrainer.controller.EjercicioController;
import com.example.personaltrainer.model.CategoriaModel;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Ejercicio;
import com.example.personaltrainer.model.Objetivo;

import java.util.ArrayList;

public class GestionarEjercicioActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Spinner spinnerCategoria;
    Button btnGuardarEjercicio, btnAgregarCategoria, btnActualizarEjercicio, btnEliminarEjercicio, btnSeleccionarImagen;
    EditText txtNombre, txtDescripcion;
    ImageView imagen;

    CategoriaController categoriaController;
    ArrayList<CategoriaModel> listaCategoria;
    int id;
    String rutaImagen; // Variable para almacenar la ruta de la imagen seleccionada

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_ejercicio);
        init();
    }

    private void init() {
        context = getApplicationContext();
        spinnerCategoria = findViewById(R.id.spinnerCategoria);
        btnAgregarCategoria = findViewById(R.id.btnAgregarCategoria);
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen);
        btnGuardarEjercicio = findViewById(R.id.btnGuardarEjercicio);
        btnActualizarEjercicio = findViewById(R.id.btnActualizarEjercicio);
        btnEliminarEjercicio = findViewById(R.id.btnEliminarEjercicio);
        txtNombre = findViewById(R.id.txtNombre);
        txtDescripcion = findViewById(R.id.txtDescripcion);
        imagen = findViewById(R.id.imagen);

        categoriaController = new CategoriaController(this);

        cargarCategoriaEnSpinner();

        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        if (bolsa != null) {
            id = bolsa.getInt("id");
            String nombre = bolsa.getString("nombre");
            String descripcion = bolsa.getString("descripcion");
            int idCategoriaEjercicio = bolsa.getInt("idCategoria");

            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);

            for (int j = 0; j < listaCategoria.size(); j++) {
                if (listaCategoria.get(j).getId() == idCategoriaEjercicio) {
                    spinnerCategoria.setSelection(j);
                    break;
                }
            }
            btnGuardarEjercicio.setVisibility(View.GONE);
            btnActualizarEjercicio.setVisibility(View.VISIBLE);
            btnEliminarEjercicio.setVisibility(View.VISIBLE);
        } else {
            btnActualizarEjercicio.setVisibility(View.GONE);
            btnEliminarEjercicio.setVisibility(View.GONE);
        }

        btnAgregarCategoria.setOnClickListener(this);
        btnGuardarEjercicio.setOnClickListener(this);
        btnActualizarEjercicio.setOnClickListener(this);
        btnEliminarEjercicio.setOnClickListener(this);
        btnSeleccionarImagen.setOnClickListener(this);
    }

    private void cargarCategoriaEnSpinner() {
        listaCategoria = categoriaController.obtenerCategorias();
        ArrayAdapter<CategoriaModel> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listaCategoria);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategoria.setAdapter(adapter);
    }

    private Ejercicio llenarDatosEjercicio() {
        Ejercicio ejercicio = new Ejercicio(this);

        String nombre = txtNombre.getText().toString();
        String descripcion = txtDescripcion.getText().toString();

        CategoriaModel categoriaSeleccionada = (CategoriaModel) spinnerCategoria.getSelectedItem();
        int idcategoriaSeleccionada = categoriaSeleccionada.getId();

        ejercicio.setId(id);
        ejercicio.setNombre(nombre);
        ejercicio.setDescripcion(descripcion);
        ejercicio.setIdCategoria(idcategoriaSeleccionada);
        ejercicio.setImagen(rutaImagen);  // Establece la imagen seleccionada (si la hay)
        return ejercicio;
    }

    private void seleccionarImagen() {
        // Abrir la galería para seleccionar una imagen
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            imagen.setImageURI(selectedImage);
            rutaImagen = selectedImage.toString();  // Almacena la ruta de la imagen seleccionada
        }
    }

    private void guardar() {
        Ejercicio ejercicio = llenarDatosEjercicio();
        EjercicioController ejercicioController = new EjercicioController(context);

        boolean resultado = ejercicioController.insertarEjercicio(ejercicio);
        if (resultado) {
            Toast.makeText(context, "Nuevo ejercicio agregado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaEjercicioActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(context, "Error al agregar el ejercicio", Toast.LENGTH_LONG).show();
        }
    }

    private void actualizar() {
        Ejercicio ejercicio = llenarDatosEjercicio();
        EjercicioController ejercicioController = new EjercicioController(context);

        boolean resultado = ejercicioController.actualizarEjercicio(ejercicio);
        if (resultado) {
            Toast.makeText(context, "Ejercicio actualizado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaEjercicioActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(context, "Error al actualizar el ejercicio", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminar() {
        EjercicioController ejercicioController = new EjercicioController(context);

        boolean resultado = ejercicioController.eliminarEjercicio(id);
        if (resultado) {
            Toast.makeText(context, "Ejercicio eliminado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaEjercicioActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(context, "Error al eliminar el ejercicio", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnGuardarEjercicio:
                guardar();
                break;
            case R.id.btnActualizarEjercicio:
                actualizar();
                break;
            case R.id.btnEliminarEjercicio:
                eliminar();
                break;
            case R.id.btnSeleccionarImagen:
                seleccionarImagen();
                break;
        }
    }
}
