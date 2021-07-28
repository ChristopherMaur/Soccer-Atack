/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.graficos;

import java.awt.BorderLayout;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import principal.herramientas.CargadorDeRecursos;

/**
 *
 * @author usuario
 */
public class Ventana extends JFrame {

    private String titulo;
    private final ImageIcon icono;

    public Ventana(final String titulo, final SuperficieDibujo sd) {
        this.titulo = titulo;
        BufferedImage imagen = CargadorDeRecursos.cargarImagenCompatibleOpaca("/imagenes/iconos/icono.png");
        this.icono = new ImageIcon(imagen);
        configurarVentana(sd);
    }

    private void configurarVentana(final SuperficieDibujo sd) {
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setIconImage(icono.getImage());
        setLayout(new BorderLayout());
        add(sd, BorderLayout.CENTER);
        setUndecorated(true);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
