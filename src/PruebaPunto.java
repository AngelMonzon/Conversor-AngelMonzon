import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PruebaPunto {
    public static void main(String[] args) {
        JFrame frame = new JFrame("JTextField Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTextField textField = new JTextField(20);
        final String[] previousText = {textField.getText()};

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                // Se llama cuando se inserta texto
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                // Se llama cuando se elimina texto
                String removedText = previousText[0].substring(e.getOffset(), e.getOffset() + e.getLength());
                System.out.println("Texto eliminado: " + removedText);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Se llama cuando hay un cambio en atributos del documento (poco común)
            }
        });

        frame.add(textField);
        frame.pack();
        frame.setVisible(true);

        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                previousText[0] = textField.getText();
            }
        });
    }
}

