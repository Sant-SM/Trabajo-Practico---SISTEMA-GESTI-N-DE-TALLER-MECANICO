package Clases;

public class Mecanico extends Persona{

    private String especialidad;
    private double salarioPorHora;

    public Mecanico(String dni, String nombre, String apellido, String telefono, String especialidad, double salarioPorHora) {
        super(dni, nombre, apellido, telefono);
        this.especialidad = especialidad;
        this.salarioPorHora = salarioPorHora;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public double getSalarioPorHora() {
        return salarioPorHora;
    }

    public void setSalarioPorHora(double salarioPorHora) {
        this.salarioPorHora = salarioPorHora;
    }

    @Override
    public String toString() {
        return "Mecanico{" +
                super.toString() +
                "especialidad='" + especialidad + '\'' +
                ", salarioPorHora=" + salarioPorHora +
                '}';
    }



}
