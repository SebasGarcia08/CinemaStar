package model;


public class Funcion {
    private Pelicula pelicula;
    private Silla[][] sillas;
    private boolean tienePreferencial;

    public Funcion(Pelicula pelicula, boolean tienePreferencial) {
        this.pelicula = pelicula;
        this.tienePreferencial = tienePreferencial;
        this.sillas = (tienePreferencial) ? new Silla[8][12] : new Silla[6][12];
        inicializarSillas();
    }

    public void inicializarSillas(){
        // Inicializando preferenciales (si hay)
        if(tienePreferencial){
            for(int i = 7; i > 5; i--)
                for(int j = 2; j <= 9; j++)
                    sillas[i][j] = new Silla();
        }

        // Inicializando sillas generales
        for(int i = 5; i >= 0;i--)
            for(int j = 0; j < sillas[0].length;j++)
                sillas[i][j] = new Silla();
    }

    public String mostrarSillas(){
        String indentacion = "        ";
        String res = indentacion + " PELICULA: " + pelicula.getNombre().toUpperCase() + " " + indentacion + "\n";

        if(tienePreferencial){
            for(int i = 7; i > 5; i--){
                res += ( (char) (i + 65) ) + indentacion;
                for(int j = 0; j < sillas[0].length; j++){
                    if(sillas[i][j] == null)
                        res += "  ";
                    else if(sillas[i][j].getEstaReservada())
                        res += "X ";
                    else
                        res+="_ ";
                }
                res+="\n";
            }
            res+=" ";
        // Generar linea
            for(int i = 0; i <= (indentacion.length() + sillas[0].length); i++)
                res += "_ ";
            res+="\n";
        }
    
        for(int i = 5; i >= 0; i--){
            res += ( (char) (i + 65) ) + indentacion;
            for(int j = 0; j < sillas[0].length; j++){
                if(sillas[i][j].getEstaReservada())
                    res += "X ";
                else
                    res+="_ ";
            }
            res += "\n";
        }

        
        res += "\n " + indentacion + " - - - - - - - - - - - \n" ;
        res +=   " " + indentacion + "|   P A N T A L L A   |\n" ;
        res +=   " " + indentacion + " - - - - - - - - - - - \n" ;
        
        return res;
    }

    public int maxNumEntradasTotales(){
        return maxNumEntradasGenerales() + maxNumEntradasPreferenciales();
    }

    public int maxNumEntradasGenerales(){
        int numero_de_sillas_disponibles = 0;
        for(int i = 0; i < sillas.length; i++)
            for(int j = 0; j < sillas[i].length; j++)
               if(sillas[i][j] != null && !sillas[i][j].getEstaReservada())
                    ++numero_de_sillas_disponibles;
        return numero_de_sillas_disponibles;
    }

    public int maxNumEntradasPreferenciales(){
        int numero_de_sillas_disponibles = 0;
        for(int i = 0; i < sillas.length; i++)
            for(int j = 0; j < sillas[i].length; j++)
                if(sillas[i][j] != null && !sillas[i][j].getEstaReservada())
                    ++numero_de_sillas_disponibles;
        return numero_de_sillas_disponibles;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public Silla[][] getSillas() {
        return sillas;
    }

    public void setSillas(Silla[][] preferencial) {
        this.sillas = preferencial;
    }
    
}