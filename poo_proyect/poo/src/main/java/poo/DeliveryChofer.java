package poo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryChofer {
    private int id;
    private String nombre;
    private boolean disponibilidad;

    public DeliveryChofer(int id, String nombre, boolean disponibilidad) {
        this.id = id;
        this.nombre = nombre;
        this.disponibilidad = disponibilidad;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isDisponible() { return disponibilidad; }

    public static void listarChoferes() {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre, disponibilidad FROM DeliveryChofer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            System.out.println("Choferes disponibles:");
            while (rs.next()) {
                String status = rs.getBoolean("disponibilidad") ? "Disponible" : "Ocupado";
                System.out.println(rs.getInt("id") + " | " + rs.getString("nombre") + " | " + status);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar choferes: " + e.getMessage());
        }
    }

    public static void asignarChofer(int choferId, boolean disponible) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "UPDATE DeliveryChofer SET disponibilidad=? WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, disponible);
            ps.setInt(2, choferId);
            ps.executeUpdate();
            System.out.println("Chofer actualizado. ID: " + choferId + " - Disponibilidad: " + disponible);
        } catch (SQLException e) {
            System.out.println("Error al actualizar chofer: " + e.getMessage());
        }
    }
    public static void agregarChofer(String nombre) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "INSERT INTO DeliveryChofer (nombre, disponibilidad) VALUES (?, 1)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();
            System.out.println("Chofer agregado: " + nombre);
        } catch (SQLException e) {
            System.out.println("Error al agregar chofer: " + e.getMessage());
        }

    }
    /// ///////
    public static String listarChoferesTexto() {
        StringBuilder texto = new StringBuilder();

        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre, disponibilidad FROM DeliveryChofer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String estado = rs.getBoolean("disponibilidad") ? "Disponible" : "Ocupado";

                texto.append("ID: ").append(id)
                        .append(" | Nombre: ").append(nombre)
                        .append(" | Estado: ").append(estado)
                        .append("\n");
            }

        } catch (SQLException e) {
            texto.append("Error: ").append(e.getMessage());
        }

        return texto.toString();
    }

/// ///////////////////////////////////////////////////////


    /// el de erriba se puede eliminar aun no se agrega nada y no afecta en anda
    /// //el de abajo estoy probando
    /// /////////
    ///
    /// }

    public static String obtenerChoferesComoTexto() {
        StringBuilder texto = new StringBuilder("=== CHOFERES ===\n\n");

        try (Connection conn = Conexion.getConnection()) {
            String sql = "SELECT id, nombre, disponibilidad FROM DeliveryChofer";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String estado = rs.getBoolean("disponibilidad") ? "Disponible" : "Ocupado";

                texto.append("ID: ").append(id)
                        .append(" | Nombre: ").append(nombre)
                        .append(" | Estado: ").append(estado)
                        .append("\n");
            }

        } catch (SQLException e) {
            return "Error al obtener choferes: " + e.getMessage();
        }

        return texto.toString();
    }
///////////////////nueva correcccion arear coferes-
public static DeliveryChofer obtenerChoferDisponible() {
    try (Connection conn = Conexion.getConnection()) {
        String sql = "SELECT TOP 1 * FROM DeliveryChofer WHERE disponibilidad = 1";
        PreparedStatement ps = conn.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            return new DeliveryChofer(
                    rs.getInt("id"),
                    rs.getString("nombre"),
                    rs.getBoolean("disponibilidad")
            );
        }
    } catch (SQLException e) {
        System.out.println("Error al obtener chofer disponible: " + e.getMessage());
    }

    return null;
}
    public static void cambiarDisponibilidad(int idChofer, boolean disponible) {
        try (Connection conn = Conexion.getConnection()) {
            String sql = "UPDATE DeliveryChofer SET disponibilidad = ? WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setBoolean(1, disponible);
            ps.setInt(2, idChofer);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar disponibilidad: " + e.getMessage());
        }
    }

/// ////////////////////////////
}


