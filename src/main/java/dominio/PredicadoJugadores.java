package dominio;

import interfaz.Consulta;

import java.util.function.Predicate;

public class PredicadoJugadores implements Predicate<Pasajero> {
        public Consulta.NodoConsulta nodoConsulta;

        public PredicadoJugadores(Consulta.NodoConsulta nodoConsulta){
            this.nodoConsulta = nodoConsulta;
        }

        @Override
        public boolean test(Pasajero pasajero) {
            return pasaConsultaRec(this.nodoConsulta,pasajero);
        }



        private boolean pasaConsultaRec(Consulta.NodoConsulta nodoActual, Pasajero p) {
            if (nodoActual == null) return false;

            return true;
        }
    }