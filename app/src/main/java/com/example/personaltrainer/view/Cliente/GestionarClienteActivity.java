package com.example.personaltrainer.view.Cliente;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.ClienteControllerProxy;
import com.example.personaltrainer.controller.ObjetivoController;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Objetivo;
import com.example.personaltrainer.strategy.ContextoEstrategia;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.text.TextWatcher;
import android.text.Editable;

public class GestionarClienteActivity extends AppCompatActivity implements View.OnClickListener {
    Context context;
    Spinner spinnerObjetivos;
    Button btnGuardarCliente, btnAgregarObjetivo, btnActualizarCliente, btnEliminarCliente;
    EditText txtNombre, txtTelefono, txtPeso, textAltura, txtIMC, txtSugerencia;
    TextView viewImc;
    ObjetivoController objetivoController;
    ContextoEstrategia contextoEstrategia;
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
        txtSugerencia = findViewById(R.id.txtSugerencia);

        //---------------------
        txtPeso.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                calcularIMC();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //-------------------------------
        objetivoController = new ObjetivoController(this);
        contextoEstrategia = new ContextoEstrategia();
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

//            viewImc.setVisibility(View.VISIBLE);
//            txtIMC.setVisibility(View.VISIBLE);

            btnGuardarCliente.setVisibility(View.GONE);
            btnActualizarCliente.setVisibility(View.VISIBLE);
            btnEliminarCliente.setVisibility(View.VISIBLE);
        }else{
            btnActualizarCliente.setVisibility(View.GONE);
            btnEliminarCliente.setVisibility(View.GONE);
//            viewImc.setVisibility(View.GONE);
//            txtIMC.setVisibility(View.GONE);
        }
        // Agregamos los listeners a los botones
        btnAgregarObjetivo.setOnClickListener(this);
        btnGuardarCliente.setOnClickListener(this);
        btnActualizarCliente.setOnClickListener(this);
        btnEliminarCliente.setOnClickListener(this);
    }


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
        float peso = 0, altura = 0;

        // Verificar si los valores de peso y altura no son vacíos
        try {
            peso = Float.parseFloat(txtPeso.getText().toString());
            altura = Float.parseFloat(textAltura.getText().toString());
        } catch (NumberFormatException e) {
            // Si los campos no contienen números válidos, podrías manejarlo aquí
            Toast.makeText(context, "Por favor ingrese valores válidos en peso y altura.", Toast.LENGTH_SHORT).show();
        }

        // Calcular el IMC (Índice de Masa Corporal)
        Objetivo objetivoSeleccionado = (Objetivo) spinnerObjetivos.getSelectedItem();
        int idObjetivoSeleccionado = objetivoSeleccionado != null ? objetivoSeleccionado.getId() : 0;

        // Establecer los valores en el cliente
        cliente.setId(id); // Suponiendo que 'id' es una variable que contiene el ID del cliente
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setPeso(peso);
        cliente.setAltura(altura);
//        cliente.setImc(imc);
        cliente.setIdObjetivo(idObjetivoSeleccionado);

        // Imprimir los valores para verificar
        Log.d("Cliente", "Nombre: " + nombre + ", Telefono: " + telefono + ", Peso: " + peso + ", Altura: " + altura + ", Objetivo: " + idObjetivoSeleccionado);

        return cliente;
    }

    private void guardar() {
        Cliente cliente = llenarDatosCliente();
        ClienteControllerProxy clienteControllerProxy = new ClienteControllerProxy(context);
        String imcString = txtIMC.getText().toString();  // Ejemplo: "IMC: 24.973986"

        // Elimina el texto "IMC: " dejando solo el número
        String soloNumeros = imcString.replaceAll("[^\\d.]", "");  // Esto eliminará todo lo que no sea un número o un punto decimal

        // Convierte el número extraído a float
        float imc = Float.parseFloat(soloNumeros);
        boolean resultado = clienteControllerProxy.insertarCliente(cliente);
        if (resultado) {
            Toast.makeText(context, "Nuevo cliente agregado", Toast.LENGTH_LONG).show();
            aplicarEstrategiaObjetivo(imc);
            Intent intent = new Intent(context, ListaClienteActivity.class);
            startActivity(intent);
            finish(); // Finalizar la actividad actual si es necesario
        } else {
            Toast.makeText(context, "Error al agregar el cliente", Toast.LENGTH_LONG).show();
        }
    }

    private void actualizar() {
        Cliente cliente = llenarDatosCliente();
        cliente.setId(id);  // El ID del cliente que estamos actualizando

        ClienteControllerProxy clienteControllerProxy = new ClienteControllerProxy(context);
        String imcString = txtIMC.getText().toString();  // Ejemplo: "IMC: 24.973986"

        // Elimina el texto "IMC: " dejando solo el número
        String soloNumeros = imcString.replaceAll("[^\\d.]", "");  // Esto eliminará todo lo que no sea un número o un punto decimal

        // Convierte el número extraído a float
        float imc = Float.parseFloat(soloNumeros);

        boolean resultado = clienteControllerProxy.actualizarCliente(cliente);
        if (resultado) {
            Toast.makeText(context, "Cliente actualizado", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, ListaClienteActivity.class);
            startActivity(intent);

            aplicarEstrategiaObjetivo(imc);
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
        ClienteControllerProxy clienteControllerProxy = new ClienteControllerProxy(context);
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Cliente")
                .setMessage("¿Estás seguro de que deseas eliminar este cliente?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Si el usuario confirma, eliminamos
                        boolean resultado = clienteControllerProxy.eliminarCliente(id);

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

    private Cliente obtenerClienteDesdeFormulario() {
        Cliente cliente = new Cliente(this);

        // Obtener los valores del formulario (EditText)
        String nombre = txtNombre.getText().toString();
        String telefono = txtTelefono.getText().toString();
        float peso = 0, altura = 0;

        // Verificar si los valores de peso y altura no son vacíos
        try {
            peso = Float.parseFloat(txtPeso.getText().toString());
            altura = Float.parseFloat(textAltura.getText().toString());
        } catch (NumberFormatException e) {
            // Si los campos no contienen números válidos, manejar la excepción
            Toast.makeText(context, "Por favor ingrese valores válidos en peso y altura.", Toast.LENGTH_SHORT).show();
        }

        // Obtener el objetivo seleccionado
        Objetivo objetivoSeleccionado = (Objetivo) spinnerObjetivos.getSelectedItem();
        int idObjetivoSeleccionado = objetivoSeleccionado != null ? objetivoSeleccionado.getId() : 0;

        // Establecer los valores en el cliente
        cliente.setId(id); // Suponiendo que 'id' es una variable que contiene el ID del cliente
        cliente.setNombre(nombre);
        cliente.setTelefono(telefono);
        cliente.setPeso(peso);
        cliente.setAltura(altura);
        cliente.setIdObjetivo(idObjetivoSeleccionado);

        // Devolver el objeto cliente
        return cliente;
    }

    private void aplicarEstrategiaObjetivo(float imc) {

        Cliente cliente = obtenerClienteDesdeFormulario();
        // Selecciona la estrategia según el IMC
        objetivoController.seleccionarEstrategia(imc,cliente);
    }
    private void calcularIMC() {
        try {
            float peso = Float.parseFloat(txtPeso.getText().toString());
            float altura = Float.parseFloat(textAltura.getText().toString());

            // Fórmula IMC: IMC = peso / (altura^2)
            float imc = peso / (altura * altura);
            txtIMC.setText("IMC: " + imc);
            // Sugerir objetivo basado en el IMC
            String objetivoSugerido = objetivoController.sugerirObjetivo(imc);
            txtSugerencia.setText("Objetivo sugerido: " + objetivoSugerido);

            //aplicarEstrategiaObjetivo(imc);
        } catch (NumberFormatException e) {
            // Si el campo está vacío o tiene datos no válidos, manejamos la excepción
            txtIMC.setText("IMC: rellene todos los campos");
        }
    }

    @Override
    public void onClick(View view) {
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