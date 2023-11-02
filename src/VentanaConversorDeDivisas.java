import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

public class VentanaConversorDeDivisas extends JFrame {
    private final JLabel lblResultado;
    private final JTextField txtMonto;
    private final JComboBox<String> cbMonedaOrigen, cbMonedaDestino;
    private final JComboBox<String> cbCambioDeidioma;


    public VentanaConversorDeDivisas() {
        // Configuración de la ventana
        setTitle("Conversor de Divisas");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icono = new ImageIcon("src/img/divisa.png");
        setIconImage(icono.getImage());

        //Obtener la lista de todas las divisas
        //ObtenerDivisas obtenerDivisas = new ObtenerDivisas();
        DivisasEnEspanol divisasEnEspanol = new DivisasEnEspanol();
        DivisasEnIngles divisasEnIngles = new DivisasEnIngles();
        List<String> keysEspanol = new ArrayList<>(divisasEnEspanol.diccionario.keySet());
        List<String> keysIngles = new ArrayList<>(divisasEnIngles.diccionario.keySet());

        //Divisas en espanol
        DefaultComboBoxModel<String> divisasCBModelOrigenEspanol = new DefaultComboBoxModel<>(keysEspanol.toArray(new String[0]));
        DefaultComboBoxModel<String> divisasCBModelDestinoEspanol = new DefaultComboBoxModel<>(keysEspanol.toArray(new String[0]));

        //Divisas en ingles
        DefaultComboBoxModel<String> divisasCBModelOrigenIngles = new DefaultComboBoxModel<>(keysIngles.toArray(new String[0]));
        DefaultComboBoxModel<String> divisasCBModelDestinoIngles = new DefaultComboBoxModel<>(keysIngles.toArray(new String[0]));


        // Creación de los componentes

        //Anadir menu
        MenuVentanas.createMenu(this);
        MenuVentanas.VENTANA_ABIERTA = 0;

        Dimension tamanoComponentes = new Dimension(150,25);

        //ComboBox para el cambio de idioma
        String[] opciones = {"Español", "Ingles"};
        cbCambioDeidioma = new JComboBox<>(opciones);

        //Anadir accion a combobox de cambio de idioma
        cbCambioDeidioma.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cbCambioDeidioma.getSelectedItem() == "Español"){
                    cbMonedaOrigen.setModel(divisasCBModelOrigenEspanol);
                    cbMonedaDestino.setModel(divisasCBModelDestinoEspanol);
                    cbMonedaOrigen.setSelectedItem("Dólar estadounidense (USD)");
                    cbMonedaDestino.setSelectedItem("Peso mexicano (MXN)");
                } else if (cbCambioDeidioma.getSelectedItem() == "Ingles") {
                    cbMonedaOrigen.setModel(divisasCBModelOrigenIngles);
                    cbMonedaDestino.setModel(divisasCBModelDestinoIngles);
                    cbMonedaOrigen.setSelectedItem("United States Dollar (USD)");
                    cbMonedaDestino.setSelectedItem("Mexican Peso (MXN)");

                } else {
                    System.out.println("error");
                }
            }
        });

        //Verificar si hay conexion y mostrarla al usuario
        VerificarConexion verificarConexion = new VerificarConexion();
        JLabel lblConexion = new JLabel(verificarConexion.estadoConeccion);
        lblConexion.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaOrigen = new JLabel("Moneda Origen:");
        lblMonedaOrigen.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaDestino = new JLabel("Moneda Destino:");
        lblMonedaDestino.setForeground(Colores.COLOR_DE_LETRAS);


        //JtextField para ingresar monto a convertir
        txtMonto =  new JTextField("1",10 );
        txtMonto.setBackground(Colores.COLOR_DE_JTextField);
        txtMonto.setForeground(Colores.COLOR_DE_LETRA_JTextField);
        txtMonto.setPreferredSize(tamanoComponentes);
        txtMonto.setHorizontalAlignment(SwingConstants.RIGHT);
        Document document = txtMonto.getDocument();
        ((AbstractDocument) document).setDocumentFilter(FiltroDeCaracteresNumericos.FILTRO_NUMERICO);
        final String[] previousText = {txtMonto.getText()};
        txtMonto.getDocument().addDocumentListener(new VerificarPuntoDecimal(previousText));
        txtMonto.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                previousText[0] = txtMonto.getText();
            }
        });
        txtMonto.addActionListener(e -> convertirDivisas());



        //Boton para alternar las monedas
        ImageIcon iconoAlternar = new ImageIcon("src/img/sincronizacion.png");
        JButton btnAlternar = new JButton(iconoAlternar);
        btnAlternar.setPreferredSize(new Dimension(30, 30));

        btnAlternar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String origenSeleccion = (String) cbMonedaOrigen.getSelectedItem();
                String destinoSeleccion = (String) cbMonedaDestino.getSelectedItem();
                cbMonedaDestino.setSelectedItem(origenSeleccion);
                cbMonedaOrigen.setSelectedItem(destinoSeleccion);
                convertirDivisas();
            }
        });

        //Combobox para seleccionar moneda origen
        cbMonedaOrigen = new JComboBox<>(divisasCBModelOrigenEspanol);
        cbMonedaOrigen.setPreferredSize(tamanoComponentes);
        cbMonedaOrigen.setSelectedItem("Dólar estadounidense (USD)");

        //Combobox para seleccionar moneda destino
        cbMonedaDestino = new JComboBox<>(divisasCBModelDestinoEspanol);
        cbMonedaDestino.setPreferredSize(tamanoComponentes);
        cbMonedaDestino.setSelectedItem("Peso mexicano (MXN)");

        //Boton para realizar la conversion
        JButton btnConvertir = new JButton("Convertir");
        // Agregar acción al botón "Convertir"
        btnConvertir.addActionListener(e -> convertirDivisas());

        //JLabel donde se mostrara el resultado
        lblResultado = new JLabel("0.00");
        lblResultado.setForeground(Colores.COLOR_DE_LETRAS);
        lblResultado.setPreferredSize(tamanoComponentes);
        lblResultado.setHorizontalAlignment(SwingConstants.RIGHT);

        // Configuración del panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Colores.COLOR_DE_FONDO);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 15, 15, 15);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(cbCambioDeidioma, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(lblConexion, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(lblMonto, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(txtMonto, constraints);


        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(lblMonedaOrigen, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(cbMonedaOrigen, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(btnAlternar, constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        panel.add(lblMonedaDestino, constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        panel.add(cbMonedaDestino, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        panel.add(btnConvertir, constraints);

        constraints.gridx = 1;
        constraints.gridy = 5;
        panel.add(lblResultado, constraints);

        // Agregar el panel a la ventana
        add(panel);

        pack();


        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaInicial ventanaInicial = new VentanaInicial();
                ventanaInicial.setVisible(true);
            }
        });

        //Establece una conversion por defecto al abrir la ventana
        convertirDivisas();
    }

    //Variables para la funcion convertirDivisas();
    String monedaOrigen;
    String monedaDestino;

    private void convertirDivisas() {
        try {
            // Obtener el monto ingresado por el usuario
            BigDecimal monto = new BigDecimal(txtMonto.getText());

            // Obtener la moneda de origen y destino seleccionadas
            DivisasEnEspanol divisasEnEspanol = new DivisasEnEspanol();
            DivisasEnIngles divisasEnIngles = new DivisasEnIngles();

            if (cbCambioDeidioma.getSelectedItem() == "Español"){
                monedaOrigen = divisasEnEspanol.diccionario.get(cbMonedaOrigen.getSelectedItem());
                monedaDestino = divisasEnEspanol.diccionario.get(cbMonedaDestino.getSelectedItem());
            } else if (cbCambioDeidioma.getSelectedItem() == "Ingles") {
                monedaOrigen = divisasEnIngles.diccionario.get(cbMonedaOrigen.getSelectedItem());
                monedaDestino = divisasEnIngles.diccionario.get(cbMonedaDestino.getSelectedItem());
            } else {
                System.out.println("error");
            }


            //Conversion
            ObtenerDivisas obtenerDivisas = new ObtenerDivisas();
            BigDecimal resultado = obtenerDivisas.compararMonedas(monto,monedaOrigen, monedaDestino);

            // Mostrar el resultado en la etiqueta
            lblResultado.setText(resultado + " " + monedaDestino);
        } catch (NumberFormatException e) {
            lblResultado.setText("Error: Ingrese un monto válido");
        } catch (IOException ex){
            lblResultado.setText("Error: Error en red");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
