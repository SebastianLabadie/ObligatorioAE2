package dominio;

import Exceptions.DuplicadoExcpetion;

public class VisitorOrdenador<T extends Comparable<T>> implements Visitor<T> {
    //ACA VA MI IMPLEMENTACION DE ARBOL
    ABB<T> elArbol=new ABB<T>();

    public ABB<T> getElArbol() {
        return elArbol;
    }

    @Override
    public void visitar(T dato) {
        try {
            elArbol.insertarDato(dato);
        } catch (DuplicadoExcpetion e) {
            System.out.println("duplicado exc");
        }
    }
}
