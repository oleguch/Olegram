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
        ImageIcon buttonExitIcon = new ImageIcon("e:\\Java\\Project\\SkillBox\\Olegram\\src\\ru\\olegram\\buttonExit.png");
        getButExit().setBorderPainted(false);
        getButExit().setFocusPainted(false);
        getButExit().setIcon(buttonExitIcon);
    }

    public JButton getButExit() {
        return butExit;
    }
}
