import org.telegram.api.engine.RpcException;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionListener;

public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getButtonSMS() {
        return buttonSMS;
    }

    private JPanel rootPanel;
    private JButton buttonSMS;
    private JTextArea textLabelSMS;
    private JPasswordField passwordField;
    private JLabel titleLabel;

    public JTextArea getTextLabelSMS() {
        return textLabelSMS;
    }

    public FormConfirmSMS() {
        passwordField.setHorizontalAlignment(0);
        DocumentFilter documentFilter = new MyDocumentFilter();
        ((AbstractDocument) passwordField.getDocument()).setDocumentFilter(documentFilter);
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        getButtonSMS().addActionListener(actionListener);
        getPasswordField().addActionListener(actionListener);
    }

    public class MyDocumentFilter extends DocumentFilter {
        public void insertString(DocumentFilter.FilterBypass fb, int offset,
                                 String text, AttributeSet attr) throws BadLocationException {
            int lengthPassw = getPasswordField().getPassword().length;
            int lengthText = text.length();
            text = text.replaceAll("\\D", "");
            if (lengthPassw >= 6)
                text = "";
            else if (lengthPassw + lengthText >= 6)
                    //(text.length() > 6)
                text = text.substring(0, 6-lengthPassw);
            fb.insertString(offset, text, attr);
        }
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            fb.remove(offset, length);
        }
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                            String text, AttributeSet attr) throws BadLocationException {
            int lengthPassw = getPasswordField().getPassword().length;
            if (text.isEmpty())
                remove(fb, 0, lengthPassw);
            else {
                text = text.replaceAll("\\D", "");
                if (text.length()  + lengthPassw - length >= 6 )
                    text = text.substring(0, 6-lengthPassw + length);
                fb.replace(offset, length,text,attr);
            }
        }
    }
}
