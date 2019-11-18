package model;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class Cine {
    public static final double PRECIO_PREFERENCIAL = 12000;
    public static final double PRECIO_GENERAL = 8000;
    public static final double PRECIO_3D = 10000;
    public static final String[] DIAS_SEMANA = {"Lunes", "Martes", "Miercoles", "Viernes", "Sabado", "Domingo"};
    public final static String[] FRANJAS_HORARIAS = {"14:00 - 16:30", "16:30 - 19:00", "19:00 - 21:00"};

    private ArrayList<Pelicula> peliculas;
    private Sala[] salas;

    public Cine() {
        this.peliculas = new ArrayList<Pelicula>();
        this.salas = new Sala[3];
        this.salas[0] = new Sala("Sala1");
        this.salas[1] = new Sala("Sala2");
        this.salas[2] = new Sala("Sala3");
    }

    // ========================== VALIDACIONES ==========================
    public boolean hayPeliculasRegistradas(){
        return (peliculas.size() > 0);
    }

    public int buscarPeliculaPorNombre(String nombre) {
        int indice = -1;
        for (int i = 0; i < peliculas.size(); i++)
            if (peliculas.get(i).getNombre().equalsIgnoreCase(nombre))
                indice = i;
        return indice;
    }

    public Pelicula getPeliculaPorNombre(String nombre){
        return peliculas.get(buscarPeliculaPorNombre(nombre));
    }

    public boolean laPeliculaYaEstaRegistrada(String nombre_pelicula) {
        return (buscarPeliculaPorNombre(nombre_pelicula) != -1);
    }

    public boolean salaEstaOcupadaEnElDia(int num_sala, int dia){
        Funcion[] funciones = salas[num_sala-1].getFunciones()[dia-1]; 
        boolean esta_ocupada = true;
        for(Funcion funcion : funciones)
            if(funcion == null)
                esta_ocupada = false;
        return esta_ocupada;
    }   

    public boolean franjaHorariaEstaDisponible(int num_sala, int dia, int franja_horaria) {
        Funcion funcion = salas[num_sala-1].getFunciones()[dia-1][franja_horaria-1]; 
        return (funcion == null);
    }

    // ========================== REGISTRO DE PELICULAS ==========================

    public void registrarPelicula(String nombre, LocalTime duracion, String idioma, String formato) {
        this.peliculas.add(new Pelicula(nombre, duracion, idioma, formato));
    }

    // ========================== ASIGNACION DE FUNCIONES ==========================

    public String mostrarPeliculas(){
        String res = "PELICULAS: \n";
        for(Pelicula pelicula : peliculas)
            res+= pelicula.toString() + "\n";
        return res;
    }

    public void asignarFuncion(int indice_de_sala, int dia, int indice_franja_horaria, Pelicula pelicula,
            boolean tienePreferencial) {
        Funcion[][] funciones_actualizadas = salas[indice_de_sala].getFunciones();
        funciones_actualizadas[dia][indice_franja_horaria] = new Funcion(pelicula, tienePreferencial);
        salas[indice_de_sala].setFunciones(funciones_actualizadas);
    }

    // ========================== VENTAS ==========================
    public String mostrarSilleteria(int indice_de_sala, int dia, int franja_horaria) {
        return salas[indice_de_sala].getFunciones()[dia][franja_horaria].mostrarSillas();
    }

    public ArrayList<Funcion> buscarFuncionesPara(String nombre_pelicula){
        ArrayList<Funcion> funciones_disponibles = new ArrayList<Funcion>();
        for(Sala sala : salas){
            Funcion[][] funciones = sala.getFunciones(); 
            for(int i = 0; i < funciones.length; i++){
                for(int j = 0; j < funciones[i].length; j++){
                    if(funciones[i][j] != null && funciones[i][j].getPelicula().getNombre().equalsIgnoreCase(nombre_pelicula))
                        funciones_disponibles.add(funciones[i][j]); 
                }
            }
        }
        return funciones_disponibles;
    }

    public boolean peliculaEstaEnDia(int indice_dia, String nombre_pelicula){
        boolean y_n = false;
        for(Sala sala : salas){
            if(sala.peliculaEstaEnDia(indice_dia, nombre_pelicula))
                y_n = true;
        }
        return y_n;
    }

    public String mostrarFuncionesPara(String nombre_pelicula){
        String funciones_disponibles = "";
        for(int k = 0; k < salas.length; k++){
            Funcion[][] funciones = salas[k].getFunciones(); 
            for(int i = 0; i < funciones.length; i++){
                for(int j = 0; j < funciones[i].length; j++){
                    if(funciones[i][j] != null && funciones[i][j].getPelicula().getNombre().equalsIgnoreCase(nombre_pelicula) && funciones[i][j].maxNumEntradasTotales() > 0)
                        funciones_disponibles += "\nSALA: " + salas[k].getId() +
                                                 "\nDia: " + DIAS_SEMANA[i] + 
                                                 "\nFranja horaria: " + FRANJAS_HORARIAS[j] + 
                                                 "\nMaximo numero de entradas disponibles: " + funciones[i][j].maxNumEntradasTotales() + 
                                                 "\nNumero de entradas generales disponibles: " + funciones[i][j].maxNumEntradasGenerales() + 
                                                 "\nNumero de entradas preferenciales disponibles:" + funciones[i][j].maxNumEntradasPreferenciales() + 
                                                 "\n======================================"; 
                }
            }
        }
        return funciones_disponibles;
    }

    public String informarSillasDisponibles(int indice_de_sala, int dia, int franja_horaria) {
        Funcion funcion = salas[indice_de_sala].getFunciones()[dia][franja_horaria];
        String generales = String.valueOf(funcion.maxNumEntradasGenerales());
        String preferenciales = String.valueOf(funcion.maxNumEntradasPreferenciales());
        return "Numero de sillas disponibles. \nGenerales: " + generales + "\nPrefereciales: " + preferenciales;
    }

    public boolean sillaEstaReservada(int indice_de_sala, int dia, int franja_horaria, char fila, int columna) {
        Funcion funcion = salas[indice_de_sala].getFunciones()[dia][franja_horaria];
        return !funcion.getSillas()[((int) fila) - 65][columna].getEstaReservada();
    }

    public void reservarSilla(int indice_de_sala, int dia, int franja_horaria, char fila, int columna) {
        Funcion funcion = salas[indice_de_sala].getFunciones()[dia][franja_horaria];
        funcion.getSillas()[((int) fila) - 65][columna - 1].setEstaReservada(true);
    }

    public boolean hayFunciones(){
        boolean hay_funciones = false;
        for(Sala sala : getSalas())
            if(sala.hayFunciones())
                hay_funciones = true;
        return hay_funciones;
    }

    // ========================== GETTERS Y SETTERS ==========================
    public ArrayList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(ArrayList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public Sala[] getSalas() {
        return salas;
    }

    public void setSalas(Sala[] salas) {
        this.salas = salas;
    }

    @Override
    public String toString() {
        return "Cine [peliculas=" + peliculas + ", salas=" + Arrays.toString(salas) + "]";
    }

}