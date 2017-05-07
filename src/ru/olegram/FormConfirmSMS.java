package ru.olegram;

import javax.swing.*;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getFieldSMS() {
        return fieldSMS;
    }

    public JButton getButtonSMS() {
        return buttonSMS;
    }

    private JPanel rootPanel;
    private JTextField fieldSMS;
    private JButton buttonSMS;
}
