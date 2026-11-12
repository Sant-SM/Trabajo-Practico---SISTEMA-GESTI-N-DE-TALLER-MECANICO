package Clases;

import Interfaces.Iidentificable;

public class Repuesto implements Iidentificable <Integer> {

    private int idRepuesto;
    private String nombre;
    private double precio;
    private int stock;

    public Repuesto(int idRepuesto, String nombre, double precio, int stock) {
        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public void setIdRepuesto(int idRepuesto) {
        this.idRepuesto = idRepuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public Integer getId() {
        return this.idRepuesto;
    }

    public void descontarStock(int cantidad){
        //verificar que el stock sea mayor que la cantidad (exepciones)
        this.stock = this.stock - cantidad;
    }
}
