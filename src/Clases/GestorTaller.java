package Clases;

import Exepciones.AccionInvalidaEx;

import java.util.ArrayList;

public class GestorTaller {

    private DBHandler db;
    //CLIENTES, MECANICOS, VEHICULOS, REPUESTOS REQUIEREN CLASE GENERICA

    public GestorTaller() {
        this.db = new DBHandler();
        this.db.conectar(); // todo... CONECTAR es un metodo INNECESARIO si las conecciones duran lo que dura la instancia del metodo... Considerarlo
    }


    // ===============================================================================================================================/IMPRIMIR/

    // Este es el metodo genererico
    public <T> String imprimirListado(ArrayList<T> lista) {
        String info = "\n --- Listado --- \n";
        for (T elemento : lista) {
            info += lista.toString() + "\n";
        }
        info += "-----------------------\n";
        return info;
    }


    // ===============================================================================================================================/CLIENTES/


    public String registrarNuevoCliente(String dni, String nombre, String apellido, String telefono, String email) throws AccionInvalidaEx {
        Cliente nuevoCliente = new Cliente(dni, nombre, apellido, telefono, email);
        // db.guardarCliente(nuevoCliente); // Acá se llamaría al INSERT
        return "EXITO: Cliente '" + nombre + " " + apellido + "' registrado correctamente en el sistema.";
    }

    // ===============================================================================================================================/MECANICOS/

    public String registrarNuevoMecanico(String dni, String nombre, String apellido, String telefono, String especialidad, double salario) throws AccionInvalidaEx {
        Mecanico nuevoMecanico = new Mecanico(dni, nombre, apellido, telefono, especialidad, salario);
        // db.guardarMecanico(nuevoMecanico);
        return "EXITO: Mecánico '" + nombre + "' (" + especialidad + ") dado de alta.";
    }


    // ===============================================================================================================================/VEHICULOS/

    public String ingresarVehiculo(String patente, double kilometraje, Modelo modelo, Cliente propietario) throws AccionInvalidaEx {
        Vehiculo nuevoVehiculo = new Vehiculo(patente, kilometraje, propietario, modelo);
        // db.guardarVehiculo(nuevoVehiculo);
        return "EXITO: Vehículo patente [" + patente + "] ingreso al taller a nombre de " + propietario.getNombre() + ".";
    }

    // ===============================================================================================================================/REPUESTOS E INVENTARIO/


    // ===============================================================================================================================/ORDENES/

    public String registrarYFacturarReparacion(int idOrden, Vehiculo vehiculo, Mecanico mecanico, int idRepuestoUsado, int cantidadUsada, double horasTrabajadas, String descProblema) throws AccionInvalidaEx /*throws StockInsuficienteException, OrdenInvalidaException*/ {

        StringBuilder ticket = new StringBuilder();
        ticket.append("\n=== COMPROBANTE DE REPARACIÓN ===\n");
        ticket.append("Orden N°: ").append(idOrden).append("\n");
        ticket.append("Vehículo: ").append(vehiculo.getPatente()).append("\n");
        ticket.append("Mecánico asignado: ").append(mecanico.getNombre()).append("\n");
        ticket.append("---------------------------------\n");

        // Instanciar la Orden con estado PENDIENTE
        OrdenDeTrabajo orden = new OrdenDeTrabajo(idOrden, descProblema, vehiculo, mecanico);

        // Buscar repuesto usando la base de datos (DBHandler)
        // Repuesto rep = db.buscarRepuestoPorId(idRepuestoUsado);
//        if (rep == null) {
//            throw new IllegalArgumentException("El repuesto con ID " + idRepuestoUsado + " no fue encontrado en el inventario.");
//        }

        // Agregar repuesto --- FALTA CREAR REPUESTO (validarlo con exepciones y buscarlo en la dbhandler)
//        orden.agregarRepuesto(rep, cantidadUsada);
//        ticket.append("Detalle de piezas:\n");
//        ticket.append(" - ").append(rep.getNombre()).append(" x").append(cantidadUsada).append(" unidades.\n");

        // Finalizar la orden
        orden.finalizarOrden(horasTrabajadas);
        ticket.append("Mano de obra:\n");
        ticket.append(" - ").append(horasTrabajadas).append(" hs trabajadas.\n");
        ticket.append("---------------------------------\n");

        // E. Calcular total polimórficamente
        double total = orden.calcularTotal();
        ticket.append("ESTADO FINAL: ").append(orden.getEstadoOrden()).append("\n");
        ticket.append("TOTAL A COBRAR: $").append(total).append("\n");
        ticket.append("=================================");

        // Guardar en Base de Datos
        // db.guardarRepuesto(rep); // Guardamos el stock actualizado
        // db.guardarOrden(orden);  // Guardamos la orden finalizada

        return ticket.toString();
    }

}