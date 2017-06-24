

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
        Font fontButton = new Font("Open Sans Light", Font.PLAIN, 30);        //Правильно ли? вроде подключил
        Font fontPhone = new Font("Open Sans Light", Font.PLAIN, 40);
        buttonPhone.setFont(fontButton);
        labelPlus7.setFont(fontPhone);
        labelPlus7.setForeground(Color.WHITE);
        Border border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);
        panelNumber.setBorder(border);
        titleLabel.setText("<html>Введите код страны и номер <br> вашего мобильного телефона");
        titleLabel.setFont(new Font("Open Sans Light", Font.PLAIN, 20));
        titleLabel.setForeground(Color.WHITE);
        fieldPhone.setBorder(BorderFactory.createEmptyBorder());
        fieldPhone.setCaretColor(Color.WHITE);
        fieldPhone.setForeground(Color.WHITE);
        fieldPhone.setFont(fontPhone);
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
