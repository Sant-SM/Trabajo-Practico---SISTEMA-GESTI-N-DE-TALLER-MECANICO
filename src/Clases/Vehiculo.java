package Clases;

public class Vehiculo {

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

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Double getKilometraje() {
        return kilometraje;
    }

    public void setKilometraje(Double kilometraje) {
        this.kilometraje = kilometraje;
    }

    public Cliente getPropietario() {
        return propietario;
    }

    public void setPropietario(Cliente propietario) {
        this.propietario = propietario;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }



}
