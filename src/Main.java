import Clases.DBHandler;
import Clases.GestorTaller;
import Clases.OrdenDeTrabajo;
import Exepciones.AccionInvalidaEx;
import Exepciones.OrdenInvalidaException;
import Exepciones.StockInsuficienteException;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GestorTaller gestor;

//        try{
//            DBHandler.conectar();
//
//        } catch (SQLException e) {
//            System.out.println("CACA");
//        }

        try {
            gestor = new GestorTaller();
        } catch (SQLException | AccionInvalidaEx ex) {
            System.out.println("No se pudo iniciar el sistema: " + ex.getMessage());
            return;
        }

        int opcion = -1;

        while (opcion != 0) {
            System.out.println("\n====== GESTION DE TALLER ======");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Buscar cliente");
            System.out.println("3. Modificar telefono de cliente");
            System.out.println("4. Eliminar cliente");
            System.out.println("5. Registrar mecanico");
            System.out.println("6. Buscar mecanico");
            System.out.println("7. Modificar salario de mecanico");
            System.out.println("8. Eliminar mecanico");
            System.out.println("9. Registrar modelo");
            System.out.println("10. Listar modelos");
            System.out.println("11. Registrar vehiculo");
            System.out.println("12. Buscar vehiculo");
            System.out.println("13. Actualizar kilometraje");
            System.out.println("14. Eliminar vehiculo");
            System.out.println("15. Registrar repuesto");
            System.out.println("16. Buscar repuesto");
            System.out.println("17. Ingresar stock");
            System.out.println("18. Registrar reparacion");
            System.out.println("19. Buscar orden de trabajo");
            System.out.println("20. Mostrar listados");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido para la opcion.");
                continue;
            }

            switch (opcion) {
                case 1: {
                    try {
                        System.out.print("DNI: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = teclado.nextLine();
                        System.out.print("Telefono: ");
                        String telefono = teclado.nextLine();
                        System.out.print("Email: ");
                        String email = teclado.nextLine();
                        System.out.println(gestor.registrarNuevoCliente(dni, nombre, apellido, telefono, email));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al registrar: " + ex.getMessage());
                    }
                    break;
                }
                case 2: {
                    try {
                        System.out.print("DNI del cliente: ");
                        System.out.println(gestor.buscarClientePorDni(teclado.nextLine()));
                    } catch (AccionInvalidaEx ex) {
                        System.out.println("Error en la busqueda: " + ex.getMessage());
                    }
                    break;
                }
                case 3: {
                    try {
                        System.out.print("DNI del cliente: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nuevo telefono: ");
                        String telefono = teclado.nextLine();
                        System.out.println(gestor.modificarTelefonoCliente(dni, telefono));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al modificar: " + ex.getMessage());
                    }
                    break;
                }
                case 4: {
                    try {
                        System.out.print("DNI del cliente: ");
                        System.out.println(gestor.eliminarCliente(teclado.nextLine()));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al eliminar: " + ex.getMessage());
                    }
                    break;
                }
                case 5: {
                    try {
                        System.out.print("DNI: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nombre: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = teclado.nextLine();
                        System.out.print("Telefono: ");
                        String telefono = teclado.nextLine();
                        System.out.print("Especialidad: ");
                        String especialidad = teclado.nextLine();
                        System.out.print("Salario por hora: ");
                        double salario = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.println(gestor.registrarNuevoMecanico(dni, nombre, apellido, telefono, especialidad, salario));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al registrar: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El salario debe ser un numero.");
                    }
                    break;
                }
                case 6: {
                    try {
                        System.out.print("DNI del mecanico: ");
                        System.out.println(gestor.buscarMecanicoPorDni(teclado.nextLine()));
                    } catch (AccionInvalidaEx ex) {
                        System.out.println("Error en la busqueda: " + ex.getMessage());
                    }
                    break;
                }
                case 7: {
                    try {
                        System.out.print("DNI del mecanico: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nuevo salario por hora: ");
                        double salario = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.println(gestor.modificarSalarioDeMecanico(dni, salario));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al modificar: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El salario debe ser un numero.");
                    }
                    break;
                }
                case 8: {
                    try {
                        System.out.print("DNI del mecanico: ");
                        System.out.println(gestor.eliminarMecanico(teclado.nextLine()));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al eliminar: " + ex.getMessage());
                    }
                    break;
                }
                case 9: {
                    try {
                        System.out.print("Marca: ");
                        String marca = teclado.nextLine();
                        System.out.print("Linea: ");
                        String linea = teclado.nextLine();
                        System.out.print("Anio: ");
                        int anio = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.ingresarModelo(marca, linea, anio));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al registrar: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El anio debe ser un numero.");
                    }
                    break;
                }
                case 10:
                    try {
                        System.out.println(gestor.imprimirListado(gestor.obtenerModelos()));
                    } catch (SQLException ex) {
                        System.out.println("Error al listar modelos: " + ex.getMessage());
                    }
                    break;
                case 11: {
                    try {
                        System.out.print("Patente: ");
                        String patente = teclado.nextLine();
                        System.out.print("Kilometraje: ");
                        double kilometraje = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.print("ID del modelo: ");
                        int idModelo = Integer.parseInt(teclado.nextLine());
                        System.out.print("DNI del propietario: ");
                        String dniPropietario = teclado.nextLine();

                        System.out.println(gestor.ingresarVehiculo(patente, kilometraje,
                                gestor.buscarModeloPorId(idModelo),
                                gestor.buscarClientePorDni(dniPropietario)));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al registrar vehiculo: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: Ingrese formatos numericos validos para kilometraje e ID.");
                    }
                    break;
                }
                case 12: {
                    try {
                        System.out.print("Patente: ");
                        System.out.println(gestor.buscarVehiculoPorPatente(teclado.nextLine()));
                    } catch (AccionInvalidaEx ex) {
                        System.out.println("Error en la busqueda: " + ex.getMessage());
                    }
                    break;
                }
                case 13: {
                    try {
                        System.out.print("Patente: ");
                        String patente = teclado.nextLine();
                        System.out.print("Nuevo kilometraje: ");
                        double kilometraje = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.println(gestor.actualizarKilometraje(patente, kilometraje));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al actualizar: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El kilometraje debe ser un numero.");
                    }
                    break;
                }
                case 14: {
                    try {
                        System.out.print("Patente: ");
                        System.out.println(gestor.eliminarVehiculo(teclado.nextLine()));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al eliminar: " + ex.getMessage());
                    }
                    break;
                }
                case 15: {
                    try {
                        System.out.print("Nombre del repuesto: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.print("Stock inicial: ");
                        int stock = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.ingresarRepuesto(nombre, precio, stock));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al registrar: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El precio y stock deben ser numericos.");
                    }
                    break;
                }
                case 16: {
                    try {
                        System.out.print("ID del repuesto: ");
                        System.out.println(gestor.obtenerRepuestoPorID(Integer.parseInt(teclado.nextLine())));
                    } catch (AccionInvalidaEx ex) {
                        System.out.println("Error en la busqueda: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El ID debe ser numerico.");
                    }
                    break;
                }
                case 17: {
                    try {
                        System.out.print("ID del repuesto: ");
                        int idRepuesto = Integer.parseInt(teclado.nextLine());
                        System.out.print("Cantidad a ingresar: ");
                        int cantidad = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.ingresarStock(idRepuesto, cantidad));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al ingresar stock: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: ID y cantidad deben ser numericos.");
                    }
                    break;
                }
                case 18: {
                    try {
                        System.out.print("Numero de orden: ");
                        int idOrden = Integer.parseInt(teclado.nextLine());
                        System.out.print("Patente del vehiculo: ");
                        String patente = teclado.nextLine();
                        System.out.print("DNI del mecanico: ");
                        String dniMecanico = teclado.nextLine();
                        System.out.print("ID del repuesto: ");
                        int idRepuesto = Integer.parseInt(teclado.nextLine());
                        System.out.print("Cantidad utilizada: ");
                        int cantidad = Integer.parseInt(teclado.nextLine());
                        System.out.print("Horas trabajadas: ");
                        double horas = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.print("Descripcion del problema: ");
                        String descripcion = teclado.nextLine();

                        System.out.println(gestor.registrarYFacturarReparacion(idOrden,
                                gestor.buscarVehiculoPorPatente(patente),
                                gestor.buscarMecanicoPorDni(dniMecanico),
                                idRepuesto, cantidad, horas, descripcion));

                    } catch (AccionInvalidaEx | SQLException | OrdenInvalidaException | StockInsuficienteException ex) {
                        System.out.println("Error en el registro de la reparacion: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: Los IDs, cantidades y horas deben ser valores numericos.");
                    }
                    break;
                }
                case 19: {
                    try {
                        System.out.print("Numero de orden: ");
                        OrdenDeTrabajo orden = gestor.buscarOrdenPorId(Integer.parseInt(teclado.nextLine()));
                        System.out.println(orden);
                        System.out.println("Total: $" + orden.calcularTotal());
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al buscar la orden: " + ex.getMessage());
                    } catch (NumberFormatException ex) {
                        System.out.println("Error: El ID debe ser numerico.");
                    }
                    break;
                }
                case 20:
                    try {
                        System.out.println("CLIENTES");
                        System.out.println(gestor.imprimirListado(gestor.obtenerClientes()));
                        System.out.println("MECANICOS");
                        System.out.println(gestor.imprimirListado(gestor.obtenerMecanicos()));
                        System.out.println("MODELOS");
                        System.out.println(gestor.imprimirListado(gestor.obtenerModelos()));
                        System.out.println("VEHICULOS");
                        System.out.println(gestor.imprimirListado(gestor.obtenerVehiculos()));
                        System.out.println("REPUESTOS");
                        System.out.println(gestor.imprimirListado(gestor.obtenerRepuestos()));
                    } catch (AccionInvalidaEx | SQLException ex) {
                        System.out.println("Error al mostrar listados: " + ex.getMessage());
                    }
                    break;
                case 0:
                    System.out.println("Cerrando el sistema...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
        teclado.close();
    }
}