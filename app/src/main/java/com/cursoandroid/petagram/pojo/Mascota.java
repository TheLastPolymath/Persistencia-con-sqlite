package com.cursoandroid.petagram.pojo;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * Entidad Mascota. La tabla "mascota" de SQLite tiene exactamente estos mismos campos
 * (ver db/ConstantesBaseDatos), asi que la entidad completa se guarda tal cual.
 */
public class Mascota {

    private final int id;                 // Identificador unico de la mascota
    private final String nombre;
    @DrawableRes private final int foto;
    @ColorRes private final int colorFondo;
    private int raiting;
    private long fechaRaiting;            // Momento del ultimo rating (ms); 0 = nunca

    public Mascota(int id, String nombre, @DrawableRes int foto, @ColorRes int colorFondo,
                   int raiting, long fechaRaiting) {
        this.id = id;
        this.nombre = nombre;
        this.foto = foto;
        this.colorFondo = colorFondo;
        this.raiting = raiting;
        this.fechaRaiting = fechaRaiting;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getFoto() {
        return foto;
    }

    public int getColorFondo() {
        return colorFondo;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public long getFechaRaiting() {
        return fechaRaiting;
    }

    public void setFechaRaiting(long fechaRaiting) {
        this.fechaRaiting = fechaRaiting;
    }

    /** Suma un hueso (raiting) y registra cuando se dio. */
    public void darRaiting() {
        raiting++;
        fechaRaiting = System.currentTimeMillis();
    }
}
