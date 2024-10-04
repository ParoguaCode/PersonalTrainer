package com.example.personaltrainer.view.Rutina;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.personaltrainer.R;
import com.example.personaltrainer.model.Ejercicio;
import com.example.personaltrainer.model.EjercicioRutina;

import java.util.ArrayList;

public class GestionarRutinaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rutina);
    }
}/*
public class GestionarRutinaActivity extends AppCompatActivity {

    private Spinner spinnerTipoRutina;
    private ListView listaEjercicios, listaEjerciciosRutina;
    private EditText txtRepeticiones;
    private Button btnAgregarEjercicioARutina, btnGuardarRutina;
    Ejercicio ejercicio = new Ejercicio();
    ArrayList<Ejercicio> ejerciciosDisponibles;
    private ArrayList<EjercicioRutina> ejerciciosEnRutina;
    private ArrayAdapter<Ejercicio> adapterEjerciciosDisponibles;
    private ArrayAdapter<EjercicioRutina> adapterEjerciciosRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_rutina);

        init();
    }

    private void init() {
        spinnerTipoRutina = findViewById(R.id.spinnerTipoRutina);
        listaEjercicios = findViewById(R.id.listaEjercicios);
        listaEjerciciosRutina = findViewById(R.id.listaEjerciciosRutina);
        txtRepeticiones = findViewById(R.id.txtRepeticiones);
        btnAgregarEjercicioARutina = findViewById(R.id.btnAgregarEjercicioARutina);
        btnGuardarRutina = findViewById(R.id.btnGuardarRutina);

        // Inicializar la lista de ejercicios
        ejerciciosDisponibles = ejercicio.obtenerTodosLosEjercicios(); // Método que obtendrá los ejercicios desde tu base de datos
        ejerciciosEnRutina = new ArrayList<>();

        // Adaptador para ejercicios disponibles
        adapterEjerciciosDisponibles = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, ejerciciosDisponibles);
        listaEjercicios.setAdapter(adapterEjerciciosDisponibles);
        listaEjercicios.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        // Adaptador para ejercicios en la rutina
        adapterEjerciciosRutina = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ejerciciosEnRutina);
        listaEjerciciosRutina.setAdapter(adapterEjerciciosRutina);

        // Agregar ejercicio a la rutina con las repeticiones seleccionadas
        btnAgregarEjercicioARutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarEjercicioARutina();
            }
        });

        // Guardar la rutina
        btnGuardarRutina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarRutina();
            }
        });
    }

    private void agregarEjercicioARutina() {
        int repeticiones = Integer.parseInt(txtRepeticiones.getText().toString());

        for (int i = 0; i < listaEjercicios.getCount(); i++) {
            if (listaEjercicios.isItemChecked(i)) {
                Ejercicio ejercicioSeleccionado = ejerciciosDisponibles.get(i);
                EjercicioRutina ejercicioRutina = new EjercicioRutina(ejercicioSeleccionado, repeticiones);
                ejerciciosEnRutina.add(ejercicioRutina);
            }
        }

        adapterEjerciciosRutina.notifyDataSetChanged();
    }

    private void guardarRutina() {
        // Aquí implementas la lógica para guardar la rutina completa
    }
}
*/