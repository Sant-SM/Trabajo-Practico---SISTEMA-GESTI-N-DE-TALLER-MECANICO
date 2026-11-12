package Clases;

import Exepciones.AccionInvalidaEx;

public class Cliente extends Persona{

    private String email;

    public Cliente(String dni, String nombre, String apellido, String telefono, String email) throws AccionInvalidaEx {

        super(dni, nombre, apellido, telefono);

        if (email == null) {
            throw new AccionInvalidaEx("Email vacio");
        }

        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                super.toString() +
                "email='" + email + '\'' +
                '}';
    }
}
