/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sadra
 */

import java.time.LocalDate;

public class LaborAgricola {
    private int codigoLabor; 
    private Parcela parcela; 
    private Cultivo cultivo; 
    private Responsable responsable; 
    private LocalDate fechaLabor;
    private String tipoLabor;
    private String descripcion;
    private double costo;

    public LaborAgricola(int codigoLabor, Parcela parcela, Cultivo cultivo, Responsable responsable, LocalDate fechaLabor, String tipoLabor, String descripcion, double costo) {
        this.codigoLabor = codigoLabor;
        this.parcela = parcela;
        this.cultivo = cultivo;
        this.responsable = responsable;
        this.fechaLabor = fechaLabor;
        this.tipoLabor = tipoLabor;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public LaborAgricola(Parcela parcela, Cultivo cultivo, Responsable responsable, LocalDate fechaLabor, String tipoLabor, String descripcion, double costo) {
        this.parcela = parcela;
        this.cultivo = cultivo;
        this.responsable = responsable;
        this.fechaLabor = fechaLabor;
        this.tipoLabor = tipoLabor;
        this.descripcion = descripcion;
        this.costo = costo;
    }

    public LaborAgricola() {}

    public int getCodigoLabor() { return codigoLabor; }
    public void setCodigoLabor(int codigoLabor) { this.codigoLabor = codigoLabor; }

    public Parcela getParcela() { return parcela; }
    public void setParcela(Parcela parcela) { this.parcela = parcela; }

    public Cultivo getCultivo() { return cultivo; }
    public void setCultivo(Cultivo cultivo) { this.cultivo = cultivo; }

    public Responsable getResponsable() { return responsable; }
    public void setResponsable(Responsable responsable) { this.responsable = responsable; }

    public LocalDate getFechaLabor() { return fechaLabor; }
    public void setFechaLabor(LocalDate fechaLabor) { this.fechaLabor = fechaLabor; }

    public String getTipoLabor() { return tipoLabor; }
    public void setTipoLabor(String tipoLabor) { this.tipoLabor = tipoLabor; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    @Override
    public String toString() {
        return "Labor #" + codigoLabor + " - " + tipoLabor + " en " + parcela.getNombre();
    }
}

