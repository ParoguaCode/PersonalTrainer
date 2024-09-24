package com.example.personaltrainer.model;

public class Objetivo {
    private int id;
    private String nombre;

    public Objetivo() {
    }

    // Constructor completo
    public Objetivo(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre != null && !nombre.isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre no puede ser nulo o vacío");
        }
    }

    @Override
    public String toString() {
        return "Objetivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}