package dominio;

import Exceptions.FormatoIdException;
import Exceptions.NumeroNegativoException;
import Exceptions.VacioException;
import interfaz.EstadoCamino;

public class Conexion {




    private String codigoEstacionOrigen;

    private String codigoEstacionDestino;

    private int ConexionId;

    private double Costo;

    private double Tiempo;

    private double Kilometros;

    private EstadoCamino Estado;


    public int getConexionId() {
        return ConexionId;
    }

    public double getCosto() {
        return Costo;
    }

    public double getTiempo() {
        return Tiempo;
    }

    public double getKilometros() {
        return Kilometros;
    }

    public EstadoCamino getEstado() {
        return Estado;
    }

    public String getCodigoEstacionOrigen() {
        return codigoEstacionOrigen;
    }

    public String getCodigoEstacionDestino() {
        return codigoEstacionDestino;
    }

    public void setCodigoEstacionOrigen(String codigoEstacionOrigen) {
        this.codigoEstacionOrigen = codigoEstacionOrigen;
    }

    public void setCodigoEstacionDestino(String codigoEstacionDestino) {
        this.codigoEstacionDestino = codigoEstacionDestino;
    }

    public void setConexionId(int conexionId) {
        ConexionId = conexionId;
    }

    public void setCosto(double costo) {
        Costo = costo;
    }

    public void setTiempo(double tiempo) {
        Tiempo = tiempo;
    }

    public void setKilometros(double kilometros) {
        Kilometros = kilometros;
    }

    public void setEstado(EstadoCamino estado) {
        Estado = estado;
    }

    public Conexion(String codigoEstacionOrigen, String codigoEstacionDestino, int conexionId, double costo, double tiempo, double kilometros, EstadoCamino estado) {
        this.codigoEstacionOrigen = codigoEstacionOrigen;
        this.codigoEstacionDestino = codigoEstacionDestino;
        ConexionId = conexionId;
        Costo = costo;
        Tiempo = tiempo;
        Kilometros = kilometros;
        Estado = estado;
    }

    public void Validar() throws FormatoIdException{

        if (!this.codigoEstacionOrigen.matches("^[a-zA-Z]{3}\\d{3}$") || this.codigoEstacionOrigen.length() != 6 ||!this.codigoEstacionDestino.matches("^[a-zA-Z]{3}\\d{3}$") || this.codigoEstacionDestino.length() != 6) {
            throw new FormatoIdException();
        }
    }


    public static Conexion of(String codigoEstacionOrigen, String codigoEstacionDestino, int conexionId, double costo, double tiempo, double kilometros, EstadoCamino estado) throws VacioException, NumeroNegativoException {
        return new Conexion(Validador.noVacio(codigoEstacionOrigen),Validador.noVacio(codigoEstacionDestino),Validador.positivo(conexionId),Validador.positivo(costo),Validador.positivo(tiempo),Validador.positivo(kilometros),estado);
    }

    public double devolverAtributoDinamico(Valor<Conexion> atributo){
        return atributo.valor(this);
    }


    @Override
    public boolean equals(Object obj) {
        Conexion miCon = (Conexion) obj;
        return  this.ConexionId == miCon.ConexionId;
    }

    @Override
    public String toString() {
        return "Conexion{" +
                "codigoEstacionOrigen='" + codigoEstacionOrigen + '\'' +
                ", codigoEstacionDestino='" + codigoEstacionDestino + '\'' +
                ", ConexionId=" + ConexionId +
                ", Costo=" + Costo +
                ", Tiempo=" + Tiempo +
                ", Kilometros=" + Kilometros +
                ", Estado=" + Estado +
                '}';
    }
}
