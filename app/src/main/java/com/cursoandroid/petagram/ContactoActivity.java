package com.cursoandroid.petagram;

import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.cursoandroid.petagram.mail.EnviadorCorreo;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/**
 * Formulario de contacto: nombre, correo y mensaje con EditText de Material Design.
 * El boton "Enviar comentario" manda el correo con JavaMail.
 */
public class ContactoActivity extends AppCompatActivity {

    private TextInputLayout tilNombre, tilCorreo, tilMensaje;
    private TextInputEditText etNombre, etCorreo, etMensaje;
    private MaterialButton btnEnviar;
    private ProgressBar pbEnviando;

    private final EnviadorCorreo enviador = new EnviadorCorreo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

        Toolbar toolbar = findViewById(R.id.toolbarSimple);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(R.string.menu_contacto);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        tilNombre = findViewById(R.id.tilNombre);
        tilCorreo = findViewById(R.id.tilCorreo);
        tilMensaje = findViewById(R.id.tilMensaje);
        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etMensaje = findViewById(R.id.etMensaje);
        btnEnviar = findViewById(R.id.btnEnviar);
        pbEnviando = findViewById(R.id.pbEnviando);

        btnEnviar.setOnClickListener(v -> enviarComentario());
    }

    private void enviarComentario() {
        String nombre = texto(etNombre);
        String correo = texto(etCorreo);
        String mensaje = texto(etMensaje);

        tilNombre.setError(null);
        tilCorreo.setError(null);
        tilMensaje.setError(null);

        boolean valido = true;
        if (nombre.isEmpty()) {
            tilNombre.setError(getString(R.string.error_nombre));
            valido = false;
        }
        if (correo.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
            tilCorreo.setError(getString(R.string.error_correo));
            valido = false;
        }
        if (mensaje.isEmpty()) {
            tilMensaje.setError(getString(R.string.error_mensaje));
            valido = false;
        }
        if (!valido) return;

        if (!EnviadorCorreo.estaConfigurado()) {
            Toast.makeText(this, R.string.error_configuracion, Toast.LENGTH_LONG).show();
            return;
        }

        mostrarEnviando(true);
        enviador.enviar(nombre, correo, mensaje, new EnviadorCorreo.Callback() {
            @Override
            public void onExito() {
                if (isDestroyed()) return;
                mostrarEnviando(false);
                Toast.makeText(ContactoActivity.this, R.string.envio_exitoso, Toast.LENGTH_LONG).show();
                etNombre.setText("");
                etCorreo.setText("");
                etMensaje.setText("");
            }

            @Override
            public void onError(String detalle) {
                if (isDestroyed()) return;
                mostrarEnviando(false);
                Toast.makeText(ContactoActivity.this,
                        getString(R.string.envio_fallido, detalle), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarEnviando(boolean enviando) {
        btnEnviar.setEnabled(!enviando);
        pbEnviando.setVisibility(enviando ? View.VISIBLE : View.GONE);
    }

    private static String texto(TextInputEditText campo) {
        Editable e = campo.getText();
        return e == null ? "" : e.toString().trim();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onDestroy() {
        enviador.cerrar();
        super.onDestroy();
    }
}
