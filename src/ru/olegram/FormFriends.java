package ru.olegram;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by olegu on 08.05.2017.
 */
public class FormFriends {
    private JPanel rootPanel;
    private JTextPane textFriends;
    private JTextArea textAreaFriends;
    private JButton min;
    private JButton butExit;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JTextPane getTextFriends() {
        return textFriends;
    }
    public JTextArea getTextAreaFriends() {
        return textAreaFriends;
    }

    public JButton getMin() {
        return min;
    }

    public JButton getButExit() {
        return butExit;
    }

    public FormFriends() {
        getMin().addActionListener(e -> Main.frame.setState(JFrame.ICONIFIED));
        getButExit().addActionListener(e -> {
            Main.frame.dispose();
            System.exit(0);
        });
    }
}
