import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class VentanaElegirAreaDeConversion extends JFrame {
    private JComboBox<String> comboBox;

    public VentanaElegirAreaDeConversion() throws IOException {
        // Configuración de la ventana
        setTitle("Ventana de Inicio");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        ImageIcon icono = new ImageIcon("src/img/unidades.png");
        setIconImage(icono.getImage());
        getContentPane().setBackground(Colores.COLOR_DE_FONDO);

        // Creación de los componentes
        JLabel lblTitulo = new JLabel("Que decea convertir?");
        lblTitulo.setForeground(Colores.COLOR_DE_LETRAS);

        //Obtencion de la lista del conversor de unidades
        ObtenerTipoConversiones obtenerTipoConversiones = new ObtenerTipoConversiones();
        List<String> listaUnidades = obtenerTipoConversiones.listaTiposConversion;

        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>(listaUnidades.toArray(new String[0]));
        comboBox = new JComboBox<>(comboBoxModel);
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

        VentanaConversorDeUnidades ventanaUnidades = new VentanaConversorDeUnidades(seleccion);
        ventanaUnidades.setVisible(true);

    }
}

