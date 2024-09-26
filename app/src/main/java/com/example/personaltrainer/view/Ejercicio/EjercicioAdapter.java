package com.example.personaltrainer.view.Ejercicio;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        // Obtener el ejercicio actual
        Ejercicio ejercicio = listaEjercicios.get(position);

        // Asignar los valores a las vistas
        holder.txtNombreEjercicio.setText(ejercicio.getNombre());
        holder.txtCategoriaEjercicio.setText(ejercicio.getCategoriaNombre());

        // Si la imagen está presente, cargarla (debe ajustarse según cómo se almacene la imagen)
        if (ejercicio.getImagenUri() != null) {
            holder.imgEjercicio.setImageURI(ejercicio.getImagenUri());
        } else {
            holder.imgEjercicio.setImageResource(R.drawable.ic_launcher_background);
        }
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
