package dominio;

public class Tupla<T,U> {
    private T uno;
    private U dos;

    public Tupla(T uno, U dos) {
        this.uno = uno;
        this.dos = dos;
    }

    public T getUno() {
        return uno;
    }

    public void setUno(T uno) {
        this.uno = uno;
    }

    public U getDos() {
        return dos;
    }

    public void setDos(U dos) {
        this.dos = dos;
    }
}
