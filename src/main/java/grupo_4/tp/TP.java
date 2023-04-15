/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package grupo_4.tp;


/**
 *
 * @author Grupo 4
 */
public class TP {

    public static PronosticoDeportivo PRODE;
    
   
    public static void main(String[] args) {
        System.out.println ("Sistema de simulación de pronósticos deportivos.");
        
        PRODE = new PronosticoDeportivo();

        PRODE.play();

    }
}
