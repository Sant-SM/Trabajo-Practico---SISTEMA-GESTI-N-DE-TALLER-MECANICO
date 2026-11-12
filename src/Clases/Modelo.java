package Clases;

public class Modelo {

    private String marca;
    private String linea;
    private int anio;

    public Modelo(String marca, String linea, int anio) throws AccionInvalidaEx {

        if (marca == null) {
            throw new AccionInvalidaEx("Marca vacia");
        }

        if (linea == null) {
            throw new AccionInvalidaEx("Linea vacia");
        }

        if (anio < 1886 || anio > 2026) {
            throw new AccionInvalidaEx("Anio invalido");
        }

        this.marca = marca;
        this.linea = linea;
        this.anio = anio;
    }
}
