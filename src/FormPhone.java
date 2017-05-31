import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.text.ParseException;


public class FormPhone {
    private JFormattedTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JLabel label;

    public JPanel getRootPanel() {
        return rootPanel;
    }
    public JFormattedTextField getFieldPhone() {
        return fieldPhone;
    }

    public FormPhone() throws ParseException {
        fieldPhone.setValue("");                                            //без этого не работает getValue(), выдает null
        fieldPhone.setHorizontalAlignment(JFormattedTextField.CENTER);      //выравнивание по центру
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }
}
