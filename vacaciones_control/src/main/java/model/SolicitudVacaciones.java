package model;

import java.util.Date;

public class SolicitudVacaciones {
    private int idSolicitud;
    private int numeroEmpleado;
    private Date fechaSolicitud;
    private Date fechaInicio;
    private Date fechaFin;
    private int diasSolicitados;
    private String estado;

    public SolicitudVacaciones() {
        this.estado = "pendiente"; // Valor por defecto
    }

    public SolicitudVacaciones(int numeroEmpleado, Date fechaSolicitud, 
                             Date fechaInicio, Date fechaFin, int diasSolicitados) {
        this();
        this.numeroEmpleado = numeroEmpleado;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasSolicitados = diasSolicitados;
    }

    // Getters y Setters
    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(int numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDiasSolicitados() {
        return diasSolicitados;
    }

    public void setDiasSolicitados(int diasSolicitados) {
        this.diasSolicitados = diasSolicitados;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Solicitud #" + idSolicitud + " - Empleado: " + numeroEmpleado + 
               " (" + estado + ") - " + diasSolicitados + " días";
    }
}
