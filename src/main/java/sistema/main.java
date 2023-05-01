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

        //NO EXISTE PASAJERO
        r =is.buscarPasajero("DE5.232.222#4");
        System.out.println(r.getValorString());

        //NO ESTA BIEN EL IDENTIFICADOR
        r =is.buscarPasajero("DE5.232222#1");
        System.out.println(r.getValorString());

        //ESTA BIEN
        r =is.buscarPasajero("DE5.232.222#1");
        System.out.println("Cantidad de recorridas:"+r.getValorInteger().toString() +" valor:"+r.getValorString());


        r =is.listarPasajerosAscendente();
        System.out.println("PASAJEROS:  "+r.getValorString());

        r =is.listarPasajerosDescendente();
        System.out.println("PASAJEROS Descendente:  "+r.getValorString());

    }
}
