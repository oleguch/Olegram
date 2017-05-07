package ru.olegram;

import javax.swing.*;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormNewUser {
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

    private JPanel rootPanel;
    private JTextField regName;
    private JTextField regSurname;
    private JButton buttonReg;
}
