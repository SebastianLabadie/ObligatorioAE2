package dominio;

import Exceptions.NumeroNegativoException;
import Exceptions.VacioException;

public class Validador {

    public static String noVacio(String campo)
            throws VacioException {
        if(campo==null || campo.isEmpty() )
            throw new VacioException();
        return campo;
    }
    public static int positivo(int valor)
            throws NumeroNegativoException {
        if(valor<=0 )
            throw new NumeroNegativoException();
        return valor;
    }

    public static double positivo(double valor)
            throws NumeroNegativoException {
        if(valor<=0 )
            throw new NumeroNegativoException();
        return valor;
    }
}