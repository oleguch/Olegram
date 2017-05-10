package ru.olegram;

import org.telegram.api.engine.RpcException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class FormPhone {
    private JTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JButton min;
    private JButton butExit;
    private JPanel innerPanel;
    private JPanel titleBar;

    public JPanel getRootPanel() {
        return rootPanel;
    }
    public JTextField getFieldPhone() {
        return fieldPhone;
    }
    public JButton getButtonPhone() {
        return buttonPhone;
    }
    public JButton getMin() {
        return min;
    }
    public JPanel getTitleBar() {
        return titleBar;
    }
    public JButton getButExit() {
        return butExit;
    }

    public FormPhone() {

        getFieldPhone().requestFocus();
        getFieldPhone().requestFocusInWindow();
    }

    public void messengerError(Exception e){
        if (e.getMessage().equals("PHONE_NUMBER_INVALID")) {                              //Если неверный номер телефона
            System.out.println("Введен неверный номер телефона");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Введен неверный номер телефона");
            getFieldPhone().requestFocus();
        } else if (e.getMessage().substring(0,10).equals("FLOOD_WAIT")) {
            System.out.println("Много попыток входа");                             //Выводим сообщение
            JOptionPane.showMessageDialog(getRootPanel(), "Много попыток входа, ждите " + e.getMessage().substring(11) + " секунд");
            getFieldPhone().requestFocus();
        }
    }
}
