package Controlador;

public class Ambiente {
    
    /**
     * retorna cual si la precipitacion es buena o mala
     * @param precipitacion
     * @return 
     */
    public int chequearPrecipitacion(String precipitacion){
        int pre = Integer.parseInt(precipitacion);
        int precipitacionBuena = 2500;
        return pre/precipitacionBuena;
    }
}
