import Clases.GestorTaller;
import Clases.OrdenDeTrabajo;
import Exepciones.AccionInvalidaEx;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        GestorTaller gestor;

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

                switch (opcion) {
                    case 1: {
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
                        break;
                    }
                    case 2: {
                        System.out.print("DNI del cliente: ");
                        System.out.println(gestor.buscarClientePorDni(teclado.nextLine()));
                        break;
                    }
                    case 3: {
                        System.out.print("DNI del cliente: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nuevo telefono: ");
                        String telefono = teclado.nextLine();
                        System.out.println(gestor.modificarTelefonoCliente(dni, telefono));
                        break;
                    }
                    case 4: {
                        System.out.print("DNI del cliente: ");
                        System.out.println(gestor.eliminarCliente(teclado.nextLine()));
                        break;
                    }
                    case 5: {
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
                        System.out.println(gestor.registrarNuevoMecanico(dni, nombre, apellido,
                                telefono, especialidad, salario));
                        break;
                    }
                    case 6: {
                        System.out.print("DNI del mecanico: ");
                        System.out.println(gestor.buscarMecanicoPorDni(teclado.nextLine()));
                        break;
                    }
                    case 7: {
                        System.out.print("DNI del mecanico: ");
                        String dni = teclado.nextLine();
                        System.out.print("Nuevo salario por hora: ");
                        double salario = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.println(gestor.modificarSalarioDeMecanico(dni, salario));
                        break;
                    }
                    case 8: {
                        System.out.print("DNI del mecanico: ");
                        System.out.println(gestor.eliminarMecanico(teclado.nextLine()));
                        break;
                    }
                    case 9: {
                        System.out.print("Marca: ");
                        String marca = teclado.nextLine();
                        System.out.print("Linea: ");
                        String linea = teclado.nextLine();
                        System.out.print("Anio: ");
                        int anio = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.(marca, linea, anio));
                        break;
                    }
                    case 10:
                        System.out.println(gestor.imprimirListado(gestor.obtenerModelos()));
                        break;
                    case 11: {
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
                        break;
                    }
                    case 12: {
                        System.out.print("Patente: ");
                        System.out.println(gestor.buscarVehiculoPorPatente(teclado.nextLine()));
                        break;
                    }
                    case 13: {
                        System.out.print("Patente: ");
                        String patente = teclado.nextLine();
                        System.out.print("Nuevo kilometraje: ");
                        double kilometraje = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.println(gestor.actualizarKilometraje(patente, kilometraje));
                        break;
                    }
                    case 14: {
                        System.out.print("Patente: ");
                        System.out.println(gestor.eliminarVehiculo(teclado.nextLine()));
                        break;
                    }
                    case 15: {
                        System.out.print("Nombre del repuesto: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(teclado.nextLine().replace(',', '.'));
                        System.out.print("Stock inicial: ");
                        int stock = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.ingresarRepuesto(nombre, precio, stock));
                        break;
                    }
                    case 16: {
                        System.out.print("ID del repuesto: ");
                        System.out.println(gestor.obtenerRepuestoPorID(Integer.parseInt(teclado.nextLine())));
                        break;
                    }
                    case 17: {
                        System.out.print("ID del repuesto: ");
                        int idRepuesto = Integer.parseInt(teclado.nextLine());
                        System.out.print("Cantidad a ingresar: ");
                        int cantidad = Integer.parseInt(teclado.nextLine());
                        System.out.println(gestor.ingresarStock(idRepuesto, cantidad));
                        break;
                    }
                    case 18: {
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
                        break;
                    }
                    case 19: {
                        System.out.print("Numero de orden: ");
                        OrdenDeTrabajo orden = gestor.buscarOrdenPorId(Integer.parseInt(teclado.nextLine()));
                        System.out.println(orden);
                        System.out.println("Total: $" + orden.calcularTotal());
                        break;
                    }
                    case 20:
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
                        break;
                    case 0:
                        System.out.println("Cerrando el sistema...");
                        break;
                    default:
                        System.out.println("Opcion invalida.");
                }
            } catch (Exception ex) {
                System.out.println("No se pudo realizar la operacion: " + ex.getMessage());
            }
        }

        teclado.close();
    }
}