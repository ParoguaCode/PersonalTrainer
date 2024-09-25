package com.example.personaltrainer.view.Cliente;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.example.personaltrainer.controller.cliente.ClienteController;
import com.example.personaltrainer.controller.objetivo.ObjetivoController;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Objetivo;
import com.example.personaltrainer.view.Objetivo.AgregarObjetivoActivity;
import com.example.personaltrainer.view.Objetivo.ListObjetivoActivity;

import java.util.ArrayList;

public class GestionarClienteActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Spinner spinnerObjetivos;
    Button btnGuardarCliente, btnAgregarObjetivo, btnActualizarCliente, btnEliminarCliente;
    EditText txtNombre, txtTelefono, txtPeso, textAltura, txtIMC;
    TextView viewImc;
    ObjetivoController objetivoController;
    ArrayList<Objetivo> listaObjetivos;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_cliente);
        init();
    }

    private void init(){
        context = getApplicationContext();
        // Inicializar componentes
        spinnerObjetivos = findViewById(R.id.spinnerObjetivos);
        btnGuardarCliente = findViewById(R.id.btnGuardarCliente);
        btnActualizarCliente = findViewById(R.id.btnActualizarCliente);
        btnEliminarCliente = findViewById(R.id.btnEliminarCliente);
        btnAgregarObjetivo = findViewById(R.id.btnAgregarObjetivo);
        txtNombre = findViewById(R.id.txtNombre);
        txtTelefono = findViewById(R.id.txtTelefono);
        txtPeso = findViewById(R.id.txtPeso);
        textAltura = findViewById(R.id.txtAltura);
        txtIMC = findViewById(R.id.txtIMC);
        viewImc = findViewById(R.id.viewImc);

        objetivoController = new ObjetivoController(this);
        // Llenar el spinner con los objetivos existentes
        cargarObjetivosEnSpinner();

        Intent i= getIntent();
        Bundle bolsa = i.getExtras();
        if(bolsa!=null){
            //si el objetivo existe, llenamos los campos con los datos recibidos
            id = bolsa.getInt("id");
            String nombre = bolsa.getString("nombre");
            String telefono = bolsa.getString("telefono");
            float peso = bolsa.getFloat("peso");
            float altura = bolsa.getFloat("altura");
            float imc = bolsa.getFloat("imc");
            int idObjetivoCliente = bolsa.getInt("idObjetivo");

            // Llenamos los campos de texto con los datos del objetivo
            txtNombre.setText(nombre);
            txtTelefono.setText(telefono);
            txtPeso.setText(String.valueOf(peso));
            textAltura.setText(String.valueOf(altura));
            txtIMC.setText(String.valueOf(imc));

            for (int j = 0; j < listaObjetivos.size(); j++) {
                if (listaObjetivos.get(j).getId() == idObjetivoCliente) {
                    spinnerObjetivos.setSelection(j); // Selecciona el objetivo en la posición adecuada
                    break;
                }
            }

            viewImc.setVisibility(View.VISIBLE);
            txtIMC.setVisibility(View.VISIBLE);
            btnGuardarCliente.setVisibility(View.GONE);
            btnActualizarCliente.setVisibility(View.VISIBLE);
            btnEliminarCliente.setVisibility(View.VISIBLE);
        }else{
            btnActualizarCliente.setVisibility(View.GONE);
            btnEliminarCliente.setVisibility(View.GONE);
            viewImc.setVisibility(View.GONE);
            txtIMC.setVisibility(View.GONE);
        }
        // Agregamos los listeners a los botones
        btnAgregarObjetivo.setOnClickListener(this);
        btnGuardarCliente.setOnClickListener(this);
        btnActualizarCliente.setOnClickListener(this);
        btnEliminarCliente.setOnClickListener(this);
    }

    // Método para cargar los objetivos existentes en el spinner
    private void cargarObjetivosEnSpinner() {
        listaObjetivos = objetivoController.obtenerObjetivos(); // Obtén los objetivos desde tu controlador
        // Crear un adaptador para el spinner usando Objetivo
        ArrayAdapter<Objetivo> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, listaObjetivos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerObjetivos.setAdapter(adapter);
    }

    private Cliente llenarDatosCliente() {
        // Crear una instancia del cliente pasando el contexto
        Cliente cliente = new Cliente(this);
        // Obtener los valores de los EditText
        String nombre = txtNombre.getText().toString();
        String telefono = txtTelefono.getText().toString();
        float peso = Float.parseFloat(txtPeso.getText().toString());
        float altura = Float.parseFloat(textAltura.getText().toString());

        // Calcular el IMC (Índice de Masa Corporal)
        float imc = peso / (altura * altura);

        // Obtener el objetivo seleccionado del Spinner
        Objetivo objetivoSeleccionado = (Objetivo) spinnerObjetivos.getSelectedItem();
         int idObjetivoSeleccionado = objetivoSeleccionado.getId();

        // Asignar los valores al cliente
        cliente.setId(id); // Suponiendo que 'id' es una variable que contiene el ID del cliente
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setPeso(peso);
        cliente.setAltura(altura);
        cliente.setImc(imc);
        cliente.setIdObjetivo(idObjetivoSeleccionado);
        return cliente;
    }
    // Método para guardar un nuevo cliente
    private void guardar() {
        Cliente cliente = llenarDatosCliente();
        ClienteController clienteController = new ClienteController(context);

        boolean resultado = clienteController.insertarCliente(cliente);
        if (resultado) {
            Toast.makeText(context, "Nuevo cliente agregado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaClienteActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al agregar el cliente", Toast.LENGTH_LONG).show();
        }
    }

    // Método para actualizar un cliente existente
    private void actualizar() {
        Cliente cliente = llenarDatosCliente();
        cliente.setId(id);  // El ID del cliente que estamos actualizando

        ClienteController clienteController = new ClienteController(context);

        boolean resultado = clienteController.actualizarCliente(cliente);
        if (resultado) {
            Toast.makeText(context, "Cliente actualizado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaClienteActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al actualizar el cliente", Toast.LENGTH_LONG).show();
        }
    }

    private void eliminar() {
        if (id == 0) {
            Toast.makeText(context, "No es posible eliminar un cliente sin ID", Toast.LENGTH_LONG).show();
            return; // Si el ID es 0, no continuamos.
        }
        ClienteController clienteController = new ClienteController(context);
        // Confirmar si se quiere eliminar
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cliente")
                .setMessage("¿Estás seguro de que deseas eliminar este cliente?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario confirma, eliminamos
                        boolean resultado = clienteController.eliminarCliente(id);

                        if (resultado) {
                            Toast.makeText(context, "Cliente eliminado", Toast.LENGTH_LONG).show();
                            // Redirigir a la lista de objetivos
                            Intent intent = new Intent(GestionarClienteActivity.this, ListaClienteActivity.class);
                            startActivity(intent);
                            finish(); // Finalizamos la actividad actual
                        } else {
                            Toast.makeText(context, "Error al eliminar el Cliente", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton(android.R.string.no, null) // Si el usuario cancela, no hacemos nada
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    @Override
    public void onClick(View view) {
        // Lógica para manejar clicks en botones si es necesario
        switch (view.getId()){
            case R.id.btnAgregarObjetivo:
                Toast.makeText(context, "Registrar Objetivo", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, com.example.personaltrainer.view.Objetivo.AgregarObjetivoActivity.class);
                startActivity(i);
                break;
            case R.id.btnGuardarCliente:
                guardar();
                break;
            case R.id.btnActualizarCliente:
                actualizar();
                break;
            case R.id.btnEliminarCliente:
                eliminar();
                break;
        }
    }
}