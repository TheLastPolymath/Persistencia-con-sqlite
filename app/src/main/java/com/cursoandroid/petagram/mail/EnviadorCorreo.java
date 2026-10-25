package com.cursoandroid.petagram.mail;

import android.os.Handler;
import android.os.Looper;

import com.cursoandroid.petagram.BuildConfig;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Envia el comentario del formulario de Contacto usando JavaMail por SMTP (Gmail).
 *
 * Android no permite operaciones de red en el hilo principal, por eso el envio
 * se hace en un hilo aparte y el resultado se devuelve al hilo de la UI.
 */
public class EnviadorCorreo {

    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PUERTO = "587";

    public interface Callback {
        void onExito();

        void onError(String mensaje);
    }

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Handler hiloUi = new Handler(Looper.getMainLooper());

    /** true si las credenciales se definieron en local.properties. */
    public static boolean estaConfigurado() {
        return !BuildConfig.MAIL_USUARIO.isEmpty() && !BuildConfig.MAIL_PASSWORD.isEmpty();
    }

    public void enviar(String nombre, String correoContacto, String comentario, Callback callback) {
        executor.execute(() -> {
            try {
                Properties props = new Properties();
                props.put("mail.smtp.auth", "true");
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.host", SMTP_HOST);
                props.put("mail.smtp.port", SMTP_PUERTO);

                Session session = Session.getInstance(props, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                BuildConfig.MAIL_USUARIO, BuildConfig.MAIL_PASSWORD);
                    }
                });

                String destino = BuildConfig.MAIL_DESTINO.isEmpty()
                        ? BuildConfig.MAIL_USUARIO
                        : BuildConfig.MAIL_DESTINO;

                String cuerpo = "Nuevo comentario desde Petagram\n\n"
                        + "Nombre: " + nombre + "\n"
                        + "Correo: " + correoContacto + "\n\n"
                        + "Mensaje:\n" + comentario;

                MimeMessage mensaje = new MimeMessage(session);
                // Gmail exige que el remitente sea la misma cuenta autenticada
                mensaje.setFrom(new InternetAddress(BuildConfig.MAIL_USUARIO, "Petagram"));
                mensaje.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destino));
                // "Responder" en el correo le contesta directamente a quien escribio
                mensaje.setReplyTo(InternetAddress.parse(correoContacto));
                mensaje.setSubject("Petagram - Comentario de " + nombre, "UTF-8");
                mensaje.setText(cuerpo, "UTF-8");

                Transport.send(mensaje);
                hiloUi.post(callback::onExito);

            } catch (Exception e) {
                String detalle = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
                hiloUi.post(() -> callback.onError(detalle));
            }
        });
    }

    public void cerrar() {
        executor.shutdown();
    }
}
