package Clases;

import Exepciones.AccionInvalidaEx;
import Interfaces.Iidentificable;

public abstract class Persona implements Iidentificable <String>{

    private String dni;
    private String nombre;
    private String apellido;
    private String telefono;

    public Persona(String dni, String nombre, String apellido, String telefono) throws AccionInvalidaEx {

        if (dni == null) {
            throw new AccionInvalidaEx("DNI vacio");
        }

        if (nombre == null) {
            throw new AccionInvalidaEx("Nombre vacio");
        }

        if (apellido == null) {
            throw new AccionInvalidaEx("Apellido vacio");
        }

        if (telefono == null) {
            throw new AccionInvalidaEx("Telefono vacio");
        }

        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
    }

    public String getDni() {
        return dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String getId() {
        return this.dni;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", telefono='" + telefono + '\'' +
                '}';
    }
}
