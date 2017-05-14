import javax.swing.*;

public class FormFriends {
    private JPanel rootPanel;

    private DefaultListModel listModel = new DefaultListModel();
    private JList friendsList;
    private JLabel titlelabel;
    private JLabel label;
    private JScrollPane scrollList;

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
