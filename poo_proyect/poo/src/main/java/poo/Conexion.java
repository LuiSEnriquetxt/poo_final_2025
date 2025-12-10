package poo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static final String URL = "jdbc:sqlserver://LCH\\SQLEXPRESS:1433;databaseName=poo;encrypt=false;trustServerCertificate=true;";
    private static final String USER = "poo_log";
    private static final String PASSWORD = "191936555";

    public static Connection getConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión exitosa!");
            return conn;
        } catch (ClassNotFoundException e) {
            System.out.println("No se encontró el driver JDBC: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error de SQL: " + e.getMessage());
        }
        return null;
    }
}
