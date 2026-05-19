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

public class CultivoAnual extends Cultivo {
    private int duracionCiclo;

    public CultivoAnual(String codigo, String nombre, String variedad, LocalDate fechaSiembra, int duracionCiclo) {
        super(codigo, nombre, variedad, fechaSiembra);
        this.duracionCiclo = duracionCiclo;
    }

    public CultivoAnual() {}

    public int getDuracionCiclo() { return duracionCiclo; }
    
    public void setDuracionCiclo(int duracionCiclo) { this.duracionCiclo = duracionCiclo; }
    
    @Override
    public String obtenerDescripcion() {
        return "Anual, " + duracionCiclo + " días.";
    }
}

