package ru.olegram;

import javax.swing.*;

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

    public JLabel getTextFromFormSMS() {
        return textFromFormSMS;
    }

    public FormConfirmSMS() {
        getFieldSMS().setText("На номер " + Main.phoneNumber + " отправен код через СМС. " + "\n" + "Введите его в следующем поле.");
    }

    private JLabel textFromFormSMS;
}
