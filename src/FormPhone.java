

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;


public class FormPhone {
    private JFormattedTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JLabel label;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public FormPhone() throws ParseException {
        Images.decorateAsImageButton(buttonPhone, Images.getButtonImage(), Images.getButtonImagePressed(), Color.BLACK);
        fieldPhone.setHorizontalAlignment(JFormattedTextField.CENTER);      //выравнивание по центру
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }

    private void createUIComponents() throws ParseException {
        rootPanel = new ImagePanel(Images.getBackground());
        MaskFormatter maskFormatter = new MaskFormatter("+7(###)###-##-##");
        maskFormatter.setPlaceholderCharacter('_');
        fieldPhone = new JFormattedTextField(maskFormatter);
        fieldPhone.setBorder(BorderFactory.createEmptyBorder());
        fieldPhone.setOpaque(false);
    }

    public String getPhoneNumber() throws ParseException {
        fieldPhone.commitEdit();
        return (String) fieldPhone.getValue();
    }
}
