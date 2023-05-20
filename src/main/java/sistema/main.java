package sistema;

import dominio.Pasajero;
import interfaz.EstadoCamino;
import interfaz.Nacionalidad;
import interfaz.Retorno;

public class main {
    private static final String MADRID_1 = "MAD001";
    private static final String MADRID_2 = "AAA001";
    private static final String PARIS_1 = "PAR001";
    private static final String PARIS_2 = "PAR001";
    private static final String MONTREAL = "MON001";
    private static final String LYON = "LYO001";
    private static final String LISBOA = "LIS001";
    private static final String LONDRES = "AAA002";


    public static void main(String[] args) {
        ImplementacionSistema is = new ImplementacionSistema();
        is.inicializarSistema(6);

        Retorno r = is.registrarPasajero("FR1.234.233#1","Alberto",22);
        System.out.println(r.getValorString());

        r =is.registrarPasajero("DE5.232.222#1","Jorge",22);
        System.out.println(r.getValorString());

        r = is.registrarPasajero("DE234.233#1","Ana",22);
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


        for (int i = 0; i < 6; i++) {
          is.registrarEstacionDeTren("AAA00" + i, "Un nombre valido");
        }

        r = is.registrarConexion(MADRID_2, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO);
        System.out.println("asd:  "+r.getResultado());

        r = is.registrarConexion(MADRID_2, LONDRES, 1, 100, 20, 300, EstadoCamino.BUENO);
        System.out.println("asd2:  "+r.getResultado());
    }
}
