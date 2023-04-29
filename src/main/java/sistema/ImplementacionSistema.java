package sistema;

import Exceptions.DuplicadoExcpetion;
import dominio.ABB;
import dominio.ListaGenerica;
import dominio.Pasajero;
import interfaz.*;

import java.util.function.Predicate;

public class ImplementacionSistema implements Sistema {

    @Override
    public Retorno inicializarSistema(int maxEstaciones) {

        //PRUEBA LISTAS
        ListaGenerica<Integer> milista = new ListaGenerica<Integer>();
        for (int i=0;i<=100;i++){
            milista.agregarFinal(i);
        }
        System.out.println(milista.toString());

        ListaGenerica<Integer> milista2 = milista.filtrar(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer%2==0;
            }
        });

        System.out.println(milista2.toString());

        //PRUEBA ABB
        Pasajero p1 = new Pasajero("DE234.233#1",Nacionalidad.fromCodigo("DE"),33,"Ana");
        Pasajero p2 = new Pasajero("FR1.234.233#1",Nacionalidad.fromCodigo("FR"),33,"Alberto");
        Pasajero p3 = new Pasajero("DE5.232.222#1",Nacionalidad.fromCodigo("DE"),33,"Jorge");


        ABB arbolPasajeros = new ABB<Pasajero>();
        try {
            arbolPasajeros.insertarDato(p2);
            arbolPasajeros.insertarDato(p3);
            arbolPasajeros.insertarDato(p1);
            System.out.println(arbolPasajeros.toString());

            arbolPasajeros.imprimir();
        } catch (DuplicadoExcpetion e) {
            System.out.println("Pasajero duplicado rey");
        }



        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarPasajero(String identificador, String nombre, int edad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno filtrarPasajeros(Consulta consulta) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno buscarPasajero(String identificador) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarPasajerosAscendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino,
                                     int identificadorConexion, double costo, double tiempo, double kilometros,
                                     EstadoCamino estadoDeLaConexion) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno actualizarCamino(String codigoEstacionOrigen, String codigoEstacionDestino,
                                    int identificadorConexion, double costo, double tiempo,
                                    double kilometros, EstadoCamino estadoDelCamino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoKilometros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno viajeCostoMinimoEuros(String codigoEstacionOrigen, String codigoEstacionDestino) {
        return null;
    }

}
