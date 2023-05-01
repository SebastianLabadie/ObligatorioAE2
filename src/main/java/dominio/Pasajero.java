package dominio;

import Exceptions.FormatoIdException;
import interfaz.Nacionalidad;

public class Pasajero implements Comparable<Pasajero> {
    private String identificadorPasajero;

    private Nacionalidad nacionalidad;

    private int edad;

    private String nombre;

    public Pasajero(String identificadorPasajero, Nacionalidad nacionalidad, int edad, String nombre) {
        this.identificadorPasajero = identificadorPasajero;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.nombre = nombre;
    }

    public void validarIdentificacion() throws FormatoIdException {
        // Validar la longitud mínima de identificación
        if (this.identificadorPasajero.length() < 8) {
            throw new FormatoIdException();
        }

        // Extraer el código de nacionalidad e identificación
        String codigoNacionalidad = this.identificadorPasajero.substring(0, 2);
        String numero = this.identificadorPasajero.substring(2);

        // Validar el código de nacionalidad
        if (!codigoNacionalidad.matches("(FR|DE|UK|ES|OT)")) {
            throw new FormatoIdException();
        }

        // Validar el formato de identificación
        if (!numero.matches("^\\d{1,3}(\\.\\d{3})*#\\d$")) {
            throw new FormatoIdException();
        }

    }


    @Override
    public int compareTo(Pasajero o) {
        String identificador1 = this.identificadorPasajero.substring(2);
        String identificador2 = o.identificadorPasajero.substring(2);

         identificador1 = identificador1.replace(".", "");
         identificador2 = identificador2.replace(".", "");
         identificador1 = identificador1.replace("#", "");
         identificador2 = identificador2.replace("#", "");

        int ci1 = Integer.parseInt(identificador1);
        int ci2 = Integer.parseInt(identificador2);

        if (ci1 < ci2) {
            System.out.println(ci1 +"menor que "+ci2);
            return -1;
        } else if (ci1 > ci2) {
            System.out.println(ci1 +"mayor que "+ci2);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return  identificadorPasajero;
    }
}
