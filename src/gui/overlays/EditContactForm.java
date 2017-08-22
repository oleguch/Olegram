package gui.overlays;

import additionally.Colors;
import additionally.Fonts;
import additionally.Helper;
import additionally.Images;

import gui.additionally.ContactInfo;
import gui.additionally.HintTextField;
import gui.additionally.HintTextFieldUnderlined;
import gui.additionally.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;


public class EditContactForm extends OverlayBackground {
    private JButton closeButton;
    private JButton saveButton;
    private JPanel contactPanel;
    private JPanel rootPanel;
    private JTextField firstNameTextField;
    private JTextField lastNameTextField;
    private JTextField phoneTextField;
    private JButton deleteButton;
    private JPanel photoPanel;
    private JLabel titleLabel;
    private final static String phoneRegexFrom = "^\\+?(\\d*)(\\d{3})(\\d{3})(\\d{2})(\\d{2})$", phoneRegexTo = "+$1($2)$3-$4-$5";

    private int id;

    {
        setContactInfo(new ContactInfo());

        ((HintTextField)firstNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)lastNameTextField).setHintAlignment(JTextField.CENTER);
        ((HintTextField)phoneTextField).setHintAlignment(JTextField.CENTER);

        Images.decorateAsImageButton(closeButton, Images.getIconBack(), null, null);
        Images.decorateAsImageButton(deleteButton, Images.getDeleteUserButton(), null, null);
        Images.decorateAsImageButton(saveButton, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);

        Helper.clearBoth(firstNameTextField);
        Helper.clearBoth(lastNameTextField);
        Helper.clearBoth(phoneTextField);
        Fonts.setFontToComponent(firstNameTextField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(lastNameTextField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        //Fonts.setFontToComponent(phoneTextField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        saveButton.setFont(Fonts.getFontButton());
        Fonts.setFontToComponent(titleLabel, Fonts.getOverlayTitleFont(), Colors.getLightBlueColor());
        Fonts.setFontToComponent(phoneTextField, Fonts.getLogoutOverlayFont(), Color.LIGHT_GRAY);
    }

    private void createUIComponents() {
        rootPanel = this;

        //Альтернтивное решение
        //closeButton = new ImageButton(Images.getCloseOverlay());
        //deleteButton = new ImageButton(Images.getRemoveContact());
        //saveButton = new ImageButton(Images.getUpdateContact());

        firstNameTextField = new HintTextFieldUnderlined("", "Имя", true, true);
        lastNameTextField = new HintTextFieldUnderlined("", "Фамилия", true, true);
        phoneTextField = new HintTextFieldUnderlined("", "Телефон", true, true);

        photoPanel = new ImagePanel(null, true, false, 0);
    }

    public void setContactInfo(ContactInfo info) {
        firstNameTextField.setText(info.getFirstName());
        lastNameTextField.setText(info.getLastName());
        //phoneTextField.setText(info.getPhone());
        phoneTextField.setText(info.getClearedPhone().replaceAll(phoneRegexFrom, phoneRegexTo));
        ((ImagePanel)photoPanel).setImage(info.getPhoto());
        id = info.getId();
    }

    public ContactInfo getContactInfo() {
        ContactInfo info = new ContactInfo();
        info.setPhone(phoneTextField.getText().trim());
        info.setFirstName(firstNameTextField.getText().trim());
        info.setLastName(lastNameTextField.getText().trim());
        info.setPhoto((BufferedImage) ((ImagePanel)photoPanel).getImage());
        info.setId(id);
        return info;
    }

    public void addActionListenerForSave(ActionListener actionListener) {
        saveButton.addActionListener(actionListener);
    }

    public void removeActionListenerForSave(ActionListener actionListener) {
        saveButton.removeActionListener(actionListener);
    }

    public void addActionListenerForRemove(ActionListener actionListener) {
        deleteButton.addActionListener(actionListener);
    }

    public void removeActionListenerForRemove(ActionListener actionListener) {
        deleteButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
