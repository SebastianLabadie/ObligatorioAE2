package dominio;

import Exceptions.DuplicadoExcpetion;

public interface Visitor<T> {
    void visitar(T dato);
}
