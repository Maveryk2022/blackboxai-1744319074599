package service;

import dao.EmpleadoDAO;
import dao.PeriodoDAO;
import dao.SolicitudDAO;
import model.Empleado;
import model.PeriodoVacacional;
import model.SolicitudVacaciones;
import java.util.Date;
import java.util.List;

public class VacacionesService {
    private final EmpleadoDAO empleadoDAO;
    private final PeriodoDAO periodoDAO;
    private final SolicitudDAO solicitudDAO;

    public VacacionesService() {
        this.empleadoDAO = new EmpleadoDAO();
        this.periodoDAO = new PeriodoDAO();
        this.solicitudDAO = new SolicitudDAO();
    }

    // Métodos para empleados
    public boolean registrarEmpleado(Empleado empleado) {
        return empleadoDAO.insertarEmpleado(empleado);
    }

    public Empleado buscarEmpleado(int numeroEmpleado) {
        return empleadoDAO.buscarPorNumero(numeroEmpleado);
    }

    public List<Empleado> listarEmpleados() {
        return empleadoDAO.listarTodos();
    }

    // Métodos para periodos vacacionales
    public boolean crearPeriodoVacacional(PeriodoVacacional periodo) {
        return periodoDAO.insertarPeriodo(periodo);
    }

    public List<PeriodoVacacional> listarPeriodos() {
        return periodoDAO.listarTodos();
    }

    // Métodos para solicitudes de vacaciones
    public boolean solicitarVacaciones(SolicitudVacaciones solicitud) {
        int diasDisponibles = solicitudDAO.calcularDiasDisponibles(solicitud.getNumeroEmpleado());
        if (diasDisponibles >= solicitud.getDiasSolicitados()) {
            return solicitudDAO.crearSolicitud(solicitud);
        }
        return false;
    }

    public boolean aprobarSolicitud(int idSolicitud) {
        return solicitudDAO.cambiarEstadoSolicitud(idSolicitud, "aprobada");
    }

    public boolean rechazarSolicitud(int idSolicitud) {
        return solicitudDAO.cambiarEstadoSolicitud(idSolicitud, "rechazada");
    }

    public List<SolicitudVacaciones> obtenerSolicitudesEmpleado(int numeroEmpleado) {
        return solicitudDAO.buscarPorEmpleado(numeroEmpleado);
    }

    public List<SolicitudVacaciones> obtenerTodasSolicitudes() {
        return solicitudDAO.listarTodas();
    }

    // Métodos para cálculo de vacaciones
    public int calcularDiasDisponibles(int numeroEmpleado) {
        return solicitudDAO.calcularDiasDisponibles(numeroEmpleado);
    }

    // Métodos para departamentos
    public List<String> obtenerDepartamentos() {
        return List.of(
            "DIRECCIÓN GENERAL",
            "PLANEACIÓN Y PROYECTOS ESTRATÉGICOS",
            "PROGRAMAS SOCIALES",
            "DESARROLLO COMUNITARIO",
            "ASISTENCIA SOCIAL",
            "ADULTO MAYOR",
            "PROGRAMAS ALIMENTARIOS",
            "UNIDAD BÁSICA DE REHABILITACIÓN",
            "ADMINISTRACIÓN Y FINANZAS",
            "CONTRALORIA",
            "PROCURADURÍA DE PROTECCIÓN A NIÑAS, NIÑOS Y ADOLESCENTES",
            "UNIDAD DE PROTECCIÓN INTEGRAL A GRUPOS VULNERABLES",
            "ACCESIBILIDAD E INCLUSIÓN DE PERSONAS CON DISCAPACIDAD"
        );
    }
}
