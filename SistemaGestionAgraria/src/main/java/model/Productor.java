/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author sadra
 */
public class Productor extends Responsable {
    private String nombreFinca;

    public Productor(String identificacion, String nombre, String correo, String telefono, String nombreFinca) {
        super(identificacion, nombre, correo, telefono);
        this.nombreFinca = nombreFinca;
    }

    public Productor() {}

    public String getNombreFinca() { return nombreFinca; }
    public void setNombreFinca(String nombreFinca) { this.nombreFinca = nombreFinca; }

    @Override
    public String obtenerPerfil() {
        return "Productor, Finca: " + nombreFinca;
    }
}

