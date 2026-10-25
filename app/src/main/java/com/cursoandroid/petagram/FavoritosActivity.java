package com.cursoandroid.petagram;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cursoandroid.petagram.adapter.MascotaAdaptor;
import com.cursoandroid.petagram.db.ConstructorMascotas;
import com.cursoandroid.petagram.pojo.Mascota;

import java.util.ArrayList;

/**
 * Ultimas 5 mascotas que recibieron rating, leidas desde la base de datos SQLite.
 * Si se da rating aqui mismo, la lista se vuelve a leer para reflejar el nuevo orden.
 */
public class FavoritosActivity extends AppCompatActivity {

    private ConstructorMascotas constructor;
    private MascotaAdaptor adaptador;
    private TextView tvSinFavoritas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        Toolbar toolbar = findViewById(R.id.toolbarSimple);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.titulo_favoritos);
            ab.setSubtitle(R.string.subtitulo_favoritos);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        tvSinFavoritas = findViewById(R.id.tvSinFavoritas);

        constructor = new ConstructorMascotas(this);
        adaptador = new MascotaAdaptor(new ArrayList<>());
        adaptador.setOnRaitingListener(mascota -> cargarFavoritas());

        RecyclerView rvFavoritas = findViewById(R.id.rvFavoritas);
        rvFavoritas.setLayoutManager(new LinearLayoutManager(this));
        rvFavoritas.setAdapter(adaptador);

        cargarFavoritas();
    }

    private void cargarFavoritas() {
        ArrayList<Mascota> ultimas = constructor.obtenerUltimasMascotas();
        adaptador.actualizarDatos(ultimas);
        tvSinFavoritas.setVisibility(ultimas.isEmpty() ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
