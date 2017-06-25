import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionListener;

public class FormConfirmSMS {
    public JPanel getRootPanel() {
        return rootPanel;
    }

    private JPanel rootPanel;
    private JButton buttonSMS;
    private JPasswordField codeField;
    private JLabel numberLabel;
    private JPanel panelLogoMini;
    private JPanel panelCode;
    private JPanel panelIconCode;
    private JLabel labelText;

    public FormConfirmSMS() {
        codeField.setHorizontalAlignment(JPasswordField.CENTER);                        //выравнивание по центру
        DocumentFilter documentFilter = new CodeDocumentFilter();                  //фильтр кода смс
        ((AbstractDocument) codeField.getDocument()).setDocumentFilter(documentFilter); //добавление фильтра к полю смс
        Images.decorateAsImageButton(buttonSMS, Images.getButtonImage(), Images.getButtonImagePressed(), Color.BLACK);
        Border border = BorderFactory.createMatteBorder(0,0,1,0, Color.WHITE);
        panelCode.setBorder(border);
        Fonts.setFontToComponent(numberLabel, Fonts.getFontNumberLabel(), Color.LIGHT_GRAY);
        Fonts.setFontToComponent(buttonSMS, Fonts.getFontButton(), Color.WHITE);
        Fonts.setFontToComponent(labelText, Fonts.getFontLabel(), Color.WHITE);
        codeField.setBorder(BorderFactory.createEmptyBorder());
        codeField.setForeground(Color.WHITE);
        codeField.setCaretColor(Color.WHITE);
        labelText.setText("<html><p align='center'>На данный номер телефона было отправлено<br>" +
                "SMS-сообщение с кодом подтверждения.<br>" +
                "Пожалуйста, введите код в поле ниже:");

    }

    //добавление слушателя на переключение форм
    public void addActionListenerForChangeForm(ActionListener actionListener) {
        buttonSMS.addActionListener(actionListener);
        codeField.addActionListener(actionListener);
    }

    public void setPhoneNumberToLabel(String phoneNumber) {
        numberLabel.setText(phoneNumber);
    }


    public void setFocusToCodeField() {
        codeField.requestFocusInWindow();
    }

    public String getCode() {
        return new String(codeField.getPassword());
    }

    private void createUIComponents() {
        rootPanel = new ImagePanel(Images.getBackground(), true);
        panelLogoMini = new ImagePanel(Images.getLogoMini(), false);
        panelIconCode = new ImagePanel(Images.getIconLock(), true);
    }
}
