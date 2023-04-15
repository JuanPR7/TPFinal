/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo_4.tp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Grupo 4
 */
public class ListaPartidos {
    private List<Partido> partidos;
    private String partidosCSV;

    public ListaPartidos(List<Partido> partidos, String partidosCSV) {
        this.partidos = partidos;
        this.partidosCSV = partidosCSV;
    }

    public ListaPartidos() {
        this.partidos = new ArrayList<Partido>();
        this.partidosCSV = "partidos.csv";
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public String getPartidosCSV() {
        return partidosCSV;
    }

    public void setPartidosCSV(String partidosCSV) {
        this.partidosCSV = partidosCSV;
    }

    @Override
    public String toString() {
        return "ListaPartidos{" + "partidos=" + partidos + ", partidosCSV=" + partidosCSV + '}';
    }

    public void addPartido(Partido e) {
        this.partidos.add(e);
    }
    public void removePartido(Partido e) {
        this.partidos.remove(e);
    }
    
    public String listar() {
        String lista = "";
        for (Partido partido: partidos) {
            lista += "\n" + partido;
        }           
        return lista;
    }
    

    public Partido getPartido (int idPartido) {
        Partido encontrado = null;
        // Recorro la lista de equipos que está cargada
        for (Partido pa : this.getPartidos()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (pa.getIdPartido() == idPartido) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = pa;
                // Y hago un break para salir del ciclo ya que no hace falta seguir buscando
                break;
            }
        }
        // Una vez fuera del ciclo retorno el equipo, pueden pasar dos cosas:
        // 1- Lo encontré en el ciclo, entonces encontrado tiene el objeto encontrado
        // 2- No lo encontré en el ciclo, entonces conserva el valor null del principio
        return encontrado;
    }
    
        // cargar desde el archivo
    public void cargarDeArchivo(ListaEquipos listEquipos) {
        // para las lineas del archivo csv
        String datosPartido;
        // para los datos individuales de cada linea
        String vectorPartido[];
        // para el objeto en memoria
        Partido partido;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File("./Partidos.csv"));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPartido = sc.next();
                //System.out.println(datosPartido);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPartido = datosPartido.split(",");   
                
                // graba el equipo en memoria
                int idPartido = Integer.parseInt(vectorPartido[0]);
                int idEquipo1 = Integer.parseInt(vectorPartido[1]);
                int idEquipo2 = Integer.parseInt(vectorPartido[2]);
                int GolesEquipo1 = Integer.parseInt(vectorPartido[3]);
                int GolesEquipo2 = Integer.parseInt(vectorPartido[4]);
                
                //obtengo los equipos desde el idequipo
                Equipo e1 = listEquipos.getEquipo(idEquipo1);
                Equipo e2 = listEquipos.getEquipo(idEquipo2);
                
                // crea el objeto en memoria
                partido = new Partido(idPartido, e1, e2, GolesEquipo1, GolesEquipo2);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPartido(partido);
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       

    }
    
}
