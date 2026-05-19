/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sadra
 */
public class TecnicoAgricola extends Responsable {
    
    private String especialidad;

    public TecnicoAgricola(String identificacion, String nombre, String correo, String telefono, String especialidad) {
        super(identificacion, nombre, correo, telefono);
        this.especialidad = especialidad;
    }

    public TecnicoAgricola() {}

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    @Override
    public String obtenerPerfil() {
        return "Técnico -Especialidad: " + especialidad;
    }
}

