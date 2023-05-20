package dominio;

import Exceptions.FormatoIdException;
import Exceptions.VacioException;

public class Estacion implements Comparable<Estacion> {

    private String Codigo;

    private String Nombre;

    public String getCodigo() {
        return Codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public Estacion(String codigo, String nombre) {
        this.Codigo = codigo;
        this.Nombre = nombre;
    }

    public void Validar() throws FormatoIdException {
        if (this.Nombre.isEmpty() || this.Codigo.isEmpty())throw new FormatoIdException();

        if (!this.Codigo.matches("^[A-Z]{3}\\d{3}$")|| this.Codigo.length() != 6 ) {
            throw new FormatoIdException();
        }

    }


    public static Estacion of(String codigo, String nombre) throws VacioException {
        return new Estacion(Validador.noVacio(codigo),Validador.noVacio(nombre));
    }

    @Override
    public String toString() {
        return  this.Codigo + ";" + this.Nombre;
    }

    @Override
    public int compareTo(Estacion otraEstacion) {
        return this.Codigo.compareTo(otraEstacion.getCodigo());
    }
}
