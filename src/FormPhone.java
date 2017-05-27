import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.text.ParseException;


public class FormPhone {
    private JFormattedTextField fieldPhoneFormatted;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JLabel label;

    public JPanel getRootPanel() {
        return rootPanel;
    }
    public JFormattedTextField getFieldPhoneFormatted() {
        return fieldPhoneFormatted;
    }
    public JButton getButtonPhone() {
        return buttonPhone;
    }

    public FormPhone() throws ParseException {
        MaskFormatter maskPhone = new MaskFormatter("+#(###)###-##-##");
        maskPhone.setPlaceholderCharacter('*');
        maskPhone.install(getFieldPhoneFormatted());
        getFieldPhoneFormatted().requestFocusInWindow();
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        getButtonPhone().addActionListener(actionListener);
        getFieldPhoneFormatted().addActionListener(actionListener);
    }
}
