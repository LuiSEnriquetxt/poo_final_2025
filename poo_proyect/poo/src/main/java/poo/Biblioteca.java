package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.DriverManager;

public class Biblioteca {

    public static List<Producto> listarProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre, descripcion, precio, stock FROM Producto";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                productos.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        }
        return productos;
    }
    /// ////////coferes////

    private static List<Chofer> choferes = new ArrayList<>();

    public static List<Chofer> listarChoferes() { return choferes; }

    public static void agregarChofer(Chofer c) { choferes.add(c); }

    public static Chofer obtenerChoferDisponible() {
        return choferes.stream()
                .filter(Chofer::isDisponible)
                .findFirst().orElse(null);
    }
    public static void actualizarStock(int idProducto, int nuevoStock) {
        try (Connection con = DriverManager.getConnection(
                "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=poo;" +
                        "user=poo_log;password=191936555;encrypt=true;trustServerCertificate=true;")) {

            String sql = "UPDATE Producto SET stock = ? WHERE id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, nuevoStock);
            ps.setInt(2, idProducto);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Error actualizando stock: " + e.getMessage());
        }
    }
}


/// //////////choferes///



