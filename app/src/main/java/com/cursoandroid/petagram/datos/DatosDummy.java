package com.cursoandroid.petagram.datos;

import com.cursoandroid.petagram.R;
import com.cursoandroid.petagram.pojo.FotoPerfil;
import com.cursoandroid.petagram.pojo.Mascota;

import java.util.ArrayList;

/**
 * Datos de prueba (dummy) de la app. Para obtener las mascotas con los raitings
 * guardados en SQLite usa ConstructorMascotas, no esta clase directamente.
 */
public final class DatosDummy {

    private static ArrayList<Mascota> mascotas;

    private DatosDummy() {
    }

    public static ArrayList<Mascota> getMascotas() {
        if (mascotas == null) {
            mascotas = new ArrayList<>();
            // id unico por mascota; fechaRaiting 0 = todavia no ha recibido rating
            mascotas.add(new Mascota(1, "Catty", R.drawable.perro_cafe, R.color.fondo_rosa, 5, 0));
            mascotas.add(new Mascota(2, "Ronny", R.drawable.perro_gris, R.color.fondo_azul, 3, 0));
            mascotas.add(new Mascota(3, "Toby", R.drawable.perro_cafe, R.color.fondo_verde, 7, 0));
            mascotas.add(new Mascota(4, "Luna", R.drawable.perro_gris, R.color.fondo_morado, 2, 0));
            mascotas.add(new Mascota(5, "Max", R.drawable.perro_cafe, R.color.fondo_ambar, 4, 0));
            mascotas.add(new Mascota(6, "Kira", R.drawable.perro_gris, R.color.fondo_rojo, 6, 0));
            mascotas.add(new Mascota(7, "Rocky", R.drawable.perro_cafe, R.color.fondo_teal, 1, 0));
        }
        return mascotas;
    }

    /** Mascota duena del perfil (Ronny, como en la maqueta). */
    public static Mascota getMascotaPerfil() {
        return getMascotas().get(1);
    }

    /** Fotos que ha subido la mascota del perfil, con sus raits. */
    public static ArrayList<FotoPerfil> getFotosPerfil() {
        int[] raitings = {5, 0, 3, 10, 2, 3, 8, 1, 4, 6, 0, 7, 2, 9, 5};
        ArrayList<FotoPerfil> fotos = new ArrayList<>();
        for (int raiting : raitings) {
            fotos.add(new FotoPerfil(R.drawable.perro_gris, R.color.fondo_azul, raiting));
        }
        return fotos;
    }
}
