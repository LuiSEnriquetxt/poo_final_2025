package poo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class Main {

    private static Administrador admin;
    private static Cliente cliente;
    private static Carrito carrito;

    public static void main(String[] args) {

        // Crear usuarios ejemplo
        admin = new Administrador(1, "Admin", "admin@ecommerce.com", "123");
        cliente = new Cliente(2, "Luis", "luis@correo.com", "123");
        carrito = new Carrito(1, cliente.getId());

        mostrarLogin();
    }
    private static void mostrarLogin() {
        JFrame frame = new JFrame("Login");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(3, 2, 10, 10));

        JTextField emailField = new JTextField();
        JPasswordField passField = new JPasswordField();
        JButton loginBtn = new JButton("Login");

        frame.add(new JLabel("Email:"));
        frame.add(emailField);
        frame.add(new JLabel("Contraseña:"));
        frame.add(passField);
        frame.add(new JLabel()); // vacío para espacio
        frame.add(loginBtn);

        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String pass = new String(passField.getPassword());

            try {
                // Conexión a la base de datos
                Connection con = DriverManager.getConnection(
                        "jdbc:sqlserver://localhost\\SQLEXPRESS;databaseName=poo;" +
                                "user=poo_log;password=191936555;encrypt=true;trustServerCertificate=true;"
                );

                String query = "SELECT * FROM Usuario WHERE email = ? AND password = ?";
                PreparedStatement ps = con.prepareStatement(query);
                ps.setString(1, email);
                ps.setString(2, pass);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String tipo = rs.getString("tipo"); // "admin" o "cliente"
                    int idUsuario = rs.getInt("id");
                    String nombre = rs.getString("nombre");

                    // Crear objeto según tipo
                    if (tipo.equalsIgnoreCase("admin")) {
                        admin = new Administrador(idUsuario, nombre, email, pass);
                        frame.dispose();
                        mostrarPanelAdmin();
                    } else {
                        cliente = new Cliente(idUsuario, nombre, email, pass);
                        carrito = new Carrito(1, cliente.getId()); // idCarrito inicial = 1
                        frame.dispose();
                        mostrarPanelCliente();
                    }

                } else {
                    JOptionPane.showMessageDialog(frame, "Usuario o contraseña incorrectos");
                }

                con.close();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "Error de conexión a la base de datos:\n" + ex.getMessage());
            }
        });

        frame.setVisible(true);
    }



    private static void mostrarPanelAdmin() {
        JFrame frame = new JFrame("Administrador");
        frame.setSize(500,400);
        frame.setLayout(new FlowLayout());

        JButton agregarBtn = new JButton("Agregar Producto");
        JButton eliminarBtn = new JButton("Eliminar Producto");
        JButton listarChoferesBtn = new JButton("Listar Choferes");

        frame.add(agregarBtn);
        frame.add(eliminarBtn);
        frame.add(listarChoferesBtn);

        agregarBtn.addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog("Nombre:");
            String desc = JOptionPane.showInputDialog("Descripción:");
            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));
            Producto p = new Producto(0, nombre, desc, java.math.BigDecimal.valueOf(precio), stock);
            admin.agregarProducto(p);
        });

        eliminarBtn.addActionListener(e -> {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID a eliminar:"));
            admin.eliminarProducto(id);
        });

        listarChoferesBtn.addActionListener(e -> {
            String lista = DeliveryChofer.obtenerChoferesComoTexto();
            JOptionPane.showMessageDialog(null, lista, "Lista de Choferes", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setVisible(true);
        /// ////////
        JButton agregarChoferBtn = new JButton("Agregar Chofer");
        frame.add(agregarChoferBtn);

        agregarChoferBtn.addActionListener(e -> {
            Main m = new Main();
            m.mostrarPanelAgregarChofer();
        });


    }
    private static void mostrarPanelCliente() {
        JFrame frame = new JFrame("Panel Cliente");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel productosPanel = new JPanel();
        productosPanel.setLayout(new GridLayout(0, 3, 10, 10)); // 3 columnas, tantas filas como productos

        JScrollPane scrollPane = new JScrollPane(productosPanel);
        frame.add(scrollPane, BorderLayout.CENTER);

        JButton verCarritoBtn = new JButton("Ver Carrito");
        JButton pagarBtn = new JButton("Pagar");
        JButton verChoferesBtn = new JButton("Ver Choferes");
        JButton reseñaGeneralBtn = new JButton("Reseña");


        JPanel accionesPanel = new JPanel();
        accionesPanel.add(verCarritoBtn);
        accionesPanel.add(pagarBtn);
        accionesPanel.add(verChoferesBtn);
        accionesPanel.add(reseñaGeneralBtn);
/// ////////////llama a la pantalla resñea 77777
        reseñaGeneralBtn.addActionListener(e -> mostrarVentanaReseñaGeneral());


        frame.add(accionesPanel, BorderLayout.SOUTH);

        // Cargar productos desde la biblioteca
        List<Producto> productos = Biblioteca.listarProductos();
        productosPanel.removeAll();

        for(Producto p : productos) {
            /// //////////////////
            if (p.getStock() == 0) {
                JButton agotadoBtn = new JButton("<html>" + p.getNombre() + "<br>AGOTADO</html>");
                agotadoBtn.setEnabled(false);
                agotadoBtn.setPreferredSize(new Dimension(150, 100));
                productosPanel.add(agotadoBtn);
                continue;
            }

            /// /////////////////////////////
            JButton prodBtn = new JButton("<html>" + p.getNombre() + "<br>Precio: " + p.getPrecio() + "<br>Stock: " + p.getStock() + "</html>");
            prodBtn.setPreferredSize(new Dimension(150, 100));
            //prodBtn.addActionListener(e -> {
              //  int cant = Integer.parseInt(JOptionPane.showInputDialog("Cantidad:"));
                //carrito.agregarProducto(new ItemProducto(p, cant));
                //JOptionPane.showMessageDialog(frame, "Producto agregado al carrito");
            //});
            prodBtn.addActionListener(e -> {
                String input = JOptionPane.showInputDialog("Cantidad:");
                if (input == null) return;

                int cant = Integer.parseInt(input);

                // VALIDAR STOCK
                if (cant > p.getStock()) {
                    JOptionPane.showMessageDialog(frame,
                            "Stock insuficiente.\nStock disponible: " + p.getStock());
                    return;
                }

                carrito.agregarProducto(new ItemProducto(p, cant));
                JOptionPane.showMessageDialog(frame, "Producto agregado al carrito");
            });

            //(seelimino esto )
            productosPanel.add(prodBtn);//
        }

        // Botón Ver Carrito
        verCarritoBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder("Carrito:\n");
            carrito.getItems().forEach(item -> sb.append(item.getProducto().getNombre())
                    .append(" x ").append(item.getCantidad())
                    .append(" = ").append(item.getSubtotal()).append("\n"));
            JOptionPane.showMessageDialog(frame, sb.toString());
        });
        pagarBtn.addActionListener(e -> {
            double total = carrito.calcularTotal();
            if(total == 0){
                JOptionPane.showMessageDialog(frame, "El carrito está vacío");
                return;
            }

            // Simular tipo de pago
            String[] opciones = {"Tarjeta", "Efectivo"};
            String metodoPago = (String) JOptionPane.showInputDialog(frame,
                    "Seleccione método de pago:", "Pago",
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);

            if(metodoPago != null){
                if(metodoPago.equals("Tarjeta")){
                    // Pedir datos de la tarjeta
                    JTextField nombreField = new JTextField();
                    JTextField numeroField = new JTextField();
                    JTextField vencField = new JTextField();
                    JTextField cvvField = new JTextField();

                    Object[] mensaje = {
                            "Nombre en la tarjeta:", nombreField,
                            "Número de tarjeta:", numeroField,
                            "Fecha de vencimiento (MM/AA):", vencField,
                            "CVV:", cvvField
                    };

                    int opcion = JOptionPane.showConfirmDialog(frame, mensaje, "Datos de tarjeta", JOptionPane.OK_CANCEL_OPTION);
                    if(opcion == JOptionPane.OK_OPTION){
                        String nombreTarjeta = nombreField.getText();
                        String numeroTarjeta = numeroField.getText();
                        String fechaVenc = vencField.getText();
                        String cvv = cvvField.getText();

                        // Aquí podrías agregar validaciones básicas
                        if(nombreTarjeta.isEmpty() || numeroTarjeta.isEmpty() || fechaVenc.isEmpty() || cvv.isEmpty()){
                            JOptionPane.showMessageDialog(frame, "Debe completar todos los campos de la tarjeta");
                            return;
                        }

                        // Simular procesamiento del pago
                        Pago.realizarPago(cliente.getId(), total);
/// /////////////////////////////////////////////////////////////////////////
                        // Reducir stock después de pagar
                        for (ItemProducto item : carrito.getItems()) {
                            Producto prod = item.getProducto();
                            int nuevoStock = prod.getStock() - item.getCantidad();
                            prod.setStock(nuevoStock);

                            // Guardar en la BD
                            Biblioteca.actualizarStock(prod.getId(), nuevoStock);
                        }

                        /// aea //

                        DeliveryChofer chofer = DeliveryChofer.obtenerChoferDisponible();

                        if (chofer == null) {
                            JOptionPane.showMessageDialog(null, "No hay choferes disponibles.");
                            return;
                        }

                        // marcar como ocupado
                        DeliveryChofer.cambiarDisponibilidad(chofer.getId(), false);


                        // Generar boleta
                        StringBuilder boleta = new StringBuilder("=== BOLETA DE COMPRA ===\n");
                        boleta.append("Cliente: ").append(cliente.getNombre()).append("\n");
                        boleta.append("Método de pago: ").append(metodoPago).append("\n");
                        boleta.append("-------------------------\n");
                        carrito.getItems().forEach(item -> {
                            boleta.append(item.getProducto().getNombre())
                                    .append(" x ").append(item.getCantidad())
                                    .append(" = ").append(item.getSubtotal()).append("\n");
                        });
                        boleta.append("-------------------------\n");
                        boleta.append("TOTAL: ").append(total).append("\n");
                        boleta.append("========================\n");
                        boleta.append("Chofer asignado: ").append(chofer.getNombre()).append("\n");

                        JOptionPane.showMessageDialog(frame, boleta.toString(), "Boleta", JOptionPane.INFORMATION_MESSAGE);
                        carrito.getItems().clear(); // Vaciar carrito
                    }

                } else { // Efectivo
                    Pago.realizarPago(cliente.getId(), total);

                    StringBuilder boleta = new StringBuilder("=== BOLETA DE COMPRA ===\n");
                    boleta.append("Cliente: ").append(cliente.getNombre()).append("\n");
                    boleta.append("Método de pago: ").append(metodoPago).append("\n");
                    boleta.append("-------------------------\n");
                    carrito.getItems().forEach(item -> {
                        boleta.append(item.getProducto().getNombre())
                                .append(" x ").append(item.getCantidad())
                                .append(" = ").append(item.getSubtotal()).append("\n");
                    });
                    boleta.append("-------------------------\n");
                    boleta.append("TOTAL: ").append(total).append("\n");
                    boleta.append("========================\n");

                    JOptionPane.showMessageDialog(frame, boleta.toString(), "Boleta", JOptionPane.INFORMATION_MESSAGE);
                    carrito.getItems().clear();
                }
            }
        });






        // Ver choferes
        verChoferesBtn.addActionListener(e -> {
            DeliveryChofer.listarChoferes();
            /// ////aea areado recien////
            String lista = DeliveryChofer.obtenerChoferesComoTexto();
            JOptionPane.showMessageDialog(null, lista, "choferes", JOptionPane.INFORMATION_MESSAGE);

        });

        frame.setVisible(true);
    }
    public void mostrarPanelAgregarChofer() {
        JFrame frame = new JFrame("Agregar Chofer");
        frame.setSize(350, 200);
        frame.setLayout(null);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 20, 80, 30);
        frame.add(lblNombre);

        JTextField txtNombre = new JTextField();
        txtNombre.setBounds(100, 20, 200, 30);
        frame.add(txtNombre);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(100, 70, 120, 30);
        frame.add(btnAgregar);

        btnAgregar.addActionListener(e -> {
            String nombre = txtNombre.getText().trim();

            if(nombre.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Ingrese un nombre.");
                return;
            }

            DeliveryChofer.agregarChofer(nombre);
            JOptionPane.showMessageDialog(frame, "Chofer agregado correctamente.");

            txtNombre.setText("");
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    /// //////////reseña*//////
    private static void mostrarVentanaReseñaGeneral() {
        // Pedir producto
        List<Producto> productos = Biblioteca.listarProductos();
        if (productos.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos para reseñar.");
            return;
        }

        String[] nombres = productos.stream().map(Producto::getNombre).toArray(String[]::new);

        String elegido = (String) JOptionPane.showInputDialog(
                null,
                "Selecciona el producto que deseas reseñar:",
                "Reseña de Producto",
                JOptionPane.QUESTION_MESSAGE,
                null,
                nombres,
                nombres[0]
        );

        if (elegido == null) return;

        // Buscar producto seleccionado
        Producto seleccionado = productos.stream()
                .filter(p -> p.getNombre().equals(elegido))
                .findFirst().orElse(null);

        if (seleccionado == null) return;

        // Abrir ventana de reseña
        JFrame ventana = new JFrame("Reseña de " + seleccionado.getNombre());
        ventana.setSize(400, 300);
        ventana.setLayout(new BorderLayout());

        JTextArea txtComentario = new JTextArea();
        txtComentario.setLineWrap(true);
        txtComentario.setWrapStyleWord(true);

        Integer[] opciones = {1, 2, 3, 4, 5};
        JComboBox<Integer> comboCalif = new JComboBox<>(opciones);

        JButton btnEnviar = new JButton("Enviar");

        btnEnviar.addActionListener(ev -> {
            String comentario = txtComentario.getText().trim();
            int calif = (int) comboCalif.getSelectedItem();

            if (comentario.isEmpty()) {
                JOptionPane.showMessageDialog(ventana, "El comentario no puede estar vacío.");
                return;
            }

            Reseña.agregarReseña(seleccionado.getId(), cliente.getId(), comentario, calif);

            JOptionPane.showMessageDialog(ventana, "¡Gracias por tu reseña!");
            ventana.dispose();
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(new JLabel("Comentario:"), BorderLayout.NORTH);
        top.add(new JScrollPane(txtComentario), BorderLayout.CENTER);

        JPanel bot = new JPanel();
        bot.add(new JLabel("Calificación:"));
        bot.add(comboCalif);
        bot.add(btnEnviar);

        ventana.add(top, BorderLayout.CENTER);
        ventana.add(bot, BorderLayout.SOUTH);

        ventana.setLocationRelativeTo(null);
        ventana.setVisible(true);
    }




}
