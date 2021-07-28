/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.herramientas;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import javax.imageio.ImageIO;

import principal.Constantes;

/**
 *
 * @author usuario
 */
public class CargadorDeRecursos {

    public static BufferedImage cargarImagenCompatibleOpaca(final String ruta) {
        Image imagen = null;
        try { 
        	imagen = ImageIO.read(new File(Constantes.RECURSOS + ruta));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GraphicsConfiguration gc = GraphicsEnvironment.
                getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.
                createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.OPAQUE);

        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();

        return imagenAcelerada;
    }

    public static BufferedImage cargarImagenCompatibleTraslucida(final String ruta) {
        Image imagen = null;
        try {  
        	imagen = ImageIO.read(new File(Constantes.RECURSOS + ruta));
        
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        GraphicsConfiguration gc = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
        BufferedImage imagenAcelerada = gc.createCompatibleImage(imagen.getWidth(null), imagen.getHeight(null), Transparency.TRANSLUCENT);

        Graphics g = imagenAcelerada.getGraphics();
        g.drawImage(imagen, 0, 0, null);
        g.dispose();

        return imagenAcelerada;
    }

    public static String LeerArchivoDeTexto(final String ruta) {
        String contenido = "";
   
        File file = new File(Constantes.RECURSOS + ruta);
        
        try {
			Reader reader = new FileReader(file);
			
			int i=0;
			while ((i = reader.read())!= -1) {
				if(i != '\n') {
					contenido +=(char) i;
				} 
			}
			 
			reader.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        
        return contenido;
    }

}
