import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionListener;

public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel rootPanel;
    private JButton buttonSMS;
    private JTextArea textLabelSMS;
    private JPasswordField codeField;
    private JLabel titleLabel;

    public FormConfirmSMS() {
        codeField.setHorizontalAlignment(JPasswordField.CENTER);                        //выравнивание по центру
        DocumentFilter documentFilter = new CodeDocumentFilter();                  //фильтр кода смс
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(documentFilter); //добавление фильтра к полю смс
    }

    //добавление слушателя на переключение форм
    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonSMS.addActionListener(actionListener);
        codeField.addActionListener(actionListener);
    }

    public void setPhoneNumberToLabel(String phoneNumber) {
        textLabelSMS.setText("На номер " + phoneNumber + "\nотправен код через СМС. " + "\nВведите его в следующем поле.");
    }


    public void setFocusToCodeField() {
        codeField.requestFocusInWindow();
    }

    public String getCode() {
        return new String(codeField.getPassword());
    }
}
