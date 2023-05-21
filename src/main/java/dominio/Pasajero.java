package dominio;

import Exceptions.FormatoIdException;
import Exceptions.NumeroNegativoException;
import Exceptions.VacioException;
import interfaz.Nacionalidad;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Pasajero implements Comparable<Pasajero> {


    private String identificadorPasajero;

    private Nacionalidad nacionalidad;
    private int idNumerico;

    private int edad;

    private String nombre;

    public Pasajero(String identificadorPasajero, Nacionalidad nacionalidad, int edad, String nombre) throws FormatoIdException {
        this.identificadorPasajero = identificadorPasajero;
        this.nacionalidad = nacionalidad;
        this.edad = edad;
        this.nombre = nombre;
        this.validarIdentificacion();

        this.idNumerico=idToNumeric(identificadorPasajero);
    }

    public Pasajero(String identificadorPasajero) throws FormatoIdException {
        this.identificadorPasajero = identificadorPasajero;
        this.validarIdentificacion();


        this.idNumerico=idToNumeric(identificadorPasajero);
    }

    public void validarIdentificacion() throws FormatoIdException {

        //(CodigoNacionalidad)P.NNN.NNN#N
        //(CodigoNacionalidad)PNN.NNN#N

        Pattern patter=Pattern.compile("^(FR|DE|UK|ES|OT)([1-9]\\.[0-9]{3}|[1-9][0-9]{2})(\\.[0-9]{3})(#[0-9]{1})$");
        Matcher matcher = patter.matcher(identificadorPasajero);
        if(!matcher.find()) throw new FormatoIdException();
//            System.out.println("Matcheo: "+matcher.group());
//
//            System.out.println("G1:"+matcher.group(1));
//            System.out.println("G2:"+matcher.group(2));
//            System.out.println("G3:"+matcher.group(3));
//            System.out.println("G4:"+matcher.group(4));
    }

//    public static void main(String[] args) throws FormatoIdException {
//        Pasajero pasajero = new Pasajero("FR10#2");
//        pasajero= new Pasajero("FR102#2");
//        pasajero= new Pasajero("FR10.222.233#2");
//        pasajero= new Pasajero("FR1.456.999#1");
//        pasajero= new Pasajero("UK456.222#2");
//        //(#\.[0-9]{1})
////        pasajero= new Pasajero("FR1.022yayayayyaES2.222");
//        pasajero.validarIdentificacion();
//
//    }

    @Override
    public int compareTo(Pasajero o) {
        return idNumerico-o.idNumerico;
    }

    @Override
    public String toString() {
        return  this.identificadorPasajero+';'+this.nombre+";"+this.edad+";"+this.nacionalidad.getCodigo();
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

    public static Pasajero of(String identificadorPasajero) throws VacioException, FormatoIdException {
        return new Pasajero(Validador.noVacio(identificadorPasajero));
    }

    public static Pasajero of(String identificadorPasajero,  int edad, String nombre) throws VacioException, NumeroNegativoException, FormatoIdException {
        Validador.noVacio(identificadorPasajero);
        Nacionalidad nacionalidad = Nacionalidad.fromCodigo(identificadorPasajero.substring(0,2));

        return new Pasajero(Validador.noVacio(identificadorPasajero),nacionalidad,Validador.positivo(edad),Validador.noVacio(nombre));
    }

    public  static int idToNumeric(String id){
        String ci = id.substring(2);
        ci = ci.replace(".", "");
        ci = ci.replace("#", "");
        return Integer.parseInt(ci);
    }


}


