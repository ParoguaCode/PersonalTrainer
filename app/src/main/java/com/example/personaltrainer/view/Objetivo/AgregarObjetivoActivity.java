package com.example.personaltrainer.view.Objetivo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.personaltrainer.R;
import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.controller.cliente.ClienteController;
import com.example.personaltrainer.controller.objetivo.ObjetivoController;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Objetivo;
import com.example.personaltrainer.view.Cliente.ListaClienteActivity;

import java.util.ArrayList;

public class AgregarObjetivoActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Button btnGuadarObjetivo, btnActualizarObjetivo, btnEliminarObjetivo;
    EditText txtnombre;
    int id;
    Objetivo objetivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_objetivo);
        init();

    }

    private void init(){
        context = this;
        btnGuadarObjetivo = findViewById(R.id.btnGuadarObjetivo);
        btnActualizarObjetivo = findViewById(R.id.btnActualizarObjetivo);
        btnEliminarObjetivo = findViewById(R.id.btnEliminarObjetivo);
        txtnombre = findViewById(R.id.txtNombre);

        Intent i = getIntent();
        Bundle bolsa = i.getExtras();

        if(bolsa!=null){
            //si el objetivo existe, llenamos los campos con los datos recibidos
            id = bolsa.getInt("id");
            String nombre = bolsa.getString("nombre");
            // Llenamos los campos de texto con los datos del objetivo
            txtnombre.setText(nombre);

            btnGuadarObjetivo.setVisibility(View.GONE);
            btnActualizarObjetivo.setVisibility(View.VISIBLE);
            btnEliminarObjetivo.setVisibility(View.VISIBLE);
        }else{
            btnActualizarObjetivo.setVisibility(View.GONE);
            btnEliminarObjetivo.setVisibility(View.GONE);
        }
        // Agregamos los listeners a los botones
        btnGuadarObjetivo.setOnClickListener(this);
        btnActualizarObjetivo.setOnClickListener(this);
        btnEliminarObjetivo.setOnClickListener(this);
    }
    private Objetivo llenarDatosObjetivo(){
        Objetivo objetivo = new Objetivo();
        String nombre = txtnombre.getText().toString();
        objetivo.setId(id);
        objetivo.setNombre(nombre);
        return objetivo;
    }
    // Método para guardar un nuevo cliente
    /*private void guardar() {
        Objetivo objetivo = llenarDatosObjetivo();
        ObjetivoController objetivoController = new ObjetivoController(context);

        boolean resultado = objetivoController.insertarObjetivo(objetivo);
        if (resultado) {
            Toast.makeText(context, "Nuevo Objetivo agregado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListObjetivoActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al agregar el objetivo", Toast.LENGTH_LONG).show();
        }
    }*/
    private void guardar() {
        String nombreIngresado = txtnombre.getText().toString().trim();

        if (nombreIngresado.isEmpty()) {
            Toast.makeText(this, "El nombre del objetivo no puede estar vacío", Toast.LENGTH_LONG).show();
            return;
        }

        Objetivo objetivo = new Objetivo();
        objetivo.setNombre(nombreIngresado);

        ObjetivoController objetivoController = new ObjetivoController(this); // Usar 'this' como contexto

        boolean resultado = objetivoController.insertarObjetivo(objetivo);
        if (resultado) {
            Toast.makeText(this, "Nuevo Objetivo agregado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, ListObjetivoActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual
        } else {
            Toast.makeText(this, "Error al agregar el objetivo", Toast.LENGTH_LONG).show();
        }
    }


    // Método para actualizar un cliente existente
    private void actualizar() {
        Objetivo objetivo = llenarDatosObjetivo();
        objetivo.setId(id);  // El ID del cliente que estamos actualizando

        ObjetivoController objetivoController = new ObjetivoController(context);

        boolean resultado = objetivoController.actualizarObjetivo(objetivo);
        if (resultado) {
            Toast.makeText(context, "Objetivo actualizado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListObjetivoActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al actualizar el cliente", Toast.LENGTH_LONG).show();
        }
    }


    //    private void guardar() {
//        Objetivo objetivo = llenarDatosObjetivo(); // este método llena tu objeto objetivo
//        ObjetivoController objetivoController = new ObjetivoController(context); // Crear una instancia del controlador
//        // Verifica que el nombre no esté vacío o nulo
//        if (objetivo.getNombre() == null || objetivo.getNombre().trim().isEmpty()) {
//            Toast.makeText(context, "El nombre del objetivo no puede estar vacío", Toast.LENGTH_LONG).show();
//            return;
//        }
//        boolean resultado;
//        if (id == 0) {
//            // Lógica para agregar nuevo objetivo
//            resultado = objetivoController.insertarObjetivo(objetivo);  // Usar el controlador para insertar
//
//            if (resultado) {
//                Toast.makeText(context, "Nuevo objetivo agregado", Toast.LENGTH_LONG).show();
//                // Intentar volver a la lista de objetivos o refrescar la vista
//
//            } else {
//                Toast.makeText(context, "Error al agregar el objetivo", Toast.LENGTH_LONG).show();
//            }
//        } else {
//
//            // Lógica para actualizar un objetivo existente
//            //ant - objetivo.setId(id); // Asegúrate de que el id esté configurado
//            resultado = objetivoController.actualizarObjetivo(objetivo); // Usar el controlador para actualizar
//
//            if (resultado) {
//                Toast.makeText(context, "Objetivo actualizado", Toast.LENGTH_LONG).show();
//            } else {
//                Toast.makeText(context, "Error al actualizar el objetivo", Toast.LENGTH_LONG).show();
//            }
//        }
//        // Si el resultado es positivo, redirigir a la actividad de la lista
//        if (resultado) {
//            Intent intent = new Intent(context, ListObjetivoActivity.class);
//            startActivity(intent);
//            finish(); // Finalizar la actividad actual si es necesario
//        }
//    }
    private void eliminarObjetivo() {
        if (id == 0) {
            Toast.makeText(context, "No es posible eliminar un objetivo sin ID", Toast.LENGTH_LONG).show();
            return; // Si el ID es 0, no continuamos.
        }

        ObjetivoController objetivoController = new ObjetivoController(context); // Crear una instancia del controlador

        // Confirmar si se quiere eliminar
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Objetivo")
                .setMessage("¿Estás seguro de que deseas eliminar este objetivo?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario confirma, eliminamos
                        boolean resultado = objetivoController.eliminarObjetivo(id); // Usar el controlador para eliminar

                        if (resultado) {
                            Toast.makeText(context, "Objetivo eliminado", Toast.LENGTH_LONG).show();
                            // Redirigir a la lista de objetivos
                            Intent intent = new Intent(AgregarObjetivoActivity.this, ListObjetivoActivity.class);
                            startActivity(intent);
                            finish(); // Finalizamos la actividad actual
                        } else {
                            Toast.makeText(context, "Error al eliminar el objetivo", Toast.LENGTH_LONG).show();
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
            case R.id.btnGuadarObjetivo:
                guardar();
                break;
            case R.id.btnActualizarObjetivo:
                actualizar();
                break;
            case R.id.btnEliminarObjetivo:
                eliminarObjetivo();
                break;
        }
    }
}