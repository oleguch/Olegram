import org.telegram.api.engine.RpcException;

import javax.swing.*;

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

    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void messageError(RpcException e2) {
        if (e2.getMessage().equals("PHONE_CODE_INVALID")) {                              //Если неверный код
            System.out.println("Введен неверный код");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(),"Введен неверный код");
            getPasswordField().setText(null);
            getPasswordField().requestFocus();
        }
    }
}
