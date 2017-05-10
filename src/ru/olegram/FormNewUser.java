package ru.olegram;

import javax.swing.*;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormNewUser {
    private JPanel rootPanel;
    private JTextField regName;
    private JTextField regSurname;
    private JButton buttonReg;
    private JButton butMinimize;
    private JButton butExit;
    private JPanel innerPanel;
    private JPanel titleBar;

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

    public JButton getButMinimize() {
        return butMinimize;
    }

    public JButton getButExit() {
        return butExit;
    }

    public JPanel getTitleBar() {
        return titleBar;
    }

    public FormNewUser() {

    }
}
