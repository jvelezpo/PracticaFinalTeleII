package Controlador;

public class Ambiente {
    
    /**
     * retorna cual si la precipitacion es buena o mala
     * @param precipitacion
     * @return 
     */
    public double chequearPrecipitacion(String precipitacion){
        double pre = Double.parseDouble(precipitacion);
        int precipitacionBuena = 2500;
        return pre/precipitacionBuena;
    }
}
