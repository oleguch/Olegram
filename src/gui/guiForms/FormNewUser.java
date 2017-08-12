package gui.guiForms;

import additionally.Fonts;
import additionally.Images;
import gui.additionally.ImagePanel;
import gui.additionally.PersonNewUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

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
        Fonts.setFontToComponent(label, Fonts.getFontLabel(), Color.WHITE);
        Images.decorateAsImageButton(buttonReg, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        Fonts.setFontToComponent(buttonReg, Fonts.getFontButton(), Color.WHITE);
        fieldRegName.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        fieldRegSurname.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        Fonts.setFontToComponent(fieldRegName, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(fieldRegSurname, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        fieldRegName.setCaretColor(Color.WHITE);
        fieldRegSurname.setCaretColor(Color.WHITE);
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

    public PersonNewUser getPerson() {
        PersonNewUser person = new PersonNewUser();
        person.setName(fieldRegName.getText().trim());
        person.setSurname(fieldRegSurname.getText().trim());
        return person;
    }

    private void createUIComponents() {
        rootPanel = new ImagePanel(Images.getBackground(), true);
        panelLogoMini = new ImagePanel(Images.getLogoMini(), false);
        fieldRegName = new JFormattedTextField();
        fieldRegSurname = new JFormattedTextField();
    }
}
