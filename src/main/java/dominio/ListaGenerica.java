package dominio;

import java.util.Objects;
import java.util.function.Predicate;

public class ListaGenerica<T> {

    private class NodoLista {
        private T dato;
        private NodoLista sig;


        public NodoLista() {
        }

        public NodoLista(T dato) {
            this.dato = dato;
        }

        public NodoLista(T dato, NodoLista sig) {
            this.dato = dato;
            this.sig = sig;
        }
    }

    private NodoLista inicio;
    private NodoLista fin;

    public void agregarInicio(T datoNuevo) {
        inicio = new NodoLista(datoNuevo, inicio);
        if (fin == null) fin = inicio;
    }

    public boolean esVacia() {
        return inicio == null;
    }

    public void agregarFinal(T datoNuevo) {
        if (esVacia()) {
            agregarInicio(datoNuevo);
        } else {
            agregarFinalRec(datoNuevo, inicio);
        }
    }

    private void agregarFinalRec(T datoNuevo, NodoLista inicioSublista) {
        if (inicioSublista.sig == null) {
            inicioSublista.sig = new NodoLista(datoNuevo);
            fin = inicioSublista.sig;
        } else {
            agregarFinalRec(datoNuevo, inicioSublista.sig);
        }
    }

    public T removerInicio() {
        Objects.requireNonNull(inicio,"La lista es vaciá");
        if (!esVacia()) {
            T resultado = inicio.dato;
            if (inicio.sig == null) {
                inicio = null;
                fin = null;
            } else {
                inicio = inicio.sig;
            }
            return resultado;
        } else {
            return null;
        }
    }

    public String toString() {
        return toStringRec(inicio);
    }

    private String toStringRec(NodoLista actual) {
        if (actual == null) return "";
        else if (actual.sig == null) {
            return actual.dato + "";
        }
        return actual.dato + "|" + toStringRec(actual.sig);
    }


    public ListaGenerica<T> filtrar(Predicate<T> predicado) {
        ListaGenerica<T> resultado = new ListaGenerica<T>();
        filtrarRec(inicio,resultado,predicado);

        return resultado;
    }

    private void filtrarRec(NodoLista nodoAct, ListaGenerica<T> resultado, Predicate<T> predicado) {
        if (nodoAct ==null){

        }else{
            if (predicado.test(nodoAct.dato)){
                resultado.agregarFinal(nodoAct.dato);
            }
            filtrarRec(nodoAct.sig,resultado,predicado);
        }
    }

    public boolean contains(T dato) {
        return containsRec(inicio, dato);
    }

    private boolean containsRec(NodoLista nodoActual, T dato) {
        if (nodoActual == null) {
            return false;
        } else if (nodoActual.dato.equals(dato)) {
            return true;
        } else {
            return containsRec(nodoActual.sig, dato);
        }
    }

    public double sumaAtributo(Valor<T> atributo){
        return sumaAtributoRec(inicio,atributo);
    }

    private double sumaAtributoRec(NodoLista nodoActual ,Valor<T> atributo) {
        if (nodoActual==null) return  0;

        return atributo.valor(nodoActual.dato) + sumaAtributoRec(nodoActual.sig,atributo);
    }

    public void ordenarPorAtributo(Valor<T> atributo) {
        if (inicio == null || inicio.sig == null) {
            return;
        }

        boolean intercambiado;
        do {
            intercambiado = false;
            NodoLista actual = inicio;
            NodoLista siguiente = inicio.sig;
            while (siguiente != null) {
                if (atributo.valor(actual.dato) > atributo.valor(siguiente.dato)) {
                    T temp = actual.dato;
                    actual.dato = siguiente.dato;
                    siguiente.dato = temp;
                    intercambiado = true;
                }
                actual = siguiente;
                siguiente = siguiente.sig;
            }
        } while (intercambiado);
    }

    public T primero(){
        return inicio.dato;
    }
}