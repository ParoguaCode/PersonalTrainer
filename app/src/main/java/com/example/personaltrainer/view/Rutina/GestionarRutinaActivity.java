package com.example.personaltrainer.view.Rutina;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

import com.example.personaltrainer.R;
import com.example.personaltrainer.controller.ClienteControllerProxy;
import com.example.personaltrainer.controller.EjercicioController;
import com.example.personaltrainer.controller.RutinaController;
import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.model.Ejercicio;

public class GestionarRutinaActivity extends AppCompatActivity {
    private EditText txtNombreRutina;
    private Spinner spinnerClientes, spinnerEjercicios;
    private EditText txtRepeticiones;
    private Button btnAgregarEjercicio, btnGuardarRutina;
    private ListView listaEjerciciosRutina;
    private List<Cliente> listaClientes;

    private ArrayAdapter<String>  EjercicioAdapter;
    private ArrayList<String> ejerciciosSeleccionados;
    private ArrayAdapter<String> adapterEjerciciosRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rutina);

        // Inicialización de vistas
        txtNombreRutina = findViewById(R.id.txtNombreRutina);
        spinnerClientes = findViewById(R.id.spinnerNombreCliente);
        spinnerEjercicios = findViewById(R.id.spinnerEjercicio);
        txtRepeticiones = findViewById(R.id.txtRepeticiones);
        btnAgregarEjercicio = findViewById(R.id.btnAgregarEjercicioARutina);
        btnGuardarRutina = findViewById(R.id.btnGuardarRutina);
        listaEjerciciosRutina = findViewById(R.id.listaEjerciciosRutina);

        // Inicializar listas
        ejerciciosSeleccionados = new ArrayList<>();

        // Configurar adaptadores

        EjercicioAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        EjercicioAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEjercicios.setAdapter(EjercicioAdapter);

        adapterEjerciciosRutina = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ejerciciosSeleccionados);
        listaEjerciciosRutina.setAdapter(adapterEjerciciosRutina);

        // Cargar datos de la base de datos
        cargarClientes();
        cargarEjercicios();

        // Configurar eventos
        btnAgregarEjercicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agregarEjercicioARutina();
            }
        });

        btnGuardarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarRutina();
            }
        });
    }

    private void cargarClientes() {
        ClienteControllerProxy clienteControllerProxy = new ClienteControllerProxy(this);
        listaClientes = clienteControllerProxy.obtenerTodosLosClientes();
        // Crear una lista de nombres para mostrar
        List<String> nombresClientes = new ArrayList<>();
        for (Cliente cliente : listaClientes) {
            nombresClientes.add(cliente.getNombre());
        }

        // Configurar el Spinner
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nombresClientes);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerClientes.setAdapter(spinnerAdapter);
    }


    private void cargarEjercicios() {
        EjercicioController ejercicioController = new EjercicioController(this);
        List<Ejercicio> ejercicios = ejercicioController.obtenerTodosLosEjercicios();

        EjercicioAdapter.clear();
        for (Ejercicio ejercicio : ejercicios) {
            EjercicioAdapter.add(ejercicio.getNombre());
        }
        EjercicioAdapter.notifyDataSetChanged();
    }

    private void agregarEjercicioARutina() {
        String ejercicioSeleccionado = spinnerEjercicios.getSelectedItem().toString();
        String repeticiones = txtRepeticiones.getText().toString();

        if (ejercicioSeleccionado.isEmpty() || repeticiones.isEmpty()) {
            Toast.makeText(this, "Debe seleccionar un ejercicio y especificar las repeticiones", Toast.LENGTH_SHORT).show();
            return;
        }

        ejerciciosSeleccionados.add(ejercicioSeleccionado + " - " + repeticiones + " rep.");
        adapterEjerciciosRutina.notifyDataSetChanged();
        txtRepeticiones.setText("");
    }

    private void guardarRutina() {
        String nombreRutina = txtNombreRutina.getText().toString();
        String clienteSeleccionado = spinnerClientes.getSelectedItem().toString();

        if (nombreRutina.isEmpty() || clienteSeleccionado.isEmpty() || ejerciciosSeleccionados.isEmpty()) {
            Toast.makeText(this, "Debe completar todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Obtener el ID del cliente seleccionado
        Cliente clienteSeleccionadoObj = listaClientes.get(spinnerClientes.getSelectedItemPosition());
        int idCliente = clienteSeleccionadoObj.getId();

        // Crear un objeto de la rutina
        RutinaController rutinaController = new RutinaController(this);
        long idRutina = rutinaController.crearRutina(nombreRutina, idCliente);

        // Verificar si la rutina se insertó correctamente
        if (idRutina == -1) {  // Si idRutina es -1, significa que hubo un error al insertar la rutina
            Toast.makeText(this, "Error al guardar la rutina", Toast.LENGTH_SHORT).show();
            return;
        }

        // Insertar ejercicios en la tabla EjercicioRutina
        boolean exito = true;
        for (String ejercicioSeleccionado : ejerciciosSeleccionados) {
            // Separar el nombre del ejercicio y las repeticiones
            String[] partes = ejercicioSeleccionado.split(" - ");
            String nombreEjercicio = partes[0].trim();
            String repeticiones = partes[1].replace(" rep.", "").trim();

            // Obtener el ID del ejercicio
            Ejercicio ejercicioObj = rutinaController.obtenerEjercicioPorNombre(nombreEjercicio);
            if (ejercicioObj != null) {
                int idEjercicio = ejercicioObj.getId();

                // Insertar la relación Ejercicio-Rutina
                exito = rutinaController.insertarEjercicioEnRutina((int) idRutina, idEjercicio, repeticiones);
                if (!exito) break;  // Si ocurre un error, salimos del bucle
            } else {
                exito = false;
                break;  // Si no se encuentra el ejercicio, salimos del bucle
            }
        }

        // Validar el resultado de la inserción
        if (exito) {
            Toast.makeText(this, "Rutina guardada exitosamente", Toast.LENGTH_SHORT).show();
            finish();  // Cierra la actividad
        } else {
            Toast.makeText(this, "Error al guardar los ejercicios de la rutina", Toast.LENGTH_SHORT).show();
        }
    }


}
