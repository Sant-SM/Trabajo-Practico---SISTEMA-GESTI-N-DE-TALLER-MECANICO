
package Clases;

import Enums.EstadoOrden;
import Exepciones.AccionInvalidaEx;
import Exepciones.OrdenInvalidaException;
import Exepciones.StockInsuficienteException;
import Interfaces.Ifacturable;
import Interfaces.Iidentificable;

import java.util.ArrayList;

public class OrdenDeTrabajo implements Iidentificable<Integer>, Ifacturable {

    private int idOrden;
    private String descripcionProblema;
    private double horasTrabajadas;
    private EstadoOrden estadoOrden;
    private ArrayList<DetalleOrden> detallesOrden;
    private Vehiculo vehiculo;
    private Mecanico mecanico;

    public OrdenDeTrabajo(int idOrden, String descripcionProblema,
                          Vehiculo vehiculo, Mecanico mecanico) throws AccionInvalidaEx {

        if (descripcionProblema == null) {
            throw new AccionInvalidaEx("Descripcion vacia");
        }
        if (vehiculo == null) {
            throw new AccionInvalidaEx("Vehiculo invalido");
        }
        if (mecanico == null) {
            throw new AccionInvalidaEx("Mecanico invalido");
        }

        this.idOrden = idOrden;
        this.descripcionProblema = descripcionProblema;
        this.horasTrabajadas = 0;
        this.estadoOrden = EstadoOrden.PENDIENTE;
        this.detallesOrden = new ArrayList<>();
        this.vehiculo = vehiculo;
        this.mecanico = mecanico;
    }

    public OrdenDeTrabajo(int idOrden, String descripcionProblema,
                          double horasTrabajadas, EstadoOrden estadoOrden,
                          Vehiculo vehiculo, Mecanico mecanico) {

        this.idOrden = idOrden;
        this.descripcionProblema = descripcionProblema;
        this.horasTrabajadas = horasTrabajadas;
        this.estadoOrden = estadoOrden;
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
        return new ArrayList<>(detallesOrden);
    }

    public void setDetallesOrden(ArrayList<DetalleOrden> detallesOrden) {
        if (detallesOrden != null) {
            this.detallesOrden = new ArrayList<>(detallesOrden);
        }
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
        return idOrden;
    }

    public void agregarRepuesto(Repuesto repuesto, int cantidad)
            throws OrdenInvalidaException, StockInsuficienteException, AccionInvalidaEx {

        if (estadoOrden == EstadoOrden.FINALIZADA ||
                estadoOrden == EstadoOrden.CANCELADA) {
            throw new OrdenInvalidaException("La orden ya esta cerrada");
        }

        if (repuesto == null) {
            throw new AccionInvalidaEx("Repuesto invalido");
        }

        // Primero se crea el detalle y despues se descuenta el stock.
        DetalleOrden nuevoDetalle = new DetalleOrden(repuesto, cantidad);

        repuesto.descontarStock(cantidad);

        detallesOrden.add(nuevoDetalle);

        if (estadoOrden == EstadoOrden.PENDIENTE) {
            estadoOrden = EstadoOrden.EN_PROCESO;
        }
    }

    public void finalizarOrden(double horasTotales)
            throws OrdenInvalidaException {

        if (estadoOrden != EstadoOrden.EN_PROCESO) {
            throw new OrdenInvalidaException(
                    "Solo se pueden finalizar ordenes en proceso"
            );
        }

        if (horasTotales <= 0) {
            throw new OrdenInvalidaException(
                    "Las horas trabajadas deben ser mayores a cero"
            );
        }

        horasTrabajadas = horasTotales;
        estadoOrden = EstadoOrden.FINALIZADA;
    }

    public boolean estaActiva() {
        return estadoOrden == EstadoOrden.PENDIENTE ||
                estadoOrden == EstadoOrden.EN_PROCESO;
    }

    @Override
    public double calcularTotal() {
        double total = mecanico.getSalarioPorHora() * horasTrabajadas;

        for (DetalleOrden detalle : detallesOrden) {
            total += detalle.calcularTotal();
        }

        return total;
    }

    @Override
    public String toString() {
        return "Orden N: " + idOrden +
                "\nDescripcion: " + descripcionProblema +
                "\nEstado: " + estadoOrden +
                "\nVehiculo: " + vehiculo.getPatente() +
                "\nMecanico: " + mecanico.getNombre() +
                "\nHoras trabajadas: " + horasTrabajadas +
                "\nTotal: $" + calcularTotal();
    }
}


