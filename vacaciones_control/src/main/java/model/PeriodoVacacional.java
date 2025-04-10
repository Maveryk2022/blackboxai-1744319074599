package model;

import java.util.Date;

public class PeriodoVacacional {
    private int idPeriodo;
    private String nombrePeriodo;
    private Date fechaInicio;
    private Date fechaFin;
    private int diasBase;

    public PeriodoVacacional() {
        this.diasBase = 10; // Valor por defecto
    }

    public PeriodoVacacional(String nombrePeriodo, Date fechaInicio, Date fechaFin) {
        this();
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    // Getters y Setters
    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
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

    public int getDiasBase() {
        return diasBase;
    }

    public void setDiasBase(int diasBase) {
        this.diasBase = diasBase;
    }

    @Override
    public String toString() {
        return nombrePeriodo + " (" + fechaInicio + " - " + fechaFin + ") - " + diasBase + " días";
    }
}
