import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;

public class VentanaConversorDeDivisas extends JFrame {
    private final JLabel lblResultado;
    private final JTextField txtMonto;
    private final JComboBox<String> cbMonedaOrigen, cbMonedaDestino;

    List<String> divisas;

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
        List<String> keys = new ArrayList<>(divisasEnEspanol.diccionario.keySet());
        //Divisas en espanol
        DefaultComboBoxModel<String> divisasCBModelOrigen = new DefaultComboBoxModel<>(keys.toArray(new String[0]));
        DefaultComboBoxModel<String> divisasCBModelDestino = new DefaultComboBoxModel<>(keys.toArray(new String[0]));

        this.divisas = keys;


        // Creación de los componentes
        Dimension tamanoComponentes = new Dimension(150,25);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaOrigen = new JLabel("Moneda Origen:");
        lblMonedaOrigen.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaDestino = new JLabel("Moneda Destino:");
        lblMonedaDestino.setForeground(Colores.COLOR_DE_LETRAS);

        lblResultado = new JLabel("0.00");
        lblResultado.setForeground(Colores.COLOR_DE_LETRAS);
        lblResultado.setPreferredSize(tamanoComponentes);
        lblResultado.setHorizontalAlignment(SwingConstants.RIGHT);

        txtMonto =  new JTextField(10);
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

        cbMonedaOrigen = new JComboBox<>(divisasCBModelOrigen);
        cbMonedaOrigen.setPreferredSize(tamanoComponentes);

        cbMonedaDestino = new JComboBox<>(divisasCBModelDestino);
        cbMonedaDestino.setPreferredSize(tamanoComponentes);

        JButton btnConvertir = new JButton("Convertir");

        // Configuración del panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Colores.COLOR_DE_FONDO);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(15, 15, 15, 15);

        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(lblMonto, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(txtMonto, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(lblMonedaOrigen, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        panel.add(cbMonedaOrigen, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(lblMonedaDestino, constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        panel.add(cbMonedaDestino, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        panel.add(btnConvertir, constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        panel.add(lblResultado, constraints);

        // Agregar el panel a la ventana
        add(panel);

        pack();

        // Agregar acción al botón "Convertir"
        btnConvertir.addActionListener(e -> convertirDivisas());

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                VentanaInicial ventanaInicial = new VentanaInicial();
                ventanaInicial.setVisible(true);
            }
        });
    }

    private void convertirDivisas() {
        try {
            // Obtener el monto ingresado por el usuario
            BigDecimal monto = new BigDecimal(txtMonto.getText());

            // Obtener la moneda de origen y destino seleccionadas
            DivisasEnEspanol divisasEnEspanol = new DivisasEnEspanol();

            String monedaOrigen = divisasEnEspanol.diccionario.get(cbMonedaOrigen.getSelectedItem());
            String monedaDestino = divisasEnEspanol.diccionario.get(cbMonedaDestino.getSelectedItem());

            //Conversion
            ObtenerDivisas obtenerDivisas = new ObtenerDivisas();
            BigDecimal resultado = obtenerDivisas.compararMonedas(monto,monedaOrigen, monedaDestino);

            // Mostrar el resultado en la etiqueta
            lblResultado.setText(resultado + " " + monedaDestino);
        } catch (NumberFormatException | IOException ex) {
            lblResultado.setText("Error: Ingrese un monto válido");
        }
    }
}
