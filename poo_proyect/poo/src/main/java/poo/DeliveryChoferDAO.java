package poo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DeliveryChoferDAO {

    private static Connection conectar() throws Exception {
        return DriverManager.getConnection(
                "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=poo;" +
                        "user=poo_log;password=191936555;encrypt=true;trustServerCertificate=true;"
        );
    }

    public static List<String> listarChoferes() {
        List<String> lista = new ArrayList<>();

        try (Connection con = conectar()) {
            String sql = "SELECT nombre, disponibilidad FROM DeliveryChofer";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(rs.getString("nombre") + " - " + rs.getString("disponibilidad"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return lista;
    }

    public static void agregarChofer(String nombre) {
        try (Connection con = conectar()) {
            String sql = "INSERT INTO DeliveryChofer(nombre, disponibilidad) VALUES (?, 'disponible')";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

