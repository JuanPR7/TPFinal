
package grupo_4.tp;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
/**
 *
 * @author Grupo 4
 */
public class ListaPronosticos {
    
    private List<Pronostico> pronosticos;
    private String nombreArchivo;

    public ListaPronosticos(List<Pronostico> pronosticos, String nombreArchivo) {
        this.pronosticos = pronosticos;
        this.nombreArchivo = nombreArchivo;
    }
    
    
        public ListaPronosticos() {
        this.pronosticos = new ArrayList<Pronostico> ();
        this.nombreArchivo = "";
    }

    public List<Pronostico> getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(List<Pronostico> pronosticos) {
        this.pronosticos = pronosticos;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    @Override
    public String toString() {
        return "ListaPronosticos{" + "pronosticos=" + pronosticos + ", nombreArchivo=" + nombreArchivo + '}';
    }

    public void addPronosticos(Pronostico p) {
        this.pronosticos.add(p);
    }
    public void removePronosticos(Pronostico p) {
        this.pronosticos.remove(p);
    }

    public String listar() {
        String lista = "";
        for (Pronostico pronostico: pronosticos) {
            lista += "\n" + pronostico;
        }           
        return lista;
    }
 
    public Pronostico getPronostico (int idPronostico) {
        // Defini un objeto de tipo Equipo en dónde va a ir mi resultado
        // Inicialmente es null, ya que no he encontrado el equipo que 
        // buscaba todavía.
        Pronostico encontrado = null;
        // Recorro la lista de equipos que está cargada
        for (Pronostico pr : this.getPronosticos()) {
            // Para cada equipo obtengo el valor del ID y lo comparo con el que
            // estoy buscando
            if (pr.getIdPronostico() == idPronostico) {
                // Si lo encuentro (son iguales) lo asigno como valor de encontrado
                encontrado = pr;
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
    public void cargarDeArchivo(ListaPartidos listPartidos) {
        // para las lineas del archivo csv
        String datosPronostico;
        // para los datos individuales de cada linea
        String vectorPronostico[];
        // para el objeto en memoria
        Pronostico pronostico;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File("./Pronosticos.csv"));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPronostico = sc.next();
               // System.out.println(datosEquipo);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPronostico = datosPronostico.split(",");   
                
                int idPronostico = Integer.parseInt(vectorPronostico[0]);
                int idPartido = Integer.parseInt(vectorPronostico[2]);
                Partido partido = listPartidos.getPartido(idPartido);
                int eq = Integer.parseInt(vectorPronostico[3]);
                  Equipo equipo = new Equipo();
                if (eq == 1){
                    equipo = partido.getEquipo1();
                }else{
                     equipo = partido.getEquipo2();
                }
                char resultado =  vectorPronostico[4].charAt(1);
                // crea el objeto en memoria
                pronostico = new Pronostico(idPronostico, equipo, partido, resultado);
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPronosticos(pronostico);
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       

    }

     public void cargarDeArchivo(ListaPartidos listPartidos, int IDparticipante) {
        // para las lineas del archivo csv
        String datosPronostico;
        // para los datos individuales de cada linea
        String vectorPronostico[];
        // para el objeto en memoria
        Pronostico pronostico;
        int fila = 0;
       
        try { 
            Scanner sc = new Scanner(new File("./Pronosticos.csv"));
            sc.useDelimiter("\n");   //setea el separador de los datos
                
            while (sc.hasNext()) {
                // levanta los datos de cada linea
                datosPronostico = sc.next();
               // System.out.println(datosEquipo);  //muestra los datos levantados 
                fila ++;
                // si es la cabecera la descarto y no se considera para armar el listado
                if (fila == 1)
                    continue;              
                 
                //Proceso auxiliar para convertir los string en vector
                // guarda en un vector los elementos individuales
                vectorPronostico = datosPronostico.split(",");   
                int IDp = Integer.parseInt(vectorPronostico[1]);
                if (IDp == IDparticipante){
                    int idPronostico = Integer.parseInt(vectorPronostico[0]);
                    int idPartido = Integer.parseInt(vectorPronostico[2]);
                    Partido partido = listPartidos.getPartido(idPartido);
                    int eq = Integer.parseInt(vectorPronostico[3]);
                      Equipo equipo = new Equipo();
                    if (eq == 1){
                        equipo = partido.getEquipo1();
                    }else{
                         equipo = partido.getEquipo2();
                    }
                    char resultado =  vectorPronostico[4].charAt(1);

                    // crea el objeto en memoria
                    pronostico = new Pronostico(idPronostico, equipo, partido, resultado);

                    // llama al metodo add para grabar el equipo en la lista en memoria
                    this.addPronosticos(pronostico);
                }
            }
            //closes the scanner
        } catch (IOException ex) {
                System.out.println("Mensaje: " + ex.getMessage());
        }       

    }

       public void cargarDeDB(ListaPartidos listPartidos, int IDparticipante){
        Pronostico pronostico;   
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db");
            Statement sta = conn.createStatement();
            String consulta = "SELECT idPronostico, idParticipante, idPartido, idEquipo, resultado FROM Pronosticos";
            ResultSet rs = sta.executeQuery(consulta);
            while (rs.next()){
                if(IDparticipante == rs.getInt("idParticipante")){
                Partido partido = listPartidos.getPartido(rs.getInt("idPartido"));
                int eq = rs.getInt("idEquipo");
                  Equipo equipo = new Equipo();
                if (eq == 1){
                    equipo = partido.getEquipo1();
                }else{
                     equipo = partido.getEquipo2();
                }
                // crea el objeto en memoria
                pronostico = new Pronostico(rs.getInt("idPronostico"), equipo, partido, rs.getString("resultado").charAt(0));
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPronosticos(pronostico);
                }
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
    
              public void cargarDeDB(ListaPartidos listPartidos){
        Pronostico pronostico;   
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:pronosticos.db");
            Statement sta = conn.createStatement();
            String consulta = "SELECT idPronostico, idParticipante, idPartido, idEquipo, resultado FROM Pronosticos";
            ResultSet rs = sta.executeQuery(consulta);
            while (rs.next()){
                
                Partido partido = listPartidos.getPartido(rs.getInt("idPartido"));
                int eq = rs.getInt("idEquipo");
                  Equipo equipo = new Equipo();
                if (eq == 1){
                    equipo = partido.getEquipo1();
                }else{
                     equipo = partido.getEquipo2();
                }
                // crea el objeto en memoria
                pronostico = new Pronostico(rs.getInt("idPronostico"), equipo, partido, rs.getString("resultado").charAt(0));
                
                // llama al metodo add para grabar el equipo en la lista en memoria
                this.addPronosticos(pronostico);

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
              
}
