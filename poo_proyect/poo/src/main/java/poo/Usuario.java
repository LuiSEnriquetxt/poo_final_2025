package poo;

public class Usuario {
    protected int id;
    protected String nombre;
    protected String email;
    protected String password;
    protected String tipo; // "cliente" o "admin"

    public Usuario(int id, String nombre, String email, String password, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTipo() { return tipo; }
    public String getPassword() { return password; } // <--- este es el que faltaba
}
