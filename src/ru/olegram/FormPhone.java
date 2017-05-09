package ru.olegram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class FormPhone {
    private JTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JButton min;
    private JButton butExit;
    private JPanel innerPanel;
    private JPasswordField passwordField1;
    private JButton button1;


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

    public FormPhone() {
        getMin().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setState(JFrame.ICONIFIED);
            }
        });
        getButExit().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.dispose();
                System.exit(0);
            }
        });
        getFieldPhone().requestFocus();
    }

    public JButton getButExit() {
        return butExit;
    }

    public JPanel getInnerPanel() {
        return innerPanel;
    }


    public void setInnerPanel(JPanel innerPanel) {
        this.innerPanel = innerPanel;
    }

    public JPasswordField getPasswordField1() {
        return passwordField1;
    }

    public JButton getButton1() {
        return button1;
    }
}
