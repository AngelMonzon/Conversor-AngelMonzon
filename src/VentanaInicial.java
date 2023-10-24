import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class VentanaInicial extends JFrame {
    private JComboBox<String> comboBox;

    public VentanaInicial() {
        // Configuración de la ventana
        setTitle("Ventana de Inicio");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icono = new ImageIcon("src/img/engranajes.png");
        setIconImage(icono.getImage());
        getContentPane().setBackground(Colores.COLOR_DE_FONDO);

        // Creación de los componentes
        JLabel lblTitulo = new JLabel("Elija el tipo de conversor:");
        lblTitulo.setForeground(Colores.COLOR_DE_LETRAS);

        String[] opciones = {"Conversor de Divisas", "Conversor de Medidas"};
        comboBox = new JComboBox<>(opciones);
        JButton btnAbrir = new JButton("Abrir");


        // Configuración del panel
        setLayout(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 10, 5);

        constraints.gridx = 0;
        constraints.gridy = 0;
        add(lblTitulo, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(comboBox, constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(btnAbrir, constraints);

        pack();
        setLocationRelativeTo(null);

        // Agregar acción al botón "Abrir"
        btnAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    abrirConversorSeleccionado();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    private void abrirConversorSeleccionado() throws IOException {
        String seleccion = (String) comboBox.getSelectedItem();
        if (seleccion.equals("Conversor de Divisas")) {
            VentanaConversorDeDivisas ventanaDivisas = new VentanaConversorDeDivisas();
            ventanaDivisas.setVisible(true);
        } else if (seleccion.equals("Conversor de Medidas")) {
            VentanaElegirAreaDeConversion ventanaElegirAreaDeConversion = new VentanaElegirAreaDeConversion();
            ventanaElegirAreaDeConversion.setVisible(true);
        }
        this.dispose();
    }
}

