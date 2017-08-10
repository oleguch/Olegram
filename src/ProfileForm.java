

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileForm extends OverlayBackground {
    private JButton closeButton;
    private JButton logoutButton;
    private JPanel rootPanel;
    private JLabel phoneLabel;
    private JLabel titleLabel;
    private JTextField lastnameUserField;
    private JTextField nameUserField;
    private JButton saveButton;

    private final static String phoneRegexFrom = "^\\+?(\\d*)(\\d{3})(\\d{3})(\\d{2})(\\d{2})$", phoneRegexTo = "+$1($2)$3-$4-$5";

    private String phone;
    private int id = 0;

    {
        Images.decorateAsImageButton(saveButton, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        saveButton.setFont(Fonts.getFontButton());

        //phoneLabel.setFont(Fonts.getNameFont().deriveFont(0, 30));
        phoneLabel.setForeground(Color.white);

        Images.decorateAsImageButton(closeButton, Images.getIconBack(), null, null);
        Fonts.setFontToComponent(logoutButton, Fonts.getLogoutOverlayFont(), Colors.getLightBlueColor());
        nameUserField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        lastnameUserField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));
        Fonts.setFontToComponent(nameUserField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(lastnameUserField, Fonts.getFontForRegistrationField(), Color.LIGHT_GRAY);
        nameUserField.setCaretColor(Color.WHITE);
        lastnameUserField.setCaretColor(Color.WHITE);
        Fonts.setFontToComponent(phoneLabel, Fonts.getFontLabel(), Color.GRAY);
        logoutButton.setText("<html><u>ВЫЙТИ");
        Fonts.setFontToComponent(titleLabel, Fonts.getOverlayTitleFont(), Colors.getLightBlueColor());
        titleLabel.setText("<html><p align='center'>Настройки профиля");
        saveButton.setFocusPainted(false);
    }

    private void createUIComponents() {
        rootPanel = this;
        logoutButton = new JButton();

        logoutButton.setOpaque(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);

        //photoPanel = new ImagePanel(null, true, true, 0);

        //Альтернтивное решение
        //closeButton = new ImageButton(Images.getCloseOverlay());
        //logoutButton = new ImageButton(Images.getLogoutIcon());
    }

    public ContactInfo getContactInfo() {
        ContactInfo info = new ContactInfo();
//        String[] data = nameLabel.getText().trim().split("\\s+", 2); //На случай редактирования, которого пока нет
//        info.setFirstName(data.length > 0 ? data[0] : "");
        info.setFirstName(nameUserField.getText().trim());
        info.setLastName(lastnameUserField.getText().trim());
//        info.setLastName(data.length > 1 ? data[1] : "");
        info.setPhone(phone);
        info.setId(id);
        return info;
    }

    public void setContactInfo(ContactInfo contactInfo) {

        if (contactInfo != null) {
            //((ImagePanel) photoPanel).setImage(contactInfo.getPhoto());
            //nameLabel.setText(contactInfo.getFirstName() + " " + contactInfo.getLastName());
            nameUserField.setText(contactInfo.getFirstName());
            lastnameUserField.setText(contactInfo.getLastName());
            phone = contactInfo.getPhone();
            phoneLabel.setText(contactInfo.getClearedPhone().replaceAll(phoneRegexFrom, phoneRegexTo));
            id = contactInfo.getId();
        } else {
//            ((ImagePanel) photoPanel).setImage(null);
            //nameLabel.setText("");
            phone = "";
            phoneLabel.setText("");
            id = 0;
        }
        System.out.println(phone);

    }

    public void addActionListenerForLogout(ActionListener actionListener) {
        logoutButton.addActionListener(actionListener);
    }

    public void removeActionListenerForLogout(ActionListener actionListener) {
        logoutButton.removeActionListener(actionListener);
    }

    public void addActionListenerForClose(ActionListener actionListener) {
        closeButton.addActionListener(actionListener);
    }

    public void removeActionListenerForClose(ActionListener actionListener) {
        closeButton.removeActionListener(actionListener);
    }
}
