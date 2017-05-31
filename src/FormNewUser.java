import javax.swing.*;
import java.awt.event.ActionListener;

public class FormNewUser {
    private JPanel rootPanel;
    private JTextField fieldRegName;
    private JTextField fieldRegSurname;
    private JButton buttonReg;
    private JLabel titleLabel;
    private JLabel label;
    private JLabel labelName;
    private JLabel labelSurname;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    //добавление слушателя на переключение форм
    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonReg.addActionListener(actionListener);
        fieldRegName.addActionListener(actionListener);
        fieldRegSurname.addActionListener(actionListener);
    }

    public void setFocusToName() {
        fieldRegName.requestFocusInWindow();
    }
    public void setFocusToSurname() {
        fieldRegSurname.requestFocusInWindow();
    }

    public Person getPerson() {
        Person person = new Person();
        person.setName(fieldRegName.getText().trim());
        person.setSurname(fieldRegSurname.getText().trim());
        return person;
    }
}
