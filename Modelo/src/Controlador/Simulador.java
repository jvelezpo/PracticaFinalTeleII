package Controlador;

import Modelo.GridModel;

public class Simulador {
    public void simular(double minWeight, double variation, int iterations, int maxGrowthY, GridModel g, double id){
        String message = "Hola";
        Hilo hilo = new Hilo();
        hilo.run();
        g.submitResults(message);
    }
}
