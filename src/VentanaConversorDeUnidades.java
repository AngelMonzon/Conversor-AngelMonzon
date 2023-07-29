import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class VentanaConversorDeUnidades extends JFrame {
    private JLabel lblResultado;
    private JTextField txtValor;
    private JComboBox<String> comboBoxOrigen, comboBoxDestino;
    private JButton btnConvertir;
    private String unit;

    public VentanaConversorDeUnidades(String unit) throws IOException {
        this.unit = unit;
        // Configuración de la ventana
        setTitle("Conversor de Medidas");
        setSize(300, 200);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Creación de los componentes
        JLabel lblValor = new JLabel("Valor:");
        lblValor.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMedidaOrigen = new JLabel("Medida Origen:");
        lblMedidaOrigen.setForeground(Colores.COLOR_DE_LETRAS);

        JLabel lblMedidaDestino = new JLabel("Medida Destino:");
        lblMedidaDestino.setForeground(Colores.COLOR_DE_LETRAS);

        lblResultado = new JLabel("Resultado: ");
        lblResultado.setForeground(Colores.COLOR_DE_LETRAS);

        txtValor = new JTextField(10);
        txtValor.setForeground(Colores.COLOR_DE_LETRAS);
        txtValor.setBackground(Colores.COLOR_DE_FONDO);

        //Obtener opciones de el area seleccionada
        ObtenerOpcionesGeneric obtenerOpcionesGeneric = new ObtenerOpcionesGeneric();
        List<String> lista = obtenerOpcionesGeneric.obtener(unit);

        //ComboBox 1 Origen
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(lista.toArray(new String[0]));
        comboBoxOrigen = new JComboBox<>(comboBoxModel);

        //ComboBox 2 Destino
        DefaultComboBoxModel<String> comboBoxModel2 = new DefaultComboBoxModel<>(lista.toArray(new String[0]));
        comboBoxDestino = new JComboBox<>(comboBoxModel2);

        btnConvertir = new JButton("Convertir");


        // Configuración del panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));
        panel.setBackground(Colores.COLOR_DE_FONDO);

        panel.add(lblValor);
        panel.add(txtValor);
        panel.add(lblMedidaOrigen);
        panel.add(comboBoxOrigen);
        panel.add(lblMedidaDestino);
        panel.add(comboBoxDestino);
        panel.add(btnConvertir);
        panel.add(lblResultado);

        // Agregar el panel a la ventana
        add(panel);

        // Agregar acción al botón "Convertir"
        btnConvertir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertirMedidas();
            }
        });
    }

    private void convertirMedidas() {
        try {
            // Obtener el valor ingresado por el usuario
            double valor = Double.parseDouble(txtValor.getText());

            // Obtener la medida de origen y destino seleccionadas
            String medidaOrigen = (String) comboBoxOrigen.getSelectedItem();
            String medidaDestino = (String) comboBoxDestino.getSelectedItem();

            // Realizar la conversión
            double resultado;
            OperacionConversorDeUnidades operacionConversorDeUnidades = new OperacionConversorDeUnidades();
            resultado = operacionConversorDeUnidades.convertir(unit, valor, medidaOrigen, medidaDestino);
            System.out.println(resultado);

            // Mostrar el resultado en la etiqueta
            lblResultado.setText("Resultado: " + resultado + " " + medidaDestino);
        } catch (NumberFormatException ex) {
            lblResultado.setText("Error: Ingrese un valor válido");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}