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

    public void setRepuesto(Repuesto repuesto) {
        this.repuesto = repuesto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getIdRepuesto(){
        return repuesto.getIdRepuesto();
    }

    public double calcularSubtotal(){
        return repuesto.getPrecio() * cantidad;
    }

}
