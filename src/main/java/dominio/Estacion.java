package dominio;

import Exceptions.FormatoIdException;

public class Estacion {

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

        if (!this.Codigo.matches("^[a-zA-Z]{3}\\d{3}$")|| this.Codigo.length() != 6 ) {
            throw new FormatoIdException();
        }

    }


}
