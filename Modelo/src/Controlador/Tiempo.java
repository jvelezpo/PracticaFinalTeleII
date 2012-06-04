package Controlador;

public class Tiempo {

    /**
     * Encargado de calcular cuantas matas pueden crecer en el tiempo
     * @param tiempo
     * @param numMatas
     * @param tamFinca
     * @return 
     */
    public int chequearTiempo(String tiempo, String numMatas, String tamFinca) {
        int t = Integer.parseInt(tiempo);
        int matas = Integer.parseInt(numMatas);
        int tamanio = Integer.parseInt(tamFinca);

        int valor = 0;

        if (t == 1) {
            valor = matas;
        } else if (t > 1) {
            valor = (matas) + ((t - 1) * 4 * matas);
        }

        return valor * tamanio;
    }
}
