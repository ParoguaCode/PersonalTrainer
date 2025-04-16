package com.example.personaltrainer.controller;

import android.content.Context;

import com.example.personaltrainer.model.Cliente;
import com.example.personaltrainer.proxy.ClienteInterface;

import java.util.ArrayList;
import java.util.List;
public class ClienteControllerProxy{
    private ClienteInterface clienteInterface;

    public ClienteControllerProxy(Context context){
        this.clienteInterface = new Cliente(context);
    }
    public boolean insertarCliente(Cliente cliente) {
        try {
            clienteInterface.insertarCliente(cliente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCliente(Cliente cliente) {
        try {
            clienteInterface.actualizarCliente(cliente);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCliente(int id) {
        try {
            clienteInterface.eliminarCliente(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public Cliente obtenerClientePorId(int id) {
        try {
            return clienteInterface.obtenerClientePorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Cliente> obtenerTodosLosClientes() {
        try {
            ArrayList<Cliente> listaClientes = clienteInterface.obtenerTodosLosClientes();
            // Validar que la lista no sea nula
            if (listaClientes == null) {
                System.out.println("llega");
                listaClientes = new ArrayList<>(); // Retornar una lista vacía
            }
            return listaClientes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Retornar una lista vacía si ocurre un error
        }
    }
}
