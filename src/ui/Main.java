package ui;

import java.time.LocalTime;
import java.util.Scanner;
import static java.lang.System.out;
import model.*;

public class Main{
    private static Cine cinemaStar;
    private static Scanner str;
    private static Scanner num;
    public Main(){
        cinemaStar = new Cine();
        str = new Scanner(System.in);
        num = new Scanner(System.in);
    }

    public static void main(String[] args){
        Main programa = new Main();
        // PRUEBA DE ESCRITORIO
        // programa.registrarPelicula("El Yosibla", LocalTime.of(2, 30), "Ingles", "3D");
        // out.println(programa.mostrarPeliculas());
        // programa.asignarFuncion(0, 0, 0, programa.cinemaStar.getPeliculas().get(0), true);
        // out.println(programa.mostrarSilleteria(0, 0, 0));
        // out.println(programa.informarSillasDisponibles(0, 0, 0));
        // out.println(programa.sillaEstaReservada(0,0,0,'G',3));
        // programa.reservarSilla(0,0,0,'G',3);
        // out.println(programa.mostrarSilleteria(0, 0, 0));
        // out.println(programa.informarSillasDisponibles(0, 0, 0));
        int seleccion = 0;

        // MENU PRINCIPAL
        while(seleccion != 4){
            out.print("\nBienvenido al menu principal, seleccione: \n[1] Registrar peliculas\n[2] Asignar funciones\n[3] Ventas\n[4] Salir\nEleccion[1/2/3/4]: ");
            seleccion = num.nextInt();
            switch (seleccion) {
                case 1:
                    programa.registrarPeliculas(); break;
                case 2:
                    if(cinemaStar.hayPeliculasRegistradas())
                        programa.asignarFunciones(); 
                    else
                        out.println("ERROR: NO HAY PELICULAS REGISTRADAS."); 
                    break;
                case 3:
                    if(cinemaStar.hayFunciones())
                        programa.menu_ventas(); 
                    else
                        out.println("ERROR: NO HAY FUNCIONES.");
                    break;
                case 4:
                    out.println("¡Adios!"); break;
                default:
                    out.println("Eleccion invalida");
                    break;
            }
        }
    }

    // ============================== REGISTRAR PELICULAS ==============================
    public String solicitarNombrePelicula(boolean exclusivo){
        String nombre_pelicula = "";
        boolean nombre_es_valido = false;
        while(!nombre_es_valido){
            out.print("Ingresa el nombre de la pelicula: ");
            nombre_pelicula = str.nextLine();
            boolean pelicula_esta_registrada = cinemaStar.laPeliculaYaEstaRegistrada(nombre_pelicula);
            if( (exclusivo) ?  pelicula_esta_registrada : !pelicula_esta_registrada)
                    out.println("ERROR:" +  ((exclusivo) ? "la pelicula ya esta registrada.": "la pelicula no esta registrada."));
            else
                    nombre_es_valido = true; 
        }
        return nombre_pelicula;
    }

    public void registrarPeliculas(){
        out.println("**REGISTRANDO PELICULA**");
        String nombre = solicitarNombrePelicula(true);
        LocalTime duracion;
        String idioma;
        String formato = Pelicula.MM35;

        out.print("Duracion (hh:mm): ");
        duracion = LocalTime.parse(str.nextLine());
        out.print("Idioma: ");
        idioma = str.nextLine();
        // Solicitar formato de pelicula
        boolean formato_valido = false;
        while(!formato_valido){
            out.print("Seleccione el formato de la pelicula: \n[1] 35mm\n[2] 3D\n[1/2]: ");
            int eleccion = num.nextInt();
            switch (eleccion) {
                case 1:
                    formato = Pelicula.MM35; formato_valido = true; break;
                case 2:
                    formato = Pelicula.TRES_D; formato_valido = true; break;
                default:
                    out.print("Eleccion invalida, trate de nuevo."); break;
            }
        
        }
        cinemaStar.registrarPelicula(nombre, duracion, idioma, formato);
        out.print("** Pelicula registrada exitosamente **");
    }
// ============================== ASIGNAR FUNCIONES ==============================
    public int solicitarSala(Pelicula pelicula){
        int sala = 0;
        boolean sala_es_valida = false;
        while(!sala_es_valida){
            
            if(pelicula.getFormato().equals(Pelicula.TRES_D)){
               sala = 3;      
               sala_es_valida = true;          
            } else{
                out.print("Selecciona en la sala de la funcion: \n[1] Sala 1\n[2] Sala 2\n[1/2]: ");        
                sala = num.nextInt();
                if( sala == 1 || sala == 2)
                    sala_es_valida = true;
                else
                    out.println("ERROR: sala invalida.");        
            }
        }
        return sala;
    }

    public int solicitarDia(int sala){
        int dia = 0;
        boolean dia_es_valido = false;
        while(!dia_es_valido){
            out.print("Selecciona el dia de la funcion: \n[1] Lunes\n[2] Martes\n[3] Miercoles\n[4] Jueves\n[5] Viernes\n[6] Sabado\n[7] Domingo\n[1/2/3/4/5/6/7]: ");
            dia = num.nextInt();
            if( 1 > dia && dia > 7){
                out.println("ERROR: dato invalida.");        
            } else if(cinemaStar.salaEstaOcupadaEnElDia(sala, dia) ){
                out.println("ERROR: la sala ya esta ocupada para este dia, elige otro");
            } else{
                dia_es_valido = true;
            }
        }
        return dia;
    }

    public int solicitarDia(){
        int dia = 0;
        boolean dia_es_valido = false;
        while(!dia_es_valido){
            out.print("Selecciona el dia de la funcion: \n[1] Lunes\n[2] Martes\n[3] Miercoles\n[4] Jueves\n[5] Viernes\n[6] Sabado\n[7] Domingo\n[1/2/3/4/5/6/7]: ");
            dia = num.nextInt();
            if( 1 > dia && dia > 7)
                out.println("ERROR: dato invalida.");        
            else
                dia_es_valido = true;
        }
        return dia;
    }

    public int solicitarFranja(int sala, int dia){
        int franja = 0;
        boolean franja_es_valida = false;
       while(!franja_es_valida){
        out.print("\nSelecciona la franja horaria: \n[1] " + Cine.FRANJAS_HORARIAS[0]+"\n[2] "+ Cine.FRANJAS_HORARIAS[1]+"\n[3] " + Cine.FRANJAS_HORARIAS[2]+"\n[1/2/3]: ");
        franja = num.nextInt();
            if( !(0 < franja && franja < 4)){
                out.println("ERROR: franja invalida");
            } else if(!cinemaStar.franjaHorariaEstaDisponible(sala, dia, franja)){
                out.println("La franja horaria escogida no está disponible");
            } else{
                franja_es_valida = true;
            }
        }
 
        return franja;
    }

    public void asignarFunciones(){
        // Pelicula pelicula, boolean tienePreferencial
        out.println("** ASIGNACION DE FUNCIONES **");
        
        out.println(cinemaStar.mostrarPeliculas());
        String nombre_pelicula = solicitarNombrePelicula(false);
        Pelicula pelicula = cinemaStar.getPeliculaPorNombre(nombre_pelicula);
        int sala = solicitarSala(pelicula);
        int dia = solicitarDia(sala);
        int franja_horaria = solicitarFranja(sala, dia);
        boolean tiene_preferencial = (sala == 1 || sala == 2); 
        cinemaStar.asignarFuncion(sala-1, dia-1, franja_horaria-1, pelicula, tiene_preferencial);
        out.println("** FUNCION ASIGNADA CORRECTAMENTE.**");
    }

    // ============================== ASIGNAR FUNCIONES ==============================
    public void menu_ventas(){
        int eleccion = 0;
        while(eleccion != 3){
            out.print("Benvenido al menu de ventas! Seleccione:\n[1] Consultar funciones disponibles para una pelicula.\n[2] Comprar boletos\n[3] Salir\nEleccion[1/2/3]: ");
            eleccion = num.nextInt();
            switch (eleccion) {
                case 1:
                    consultarDisponibilidad(); break;
                case 2:
                    comprarEntradas(); break;
                case 3:
                    out.println("Adios!"); break;
                default:
                    out.println("Eleccion invalida"); break;
            }
        }
    }
    public void consultarDisponibilidad(){
        out.println(cinemaStar.mostrarPeliculas());
        String nombre_pelicula = solicitarNombrePelicula(false);
        if(cinemaStar.buscarFuncionesPara(nombre_pelicula).size() > 0){
            out.println(cinemaStar.mostrarFuncionesPara(nombre_pelicula));
        } else {
            out.println("La pelicula todavia no tiene asignada funciones.");
        }
    }

    public void comprarEntradas(){
        Sala sala3 = null;
        out.println(cinemaStar.mostrarPeliculas());
        String nombre_pelicula = solicitarNombrePelicula(false);
        int num_dia = solicitarDia();
        int indice_dia = num_dia - 1;
        if(cinemaStar.getPeliculaPorNombre(nombre_pelicula).getFormato().equals(Pelicula.TRES_D)){
            sala3 = cinemaStar.getSalas()[2];
            if(sala3.peliculaEstaEnDia(indice_dia, nombre_pelicula)){
                out.println("Selecciona la franja de funcion:");
                int[] franjas = sala3.getFranjasParaPelicula(indice_dia, nombre_pelicula);

                for(int i = 0; i < franjas.length; i++){
                    out.println("[" + (i+1) +"] " + franjas[i]);
                }
                int num_franja = franjas[num.nextInt()];
                solicitarSillasYRealizarCompra(3, num_dia, num_franja);
            }
            else{
                out.println("Pelicula no disponible en dia " + Cine.DIAS_SEMANA[indice_dia]); 
            }
        } else{ 
            out.print("Seleccione la sala: \n[1] Sala 1\n[2] Sala 2\n[1/2]: ");
            int num_sala = num.nextInt();
            Sala sala_1_2 = (num_sala == 1) ? cinemaStar.getSalas()[0] : cinemaStar.getSalas()[1];
            if(sala_1_2.peliculaEstaEnDia(indice_dia, nombre_pelicula)){
                out.println("Selecciona la franja de funcion:");
                int[] franjas_tot = sala_1_2.getFranjasParaPelicula(indice_dia, nombre_pelicula);
                int num_fun = 0;
                
                // Contar numero de funciones
                for(int i = 0; i < franjas_tot.length; i++) 
                    num_fun += (franjas_tot[i] != -1) ? 1: 0;

                int[] franjas = new int[num_fun];
                int indice = 0;
                for(int i = 0; i < franjas_tot.length; i++)
                    if(franjas_tot[i] != -1){
                        franjas[indice] = franjas_tot[i];
                        ++indice;
                    }

                String franjas_str = "";
                for(int i = 0; i < franjas.length; i++){
                    franjas_str += ((franjas[i] != -1)  ? "[" + (i+1) +"] " + Cine.FRANJAS_HORARIAS[i] : "");
                }
                out.println(franjas_str+"\nSeleccion: ");
                int num_franja = franjas[num.nextInt()-1];
                this.solicitarSillasYRealizarCompra(num_sala, num_dia, num_franja);
            } else{
                out.println("Pelicula no disponible en dia " + Cine.DIAS_SEMANA[indice_dia]); 
            }
        }
    }

    public void solicitarSillasYRealizarCompra(int num_sala, int num_dia, int num_franja){
        boolean datos_correctos = false;
        double costo = 0;
        double precio_base = (num_sala == 3) ? Cine.PRECIO_3D : Cine.PRECIO_GENERAL;
        String[] sillas;
        int inicio_ascci_A = 65;
        int fin_ascci_E_o_H = (num_sala == 3) ? 70 : 72;
        int indice_sala = num_sala -1, indice_dia = num_dia -1, indice_franja = num_franja -1;

        while(!datos_correctos){
            out.print(cinemaStar.mostrarSilleteria(indice_sala, indice_dia, indice_franja));
            out.print("Escriba las sillas que desea reservar separado por comas: (Ejemplos: F1, F2, F3): ");
            sillas = str.nextLine().replaceAll("\\s+","").toUpperCase().split(",");
                boolean sillas_correctas = true;
                boolean filas_correctas = true;
                boolean columnas_correctas = true;
                boolean es_preferencial = false;
                boolean silla_disponible = true;

                for(int i = 0; i < sillas.length && sillas_correctas; i++){
                    int columna = Integer.parseInt(sillas[i].substring(1, sillas[i].length())) - 1;
                    int cod_ascci = ((int) sillas[i].charAt(0));
                    char fila = sillas[i].charAt(0);
                    es_preferencial = ( fila == 'G' || fila == 'H' );
                    filas_correctas = (cod_ascci >= inicio_ascci_A && cod_ascci <= fin_ascci_E_o_H);
                    columnas_correctas = (es_preferencial) ? (columna > 2 && columna < 8) : (columna > -1 && columna < 13);
                    silla_disponible = cinemaStar.sillaEstaReservada(indice_sala, indice_dia, indice_franja, fila, columna); 
                    sillas_correctas = (filas_correctas && silla_disponible && columnas_correctas);
                }
                if(!filas_correctas){
                    out.println("Fila invalida: solo puede seleccionar desde la fila " + ((char) inicio_ascci_A) +" hasta la fila " + ((char) fin_ascci_E_o_H));
                } else if(!silla_disponible){
                    out.println("Silla ya esta reservada.");
                } else if (!columnas_correctas){
                    out.println("Columna de sala no valida, debe ser mayor a 0 y menor a 13");
                } else {
                    for(int i = 0; i < sillas.length; i++){
                        char fila = sillas[i].charAt(0);
                        int columna = Integer.parseInt(sillas[i].substring(1));
                        costo += precio_base + ( ( fila == 'G' || fila == 'H' ) ? ( Cine.PRECIO_PREFERENCIAL - Cine.PRECIO_GENERAL) : 0); 
                        cinemaStar.reservarSilla(indice_sala, indice_dia, indice_franja, fila, columna);
                    }
                    out.println("Costo de todas las boletas: " + costo);
                    datos_correctos = true;
                }
        }
    }
}