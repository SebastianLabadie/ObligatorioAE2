package dominio;

public class Cola<T> {
    private ListaGenerica<T> lista = new ListaGenerica<>();

    public void add(T dato){
        lista.agregarFinal(dato);
    }

    public T poll(){
        return lista.removerInicio();
    }

    public boolean isEmpty(){
        return lista.esVacia();
    }
}
