import Clases.*;
import Exepciones.AccionInvalidaEx;
import Exepciones.OrdenInvalidaException;
import Exepciones.StockInsuficienteException;

import java.sql.SQLException;
import java.util.ArrayList;
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

        //=======================================MENU-PRINCIPAL=======================================
        while (opcion != 0) {
            System.out.println("\n========================================");
            System.out.println("       SISTEMA DE GESTION DE TALLER     ");
            System.out.println("========================================");
            System.out.println("1. Gestion de Clientes");
            System.out.println("2. Gestion de Mecanicos");
            System.out.println("3. Gestion de Modelos y Vehiculos");
            System.out.println("4. Gestion de Repuestos e Inventario");
            System.out.println("5. Ordenes de Trabajo (Ingreso, Diagnostico y Facturacion)");
            System.out.println("6. Reporte General (Todos los listados)");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un numero valido.");
                continue;
            }

            switch (opcion) {
                case 1:
                    menuClientes(teclado, gestor);
                    break;
                case 2:
                    menuMecanicos(teclado, gestor);
                    break;
                case 3:
                    menuVehiculosYModelos(teclado, gestor);
                    break;
                case 4:
                    menuRepuestos(teclado, gestor);
                    break;
                case 5:
                    menuOrdenes(teclado, gestor);
                    break;
                case 6:
                    mostrarReporteGeneral(gestor);
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



    //=======================================MENU-CLIENTE=======================================
    private static void menuClientes(Scanner teclado, GestorTaller gestor) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE CLIENTES ---");
            System.out.println("1. Registrar nuevo cliente");
            System.out.println("2. Buscar cliente por DNI");
            System.out.println("3. Buscar clientes por nombre");
            System.out.println("4. Modificar telefono de cliente");
            System.out.println("5. Eliminar cliente");
            System.out.println("6. Listar todos los clientes");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: {
                    registrarCliente(teclado, gestor);
                    break;
                }
                case 2: {
                    buscarClienteDNI(teclado, gestor);
                    break;
                }
                case 3: {
                    buscarClienteNombre(teclado, gestor);
                    break;
                }
                case 4: {
                    modificarTelefono(teclado, gestor);
                    break;
                }
                case 5: {
                    eliminarCLiente(teclado, gestor);
                    break;
                }
                case 6: {
                   mostrarClientes(gestor);
                   break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    //=======================================MENU-MECANICO=======================================
    private static void menuMecanicos(Scanner teclado, GestorTaller gestor) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE MECANICOS ---");
            System.out.println("1. Registrar nuevo mecanico");
            System.out.println("2. Buscar mecanico por DNI");
            System.out.println("3. Buscar mecanicos por nombre");
            System.out.println("4. Modificar salario por hora");
            System.out.println("5. Eliminar mecanico");
            System.out.println("6. Listar todos los mecanicos");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: {
                    registrarMecanico(teclado, gestor);
                    break;
                }
                case 2: {
                    buscarMecanicoDNI(teclado, gestor);
                    break;
                }
                case 3: {
                    buscarMecanicoNombre(teclado, gestor);
                    break;
                }
                case 4: {
                    actualizarSalarioMecanico(teclado, gestor);
                    break;
                }
                case 5: {
                    eliminarMecanico(teclado, gestor);
                    break;
                }
                case 6: {
                    mostrarMecanicos(teclado, gestor);
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    //=======================================MENU-VEHICULOSyMODELOS=======================================
    private static void menuVehiculosYModelos(Scanner teclado, GestorTaller gestor) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE MODELOS Y VEHICULOS ---");
            System.out.println("1. Registrar modelo");
            System.out.println("2. Listar modelos");
            System.out.println("3. Eliminar modelo");
            System.out.println("4. Registrar vehiculo");
            System.out.println("5. Buscar vehiculo por patente");
            System.out.println("6. Actualizar kilometraje");
            System.out.println("7. Eliminar vehiculo");
            System.out.println("8. Listar todos los vehiculos");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: {
                    registrarModelo(teclado, gestor);
                    break;
                }
                case 2: {
                    mostrarModelos(teclado, gestor);
                    break;
                }
                case 3:
                    eliminarModelo(teclado, gestor);
                    break;
                case 4: {
                    registrarVehiculo(teclado, gestor);
                    break;
                }
                case 5: {
                    buscarVehiculoPatente(teclado, gestor);
                    break;
                }
                case 6: {
                    modificarKilometraje(teclado, gestor);
                    break;
                }
                case 7: {
                    eliminarVehiculo(teclado, gestor);
                    break;
                }
                case 8: {
                    mostrarVehiculos(teclado, gestor);
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    //=======================================MENU-REPUESTOS=======================================
    private static void menuRepuestos(Scanner teclado, GestorTaller gestor) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- GESTION DE REPUESTOS ---");
            System.out.println("1. Registrar nuevo repuesto");
            System.out.println("2. Buscar repuesto por ID");
            System.out.println("3. Buscar repuestos por nombre");
            System.out.println("4. Ingresar stock");
            System.out.println("5. Listar todos los repuestos");
            System.out.println("6. Eliminar repuesto");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: {
                    registrarRepuesto(teclado, gestor);
                    break;
                }
                case 2: {
                    obtenerRepuestoPorID(teclado, gestor);
                    break;
                }
                case 3: {
                    obtenerRepuestoPorNombre(teclado, gestor);
                    break;
                }
                case 4: {
                    actualizarStock(teclado, gestor);
                    break;
                }
                case 5: {
                    mostrarRepuestos(teclado, gestor);
                    break;
                }
                case 6:
                    eliminarRepuesto(teclado, gestor);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }

    private static void menuOrdenes(Scanner teclado, GestorTaller gestor) {
        int op = -1;
        while (op != 0) {
            System.out.println("\n--- ORDENES DE TRABAJO ---");
            System.out.println("1. Paso 1: Ingresar auto al taller (Abrir orden PENDIENTE)");
            System.out.println("2. Paso 2: Cargar diagnostico tecnico (Pasar a EN_PROCESO)");
            System.out.println("3. Paso 3: Agregar repuesto a una orden");
            System.out.println("4. Paso 4: Finalizar y facturar orden");
            System.out.println("5. Reparacion rapida (Ingreso, repuesto y facturacion directa)");
            System.out.println("6. Buscar orden por ID");
            System.out.println("7. Ver ordenes activas (Pendientes / En proceso)");
            System.out.println("8. Ver historial de todas las ordenes");
            System.out.println("9. Eliminar orden finalizada");
            System.out.println("0. Volver al menu principal");
            System.out.print("Opcion: ");

            try {
                op = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un numero valido.");
                continue;
            }

            switch (op) {
                case 1: {
                    paso_1(teclado, gestor);
                    break;
                }
                case 2: {
                    paso_2(teclado, gestor);
                    break;
                }
                case 3: {
                    paso_3(teclado, gestor);
                    break;
                }
                case 4: {
                    paso_4(teclado, gestor);
                    break;
                }
                case 5: {
                    reparacionRapida(teclado, gestor);
                    break;
                }
                case 6: {
                    buscarOrdenPorID(teclado, gestor);
                    break;
                }
                case 7: {
                   mostrarOrdenesActivas(teclado, gestor);
                   break;
                }
                case 8:
                    mostrarTodasLasOrdenes(gestor);
                    break;
                case 9:
                    eliminarOrdenFinalizada(teclado, gestor);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        }
    }


    //METODO REGISTRAR CLIENTE
    public static void registrarCliente(Scanner teclado, GestorTaller gestor)
    {
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
            System.out.println("Error al registrar cliente: " + ex.getMessage());
        }
    }

    //METODO PARA BUSCAR CLIENTE POR DNI
    public static void buscarClienteDNI(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisClientes(gestor);
            System.out.print("DNI del cliente: ");
            System.out.println(gestor.buscarClientePorDni(teclado.nextLine()));
        } catch (AccionInvalidaEx ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO PARA BUSCAR CLIENTE POR NOMBRE
    public static void buscarClienteNombre(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Nombre a buscar: ");
            System.out.println(gestor.imprimirListado(gestor.buscarClientesPorNombre(teclado.nextLine())));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO PARA MODIFICAR TELEFONO DE CLIENTE
    public static void modificarTelefono(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisClientes(gestor);
            System.out.print("DNI del cliente: ");
            String dni = teclado.nextLine();
            System.out.print("Nuevo telefono: ");
            String telefono = teclado.nextLine();
            System.out.println(gestor.modificarTelefonoCliente(dni, telefono));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al modificar telefono: " + ex.getMessage());
        }
    }

    //METODO ELIMINAR CLIENTE
    public static void eliminarCLiente(Scanner teclado, GestorTaller gestor)
    {
        mostrarDnisClientes(gestor);
        try {
            System.out.print("DNI del cliente: ");
            System.out.println(gestor.eliminarCliente(teclado.nextLine()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al eliminar cliente: " + ex.getMessage());
        }
    }

    //METODO MOSTRAR CLIENTES
    public static void mostrarClientes(GestorTaller gestor) {
        try {
            System.out.println(gestor.imprimirListado(gestor.obtenerClientes()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar clientes: " + ex.getMessage());
        }
    }

    //METODO PARA REGISTRAR MECANICO
    public static void registrarMecanico(Scanner teclado, GestorTaller gestor)
    {
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
            System.out.println("Error al registrar mecanico: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El salario debe ser un valor numerico.");
        }
    }

    //METODO PARA BUSCAR MECANICO POR DNI
    public static void buscarMecanicoDNI(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisMecanicos(gestor);
            System.out.print("DNI del mecanico: ");
            System.out.println(gestor.buscarMecanicoPorDni(teclado.nextLine()));
        } catch (AccionInvalidaEx ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO PARA BUSCAR MECANICO POR NOMBRE
    public static void buscarMecanicoNombre(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisMecanicos(gestor);
            System.out.print("Nombre a buscar: ");
            System.out.println(gestor.imprimirListado(gestor.buscarMecanicosPorNombre(teclado.nextLine())));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO PARA ACTUALIZAR SALARIO
    public static void actualizarSalarioMecanico(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisMecanicos(gestor);
            System.out.print("DNI del mecanico: ");
            String dni = teclado.nextLine();
            System.out.print("Nuevo salario por hora: ");
            double salario = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            System.out.println(gestor.modificarSalarioDeMecanico(dni, salario));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al modificar salario: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El salario debe ser un valor numerico.");
        }
    }

    //METODO PARA ELIMINAR MECANICO
    public static void eliminarMecanico(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarDnisMecanicos(gestor);
            System.out.print("DNI del mecanico: ");
            System.out.println(gestor.eliminarMecanico(teclado.nextLine()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al eliminar mecanico: " + ex.getMessage());
        }
    }

    //METODO MOSTRAR MECANICOS
    public static void mostrarMecanicos(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.println(gestor.imprimirListado(gestor.obtenerMecanicos()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar mecanicos: " + ex.getMessage());
        }
    }

    //METODO REGISTRAR MODELO
    public static void registrarModelo(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Marca: ");
            String marca = teclado.nextLine();
            System.out.print("Linea: ");
            String linea = teclado.nextLine();
            System.out.print("Anio: ");
            int anio = Integer.parseInt(teclado.nextLine());
            System.out.println(gestor.ingresarModelo(marca, linea, anio));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al registrar modelo: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El anio debe ser numerico.");
        }
    }

    //METODO MOSTRAR MODELOS
    public static void mostrarModelos(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarIdsModelos(gestor);
            System.out.println(gestor.imprimirListado(gestor.obtenerModelos()));
        } catch (SQLException ex) {
            System.out.println("Error al listar modelos: " + ex.getMessage());
        }
    }

    //METODO REGISTRAR VEHICULOS
    public static void registrarVehiculo(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Patente: ");
            mostrarPatentesVehiculos(gestor);
            String patente = teclado.nextLine();
            System.out.print("Kilometraje: ");
            double kilometraje = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            mostrarIdsModelos(gestor);
            System.out.print("ID del modelo: ");
            int idModelo = Integer.parseInt(teclado.nextLine());
            mostrarDnisClientes(gestor);
            System.out.print("DNI del propietario: ");
            String dniPropietario = teclado.nextLine();

            System.out.println(gestor.ingresarVehiculo(
                    patente,
                    kilometraje,
                    gestor.buscarModeloPorId(idModelo),
                    gestor.buscarClientePorDni(dniPropietario)
            ));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al registrar vehiculo: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Kilometraje e ID deben ser numericos.");
        }
    }

    //METODO BUSCAR VEHICULO POR PATENTE
    public static void buscarVehiculoPatente(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarPatentesVehiculos(gestor);
            System.out.print("Patente: ");
            System.out.println(gestor.buscarVehiculoPorPatente(teclado.nextLine()));
        } catch (AccionInvalidaEx ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO MODIFICAR KILOMETRAJE
    public static void modificarKilometraje(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarPatentesVehiculos(gestor);
            System.out.print("Patente: ");
            String patente = teclado.nextLine();
            System.out.print("Nuevo kilometraje: ");
            double kilometraje = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            System.out.println(gestor.actualizarKilometraje(patente, kilometraje));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al actualizar kilometraje: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El kilometraje debe ser numerico.");
        }
    }

    //METODO ELIMINAR VEHICULO
    public static void eliminarVehiculo(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarPatentesVehiculos(gestor);
            System.out.print("Patente: ");
            System.out.println(gestor.eliminarVehiculo(teclado.nextLine()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al eliminar vehiculo: " + ex.getMessage());
        }
    }

    //METODO MOSTRAR VEHICULOS
    public static void mostrarVehiculos(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.println(gestor.imprimirListado(gestor.obtenerVehiculos()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar vehiculos: " + ex.getMessage());
        }
    }

    //METODO REGISTRAR REPUESTO
    public static void registrarRepuesto(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Nombre del repuesto: ");
            String nombre = teclado.nextLine();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            System.out.print("Stock inicial: ");
            int stock = Integer.parseInt(teclado.nextLine());
            System.out.println(gestor.ingresarRepuesto(nombre, precio, stock));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al registrar repuesto: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Precio y stock deben ser numericos.");
        }
    }

    //METODO OBTENER REPUESTO POR ID
    public static void obtenerRepuestoPorID(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarIdsRepuestos(gestor);
            System.out.print("ID del repuesto: ");
            System.out.println(gestor.obtenerRepuestoPorID(Integer.parseInt(teclado.nextLine())));
        } catch (AccionInvalidaEx ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El ID debe ser numerico.");
        }
    }

    //METODO OBTENER REPUESTO POR NOMBRE
    public static void obtenerRepuestoPorNombre(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Nombre a buscar: ");
            System.out.println(gestor.imprimirListado(gestor.buscarRepuestosPorNombre(teclado.nextLine())));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error en la busqueda: " + ex.getMessage());
        }
    }

    //METODO ACTUALIZAR STOCK
    public static void actualizarStock(Scanner teclado, GestorTaller gestor)
    {
        try {
            mostrarIdsRepuestos(gestor);
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
    }

    //METODO MOSTRAR REPUESTOS
    public static void mostrarRepuestos(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.println(gestor.imprimirListado(gestor.obtenerRepuestos()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar repuestos: " + ex.getMessage());
        }
    }

    //METODO ELIMINAR REPUESTO
    private static void eliminarRepuesto(Scanner teclado, GestorTaller gestor) {
        mostrarIdsRepuestos(gestor);

        try {
            System.out.print("ID del repuesto a eliminar: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println(gestor.eliminarRepuesto(id));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al eliminar repuesto: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El ID debe ser numerico.");
        }
    }

    //METODO REGISTRAR PASO 1
    public static void paso_1(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Patente del vehiculo: ");
            mostrarPatentesVehiculos(gestor);
            String patente = teclado.nextLine();
            System.out.print("DNI del mecanico asignado: ");
            mostrarDnisMecanicos(gestor);
            String dniMecanico = teclado.nextLine();
            System.out.print("Motivo de ingreso reportado por el cliente: ");
            String motivo = teclado.nextLine();
            System.out.println(gestor.ingresarAutoATaller(patente, dniMecanico, motivo));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al ingresar auto al taller: " + ex.getMessage());
        }
    }

    //METODO REGISTRAR PASO 2
    public static void paso_2(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Numero de orden: ");
            mostrarIdOrden(gestor);
            int idOrden = Integer.parseInt(teclado.nextLine());
            System.out.print("Diagnostico tecnico del taller: ");
            String diagnostico = teclado.nextLine();
            System.out.println(gestor.registrarDiagnosticoOrden(idOrden, diagnostico));
        } catch (AccionInvalidaEx | OrdenInvalidaException | SQLException ex) {
            System.out.println("Error al cargar diagnostico: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El numero de orden debe ser numerico.");
        }
    }

    //METODO REGISTRAR PASO 3
    public static void paso_3(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Numero de orden: ");
            mostrarIdOrden(gestor);
            int idOrden = Integer.parseInt(teclado.nextLine());
            System.out.print("ID del repuesto: ");
            int idRepuesto = Integer.parseInt(teclado.nextLine());
            System.out.print("Cantidad utilizada: ");
            int cantidad = Integer.parseInt(teclado.nextLine());
            System.out.println(gestor.agregarRepuestoAOrden(idOrden, idRepuesto, cantidad));
        } catch (AccionInvalidaEx | OrdenInvalidaException | StockInsuficienteException | SQLException ex) {
            System.out.println("Error al agregar repuesto: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Orden, ID y cantidad deben ser numericos.");
        }
    }

    //METODO REGISTRAR PASO 4
    public static void paso_4(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Numero de orden a finalizar: ");
            int idOrden = Integer.parseInt(teclado.nextLine());
            System.out.print("Horas trabajadas: ");
            double horas = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            System.out.println(gestor.finalizarYFacturarOrden(idOrden, horas));
        } catch (AccionInvalidaEx | OrdenInvalidaException | SQLException ex) {
            System.out.println("Error al finalizar orden: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: Orden y horas deben ser numericos.");
        }
    }

    //METODO REGISTRAR REPARACION RAPIDA
    public static void reparacionRapida(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Patente del vehiculo: ");
            mostrarPatentesVehiculos(gestor);
            String patente = teclado.nextLine();
            System.out.print("DNI del mecanico: ");
            mostrarDnisMecanicos(gestor);
            String dniMecanico = teclado.nextLine();
            System.out.print("ID del repuesto: ");
            mostrarIdsRepuestos(gestor);
            int idRepuesto = Integer.parseInt(teclado.nextLine());
            System.out.print("Cantidad utilizada: ");
            int cantidad = Integer.parseInt(teclado.nextLine());
            System.out.print("Horas trabajadas: ");
            double horas = Double.parseDouble(teclado.nextLine().replace(',', '.'));
            System.out.print("Descripcion del problema: ");
            String descripcion = teclado.nextLine();

            System.out.println(gestor.registrarYFacturarReparacion(
                    0,
                    gestor.buscarVehiculoPorPatente(patente),
                    gestor.buscarMecanicoPorDni(dniMecanico),
                    idRepuesto,
                    cantidad,
                    horas,
                    descripcion
            ));
        } catch (AccionInvalidaEx | SQLException | OrdenInvalidaException | StockInsuficienteException ex) {
            System.out.println("Error en reparacion rapida: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: IDs, cantidades y horas deben ser numericos.");
        }
    }

    //METODO BUSCAR ORDEN POR ID
    public static void buscarOrdenPorID(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.print("Numero de orden: ");
            mostrarIdOrden(gestor);
            OrdenDeTrabajo orden = gestor.buscarOrdenPorId(Integer.parseInt(teclado.nextLine()));
            System.out.println(orden);
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al buscar orden: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El numero de orden debe ser numerico.");
        }

    }

    //MOSTRAR ORDENES ACTIVAS
    public static void mostrarOrdenesActivas(Scanner teclado, GestorTaller gestor)
    {
        try {
            System.out.println(gestor.imprimirListado(gestor.obtenerOrdenesActivas()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al obtener ordenes activas: " + ex.getMessage());
        }
    }

    //MOSTRAR TODAS LAS ORDENES
    private static void mostrarTodasLasOrdenes(GestorTaller gestor) {
        try {
            ArrayList<OrdenDeTrabajo> ordenes = gestor.obtenerTodasLasOrdenes();

            if (ordenes.isEmpty()) {
                System.out.println("No hay ordenes registradas.");
                return;
            }

            System.out.println(gestor.imprimirListado(ordenes));
            System.out.println("Total de ordenes: " + ordenes.size());
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar ordenes: " + ex.getMessage());
        }
    }

    private static void mostrarReporteGeneral(GestorTaller gestor) {
        try {
            System.out.println("\n=== CLIENTES ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerClientes()));
            System.out.println("=== MECANICOS ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerMecanicos()));
            System.out.println("=== MODELOS ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerModelos()));
            System.out.println("=== VEHICULOS ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerVehiculos()));
            System.out.println("=== REPUESTOS ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerRepuestos()));
            System.out.println("=== ORDENES ACTIVAS ===");
            System.out.println(gestor.imprimirListado(gestor.obtenerOrdenesActivas()));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al mostrar reporte general: " + ex.getMessage());
        }
    }

    private static void mostrarIdOrden(GestorTaller gestor){
        try {
            ArrayList<OrdenDeTrabajo> ordenes = gestor.obtenerTodasLasOrdenes();

            if(ordenes.isEmpty()) {
                System.out.println("No hay ordenes registradas");
            }

            System.out.println("\n--- Ordenes ID ---");
            for(OrdenDeTrabajo o: ordenes){
                System.out.println(o.getId());
            }
            System.out.println("\n");
        }catch (AccionInvalidaEx | SQLException ex){
            System.out.println("Error al listar las ordenes: " + ex.getMessage());
        }


    }


    private static void mostrarDnisClientes(GestorTaller gestor) {
        try {
            ArrayList<Cliente> clientes = gestor.obtenerClientes();

            if (clientes.isEmpty()) {
                System.out.println("No hay clientes registrados.");
                return;
            }

            System.out.println("\n--- DNI de clientes ---");
            for (Cliente c : clientes) {
                System.out.println(c.getDni() + " " + c.getNombre() + " " + c.getApellido());
            }
            System.out.println("\n");
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar clientes: " + ex.getMessage());
        }
    }

    private static void mostrarDnisMecanicos(GestorTaller gestor) {
        try {
            ArrayList<Mecanico> mecanicos = gestor.obtenerMecanicos();

            if (mecanicos.isEmpty()) {
                System.out.println("No hay mecanicos registrados.");
                return;
            }

            System.out.println("\n--- DNI de mecanicos ---");
            for (Mecanico c : mecanicos) {
                System.out.println(c.getDni() + " " + c.getNombre() + " " + c.getNombre());
            }
            System.out.println("\n");
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar mecanicos: " + ex.getMessage());
        }
    }

    private static void mostrarIdsModelos(GestorTaller gestor) {
        try {
            ArrayList<Modelo> modelos = gestor.obtenerModelos();

            if (modelos.isEmpty()) {
                System.out.println("No hay modelos registrados.");
                return;
            }

            System.out.println("\n--- ID de modelos ---");
            for (Modelo m : modelos) {
                System.out.println(m.getId() + " " + m.getMarca() + " " + m.getLinea() + " " + m.getAnio());
            }
        } catch (SQLException ex) {
            System.out.println("Error al listar modelos: " + ex.getMessage());
        }
    }

    private static void mostrarPatentesVehiculos(GestorTaller gestor) {
        try {
            ArrayList<Vehiculo> vehiculos = gestor.obtenerVehiculos();

            if (vehiculos.isEmpty()) {
                System.out.println("No hay vehiculos registrados.");
                return;
            }

            System.out.println("\n--- Patentes de vehiculos ---");
            for (Vehiculo v : vehiculos) {
                System.out.println(v.getPatente());
            }
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar vehiculos: " + ex.getMessage());
        }
    }

    private static void mostrarIdsRepuestos(GestorTaller gestor) {
        try {
            ArrayList<Repuesto> repuestos = gestor.obtenerRepuestos();

            if (repuestos.isEmpty()) {
                System.out.println("No hay repuestos registrados.");
                return;
            }

            System.out.println("\n--- ID de repuestos ---");
            for (Repuesto r : repuestos) {
                System.out.println(r.getIdRepuesto() + " " + r.getNombre() + " " + r.getStock());
            }
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar repuestos: " + ex.getMessage());
        }
    }




    private static void eliminarModelo(Scanner teclado, GestorTaller gestor) {
        mostrarIdsModelos(gestor);

        try {
            System.out.print("ID del modelo a eliminar: ");
            int id = Integer.parseInt(teclado.nextLine());

            System.out.println(gestor.eliminarModelo(id));
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al eliminar modelo: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El ID debe ser numerico.");
        }
    }

    //METODO ELIMINAR ORDEN FINALIZADA
    private static void eliminarOrdenFinalizada(Scanner teclado, GestorTaller gestor) {
        mostrarIdsOrdenesFinalizadas(gestor);

        try {
            System.out.print("Numero de orden finalizada a eliminar: ");
            int idOrden = Integer.parseInt(teclado.nextLine());
            System.out.println(gestor.eliminarOrdenFinalizada(idOrden));
        } catch (AccionInvalidaEx | OrdenInvalidaException | SQLException ex) {
            System.out.println("Error al eliminar orden: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Error: El numero de orden debe ser numerico.");
        }
    }

    private static void mostrarIdsOrdenesFinalizadas(GestorTaller gestor) {
        try {
            ArrayList<OrdenDeTrabajo> finalizadas = gestor.obtenerOrdenesFinalizadas();

            if (finalizadas.isEmpty()) {
                System.out.println("No hay ordenes finalizadas registradas.");
                return;
            }

            System.out.println("\n--- Ordenes FINALIZADAS (ID) ---");
            for (OrdenDeTrabajo o : finalizadas) {
                System.out.println(o.getId());
            }
            System.out.println();
        } catch (AccionInvalidaEx | SQLException ex) {
            System.out.println("Error al listar ordenes finalizadas: " + ex.getMessage());
        }
    }




}