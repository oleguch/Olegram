package ru.olegram;

import javafx.stage.Stage;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by olegu on 07.05.2017.
 */
public class FormPhone {
    private JTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JButton button1;
    private JButton button2;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextField getFieldPhone() {
        return fieldPhone;
    }

    public JButton getButtonPhone() {
        return buttonPhone;
    }

    public JButton getButton1() {
        return button1;
    }

    public FormPhone() {
        getButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.frame.setState(JFrame.ICONIFIED);
                //Main.frame.dispose();
                //System.exit(0);
            }
        });
    }
}
