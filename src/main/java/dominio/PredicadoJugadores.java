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
            boolean valorSubExpresionIzq = pasaConsultaRec(nodoActual.getIzq(),p);
            boolean valorSubExpresionDer = pasaConsultaRec(nodoActual.getDer(),p);
            switch (nodoActual.getTipoNodoConsulta()){
                case Or :
                    return valorSubExpresionIzq || valorSubExpresionDer;
                case And:
                    return valorSubExpresionIzq && valorSubExpresionDer;
                case NombreIgual:
                    return p.getNombre().equals(nodoActual.getValorString());
                case EdadMayor:
                    return p.getEdad() > nodoActual.getValorInt();
                case Nacionalidad:
                    return p.getNacionalidad().equals(nodoActual.getValorNacionalidad());
            }
            return true;
        }
    }