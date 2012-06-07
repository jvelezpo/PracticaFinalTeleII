package Controlador;

public class Ambiente {
    
    /**
     * retorna cual si la precipitacion es buena o mala
     * @param precipitacion
     * @return 
     */
    public double chequearPrecipitacion(String precipitacion){
        double pre = Double.parseDouble(precipitacion);
        double retorne;
        if(pre >= 2200 || pre <= 2800){
            retorne = 0.05;
        }else if(pre < 2200){
            retorne = -0.05;
        }else{
            retorne = -0.1;
        }
        return retorne;
    }
}
