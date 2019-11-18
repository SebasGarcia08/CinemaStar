package model;

import java.util.ArrayList;

public class Sala {
    private String id;
    private Funcion[][] funciones;
   
    public Sala(String id) {
        this.id = id;
        this.funciones = new Funcion[7][3];
    }

    public boolean peliculaEstaEnDia(int indice_dia, String nombre_pelicula){
        boolean found = false;
        boolean y_n = false;
        for(int i = 0; i < funciones[indice_dia].length && !found; i++){
            if(funciones[indice_dia][i] != null && funciones[indice_dia][i].getPelicula().getNombre().equalsIgnoreCase(nombre_pelicula)){
                found = true;
                y_n = true;
            }
        }
        return y_n;
    }

    public ArrayList<Funcion> getFuncionesParaPelicula(int indice_dia, String nombre_pelicula){
        ArrayList<Funcion> funciones_dips = new ArrayList<Funcion>();
        for(int i=0; i < funciones[indice_dia].length; i++)
            if(funciones[indice_dia][i].getPelicula().getNombre().equalsIgnoreCase(nombre_pelicula))
                funciones_dips.add(funciones[indice_dia][i]);
        return funciones_dips;
    }

    public int[] getFranjasParaPelicula(int indice_dia, String nombre_pelicula){
         int[] res = {-1,-1,-1};
         for(int i=0; i < res.length; i++)
            if(funciones[indice_dia][i] != null && funciones[indice_dia][i].getPelicula().getNombre().equalsIgnoreCase(nombre_pelicula) && funciones[indice_dia][i].maxNumEntradasTotales()>0)
                res[i] = 1;
        return res;
    }

    public boolean hayFunciones(){
        boolean hay_funciones = false;
        for(int i = 0; i < funciones.length && !hay_funciones; i++)
            for(int j = 0; j < funciones[i].length; j++)
                if(funciones[i][j] != null)
                    hay_funciones = true;
        return hay_funciones;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Funcion[][] getFunciones() {
        return funciones;
    }

    public void setFunciones(Funcion[][] funciones) {
        this.funciones = funciones;
    }
}