package com.example.personaltrainer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Context context;
    Button btnModuloCliente, btnModuloCatalogo, btnModuloRutina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init(){
        context = getApplicationContext();

        btnModuloCliente = findViewById(R.id.btnModuloCliente);
        btnModuloCatalogo = findViewById(R.id.btnModuloCatalogo);
        btnModuloRutina = findViewById(R.id.btnModuloRutina);

        // Configura los listeners de clic para cada botón
        btnModuloCliente.setOnClickListener(this);
        btnModuloCatalogo.setOnClickListener(this);
        btnModuloRutina.setOnClickListener(this);
    }

    private void loadClientes(){

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnModuloCliente:
                Toast.makeText(context, "ListaCliente", Toast.LENGTH_LONG).show();
                Intent i = new Intent(context, com.example.personaltrainer.view.Cliente.ListaClienteActivity.class);
                startActivity(i);
                break;
            case R.id.btnModuloCatalogo:
                Toast.makeText(context, "ListaEjercicio", Toast.LENGTH_LONG).show();
                Intent i2 = new Intent(context, com.example.personaltrainer.view.Categoria.ListaCategoriaActivity.class);
                startActivity(i2);
                break;
            case R.id.btnModuloRutina:
                Toast.makeText(context, "ListaRutina", Toast.LENGTH_LONG).show();
                // Intent i3 = new Intent(context, com.example.entrenadorpersonal.view.cliente.ClienteActivity.class);
                // startActivity(i3);
                break;
        }
    }

}
