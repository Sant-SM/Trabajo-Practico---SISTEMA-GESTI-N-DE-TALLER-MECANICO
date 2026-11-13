
package Clases;

import Enums.EstadoOrden;
import Exepciones.AccionInvalidaEx;
import Exepciones.OrdenInvalidaException;
import Exepciones.StockInsuficienteException;

import java.sql.SQLException;
import java.util.ArrayList;

public class GestorTaller {

    private DBHandler db;

    private CacheMemoria<String, Cliente> cacheClientes;
    private CacheMemoria<String, Mecanico> cacheMecanicos;
    private CacheMemoria<String, Vehiculo> cacheVehiculos;
    private CacheMemoria<Integer, Repuesto> cacheRepuestos;
    private CacheMemoria<Integer, Modelo> cacheModelo;

    // CLIENTES, MECANICOS, VEHICULOS, REPUESTOS REQUIEREN CLASE GENERICA

    public GestorTaller() throws SQLException, AccionInvalidaEx {
        this.db = new DBHandler();
     //   DBHandler.crearTablas();

        this.cacheClientes = new CacheMemoria<>();
        this.cacheClientes.cargarTodos(DBHandler.obtenerTodosLosClientes());

        this.cacheMecanicos = new CacheMemoria<>();
        this.cacheMecanicos.cargarTodos(DBHandler.obtenerTodosLosMecanicos());

        this.cacheVehiculos = new CacheMemoria<>();
        this.cacheVehiculos.cargarTodos(DBHandler.obtenerTodosLosVehiculos());

        this.cacheRepuestos = new CacheMemoria<>();
        this.cacheRepuestos.cargarTodos(DBHandler.obtenerTodosLosRepuestos());

        this.cacheModelo = new CacheMemoria<>();
        this.cacheModelo.cargarTodos(DBHandler.obtenerTodosLosModelos());
    }


    // ===============================================================================================================================/IMPRIMIR/

    // Este es el metodo generico
    public <T> String imprimirListado(ArrayList<T> lista) {
        String info = "\n --- Listado --- \n";
        for (T elemento : lista) {
            info += elemento.toString() + "\n";
        }
        info += "-----------------------\n";
        return info;
    }


    // ===============================================================================================================================/CLIENTES/

    //REGISTRAR UN NUEVO CLIENTE
    public String registrarNuevoCliente(String dni, String nombre, String apellido, String telefono, String email) throws AccionInvalidaEx, SQLException {
        Cliente nuevoCliente = new Cliente(dni, nombre, apellido, telefono, email);

        if(cacheClientes.existe(nuevoCliente.getDni())){
           throw new AccionInvalidaEx("Ya existe un cliente con el dni: "+ dni);
        }

        if(DBHandler.guardarCliente(dni, nombre, apellido, telefono, email) != 1){
            throw new AccionInvalidaEx("No se pudo guardar el cliente en la base de datos");
        }

        cacheClientes.agregar(dni, nuevoCliente);

        return "EXITO: Cliente '" + nombre + "' (" + email + ") dado de alta.";
    }


    //BUSCAR CLIENTE POR DNI
    public Cliente buscarClientePorDni(String dni) throws AccionInvalidaEx{
        if(!cacheClientes.existe(dni)){
            throw new AccionInvalidaEx("No existe cliente con ese dni");
        }

        Cliente cliente = cacheClientes.obtener(dni);

        return cliente;
    }

    //MODIFICAR TELEFONO DE CLIENTE
    public String modificarTelefonoCliente(String dni, String nuevoTelefono) throws AccionInvalidaEx, SQLException {
        if(!cacheClientes.existe(dni)){
            throw new AccionInvalidaEx("No existe cliente con ese dni");
        }
        Cliente c = cacheClientes.obtener(dni);

        int filasAfectadas = DBHandler.actualizarDatosCliente(c.getDni(), c.getNombre(), c.getApellido(), nuevoTelefono, c.getEmail());

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo actualizar el telefono");
        }

        c.setTelefono(nuevoTelefono);

        return "Telefono Actualizado";
    }

    //ELIMINAR CLIENTE
    public String eliminarCliente(String dni) throws AccionInvalidaEx, SQLException {
        if(!cacheClientes.existe(dni)){
            throw new AccionInvalidaEx("No existe cliente con ese dni");
        }

        int filasAfectadas = DBHandler.eliminarCliente(dni);

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo eliminar el cliente");
        }

        Cliente c = cacheClientes.obtener(dni);

        cacheClientes.eliminar(dni);

        return "El cliente " + c.getNombre() + " " + c.getApellido() + "con DNI: " + c.getDni() + " a sido eliminado del sistema";
    }


    // ===============================================================================================================================/MECANICOS/

    public String registrarNuevoMecanico(String dni, String nombre, String apellido, String telefono, String especialidad, double salario) throws AccionInvalidaEx, SQLException {
        Mecanico nuevoMecanico = new Mecanico(dni, nombre, apellido, telefono, especialidad, salario);

        if(cacheMecanicos.existe(nuevoMecanico.getDni())){
            throw new AccionInvalidaEx("Ya existe un mecanico con el dni: "+ dni);
        }
        if(DBHandler.guardarMecanico(dni, nombre, apellido, telefono, especialidad, salario) != 1){
            throw new AccionInvalidaEx("No se pudo guardar el mecanico en la base de datos");
        }

        cacheMecanicos.agregar(dni, nuevoMecanico);

        return "EXITO: Mecanico '" + nombre + "' (" + especialidad + ") dado de alta.";
    }


    //BUSCAR MECANICO POR DNI
    public Mecanico buscarMecanicoPorDni(String dni) throws AccionInvalidaEx{
        if(!cacheMecanicos.existe(dni)){
            throw new AccionInvalidaEx("No existe mecanico con ese dni");
        }

        Mecanico mecanico = cacheMecanicos.obtener(dni);

        return mecanico;
    }

    //MODIFICAR SALARIO DE MECANICO
    public String modificarSalarioDeMecanico(String dni, double nuevoSalario) throws AccionInvalidaEx, SQLException {
        if(!cacheMecanicos.existe(dni)){
            throw new AccionInvalidaEx("No existe mecanico con ese dni");
        }
        Mecanico m = cacheMecanicos.obtener(dni);

        int filasAfectadas = DBHandler.actualizarSalarioMecanico(dni, nuevoSalario);

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo actualizar el salario");
        }

        m.setSalarioPorHora(nuevoSalario);

        return "Salario Actualizado";
    }

    //ELIMINAR MECANICO
    public String eliminarMecanico(String dni) throws AccionInvalidaEx, SQLException {
        if(!cacheMecanicos.existe(dni)){
            throw new AccionInvalidaEx("No existe mecanico con ese dni");
        }

        int filasAfectadas = DBHandler.eliminarMecanico(dni);

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo eliminar al mecanico");
        }

        Mecanico m = cacheMecanicos.obtener(dni);

        cacheMecanicos.eliminar(dni);

        return "El mecanico " + m.getNombre() + " " + m.getApellido() + "con DNI: " + m.getDni() + " a sido eliminado del sistema";
    }



    // ===============================================================================================================================/VEHICULOS/

    public String ingresarVehiculo(String patente, double kilometraje, Modelo modelo, Cliente propietario) throws AccionInvalidaEx, SQLException {

        Vehiculo nuevoVehiculo = new Vehiculo(patente, kilometraje, propietario, modelo);

        if(cacheVehiculos.existe(nuevoVehiculo.getPatente())){
            throw new AccionInvalidaEx("Ya existe un vehiculo con la patente: "+ patente);
        }
        if(DBHandler.guardarVehiculo(patente, kilometraje, modelo.getId(), propietario.getDni()) != 1){
            throw new AccionInvalidaEx("No se pudo guardar el mecanico en la base de datos");
        }

        cacheVehiculos.agregar(patente, nuevoVehiculo);

        return "EXITO: Vehiculo patente [" + patente + "] ingreso al taller a nombre de " + propietario.getNombre() + ".";
    }

    public Vehiculo buscarVehiculoPorPatente(String patente) throws AccionInvalidaEx{
        if(!cacheVehiculos.existe(patente)){
            throw new AccionInvalidaEx("No existe vehiculo con esa patente");
        }

        Vehiculo vehiculo = cacheVehiculos.obtener(patente);

        return vehiculo;
    }

    public String eliminarVehiculo(String patente) throws AccionInvalidaEx, SQLException {
        if(!cacheVehiculos.existe(patente)){
            throw new AccionInvalidaEx("No existe vehiculo con esa patente");
        }

        int filasAfectadas = DBHandler.eliminarVehiculo(patente);

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo eliminar el vehiculo");
        }

        Vehiculo v = cacheVehiculos.obtener(patente);
        cacheVehiculos.eliminar(patente);

        return "El vehiculo con patente " + v.getPatente() + " ha sido eliminado del sistema";
    }

    public String actualizarKilometraje(String patente, double nuevoKilometros) throws AccionInvalidaEx, SQLException {
        if(!cacheVehiculos.existe(patente)){
            throw new AccionInvalidaEx("No existe vehiculo con esa patente");
        }

        Vehiculo v = cacheVehiculos.obtener(patente);

        int filasAfectadas = DBHandler.actualizarKilometrajeVehiculo(patente, nuevoKilometros);

        if(filasAfectadas != 1){
            throw new AccionInvalidaEx("No se pudo actualizar kilometraje");
        }

        v.setKilometraje(nuevoKilometros);

        return "Actualizacion Exitosa";
    }

    // ===============================================================================================================================/MODELOS

    public String ingresarModelo(String marca, String linea, int anio) throws AccionInvalidaEx, SQLException {
        Modelo nuevo = new Modelo(marca, linea, anio);

        int idAgregado = DBHandler.guardarModelo(marca, linea, anio);

        if(idAgregado == -1){
            throw new AccionInvalidaEx("No se pudo guardar en la base de datos");
        }


        cacheModelo.agregar(idAgregado, nuevo);

        return "Se pudo";

    }

    public Modelo buscarModeloPorId(int idModelo) throws AccionInvalidaEx {
        if(!cacheModelo.existe(idModelo)){
            throw new AccionInvalidaEx("No existe modelo con ese id");
        }

        return cacheModelo.obtener(idModelo);
    }

    // ===============================================================================================================================/REPUESTOS E INVENTARIO/

    public String ingresarRepuesto(String nombre, double precio, int stock) throws AccionInvalidaEx, SQLException {
        Repuesto aux = new Repuesto(nombre, precio, stock);

        int idAgregado = DBHandler.guardarRepuesto(nombre, precio, stock);

        if(idAgregado == -1){
            throw new AccionInvalidaEx("No se pudo guardar el repuesto en la base de datos");
        }

        Repuesto nuevoRepuesto = new Repuesto(idAgregado, nombre, precio, stock);

        cacheRepuestos.agregar(idAgregado, nuevoRepuesto);

        return "EXITO: Repuesto ID [" + nuevoRepuesto.getId() + "] se agrego correctamente";
    }

    public Repuesto obtenerRepuestoPorID(int idRepuesto) throws AccionInvalidaEx{
        if(!cacheRepuestos.existe(idRepuesto)){
            throw new AccionInvalidaEx("No se encuentra repuesto con ese id");
        }

        Repuesto repuesto = cacheRepuestos.obtener(idRepuesto);

        return repuesto;
    }

    public String ingresarStock(int idRepuesto, int cantidadAgregada) throws AccionInvalidaEx, SQLException {
        if(!cacheRepuestos.existe(idRepuesto)){
            throw new AccionInvalidaEx("No existe repuesto con ese id");
        }

        if(cantidadAgregada <= 0 ){
            throw new AccionInvalidaEx("Cantidad invalida");
        }

        Repuesto r = cacheRepuestos.obtener(idRepuesto);

        cantidadAgregada += r.getStock();

        if(!DBHandler.actualizarStockRepuesto(idRepuesto, cantidadAgregada)){
            throw new AccionInvalidaEx("No se pudo Actualizar el stock");
        }

        r.setStock(cantidadAgregada);

        return "Stock actualizado con exito";
    }


    // ===============================================================================================================================/ORDENES/

    public String registrarYFacturarReparacion(int idOrden, Vehiculo vehiculo, Mecanico mecanico, int idRepuestoUsado, int cantidadUsada, double horasTrabajadas, String descProblema) throws AccionInvalidaEx, OrdenInvalidaException, StockInsuficienteException, SQLException /*throws StockInsuficienteException, OrdenInvalidaException*/ {

        if(!cacheRepuestos.existe(idRepuestoUsado)){
            throw new AccionInvalidaEx("No existe repuesto con ese id");
        }


        StringBuilder ticket = new StringBuilder();
        ticket.append("\n=== COMPROBANTE DE REPARACION ===\n");
        ticket.append("Orden N: ").append(idOrden).append("\n");
        ticket.append("Vehiculo: ").append(vehiculo.getPatente()).append("\n");
        ticket.append("Mecanico asignado: ").append(mecanico.getNombre()).append("\n");
        ticket.append("---------------------------------\n");


        OrdenDeTrabajo orden = new OrdenDeTrabajo(idOrden, descProblema, vehiculo, mecanico);


        Repuesto repuestoUsado = cacheRepuestos.obtener(idRepuestoUsado);


        orden.agregarRepuesto(repuestoUsado, cantidadUsada);

        ticket.append("Detalle de piezas: \n");

        ticket.append(" - ").append(repuestoUsado.getNombre()).append(" x ").append(cantidadUsada).append(" unidades\n");
        
        orden.finalizarOrden(horasTrabajadas);
        ticket.append("Mano de obra:\n");
        ticket.append(" - ").append(horasTrabajadas).append(" hs trabajadas.\n");
        ticket.append("---------------------------------\n");

        double total = orden.calcularTotal();
        ticket.append("ESTADO FINAL: ").append(orden.getEstadoOrden()).append("\n");
        ticket.append("TOTAL A COBRAR: $").append(total).append("\n");
        ticket.append("=================================");

        DBHandler.actualizarStockRepuesto(repuestoUsado.getIdRepuesto(), repuestoUsado.getStock());
        DBHandler.guardarOrdenDeTrabajo(descProblema, orden.getEstadoOrden(), horasTrabajadas, vehiculo.getPatente(), mecanico.getDni());
        // db.guardarOrden(orden);  // Guardamos la orden finalizada

        return ticket.toString();
    }

    public OrdenDeTrabajo buscarOrdenPorId(int idOrden) throws AccionInvalidaEx, SQLException {
        OrdenDeTrabajo orden = DBHandler.getOrdenDeTrabajoPorID(idOrden);

        if(orden == null){
            throw new AccionInvalidaEx("No existe orden con ese id");
        }

        return orden;
    }

    // ===============================================================================================================================/OBTENER LISTADOS/


    public ArrayList<Cliente> obtenerClientes() throws SQLException, AccionInvalidaEx {
        return new ArrayList<>(DBHandler.obtenerTodosLosClientes().values());
    }

    public ArrayList<Mecanico> obtenerMecanicos() throws SQLException, AccionInvalidaEx {
        return new ArrayList<>(DBHandler.obtenerTodosLosMecanicos().values());
    }

    public ArrayList<Modelo> obtenerModelos() throws SQLException {
        return new ArrayList<>(DBHandler.obtenerTodosLosModelos().values());
    }

    public ArrayList<Vehiculo> obtenerVehiculos() throws SQLException, AccionInvalidaEx {
        return new ArrayList<>(DBHandler.obtenerTodosLosVehiculos().values());
    }

    public ArrayList<Repuesto> obtenerRepuestos() throws SQLException, AccionInvalidaEx {
        return new ArrayList<>(DBHandler.obtenerTodosLosRepuestos().values());
    }


}
