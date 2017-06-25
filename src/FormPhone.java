

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.ParseException;


public class FormPhone {
    private JFormattedTextField fieldPhone;
    private JPanel rootPanel;
    private JButton buttonPhone;
    private JLabel titleLabel;
    private JPanel panelLogo;
    private JPanel panelNumber;
    private JPanel panelIconPhone;
    private JLabel labelPlus7;


    public JPanel getRootPanel() {
        return rootPanel;
    }

    public FormPhone() throws ParseException, IOException, FontFormatException {
        Images.decorateAsImageButton(buttonPhone, Images.getButtonImage(), Images.getButtonImagePressed(), Color.WHITE);
        Fonts.setFontToComponent(buttonPhone, Fonts.getFontButton(), Color.WHITE);
        Fonts.setFontToComponent(labelPlus7, Fonts.getFontNumberLabel(), Color.WHITE);
        Fonts.setFontToComponent(fieldPhone, Fonts.getFontNumberLabel(),Color.WHITE);
        Fonts.setFontToComponent(titleLabel, Fonts.getFontLabel(), Color.WHITE);
        Border border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);
        panelNumber.setBorder(border);
        titleLabel.setText("<html><p align='center'>Введите код страны и номер <br> вашего мобильного телефона");
        fieldPhone.setBorder(BorderFactory.createEmptyBorder());
        fieldPhone.setCaretColor(Color.WHITE);
    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }

    private void createUIComponents() throws ParseException, IOException, FontFormatException {
        rootPanel = new ImagePanel(Images.getBackground(), true);
        panelLogo = new ImagePanel(Images.getLogo(), false);
        MaskFormatter maskFormatter1 = new MaskFormatter("### ###-##-##");
        panelIconPhone = new ImagePanel(Images.getIconPhone(), false);
        fieldPhone = new JFormattedTextField(maskFormatter1);
    }

    public String getPhoneNumber() throws ParseException {
        fieldPhone.commitEdit();
        return "+7" + fieldPhone.getValue();
    }
}
