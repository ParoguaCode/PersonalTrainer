package com.example.personaltrainer.view.Ejercicio;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.personaltrainer.R;
import com.example.personaltrainer.model.Ejercicio;

import java.util.ArrayList;

public class EjercicioAdapter extends RecyclerView.Adapter<EjercicioAdapter.ViewHolder> {
    ArrayList<Ejercicio> listaEjercicios;
    Context context;

    public EjercicioAdapter(ArrayList<Ejercicio> listaEjercicios, Context context) {
        this.listaEjercicios = listaEjercicios;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list_ejercicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ejercicio ejercicio = listaEjercicios.get(position);

        holder.txtNombreEjercicio.setText(ejercicio.getNombre());
        holder.txtCategoriaEjercicio.setText(ejercicio.getCategoriaNombre() != null ? ejercicio.getCategoriaNombre() : "Categoría desconocida");

        Uri imagenUri = ejercicio.getImagenUri();
        if (imagenUri != null) {
            // Usar Glide para cargar la imagen desde el URI
            Glide.with(context)
                    .load(imagenUri) // Carga la imagen usando el URI
                    .placeholder(R.drawable.ic_launcher_background) // Imagen por defecto mientras carga
                    .error(R.drawable.ic_launcher_background) // Imagen por defecto en caso de error
                    .into(holder.imgEjercicio); // ImagenView donde se mostrará la imagen
        } else {
            holder.imgEjercicio.setImageResource(R.drawable.ic_launcher_background);
        }
        // Configurar listener de clic para mostrar detalles
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtener la posición actual con getAdapterPosition()
                int currentPosition = holder.getAdapterPosition();

                // Verificar que la posición no sea inválida (por si el elemento se eliminó)
                if (currentPosition != RecyclerView.NO_POSITION) {
                    // Usar la posición actual para obtener el ejercicio correspondiente
                    Ejercicio ejercicio = listaEjercicios.get(currentPosition);

                    // Crear un Bundle para pasar los datos del ejercicio a la siguiente actividad
                    Bundle bolsa = new Bundle();
                    bolsa.putInt("id", ejercicio.getId());
                    bolsa.putString("nombre", ejercicio.getNombre());
                    bolsa.putString("descripcion", ejercicio.getDescripcion()); // Asegúrate de tener este método en tu clase Ejercicio
                    bolsa.putString("categoriaEjercicio", ejercicio.getCategoriaNombre());
                    bolsa.putInt("idCategoria", ejercicio.getIdCategoria()); // Agrega este campo también
                    bolsa.putString("imagen", ejercicio.getImagenUri() != null ? ejercicio.getImagenUri().toString() : null);


                    // Crear un Intent para abrir la nueva actividad de detalles
                    Intent intent = new Intent(context, GestionarEjercicioActivity.class);
                    intent.putExtras(bolsa); // Pasar el Bundle al Intent

                    // Iniciar la nueva actividad
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaEjercicios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtNombreEjercicio, txtCategoriaEjercicio;
        ImageView imgEjercicio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreEjercicio = itemView.findViewById(R.id.txtNombreEjercicio);
            txtCategoriaEjercicio = itemView.findViewById(R.id.txtCategoriaEjercicio);
            imgEjercicio = itemView.findViewById(R.id.imgEjercicio);
        }
    }
}
