package dominio;

public class Pila<T> {
    private ListaGenerica<T> lista = new ListaGenerica<>();

    public void push(T dato){
        lista.agregarInicio(dato);
    }

    public T pop(){
        return lista.removerInicio();
    }

    public boolean esVacia(){
        return lista.esVacia();
    }
}
