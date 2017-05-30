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
        fieldPhone.requestFocusInWindow();
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    private void createUIComponents() throws ParseException {
        MaskFormatter maskFormatter = new MaskFormatter("+#(###)###-##-##");
        maskFormatter.setPlaceholderCharacter('_');
        fieldPhone = new JFormattedTextField(maskFormatter);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }
}
