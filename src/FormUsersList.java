import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Created by oleg on 11.06.17.
 */
public class FormUsersList {
    private JButton buttonOK;
    private JButton buttonCancel;
    private JScrollPane scrollPane;
    private JList list;
    private JPanel contentPane;

    public JPanel getContentPane() {
        return contentPane;
    }

    public FormUsersList() {
                buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onOK();
            }
        });
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                onOK();
            }
        });

        list.setCellRenderer(new FormContact());
        //list.setListData(new String[] {"first", "second", "third"});
    }

    private void onOK() {

    }

    public void setListData (Object[] arrayList) {
        list.setListData(arrayList);

    }

    public void addActionListenerForCloseWindow(ActionListener actionListener) {
        buttonCancel.addActionListener(actionListener);
        buttonOK.addActionListener(actionListener);
    }
}
