import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class MenuVentanas {
    public static int VENTANA_ABIERTA;
    public static void createMenu(JFrame frame) {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu archivoMenu = new JMenu("Archivo");
        menuBar.add(archivoMenu);

        JMenuItem reinciarItem = new JMenuItem("Reiniciar");
        JMenuItem salirAHomeItem = new JMenuItem("Salir a Home");
        JMenuItem salirItem = new JMenuItem("Salir");

        archivoMenu.add(reinciarItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirAHomeItem);
        archivoMenu.addSeparator();
        archivoMenu.add(salirItem);

        reinciarItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (VENTANA_ABIERTA == 0){
                    frame.dispose();
                    VentanaConversorDeDivisas ventanaConversorDeDivisas = new VentanaConversorDeDivisas();
                    ventanaConversorDeDivisas.setVisible(true);
                } else if (VENTANA_ABIERTA == 1) {
                    frame.dispose();
                    VentanaConversorDeUnidades ventanaConversorDeUnidades = null;
                    try {
                        ventanaConversorDeUnidades = new VentanaConversorDeUnidades("length");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    ventanaConversorDeUnidades.setVisible(true);
                }
            }
        });

        salirAHomeItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                VentanaInicial ventanaInicial = new VentanaInicial();
                ventanaInicial.setVisible(true);
            }
        });

        salirItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }
}
