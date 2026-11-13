package Clases;

import Exepciones.AccionInvalidaEx;
import Exepciones.StockInsuficienteException;
import Interfaces.Iidentificable;

public class Repuesto implements Iidentificable <Integer> {

    private int idRepuesto;
    private String nombre;
    private double precio;
    private int stock;

    public Repuesto(String nombre, double precio, int stock) throws AccionInvalidaEx {

        if (nombre == null) {
            throw new AccionInvalidaEx("Nombre vacio");
        }

        if (precio <= 0) {
            throw new AccionInvalidaEx("Precio invalido");
        }

        if (stock < 0) {
            throw new AccionInvalidaEx("Stock invalido");
        }

        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public Repuesto(int idRepuesto, String nombre, double precio, int stock) {
        this.idRepuesto = idRepuesto;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public int getIdRepuesto() {
        return idRepuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public int getStock() {
        return stock;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public Integer getId() {
        return this.idRepuesto;
    }

    public void descontarStock(int cantidadUsada) throws StockInsuficienteException {
        if(cantidadUsada <= 0) {
            throw new StockInsuficienteException("La cantidad debe ser mayor a cero.");
        }
        if(this.stock < cantidadUsada) {
            throw new StockInsuficienteException ("No hay stock suficiente de: " + this.nombre + ". Disponible: " + this.stock);
        }
        this.stock -= cantidadUsada;
    }


}
