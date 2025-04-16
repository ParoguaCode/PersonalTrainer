package com.example.personaltrainer.proxy;

import com.example.personaltrainer.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public interface ClienteInterface {
    void insertarCliente(Cliente cliente);
    void actualizarCliente(Cliente cliente);
    void eliminarCliente(int id);
    Cliente obtenerClientePorId(int id);
    ArrayList<Cliente> obtenerTodosLosClientes();
}
