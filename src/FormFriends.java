import javax.swing.*;

public class FormFriends {
    private JPanel rootPanel;

    private DefaultListModel listModel = new DefaultListModel();
    private JList friendsList;

    public JPanel getRootPanel() {
        return rootPanel;
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
