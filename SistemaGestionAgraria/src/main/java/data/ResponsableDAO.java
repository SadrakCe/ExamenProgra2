/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author sadra
 */

import config.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Responsable;
import model.Productor;
import model.TecnicoAgricola;

public class ResponsableDAO {
    public boolean insertar(Responsable responsable) {
        String sql = "INSERT INTO responsables (identificacion, nombre, correo, telefono, tipo_responsable, nombre_finca, especialidad) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, responsable.getIdentificacion());
            ps.setString(2, responsable.getNombre());
            ps.setString(3, responsable.getCorreo());
            ps.setString(4, responsable.getTelefono());
            
            if (responsable instanceof Productor) {
                ps.setString(5, "PRODUCTOR");
                ps.setString(6, ((Productor) responsable).getNombreFinca());
                ps.setNull(7, java.sql.Types.VARCHAR); // especialidad queda null
            } else if (responsable instanceof TecnicoAgricola) {
                ps.setString(5, "TECNICO");
                ps.setNull(6, java.sql.Types.VARCHAR); // nombre_finca queda null
                ps.setString(7, ((TecnicoAgricola) responsable).getEspecialidad());
            }
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar responsable: " + e.getMessage());
            return false;
        }
    }

    public List<Responsable> listar() {
        List<Responsable> lista = new ArrayList<>();
        String sql = "SELECT * FROM responsables";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String tipo = rs.getString("tipo_responsable");
                String id = rs.getString("identificacion");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                String telefono = rs.getString("telefono");
                
                if ("PRODUCTOR".equals(tipo)) {
                    String finca = rs.getString("nombre_finca");
                    lista.add(new Productor(id, nombre, correo, telefono, finca));
                } else {
                    String especialidad = rs.getString("especialidad");
                    lista.add(new TecnicoAgricola(id, nombre, correo, telefono, especialidad));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar responsables: " + e.getMessage());
        }
        return lista;
    }
}

