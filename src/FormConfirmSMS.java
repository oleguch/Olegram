import javafx.scene.AmbientLight;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.event.ActionListener;

import static javax.swing.GroupLayout.Alignment.CENTER;

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
        DocumentFilter documentFilter = new MyDocumentFilterForCode();                  //фильтр кода смс
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


    //фильтр кода смс
    public class MyDocumentFilterForCode extends DocumentFilter {
        int amountNumberOfCode = 5;                                                     //количество цифр в коде
        public void insertString(DocumentFilter.FilterBypass fb, int offset,            //действия при вставке
                                 String text, AttributeSet attr) throws BadLocationException {
            text = text.replaceAll("\\D", "");                          //избавляемся от нецифр
            int lengthCode = fb.getDocument().getLength();
            if (lengthCode >= amountNumberOfCode)
                text = "";                                                              //в случае если уже макс цифр в коде, то убираем строку
            else if (lengthCode + text.length() >= amountNumberOfCode)                  //иначе смотрим, не станет ли больше при вставке
                text = text.substring(0, amountNumberOfCode-lengthCode);                //если станет, то обрезаем вставляемую строку
            fb.insertString(offset, text, attr);                                        //вставляем строку
        }
        public void remove(DocumentFilter.FilterBypass fb, int offset, int length) throws BadLocationException {
            fb.remove(offset, length);                                                  //удаляем
        }
        public void replace(DocumentFilter.FilterBypass fb, int offset, int length,     //действия при замене символов
                            String text, AttributeSet attr) throws BadLocationException {
            int lengthCode = fb.getDocument().getLength();
            if (text.isEmpty())
                remove(fb, 0, lengthCode);                                          //если вставляем пустую строку, то удаляем всю (например при setValue(""))
            else {
                text = text.replaceAll("\\D", "");
                if (text.length()  + lengthCode - length >= amountNumberOfCode )            //смотрим, не станет ли больше макс при замене и вставке новой строки
                    text = text.substring(0, amountNumberOfCode-lengthCode + length);
                fb.replace(offset, length,text,attr);
            }
        }
    }

    public void setFocusToCodeField() {
        codeField.requestFocusInWindow();
    }

    public String getCode() {
        return new String(codeField.getPassword());
    }
}
