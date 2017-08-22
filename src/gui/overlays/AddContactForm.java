package gui.overlays;

import additionally.*;
import gui.additionally.ContactInfo;
import gui.additionally.HintTextField;
import gui.additionally.HintTextFieldUnderlined;
import gui.additionally.ImagePanel;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.ParseException;


public class AddContactForm extends OverlayBackground {
    private JButton closeButton;
    private JButton addButton;
    private JPanel photoPanel;
    private JPanel rootPanel;
    private JLabel nameLabel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JLabel titleLabel;
    private JPanel panelNumber;
    private JPanel panelIconPhone;
    private JLabel labelPlus7;
    private JFormattedTextField fieldPhone;
    private JLabel labelPhone;

    {
        setContactInfo(new ContactInfo());

        ((HintTextField)firstNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)lastNameTextField).setHintAlignment(JTextField.CENTER);
        //((HintTextField)fieldPhone).setHintAlignment(JTextField.CENTER);

        Images.decorateAsImageButton(closeButton, Images.getIconBack(), null, null);
        Images.decorateAsImageButton(addButton, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);

        Helper.clearBoth(firstNameTextField);
        Helper.clearBoth(lastNameTextField);
        Helper.clearBoth(fieldPhone);
        Fonts.setFontToComponent(titleLabel, Fonts.getOverlayTitleFont(), Colors.getLightBlueColor());
        labelPhone.setText("<html><p align='center'>Введите код страны и номер <br> мобильного телефона пользователя");
        Fonts.setFontToComponent(labelPhone,Fonts.getFontLabel(), Color.WHITE);
        Fonts.setFontToComponent(firstNameTextField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(lastNameTextField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        addButton.setFont(Fonts.getFontButton());
        Fonts.setFontToComponent(fieldPhone, Fonts.getFontNumberLabel(),Color.WHITE);
        Fonts.setFontToComponent(labelPlus7, Fonts.getFontNumberLabel(), Color.WHITE);
        fieldPhone.setCaretColor(Color.WHITE);
        Border border = BorderFactory.createMatteBorder(0,0,2,0, Color.WHITE);
        fieldPhone.setBorder(border);
    }

    private void createUIComponents() throws ParseException {
        rootPanel = this;

        //Альтернтивное решение
        //closeButton = new ImageButton(Images.getCloseOverlay());
        //addButton = new ImageButton(Images.getAddContact());

        firstNameTextField = new HintTextFieldUnderlined("", "Имя", true, true);
        lastNameTextField = new HintTextFieldUnderlined("", "Фамилия", true, true);
//        fieldPhone = new HintTextFieldUnderlined("", "Телефон", true, true);
        MaskFormatter maskFormatter1 = new MaskFormatter("### ###-##-##");
        panelIconPhone = new ImagePanel(Images.getIconPhone(), false);
        fieldPhone = new JFormattedTextField(maskFormatter1);
    }

    public void setContactInfo(ContactInfo info) {
        firstNameTextField.setText(info.getFirstName());
        lastNameTextField.setText(info.getLastName());
        fieldPhone.setText(info.getPhone());
    }

    public ContactInfo getContactInfo() {
        return new ContactInfo("+7" + fieldPhone.getText().trim(),
                firstNameTextField.getText().trim(),
                lastNameTextField.getText().trim());
    }

    public void addActionListenerForAdd(ActionListener actionListener) {
        addButton.addActionListener(actionListener);
    }

    public void removeActionListenerForAdd(ActionListener actionListener) {
        addButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
