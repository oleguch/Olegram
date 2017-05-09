package ru.olegram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormNewUser {
    private JPanel rootPanel;
    private JTextField regName;
    private JTextField regSurname;
    private JButton buttonReg;
    private JButton min;
    private JButton butExit;

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

    public JButton getMin() {
        return min;
    }

    public JButton getButExit() {
        return butExit;
    }

    public FormNewUser() {
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
    }
}
