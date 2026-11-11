package Clases;

public class Cliente extends Persona{

    private String email;

    public Cliente(String dni, String nombre, String apellido, String telefono, String email) {
        super(dni, nombre, apellido, telefono);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
