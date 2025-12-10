package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Reseña {
    public static void agregarReseña(int productoId, int usuarioId, String comentario, int calificacion) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "INSERT INTO Reseña(producto_id, usuario_id, comentario, calificacion) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productoId);
            ps.setInt(2, usuarioId);
            ps.setString(3, comentario);
            ps.setInt(4, calificacion);
            ps.executeUpdate();
            System.out.println("Reseña agregada al producto: " + productoId);
        } catch (SQLException e) {
            System.out.println("Error al agregar reseña: " + e.getMessage());
        }
    }
}
