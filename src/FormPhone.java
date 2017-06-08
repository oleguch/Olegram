

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

    public FormPhone() throws ParseException {
        fieldPhone.setHorizontalAlignment(JFormattedTextField.CENTER);      //выравнивание по центру

    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }

    private void createUIComponents() throws ParseException {
        MaskFormatter maskFormatter = new MaskFormatter("+7(###)###-##-##");
        maskFormatter.setPlaceholderCharacter('_');
        fieldPhone = new JFormattedTextField(maskFormatter);
        fieldPhone.setBorder(BorderFactory.createEmptyBorder());
        fieldPhone.setOpaque(false);
        fieldPhone.setFocusLostBehavior(JFormattedTextField.COMMIT);

    }

    public String getPhoneNumber() throws ParseException {
        fieldPhone.commitEdit();
        return (String) fieldPhone.getValue();
    }
}
