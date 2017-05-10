package ru.olegram;
import javax.swing.*;

public class FormFriends {
    private JPanel rootPanel;
    private JButton butMinimize;
    private JButton butExit;
    private JPanel innerpanel;

    private DefaultListModel listModel = new DefaultListModel();
    private JList friendsList;
    private JPanel titleBar;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public JButton getButMinimize() {
        return butMinimize;
    }

    public JButton getButExit() {
        return butExit;
    }

    public JPanel getTitleBar() {
        return titleBar;
    }

    public FormFriends() {
        getFriendsList().setModel(listModel);

    }

    public JList getFriendsList() {
        return friendsList;
    }

    public DefaultListModel getListModel() {
        return listModel;
    }
}
