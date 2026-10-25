package com.cursoandroid.petagram;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * Pantalla con la bio del desarrollador. El texto se edita en res/values/strings.xml.
 */
public class AcercaDeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);

        Toolbar toolbar = findViewById(R.id.toolbarSimple);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.menu_acerca_de);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        TextView tvVersion = findViewById(R.id.tvVersion);
        tvVersion.setText(getString(R.string.version_app, BuildConfig.VERSION_NAME));
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
