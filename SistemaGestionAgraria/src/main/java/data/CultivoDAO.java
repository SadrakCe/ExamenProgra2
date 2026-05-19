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
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import model.Cultivo;
import model.CultivoAnual;
import model.CultivoPerenne;

public class CultivoDAO {
    public boolean insertar(Cultivo cultivo) {
        String sql = "INSERT INTO cultivos (codigo, nombre, variedad, fecha_siembra, tipo_cultivo, duracion_ciclo, anios_produccion) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, cultivo.getCodigo());
            ps.setString(2, cultivo.getNombre());
            ps.setString(3, cultivo.getVariedad());
            ps.setDate(4, Date.valueOf(cultivo.getFechaSiembra())); // Convierte LocalDate a java.sql.Date
            
            if (cultivo instanceof CultivoAnual) {
                ps.setString(5, "ANUAL");
                ps.setInt(6, ((CultivoAnual) cultivo).getDuracionCiclo());
                ps.setNull(7, java.sql.Types.INTEGER); // anios_produccion queda null
            } else if (cultivo instanceof CultivoPerenne) {
                ps.setString(5, "PERENNE");
                ps.setNull(6, java.sql.Types.INTEGER); // duracion_ciclo queda null
                ps.setInt(7, ((CultivoPerenne) cultivo).getAniosProduccion());
            }
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar cultivo: " + e.getMessage());
            return false;
        }
    }

    public List<Cultivo> listar() {
        List<Cultivo> lista = new ArrayList<>();
        String sql = "SELECT * FROM cultivos";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                String tipo = rs.getString("tipo_cultivo");
                String codigo = rs.getString("codigo");
                String nombre = rs.getString("nombre");
                String variedad = rs.getString("variedad");
                LocalDate fecha = rs.getDate("fecha_siembra").toLocalDate();
                
                if ("ANUAL".equals(tipo)) {
                    int ciclo = rs.getInt("duracion_ciclo");
                    lista.add(new CultivoAnual(codigo, nombre, variedad, fecha, ciclo));
                } else {
                    int anios = rs.getInt("anios_produccion");
                    lista.add(new CultivoPerenne(codigo, nombre, variedad, fecha, anios));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cultivos: " + e.getMessage());
        }
        return lista;
    }
}
