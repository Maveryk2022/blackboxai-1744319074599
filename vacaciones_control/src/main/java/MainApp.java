import model.Empleado;
import model.PeriodoVacacional;
import model.SolicitudVacaciones;
import service.VacacionesService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainApp {
    private static final VacacionesService service = new VacacionesService();
    private static final Scanner scanner = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\n=== SISTEMA DE CONTROL DE VACACIONES ===");
            System.out.println("1. Registrar nuevo empleado");
            System.out.println("2. Registrar periodo vacacional");
            System.out.println("3. Solicitar vacaciones");
            System.out.println("4. Aprobar/Rechazar solicitudes");
            System.out.println("5. Consultar saldo de vacaciones");
            System.out.println("6. Listar empleados");
            System.out.println("7. Listar periodos vacacionales");
            System.out.println("8. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    registrarEmpleado();
                    break;
                case 2:
                    registrarPeriodo();
                    break;
                case 3:
                    solicitarVacaciones();
                    break;
                case 4:
                    gestionarSolicitudes();
                    break;
                case 5:
                    consultarSaldo();
                    break;
                case 6:
                    listarEmpleados();
                    break;
                case 7:
                    listarPeriodos();
                    break;
                case 8:
                    running = false;
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        }
        System.out.println("Sistema cerrado. ¡Hasta pronto!");
    }

    private static void registrarEmpleado() {
        System.out.println("\n--- REGISTRAR NUEVO EMPLEADO ---");
        System.out.print("Número de empleado: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha de ingreso (dd-MM-yyyy): ");
        Date fechaIngreso = parseDate(scanner.nextLine());
        
        System.out.println("Departamentos disponibles:");
        List<String> departamentos = service.obtenerDepartamentos();
        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println((i+1) + ". " + departamentos.get(i));
        }
        System.out.print("Seleccione departamento (número): ");
        int deptoIndex = scanner.nextInt() - 1;
        scanner.nextLine();
        
        Empleado empleado = new Empleado(numero, nombre, fechaIngreso, departamentos.get(deptoIndex));
        if (service.registrarEmpleado(empleado)) {
            System.out.println("Empleado registrado exitosamente");
        } else {
            System.out.println("Error al registrar empleado");
        }
    }

    private static void registrarPeriodo() {
        System.out.println("\n--- REGISTRAR PERIODO VACACIONAL ---");
        System.out.print("Nombre del periodo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Fecha inicio (dd-MM-yyyy): ");
        Date inicio = parseDate(scanner.nextLine());
        
        System.out.print("Fecha fin (dd-MM-yyyy): ");
        Date fin = parseDate(scanner.nextLine());
        
        PeriodoVacacional periodo = new PeriodoVacacional(nombre, inicio, fin);
        if (service.crearPeriodoVacacional(periodo)) {
            System.out.println("Periodo registrado exitosamente");
        } else {
            System.out.println("Error al registrar periodo");
        }
    }

    private static void solicitarVacaciones() {
        System.out.println("\n--- SOLICITAR VACACIONES ---");
        System.out.print("Número de empleado: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Fecha inicio vacaciones (dd-MM-yyyy): ");
        Date inicio = parseDate(scanner.nextLine());
        
        System.out.print("Fecha fin vacaciones (dd-MM-yyyy): ");
        Date fin = parseDate(scanner.nextLine());
        
        System.out.print("Días solicitados: ");
        int dias = scanner.nextInt();
        scanner.nextLine();
        
        SolicitudVacaciones solicitud = new SolicitudVacaciones(
            numero, new Date(), inicio, fin, dias
        );
        
        if (service.solicitarVacaciones(solicitud)) {
            System.out.println("Solicitud registrada exitosamente");
        } else {
            System.out.println("No tiene suficientes días disponibles o error al registrar");
        }
    }

    private static void gestionarSolicitudes() {
        System.out.println("\n--- GESTIONAR SOLICITUDES ---");
        List<SolicitudVacaciones> solicitudes = service.obtenerTodasSolicitudes();
        
        if (solicitudes.isEmpty()) {
            System.out.println("No hay solicitudes pendientes");
            return;
        }
        
        for (SolicitudVacaciones s : solicitudes) {
            System.out.println(s);
        }
        
        System.out.print("ID de solicitud a gestionar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Aprobar (A) o Rechazar (R): ");
        String accion = scanner.nextLine().toUpperCase();
        
        boolean resultado = false;
        if (accion.equals("A")) {
            resultado = service.aprobarSolicitud(id);
        } else if (accion.equals("R")) {
            resultado = service.rechazarSolicitud(id);
        }
        
        if (resultado) {
            System.out.println("Solicitud actualizada");
        } else {
            System.out.println("Error al actualizar solicitud");
        }
    }

    private static void consultarSaldo() {
        System.out.println("\n--- CONSULTAR SALDO DE VACACIONES ---");
        System.out.print("Número de empleado: ");
        int numero = scanner.nextInt();
        scanner.nextLine();
        
        int dias = service.calcularDiasDisponibles(numero);
        System.out.println("Días disponibles: " + dias);
    }

    private static void listarEmpleados() {
        System.out.println("\n--- LISTA DE EMPLEADOS ---");
        List<Empleado> empleados = service.listarEmpleados();
        for (Empleado e : empleados) {
            System.out.println(e);
        }
    }

    private static void listarPeriodos() {
        System.out.println("\n--- LISTA DE PERIODOS VACACIONALES ---");
        List<PeriodoVacacional> periodos = service.listarPeriodos();
        for (PeriodoVacacional p : periodos) {
            System.out.println(p);
        }
    }

    private static Date parseDate(String dateStr) {
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            System.out.println("Formato de fecha inválido. Use dd-MM-yyyy");
            return new Date(); // Fecha actual por defecto
        }
    }
}
