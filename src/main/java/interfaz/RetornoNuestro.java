package interfaz;

import java.util.Objects;


public class RetornoNuestro {
    public enum Resultado {
        OK, ERROR_1,
    }

    private Resultado resultado;
    private Integer valorInteger;
    private String valorString;

    public RetornoNuestro(Resultado resultado, Integer valorInteger, String valorString) {
        this.resultado = resultado;
        this.valorInteger = valorInteger;
        this.valorString = valorString;
    }

    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public void setValorInteger(Integer valorInteger) {
        this.valorInteger = valorInteger;
    }

    public void setValorString(String valorString) {
        this.valorString = valorString;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public Integer getValorInteger() {
        return valorInteger;
    }

    public String getValorString() {
        return valorString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RetornoNuestro retorno = (RetornoNuestro) o;
        return Objects.equals(valorInteger, retorno.valorInteger)
                && resultado == retorno.resultado && Objects.equals(valorString, retorno.valorString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultado, valorInteger, valorString);
    }


    public static RetornoNuestro ok(String valorString) {
        return new RetornoNuestro(Resultado.OK, null, valorString);
    }

    public static RetornoNuestro ok(int valorInteger) {
        return ok(valorInteger, null);
    }

    public static RetornoNuestro ok(int valorInteger, String valorString) {
        return new RetornoNuestro(Resultado.OK, valorInteger, valorString);
    }

    public static RetornoNuestro error(Resultado error, String msg) {
        return new RetornoNuestro(error, 0, msg);
    }

    public static RetornoNuestro error1(String mensaje) {
        return error(Resultado.ERROR_1, mensaje);
    }


    public boolean isOk() {
        return this.resultado == Resultado.OK;
    }

    @Override
    public String toString() {
        if (this.resultado == Resultado.OK) {
            if (this.valorString != null && valorInteger != null) {
                return String.format("OK[%s,'%s']", valorInteger, valorString);
            } else if (valorString != null) {
                return String.format("OK['%s']", valorString);
            } else if (valorInteger != null) {
                return String.format("OK[%s]", valorInteger);
            } else {
                return "OK";
            }
        }
        return String.format("%s['%s']", resultado.name(), valorString);
    }
}
