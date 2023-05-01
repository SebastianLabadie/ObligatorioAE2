package sistema;

import dominio.Pasajero;
import interfaz.Nacionalidad;
import interfaz.Retorno;

public class main {
    public static void main(String[] args) {
        ImplementacionSistema is = new ImplementacionSistema();
        is.inicializarSistema(22);


        Retorno r = is.registrarPasajero("DE234.233#1","Ana",22);
        System.out.println(r.getValorString());

        r = is.registrarPasajero("FR1.234.233#1","Alberto",22);
        System.out.println(r.getValorString());

        r =is.registrarPasajero("DE5.232.222#1","Jorge",22);
        System.out.println(r.getValorString());


    }
}
