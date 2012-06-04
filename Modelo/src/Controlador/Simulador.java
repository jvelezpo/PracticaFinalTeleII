package Controlador;

import Modelo.GridModel;

public class Simulador {

    private Finca finca;
    private Tiempo tiempo;
    private Ambiente ambiente;
    private Materiales materiales;

    /**
     * Encargado de simular el modelo deacurdo a todos los datos de entrada
     *
     * @param g
     * @param tamFinca
     * @param tiempo
     * @param numMatas
     * @param precipitacion
     * @param glifo
     * @param ara
     */
    public void simular(GridModel g, String tamFinca, String tiempo, String numMatas, String precipitacion, String glifo, String ara) {
        this.finca = new Finca();
        this.tiempo = new Tiempo();
        this.ambiente = new Ambiente();
        this.materiales = new Materiales();

        double matasPorHectarea = this.finca.ChequearMatasPorHectarea(numMatas);
        int tiempoChequeado = this.tiempo.chequearTiempo(tiempo, numMatas, tamFinca);
        int preciChequeada = this.ambiente.chequearPrecipitacion(precipitacion);
        double glifoChequeado = this.materiales.chequearGlifosato(glifo);
        double araChequeado = this.materiales.chequearAracuat(ara);

        double produccion = tiempoChequeado + (tiempoChequeado * glifoChequeado) + (tiempoChequeado * araChequeado);

        String message = "La produccion esperada es de: " + produccion + "\n";
        message += "La utilizacion de la finca es de: " + matasPorHectarea + "\n";
        Hilo hilo = new Hilo();
        hilo.run();
        g.submitResults(message);
    }
}
