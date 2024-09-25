package com.example.personaltrainer.controller;

import android.content.Context;

import com.example.personaltrainer.conexion.DBHelper;
import com.example.personaltrainer.model.CategoriaModel;

import java.util.ArrayList;

public class CategoriaController {
    private DBHelper dbHelper;
    private Context context;

    public CategoriaController(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }
    public ArrayList<CategoriaModel>obtenerCategorias(){
        CategoriaModel categoriaModel = new CategoriaModel(context);
        return categoriaModel.obtenerTodosLasCategorias();
    }
    public boolean insertarCategoria(CategoriaModel categoriaModel){
        try {
            categoriaModel.insertarCategoria();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarCategoria(CategoriaModel categoriaModel) {
        try {
            categoriaModel.actualizarCategoria();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarCategoria(int id) {
        try {
            CategoriaModel categoriaModel = new CategoriaModel(context);
            categoriaModel.setId(id);
            categoriaModel.eliminarCategoria();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public CategoriaModel obtenerCategoriaPorId(int id) {
        CategoriaModel categoriaModel = new CategoriaModel(context);
        return categoriaModel.obtenerCategoriaPorId(id);
    }
}
