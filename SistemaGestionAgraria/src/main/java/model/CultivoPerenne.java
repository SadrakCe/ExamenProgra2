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

public class CultivoPerenne extends Cultivo {
    private int aniosProduccion;

    public CultivoPerenne(String codigo, String nombre, String variedad, LocalDate fechaSiembra, int aniosProduccion) {
        super(codigo, nombre, variedad, fechaSiembra);
        this.aniosProduccion = aniosProduccion;
    }

    public CultivoPerenne() {}

    public int getAniosProduccion() { return aniosProduccion; }
    
    public void setAniosProduccion(int aniosProduccion) { this.aniosProduccion = aniosProduccion; }
    
    @Override
    public String obtenerDescripcion() {
        return "Perenne, " + aniosProduccion + " años.";
    }
}

    
