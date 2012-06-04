package Controlador;

public class Materiales {
    
    /**
     * revisa si existe el glifosato en la plantacion 
     * @param glifo
     * @return 
     */
    public double chequearGlifosato(String glifo){
        boolean gli = Boolean.parseBoolean(glifo);
        if(gli)
            return 0.05;
        else
            return 0;
    }
    
    /**
     * revisa si existe el aracuat en la plantacion 
     * @param aracuat
     * @return 
     */
    public double chequearAracuat(String aracuat){
        boolean ara = Boolean.parseBoolean(aracuat);
        if(ara)
            return 0.05;
        else
            return 0;
    }
}
