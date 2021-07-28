/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

/**
 *
 * @author usuario
 */
public class Constantes {

    public static final int LADO_SPRITE = 32;
    public static final int LADO_TILE = 32;
    public static int ANCHO_MUNDO_VIRTUAL = 900;
    public static int ALTO_MUNDO_VIRTUAL = 600;

    public static int ANCHO_PANTALLA_COMPLETA = 900;
    public static int ALTO_PANTALLA_COMPLETA = 600;
    
    public static final int  MARGEN_X= ANCHO_MUNDO_VIRTUAL /2 - LADO_SPRITE/2;
    public static final int  MARGEN_Y= ALTO_MUNDO_VIRTUAL /2 - LADO_SPRITE/2;
    
    public static double FACTOR_ESCALADO_X = ((double) ANCHO_PANTALLA_COMPLETA / (double) ANCHO_MUNDO_VIRTUAL);
    public static double FACTOR_ESCALADO_Y = ((double) ALTO_PANTALLA_COMPLETA / (double) ALTO_MUNDO_VIRTUAL);

    public static int CENTRO_VENTANA_X = ANCHO_MUNDO_VIRTUAL / 2;
    public static int CENTRO_VENTANA_Y = ALTO_MUNDO_VIRTUAL / 2;
    
    public static String RECURSOS = "src/Recursos/"; 

}
