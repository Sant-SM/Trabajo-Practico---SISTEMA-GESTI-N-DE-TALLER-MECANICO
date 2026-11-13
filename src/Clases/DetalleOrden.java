package Clases;

import Exepciones.AccionInvalidaEx;
import Interfaces.Ifacturable;

public class DetalleOrden implements Ifacturable {

    private Repuesto repuesto;
    private int cantidad;

    public DetalleOrden(Repuesto repuesto, int cantidad) throws AccionInvalidaEx {

        if (repuesto == null) {
            throw new AccionInvalidaEx("Repuesto invalido");
        }

        if (cantidad <= 0) {
            throw new AccionInvalidaEx("Cantidad invalida");
        }

        if (cantidad > repuesto.getStock()) {
            throw new AccionInvalidaEx("Stock insuficiente");
        }

        this.repuesto = repuesto;
        this.cantidad = cantidad;
    }

    public Repuesto getRepuesto() {
        return repuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public int getIdRepuesto(){
        return repuesto.getIdRepuesto();
    }

    @Override
    public double calcularTotal() {
        return this.repuesto.getPrecio() * this.cantidad;
    }
}
