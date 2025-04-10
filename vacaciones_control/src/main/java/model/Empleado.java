package model;

import java.util.Date;

public class Empleado {
    private int numeroEmpleado;
    private String nombreCompleto;
    private Date fechaIngreso;
    private String departamento;
    private int diasAdicionales;

    public Empleado() {}

    public Empleado(int numeroEmpleado, String nombreCompleto, Date fechaIngreso, String departamento) {
        this.numeroEmpleado = numeroEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
    }

    // Getters y Setters
    public int getNumeroEmpleado() {
        return numeroEmpleado;
    }

    public void setNumeroEmpleado(int numeroEmpleado) {
        this.numeroEmpleado = numeroEmpleado;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public int getDiasAdicionales() {
        return diasAdicionales;
    }

    public void setDiasAdicionales(int diasAdicionales) {
        this.diasAdicionales = diasAdicionales;
    }

    @Override
    public String toString() {
        return numeroEmpleado + " - " + nombreCompleto + " (" + departamento + ")";
    }
}
