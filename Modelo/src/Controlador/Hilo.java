
package Controlador;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Hilo extends Thread{
    public void run(){
        try {
            sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(Hilo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
