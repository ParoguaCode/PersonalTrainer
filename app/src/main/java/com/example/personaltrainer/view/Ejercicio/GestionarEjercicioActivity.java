package com.example.personaltrainer.view.Ejercicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.CategoriaController;
import com.example.personaltrainer.controller.EjercicioController;
import com.example.personaltrainer.model.CategoriaModel;
import com.example.personaltrainer.model.Ejercicio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class GestionarEjercicioActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int REQUEST_CODE = 100;
    private static final int REQUEST_IMAGE_PICK = 1;
    private Context context;
    private Spinner spinnerCategoria;
    private Button btnGuardarEjercicio, btnAgregarCategoria, btnActualizarEjercicio, btnEliminarEjercicio, btnSeleccionarImagen;
    private EditText txtNombre, txtDescripcion;
    private ImageView imagen;

    private CategoriaController categoriaController;
    private ArrayList<CategoriaModel> listaCategoria;
    private int id;
    private String rutaImagen; // Variable para almacenar la ruta de la imagen seleccionada

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

        // Verificar si se están editando los datos de un ejercicio existente
        Intent i = getIntent();
        Bundle bolsa = i.getExtras();
        if (bolsa != null) {
            id = bolsa.getInt("id");
            String nombre = bolsa.getString("nombre");
            String descripcion = bolsa.getString("descripcion");
            int idCategoriaEjercicio = bolsa.getInt("idCategoria");
            String imagenUri = bolsa.getString("imagen");

            // Establecer los valores obtenidos
            txtNombre.setText(nombre);
            txtDescripcion.setText(descripcion);

            // Verificar permisos de almacenamiento
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE);
            }

            // Cargar la imagen con Glide desde la URI si está disponible
            if (imagenUri != null) {
                Glide.with(this)
                        .load(imagenUri)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imagen);
            } else {
                imagen.setImageResource(R.drawable.ic_launcher_background);
            }

            // Seleccionar la categoría en el spinner
            for (int j = 0; j < listaCategoria.size(); j++) {
                if (listaCategoria.get(j).getId() == idCategoriaEjercicio) {
                    spinnerCategoria.setSelection(j);
                    break;
                }
            }

            // Mostrar los botones de actualización y eliminación
            btnGuardarEjercicio.setVisibility(View.GONE);
            btnActualizarEjercicio.setVisibility(View.VISIBLE);
            btnEliminarEjercicio.setVisibility(View.VISIBLE);
        } else {
            btnActualizarEjercicio.setVisibility(View.GONE);
            btnEliminarEjercicio.setVisibility(View.GONE);
        }

        // Establecer los listeners
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
        int idCategoriaSeleccionada = categoriaSeleccionada.getId();

        ejercicio.setId(id);
        ejercicio.setNombre(nombre);
        ejercicio.setDescripcion(descripcion);
        ejercicio.setIdCategoria(idCategoriaSeleccionada);
        ejercicio.setImagen(rutaImagen); // Establecer la imagen seleccionada

        return ejercicio;
    }

    private void seleccionarImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            if (imageUri != null) {
                // Guardar la ruta de la imagen seleccionada
                rutaImagen = imageUri.toString();

                // Mostrar la imagen seleccionada en el ImageView
                Glide.with(this)
                        .load(imageUri)
                        .placeholder(R.drawable.ic_launcher_foreground)
                        .error(R.drawable.ic_launcher_foreground)
                        .into(imagen);
            } else {
                imagen.setImageResource(R.drawable.ic_launcher_foreground);
            }
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
            case R.id.btnAgregarCategoria:
                Toast.makeText(context, "Registrar Categoria", Toast.LENGTH_LONG).show();
                Intent i= new Intent(context, com.example.personaltrainer.view.Categoria.GestionarCategoriaActivity.class);
                startActivity(i);
                break;
        }
    }
}

