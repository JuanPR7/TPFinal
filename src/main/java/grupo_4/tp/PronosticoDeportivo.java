/*
Para entrega 2
 */
package grupo_4.tp;

import java.util.Collections;
import java.util.List;


/**
 *
 * @author aguzman
 */
public class PronosticoDeportivo {
    private ListaEquipos equipos;
    private ListaPartidos partidos;
    private ListaPronosticos pronosticos;
    private ListaParticipantes participantes;

    public PronosticoDeportivo() {
        equipos = new ListaEquipos();
        partidos = new ListaPartidos();
        pronosticos = new ListaPronosticos();
        participantes = new ListaParticipantes();
    }

    public void play(){
       // cargar y listar los equipos
        equipos.cargarDeDB();
       // System.out.println("Los equipos cargados son: " + equipos.listar()+ "\n" );
        // cargar lista de partidos
        partidos.cargarDeDB(equipos);
       // System.out.println("Los partidos cargados son: " + partidos.listar()+ "\n" );
        // cargar lista de pronosticos
        pronosticos.cargarDeDB(partidos);
        //System.out.println("Los pronosticos cargados son: " + pronosticos.listar()+ "\n" );
        
        //cargar la lista de participantes con su lista de pronostico de cada participante
        participantes.cargarDeDB();
        //System.out.println("Los participantes cargados son: " + participantes.listar()+ "\n" );
        String lista = "";
        List<Participante> p = participantes.getParticipantes();
        for (Participante participante: p ) {
            ListaPronosticos pronosticosP = new ListaPronosticos();
            pronosticosP.cargarDeDB(partidos,participante.getIdParticipante());
            participante.setPronosticos(pronosticosP);
        }   
        lista = participantes.listaOrdenadosPorPuntaje();
        
        
        System.out.println("Ordenados por puntaje: " + lista + "\n");
        
       
         System.out.println( participantes.getGanador() + "\n");
        
        
        

        
        
        
        
    }    
}
