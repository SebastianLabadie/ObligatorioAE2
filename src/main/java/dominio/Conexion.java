package dominio;

import Exceptions.FormatoIdException;

public class Conexion {

    public enum Estado {
        EXCELENTE,
        BUENO,
        MALO,
    }
    private String codigoEstacionOrigen;

    private String codigoEstacionDestino;

    private int ConexionId;

    private double Costo;

    private double Tiempo;

    private double Kilometros;

    private Estado Estado;


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

    public Conexion.Estado getEstado() {
        return Estado;
    }

    public String getCodigoEstacionOrigen() {
        return codigoEstacionOrigen;
    }

    public String getCodigoEstacionDestino() {
        return codigoEstacionDestino;
    }

    public void Validar() throws FormatoIdException {
        if (this.Costo<=0 || this.Kilometros <=0)throw new FormatoIdException();

        if(this.codigoEstacionDestino.isEmpty() || this.codigoEstacionOrigen.isEmpty() || this.Estado == null)throw new FormatoIdException();

        if (!this.codigoEstacionOrigen.matches("^[a-zA-Z]{3}\\d{3}$") || this.codigoEstacionOrigen.length() != 6 ||!this.codigoEstacionDestino.matches("^[a-zA-Z]{3}\\d{3}$") || this.codigoEstacionDestino.length() != 6) {
            throw new FormatoIdException();
        }
    }

}
