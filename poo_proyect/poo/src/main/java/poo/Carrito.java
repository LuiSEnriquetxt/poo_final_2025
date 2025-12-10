package poo;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private int id;
    private int usuarioId;
    private List<ItemProducto> items;

    public Carrito(int id, int usuarioId) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.items = new ArrayList<>();
    }

    public void agregarProducto(ItemProducto item) {
        items.add(item);
    }

    public void eliminarProducto(int productoId) {
        items.removeIf(item -> item.getProducto().getId() == productoId);
    }

    public double calcularTotal() {
        return items.stream().mapToDouble(ItemProducto::getSubtotal).sum();
    }

    public List<ItemProducto> getItems() {
        return items;
    }
    public Carrito(int usuarioId) {
        this.id = 1; // temporal, o puedes generar un id dinámico
        this.usuarioId = usuarioId;
        this.items = new ArrayList<>();
    }

}
