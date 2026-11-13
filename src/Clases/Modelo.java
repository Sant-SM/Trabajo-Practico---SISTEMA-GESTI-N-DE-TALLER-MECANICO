package Clases;

import Exepciones.AccionInvalidaEx;

public class Modelo {

    private int id;
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

    public Modelo(int id, String marca, String linea, int anio) {
        this.id = id;
        this.marca = marca;
        this.linea = linea;
        this.anio = anio;
    }

    public int getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getLinea() {
        return linea;
    }

    public int getAnio() {
        return anio;
    }


    @Override
    public String toString() {
        return "Modelo{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", linea='" + linea + '\'' +
                ", anio=" + anio +
                '}';
    }
}
