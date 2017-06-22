

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
    private BufferedImage background;
    private BufferedImage buttonBackground;

    public JPanel getRootPanel() {
        return rootPanel;
    }

    public FormPhone() throws ParseException {
        fieldPhone.setHorizontalAlignment(JFormattedTextField.CENTER);      //выравнивание по центру
        try {
            background = ImageIO.read(new File("res/img/background.png"));
            buttonBackground = ImageIO.read(new File("res/img/button-background.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonPhone.addActionListener(actionListener);
        fieldPhone.addActionListener(actionListener);
    }

    public void setFocusToFieldPhone() {
        fieldPhone.requestFocusInWindow();
    }

    private void createUIComponents() throws ParseException {
        rootPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(background, 0,0,null);
            }
        };
        buttonPhone = new JButton(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(buttonBackground,0,0,null);
            }
        };

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
