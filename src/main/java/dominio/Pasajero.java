package dominio;

import Exceptions.FormatoIdException;
import interfaz.Nacionalidad;

public class Pasajero implements Comparable<Pasajero> {


    private String identificadorPasajero;

    private Nacionalidad nacionalidad;
    private int idNumerico;

    private int edad;

    private String nombre;

    public Pasajero(String identificadorPasajero, Nacionalidad nacionalidad, int edad, String nombre) {
        this.identificadorPasajero = identificadorPasajero;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.nombre = nombre;
        this.idNumerico=idToNumeric(identificadorPasajero);
    }

    public Pasajero(String identificadorPasajero) {
        this.identificadorPasajero = identificadorPasajero;
        this.idNumerico=idToNumeric(identificadorPasajero);
    }

    public void validarIdentificacion() throws FormatoIdException {
        if (this.identificadorPasajero.length() < 8) {
            throw new FormatoIdException();
        }

        String codigoNacionalidad = this.identificadorPasajero.substring(0, 2);
        String numero = this.identificadorPasajero.substring(2);

        if (!codigoNacionalidad.matches("(FR|DE|UK|ES|OT)")) {
            throw new FormatoIdException();
        }

        if (!numero.matches("^\\d{1,3}(\\.\\d{3})*#\\d$")) {
            throw new FormatoIdException();
        }

    }


    @Override
    public int compareTo(Pasajero o) {
        return idNumerico-o.idNumerico;
    }

    @Override
    public String toString() {
        return  this.identificadorPasajero+';'+this.nombre+";"+this.edad+";"+this.nacionalidad;

    }

    public String getIdentificadorPasajero() {
        return identificadorPasajero;
    }

    public Nacionalidad getNacionalidad() {
        return nacionalidad;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }

    public  static int idToNumeric(String id){
        String ci = id.substring(2);
        ci = ci.replace(".", "");
        ci = ci.replace("#", "");
        return Integer.parseInt(ci);
    }


}


