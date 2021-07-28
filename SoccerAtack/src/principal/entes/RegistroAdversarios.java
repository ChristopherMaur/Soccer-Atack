/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.entes;

/**
 *
 * @author usuario
 */
public class RegistroAdversarios {
    public static Adversario obtenerAdversario(final int idAdversario){
        Adversario adversario = null;
        switch(idAdversario){
            case 1:
                adversario= new Delantero(idAdversario,"Delantero");
            break;
        }
        
        return adversario;
    }
    
}
