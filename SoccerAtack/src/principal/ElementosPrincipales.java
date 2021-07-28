package principal;

import principal.entes.Delantero;
import principal.entes.Jugador;
import principal.mapas.Mapa;

public class ElementosPrincipales {

    public static Mapa mapa = new Mapa("/Texto/texto.txt");
    public static Jugador jugador = new Jugador();
    public static Delantero delantero = new Delantero(1, "kaka");

}
