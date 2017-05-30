import javafx.scene.AmbientLight;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.CENTER;

public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel rootPanel;
    private JButton buttonSMS;
    private JTextArea textLabelSMS;
    private JPasswordField codeField;
    private JLabel titleLabel;

    public JTextArea getTextLabelSMS() {
        return textLabelSMS;
    }

    public FormConfirmSMS() {
        codeField.setHorizontalAlignment(JPasswordField.CENTER);
        DocumentFilter documentFilter = new MyDocumentFilterForCode();
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(documentFilter);
    }

    public JPasswordField getCodeField() {
        return codeField;
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonSMS.addActionListener(actionListener);
        codeField.addActionListener(actionListener);
    }

    public class MyDocumentFilterForCode extends DocumentFilter {
        int amountNumberOfCode = 5;
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                                 String text, AttributeSet attr) throws BadLocationException {
            text = text.replaceAll("\\D", "");
            int lengthCode = fb.getDocument().getLength();
            if (lengthCode >= amountNumberOfCode)
                text = "";
            else if (lengthCode + text.length() >= amountNumberOfCode)
                text = text.substring(0, amountNumberOfCode-lengthCode);
            fb.insertString(offset, text, attr);
        }
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            fb.remove(offset, length);
        }
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                            String text, AttributeSet attr) throws BadLocationException {
            int lengthCode = fb.getDocument().getLength();
            if (text.isEmpty())
                remove(fb, 0, lengthCode);
            else {
                text = text.replaceAll("\\D", "");
                if (text.length()  + lengthCode - length >= amountNumberOfCode )
                    text = text.substring(0, amountNumberOfCode-lengthCode + length);
                fb.replace(offset, length,text,attr);
            }
        }
    }
    public void setFocusToCodeField() {
        codeField.requestFocusInWindow();
    }
}
