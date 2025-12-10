package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Pago {
    public static void realizarPago(int usuarioId, double total) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "INSERT INTO Constancia(usuario_id, total, fecha) VALUES(?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, usuarioId);
            ps.setBigDecimal(2, new java.math.BigDecimal(total));
            ps.setObject(3, LocalDateTime.now());
            ps.executeUpdate();
            System.out.println("Pago realizado. Total: " + total);
        } catch (SQLException e) {
            System.out.println("Error al realizar pago: " + e.getMessage());
        }
    }
}

