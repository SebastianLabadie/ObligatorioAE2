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


    @Override
    public boolean equals(Object obj) {
        Conexion miCon = (Conexion) obj;
        return this.codigoEstacionDestino == miCon.codigoEstacionDestino && this.codigoEstacionOrigen == miCon.codigoEstacionOrigen && this.ConexionId == miCon.ConexionId;
    }
}
