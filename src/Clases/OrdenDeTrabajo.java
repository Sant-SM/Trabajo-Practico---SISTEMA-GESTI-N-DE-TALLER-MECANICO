package Clases;

import Enums.EstadoOrden;
import Interfaces.Ifacturable;
import Interfaces.Iidentificable;

import java.util.ArrayList;
import java.util.HashMap;

public class OrdenDeTrabajo implements Iidentificable <Integer>, Ifacturable {

    private int idOrden;
    private String descripcionProblema;
    private double horasTrabajadas;
    private EstadoOrden estadoOrden;
    private ArrayList<DetalleOrden> detallesOrden;
    private Vehiculo vehiculo;
    private Mecanico mecanico;

    public OrdenDeTrabajo(int idOrden, String descripcionProblema, Vehiculo vehiculo, Mecanico mecanico) {
        this.idOrden = idOrden;
        this.descripcionProblema = descripcionProblema;
        this.horasTrabajadas = 0;
        this.estadoOrden = EstadoOrden.PENDIENTE;
        this.detallesOrden = new ArrayList<>();
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

    public ArrayList<DetalleOrden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(ArrayList<DetalleOrden> detallesOrden) {
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

    public void agregarRepuesto(Repuesto repuesto, int cantidad) /*throws StockInsuficienteException, OrdenInvalidaException*/ {
        if (this.estadoOrden == EstadoOrden.FINALIZADA || this.estadoOrden == EstadoOrden.CANCELADA) {
            //throw new OrdenInvalidaException("No se pueden agregar repuestos a una orden cerrada.");
        }

        repuesto.descontarStock(cantidad);

        DetalleOrden nuevoDetalle = new DetalleOrden(repuesto, cantidad);

        this.detallesOrden.add(nuevoDetalle);

        if (this.estadoOrden == EstadoOrden.PENDIENTE) {
            this.estadoOrden = EstadoOrden.EN_PROCESO;
        }
    }

    public void finalizarOrden(double horasTotales) /*throws OrdenInvalidaException*/ {
        if (this.estadoOrden != EstadoOrden.EN_PROCESO) {
            //throw new OrdenInvalidaException("Solo se pueden finalizar órdenes En Proceso.");
        }
        this.horasTrabajadas = horasTotales;
        this.estadoOrden = EstadoOrden.FINALIZADA;
    }

    @Override
    public double calcularTotal() {
        double total = 0;

        total += (this.mecanico.getSalarioPorHora() * this.horasTrabajadas);

        // Suma el subtotal de repuestos
        for (DetalleOrden detalle : this.detallesOrden) {
            total += detalle.calcularSubtotal();
        }

        return total;
    }
}

