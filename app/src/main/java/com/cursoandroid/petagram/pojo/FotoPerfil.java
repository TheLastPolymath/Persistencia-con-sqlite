package com.cursoandroid.petagram.pojo;

import androidx.annotation.ColorRes;
import androidx.annotation.DrawableRes;

/**
 * Foto subida por la mascota del perfil, con la cantidad de raits (huesos) recibidos.
 */
public class FotoPerfil {

    @DrawableRes private final int foto;
    @ColorRes private final int colorFondo;
    private final int raiting;

    public FotoPerfil(@DrawableRes int foto, @ColorRes int colorFondo, int raiting) {
        this.foto = foto;
        this.colorFondo = colorFondo;
        this.raiting = raiting;
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
}
