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
import model.Parcela;

public class ParcelaDAO {
    
    public boolean insertar(Parcela parcela) {
        String sql = "INSERT INTO parcelas (codigo, nombre, ubicacion, area, tipo_suelo, estado) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, parcela.getCodigo());
            ps.setString(2, parcela.getNombre());
            ps.setString(3, parcela.getUbicacion());
            ps.setDouble(4, parcela.getArea());
            ps.setString(5, parcela.getTipoSuelo());
            ps.setString(6, parcela.getEstado());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar parcela: " + e.getMessage());
            
            e.printStackTrace();
            
            return false;
        }
    }

    public List<Parcela> listar() {
        List<Parcela> lista = new ArrayList<>();
        String sql = "SELECT * FROM parcelas";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Parcela p = new Parcela(
                    rs.getString("codigo"),
                    rs.getString("nombre"),
                    rs.getString("ubicacion"),
                    rs.getDouble("area"),
                    rs.getString("tipo_suelo"),
                    rs.getString("estado")
                );
                lista.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar parcelas: " + e.getMessage());
        }
        return lista;
    }
}

