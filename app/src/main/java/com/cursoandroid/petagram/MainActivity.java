package com.cursoandroid.petagram;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.cursoandroid.petagram.adapter.PageAdapter;
import com.cursoandroid.petagram.fragment.InicioFragment;
import com.cursoandroid.petagram.fragment.PerfilFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.miActionBar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            // El titulo "Petagram" y la huella ya estan dibujados dentro del Toolbar
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        configurarViewPager();

        FloatingActionButton fabCamara = findViewById(R.id.fabCamara);
        fabCamara.setOnClickListener(v ->
                Toast.makeText(this, R.string.camara_proximamente, Toast.LENGTH_SHORT).show());
    }

    private void configurarViewPager() {
        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new InicioFragment());
        fragments.add(new PerfilFragment());

        viewPager.setAdapter(new PageAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);

        TabLayout.Tab tabInicio = tabLayout.getTabAt(0);
        TabLayout.Tab tabPerfil = tabLayout.getTabAt(1);
        if (tabInicio != null) {
            tabInicio.setIcon(R.drawable.ic_home);
            tabInicio.setContentDescription(R.string.tab_inicio);
        }
        if (tabPerfil != null) {
            tabPerfil.setIcon(R.drawable.ic_dog);
            tabPerfil.setContentDescription(R.string.tab_perfil);
        }
    }

    // ---------- Menu de opciones ----------

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.mFavoritos) {
            startActivity(new Intent(this, FavoritosActivity.class));
            return true;
        } else if (id == R.id.mContacto) {
            startActivity(new Intent(this, ContactoActivity.class));
            return true;
        } else if (id == R.id.mAcercaDe) {
            startActivity(new Intent(this, AcercaDeActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
