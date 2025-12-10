package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente extends Usuario {

    public Cliente(int id, String nombre, String email, String password) {
        super(id, nombre, email, password, "cliente");
    }

    public void verProductos() {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre, descripcion, precio, stock FROM Producto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Productos disponibles:");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("nombre") + " | Precio: " + rs.getBigDecimal("precio") + " | Stock: " + rs.getInt("stock"));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar productos: " + e.getMessage());
        }
    }

    // Métodos para agregar productos al carrito, pagar, ver choferes, etc.
}
