/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal.herramientas;

/**
 *
 * @author usuario
 */
public class Cronometro implements Runnable {

    private Thread crono;
    private int minutos = 0, segundos = 0, horas = 0;

    public Cronometro() {

    }

    public int get_segundos() {
        return (segundos);
    }

    public void Strat() {
        crono = new Thread(this);
        crono.start();
    }
    
    public void Parar(){
     crono.stop();
    }
    
    public String getTiempo(){
    
        return (""+horas + ":" + minutos + ":" + segundos+"");
    }
    
    @Override
    public void run() {
        {
            try {
                for (;;) {
                    if (segundos == 59) {
                        segundos = 0;
                        minutos++;
                    }
                    if (minutos == 59) {
                        minutos = 0;
                        horas++;
                    }
                    segundos++;

                    //System.out.println(horas + ":" + minutos + ":" + segundos);
                    crono.sleep(1000);
                }
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }

        }

    }
}
