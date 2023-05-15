package dominio;

public class VisitorListaFin<T> implements Visitor<T> {
    private ListaGenerica<T> lista = new ListaGenerica<T>();

    @Override
    public String toString() {
        return lista.toString();
    }

    @Override
    public void visitar(T dato) {
        lista.agregarFinal(dato);
    }
}
