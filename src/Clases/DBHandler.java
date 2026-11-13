

package Clases;

import Enums.EstadoOrden;
import Exepciones.AccionInvalidaEx;

import java.sql.*;
import java.util.HashMap;

public class DBHandler {

    private final static String url = "jdbc:sqlite:taller_mecanico.db";

    public static boolean conectar() throws SQLException {
        try (Connection connection = DriverManager.getConnection(url)) {
            return true;
        }
    }

    //METODO PARA CREAR LAS TABLAS
    public static boolean crearTablas() throws SQLException {

        //Tabla Clientes
        String sqlTablaClientes = "CREATE TABLE clientes (" +
                "dni VARCHAR(10) PRIMARY KEY NOT NULL," +
                "nombre VARCHAR(50) NOT NULL," +
                "apellido VARCHAR(70) NOT NULL," +
                "telefono VARCHAR(18) UNIQUE NOT NULL," +
                "email VARCHAR(80) UNIQUE NOT NULL);";

        //Tabla Modelos
        String sqlTablaModelo = "CREATE TABLE modelos (" +
                "id_modelo INTEGER PRIMARY KEY AUTOINCREMENT," +
                "marca VARCHAR(80) NOT NULL," +
                "linea VARCHAR(80) NOT NULL," +
                "anio INTEGER NOT NULL CHECK(anio > 0));";

        //Tabla Vehiculos
        String sqlTablaVehiculos = "CREATE TABLE vehiculos (" +
                "patente VARCHAR(7) PRIMARY KEY NOT NULL," +
                "kilometraje DECIMAL(10, 2) NOT NULL CHECK(kilometraje >= 0)," +
                "id_modelo INTEGER NOT NULL," +
                "dni VARCHAR(10) NOT NULL," +
                "FOREIGN KEY (id_modelo) REFERENCES modelos(id_modelo)," +
                "FOREIGN KEY (dni) REFERENCES clientes(dni));";

        //Tabla Mecanicos
        String sqlTablaMecanicos = "CREATE TABLE mecanicos (" +
                "dni VARCHAR(10) PRIMARY KEY NOT NULL, " +
                "nombre VARCHAR(50) NOT NULL," +
                "apellido VARCHAR(70) NOT NULL," +
                "telefono VARCHAR(18) UNIQUE NOT NULL," +
                "especialidad VARCHAR(80) NOT NULL," +
                "salario_por_hora DECIMAL(10, 2) NOT NULL CHECK(salario_por_hora >= 0));";

        //Tabla Ordenes de Trabajo
        String sqlTablaOrdenesTrabajo = "CREATE TABLE ordenes_trabajo (" +
                "id_orden INTEGER PRIMARY KEY AUTOINCREMENT," +
                "descripcion TEXT NOT NULL," +
                "estado TEXT NOT NULL CHECK(estado IN('PENDIENTE', 'EN_PROCESO', 'FINALIZADA', 'CANCELADA'))," +
                "horas_trabajadas DECIMAL(10, 2) NOT NULL CHECK(horas_trabajadas >= 0)," +
                "patente VARCHAR(7) NOT NULL," +
                "dni_mecanico VARCHAR(10) NOT NULL," +
                "FOREIGN KEY (patente) REFERENCES vehiculos(patente)," +
                "FOREIGN KEY (dni_mecanico) REFERENCES mecanicos(dni));";

        //Tabla Repuestos
        String sqlTablaRepuestos = "CREATE TABLE repuestos (" +
                "id_repuesto INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre VARCHAR(100) NOT NULL," +
                "precio DECIMAL(10, 2) NOT NULL CHECK(precio >= 0)," +
                "stock INTEGER NOT NULL CHECK(stock >= 0));";

        //Tabla detalle Ordenes
        String sqlTablaDetalleOrdenes = "CREATE TABLE detalles_orden (" +
                "id_orden INTEGER NOT NULL," +
                "id_repuesto INTEGER NOT NULL," +
                "cantidad INTEGER NOT NULL CHECK(cantidad >= 0)," +
                "PRIMARY KEY (id_orden, id_repuesto)," +
                "FOREIGN KEY (id_orden) REFERENCES ordenes_trabajo(id_orden)," +
                "FOREIGN KEY (id_repuesto) REFERENCES repuestos(id_repuesto));";

        try (Connection connection = DriverManager.getConnection(url);
             Statement ps = connection.createStatement()) {

            ps.execute("PRAGMA foreign_keys = ON;");
            ps.execute(sqlTablaClientes);
            ps.execute(sqlTablaModelo);
            ps.execute(sqlTablaVehiculos);
            ps.execute(sqlTablaMecanicos);
            ps.execute(sqlTablaOrdenesTrabajo);
            ps.execute(sqlTablaRepuestos);
            ps.execute(sqlTablaDetalleOrdenes);

            return true;
        }
    }

    //GUARDAR CLIENTE
    public static int guardarCliente(String dni, String nombre, String apellido, String telefono, String email) throws SQLException, AccionInvalidaEx {

        int filasAfectadas = 0;

        String sqlAddCliente = "INSERT INTO clientes(dni, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddCliente)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, email);

            filasAfectadas = ps.executeUpdate();
        }

        if (filasAfectadas <= 0) {
            throw new AccionInvalidaEx("No se encontro el cliente");
        }

        return filasAfectadas;
    }

    //GUARDAR MECANICO
    public static int guardarMecanico(String dni, String nombre, String apellido, String telefono, String especialidad, double salarioXhora) throws SQLException, AccionInvalidaEx {

        int filasAfectadas = 0;

        String sqlAddMecanico = "INSERT INTO mecanicos(dni, nombre, apellido, telefono, especialidad, salario_por_hora) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddMecanico)) {

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, especialidad);
            ps.setDouble(6, salarioXhora);

            filasAfectadas = ps.executeUpdate();
        }

        if (filasAfectadas <= 0) {
            throw new AccionInvalidaEx("No se encontro el mecanico");
        }

        return filasAfectadas;
    }

    //GUARDAR VEHICULO
    public static int guardarVehiculo(String patente, double kilometraje, int idModelo, String dni) throws SQLException, AccionInvalidaEx {

        int filasAfectadas = 0;

        String sqlAddVehiculo = "INSERT INTO vehiculos(patente, kilometraje, id_modelo, dni) VALUES (?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddVehiculo)) {

            ps.setString(1, patente);
            ps.setDouble(2, kilometraje);
            ps.setInt(3, idModelo);
            ps.setString(4, dni);

            filasAfectadas = ps.executeUpdate();
        }

        if (filasAfectadas <= 0) {
            throw new AccionInvalidaEx("No se encontro el vehiculo");
        }

        return filasAfectadas;
    }

    //GUARDAR REPUESTO
    public static int guardarRepuesto(String nombre, double precio, int stock) throws SQLException {
        int idAgregado = 0;
        String sqlAddRepuesto = "INSERT INTO repuestos(nombre, precio, stock) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddRepuesto, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nombre);
            ps.setDouble(2, precio);
            ps.setInt(3, stock);
            ps.executeQuery();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    idAgregado = rs.getInt(1);
                }
            }
        }
        return idAgregado;
    }

    //GUARDAR MODELO
    public static int guardarModelo(String marca, String linea, int anio) throws SQLException {

        int filasAfectadas = 0;

        String sqlAddModelo = "INSERT INTO modelos(marca, linea, anio) VALUES (?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddModelo)) {

            ps.setString(1, marca);
            ps.setString(2, linea);
            ps.setInt(3, anio);

            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //GUARDAR ORDEN DE TRABAJO
    public static int guardarOrdenDeTrabajo(String descripcion, EstadoOrden estado, double horas_trabajadas, String patente, String dni_mecanico) throws SQLException {

        int idAgregado = -1;

        String sqlAddOrdenTrabajo = "INSERT INTO ordenes_trabajo(descripcion, estado, horas_trabajadas, patente, dni_mecanico) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlAddOrdenTrabajo, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, descripcion);
            ps.setString(2, String.valueOf(estado));
            ps.setDouble(3, horas_trabajadas);
            ps.setString(4, patente);
            ps.setString(5, dni_mecanico);

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                while (rs.next()) {
                    idAgregado = rs.getInt(1);
                }
            }
        }

        return idAgregado;
    }

    //ACTUALIZAR STOCK REPUESTO
    public static boolean actualizarStockRepuesto(int id_repuesto, int cantidad) throws SQLException {

        String sqlActualizarStock = "UPDATE repuestos SET stock = ? WHERE id_repuesto = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlActualizarStock)) {

            ps.setInt(1, cantidad);
            ps.setInt(2, id_repuesto);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 1) {
                return true;
            }
        }

        return false;
    }

    //ACTUALIZAR KILOMETRAJE DEL VEHICULO
    public static int actualizarKilometrajeVehiculo(String patente, double nuevoKilometraje) throws SQLException {

        int filasAfectadas = 0;

        String sqlUpdateKm = "UPDATE vehiculos SET kilometraje = ? WHERE patente = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlUpdateKm)) {

            ps.setDouble(1, nuevoKilometraje);
            ps.setString(2, patente);

            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //ACTUALIZAR DATOS DE CLIENTE
    public static int actualizarDatosCliente(String dni, String nombre, String apellido, String telefono, String email) throws SQLException {

        int filasAfectadas = 0;

        String sqlUpdateCliente = "UPDATE clientes SET nombre = ?, apellido = ?, telefono = ?, email = ? WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlUpdateCliente)) {

            ps.setString(1, nombre);
            ps.setString(2, apellido);
            ps.setString(3, telefono);
            ps.setString(4, email);
            ps.setString(5, dni);

            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //ACTUALIZAR SALARIO DE MECANICO
    public static int actualizarSalarioMecanico(String dni, double nuevoSalario) throws SQLException {

        int filasAfectadas = 0;

        String sqlUpdateSalario = "UPDATE mecanicos SET salario_por_hora = ? WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlUpdateSalario)) {

            ps.setDouble(1, nuevoSalario);
            ps.setString(2, dni);

            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //OBTENER REPUESTO POR ID
    public static Repuesto getRespuestoPorID(int id_repuesto) throws SQLException, AccionInvalidaEx {

        Repuesto buscado = null;

        String sqlGetRepuesto = "SELECT * FROM repuestos WHERE id_repuesto = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetRepuesto)) {

            ps.setInt(1, id_repuesto);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id_repuesto");
                    String nombre = rs.getString("nombre");
                    double precio = rs.getDouble("precio");
                    int stock = rs.getInt("stock");

                    buscado = new Repuesto(id, nombre, precio, stock);
                }
            }
        }

        if (buscado == null) {
            throw new AccionInvalidaEx("No se encontro el repuesto");
        }

        return buscado;
    }

    //OBTENER CLIENTE POR DNI
    public static Cliente getClientePorDNI(String dni) throws SQLException, AccionInvalidaEx {

        Cliente buscado = null;

        String sqlGetCliente = "SELECT * FROM clientes WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetCliente)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dniB = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String telefono = rs.getString("telefono");
                    String email = rs.getString("email");

                    buscado = new Cliente(dniB, nombre, apellido, telefono, email);
                }
            }
        }

        if (buscado == null) {
            throw new AccionInvalidaEx("No se encontro el cliente");
        }

        return buscado;
    }

    //OBTENER MECANICO POR DNI
    public static Mecanico getMecanicoPorDni(String dni) throws SQLException, AccionInvalidaEx {

        Mecanico buscado = null;

        String sqlGetMecanico = "SELECT * FROM mecanicos WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetMecanico)) {

            ps.setString(1, dni);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String dniB = rs.getString("dni");
                    String nombre = rs.getString("nombre");
                    String apellido = rs.getString("apellido");
                    String telefono = rs.getString("telefono");
                    String especialidad = rs.getString("especialidad");
                    double salario_por_hora = rs.getDouble("salario_por_hora");

                    buscado = new Mecanico(dniB, nombre, apellido, telefono, especialidad, salario_por_hora);
                }
            }
        }

        if (buscado == null) {
            throw new AccionInvalidaEx("No se encontro el mecanico");
        }

        return buscado;
    }

    //OBTENER MODELO POR ID
    public static Modelo getModeloPorID(int idModelo) throws SQLException, AccionInvalidaEx {

        Modelo buscado = null;

        String sqlGetModelo = "SELECT * FROM modelos WHERE id_modelo = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetModelo)) {

            ps.setInt(1, idModelo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id_modelo = rs.getInt("id_modelo");
                    String marca = rs.getString("marca");
                    String linea = rs.getString("linea");
                    int anio = rs.getInt("anio");

                    buscado = new Modelo(id_modelo, marca, linea, anio);
                }
            }
        }

        if (buscado == null) {
            throw new AccionInvalidaEx("No se encontro el modelo");
        }

        return buscado;
    }

    //OBTENER VEHICULO POR PATENTE
    public static Vehiculo getVehiculoPorPatente(String patente) throws SQLException, AccionInvalidaEx {

        Vehiculo buscado = null;

        String sqlGetVehiculo = "SELECT * FROM vehiculos WHERE patente = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetVehiculo)) {

            ps.setString(1, patente);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String patenteB = rs.getString("patente");
                    double kms = rs.getDouble("kilometraje");
                    int idModelo = rs.getInt("id_modelo");
                    String dni = rs.getString("dni");

                    Cliente cliente = getClientePorDNI(dni);

                    if (cliente == null) {
                        throw new AccionInvalidaEx("El cliente es nulo");
                    }

                    Modelo modelo = getModeloPorID(idModelo);

                    if (modelo == null) {
                        throw new AccionInvalidaEx("El modelo esta vacio");
                    }

                    buscado = new Vehiculo(patenteB, kms, cliente, modelo);
                }
            }
        }

        return buscado;
    }

    //OBTENER ORDEN DE TRABAJO POR ID
    public static OrdenDeTrabajo getOrdenDeTrabajoPorID(int id) throws SQLException, AccionInvalidaEx {

        OrdenDeTrabajo buscado = null;

        String sqlGetOrdenDeTrabajo = "SELECT * FROM ordenes_trabajo WHERE id_orden = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlGetOrdenDeTrabajo)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id_orden = rs.getInt("id_orden");
                    String descripcion = rs.getString("descripcion");
                    EstadoOrden estado = EstadoOrden.valueOf(rs.getString("estado"));
                    double horasTrabajadas = rs.getDouble("horas_trabajadas");
                    String patente = rs.getString("patente");
                    String dniMeca = rs.getString("dni_mecanico");

                    Vehiculo vehiculo = getVehiculoPorPatente(patente);

                    if (vehiculo == null) {
                        throw new AccionInvalidaEx("El vehiculo es nulo");
                    }

                    Mecanico mecanico = getMecanicoPorDni(dniMeca);

                    if (mecanico == null) {
                        throw new AccionInvalidaEx("Mecanico nulo");
                    }

                    buscado = new OrdenDeTrabajo(id_orden, descripcion, horasTrabajadas, estado, vehiculo, mecanico);
                }
            }
        }

        return buscado;
    }

    //ELIMINAR CLIENTE
    public static int eliminarCliente(String dni) throws SQLException {

        int filasAfectadas = 0;

        String sqlDeleteCliente = "DELETE FROM clientes WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlDeleteCliente)) {

            ps.setString(1, dni);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //ELIMINAR VEHICULO
    public static int eliminarVehiculo(String patente) throws SQLException {

        int filasAfectadas = 0;

        String sqlDeleteVehiculo = "DELETE FROM vehiculos WHERE patente = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlDeleteVehiculo)) {

            ps.setString(1, patente);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }

    //ELIMINAR MECANICO
    public static int eliminarMecanico(String dni) throws SQLException {

        int filasAfectadas = 0;

        String sqlDeleteMecanico = "DELETE FROM mecanicos WHERE dni = ?";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sqlDeleteMecanico)) {

            ps.setString(1, dni);
            filasAfectadas = ps.executeUpdate();
        }

        return filasAfectadas;
    }


    // TRAER CLIENTES
    public static HashMap<String, Cliente> obtenerTodosLosClientes() throws SQLException, AccionInvalidaEx {

        HashMap<String, Cliente> clientes = new HashMap<>();

        String sql = "SELECT * FROM clientes";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");

                Cliente cliente = new Cliente(dni, nombre, apellido, telefono, email);

                clientes.put(dni, cliente);
            }
        }

        return clientes;
    }


    // TRAER MECANICOS
    public static HashMap<String, Mecanico> obtenerTodosLosMecanicos() throws SQLException, AccionInvalidaEx {

        HashMap<String, Mecanico> mecanicos = new HashMap<>();

        String sql = "SELECT * FROM mecanicos";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String dni = rs.getString("dni");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String telefono = rs.getString("telefono");
                String especialidad = rs.getString("especialidad");
                double salario = rs.getDouble("salario_por_hora");

                Mecanico mecanico = new Mecanico(dni, nombre, apellido, telefono, especialidad, salario);

                mecanicos.put(dni, mecanico);
            }
        }

        return mecanicos;
    }


    // TRAER VEHICULOS
    public static HashMap<String, Vehiculo> obtenerTodosLosVehiculos() throws SQLException, AccionInvalidaEx {

        HashMap<String, Vehiculo> vehiculos = new HashMap<>();

        String sql = "SELECT * FROM vehiculos";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String patente = rs.getString("patente");
                double kilometraje = rs.getDouble("kilometraje");
                int idModelo = rs.getInt("id_modelo");
                String dniCliente = rs.getString("dni");

                Modelo modelo = getModeloPorID(idModelo);
                Cliente cliente = getClientePorDNI(dniCliente);

                Vehiculo vehiculo = new Vehiculo(patente, kilometraje, cliente, modelo);

                vehiculos.put(patente, vehiculo);
            }
        }

        return vehiculos;
    }


    // TRAER REPUESTOS
    public static HashMap<Integer, Repuesto> obtenerTodosLosRepuestos() throws SQLException, AccionInvalidaEx {

        HashMap<Integer, Repuesto> repuestos = new HashMap<>();

        String sql = "SELECT * FROM repuestos";

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id_repuesto");
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                int stock = rs.getInt("stock");

                Repuesto repuesto = new Repuesto(id, nombre, precio, stock);

                repuestos.put(id, repuesto);
            }
        }

        return repuestos;
    }


}
