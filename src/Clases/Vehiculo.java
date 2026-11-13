package Clases;

import Exepciones.AccionInvalidaEx;
import Interfaces.Iidentificable;

public class Vehiculo implements Iidentificable <String> {

    private String patente;
    private Double kilometraje;
    private Cliente propietario;
    private Modelo modelo;

    public Vehiculo(String patente, Double kilometraje, Cliente propietario, Modelo modelo) throws AccionInvalidaEx {

        if (patente == null) {
            throw new AccionInvalidaEx("Patente vacia");
        }

        if (kilometraje == null || kilometraje < 0) {
            throw new AccionInvalidaEx("Kilometraje invalido");
        }

        if (propietario == null) {
            throw new AccionInvalidaEx("Propietario invalido");
        }

        if (modelo == null) {
            throw new AccionInvalidaEx("Modelo invalido");
        }

        this.patente = patente;
        this.kilometraje = kilometraje;
        this.propietario = propietario;
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }

    public Double getKilometraje() {
        return kilometraje;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public void setKilometraje(Double kilometraje) {
        this.kilometraje = kilometraje;
    }

    @Override
    public String getId() {
        return this.patente;
    }

    public void actualizarKilometraje(double nuevoKilometraje)throws AccionInvalidaEx {
        if(nuevoKilometraje > this.kilometraje) {
            this.kilometraje = nuevoKilometraje;
        } else {
            throw new AccionInvalidaEx("el km nuevo es menor al actual");
        }
    }
}

