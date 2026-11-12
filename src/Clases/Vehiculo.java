package Clases;

import Interfaces.Iidentificable;

public class Vehiculo implements Iidentificable <String> {

    private String patente;
    private Double kilometraje;
    private Cliente propietario;
    private Modelo modelo;

    public Vehiculo(String patente, Double kilometraje, Cliente propietario, Modelo modelo) {
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

    public void actualizarKilometraje(double nuevoKilometraje) {
        if(nuevoKilometraje > this.kilometraje) {
            this.kilometraje = nuevoKilometraje;
        } else {
            System.out.println("Error: El kilometraje no puede reducirse.");
        }
    }
}

