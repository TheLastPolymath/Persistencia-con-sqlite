package com.cursoandroid.petagram.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.petagram.R;
import com.cursoandroid.petagram.pojo.FotoPerfil;

import java.util.ArrayList;

/**
 * Adaptador del Grid de fotos del perfil: cada item muestra la foto y sus raits (huesos).
 */
public class PerfilAdaptor extends RecyclerView.Adapter<PerfilAdaptor.FotoViewHolder> {

    private final ArrayList<FotoPerfil> fotos;

    public PerfilAdaptor(ArrayList<FotoPerfil> fotos) {
        this.fotos = fotos;
    }

    @NonNull
    @Override
    public FotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_foto_perfil, parent, false);
        return new FotoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FotoViewHolder holder, int position) {
        FotoPerfil foto = fotos.get(position);
        holder.imgFoto.setImageResource(foto.getFoto());
        holder.imgFoto.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.getContext(), foto.getColorFondo()));
        holder.tvRaiting.setText(String.valueOf(foto.getRaiting()));
    }

    @Override
    public int getItemCount() {
        return fotos.size();
    }

    static class FotoViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgFoto;
        final TextView tvRaiting;

        FotoViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgFotoPerfil);
            tvRaiting = itemView.findViewById(R.id.tvRaitingFoto);
        }
    }
}
