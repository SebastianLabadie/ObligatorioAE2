package dominio;

public class VisitorListaFinPasajero<Pasajero> implements Visitor<Pasajero> {
    private ListaGenerica<String> lista = new ListaGenerica<String>();

    @Override
    public String toString() {
        return lista.toString();
    }

    @Override
    public void visitar(Pasajero dato) {
        dominio.Pasajero p = (dominio.Pasajero) dato;
        lista.agregarFinal(p.getIdentificadorPasajero());
    }
}
