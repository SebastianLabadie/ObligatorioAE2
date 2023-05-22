package sistema;

import Exceptions.*;
import dominio.*;
import interfaz.*;

public class ImplementacionSistema implements Sistema {

    public ABB<Pasajero> arbolPasajeros;
    public ListaGenerica<Pasajero> listaPasajerosFR;
    public ListaGenerica<Pasajero> listaPasajerosDE;
    public ListaGenerica<Pasajero> listaPasajerosUK;
    public ListaGenerica<Pasajero> listaPasajerosES;
    public ListaGenerica<Pasajero> listaPasajerosOT;

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
        this.listaPasajerosFR = new ListaGenerica<>();
        this.listaPasajerosDE = new ListaGenerica<>();
        this.listaPasajerosES = new ListaGenerica<>();
        this.listaPasajerosOT = new ListaGenerica<>();
        this.listaPasajerosUK = new ListaGenerica<>();

        return Retorno.ok();
    }

    @Override
    public Retorno registrarPasajero(String identificador, String nombre, int edad) {



        try {
            Pasajero nuevoPasajero = Pasajero.of(identificador,edad,nombre);

            nuevoPasajero.validarIdentificacion();
            arbolPasajeros.insertarDato(nuevoPasajero);
            registrarPasajeroPorNacionalidad(nuevoPasajero);

        }catch (FormatoIdException e) {
            return Retorno.error2("El identificador no tiene el formato válido");
        }
        catch (DuplicadoExcpetion e) {
           return Retorno.error3("Ya existe un pasajero registrado con ese identificador.");
        } catch (VacioException | NumeroNegativoException e) {
            return Retorno.error1("Alguno de los parámetros es vacío o null.");
        }

        return Retorno.ok();
    }

    private void registrarPasajeroPorNacionalidad(Pasajero nuevoPasajero) {
       ListaGenerica<Pasajero> lista = obtenerListaPorNacionalidadPasajero(nuevoPasajero.getNacionalidad());

       lista.agregarFinal(nuevoPasajero);
    }

    private ListaGenerica<Pasajero> obtenerListaPorNacionalidadPasajero(Nacionalidad nacionalidad) {
        switch (nacionalidad){
            case Espania -> {
                return listaPasajerosES;
            }
            case ReinoUnido -> {
                return listaPasajerosUK;
            }
            case Alemania -> {
                return listaPasajerosDE;
            }
            case Francia -> {
                return listaPasajerosFR;
            }
        }

        return listaPasajerosOT;
    }

    @Override
    public Retorno filtrarPasajeros(Consulta consulta) {
        if (consulta == null) return Retorno.error1("Consulta nula");

        VisitorListaFinPasajero<Pasajero> lista = new VisitorListaFinPasajero<>();
        PredicadoJugadores predicado = new PredicadoJugadores(consulta.getRaiz());
        this.arbolPasajeros.filtrar(lista,predicado);

//        System.out.println("ola k ase");
//        System.out.println(lista.toString());
//        System.out.println("ola k ase");

        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno buscarPasajero(String identificador) {
        try {
            Pasajero pasajeroABuscar = Pasajero.of(identificador);

            Tupla<Pasajero,Integer> pasajeroFiltrado = arbolPasajeros.buscarDatoRet(pasajeroABuscar);
            if (pasajeroFiltrado == null){
//                System.out.println(identificador);
//                System.out.println("error 2");
                return Retorno.error2("No existe un pasajero registrado con ese identificador");
            }

            return Retorno.ok(pasajeroFiltrado.getDos(),pasajeroFiltrado.getUno().toString());
        } catch (FormatoIdException e) {
            return Retorno.error1("Identificador no tiene formato válido");
        } catch (VacioException e) {
            return Retorno.error1("Identificador vacio");
        }


    }

    @Override
    public Retorno listarPasajerosAscendente() {
        VisitorListaFin<Pasajero> lista = new VisitorListaFin<Pasajero>();
        arbolPasajeros.listarInOrder(lista);
        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno listarPasajerosDescendente() {
        VisitorListaIni<Pasajero> lista = new VisitorListaIni<Pasajero>();
        arbolPasajeros.listarInOrder(lista);
        return Retorno.ok(lista.toString());
    }

    @Override
    public Retorno listarPasajerosPorNacionalidad(Nacionalidad nacionalidad) {
        if (nacionalidad == null){
            return Retorno.error1("Nacionalidad nula");
        }

        ListaGenerica<Pasajero> lista =  obtenerListaPorNacionalidadPasajero(nacionalidad);
        return Retorno.ok(lista.toString());
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
        try {
            Conexion conexion = Conexion.of(codigoEstacionOrigen,codigoEstacionDestino,identificadorConexion,costo,tiempo,kilometros,estadoDelCamino);
            conexion.Validar();

            this.grafoEstaciones.actualizarArista(codigoEstacionOrigen,codigoEstacionDestino,conexion);
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
        } catch (NoExisteCaminoException e) {
            return Retorno.error6("No existe un camino entre el origen y el destino..");
        }
        return Retorno.ok();
    }

    @Override
    public Retorno listadoEstacionesCantTrasbordos(String codigo, int cantidad) {
        try {

            if (cantidad<0) return Retorno.error1("Cantidad menor que 0");

            Estacion estacionTemp = Estacion.of(codigo,"asdasd");
            estacionTemp.Validar();

            VisitorListaFin<Estacion> listaEstaciones = new VisitorListaFin<>();
            VisitorOrdenador<Estacion> arbolEstacion = new VisitorOrdenador<>();
            this.grafoEstaciones.imprimirBreadthFirstSearchConSaltos(codigo,cantidad,arbolEstacion);


            arbolEstacion.getElArbol().listarInOrder(listaEstaciones);
            System.out.println(arbolEstacion.getElArbol().toString());
            System.out.println("resultado: "+listaEstaciones.toString());

            return Retorno.ok(listaEstaciones.toString());
        } catch (IndiceDestinoException | IndiceOrigenException e) {
            return Retorno.error4("Estación no está registrada en el sistema");
        } catch (VacioException e) {
            return Retorno.error2("Codigo es nulo o vacio");
        } catch (FormatoIdException e) {
            return Retorno.error3("Codigo de estacion invalido");
        }
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
