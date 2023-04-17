/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package grupo_4.tp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Grupo 4
 */
public class ListaParticipantes {

    private List<Participante> participantes;
    private String participantesCSV;

    public ListaParticipantes(List<Participante> participantes, String participantesCSV) {
        this.participantes = participantes;
        this.participantesCSV = participantesCSV;
    }
    
    public ListaParticipantes() {
        this.participantes = new ArrayList<Participante> ();
        this.participantesCSV = "participantes.csv";
    }

    public List<Participante> getParticipantes() {
        return participantes;
    }

    public void setParticipantes(List<Participante> participantes) {
        this.participantes = participantes;
    }

    public String getParticipantesCSV() {
        return participantesCSV;
    }

    public void setParticipantesCSV(String participantesCSV) {
        this.participantesCSV = participantesCSV;
    }

    @Override
    public String toString() {
        return "ListaParticipantes{" + "participantes=" + participantes + ", participantesCSV=" + participantesCSV + '}';
    }
    
    public void addParticipante(Participante p) {
        this.participantes.add(p);
    }
    public void removeParticipante(Participante p) {
        this.participantes.remove(p);
    }
    
    public String listar() {
        String lista = "";
        for (Participante participante: participantes) {
            lista += "\n" + participante;
        }           
        return lista;
    }
    
    // cargar desde el archivo
    public void cargarDeArchivo() {
        // para las lineas del archivo csv
        String datosParticipante;
        // para los datos individuales de cada linea
        String vectorParticipante[];
        // para el objeto en memoria
        Participante participante;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File("./Participantes.csv"));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosParticipante = sc.next();
                //System.out.println(datosParticipante);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorParticipante = datosParticipante.split(",");   
                
                // graba el equipo en memoria
                //convertir un string a un entero;
                int idParticipante = Integer.parseInt(vectorParticipante[0]);
                String Participante = vectorParticipante[1];
              //  ListaPronosticos pronos = pronosticos.cargarDeArchivo(pronosticos,idParticipante);
                // crea el objeto en memoria
                participante = new Participante(idParticipante, Participante, null);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       

    }
    
        public void cargarDeDB(){
        Participante participante;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db");
            Statement sta = conn.createStatement();
            String consulta = "SELECT idParticipante, nombre FROM Participantes";
            ResultSet rs = sta.executeQuery(consulta);
            while (rs.next()){
                // crea el objeto en memoria
                participante = new Participante(rs.getInt("idParticipante"), rs.getString("nombre"), null);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addParticipante(participante);

            }
        
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // conn close failed.
                System.out.println(e.getMessage());
            }
        }
    
    }
        
    public List<Participante> getOrdenadosPorPuntaje(){
        List<Participante> ordenados = new ArrayList<Participante>();
        ordenados.addAll(participantes);
        
        Collections.sort(ordenados, Collections.reverseOrder());
        return ordenados;
    }
    
    public String listaOrdenadosPorPuntaje(){
        List<Participante> ordenados = this.getOrdenadosPorPuntaje();
        String lista = "";
        for (Participante participante: ordenados){
            lista += "\n nombre: " + participante.getNombre() + " - puntaje Total:  " + participante.getPuntaje()+ " - #aciertos:  " + participante.getAciertos();
        }
        return lista;
    }
    
    public String getGanador(){
        List<Participante> ordenados = this.getOrdenadosPorPuntaje();
        int puntos = 0;
        String lista = "El/los ganador/es son: ";
        for (Participante participante: ordenados ) {
          if (puntos <= participante.getPuntaje()){
             puntos = participante.getPuntaje();
             lista += "\n" + participante.getNombre() + " - puntaje Total:  " + participante.getPuntaje();
            }else
                break;
        } 
        
        return lista;
    }
}
