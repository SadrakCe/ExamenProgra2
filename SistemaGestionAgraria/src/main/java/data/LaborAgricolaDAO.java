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
import model.*;

public class LaborAgricolaDAO {
    public boolean insertar(LaborAgricola labor) {
        String sql = "INSERT INTO labores_agricolas (codigo_parcela, codigo_cultivo, id_responsable, fecha_labor, tipo_labor, descripcion, costo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, labor.getParcela().getCodigo());
            ps.setString(2, labor.getCultivo().getCodigo());
            ps.setString(3, labor.getResponsable().getIdentificacion());
            ps.setDate(4, Date.valueOf(labor.getFechaLabor()));
            ps.setString(5, labor.getTipoLabor());
            ps.setString(6, labor.getDescripcion());
            ps.setDouble(7, labor.getCosto());
            
            return ps.executeUpdate() > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al insertar labor agrícola: " + e.getMessage());
            return false;
        }
    }

    public List<LaborAgricola> listar() {
        List<LaborAgricola> lista = new ArrayList<>();
        
        String sql = "SELECT l.*, "
                   + "p.nombre AS p_nom, p.ubicacion AS p_ubi, p.area AS p_are, p.tipo_suelo AS p_sue, p.estado AS p_est, "
                   + "c.nombre AS c_nom, c.variedad AS c_var, c.fecha_siembra AS c_fec, c.tipo_cultivo AS c_tip, c.duracion_ciclo AS c_cic, c.anios_produccion AS c_ann, "
                   + "r.nombre AS r_nom, r.correo AS r_cor, r.telefono AS r_tel, r.tipo_responsable AS r_tip, r.nombre_finca AS r_fin, r.especialidad AS r_esp "
                   + "FROM labores_agricolas l "
                   + "JOIN parcelas p ON l.codigo_parcela = p.codigo "
                   + "JOIN cultivos c ON l.codigo_cultivo = c.codigo "
                   + "JOIN responsables r ON l.id_responsable = r.identificacion";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                
                Parcela parcela = new Parcela(
                    rs.getString("codigo_parcela"),
                    rs.getString("p_nom"),
                    rs.getString("p_ubi"),
                    rs.getDouble("p_are"),
                    rs.getString("p_sue"),
                    rs.getString("p_est")
                );
                
                
                Cultivo cultivo;
                String tipoC = rs.getString("c_tip");
                String codC = rs.getString("codigo_cultivo");
                String nomC = rs.getString("c_nom");
                String varC = rs.getString("c_var");
                LocalDate fecC = rs.getDate("c_fec").toLocalDate();
                if ("ANUAL".equals(tipoC)) {
                    cultivo = new CultivoAnual(codC, nomC, varC, fecC, rs.getInt("c_cic"));
                } else {
                    cultivo = new CultivoPerenne(codC, nomC, varC, fecC, rs.getInt("c_ann"));
                }
                
                
                Responsable responsable;
                String tipoR = rs.getString("r_tip");
                String idR = rs.getString("id_responsable");
                String nomR = rs.getString("r_nom");
                String corR = rs.getString("r_cor");
                String telR = rs.getString("r_tel");
                if ("PRODUCTOR".equals(tipoR)) {
                    responsable = new Productor(idR, nomR, corR, telR, rs.getString("r_fin"));
                } else {
                    responsable = new TecnicoAgricola(idR, nomR, corR, telR, rs.getString("r_esp"));
                }
                
                
                LaborAgricola labor = new LaborAgricola(
                    rs.getInt("codigo_labor"),
                    parcela,
                    cultivo,
                    responsable,
                    rs.getDate("fecha_labor").toLocalDate(),
                    rs.getString("tipo_labor"),
                    rs.getString("descripcion"),
                    rs.getDouble("costo")
                );
                
                lista.add(labor);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar labores agrícolas: " + e.getMessage());
        }
        return lista;
    }
}

