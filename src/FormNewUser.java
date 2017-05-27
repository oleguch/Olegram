import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormNewUser {
    private JPanel rootPanel;
    private JTextField regName;
    private JTextField regSurname;
    private JButton buttonReg;
    private JLabel titleLabel;
    private JLabel label;
    private JLabel labelName;
    private JLabel labelSurname;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getRegName() {
        return regName;
    }

    public JTextField getRegSurname() {
        return regSurname;
    }

    public JButton getButtonReg() {
        return buttonReg;
    }

    public FormNewUser() {

    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        getButtonReg().addActionListener(actionListener);
        getRegName().addActionListener(actionListener);
        getRegSurname().addActionListener(actionListener);
    }
}
