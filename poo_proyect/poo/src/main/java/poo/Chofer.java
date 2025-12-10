package poo;

public class Chofer {
    private int id;
    private String nombre;
    private boolean disponible;

    public Chofer(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.disponible = true;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
}
