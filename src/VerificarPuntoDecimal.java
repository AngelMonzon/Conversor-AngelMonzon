import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class VerificarPuntoDecimal implements DocumentListener {
    String[] previousText;

    VerificarPuntoDecimal(String[] previousText){
        this.previousText = previousText;
    }
    @Override
    public void insertUpdate(DocumentEvent e) {

    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        String removedText = previousText[0].substring(e.getOffset(), e.getOffset() + e.getLength());
        if (removedText.equals(".")){
            FiltroDeCaracteresNumericos.PUNTO_INSERTADO = false;
        }
    }

    @Override
    public void changedUpdate(DocumentEvent e) {

    }
}
