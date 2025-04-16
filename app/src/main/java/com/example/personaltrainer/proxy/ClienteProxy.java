package com.example.personaltrainer.proxy;

import com.example.personaltrainer.model.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteProxy implements ClienteInterface {
    private Cliente servicioReal;

    public ClienteProxy(Cliente servicioReal) {
        this.servicioReal = servicioReal;
    }

    @Override
    public void insertarCliente(Cliente cliente) {
        System.out.println("Proxy: Validando inserción de cliente...");
        // insertar validacion de datos
        servicioReal.insertarCliente(cliente);
    }

    @Override
    public void actualizarCliente(Cliente cliente) {
        System.out.println("Proxy: Validando actualización de cliente...");
        // Validación o reglas de negocio adicionales antes de proceder
        servicioReal.actualizarCliente(cliente);
    }

    @Override
    public void eliminarCliente(int id) {
        System.out.println("Proxy: Validando eliminación de cliente...");
        // verificar permisos para eliminar al cliente
        servicioReal.eliminarCliente(id);
    }

    @Override
    public Cliente obtenerClientePorId(int id) {
        System.out.println("Proxy: Validando acceso para obtener cliente...");
        return servicioReal.obtenerClientePorId(id);
    }

    @Override
    public ArrayList<Cliente> obtenerTodosLosClientes() {
        System.out.println("Proxy: Validando acceso para obtener todos los clientes...");
        return servicioReal.obtenerTodosLosClientes();
    }

}
