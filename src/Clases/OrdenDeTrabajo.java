package Clases;

import Enums.EstadoOrden;
import Interfaces.Iidentificable;

import java.util.HashMap;

public class OrdenDeTrabajo implements Iidentificable <Integer>{

    private int idOrden;
    private String descripcionProblema;
    private double horasTrabajadas;
    private EstadoOrden estadoOrden;
    private HashMap<Integer, DetalleOrden> detallesOrden;
    private Vehiculo vehiculo;
    private Mecanico mecanico;

    public OrdenDeTrabajo(int idOrden, String descripcionProblema, double horasTrabajadas, EstadoOrden estadoOrden, Vehiculo vehiculo, Mecanico mecanico) throws AccionInvalidaEx {

        if (descripcionProblema == null) {
            throw new AccionInvalidaEx("Descripcion vacia");
        }

        if (horasTrabajadas < 0) {
            throw new AccionInvalidaEx("Horas trabajadas invalidas");
        }

        if (estadoOrden == null) {
            throw new AccionInvalidaEx("Estado invalido");
        }

        if (vehiculo == null) {
            throw new AccionInvalidaEx("Vehiculo invalido");
        }

        if (mecanico == null) {
            throw new AccionInvalidaEx("Mecanico invalido");
        }

        this.idOrden = idOrden;
        this.descripcionProblema = descripcionProblema;
        this.horasTrabajadas = horasTrabajadas;
        this.estadoOrden = estadoOrden;
        this.detallesOrden = new HashMap<>();
        this.vehiculo = vehiculo;
        this.mecanico = mecanico;
    }

    public int getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(int idOrden) {
        this.idOrden = idOrden;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }

    public double getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(double horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public EstadoOrden getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(EstadoOrden estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public HashMap<Integer, DetalleOrden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(HashMap<Integer, DetalleOrden> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Mecanico getMecanico() {
        return mecanico;
    }

    public void setMecanico(Mecanico mecanico) {
        this.mecanico = mecanico;
    }

    @Override
    public Integer getId() {
        return this.idOrden;
    }

    public Repuesto buscarRepuestoID(int idRep){
        for(DetalleOrden d : detallesOrden.values()){
            if(d.getIdRepuesto() == idRep){
                return d.getRepuesto();
            }
        }
        return null;
    }

    public void añadirRepuesto (int idRepuesto, int cantidad){
        DetalleOrden detalleOrden = new DetalleOrden(idRepuesto, cantidad);
        detallesOrden.put(1, detalleOrden);
    }

}
