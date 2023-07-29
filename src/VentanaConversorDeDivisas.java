import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VentanaConversorDeDivisas extends JFrame {
    private JLabel lblResultado;
    private JTextField txtMonto;
    private JComboBox<String> cbMonedaOrigen, cbMonedaDestino;
    private JButton btnConvertir;

    public VentanaConversorDeDivisas() throws IOException {
        // Configuración de la ventana
        setTitle("Conversor de Divisas");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icono = new ImageIcon("src/img/divisa.png");
        setIconImage(icono.getImage());

        //Obtener la lista de todas las divisas
        ObtenerDivisas obtenerDivisas = new ObtenerDivisas();
        List<String> keys = new ArrayList<>(obtenerDivisas.divisas.rates.keySet());
        DefaultComboBoxModel<String> divisasOrigen = new DefaultComboBoxModel<>(keys.toArray(new String[0]));
        DefaultComboBoxModel<String> divisasDestino = new DefaultComboBoxModel<>(keys.toArray(new String[0]));


        // Creación de los componentes
        Dimension tamanoComponentes = new Dimension(150,25);

        JLabel lblMonto = new JLabel("Monto:");
        lblMonto.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaOrigen = new JLabel("Moneda Origen:");
        lblMonedaOrigen.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMonedaDestino = new JLabel("Moneda Destino:");
        lblMonedaDestino.setForeground(Colores.COLOR_DE_LETRAS);

        lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(Colores.COLOR_DE_LETRAS);
        lblResultado.setPreferredSize(tamanoComponentes);

        txtMonto = new JTextField(10);
        txtMonto.setBackground(Colores.COLOR_DE_FONDO);
        txtMonto.setForeground(Colores.COLOR_DE_LETRAS);
        txtMonto.setPreferredSize(tamanoComponentes);

        cbMonedaOrigen = new JComboBox<>(divisasOrigen);
        cbMonedaOrigen.setPreferredSize(tamanoComponentes);

        cbMonedaDestino = new JComboBox<>(divisasDestino);
        cbMonedaDestino.setPreferredSize(tamanoComponentes);

        btnConvertir = new JButton("Convertir");

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
        btnConvertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertirDivisas();
            }
        });
    }

    private void convertirDivisas() {
        try {
            // Obtener el monto ingresado por el usuario
            BigDecimal monto = new BigDecimal(txtMonto.getText());

            // Obtener la moneda de origen y destino seleccionadas
            String monedaOrigen = (String) cbMonedaOrigen.getSelectedItem();
            String monedaDestino = (String) cbMonedaDestino.getSelectedItem();

            //Conversion
            ObtenerDivisas obtenerDivisas = new ObtenerDivisas();
            BigDecimal resultado = obtenerDivisas.compararMonedas(monto,monedaOrigen, monedaDestino);

            // Mostrar el resultado en la etiqueta
            lblResultado.setText("Resultado: " + resultado + " " + monedaDestino);
        } catch (NumberFormatException | IOException ex) {
            lblResultado.setText("Error: Ingrese un monto válido");
        }
    }
}
