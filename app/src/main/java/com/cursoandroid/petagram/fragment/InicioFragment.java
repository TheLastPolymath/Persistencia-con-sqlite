package com.cursoandroid.petagram.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.petagram.R;
import com.cursoandroid.petagram.adapter.MascotaAdaptor;
import com.cursoandroid.petagram.db.ConstructorMascotas;

/**
 * Pestana Inicio (icono de casa): la lista de mascotas del ejercicio anterior,
 * ahora dentro de un Fragment.
 */
public class InicioFragment extends Fragment {

    private MascotaAdaptor adaptador;

    public InicioFragment() {
        // Constructor vacio requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_inicio, container, false);

        RecyclerView rvMascotas = v.findViewById(R.id.rvMascotas);
        rvMascotas.setLayoutManager(new LinearLayoutManager(getContext()));

        // Lista dummy con los raitings que quedaron guardados en SQLite
        adaptador = new MascotaAdaptor(new ConstructorMascotas(requireContext()).obtenerMascotas());
        rvMascotas.setAdapter(adaptador);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Si se dieron raitings desde Favoritos, refresca la lista al volver
        if (adaptador != null) adaptador.notifyDataSetChanged();
    }
}
