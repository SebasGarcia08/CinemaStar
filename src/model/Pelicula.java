package model;

import java.time.LocalTime;

public class Pelicula {
    public static final String TRES_D = "3D";
    public static final String MM35 = "35mm";
    public static int numberOfObjs = 0;
    
    private String nombre;
    private LocalTime duracion;
    private String idioma;
    private String formato;
    

    public Pelicula(String nombre, LocalTime duracion, String idioma, String formato) {
        this.nombre = nombre;
        this.duracion = duracion;
        this.idioma = idioma;
        this.formato = formato;
        ++numberOfObjs;
    }

    public static int getNumberOfObjs() {
        return numberOfObjs;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalTime getDuracion() {
        return duracion;
    }

    public void setDuracion(LocalTime duracion) {
        this.duracion = duracion;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    @Override
    public String toString() {
        return "Pelicula [duracion=" + duracion + ", formato=" + formato + ", idioma=" + idioma + ", nombre=" + nombre
                + "]";
    }
    
}