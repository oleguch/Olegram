package ru.olegram;

import org.telegram.api.engine.RpcException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getButtonSMS() {
        return buttonSMS;
    }

    private JPanel rootPanel;
    private JButton min;
    private JButton butExit;
    private JButton buttonSMS;
    private JTextArea textLabelSMS;
    private JPanel innerPanel;
    private JPasswordField passwordField;
    private JPanel titleBar;


    public JTextArea getTextLabelSMS() {
        return textLabelSMS;
    }

    public JButton getMin() {
        return min;
    }

    public JButton getButExit() {
        return butExit;
    }

    public FormConfirmSMS() {

    }

    public JPanel getInnerPanel() {
        return innerPanel;
    }

    public JPanel getTitleBar() {
        return titleBar;
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
