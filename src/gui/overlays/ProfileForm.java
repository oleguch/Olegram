package gui.overlays;

import additionally.Colors;
import additionally.Fonts;
import additionally.Images;
import gui.additionally.ContactInfo;
import gui.additionally.ImagePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProfileForm extends OverlayBackground {
    private JButton closeButton;
    private JButton logoutButton;
    private JPanel rootPanel;
    private JLabel phoneLabel;
    private JLabel titleLabel;
    private JLabel nameLabel;
    private JPanel photoPanel;

    private final static String phoneRegexFrom = "^\\+?(\\d*)(\\d{3})(\\d{3})(\\d{2})(\\d{2})$", phoneRegexTo = "+$1($2)$3-$4-$5";

    private String phone;
    private int id = 0;

    {

        //phoneLabel.setFont(additionally.Fonts.getNameFont().deriveFont(0, 30));
//        phoneLabel.setForeground(Color.white);

        Images.decorateAsImageButton(closeButton, Images.getIconBack(), null, null);
        Fonts.setFontToComponent(logoutButton, Fonts.getLogoutOverlayFont(), Colors.getLightBlueColor());
        Fonts.setFontToComponent(phoneLabel, Fonts.getLogoutOverlayFont(), Color.GRAY);
        logoutButton.setText("<html><u>ВЫЙТИ");
        Fonts.setFontToComponent(titleLabel, Fonts.getOverlayTitleFont(), Colors.getLightBlueColor());
        Fonts.setFontToComponent(nameLabel, Fonts.getFontForRegistrationField(), Colors.getLightBlueColor());

    }

    private void createUIComponents() {
        rootPanel = this;
        logoutButton = new JButton();
        photoPanel = new ImagePanel(null, true, true, 0);
        logoutButton.setOpaque(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setFocusPainted(false);
        logoutButton.setContentAreaFilled(false);


        //Альтернтивное решение
        //closeButton = new ImageButton(additionally.Images.getCloseOverlay());
        //logoutButton = new ImageButton(additionally.Images.getLogoutIcon());
    }

    public ContactInfo getContactInfo() {
        ContactInfo info = new ContactInfo();
        String[] data = nameLabel.getText().trim().split("\\s+", 2); //На случай редактирования, которого пока нет
        info.setFirstName(data.length > 0 ? data[0] : "");
        info.setLastName(data.length > 1 ? data[1] : "");
        info.setPhone(phone);
        info.setId(id);
        return info;
    }

    public void setContactInfo(ContactInfo contactInfo) {

        if (contactInfo != null) {
            ((gui.additionally.ImagePanel) photoPanel).setImage(contactInfo.getPhoto());
            //nameLabel.setText("Олег Самылов");
            nameLabel.setText(contactInfo.getFirstName() + " " + contactInfo.getLastName());
            phone = contactInfo.getPhone();
            phoneLabel.setText(contactInfo.getClearedPhone().replaceAll(phoneRegexFrom, phoneRegexTo));
            id = contactInfo.getId();
        } else {
            ((gui.additionally.ImagePanel) photoPanel).setImage(null);
            nameLabel.setText("");
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
