
package grupo_4.tp;

/*
 * @author Grupo 4
 */
public class Participante implements Comparable<Participante>{
    
    private int idParticipante;
    private String nombre;
    private ListaPronosticos pronosticos;

    public Participante(int idParticipante, String nombre, ListaPronosticos pronosticos) {
        this.idParticipante = idParticipante;
        this.nombre = nombre;
        this.pronosticos = pronosticos;
    }
    
    public Participante() {
        this.idParticipante = 0;
        this.nombre = null;
        this.pronosticos = null;
    }

    public int getIdParticipante() {
        return idParticipante;
    }

    public void setIdParticipante(int idParticipante) {
        this.idParticipante = idParticipante;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ListaPronosticos getPronosticos() {
        return pronosticos;
    }

    public void setPronosticos(ListaPronosticos pronosticos) {
        this.pronosticos = pronosticos;
    }

    @Override
    public String toString() {
        return "Participante{" + "idParticipante=" + idParticipante + ", nombre=" + nombre + ", pronosticos=" + pronosticos + '}';
    }

 
    public void cargarPronosticos(ListaPronosticos pronosticos){
        this.pronosticos = pronosticos;
    
    }
    
    public int getPuntaje(){
        int puntosTotal = 0;
        for (Pronostico pronos : pronosticos.getPronosticos()) {
            var resulPronostico = pronos.getResultado();
            char resulPartido = pronos.getPartido().resultado(pronos.getEquipo());
            //si resultado Pronostico es igual al del partido => acierto suma 3
            if (resulPronostico == resulPartido)
                puntosTotal = puntosTotal +3;

        } 
        
        return puntosTotal;
    }
    
    public int getAciertos(){
         int aciertos = 0;
        for (Pronostico pronos : pronosticos.getPronosticos()) {
            var resulPronostico = pronos.getResultado();
            char resulPartido = pronos.getPartido().resultado(pronos.getEquipo());
            //si resultado Pronostico es igual al del partido => acierto +1
            if (resulPronostico == resulPartido)
                aciertos += 1;

        } 
        
        return aciertos;
    }

    @Override
    public int compareTo(Participante p) {
        int miPuntaje = this.getPuntaje();
        int otroPuntaje = p.getPuntaje();
        if (miPuntaje == otroPuntaje)
            return 0;
        else if(miPuntaje > otroPuntaje)
            return 1;
        else
            return -1;     
    }


    
}
