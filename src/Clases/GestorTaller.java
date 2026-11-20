
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

    public GestorTaller() throws SQLException, AccionInvalidaEx {

        this.db = new DBHandler();

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

    // =========================================================
    // IMPRIMIR LISTADOS
    // =========================================================

    public <T> String imprimirListado(ArrayList<T> lista) {

        StringBuilder info = new StringBuilder();

        info.append("\n========== LISTADO ==========\n");

        for (T elemento : lista) {
            info.append(elemento.toString()).append("\n");
            info.append("-----------------------------\n");
        }

        return info.toString();
    }

    // =========================================================
    // CLIENTES
    // =========================================================

    public String registrarNuevoCliente(String dni, String nombre,
                                        String apellido, String telefono,
                                        String email)
            throws AccionInvalidaEx, SQLException {

        int comprobar = Integer.valueOf(dni);

        if(comprobar < 0){
            throw new AccionInvalidaEx("Dni no puede ser negativo");
        }

        Cliente nuevoCliente = new Cliente(
                dni, nombre, apellido, telefono, email
        );

        if (cacheClientes.existe(dni)) {
            throw new AccionInvalidaEx(
                    "Ya existe un cliente con el dni: " + dni
            );
        }

        if (DBHandler.guardarCliente(
                dni, nombre, apellido, telefono, email) != 1) {

            throw new AccionInvalidaEx(
                    "No se pudo guardar el cliente en la base de datos"
            );
        }

        cacheClientes.agregar(dni, nuevoCliente);

        return "EXITO: Cliente '" + nombre + "' dado de alta.";
    }

    public Cliente buscarClientePorDni(String dni)
            throws AccionInvalidaEx {

        if (!cacheClientes.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe cliente con ese dni"
            );
        }

        return cacheClientes.obtener(dni);
    }

    public String modificarTelefonoCliente(String dni, String nuevoTelefono)
            throws AccionInvalidaEx, SQLException {

        if (!cacheClientes.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe cliente con ese dni"
            );
        }

        Cliente cliente = cacheClientes.obtener(dni);

        int filasAfectadas = DBHandler.actualizarDatosCliente(
                dni,
                cliente.getNombre(),
                cliente.getApellido(),
                nuevoTelefono,
                cliente.getEmail()
        );

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo actualizar el telefono"
            );
        }

        cliente.setTelefono(nuevoTelefono);

        return "Telefono actualizado correctamente";
    }

    public String eliminarCliente(String dni)
            throws AccionInvalidaEx, SQLException {

        if (!cacheClientes.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe cliente con ese dni"
            );
        }

        int filasAfectadas = DBHandler.eliminarCliente(dni);

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo eliminar el cliente"
            );
        }

        Cliente cliente = cacheClientes.obtener(dni);
        cacheClientes.eliminar(dni);

        return "El cliente " + cliente.getNombre() + " "
                + cliente.getApellido() + " con DNI: "
                + cliente.getDni() + " fue eliminado";
    }

    // BUSCAR CLIENTES POR NOMBRE

    public ArrayList<Cliente> buscarClientesPorNombre(String nombre)
            throws SQLException, AccionInvalidaEx {

        if (nombre == null) {
            throw new AccionInvalidaEx("Nombre vacio");
        }

        ArrayList<Cliente> encontrados = new ArrayList<>();

        for (Cliente cliente : obtenerClientes()) {

            if (cliente.getNombre().toLowerCase()
                    .contains(nombre.toLowerCase())) {

                encontrados.add(cliente);
            }
        }

        return encontrados;
    }

    // =========================================================
    // MECANICOS
    // =========================================================

    public String registrarNuevoMecanico(String dni, String nombre,
                                         String apellido, String telefono,
                                         String especialidad, double salario)
            throws AccionInvalidaEx, SQLException {

        int comprobar = Integer.valueOf(dni);

        if(comprobar < 0){
            throw new AccionInvalidaEx("Dni no puede ser negativo");
        }

        Mecanico nuevoMecanico = new Mecanico(
                dni, nombre, apellido, telefono, especialidad, salario
        );

        if (cacheMecanicos.existe(dni)) {
            throw new AccionInvalidaEx(
                    "Ya existe un mecanico con el dni: " + dni
            );
        }

        if (DBHandler.guardarMecanico(
                dni, nombre, apellido, telefono,
                especialidad, salario) != 1) {

            throw new AccionInvalidaEx(
                    "No se pudo guardar el mecanico"
            );
        }

        cacheMecanicos.agregar(dni, nuevoMecanico);

        return "EXITO: Mecanico '" + nombre + "' dado de alta.";
    }

    public Mecanico buscarMecanicoPorDni(String dni)
            throws AccionInvalidaEx {

        if (!cacheMecanicos.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe mecanico con ese dni"
            );
        }

        return cacheMecanicos.obtener(dni);
    }

    public String modificarSalarioDeMecanico(String dni, double nuevoSalario)
            throws AccionInvalidaEx, SQLException {

        if (!cacheMecanicos.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe mecanico con ese dni"
            );
        }

        if (nuevoSalario <= 0) {
            throw new AccionInvalidaEx("Salario invalido");
        }

        Mecanico mecanico = cacheMecanicos.obtener(dni);

        int filasAfectadas = DBHandler.actualizarSalarioMecanico(
                dni, nuevoSalario
        );

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo actualizar el salario"
            );
        }

        mecanico.setSalarioPorHora(nuevoSalario);

        return "Salario actualizado correctamente";
    }

    public String eliminarMecanico(String dni)
            throws AccionInvalidaEx, SQLException {

        if (!cacheMecanicos.existe(dni)) {
            throw new AccionInvalidaEx(
                    "No existe mecanico con ese dni"
            );
        }

        int filasAfectadas = DBHandler.eliminarMecanico(dni);

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo eliminar al mecanico"
            );
        }

        Mecanico mecanico = cacheMecanicos.obtener(dni);
        cacheMecanicos.eliminar(dni);

        return "El mecanico " + mecanico.getNombre() + " "
                + mecanico.getApellido() + " fue eliminado";
    }

    // BUSCAR MECANICOS POR NOMBRE

    public ArrayList<Mecanico> buscarMecanicosPorNombre(String nombre)
            throws SQLException, AccionInvalidaEx {

        if (nombre == null) {
            throw new AccionInvalidaEx("Nombre vacio");
        }

        ArrayList<Mecanico> encontrados = new ArrayList<>();

        for (Mecanico mecanico : obtenerMecanicos()) {

            if (mecanico.getNombre().toLowerCase()
                    .contains(nombre.toLowerCase())) {

                encontrados.add(mecanico);
            }
        }

        return encontrados;
    }

    // =========================================================
    // VEHICULOS
    // =========================================================

    public String ingresarVehiculo(String patente, double kilometraje,
                                   Modelo modelo, Cliente propietario)
            throws AccionInvalidaEx, SQLException {

        Vehiculo nuevoVehiculo = new Vehiculo(
                patente, kilometraje, propietario, modelo
        );

        if (cacheVehiculos.existe(patente)) {
            throw new AccionInvalidaEx(
                    "Ya existe un vehiculo con la patente: " + patente
            );
        }

        if (DBHandler.guardarVehiculo(
                patente, kilometraje, modelo.getId(),
                propietario.getDni()) != 1) {

            throw new AccionInvalidaEx(
                    "No se pudo guardar el vehiculo"
            );
        }

        cacheVehiculos.agregar(patente, nuevoVehiculo);

        return "EXITO: Vehiculo patente [" + patente
                + "] registrado a nombre de "
                + propietario.getNombre();
    }

    public Vehiculo buscarVehiculoPorPatente(String patente)
            throws AccionInvalidaEx {

        if (!cacheVehiculos.existe(patente)) {
            throw new AccionInvalidaEx(
                    "No existe vehiculo con esa patente"
            );
        }

        return cacheVehiculos.obtener(patente);
    }

    public String eliminarVehiculo(String patente)
            throws AccionInvalidaEx, SQLException {

        if (!cacheVehiculos.existe(patente)) {
            throw new AccionInvalidaEx(
                    "No existe vehiculo con esa patente"
            );
        }

        int filasAfectadas = DBHandler.eliminarVehiculo(patente);

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo eliminar el vehiculo"
            );
        }

        cacheVehiculos.eliminar(patente);

        return "Vehiculo con patente " + patente
                + " eliminado correctamente";
    }

    public String actualizarKilometraje(String patente,
                                        double nuevoKilometraje)
            throws AccionInvalidaEx, SQLException {

        if (!cacheVehiculos.existe(patente)) {
            throw new AccionInvalidaEx(
                    "No existe vehiculo con esa patente"
            );
        }

        Vehiculo vehiculo = cacheVehiculos.obtener(patente);

        if (nuevoKilometraje < 0) {
            throw new AccionInvalidaEx("Kilometraje invalido");
        }

        int filasAfectadas = DBHandler.actualizarKilometrajeVehiculo(
                patente, nuevoKilometraje
        );

        if (filasAfectadas != 1) {
            throw new AccionInvalidaEx(
                    "No se pudo actualizar kilometraje"
            );
        }

        vehiculo.setKilometraje(nuevoKilometraje);

        return "Kilometraje actualizado correctamente";
    }

    // =========================================================
    // MODELOS
    // =========================================================

    public String ingresarModelo(String marca, String linea, int anio)
            throws AccionInvalidaEx, SQLException {

        Modelo nuevo = new Modelo(marca, linea, anio);

        int idAgregado = DBHandler.guardarModelo(marca, linea, anio);

        if (idAgregado == -1) {
            throw new AccionInvalidaEx(
                    "No se pudo guardar el modelo"
            );
        }

        nuevo = new Modelo(idAgregado, marca, linea, anio);

        cacheModelo.agregar(idAgregado, nuevo);

        return "Modelo registrado correctamente. ID: " + idAgregado;
    }

    public Modelo buscarModeloPorId(int idModelo)
            throws AccionInvalidaEx {

        if (!cacheModelo.existe(idModelo)) {
            throw new AccionInvalidaEx(
                    "No existe modelo con ese id"
            );
        }

        return cacheModelo.obtener(idModelo);
    }

    // =========================================================
    // REPUESTOS E INVENTARIO
    // =========================================================

    public String ingresarRepuesto(String nombre, double precio, int stock)
            throws AccionInvalidaEx, SQLException {

        Repuesto nuevo = new Repuesto(nombre, precio, stock);

        int idAgregado = DBHandler.guardarRepuesto(
                nombre, precio, stock
        );

        if (idAgregado <= 0) {
            throw new AccionInvalidaEx(
                    "No se pudo guardar el repuesto en la base de datos"
            );
        }

        Repuesto nuevoRepuesto = new Repuesto(
                idAgregado, nombre, precio, stock
        );

        cacheRepuestos.agregar(idAgregado, nuevoRepuesto);

        return "EXITO: Repuesto ID [" + idAgregado
                + "] agregado correctamente";
    }

    public Repuesto obtenerRepuestoPorID(int idRepuesto)
            throws AccionInvalidaEx {

        if (!cacheRepuestos.existe(idRepuesto)) {
            throw new AccionInvalidaEx(
                    "No se encuentra repuesto con ese id"
            );
        }

        return cacheRepuestos.obtener(idRepuesto);
    }

    public String ingresarStock(int idRepuesto, int cantidadAgregada)
            throws AccionInvalidaEx, SQLException {

        if (!cacheRepuestos.existe(idRepuesto)) {
            throw new AccionInvalidaEx(
                    "No existe repuesto con ese id"
            );
        }

        if (cantidadAgregada <= 0) {
            throw new AccionInvalidaEx("Cantidad invalida");
        }

        Repuesto repuesto = cacheRepuestos.obtener(idRepuesto);

        int nuevoStock = repuesto.getStock() + cantidadAgregada;

        if (!DBHandler.actualizarStockRepuesto(
                idRepuesto, nuevoStock)) {

            throw new AccionInvalidaEx(
                    "No se pudo actualizar el stock"
            );
        }

        repuesto.setStock(nuevoStock);

        return "Stock actualizado correctamente";
    }

    // BUSCAR REPUESTOS POR NOMBRE

    public ArrayList<Repuesto> buscarRepuestosPorNombre(String nombre)
            throws SQLException, AccionInvalidaEx {

        if (nombre == null) {
            throw new AccionInvalidaEx("Nombre vacio");
        }

        ArrayList<Repuesto> encontrados = new ArrayList<>();

        for (Repuesto repuesto : obtenerRepuestos()) {

            if (repuesto.getNombre().toLowerCase()
                    .contains(nombre.toLowerCase())) {

                encontrados.add(repuesto);
            }
        }

        return encontrados;
    }

    // =========================================================
    // ORDENES DE TRABAJO
    // =========================================================

    public String registrarYFacturarReparacion(
            int idOrden,
            Vehiculo vehiculo,
            Mecanico mecanico,
            int idRepuestoUsado,
            int cantidadUsada,
            double horasTrabajadas,
            String descProblema)
            throws AccionInvalidaEx, OrdenInvalidaException,
            StockInsuficienteException, SQLException {

        if (!cacheRepuestos.existe(idRepuestoUsado)) {
            throw new AccionInvalidaEx(
                    "No existe repuesto con ese id"
            );
        }

        if (vehiculo == null || mecanico == null) {
            throw new AccionInvalidaEx(
                    "Vehiculo o mecanico invalido"
            );
        }

        Repuesto repuestoUsado = cacheRepuestos.obtener(
                idRepuestoUsado
        );

        // Guardamos el stock anterior por si falla el registro.
        int stockAnterior = repuestoUsado.getStock();

        OrdenDeTrabajo orden = new OrdenDeTrabajo(
                0, descProblema, vehiculo, mecanico
        );

        orden.registrarDiagnostico("Reparacion directa en taller");
        orden.agregarRepuesto(repuestoUsado, cantidadUsada);
        orden.finalizarOrden(horasTrabajadas);

        double total = orden.calcularTotal();

        boolean stockActualizado = DBHandler.actualizarStockRepuesto(
                repuestoUsado.getIdRepuesto(),
                repuestoUsado.getStock()
        );

        if (!stockActualizado) {
            repuestoUsado.setStock(stockAnterior);
            throw new AccionInvalidaEx("No se pudo actualizar el stock");
        }

        int idGenerado;

        try {
            idGenerado = DBHandler.guardarOrdenDeTrabajo(
                    orden.getDescripcionProblema(),
                    orden.getEstadoOrden(),
                    horasTrabajadas,
                    vehiculo.getPatente(),
                    mecanico.getDni()
            );

            if (idGenerado <= 0) {
                throw new AccionInvalidaEx("No se pudo guardar la orden");
            }

            DBHandler.guardarDetalleOrden(idGenerado, idRepuestoUsado, cantidadUsada);

        } catch (SQLException | AccionInvalidaEx ex) {
            repuestoUsado.setStock(stockAnterior);
            DBHandler.actualizarStockRepuesto(
                    repuestoUsado.getIdRepuesto(),
                    stockAnterior
            );
            throw ex;
        }

        orden.setIdOrden(idGenerado);

        StringBuilder ticket = new StringBuilder();

        ticket.append("\n=================================\n");
        ticket.append("     COMPROBANTE DE REPARACION\n");
        ticket.append("=================================\n");
        ticket.append("Orden N: ").append(idGenerado).append("\n");
        ticket.append("Vehiculo: ").append(vehiculo.getPatente()).append("\n");
        ticket.append("Mecanico: ").append(mecanico.getNombre()).append("\n");
        ticket.append("---------------------------------\n");

        ticket.append("Descripcion: ")
                .append(descProblema).append("\n");

        ticket.append("Repuesto utilizado: ")
                .append(repuestoUsado.getNombre()).append("\n");

        ticket.append("Cantidad: ")
                .append(cantidadUsada).append("\n");

        ticket.append("Horas trabajadas: ")
                .append(horasTrabajadas).append("\n");

        ticket.append("---------------------------------\n");
        ticket.append("Estado: ")
                .append(orden.getEstadoOrden()).append("\n");

        ticket.append("TOTAL A COBRAR: $")
                .append(total).append("\n");

        ticket.append("=================================\n");

        return ticket.toString();
    }

    public OrdenDeTrabajo buscarOrdenPorId(int idOrden)
            throws AccionInvalidaEx, SQLException {

        OrdenDeTrabajo orden = DBHandler.getOrdenDeTrabajoPorID(
                idOrden
        );

        if (orden == null) {
            throw new AccionInvalidaEx(
                    "No existe orden con ese id"
            );
        }

        return orden;
    }

    public String ingresarAutoATaller(String patente, String dniMecanico, String motivoIngreso) throws AccionInvalidaEx, SQLException {

        Vehiculo vehiculo = buscarVehiculoPorPatente(patente);
        Mecanico mecanico = buscarMecanicoPorDni(dniMecanico);

        OrdenDeTrabajo nuevaOrden = new OrdenDeTrabajo(0, motivoIngreso, vehiculo, mecanico);

        int idGenerado = DBHandler.guardarOrdenDeTrabajo(
                nuevaOrden.getDescripcionProblema(),
                nuevaOrden.getEstadoOrden(),
                0.0,
                vehiculo.getPatente(),
                mecanico.getDni()
        );

        if (idGenerado <= 0) {
            throw new AccionInvalidaEx("No se pudo registrar el ingreso del vehiculo al taller");
        }

        return "EXITO: Vehiculo ingresado. Orden N: " + idGenerado + " abierta en estado PENDIENTE.";
    }

    public String registrarDiagnosticoOrden(int idOrden, String diagnostico) throws AccionInvalidaEx, OrdenInvalidaException, SQLException {

        OrdenDeTrabajo orden = buscarOrdenPorId(idOrden);
        orden.registrarDiagnostico(diagnostico);

        int filas = DBHandler.actualizarDiagnosticoOrden(
                idOrden,
                orden.getDescripcionProblema(),
                orden.getEstadoOrden()
        );

        if (filas != 1) {
            throw new AccionInvalidaEx("No se pudo actualizar el diagnostico en la base de datos");
        }

        return "EXITO: Diagnostico cargado. La orden N: " + idOrden + " paso a EN_PROCESO.";
    }

    public String agregarRepuestoAOrden(int idOrden, int idRepuesto, int cantidad) throws AccionInvalidaEx, OrdenInvalidaException, StockInsuficienteException, SQLException {

        OrdenDeTrabajo orden = buscarOrdenPorId(idOrden);
        Repuesto repuesto = obtenerRepuestoPorID(idRepuesto);
        int stockAnterior = repuesto.getStock();

        orden.agregarRepuesto(repuesto, cantidad);

        if (!DBHandler.actualizarStockRepuesto(idRepuesto, repuesto.getStock())) {
            repuesto.setStock(stockAnterior);
            throw new AccionInvalidaEx("No se pudo actualizar el stock del repuesto");
        }

        try {
            DBHandler.guardarDetalleOrden(idOrden, idRepuesto, cantidad);
            DBHandler.actualizarDiagnosticoOrden(idOrden, orden.getDescripcionProblema(), orden.getEstadoOrden());
        } catch (SQLException ex) {
            repuesto.setStock(stockAnterior);
            DBHandler.actualizarStockRepuesto(idRepuesto, stockAnterior);
            throw ex;
        }

        return "EXITO: Se agrego " + repuesto.getNombre() + " (x" + cantidad + ") a la orden N: " + idOrden;
    }

    public String finalizarYFacturarOrden(int idOrden, double horasTrabajadas)
            throws AccionInvalidaEx, OrdenInvalidaException, SQLException {

        OrdenDeTrabajo orden = buscarOrdenPorId(idOrden);
        orden.finalizarOrden(horasTrabajadas);

        int filas = DBHandler.finalizarOrdenEnBD(idOrden, orden.getEstadoOrden(), horasTrabajadas);
        if (filas != 1) {
            throw new AccionInvalidaEx("No se pudo finalizar la orden en la base de datos");
        }

        StringBuilder ticket = new StringBuilder();
        ticket.append("\n=================================\n");
        ticket.append("     COMPROBANTE DE REPARACION\n");
        ticket.append("=================================\n");
        ticket.append("Orden N: ").append(idOrden).append("\n");
        ticket.append("Vehiculo: ").append(orden.getVehiculo().getPatente()).append("\n");
        ticket.append("Mecanico: ").append(orden.getMecanico().getNombre()).append("\n");
        ticket.append("Detalle: ").append(orden.getDescripcionProblema()).append("\n");
        ticket.append("Horas trabajadas: ").append(horasTrabajadas).append("\n");
        ticket.append("Estado: ").append(orden.getEstadoOrden()).append("\n");
        ticket.append("TOTAL A COBRAR: $").append(orden.calcularTotal()).append("\n");
        ticket.append("=================================\n");

        return ticket.toString();
    }

    // OBTENER ORDENES ACTIVAS

    public ArrayList<OrdenDeTrabajo> obtenerOrdenesActivas()
            throws SQLException, AccionInvalidaEx {

        return DBHandler.obtenerOrdenesActivas();
    }

    // =========================================================
    // OBTENER LISTADOS
    // =========================================================

    public ArrayList<Cliente> obtenerClientes()
            throws SQLException, AccionInvalidaEx {

        return new ArrayList<>(
                DBHandler.obtenerTodosLosClientes().values()
        );
    }

    public ArrayList<Mecanico> obtenerMecanicos()
            throws SQLException, AccionInvalidaEx {

        return new ArrayList<>(
                DBHandler.obtenerTodosLosMecanicos().values()
        );
    }

    public ArrayList<Modelo> obtenerModelos()
            throws SQLException {

        return new ArrayList<>(
                DBHandler.obtenerTodosLosModelos().values()
        );
    }

    public ArrayList<Vehiculo> obtenerVehiculos()
            throws SQLException, AccionInvalidaEx {

        return new ArrayList<>(
                DBHandler.obtenerTodosLosVehiculos().values()
        );
    }

    public ArrayList<Repuesto> obtenerRepuestos()
            throws SQLException, AccionInvalidaEx {

        return new ArrayList<>(
                DBHandler.obtenerTodosLosRepuestos().values()
        );
    }
}
