import javax.swing.*;
import java.awt.event.ActionListener;

public class FormUsersList {
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane;
    private JList list;
    private JPanel rootPanel;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public FormUsersList() {
        list.setCellRenderer(new FormContact());
    }


    public void setListData (Object[] arrayList) {
        list.setListData(arrayList);

    }

    public void addActionListenerForCloseWindow(ActionListener actionListener) {
        buttonCancel.addActionListener(actionListener);
        buttonOK.addActionListener(actionListener);
    }
}
