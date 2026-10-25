package com.cursoandroid.petagram.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.petagram.R;
import com.cursoandroid.petagram.adapter.PerfilAdaptor;
import com.cursoandroid.petagram.datos.DatosDummy;
import com.cursoandroid.petagram.pojo.Mascota;
import com.google.android.material.imageview.ShapeableImageView;

/**
 * Pestana Perfil (icono de perro): foto de perfil, nombre y un Grid con las fotos
 * que ha subido la mascota y los raits de cada una.
 */
public class PerfilFragment extends Fragment {

    private static final int COLUMNAS_GRID = 3;

    public PerfilFragment() {
        // Constructor vacio requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_perfil, container, false);

        Mascota mascota = DatosDummy.getMascotaPerfil();

        ShapeableImageView imgPerfil = v.findViewById(R.id.imgPerfil);
        imgPerfil.setImageResource(mascota.getFoto());
        imgPerfil.setBackgroundColor(ContextCompat.getColor(requireContext(), mascota.getColorFondo()));

        TextView tvNombre = v.findViewById(R.id.tvNombrePerfil);
        tvNombre.setText(mascota.getNombre());

        RecyclerView rvFotos = v.findViewById(R.id.rvFotosPerfil);
        rvFotos.setLayoutManager(new GridLayoutManager(getContext(), COLUMNAS_GRID));
        rvFotos.setAdapter(new PerfilAdaptor(DatosDummy.getFotosPerfil()));

        return v;
    }
}
