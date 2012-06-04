package Controlador;

public class Finca {
    
    /**
     * Revisa el numero de matas por hectareas sea la ideal
     * @param numMatas
     * @return 
     */
    public double ChequearMatasPorHectarea(String numMatas){
        int porcantajeMatasBuenas = 4000;
        return Double.parseDouble(numMatas)/porcantajeMatasBuenas;
    }
    
    
}
