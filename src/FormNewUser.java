import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class FormNewUser {
    private JPanel rootPanel;
    private JTextField fieldRegName;
    private JTextField fieldRegSurname;
    private JButton buttonReg;
    private JLabel label;
    private JPanel panelLogoMini;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public FormNewUser() {
        label.setText("<html><p align='center'>Введите Ваши имя и фамилию<br>" +
                "для завершения регистрации</p>");
        Fonts.setFontToComponent(label, Fonts.getFontLabel(),Color.WHITE);
        Images.decorateAsImageButton(buttonReg, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        Fonts.setFontToComponent(buttonReg, Fonts.getFontButton(), Color.WHITE);
        fieldRegName.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.WHITE));
        fieldRegSurname.setBorder(BorderFactory.createMatteBorder(0,0,1,0,Color.WHITE));
        //Font font = new Font("Open Sans Light", Font.PLAIN, 40);
        Fonts.setFontToComponent(fieldRegName, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(fieldRegSurname, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        String hintName = "Имя";
        String hintSurname = "Фамилия";
        fieldRegSurname.setText(hintSurname);
        fieldRegName.setText(hintName);
        fieldRegName.setCaretColor(Color.WHITE);
        fieldRegSurname.setCaretColor(Color.WHITE);
        fieldRegName.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hintTextFieldFocusGained((JTextField) e.getComponent(), hintName);
            }

            @Override
            public void focusLost(FocusEvent e) {
                hintTextFieldLostFocus((JTextField) e.getComponent(), hintName);
            }
        });
        fieldRegSurname.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                hintTextFieldFocusGained((JTextField) e.getComponent(), hintSurname);
            }

            @Override
            public void focusLost(FocusEvent e) {
                hintTextFieldLostFocus((JTextField) e.getComponent(), hintSurname);
            }
        });
    }

    private void hintTextFieldFocusGained(JTextField textField, String hint) {
        textField.setForeground(Color.WHITE);
        if (textField.getText().equals(hint))
            textField.setText("");
    }
    private void hintTextFieldLostFocus(JTextField textField, String hint) {
        if (textField.getText().isEmpty()) {
            textField.setForeground(Color.LIGHT_GRAY);
            textField.setText(hint);
        }
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

    private void createUIComponents() {
        rootPanel = new ImagePanel(Images.getBackground(), true);
        panelLogoMini = new ImagePanel(Images.getLogoMini(), false);
    }
}
