package Clases;

import Enums.EstadoOrden;

import java.sql.*;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class DBHandler {
    private final static String url = "jdbc:sqlite:taller_mecanico.db";

    public static boolean conectar() {
        try(Connection connection = DriverManager.getConnection(url)){
            return true;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //METODO PARA CREAR LAS TABLAS
    public static boolean crearTablas(){

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
                                        "especialidad VARCHAR(80) NOT NULL," +
                                        "salario_por_hora DECIMAL(10, 2) NOT NULL CHECK(salario_por_hora >= 0));";

        //Tabla Ordenes de Trabajo
        String sqlTablaOrdenesTrabajo = "CREATE TABLE ordenes_trabajo (" +
                                            "id_orden INTEGER PRIMARY KEY AUTOINCREMENT," +
                                            "estado TEXT NOT NULL CHECK(estado IN('PENDIENTE', 'EN_PROCESO', 'FINALIZADA', 'CANCELADA'))," +
                                            "horas_trabajadas DECIMAL(10, 2) NOT NULL CHECK(horas_trabajadas >= 0)," +
                                            "patente VARCHAR(7) NOT NULL," +
                                            "dni_mecanico VARCHAR(10) NOT NULL," +
                                            "FOREIGN KEY (patente) REFERENCES vehiculos(patente)," +
                                            "FOREIGN KEY (dni_mecanico) REFERENCES mecanicos(dni));";

        //Tabla Repuestos
        String sqlTablaRepuestos = "CREATE TABLE repuestos (" +
                                        "id_repuesto INTEGER PRIMARY KEY  AUTOINCREMENT," +
                                        "nombre VARCHAR(100) NOT NULL," +
                                        "precio DECIMAL(10, 2) NOT NULL CHECK(precio >= 0)," +
                                        "stock INTEGER NOT NULL CHECK(stock >= 0));";

        //Tabla detalle Ordenes
        String sqlTablaDetalleOrdenes = "CREATE TABLE detalles_orden (" +
                                            "id_orden INTEGER NOT NULL," +
                                            "id_repuesto INTEGER NOT NULL," +
                                            "cantidad INTEGER NOT NULL CHECK(cantidad >= 0)," +
                                            "PRIMARY KEY (id_orden, id_repuesto),"+
                                            "FOREIGN KEY (id_orden) REFERENCES ordenes_trabajo(id_orden)," +
                                            "FOREIGN KEY (id_repuesto) REFERENCES repuestos(id_repuesto));";

        try(Connection connection = DriverManager.getConnection(url);
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }

    //GUARDAR CLIENTE
    public static int guardarCliente(String dni, String nombre, String apellido, String telefono, String email){
        int filasAfectadas = 0;

        String sqlAddCliente = "INSERT INTO clientes(dni, nombre, apellido, telefono, email) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement(sqlAddCliente)){

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, telefono);
            ps.setString(5, email);

            filasAfectadas = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    //GUARDAR MECANICO
    public static int guardarMecanico(String dni, String nombre, String apellido, String especialidad, double salarioXhora){
        int filasAfectadas = 0;

        String sqlAddMecanico = "INSERT INTO mecanicos(dni, nombre, apellido, especialidad, salario_por_hora) VALUES (?, ?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement(sqlAddMecanico)){

            ps.setString(1, dni);
            ps.setString(2, nombre);
            ps.setString(3, apellido);
            ps.setString(4, especialidad);
            ps.setDouble(5, salarioXhora);

            filasAfectadas = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    //GUARDAR VEHICULO
    public static int guardarVehiculo(String patente, double kilometraje, int idModelo, String dni){
        int filasAfectadas = 0;

        String sqlAddVehiculo = "INSERT INTO vehiculos(patente, kilometraje, id_modelo, dni) VALUES (?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement(sqlAddVehiculo)){

            ps.setString(1, patente);
            ps.setDouble(2, kilometraje);
            ps.setInt(3, idModelo);
            ps.setString(4, dni);

            filasAfectadas = ps.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }
        return filasAfectadas;
    }

    //GUARDAR ORDEN DE TRABAJO
    public static int guardarOrdenDeTrabajo(EstadoOrden estado, double horas_trabajadas, String patente, String dni_mecanico){
        int idAgregado = -1;

        String sqlAddOrdenTrabajo = "INSERT INTO ordenes_trabajo(estado, horas_trabajadas, patente, dni_mecanico) VALUES (?, ?, ?, ?)";

        try(Connection connection = DriverManager.getConnection(url);
            PreparedStatement ps = connection.prepareStatement(sqlAddOrdenTrabajo, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, String.valueOf(estado));
            ps.setDouble(2, horas_trabajadas);
            ps.setString(3, patente);
            ps.setString(4, dni_mecanico);

            ps.executeUpdate();

            try(ResultSet rs = ps.getGeneratedKeys()){
                while (rs.next()){
                    idAgregado = rs.getInt(1);
                }
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return idAgregado;
    }


    // todo... METODOS QUE FALTAN:

    //guardarCliente(Cliente c) - void LISTO
    //guardarMecanico(Mecanico m) - void LISTO
    //guardarVehiculo(vehiculo v, int idModelo) - void LIST0
    //guardarOrden(OrdenDeTrabajo O) - void LISTO

    //actualizarStockRepuesto(Repuesto r) - void

    //obtenerRepuestoPorID(int idRepuesto) - retorna Repuesto
    //obtenerClientePorDNI(String dni) - retorna Cliente
    //obtenerMecanicoPorDNI(String dni) - retorna Mecanico
    //obtenerModeloPorID(int idModelo) - reotnra Modelo
    //obtenerVehiculoPorPatente(String patente) - retorna Vehiculo
    //obtenerOrden(int idOrden) - retorna OrdenDeTrabajo



}
