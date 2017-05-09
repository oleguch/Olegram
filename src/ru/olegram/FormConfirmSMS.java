package ru.olegram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JButton min;
    private JButton butExit;
    private JTextField fieldSMS;
    private JButton buttonSMS;
    private JTextArea textLabelSMS;



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
