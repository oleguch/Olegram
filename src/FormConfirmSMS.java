import org.telegram.api.engine.RpcException;

import javax.swing.*;
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
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        getButtonSMS().addActionListener(actionListener);
        getPasswordField().addActionListener(actionListener);
    }
}
