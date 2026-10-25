package com.cursoandroid.petagram.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.petagram.R;
import com.cursoandroid.petagram.db.ConstructorMascotas;
import com.cursoandroid.petagram.pojo.Mascota;

import java.util.ArrayList;

/**
 * Adaptador de las tarjetas grandes de mascota (pestana Inicio y pantalla Favoritos).
 */
public class MascotaAdaptor extends RecyclerView.Adapter<MascotaAdaptor.MascotaViewHolder> {

    /** Aviso opcional para la pantalla cuando se da un rating (Favoritos lo usa para recargar). */
    public interface OnRaitingListener {
        void onRaitingDado(Mascota mascota);
    }

    private final ArrayList<Mascota> mascotas;
    private OnRaitingListener raitingListener;

    public MascotaAdaptor(ArrayList<Mascota> mascotas) {
        this.mascotas = mascotas;
    }

    public void setOnRaitingListener(OnRaitingListener listener) {
        this.raitingListener = listener;
    }

    /** Reemplaza los datos mostrados (por ejemplo, despues de releer la base de datos). */
    public void actualizarDatos(ArrayList<Mascota> nuevas) {
        mascotas.clear();
        mascotas.addAll(nuevas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MascotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_mascota, parent, false);
        return new MascotaViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MascotaViewHolder holder, int position) {
        Mascota mascota = mascotas.get(position);

        holder.imgFoto.setImageResource(mascota.getFoto());
        holder.imgFoto.setBackgroundColor(colorSeguro(holder.itemView.getContext(), mascota.getColorFondo()));
        holder.tvNombre.setText(mascota.getNombre());
        holder.tvRaiting.setText(String.valueOf(mascota.getRaiting()));

        holder.btnRaiting.setOnClickListener(v -> {
            int pos = holder.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return;

            Mascota m = mascotas.get(pos);
            // Suma el rating y guarda la entidad completa en SQLite
            new ConstructorMascotas(v.getContext()).darRaiting(m);
            notifyItemChanged(pos);
            Toast.makeText(v.getContext(),
                    v.getContext().getString(R.string.raiting_dado, m.getNombre()),
                    Toast.LENGTH_SHORT).show();

            if (raitingListener != null) raitingListener.onRaitingDado(m);
        });
    }

    /**
     * Los ids de recursos (R.color / R.drawable) que guarda SQLite pueden cambiar si agregas
     * recursos nuevos y recompilas. Si el color guardado ya no existe, se usa el primario
     * en lugar de cerrar la app.
     */
    private static int colorSeguro(Context context, int colorRes) {
        try {
            return ContextCompat.getColor(context, colorRes);
        } catch (Resources.NotFoundException e) {
            return ContextCompat.getColor(context, R.color.colorPrimary);
        }
    }

    @Override
    public int getItemCount() {
        return mascotas.size();
    }

    static class MascotaViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgFoto;
        final ImageButton btnRaiting;
        final TextView tvNombre;
        final TextView tvRaiting;

        MascotaViewHolder(@NonNull View itemView) {
            super(itemView);
            imgFoto = itemView.findViewById(R.id.imgFoto);
            btnRaiting = itemView.findViewById(R.id.btnRaiting);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvRaiting = itemView.findViewById(R.id.tvRaiting);
        }
    }
}
