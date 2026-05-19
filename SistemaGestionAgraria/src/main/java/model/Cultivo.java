/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;

/**
 *
 * @author sadra
 */
public abstract class Cultivo {
    
    protected String codigo;
    protected String nombre;
    protected String variedad;
    protected LocalDate fechaSiembra;

    public Cultivo(String codigo, String nombre, String variedad, LocalDate fechaSiembra) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.variedad = variedad;
        this.fechaSiembra = fechaSiembra;
    }

    public Cultivo() {}
    
    public abstract String obtenerDescripcion();

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getVariedad() { return variedad; }
    public void setVariedad(String variedad) { this.variedad = variedad; }
    public LocalDate getFechaSiembra() { return fechaSiembra; }
    public void setFechaSiembra(LocalDate fechaSiembra) { this.fechaSiembra = fechaSiembra; }

    @Override
    public String toString() {
        return nombre + " - " + variedad + " (" + codigo + ")";
    }
}
