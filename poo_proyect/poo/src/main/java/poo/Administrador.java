package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Administrador extends Usuario {

    public Administrador(int id, String nombre, String email, String password) {
        super(id, nombre, email, password, "admin");
    }

    public void agregarProducto(Producto p) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "INSERT INTO Producto(nombre, descripcion, precio, stock) VALUES(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getDescripcion());
            ps.setBigDecimal(3, p.getPrecio());
            ps.setInt(4, p.getStock());
            ps.executeUpdate();
            System.out.println("Producto agregado: " + p.getNombre());
        } catch (SQLException e) {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }
    }

    public void eliminarProducto(int productoId) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "DELETE FROM Producto WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, productoId);
            ps.executeUpdate();
            System.out.println("Producto eliminado: " + productoId);
        } catch (SQLException e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    // Aquí se pueden agregar métodos para administrar choferes
}
