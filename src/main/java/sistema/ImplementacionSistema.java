package sistema;

import Exceptions.DuplicadoExcpetion;
import Exceptions.FormatoIdException;
import dominio.ABB;
import dominio.ListaGenerica;
import dominio.Pasajero;
import interfaz.*;

import java.util.function.Predicate;

public class ImplementacionSistema implements Sistema {

    public ABB arbolPasajeros;

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
            public boolean test(Integer num) {
                return num%2 ==0;
            }
        });

        System.out.println(milista2.toString());

        //PRUEBA ABB


        this.arbolPasajeros = new ABB<Pasajero>();




        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarPasajero(String identificador, String nombre, int edad) {
        if (identificador == null || identificador.isEmpty() || nombre == null  || nombre.isEmpty() ||  edad< 0) return Retorno.error1("Alguno de los parámetros es vacío o null.");

        //Arreglar nacionalidad
        Pasajero nuevoPasajero = new Pasajero(identificador, Nacionalidad.fromCodigo("DE"),edad,nombre);


        try {
            nuevoPasajero.validarIdentificacion();
            arbolPasajeros.insertarDato(nuevoPasajero);
//            System.out.println(arbolPasajeros.toString());
//
//            arbolPasajeros.imprimir();
        }catch (FormatoIdException e) {
            return Retorno.error2("El identificador no tiene el formato válido");
        }
        catch (DuplicadoExcpetion e) {
           return Retorno.error3("Ya existe un pasajero registrado con ese identificador.");
        }

        return Retorno.ok();
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
