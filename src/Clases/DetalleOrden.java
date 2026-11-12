package Clases;

public class DetalleOrden {

    private Repuesto repuesto;
    private int cantidad;

    public DetalleOrden(Repuesto repuesto, int cantidad) {
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

    public double calcularSubtotal(){
        return this.repuesto.getPrecio() * this.cantidad;
    }

}
