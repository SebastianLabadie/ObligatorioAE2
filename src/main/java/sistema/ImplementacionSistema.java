package sistema;

import Exceptions.*;
import dominio.*;
import interfaz.*;

import java.util.function.Predicate;

public class ImplementacionSistema implements Sistema {

    public ABB<Pasajero> arbolPasajeros;
    public Grafo grafoEstaciones;

    @Override
    public Retorno inicializarSistema(int maxEstaciones) {

        //PRUEBA LISTAS
//        ListaGenerica<Integer> milista = new ListaGenerica<Integer>();
//        for (int i=0;i<=100;i++){
//            milista.agregarInicio(i);
//        }
//        System.out.println(milista.toString());
//
//        ListaGenerica<Integer> milista2 = milista.filtrar(new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer num) {
//                return num%2 ==0;
//            }
//        });
//
//        System.out.println(milista2.toString());

        if (maxEstaciones <= 5) return Retorno.error1("Error maxEstaciones es menor o igual a 5");

        this.arbolPasajeros = new ABB<Pasajero>();
        this.grafoEstaciones = new Grafo(maxEstaciones);


        return Retorno.ok();
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
//           arbolPasajeros.imprimir();
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
        try {
            Pasajero pasajeroABuscar = new Pasajero(identificador);
            pasajeroABuscar.validarIdentificacion();

            Tupla<Pasajero,Integer> pasajeroFiltrado = arbolPasajeros.buscarDatoRet(pasajeroABuscar);
            if (pasajeroFiltrado == null){
                return Retorno.error2("No existe un pasajero registrado con ese identificador");
            }

            return Retorno.ok(pasajeroFiltrado.getDos(),pasajeroFiltrado.getUno().toString());
        } catch (FormatoIdException e) {
            return Retorno.error1("Identificador no tiene formato válido");
        }


    }

    @Override
    public Retorno listarPasajerosAscendente() {
        VisitorListaFin<Pasajero> lista = new VisitorListaFin<Pasajero>();
        arbolPasajeros.ListarInOrder(lista);
        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        VisitorListaIni<Pasajero> lista = new VisitorListaIni<Pasajero>();
        arbolPasajeros.ListarInOrder(lista);
        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad) {
        return Retorno.noImplementada();
    }

    @Override
    public Retorno registrarEstacionDeTren(String codigo, String nombre) {

        try{
            Estacion nuevaEstacion = Estacion.of(codigo,nombre);
            nuevaEstacion.Validar();

            this.grafoEstaciones.agregarVertice(nuevaEstacion);
        }catch (DuplicadoExcpetion e){
                return Retorno.error4("Ya existe una estación con ese código.");
        }catch (GrafoFullException e){
                return Retorno.error1("Ya hay registrados maxEstaciones.");
        } catch (FormatoIdException e) {
                return Retorno.error3("El código es inválido.");
        } catch (VacioException e) {
            return Retorno.error2("Codigo o Nombre vacio");
        }


        return Retorno.ok();
    }

    @Override
        public Retorno registrarConexion(String codigoEstacionOrigen, String codigoEstacionDestino,int identificadorConexion, double costo, double tiempo, double kilometros, EstadoCamino estadoDeLaConexion){
        try{
            Conexion conexion = Conexion.of(codigoEstacionOrigen,codigoEstacionDestino,identificadorConexion,costo,tiempo,kilometros,estadoDeLaConexion);
            conexion.Validar();

        this.grafoEstaciones.agregarArista(codigoEstacionOrigen,codigoEstacionDestino,conexion);
        }catch(NumeroNegativoException e){
            return Retorno.error1("alguno de los parámetros double es menor o igual a 0.");
        } catch (VacioException e) {
            return Retorno.error2("Alguno de los valores String es vació o nulo.");
        }catch (FormatoIdException e) {
            return Retorno.error3("Alguno de los Codigos de estacion no cumple con el formatio.");
        } catch (IndiceOrigenException e) {
            return Retorno.error4("No existe la estación de origen.");
        } catch (IndiceDestinoException e) {
            return Retorno.error5("No existe la estación de destino.");
        } catch (DuplicadoExcpetion e) {
            return Retorno.error6("Ya existe uncamino entre el origen y el destino..");
        }


        return Retorno.ok();
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
