
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

public class FiltroDeCaracteresNumericos {
    public static boolean PUNTO_INSERTADO = false;
     public static DocumentFilter FILTRO_NUMERICO = (new DocumentFilter() {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            StringBuilder builder = new StringBuilder(string);
            for (int i = builder.length() - 1; i >= 0; i--) {
                char c = builder.charAt(i);
                if (!Character.isDigit(c) && c != '.') {
                    builder.deleteCharAt(i);
                }
            }
            System.out.println("insertString");
            super.insertString(fb, offset, builder.toString(), attr);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text != null) {
                StringBuilder builder = new StringBuilder(text);

                for (int i = builder.length() - 1; i >= 0; i--) {
                    char c = builder.charAt(i);
                    if (c == '.' && PUNTO_INSERTADO) {
                        builder.deleteCharAt(i);
                    } else if (c == '.') {
                        PUNTO_INSERTADO = true;
                    } else if (!Character.isDigit(c) && c != '.') {
                        builder.deleteCharAt(i);
                    }
                }

                text = builder.toString();
            }
            super.replace(fb, offset, length, text, attrs);
        }

     });
}