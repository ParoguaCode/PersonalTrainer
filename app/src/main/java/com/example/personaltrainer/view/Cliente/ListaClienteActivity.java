package com.example.personaltrainer.view.Cliente;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.personaltrainer.R;

import com.example.personaltrainer.controller.ClienteControllerProxy;
import com.example.personaltrainer.model.Cliente;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ListaClienteActivity extends AppCompatActivity {

    private FloatingActionButton btnAgregarCliente;
    ListView listView;
    ArrayList<String> nombreCliente;
    ArrayList<Integer> idClientes;
    ClienteControllerProxy clienteControllerProxy;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        clienteControllerProxy = new ClienteControllerProxy(this);
        init();
    }

    private void init() {
        context = this.getApplicationContext();
        btnAgregarCliente = findViewById(R.id.btnAgregarCliente);
        listView = findViewById(R.id.listaClientes);

        llenarListView();

        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ListaClienteActivity.this, GestionarClienteActivity.class));
            }
        });
    }

    private void llenarListView() {
        nombreCliente = new ArrayList<>();
        idClientes = new ArrayList<>();

        List<Cliente> listaCliente = clienteControllerProxy.obtenerTodosLosClientes();
        for (Cliente cliente : listaCliente) {
            nombreCliente.add(cliente.getNombre());
            idClientes.add(cliente.getId());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                ListaClienteActivity.this,
                android.R.layout.simple_list_item_1,
                nombreCliente
        );
        listView.setAdapter(adapter);
        // en ves de usar selectListenerCliente
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cliente cliente = listaCliente.get(i);
                Bundle bolsa = new Bundle();
                bolsa.putInt("id", cliente.getId());
                bolsa.putString("nombre", cliente.getNombre());
                bolsa.putString("telefono", cliente.getTelefono());
                bolsa.putFloat("peso", cliente.getPeso());
                bolsa.putFloat("altura", cliente.getAltura());
                bolsa.putFloat("imc", cliente.getImc());

                bolsa.putInt("idObjetivo", cliente.getIdObjetivo()); // Si es necesario

                Intent intent = new Intent(ListaClienteActivity.this, GestionarClienteActivity.class);
                intent.putExtras(bolsa);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Volver a llenar la lista cuando se regrese a esta actividad
        llenarListView();
    }
}
