/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.mapas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import principal.Constantes;
import principal.ElementosPrincipales;
import principal.control.GestorControles;
import principal.entes.Adversario;
import principal.entes.RegistroAdversarios;
import principal.herramientas.CargadorDeRecursos;
import principal.herramientas.DibujoDebug;
import principal.herramientas.Nodo;
import principal.sprites.HojaSprites;
import principal.sprites.Sprite;

/**
 *
 * @author usuario
 */
public class Mapa extends Constantes {

    private String contenido;
    private String[] partes;

    private final int ancho;
    private final int alto;

    private final Point posicionInicial;
    private final Point posicionInicialAdversario;

    private final Point puntoSalida;

    private Rectangle Arcos;

    private final Sprite[] paleta;
    private final int[] sprites;

    private final boolean[] coliciones;
    public ArrayList<Rectangle> areasColision = new ArrayList<Rectangle>();
    //public final ArrayList<Adversario> Adversarios;

    private ArrayList<Integer> Sprites;
    private final int margen_x = (Constantes.ANCHO_MUNDO_VIRTUAL / 2) - (Constantes.LADO_SPRITE / 2);
    private final int margen_y = (Constantes.ALTO_MUNDO_VIRTUAL / 2) - (Constantes.LADO_SPRITE / 2);

    private Comparator<Nodo> nodeSorter = new Comparator<Nodo>() {
        @Override
        public int compare(Nodo n0, Nodo n1) {
            if (n1.fCost < n0.fCost) {
                return +1;
            }
            if (n1.fCost > n0.fCost) {
                return -1;
            }
            return 0;
        }
    };

    public Mapa(final String ruta) {
        this.contenido = CargadorDeRecursos.LeerArchivoDeTexto(ruta);

        partes = contenido.split("\\*");
       
        ancho = Integer.parseInt(partes[0]);
        alto = Integer.parseInt(partes[1]);

        String HojasUtilizadas = partes[2];
        String[] HojasSeparadas = HojasUtilizadas.split(",");

        String PaletaEntera = partes[3];
        String[] PartesPaleta = PaletaEntera.split("#");

        paleta = AsignarSprites(PartesPaleta, HojasSeparadas);

        String colisionesEnteras = partes[4];
        coliciones = extraerColisiones(colisionesEnteras);

        String SpritesEnteros = partes[5];
        String[] cadenasDeSprites = SpritesEnteros.split(" ");

        sprites = extraerSprites(cadenasDeSprites);

        String posicion = partes[6];
        String[] pocisiones = posicion.split("-");
        posicionInicial = new Point();
        posicionInicial.x = Integer.parseInt(pocisiones[0]) * Constantes.LADO_SPRITE;
        posicionInicial.y = Integer.parseInt(pocisiones[1]) * Constantes.LADO_SPRITE;

        String salida = partes[7];
        String[] datosSalida = salida.split("-");
        puntoSalida = new Point();
        puntoSalida.x = Integer.parseInt(datosSalida[0]);
        puntoSalida.y = Integer.parseInt(datosSalida[1]);

        Arcos = new Rectangle();

        String salidaAdversario = partes[7];
        String[] posicionAdversario = salidaAdversario.split("-");
        posicionInicialAdversario = new Point();
        posicionInicialAdversario.x = Integer.parseInt(posicionAdversario[0]) * Constantes.LADO_SPRITE;
        posicionInicialAdversario.y = Integer.parseInt(posicionAdversario[1]) * Constantes.LADO_SPRITE;
        //String informacionAdversarios = partes[8];
        //Adversarios = asignarAdversarios(informacionAdversarios);

    }
/*
    public List<Nodo> buscarRuta(Vector2d inicio, Vector2d fin) {

        List<Nodo> openList = new ArrayList<Nodo>();
        List<Nodo> ClosedList = new ArrayList<Nodo>();
        Nodo Current = new Nodo(inicio, null, 0, getDistancia(inicio, fin));
        openList.add(Current);
        while (openList.size() > 0) {
            Collections.sort(openList, nodeSorter);
            Current = openList.get(0);
            if (Current.tile.equals(fin)) {
                List<Nodo> path = new ArrayList<Nodo>();
                while (Current.parent != null) {
                    path.add(Current);
                    Current = Current.parent;
                }
                openList.clear();
                ClosedList.clear();
                return path;
            }
            openList.remove(Current);
            ClosedList.add(Current);
            for (int i = 0; i < 9; i++) {
                if (i == 4) {
                    continue;
                }
                double x = Current.tile.getX();
                double y = Current.tile.getY();
                double xd = (i % 3) - 1;
                double yd = (i / 3) - 1;
                //Sprite at = ElementosPrincipales.mapa.obtenerSpritePaleta((int) x + (int) xd, (int) y + (int) yd);
                int algo = ((int) x + (int) xd);
                int algo2 = ((int) y + (int) yd);
                Sprite at = paleta[algo + algo2 * ancho];
                if (at == null) {
                    continue;
                }
                if (ElementosPrincipales.mapa.obtenerColiciones((int) x + (int) xd, (int) y + (int) yd)) {
                    continue;
                }
                Vector2d a = new Vector2d(x + xd, y + yd);
                double gCost = Current.gCost + getDistancia(Current.tile, a);
                double hCost = getDistancia(a, fin);
                Nodo nodo = new Nodo(a, Current, gCost, hCost);

                if (vecInList(ClosedList, a) && gCost >= nodo.gCost) {
                    continue;
                }
                if (!vecInList(openList, a) || gCost < nodo.gCost) {
                    openList.add(nodo);
                }
            }
        }
        ClosedList.clear();
        return null;
    }

    private boolean vecInList(List<Nodo> list, Vector2d vector) {
        for (Nodo n : list) {
            if (n.tile.equals(vector)) {
                return true;
            }
        }
        return false;
    }

    private double getDistancia(Vector2d inicio, Vector2d fin) {
        double dx = inicio.getX() - fin.getX();
        double dy = inicio.getY() - fin.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
*/
    private ArrayList<Adversario> asignarAdversarios(final String informacionAdversarios) {
        ArrayList<Adversario> adversarios = new ArrayList<>();

        String[] informacionAdversarioSeparada = informacionAdversarios.split("#");

        for (int i = 0; i < informacionAdversarioSeparada.length; i++) {
            String[] informacionAdversarioActual = informacionAdversarioSeparada[i].split(":");
            String[] coordenadas = informacionAdversarioActual[0].split(",");
            String idAdversario = informacionAdversarioActual[1];

            Adversario adversario = RegistroAdversarios.obtenerAdversario(Integer.parseInt(idAdversario));
            adversario.establecerPosicion(Double.parseDouble(coordenadas[0]), Double.parseDouble(coordenadas[1]));
            adversarios.add(adversario);
        }
        return adversarios;
    }

    private void actualizarArcos() {
        int puntoX = ((int) puntoSalida.getX()) * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionXint() + margen_x;
        int puntoY = ((int) puntoSalida.getY()) * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionYint() + margen_y;
        Arcos = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);

    }

    public Rectangle obtenerZonaArcos() {
        return Arcos;
    }

    public Point obtenerArcos() {
        return puntoSalida;
    }

    public Point obtenerPocisionInicial() {
        return posicionInicial;
    }

    public Point obtenerPocisionInicialAdversario() {
        return posicionInicialAdversario;
    }

    public void actualizar() {
        actulizarAreasColicion();
        actualizarArcos();
    }

    private void actulizarAreasColicion() {
        if (!areasColision.isEmpty()) {
            areasColision.clear();
        }
        for (int y = 0; y < this.alto; y++) {
            for (int x = 0; x < this.ancho; x++) {
                int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionXint() + margen_x;
                int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionYint() + margen_y;

                if (coliciones[x + y * this.ancho]) {
                    final Rectangle area = new Rectangle(puntoX, puntoY, Constantes.LADO_SPRITE, Constantes.LADO_SPRITE);
                    areasColision.add(area);
                }

            }
        }
    }

    private Sprite[] AsignarSprites(final String[] partesPaleta, final String[] HojasSeparadas) {

        Sprite[] paleta = new Sprite[partesPaleta.length];
        HojaSprites hoja = new HojaSprites("/imagenes/HojasDeTexturas/" + HojasSeparadas[0] + ".png",
                32, false);

        for (int i = 0; i < partesPaleta.length; i++) {
            String spriteTemporal = partesPaleta[i];
            String[] partesDelSprite = spriteTemporal.split("-");

            int indicePaleta = Integer.parseInt(partesDelSprite[0]);
            int indiceDelSpriteHoja = Integer.parseInt(partesDelSprite[2]);
            paleta[indicePaleta] = hoja.obtenerSprite(indiceDelSpriteHoja);
        }

        return paleta;
    }

    private int[] extraerSprites(final String[] cadenasDeSprites) {
        ArrayList<Integer> sprites = new ArrayList<Integer>();

        for (int i = 0; i < cadenasDeSprites.length; i++) {
            if (cadenasDeSprites[i].length() == 2) {
                sprites.add(Integer.parseInt(cadenasDeSprites[i]));
            } else {
                String uno = "";
                String dos = "";
                String error = cadenasDeSprites[i];
                uno += error.charAt(0);
                uno += error.charAt(1);
                dos += error.charAt(2);
                dos += error.charAt(3);

                sprites.add(Integer.parseInt(uno));
                sprites.add(Integer.parseInt(dos));
            }
        }

        int[] VectorSprites = new int[sprites.size()];

        for (int j = 0; j < sprites.size(); j++) {
            VectorSprites[j] = sprites.get(j);
        }
        return VectorSprites;
    }

    private boolean[] extraerColisiones(final String textoColisiones) {
        boolean[] colisiones = new boolean[textoColisiones.length()];
        for (int i = 0; i < textoColisiones.length(); i++) {
            if (textoColisiones.charAt(i) == '0') {
                colisiones[i] = false;
            } else {
                colisiones[i] = true;
            }
        }
        return colisiones;
    }

    public void escribirArray() {
        for (int i = 0; i < partes.length; i++) {
            System.out.println("" + partes[i]);
        }

    }

    public Rectangle obtenerBordes(final int posicionX, final int posicionY) {

        int x = margen_x - posicionX + ElementosPrincipales.jugador.obtenerAncho_alto();
        int y = margen_y - posicionY + ElementosPrincipales.jugador.obtenerAncho_alto();
        int ancho = this.ancho * 32 - ElementosPrincipales.jugador.obtenerAncho_alto() * 2;
        int alto = this.alto * 32 - ElementosPrincipales.jugador.obtenerAncho_alto() * 2;
        return new Rectangle(x, y, ancho, alto);
    }

    public void dibujar(Graphics g) {

        //int anchoSprite = this.paleta[0].obtenerAncho();
        //int altoSprite = this.paleta[0].obtenerAlto();        
        for (int y = 0; y < this.alto; y++) {
            for (int x = 0; x < this.ancho; x++) {
                BufferedImage imagen = paleta[sprites[x + y * this.ancho]].obtenerImagen();

                int puntoX = x * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionXint() + margen_x;
                int puntoY = y * Constantes.LADO_SPRITE - ElementosPrincipales.jugador.obtenerPosicionYint() + margen_y;
                DibujoDebug.dibujarImagen(g, imagen, puntoX, puntoY);
                //g.drawImage(imagen, puntoX, puntoY, null);

                if (GestorControles.teclado.debug) {
                    for (int i = 0; i < areasColision.size(); i++) {
                        Rectangle area = areasColision.get(i);
                        DibujoDebug.dibujarRectanguloContorno(g, area, Color.GREEN);
                    }

                }
                //dibujar areas obstaculos
                /*
                g.setColor(Color.green);
                for (int i = 0; i < areasColision.size(); i++) {
                    Rectangle area = areasColision.get(i);
                    g.drawRect(area.x, area.y, area.width, area.height);
                }
                 */
            }
        }
        /*
        if (!Adversarios.isEmpty()) {
            for (Adversario adversario : Adversarios) {
                final int puntoX = (int) adversario.obtenerPosicionXint() * Constantes.LADO_SPRITE-ElementosPrincipales.jugador.obtenerPosicionXint() + margen_x;
                final int puntoY = (int) adversario.obtenerPosicionY() * Constantes.LADO_SPRITE
                        - ElementosPrincipales.jugador.obtenerPosicionYint() + margen_y;
                adversario.dibujar(g, puntoX, puntoY);
            
            }
        }
         */
    }

    public int obtenerAncho() {
        return this.ancho;
    }

    public int obtenerAlto() {
        return this.alto;
    }

    public Sprite obtenerSpritePaleta(final int indice) {
        return paleta[indice];
    }

    public Sprite obtenerSpritePaleta(final int x, final int y) {
        return paleta[x + y * this.ancho];
    }

    public boolean obtenerColiciones(final int x, final int y) {
        return coliciones[x + y * this.ancho];
    }

    public Sprite[] obtenerPaleta() {
        return this.paleta;
    }

}
