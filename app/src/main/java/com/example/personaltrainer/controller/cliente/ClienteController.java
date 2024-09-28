package com.example.personaltrainer.controller.cliente;

import android.content.Context;
import com.example.personaltrainer.model.Cliente;
import java.util.ArrayList;

public class ClienteController {
    private Context context;

    // Constructor
    public ClienteController(Context context) {
        this.context = context;
    }
    // Obtener todos los clientes
    public ArrayList<Cliente> obtenerClientes() {
        Cliente cliente = new Cliente(context);
        return cliente.obtenerTodosLosClientes();
    }
    // Insertar un cliente
    public boolean insertarCliente(Cliente cliente) {
        try {
            cliente.insertarCliente();
            return true;
        } catch (Exception e) {
            e.printStackTrace();// Capturamos e imprimimos errores
            return false;
        }
    }

    // Actualizar un cliente
    public boolean actualizarCliente(Cliente cliente) {
        try {
            cliente.actualizarCliente();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Eliminar un cliente por ID
    public boolean eliminarCliente(int id) {
        try {
            Cliente cliente = new Cliente(context);
            cliente.setId(id);
            cliente.eliminarCliente();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Obtener un cliente por su ID
    public Cliente obtenerClientePorId(int id) {
        Cliente cliente = new Cliente(context);
        return cliente.obtenerClientePorId(id);
    }
}
